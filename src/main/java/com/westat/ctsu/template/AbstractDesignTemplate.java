package com.westat.ctsu.template;

import static com.westat.ctsu.util.JiraConstants.JIRA_STATUS;
import static com.westat.ctsu.util.JiraConstants.OUTPUT_DIR;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.wickedsource.docxstamper.DocxStamper;
import org.wickedsource.docxstamper.DocxStamperConfiguration;
import org.wickedsource.docxstamper.replace.typeresolver.image.Image;

import com.westat.ctsu.DesignTemplateModel;
import com.westat.ctsu.DesignTemplateModel.FileObj;
import com.westat.ctsu.TemplateConfiguration;
import com.westat.ctsu.TemplateConfiguration.JiraProperties;
import com.westat.ctsu.util.DesignImageTypeEnum;

import net.rcarz.jiraclient.Attachment;
import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;

public abstract class AbstractDesignTemplate implements DesignTemplate {

  protected DesignTemplateModel tModel;
  @Autowired
  protected TemplateConfiguration tConfig;
  
  public AbstractDesignTemplate() {
  }

  @Override
  public void generateDesignTemplate() {
    loadTemplateModel();
    if ("BATCH".equalsIgnoreCase(tConfig.getGenerationType())) {
      this.generateBatchDesignTemplates(tModel);
    } else {
      DesignTemplateModel dModel = buildTemplateModel(tModel);
      try (FileInputStream fis = new FileInputStream(tConfig.getSourceTemplateName())) {
        try (FileOutputStream fos = new FileOutputStream(
            OUTPUT_DIR + tConfig.getTargetTemplateName() + ".docx")) {
          DocxStamper<DesignTemplateModel> stamper = new DocxStamper<>(new DocxStamperConfiguration());
          stamper.stamp(fis, dModel, fos);         
        } catch (Exception e) {
          System.err.println("fis error :: " + e);
        }
        System.out.println("Template Generation Successfully Completed for Jira : ");
      } catch (Exception e2) {
        System.err.println("Exception @ Template generation for jira:: Exception:" + e2);
      }
    }
  }

  public abstract DesignTemplateModel buildTemplateModel(DesignTemplateModel tModel);
  public abstract void generateBatchDesignTemplates(DesignTemplateModel tModel);

  protected List<FileObj> list2StringConverter(Collection<String> fileList) {
    List<FileObj> fileObjList = new ArrayList<FileObj>();
    fileList.forEach(fileName -> {
      FileObj fileObj = new FileObj();
      fileObj.setName(fileName);
      fileObjList.add(fileObj);
    });
    return fileObjList;
  }

  private void loadTemplateModel() {
    tModel = new DesignTemplateModel();
    tModel.setJiraNo(tConfig.getWorkItems());
    tModel.setDisplayName(tConfig.getDisplayName());
    tConfig.setTargetTemplateName(tConfig.getModuleName());    
    List<Issue> jiraIssues = geJiratIssuesByQueryString(tModel);
    jiraIssues.forEach(issue -> {
      tConfig.setWorkItems(tConfig.getWorkItems().length() > 0 ? 
          tConfig.getWorkItems() + "," + issue.getKey() :
          issue.getKey() + ",");
    });
    System.out.println("tModel.getWorkItems() :: "+tConfig.getWorkItems());
    //dModel.setChangeListEnum(ChangeListEnum.valueOf(tConfig.getChangeListEnum()));
  }
  
  private List<Issue> geJiratIssuesByQueryString(DesignTemplateModel tModel) {
    String jiraQuery = tConfig.getJiraQuery();
    try {
      JiraProperties jiraProps = tConfig.getJiraProps();
      BasicCredentials creds = new BasicCredentials(jiraProps.getUsername(), jiraProps.getPassword());
      final JiraClient jira = new JiraClient(jiraProps.getUrl(), creds);
      return jiraQuery != null ? jira.searchIssues(jiraQuery).issues : Collections.emptyList();
    } catch (JiraException e1) {
      System.err.println("exception CNF :: " + e1);
    }
    return Collections.emptyList();
  }
  
  protected void populateModelWithImages(List<Attachment> attachments) {
	  try {
		  List<Attachment> filterAttachments = attachments.stream().filter(attachment -> {
			   return DesignImageTypeEnum.isImage(attachment);
		  }).collect(Collectors.toList());
		  for(Attachment pAttachment : filterAttachments) {
			  if(DesignImageTypeEnum.SEQUENCE.isMatched(pAttachment.getFileName())) {
				  this.tModel.setSequenceDiagram(new Image(pAttachment.download()));
				  this.tModel.setSequenceDiagramCaption(pAttachment.getFileName());
			  } else if(DesignImageTypeEnum.ACTIVITY.isMatched(pAttachment.getFileName())) {
				  this.tModel.setActivityDiagram(new Image(pAttachment.download()));
				  this.tModel.setActivityDiagramCaption(pAttachment.getFileName());
			  } else if(DesignImageTypeEnum.CLASS.isMatched(pAttachment.getFileName())) {
				  this.tModel.setClassDiagram(new Image(pAttachment.download()));
				  this.tModel.setClassDiagramCaption(pAttachment.getFileName());
			  } else if(DesignImageTypeEnum.STATE.isMatched(pAttachment.getFileName())) {
				  this.tModel.setClassDiagram(new Image(pAttachment.download()));
				  this.tModel.setStateDiagramCaption(pAttachment.getFileName());
			  }
		  }
	  } catch (Exception e) {
		  System.out.println("Failed to load the images ::"+e.getMessage());
	}
  }

  protected void updateDisplayName(Issue issue) {
	if (JIRA_STATUS.contains(issue.getStatus().getName())) {
		tModel.setDisplayName(issue.getComments().get(issue.getComments().size() - 1).getAuthor().getDisplayName());
	} if(issue.getAssignee() != null) {
		tModel.setDisplayName(issue.getAssignee().getDisplayName());
	} else {
		tModel.setDisplayName("NA");
	}
  }
}
