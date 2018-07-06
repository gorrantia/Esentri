package ch.rhb.integra.cleanup;

import java.io.File;
import java.io.FileFilter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * This Apache Camel Processor implementation deletes files older than a specified time from the
 * archive location.
 * 
 * @author esentri AG, <a href="mailto:markus.lohn@esentri.com">mlohn</a>
 */
@Slf4j
public class FilesCleanupProcessor implements Processor {

  @Override
  public void process(Exchange exchange) throws Exception {
    String archiveDirectory = exchange.getProperty("archiveDirectory", String.class);
    Integer retentionPeriod = exchange.getProperty("retentionPeriod", Integer.class);

    if (archiveDirectory != null) {
      log.debug("Cleanup resources in directory {} oder than {} days.", archiveDirectory,
          retentionPeriod);
      File dir = new File(archiveDirectory);
      File[] files = dir.listFiles(new FileAgeFilter(retentionPeriod));
      log.trace("Found {} files to cleanup.", files.length);
      for (int i = 0; i < files.length; i++) {
        log.trace("Deletes {} from file system.", files[i].getAbsolutePath());
        delete(files[i]);
      }
    } else {
      log.debug("archiveDirectory was null. Nothing to cleanup now.");
    }

  }

  private boolean delete(File file) {
    if (file.isDirectory()) {
      File[] files = file.listFiles();
      for (int i = 0; i < files.length; i++) {
        if (files[i].isDirectory()) {
          delete(files[i]);
        } else {
          log.trace("Delete file {}.", files[i].getAbsolutePath());
          files[i].delete();
        }
      }
    }
    log.trace("Delete file {}.", file.getAbsolutePath());
    return file.delete();
  }

  public class FileAgeFilter implements FileFilter {

    private Integer retentionPeriod = null;

    /**
     * Creates a new FileAgeFilter.
     * @param retentionPeriod the retention period in days
     */
    public FileAgeFilter(Integer retentionPeriod) {
      if (retentionPeriod == null) {
        this.retentionPeriod = Integer.valueOf(5);
        log.debug("Use default value for retention period {}.", retentionPeriod);
      } else {
        this.retentionPeriod = retentionPeriod;
      }

    }

    @Override
    public boolean accept(File pathname) {
      if (pathname.isHidden()) {
        return false;
      } else {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastModifyDate = LocalDateTime
            .ofInstant(Instant.ofEpochMilli(pathname.lastModified()), ZoneId.systemDefault());

        if (lastModifyDate != null
            && lastModifyDate.isBefore(now.minusDays(retentionPeriod.longValue()))
            && pathname.getName().matches("([0-9]{4}.*)|(.*\\.(json|xml|txt))")) {
          return true;
        } else {
          return false;
        }
      }
    }
  }

}
