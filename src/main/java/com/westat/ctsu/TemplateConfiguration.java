package com.westat.ctsu;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.westat.ctsu.util.ChangeListEnum;

@Component
@ConfigurationProperties("appConfig")
public class TemplateConfiguration {

  /*Template & Application Configuration*/
  private String moduleName;
  private String workItems=""; //jira items
  private String displayName;
  private String reqItems;
  private String templateEnum;
  private String changeListEnum;
  private String sourceTemplateName;
  private String targetTemplateName;
  private String rootPackage;
  private String jiraQuery;
  private boolean offlineReqJira;
  private String pathBreaker;
  
  private JiraProperties jiraProps;
  private SVNProperties  svnProps;
  private String serviceTemplateName;
  private String uiTemplateName;
  private String uiSourcePath;
  private String generationType;

  public static class JiraProperties extends BaseProperties {
    private List<String> workItems;
    private List<String> jiraNums;
    public List<String> getWorkItems() {
      return workItems;
    }
    public void setWorkItems(List<String> workItems) {
      this.workItems = workItems;
    }
    public List<String> getJiraNums() {
      return jiraNums;
    }
    public void setJiraNums(List<String> jiraNums) {
      this.jiraNums = jiraNums;
    }
    
  }
  
  public static class SVNProperties  extends BaseProperties {
    private String rootPackage;

    public String getRootPackage() {
      return rootPackage;
    }

    public void setRootPackage(String rootPackage) {
      this.rootPackage = rootPackage;
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("SVNProperties [rootPackage=");
      builder.append(rootPackage);
      builder.append("]");
      return builder.toString();
    }
    
  }
  
  static class BaseProperties {
    private String url;
    private String username;
    private String password;
    public String getUrl() {
      return url;
    }
    public void setUrl(String url) {
      this.url = url;
    }
    public String getUsername() {
      return username;
    }
    public void setUsername(String username) {
      this.username = username;
    }
    public String getPassword() {
      return password;
    }
    public void setPassword(String password) {
      this.password = password;
    }
    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("BaseProperties [url=");
      builder.append(url);
      builder.append(", username=");
      builder.append(username);
      builder.append(", password=");
      builder.append(password);
      builder.append("]");
      return builder.toString();
    }
    
  }

  public JiraProperties getJiraProps() {
    return jiraProps;
  }

  public void setJiraProps(JiraProperties jiraProps) {
    this.jiraProps = jiraProps;
  }

  public SVNProperties getSvnProps() {
    return svnProps;
  }

  public void setSvnProps(SVNProperties svnProps) {
    this.svnProps = svnProps;
  }

  public String getServiceTemplateName() {
    return serviceTemplateName;
  }

  public void setServiceTemplateName(String serviceTemplateName) {
    this.serviceTemplateName = serviceTemplateName;
  }

  public String getUiTemplateName() {
    return uiTemplateName;
  }

  public void setUiTemplateName(String uiTemplateName) {
    this.uiTemplateName = uiTemplateName;
  }

  public String getUiSourcePath() {
    return uiSourcePath;
  }

  public void setUiSourcePath(String uiSourcePath) {
    this.uiSourcePath = uiSourcePath;
  }

  public String getModuleName() {
    return moduleName;
  }
  public void setModuleName(String moduleName) {
    this.moduleName = moduleName;
  }
  public String getWorkItems() {
    return workItems;
  }
  public void setWorkItems(String workItems) {
    this.workItems = workItems;
  }
  public String getReqItems() {
    return reqItems;
  }
  public void setReqItems(String reqItems) {
    this.reqItems = reqItems;
  }
  public String getTemplateEnum() {
    return templateEnum;
  }
  public void setTemplateEnum(String templateEnum) {
    this.templateEnum = templateEnum;
  }
  public ChangeListEnum getChangeListEnum() {
    return ChangeListEnum.valueOf(changeListEnum);
  }
  public void setChangeListEnum(String changeListEnum) {
    this.changeListEnum = changeListEnum;
  }
  public String getSourceTemplateName() {
    return sourceTemplateName;
  }
  public void setSourceTemplateName(String sourceTemplateName) {
    this.sourceTemplateName = sourceTemplateName;
  }
  public String getTargetTemplateName() {
    return targetTemplateName;
  }
  public void setTargetTemplateName(String targetTemplateName) {
    this.targetTemplateName = targetTemplateName;
  }
  public String getDisplayName() {
    return displayName;
  }
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }
  public String getRootPackage() {
    return rootPackage;
  }
  public void setRootPackage(String rootPackage) {
    this.rootPackage = rootPackage;
  }
  
  public String getGenerationType() {
    return generationType;
  }

  public void setGenerationType(String generationType) {
    this.generationType = generationType;
  }

  public String getJiraQuery() {
    return jiraQuery;
  }

  public void setJiraQuery(String jiraQuery) {
    this.jiraQuery = jiraQuery;
  }

  public boolean isOfflineReqJira() {
    return offlineReqJira;
  }

  public void setOfflineReqJira(boolean offlineReqJira) {
    this.offlineReqJira = offlineReqJira;
  }

  public String getPathBreaker() {
    return pathBreaker;
  }

  public void setPathBreaker(String pathBreaker) {
    this.pathBreaker = pathBreaker;
  }
}
