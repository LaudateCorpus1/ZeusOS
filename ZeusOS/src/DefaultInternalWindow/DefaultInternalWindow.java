package DefaultInternalWindow;

import InternalWindow.InternalWindow;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class DefaultInternalWindow extends InternalWindow {
  @FXML
  Label label;
  @FXML
  BorderPane titleBar;
  @FXML
  Button closeButton;
  @FXML
  ImageView imageView;
  @FXML
  BorderPane windowPane;

  public DefaultInternalWindow() {
    super("/DefaultInternalWindow/DefaultInternalWindow.fxml");
  }

  @Override
  public void initialize() {
    super.defineDraggableElements(titleBar, label);
    super.makeDraggable();
    super.makeResizable(10);
    super.makeFocusable();
  }

  public void set(String imagepath) {
    imageView.setImage(new Image(imagepath));
  }

}