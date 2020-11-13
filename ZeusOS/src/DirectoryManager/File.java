package DirectoryManager;

import Desktop.Desktop;
import Icon.Icon;
import javafx.scene.Node;

public class File {
  String name;
  Directory parent;
  Desktop gui;
  String description;
  Node node;

  public File(String name, Directory parent, Node node, String description) {
    // Currently a file can either be a Notepad or an Icon
    this.name = name;
    this.parent = parent;
    this.node = node;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public Node getNode() {
    return node;
  }

  public String getDescription() {
    return description;
  }

  public void performAction() {
    // System.out.println("File's Action Method was called.:" +
    // this.node.getClass().getName());
    switch (this.node.getClass().getName()) {
      case "Icon.Icon":
        ((Icon) node).getDesktop().addChild(node);
        break;
      default:
        System.out.println("Message from File:");
        System.out.println("That class has no action yet.");
    }
  }
}