package com.westat.ctsu.template;

import org.springframework.context.ApplicationContext;

import com.westat.ctsu.util.TemplateEnum;

public class DesignTemplateFactory {

  public static DesignTemplate getDesignTemplate(ApplicationContext context,
      TemplateEnum template) {
    DesignTemplate dTemplate = null;
    switch (template) {
      case UI:
        return context.getBean(UIDesignTemplate.class);
      case SERVICE:
        return context.getBean(ServiceDesignTemplate.class);
      case DB:
        return context.getBean(DBDesignTemplate.class);
    }
    return dTemplate;
  }

}
