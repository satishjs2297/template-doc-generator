package com.westat.ctsu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.westat.ctsu.template.DesignTemplate;
import com.westat.ctsu.template.DesignTemplateFactory;
import com.westat.ctsu.util.TemplateEnum;

/**
 * @author yandagudita_s
 *
 */

@Service
public class DesignTemplateManager {
  
  @Autowired
  private ApplicationContext appContext;
  
  @Autowired
  private TemplateConfiguration tConfig;
  
  public void generateDesignTemplate() {
    DesignTemplate designTemplate = DesignTemplateFactory.getDesignTemplate(appContext, TemplateEnum.valueOf(tConfig.getTemplateEnum()));    
    designTemplate.generateDesignTemplate();
  }

}
