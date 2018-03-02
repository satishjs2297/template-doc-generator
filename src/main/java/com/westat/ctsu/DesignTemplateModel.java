package com.westat.ctsu;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.wickedsource.docxstamper.replace.typeresolver.image.Image;

public class DesignTemplateModel {

	private String jiraNo;
	private String summary;
	private String description;
	private String title;
	private String displayName;

	// Service Template
	private List<FileObj> controllerFileList = null;
	private List<FileObj> serviceFileList = null;
	private List<FileObj> dtoFileList = null;
	private List<FileObj> daoFileList = null;
	private List<FileObj> utilFileList = null;
	private String validatorFileList = "";
	private List<FileObj> uiFileList = null;

	// UI Template
	private List<FileObj> components = null;
	private List<FileObj> models = null;
	private List<FileObj> modules = null;
	private List<FileObj> routes = null;
	private List<FileObj> services = null;
	private List<FileObj> utils = null;
	private List<FileObj> htmlCss = null;

	private Image sequenceDiagram;
	private Image activityDiagram;
	private Image classDiagram;
	private Image stateDiagram;

	private String sequenceDiagramCaption;
	private String activityDiagramCaption;
	private String classDiagramCaption;
	private String stateDiagramCaption;

	public String getSequenceDiagramCaption() {
		return trimExtensionAndAddSpace(sequenceDiagramCaption);
	}

	public void setSequenceDiagramCaption(String sequenceDiagramCaption) {
		this.sequenceDiagramCaption = sequenceDiagramCaption;
	}

	public String getActivityDiagramCaption() {
		return trimExtensionAndAddSpace(activityDiagramCaption);
	}

	public void setActivityDiagramCaption(String activityDiagramCaption) {
		this.activityDiagramCaption = activityDiagramCaption;
	}

	public String getClassDiagramCaption() {
		return trimExtensionAndAddSpace(classDiagramCaption);
	}

	public void setClassDiagramCaption(String classDiagramCaption) {
		this.classDiagramCaption = classDiagramCaption;
	}

	public String getStateDiagramCaption() {
		return trimExtensionAndAddSpace(stateDiagramCaption);
	}

	public void setStateDiagramCaption(String stateDiagramCaption) {
		this.stateDiagramCaption = stateDiagramCaption;
	}

	public String getJiraNo() {
		return StringUtils.defaultIfBlank(jiraNo, "NA");
	}

	public void setJiraNo(String jiraNo) {
		this.jiraNo = jiraNo;
	}

	public String getSummary() {
		return StringUtils.defaultIfBlank(summary, "NA");
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDescription() {
		return StringUtils.defaultIfBlank(description, "NA");
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return StringUtils.defaultIfBlank(title, "NA");
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public List<FileObj> getComponents() {
		return components;
	}
	public void setComponents(List<FileObj> arrayList) {
		this.components = arrayList;
	}

	public List<FileObj> getModels() {
		return models;
	}
	public void setModels(List<FileObj> models) {
		this.models = models;
	}

	public List<FileObj> getModules() {
		return modules;
	}
	public void setModules(List<FileObj> arrayList) {
		this.modules = arrayList;
	}

	public List<FileObj> getRoutes() {
		return routes;
	}
	public void setRoutes(List<FileObj> routes) {
		this.routes = routes;
	}

	public List<FileObj> getServices() {
		return services;
	}
	public void setServices(List<FileObj> arrayList) {
		this.services = arrayList;
	}

	public List<FileObj> getUtils() {
		return utils;
	}
	public void setUtils(List<FileObj> utils) {
		this.utils = utils;
	}

	public List<FileObj> getHtmlCss() {
		return htmlCss;
	}
	public void setHtmlCss(List<FileObj> htmlCss) {
		this.htmlCss = htmlCss;
	}

	public List<FileObj> getControllerFileList() {
		return controllerFileList;
	}

	public void setControllerFileList(List<FileObj> arrayList) {
		this.controllerFileList = arrayList;
	}
	public List<FileObj> getServiceFileList() {
		return serviceFileList;
	}

	public void setServiceFileList(List<FileObj> arrayList) {
		this.serviceFileList = arrayList;
	}
	public List<FileObj> getDtoFileList() {
		return dtoFileList;
	}

	public void setDtoFileList(List<FileObj> arrayList) {
		this.dtoFileList = arrayList;
	}

	public List<FileObj> getDaoFileList() {
		return daoFileList;
	}

	public void setDaoFileList(List<FileObj> arrayList) {
		this.daoFileList = arrayList;
	}

	public List<FileObj> getUtilFileList() {
		return utilFileList;
	}

	public void setUtilFileList(List<FileObj> utilList) {
		this.utilFileList = utilList;
	}

	public String getValidatorFileList() {
		return validatorFileList;
	}

	public void setValidatorFileList(String validatorFileList) {
		this.validatorFileList = validatorFileList;
	}

	public List<FileObj> getUiFileList() {
		return uiFileList;
	}

	public void setUiFileList(List<FileObj> arrayList) {
		this.uiFileList = arrayList;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Image getSequenceDiagram() {
		return sequenceDiagram;
	}

	public void setSequenceDiagram(Image sequenceDiagram) {
		this.sequenceDiagram = sequenceDiagram;
	}

	public Image getActivityDiagram() {
		return activityDiagram;
	}

	public void setActivityDiagram(Image activityDiagram) {
		this.activityDiagram = activityDiagram;
	}

	public Image getClassDiagram() {
		return classDiagram;
	}

	public void setClassDiagram(Image classDiagram) {
		this.classDiagram = classDiagram;
	}

	public Image getStateDiagram() {
		return stateDiagram;
	}

	public void setStateDiagram(Image stateDiagram) {
		this.stateDiagram = stateDiagram;
	}

	private String trimExtensionAndAddSpace(String filename) {
		String captionWithHypen = filename.substring(0, filename.length() - 4);
		String captionWithSpace = captionWithHypen.replace("-", "  ");
		return captionWithSpace;
	}

	public static class FileObj {
		private String name;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		@Override
		public String toString() {
			return name;
		}
	}

}
