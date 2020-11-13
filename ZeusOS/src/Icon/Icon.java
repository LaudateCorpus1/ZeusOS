package Icon;

import Desktop.Desktop;
import Notepad.Notepad;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class Icon extends Pane {
  @FXML
  Label title;
  @FXML
  ImageView imageView;
  @FXML
  BorderPane skeleton;

  Desktop parent;
  FXMLLoader fxmlLoader;
  private String name;
  Image image;
  String childClass;

  public Icon() {
    parent = null;
    childClass = null;
    this.name = "Placeholder";
    this.image = new Image("https://upload.wikimedia.org/wikipedia/commons/8/87/Google_Chrome_icon_%282011%29.png");
    this.fxmlInitialize("/Icon/Icon.fxml");
  }

  public Icon(String title, String imageLink, String childClass, Desktop parent) {
    this.parent = parent;
    this.childClass = childClass;
    this.name = title;
    this.image = new Image(imageLink);
    this.fxmlInitialize("/Icon/Icon.fxml");
  }

  public Icon(String title, Image img, String childClass, Desktop parent) {
    this.parent = parent;
    this.childClass = childClass;
    name = title;
    this.image = img;
    this.fxmlInitialize("/Icon/Icon.fxml");
  }

  public void fxmlInitialize(String path) {
    fxmlLoader = new FXMLLoader(getClass().getResource(path));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
    } catch (Exception exception) {
      exception.printStackTrace();
      System.out.println("Error in loading FXML. (Icon)");
    }
  }

  @FXML
  public void initialize() {
    title.setText(name);
    imageView.setImage(this.image);
    setAction();
    makeDraggable();
  }

  public Desktop getDesktop() {
    return parent;
  }

  public void setAction() {
    this.setOnMouseClicked(e -> {
      if (e.getButton().equals(MouseButton.PRIMARY)) {
        if (e.getClickCount() == 2) {
          addCorrectChild();
        }
      }
    });
  }

  public void addCorrectChild() {
    if (childClass.equalsIgnoreCase("Notepad")) {
      parent.addChild(new Notepad(parent));
    } else {
      System.out.println("That child isn't specified.");
    }
  }

  private double VectorX, VectorY;

  public void makeDraggable() {
    Node[] draggableNodes = new Node[] { skeleton, imageView, title };
    for (Node node : draggableNodes) {
      node.setOnMousePressed(e -> {
        VectorX = getLayoutX() - e.getScreenX();
        VectorY = getLayoutY() - e.getScreenY();
        toFront();
      });
      node.setOnMouseDragged(e -> {
        setLayoutX(e.getScreenX() + VectorX);
        setLayoutY(e.getScreenY() + VectorY);
      });
    }
  }


}