package com.westat.ctsu.template;

import static com.westat.ctsu.util.JiraConstants.OUTPUT_DIR;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.wickedsource.docxstamper.DocxStamper;
import org.wickedsource.docxstamper.DocxStamperConfiguration;

import com.westat.ctsu.DesignTemplateModel;
import com.westat.ctsu.TemplateConfiguration.JiraProperties;
import com.westat.ctsu.template.strategy.ChangeListStrategyFactory;
import com.westat.ctsu.util.ChangeListEnum;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;


@Service
public class ServiceDesignTemplate extends AbstractDesignTemplate {

	@Override
	public DesignTemplateModel buildTemplateModel(DesignTemplateModel tModel) {
		tConfig.setSourceTemplateName(tConfig.getServiceTemplateName());
		tModel.setDescription(getDescriptionsFromJira(tConfig.getWorkItems()));
		final JiraProperties jiraProps = tConfig.getJiraProps();
		BasicCredentials creds = new BasicCredentials(jiraProps.getUsername(), jiraProps.getPassword());
		final JiraClient jira = new JiraClient(jiraProps.getUrl(), creds);
		try {
			Issue issue = jira.getIssue(tConfig.getWorkItems());
			tModel.setTitle(issue.getSummary());
			populateModelWithImages(issue.getAttachments());
			updateDisplayName(issue);
		} catch (JiraException e) {
			System.err.println("JiraException ::" + e);
		}
		buildTemplateModelWithChangelist(tModel);
		return tModel;
	}

	private String getDescriptionsFromJira(String workItems) {
		final JiraProperties jiraProps = tConfig.getJiraProps();
		BasicCredentials creds = new BasicCredentials(jiraProps.getUsername(), jiraProps.getPassword());
		final JiraClient jira = new JiraClient(jiraProps.getUrl(), creds);
		StringBuilder sb = new StringBuilder();
		if (workItems != null) {
			Arrays.asList(workItems.split(",")).forEach(jiraNo -> {
				try {
					Issue issue = jira.getIssue(jiraNo);
					if (!issue.getIssueLinks().isEmpty() && !tConfig.isOfflineReqJira()) {
						issue.getIssueLinks().forEach(issueLink -> {
							Issue reqIssue = issueLink.getOutwardIssue();
							// reqIssue = (reqIssue == null) ? issueLink.getOutwardIssue() : reqIssue;
							if (reqIssue != null && StringUtils.isNotBlank(reqIssue.getKey())) {
								try {
									sb.append(jira.getIssue(reqIssue.getKey()).getDescription())
											.append("\t\t\t\t\t \t\t\t");
								} catch (JiraException e) {
									System.err.println("JiraException :: Exception :: " + e);
								}
							} else {
								System.out.println("reqIssue is null");
							}
						});
					} else {
						sb.append(issue.getDescription()).append("\t\t\t\t\t \t\t\t");
					}
				} catch (Exception e2) {
					System.err.println("Exception @ Template generation for jira:: " + jiraNo + " : Exception:" + e2);
				}
			});
		}
		return sb.toString();
	}

	private void buildTemplateModelWithChangelist(DesignTemplateModel templateModel) {
		try {
			List<String> list = ChangeListStrategyFactory.getChangeListStrategy(tConfig.getChangeListEnum())
					.getChangeList(tConfig);
			List<String> contollerList = new ArrayList<>();
			List<String> serviceList = new ArrayList<>();
			List<String> daoList = new ArrayList<>();
			List<String> utilList = new ArrayList<>();
			List<String> dtoList = new ArrayList<>();
			List<String> otherList = new ArrayList<>();
			List<String> uiFileList = new ArrayList<>();
			String suffix = tConfig.getChangeListEnum() == ChangeListEnum.PROJECT ? ".java" : "";
			System.out.println(" file list ::" + list);
			for (String filePath : list) {
				if (filePath.endsWith("Test"))
					continue;
				if (filePath.contains("Controller")) {
					contollerList.add(filePath + suffix);
				} else if (filePath.contains("Service")) {
					serviceList.add(filePath + suffix);
				} else if (filePath.contains("Dao") || filePath.contains("DaoImpl") || filePath.contains("Entity")) {
					daoList.add(filePath + suffix);
				} else if (filePath.contains("utils")) {
					utilList.add(filePath + suffix);
				} else if (filePath.endsWith("Dto")) {
					dtoList.add(filePath + suffix);
				} else if (filePath.indexOf(".jsp") != -1 || filePath.indexOf(".html") != -1
						|| filePath.indexOf(".js") != -1 || filePath.indexOf(".css") != -1) {
					uiFileList.add(filePath);
				} else {
					otherList.add(filePath + suffix);
				}
			}
			templateModel.setControllerFileList(list2StringConverter(contollerList));
			templateModel.setServiceFileList(list2StringConverter(serviceList));
			templateModel.setDtoFileList(list2StringConverter(dtoList));
			templateModel.setDaoFileList(list2StringConverter(daoList));
			utilList.addAll(otherList);
			templateModel.setUtilFileList(list2StringConverter(utilList));
			templateModel.setUiFileList(list2StringConverter(uiFileList));
		} catch (Exception e) {
			System.err.println("exception CNF :: " + e);
		}
	}

	@Override
	public void generateBatchDesignTemplates(DesignTemplateModel tModel) {
		JiraProperties jiraProps = tConfig.getJiraProps();
		BasicCredentials creds = new BasicCredentials(jiraProps.getUsername(), jiraProps.getPassword());
		final JiraClient jira = new JiraClient(jiraProps.getUrl(), creds);
		List<String> jiraList = Arrays.asList(tConfig.getWorkItems().split(","));
		jiraList.forEach(jiraNo -> {
			jiraNo = jiraNo.trim();
			tConfig.setWorkItems(jiraNo);
			try (FileInputStream fis = new FileInputStream(tConfig.getServiceTemplateName())) {
				Issue issue = jira.getIssue(jiraNo);
				populateModelWithImages(issue.getAttachments());
				updateDisplayName(issue);
				try (FileOutputStream fos = new FileOutputStream(OUTPUT_DIR + issue.getKey() + ".docx")) {
					DesignTemplateModel designModel = getTemplateModel(issue, jira, tModel);
					DocxStamper<DesignTemplateModel> stamper = new DocxStamper<>(new DocxStamperConfiguration());
					stamper.stamp(fis, designModel, fos);
				} catch (Exception e) {
					System.err.println("fis error :: " + e);
				}
				System.out.println("Template Generation Successfully Completed for Jira : " + jiraNo);
			} catch (Exception e2) {
				System.err.println("Exception @ Template generation for jira:: " + jiraNo + " : Exception:" + e2);
			}
		});
	}

	private DesignTemplateModel getTemplateModel(Issue issue, final JiraClient jira, DesignTemplateModel tModel) {
		tModel.setDescription(getDescriptionsFromJira(issue.getKey()));
		tModel.setSummary(issue.getSummary());
		tModel.setJiraNo(issue.getKey());
		tModel.setTitle(issue.getSummary());
		buildTemplateModelWithChangelist(tModel);
		return tModel;
	}
}
