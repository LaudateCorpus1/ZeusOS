package MemoryManager;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Properties;

import Notepad.Notepad;

public class NotepadManager {

  public void save(Notepad node) {
    Properties prop = new Properties();
    try {
      // Handle File Creation
      URI uri = getClass().getResource("/Notepad/savedInfo" + 0 + ".xml").toURI();
      File file = new File(uri);
      // Handle Properties
      ArrayList<String> capture = node.getInfo();
      prop.setProperty("Text", capture.get(0));
      prop.setProperty("LayoutX", capture.get(1));
      prop.setProperty("LayoutY", capture.get(2));
      prop.setProperty("Width", capture.get(3));
      prop.setProperty("Height", capture.get(4));

      prop.storeToXML(new FileOutputStream(file), "Values", "UTF-8");
    } catch (Exception e) {
      e.printStackTrace();
      // System.out.println("Error while Saving");
    }
  }
}