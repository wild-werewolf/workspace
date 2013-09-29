package ru.murzoid.bookdownload.server.util;

import java.io.File;

public class FilesHelper {

  
  public static boolean deleteFileAndDir(File dir) {
      if (dir.isDirectory()) {
          String[] children = dir.list();
          for (int i=0; i<children.length; i++) {
              boolean success = deleteFileAndDir(new File(dir, children[i]));
              if (!success) {
                  return false;
              }
          }
      }
      return dir.delete();
  }
}
