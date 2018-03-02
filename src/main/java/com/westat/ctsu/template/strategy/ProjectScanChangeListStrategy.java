package com.westat.ctsu.template.strategy;

import java.util.ArrayList;
import java.util.List;

import com.westat.ctsu.TemplateConfiguration;
import com.westat.ctsu.template.ProjectScanner;

public class ProjectScanChangeListStrategy implements ChangeListStrategy {

  @Override
  public List<String> getChangeList(final TemplateConfiguration tConfig) {
    List<String> changeList = new ArrayList<>();
    try {
      ProjectScanner ps = new ProjectScanner();
      changeList = ps.getRecursiveClassName(tConfig.getModuleName(), tConfig.getRootPackage());
    } catch (ClassNotFoundException e) {
      System.err.println("e :: "+ e);
    }
    return changeList;
  }

}
