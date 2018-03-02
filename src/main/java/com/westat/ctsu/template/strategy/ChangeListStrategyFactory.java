package com.westat.ctsu.template.strategy;

import com.westat.ctsu.util.ChangeListEnum;

public class ChangeListStrategyFactory {

  public static ChangeListStrategy getChangeListStrategy(ChangeListEnum enumChangeList) {
    ChangeListStrategy cStrategy = null;
    switch (enumChangeList) {
      case SVN:
        cStrategy = new SVNChangeListStrategy();
        break;
      case PROJECT:
        cStrategy = new ProjectScanChangeListStrategy();
        break;
    }
    return cStrategy;

  }
}
