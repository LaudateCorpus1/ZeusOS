package Notepad;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

import Desktop.Desktop;
import InternalWindow.InternalWindow;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

public class Notepad extends InternalWindow {
  @FXML
  TextArea textArea;
  @FXML
  GridPane topBar;
  @FXML
  GridPane buttonBar;
  @FXML
  Button closeButton;
  @FXML
  Button minButton;
  @FXML
  Label title;
  @FXML
  BorderPane skeleton;

  private Desktop parent;
  private Properties prop;

  public Notepad() {
    super("/Notepad/Notepad.fxml");
  }

  public Notepad(Desktop parent) {
    super("/Notepad/Notepad.fxml");
    this.parent = parent;
  }

  @Override
  public void initialize() {
    super.defineDraggableElements(topBar, title, buttonBar);
    super.makeDraggable();
    super.makeResizable(10);
    super.makeFocusable();
    resolveMenuButtonsHover();
  }

  public void resolveMenuButtonsHover() {
    buttonBar.setOnMouseEntered(e -> {
      closeButton.setText("X");
      minButton.setText("-");
    });
    buttonBar.setOnMouseExited(e -> {
      closeButton.setText("");
      minButton.setText("");
    });
  }

  public void close() {
    // Attached to closeButton
    parent.closeChild(this);
  }

  public void hide() {
    // Attached to minButton
    parent.hideChild(this);
  }

  /**
   * Returns info in Notepad in the following order:
   * 
   * @return ArrayList<String> [textArea text, X, Y, Width, Height]
   */
  public ArrayList<String> getInfo() {
    ArrayList<String> objects = new ArrayList<String>();
    Collections.addAll(objects, new String[] { textArea.getText(), String.valueOf(getLayoutX()),
        String.valueOf(getLayoutY()), String.valueOf(getWidth()), String.valueOf(getHeight()) });
    return objects;
  }

  public void setSaveToIndex(int index) {
    // Assuming that conditions for savedInfoINDEX are satisfied
    // Set closeButton to save to Certain Index at savedInfoINDEX.xml
    closeButton.setOnAction(evt -> {
      // MemoryManager.save(this)
      parent.getChildren().remove(this);
      parent.getVisibleWindows().remove(this);
      parent.getClosedWindows().add(this);
    });
  }

  public void saveToNextAvailable() {
    // Will Notepad to next Available savedInfoINDEX slot
    // In the relative folder /Notepad/savedInfo
    int correctIndex = 0;
    File file = null;
    ArrayList<String> files = new ArrayList<String>();
    try {
      URI uri = getClass().getResource("/Notepad/").toURI();
      File dir = new File(uri);
      if (dir.isDirectory()) {
        files.addAll(Arrays.asList(dir.list()));
        Collections.sort(files);
      }
      boolean foundFile = false;
      for (String name : files) {
        // Assuming alphabetical order
        if (name.contains("savedInfo")) {
          correctIndex = Integer.parseInt(String.valueOf(name.charAt(9))) + 1;
          foundFile = true;
        }
      }
      String filename;
      if (!foundFile)
        filename = "savedInfo1.xml";
      else
        filename = "savedInfo" + correctIndex + ".xml";
      file = new File(dir, filename);
    } catch (Exception e) {
    }

    prop = new Properties();
    try {
      prop.setProperty("Text", textArea.getText());
      prop.setProperty("LayoutX", String.valueOf(getLayoutX()));
      prop.setProperty("LayoutY", String.valueOf(getLayoutY()));
      prop.setProperty("Width", String.valueOf(getWidth()));
      prop.setProperty("Height", String.valueOf(getHeight()));

      prop.storeToXML(new FileOutputStream(file), "Values", "UTF-8");
    } catch (Exception e) {
      e.printStackTrace();
      // System.out.println("Error while Saving");
    }
  }

  public boolean load(int index) {
    // Loads from file savedInfoINDEX
    // Returns false if file doesn't exist in /Notepad/
    URI uri = null;
    Properties prop = new Properties();
    try {
      uri = getClass().getResource("/Notepad/savedInfo" + index + ".xml").toURI();
    } catch (Exception e) {
    }
    if (uri == null) {
      System.out.println("A notepad save with that index doesn't exist.");
      return false;
    } else {
      try {
        prop.loadFromXML(new FileInputStream(new File(uri)));

        textArea.setText(prop.getProperty("Text"));
        this.setLayoutX(Double.parseDouble(prop.getProperty("LayoutX")));
        this.setLayoutY(Double.parseDouble(prop.getProperty("LayoutY")));
        Region region = (Region) getChildren().get(0);
        region.setMinSize(Double.parseDouble(prop.getProperty("Width")),
            Double.parseDouble(prop.getProperty("Height")));
      } catch (Exception e) {
      }
      return true;
    }
  }

}