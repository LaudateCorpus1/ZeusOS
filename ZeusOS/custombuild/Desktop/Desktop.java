package Desktop;

import java.util.ArrayList;

import Notepad.Notepad;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

public class Desktop extends Pane {

  @FXML
  Button spawnButton;
  @FXML
  Button showAll;
  @FXML
  SplitMenuButton loadMenu;
  @FXML
  ToggleGroup load;

  private ArrayList<Node> visibleWindows;
  private ArrayList<Node> hiddenWindows;
  private ArrayList<Node> closedWindows;

  public Desktop() {
    visibleWindows = new ArrayList<Node>();
    hiddenWindows = new ArrayList<Node>();
    closedWindows = new ArrayList<Node>();

    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Desktop/Desktop.fxml"));

    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
    } catch (Exception exception) {
      exception.printStackTrace();
      System.out.println("Error in loading FXML. (Desktop)");
    }
  }

  @FXML
  public void initialize() {
    saveMe();
  }


  //Accessor Functions
  public ArrayList<Node> getVisibleWindows() {return visibleWindows;}
  public ArrayList<Node> getHiddenWindows() {return hiddenWindows;}
  public ArrayList<Node> getClosedWindows() {return closedWindows;}

  
  public void saveMe() {
    // Saves the state of the current Desktop
  }

  public void spawn() {
    // Attached to spawnButton
    addChild(new Notepad(this));
  }

  public void showAllHidden() {
    // Attached to showAll Button
    for (int i = hiddenWindows.size() - 1; i >= 0; i--) {
      this.addChild(hiddenWindows.get(i));
      hiddenWindows.remove(i);
    }
  }

  public void loadChild() {
    // Attached to loadMenu
    RadioMenuItem selectedButton = (RadioMenuItem) load.getSelectedToggle();
    String name = selectedButton.getText();
    int value = Integer.parseInt(name.substring(name.length() - 1));
    Notepad newChild = new Notepad(this);
    if (newChild.load(value)) {
      newChild.setSaveToIndex(value);
      addChild(newChild);
    }
  }

  public void addChild(Node window) {
    visibleWindows.add(window);
    this.getChildren().add(window);
  }

  public void hideChild(Node window) {
    this.getChildren().remove(window);
    visibleWindows.remove(window);
    hiddenWindows.add(window);
  }

  public void closeChild(Node window) {
    this.getChildren().remove(window);
    visibleWindows.remove(window);
    closedWindows.add(window);
    if (window instanceof Notepad) {
      ((Notepad) window).saveToNextAvailable();
    }
  }

}