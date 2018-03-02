package com.westat.ctsu.util;

public enum FileExtensionEnum {

  HTML(".html"), TS(".ts"), CSS(".css"), JAVA(".java"), SQL(".sql");
  private String fileExtension;

  private FileExtensionEnum(String fileExtension) {
    this.fileExtension = fileExtension;
  }

  public boolean isMatched(String extension) {
   return this.fileExtension.equals(extension);
  }

  @Override
  public String toString() {
    return fileExtension;
  }

}
