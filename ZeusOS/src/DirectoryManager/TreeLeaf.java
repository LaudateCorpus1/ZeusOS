package DirectoryManager;

import javafx.scene.control.TreeItem;

public class TreeLeaf extends TreeItem<String> {
  private File filChild;
  private Directory dirChild;
  private String type;
  public TreeLeaf(String name) {
    super(name);
  }

  public TreeLeaf(File file) {
    super(file.getName());
    filChild = file;
    type = "FILE";
  }

  public TreeLeaf(Directory dir) {
    super(dir.getName());
    dirChild = dir;
    type = "DIRECTORY";
  }

  public File getFilChild() {
    return filChild;
  }

  public Directory getDirChild() {
    return dirChild;
  }

  public String getType() {
    return type;
  }
}