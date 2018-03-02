package com.westat.ctsu.template;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.springframework.stereotype.Service;

import com.westat.ctsu.DesignTemplateModel;
import com.westat.ctsu.DesignTemplateModel.FileObj;;

@Service
public class UIDesignTemplate extends AbstractDesignTemplate {

  @Override
  public DesignTemplateModel buildTemplateModel(DesignTemplateModel tModel) {
    tConfig.setSourceTemplateName(tConfig.getUiTemplateName());
    tModel.setComponents(filterListByMatchWord(".component.ts", tModel));
    tModel.setServices(filterListByMatchWord(".service.ts", tModel));
    tModel.setModules(filterListByMatchWord(".module.ts", tModel));
    tModel.setModels(filterListByMatchWord(".model.ts", tModel));
    List<FileObj> htmlCssList = filterListByMatchWord(".component.html", tModel);
    htmlCssList.addAll(filterListByMatchWord(".component.css", tModel));
    htmlCssList.addAll(filterListByMatchWord(".component.css", tModel));
    tModel.setHtmlCss(htmlCssList);
    return tModel;
  }
  private List<FileObj> filterListByMatchWord(String matchString, DesignTemplateModel tModel) {
    List<String> list = getFilePathByModuleName(tModel);
    List<String> filterList = list.stream().filter(path -> path.contains(matchString))
        .collect(Collectors.toList());
    return list2StringConverter(filterList);
  }
  private List<String> getFilePathByModuleName(DesignTemplateModel tModel) {
    final String dir = tConfig.getUiSourcePath() + tConfig.getModuleName();
    Collection<File> files = FileUtils.listFiles(new File(dir), new RegexFileFilter("^(.*?)"),
        DirectoryFileFilter.DIRECTORY);
    List<String> filePathList = new LinkedList<>();
    files.forEach(file -> {
      String path = file.getPath();      
      filePathList.add(path.substring(path.indexOf("\\app")));
    });
    return filePathList;
  }
  @Override
  public void generateBatchDesignTemplates(DesignTemplateModel tModel) {
    // TODO Currently point batch to single 
	  tConfig.setGenerationType("SINGLE");
	  super.generateDesignTemplate();
  }
}
