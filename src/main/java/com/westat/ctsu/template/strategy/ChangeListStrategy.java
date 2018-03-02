package com.westat.ctsu.template.strategy;

import java.util.List;

import com.westat.ctsu.TemplateConfiguration;

/**
 * @author yandagudita_s
 *
 */
public interface ChangeListStrategy {

  public List<String> getChangeList(final TemplateConfiguration tConfig);
}
