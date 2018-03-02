package com.westat.ctsu.template.strategy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.westat.ctsu.TemplateConfiguration;
import com.westat.ctsu.TemplateConfiguration.SVNProperties;

public class SVNChangeListStrategy implements ChangeListStrategy {

  //Caching logEntries
  private Collection<SVNLogEntry> logEntries = null;
  
  @Override
  public List<String> getChangeList(final TemplateConfiguration tConfig) {
    List<String> changeList = new ArrayList<>();
    Arrays.asList(tConfig.getWorkItems().split(",")).forEach(item -> {
      changeList.addAll(getSVNRevisionListByJiraId(item, tConfig));  
    });
    return changeList;
  }
  private List<String> getSVNRevisionListByJiraId(String jiraId, final TemplateConfiguration tConfig) {
    List<String> svnChangeList = new ArrayList<>();
    try {
      if (logEntries == null) {
        logEntries = getLogEntries(tConfig);
      }
      logEntries.forEach(logEntry -> {
        try {
          if (logEntry.getMessage().contains(jiraId) && logEntry.getChangedPaths().size() > 0) {
            Set<String> changedPathsSet = logEntry.getChangedPaths().keySet();
            for (Iterator<String> changedPaths = changedPathsSet.iterator(); changedPaths
                .hasNext();) {
              SVNLogEntryPath entryPath = (SVNLogEntryPath)logEntry.getChangedPaths()
                  .get(changedPaths.next());
              String path = entryPath.getPath();
              String fileName = path.substring(path.indexOf("/"+tConfig.getPathBreaker()));
              svnChangeList.add(fileName);
            }
          }
        } catch (Exception e) {
          System.err.println("Exception With jira :: " + jiraId + " :: Exception :" + e);
        }
      });
    } catch (Exception e) {
      System.err.println("Exception With jira :: " + jiraId + " :: Exception :" + e);
    }
    return svnChangeList;
  }

  @SuppressWarnings({ "deprecation", "unchecked" })
  private Collection<SVNLogEntry> getLogEntries(final TemplateConfiguration tConfig)
      throws IOException, SVNException {
    SVNRepository repository = null;
    try {
      SVNProperties svnProps = tConfig.getSvnProps();
      SVNURL url = SVNURL.parseURIDecoded(svnProps.getUrl());
      repository = SVNRepositoryFactory.create(url, null);
      ISVNAuthenticationManager myAuthManager = SVNWCUtil
          .createDefaultAuthenticationManager(svnProps.getUsername(), svnProps.getPassword());
      repository.setAuthenticationManager(myAuthManager);
      long startRevision = 0;
      long endRevision = repository.getLatestRevision();
      return repository.log(new String[] { "" }, null, startRevision, endRevision, true, true);
    } finally {
      if (repository != null) {
        repository.closeSession();
      }
    }
  }  
  

}
