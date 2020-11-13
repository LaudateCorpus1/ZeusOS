package FileManager;

import java.io.*;
import java.util.*;
import java.net.URI;

/* We assume that every folder in the database have files with names
1, 2, 3 (1-indexed), where every files first line is the "header"
to that file. Currently, every header only needs to save the name
of the file it represents.
*/

public class FileManager {
  File database;
  URI myPath;

  public FileManager(String database) {
    try {
      this.myPath = this.getClass().getResource("/FileManager/").toURI();
    } catch (Exception e) {
      System.out.println("File Manager folder not found in running folder.");
      // e.printStackTrace();
    }
    try {
      this.database = new File(this.getClass().getResource("/" + database + "/").toURI());
      if (!this.database.isDirectory()) {
        throw new Exception("Database is not directory.");
      }
    } catch (Exception e) {
      System.out.println("Database Folder not found");
      e.printStackTrace();
    }
  }

  public URI getMyPath() {
    return myPath;
  }

  public File getFile(String name) {
    return new File(database.getAbsolutePath() + "/" + name);
  }

  public List<String> getAllList(String name) {
    return Arrays.asList(getFile(name).list());
  }

  public List<String> getDirList(String x) {
    FilenameFilter filter = (fil, name) -> {
      return !name.contains(".");
    };
    return Arrays.asList(getFile(x).list(filter));
  }

  public List<String> getFilList(String x) {
    FilenameFilter filter = (fil, name) -> {
      return name.contains(".");
    };
    return Arrays.asList(getFile(x).list(filter));
  }

  public boolean createDir(String name) {
    return getFile(name).mkdir();
  }

  /**
   * Creates a file in the database.
   * 
   * @param name     Folder name
   * @param contents File Contents
   * @return If file was created or not
   */
  public boolean createFile(String name) {
    try {
      return getFile(name).createNewFile();
    } catch (Exception e) {
      return false;
    }
  }

  public boolean writeToFile(String name, boolean append, String contents) {
    FileWriter writer = null;
    try {
      writer = new FileWriter(getFile(name), append);
      writer.write(contents);
      writer.close();
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  /** Will find the next available index (an integer) given that all files are in the form
   * "<INT>.<SOMEENDING>" Requires that file names contain a period.
   * 
   * @param name Location of Directory.
   * @return 
   * @throws Exception
   */
  public int getNextAvailableIndex(String name) throws Exception {
    File dir = getFile(name);
    if (!dir.isDirectory() || !dir.exists()) {
      throw new Exception("Directory doesn't exist.");
    } else {
      int count = 0;
      for (String x : getFilList(name)) {
        int convert = -1;
        try {
          convert = Integer.parseInt(x.substring(0, x.indexOf(".")));
        } catch (Exception e) {
        }
        if (convert > count) {
          count = convert;
        }
      }
      return count + 1;
    }
  }

  /**
   * Returns a file that doesn't exist yet, for some directory.
   * 
   * @param create Choose whether to create the file while returning.
   */
  public File getNextAvailableFile(String name, boolean create) throws Exception {
    File dir = getFile(name);
    if (!dir.isDirectory() || !dir.exists()) {
      throw new Exception("Directory doesn't exist.");
    }
    int index = getNextAvailableIndex(name);
    File child = new File(dir.getAbsolutePath() + "/" + index + ".txt");
    if (create) {
      child.createNewFile();
    }
    return child;
  }

  /**
   * Will find the first file in the directory "Database/" + "name" with the header
   * corresponding to header and returns it.
   * 
   * @param name Name of Directory.
   * @param header Header to look for.
   * @return File or null if no file with that header was found.
   * @throws Exception if Directory doesn't exist
   */
  public File findByHeader(String name, String header) throws Exception {
    File dir = getFile(name);
    if (!dir.isDirectory() || !dir.exists()) {
      throw new Exception("Directory doesn't exist.");
    }
    for (String x : getFilList(name)) {
      File child = new File(dir.getAbsolutePath() + "/" + x);
      Scanner read = new Scanner(child);
      if (read.hasNextLine() && header.equalsIgnoreCase(read.nextLine())) {
        read.close();
        return child;
      }
      read.close();
    }
    return null; // Header doesn't exist in file or file is empty.
  }

  public void insertAtStart(String name, String contents) throws Exception {
    File fil = getFile(name);
    if (!fil.exists() || fil.isDirectory()) {
      throw new Exception("File doesn't exist");
    }
    String capture_contents = "";
    Scanner read = new Scanner(fil);
    while (read.hasNextLine()) {
      capture_contents += read.nextLine() + "\n";
    }
    read.close();
    writeToFile(name, false, contents);
    writeToFile(name, true, "\n" + capture_contents);
  }

}