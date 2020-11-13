package InternalWindow;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

public class InternalWindow extends Pane {

  private Node[] draggableNodes;
  private FXMLLoader fxmlLoader;

  public InternalWindow() {
    this.fxmlInitialize("/InternalWindow/InternalWindow.fxml");
  }

  public InternalWindow(String path) {
    this.fxmlInitialize(path);
  }

  public void fxmlInitialize(String path) {
    fxmlLoader = new FXMLLoader(getClass().getResource(path));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
    } catch (Exception exception) {
      exception.printStackTrace();
      System.out.println("Error in loading FXML. (InternalWindow Parent)");
    }
  }

  @FXML
  public void initialize() {
    // System.out.println("Internal Window Parent Initializing...");
  }

  public void defineDraggableElements(Node... nodes) {
    draggableNodes = nodes;
  }

  public void makeFocusable() {
    this.setOnMouseClicked(e -> {
      toFront();
    });
  }

  private double VectorX, VectorY;

  public void makeDraggable() {
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

  public void disableDrag() {
    for (Node node : draggableNodes) {
      node.setOnMousePressed(e -> {
      });
      node.setOnMouseDragged(e -> {
      });
    }
  }

  private boolean RESIZE_BOTTOM;
  private boolean RESIZE_RIGHT;
  private boolean RESIZE_LEFT;
  private boolean RESIZE_TOP;
  private double borderWidth;

  public void makeResizable(double borderWidth) {
    this.setOnMouseReleased(evt -> {
      this.makeDraggable();
      this.makeDraggable();
      this.resetResizable();
      this.makeResizable(this.borderWidth);
    });
    this.borderWidth = borderWidth;
    this.setOnMouseReleased(evt -> {
      this.makeDraggable();
      this.makeDraggable();
      this.resetResizable();
      this.makeResizable(this.borderWidth);
    });
    this.setOnMouseMoved(e -> {
      double mouseX = e.getX();
      double mouseY = e.getY();
      // window size
      double width = this.boundsInLocalProperty().get().getWidth();
      double height = this.boundsInLocalProperty().get().getHeight();
      RESIZE_BOTTOM = false;
      RESIZE_RIGHT = false;
      RESIZE_TOP = false;
      RESIZE_LEFT = false;
      // if we on the edge, change state and cursor
      if (Math.abs(mouseX - width) < borderWidth && Math.abs(mouseY - height) < borderWidth) {
        // Bottom Right
        RESIZE_RIGHT = true;
        RESIZE_BOTTOM = true;
        this.setCursor(Cursor.NW_RESIZE);
      } else if (Math.abs(mouseX - 0) < borderWidth && Math.abs(mouseY - 0) < borderWidth) {
        // Top Left
        RESIZE_TOP = true;
        RESIZE_LEFT = true;
        this.setCursor(Cursor.NW_RESIZE);
      } else if (Math.abs(mouseX - width) < borderWidth && Math.abs(mouseY - 0) < borderWidth) {
        // Top Right
        RESIZE_TOP = true;
        RESIZE_RIGHT = true;
        this.setCursor(Cursor.NE_RESIZE);
      } else if (Math.abs(mouseX - 0) < borderWidth && Math.abs(mouseY - height) < borderWidth) {
        // Bottom Left
        RESIZE_BOTTOM = true;
        RESIZE_LEFT = true;
        this.setCursor(Cursor.NE_RESIZE);
      } else if (Math.abs(mouseX - 0) < borderWidth && mouseY > borderWidth && mouseY < height - borderWidth) {
        // Left
        RESIZE_LEFT = true;
        this.setCursor(Cursor.W_RESIZE);
      } else if (Math.abs(mouseY - 0) < borderWidth && mouseX > borderWidth && mouseX < width - borderWidth) {
        // Top
        RESIZE_TOP = true;
        this.setCursor(Cursor.N_RESIZE);
      } else if (Math.abs(mouseX - width) < borderWidth && mouseY > borderWidth && mouseY < height - borderWidth) {
        // Right
        RESIZE_RIGHT = true;
        this.setCursor(Cursor.W_RESIZE);
      } else if (Math.abs(mouseY - height) < borderWidth && mouseX > borderWidth && mouseX < width - borderWidth) {
        // Bottom
        RESIZE_BOTTOM = true;
        this.setCursor(Cursor.N_RESIZE);
      } else {
        this.setCursor(Cursor.DEFAULT);
      }
    });

    this.setOnMousePressed(event -> {
      this.resetResizable();
      // System.out.println("TOP:" + RESIZE_TOP + ", LEFT:" + RESIZE_LEFT + ",
      // BOTTOM:" + RESIZE_BOTTOM + ", RIGHT:" + RESIZE_RIGHT);
      if (RESIZE_TOP && RESIZE_LEFT && !RESIZE_BOTTOM && !RESIZE_RIGHT) {
        // Top Left
        this.disableDrag();
        this.disableDrag();
        Region region = (Region) getChildren().get(0);
        VectorX = this.getLayoutX() - event.getScreenX();
        VectorY = this.getLayoutY() - event.getScreenY();
        double initX = event.getScreenX();
        double initY = event.getScreenY();
        double initW = this.getWidth();
        double initH = this.getHeight();
        this.setOnMouseDragged(e -> {
          this.setLayoutX(e.getScreenX() + VectorX);
          this.setLayoutY(e.getScreenY() + VectorY);
          region.setMinSize(initX - e.getScreenX() + initW, initY - e.getScreenY() + initH);
          this.setOnMouseReleased(evt -> {
            this.makeDraggable();
            this.makeDraggable();
            this.resetResizable();
            this.makeResizable(this.borderWidth);
          });
        });
      } else if (RESIZE_TOP && RESIZE_RIGHT && !RESIZE_BOTTOM && !RESIZE_LEFT) {
        // Top Right
        this.disableDrag();
        this.disableDrag();
        Region region = (Region) getChildren().get(0);
        double initX = event.getScreenX();
        double initY = event.getScreenY();
        double initW = this.getWidth();
        double initH = this.getHeight();
        double layoutY = this.getLayoutY();
        this.setOnMouseDragged(e -> {
          region.setMinSize(e.getScreenX() - initX + initW, -e.getScreenY() + initY + initH);
          this.setLayoutY(layoutY + e.getScreenY() - initY);
          this.setOnMouseReleased(evt -> {
            this.makeDraggable();
            this.makeDraggable();
            this.resetResizable();
            this.makeResizable(this.borderWidth);
          });
        });
      } else if (RESIZE_TOP && !RESIZE_BOTTOM && !RESIZE_RIGHT && !RESIZE_LEFT) {
        // Top Only
        this.disableDrag();
        this.disableDrag();
        Region region = (Region) getChildren().get(0);
        double initY = event.getScreenY();
        double initW = this.getWidth();
        double initH = this.getHeight();
        double layoutY = this.getLayoutY();
        this.setOnMouseDragged(e -> {
          region.setMinSize(initW, -e.getScreenY() + initY + initH);
          this.setLayoutY(layoutY + e.getScreenY() - initY);
          this.setOnMouseReleased(evt -> {
            this.makeDraggable();
            this.makeDraggable();
            this.resetResizable();
            this.makeResizable(this.borderWidth);
          });
        });
      } else if (RESIZE_BOTTOM && RESIZE_LEFT && !RESIZE_TOP && !RESIZE_RIGHT) {
        // Bottom Left
        Region region = (Region) getChildren().get(0);
        double initX = event.getScreenX();
        double initY = event.getScreenY();
        double initW = this.getWidth();
        double initH = this.getHeight();
        double layoutX = this.getLayoutX();
        this.setOnMouseDragged(e -> {
          region.setMinSize(-e.getScreenX() + initX + initW, e.getScreenY() - initY + initH);
          this.setLayoutX(layoutX + e.getScreenX() - initX);
          this.setOnMouseReleased(evt -> {
            this.resetResizable();
            this.makeResizable(this.borderWidth);
          });
        });
      } else if (RESIZE_BOTTOM && RESIZE_RIGHT && !RESIZE_TOP && !RESIZE_LEFT) {
        // Bottom Right
        Region region = (Region) getChildren().get(0);
        double initX = event.getScreenX();
        double initY = event.getScreenY();
        double initW = this.getWidth();
        double initH = this.getHeight();
        this.setOnMouseDragged(e -> {
          region.setMinSize(e.getScreenX() - initX + initW, e.getScreenY() - initY + initH);
          this.setOnMouseReleased(evt -> {
            this.resetResizable();
            this.makeResizable(this.borderWidth);
          });
        });
      } else if (RESIZE_LEFT && !RESIZE_BOTTOM && !RESIZE_RIGHT && !RESIZE_TOP) {
        // Left only
        this.disableDrag();
        this.disableDrag();
        Region region = (Region) getChildren().get(0);
        VectorX = this.getLayoutX() - event.getScreenX();
        double initX = event.getScreenX();
        double initW = this.getWidth();
        double initH = this.getHeight();
        this.setOnMouseDragged(e -> {
          this.setLayoutX(e.getScreenX() + VectorX);
          region.setMinSize(initX - e.getScreenX() + initW, initH);
          this.setOnMouseReleased(evt -> {
            this.makeDraggable();
            this.makeDraggable();
            this.resetResizable();
            this.makeResizable(this.borderWidth);
          });
        });
      } else if (RESIZE_RIGHT && !RESIZE_BOTTOM && !RESIZE_LEFT && !RESIZE_TOP) {
        // Right Only
        this.disableDrag();
        this.disableDrag();
        Region region = (Region) getChildren().get(0);
        double initX = event.getScreenX();
        double initW = this.getWidth();
        double initH = this.getHeight();
        this.setOnMouseDragged(e -> {
          region.setMinSize(e.getScreenX() - initX + initW, initH);
          this.setOnMouseReleased(evt -> {
            this.makeDraggable();
            this.makeDraggable();
            this.resetResizable();
            this.makeResizable(this.borderWidth);
          });
        });
      } else if (RESIZE_BOTTOM && !RESIZE_TOP && !RESIZE_RIGHT && !RESIZE_LEFT) {
        // Bottom Only
        this.disableDrag();
        this.disableDrag();
        Region region = (Region) getChildren().get(0);
        double initY = event.getScreenY();
        double initW = this.getWidth();
        double initH = this.getHeight();
        this.setOnMouseDragged(e -> {
          region.setMinSize(initW, e.getScreenY() - initY + initH);
          this.setOnMouseReleased(evt -> {
            this.makeDraggable();
            this.makeDraggable();
            this.resetResizable();
            this.makeResizable(this.borderWidth);
          });
        });
      } else {
        this.resetResizable();
        this.makeResizable(this.borderWidth);
      }
    });
  }

  public void resetResizable() {
    this.setOnMousePressed(e -> {
    });
    this.setOnMouseDragged(e -> {
    });
    this.setOnMouseReleased(e -> {
    });
  }
}
