package DirectoryManager;

import Desktop.Desktop;
import Icon.Icon;
import InternalWindow.InternalWindow;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class DirectoryManager extends InternalWindow {
    private Desktop parent;
    // private Directory currentWorkingDirectory;
    private String type;
    private Directory root;

    @FXML
    BorderPane skeleton;
    @FXML
    BorderPane topBar;
    @FXML
    GridPane buttonBar;
    @FXML
    Button closeButton;
    @FXML
    Button minButton;
    @FXML
    MenuBar menuBar;
    // Each of the menus can be added manually or injected
    @FXML
    TreeView<String> treeView;
    @FXML
    TreeView<String> listView;

    public DirectoryManager(String type) {
        super("/DirectoryManager/DirectoryManager.fxml");
        this.type = type;
    }

    public DirectoryManager(Desktop parent, String type) {
        super("/DirectoryManager/DirectoryManager.fxml");
        this.parent = parent;
        this.type = type;

        initializeDefaults();
        populateTreeView();
        setTreeViewControls();
    }

    @Override
    public void initialize() {
        super.defineDraggableElements(topBar, buttonBar);
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
    }

    public void hide() {
    }

    // Tree View Logic Begins Here

    /*
     * Will show all directories in sidebar, not files.
     */
    public void populateTreeView() {
        treeView.setRoot(this.root.getMyTree(true, false));
    }

    public void setTreeViewControls() {
        treeView.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if ((TreeLeaf) newValue != null && ((TreeLeaf) newValue).getType().equalsIgnoreCase("DIRECTORY")) {
                // TreeLeaf has a directory
                showInListView(((TreeLeaf) newValue).getDirChild());
            } else if ((TreeLeaf) newValue != null && ((TreeLeaf) newValue).getType().equalsIgnoreCase("FILE")) {
                // This method is deprecated for file viewing
                // showInListView(((TreeLeaf) newValue).getDirChild());
            }
        });
    }

    public void initializeDefaults() {
        // Taking in account the Type of DirectoryManager, Initialize
        // The Root Directory and Following Directories
        if (type.equalsIgnoreCase("DEFAULT")) {
            this.root = new Directory("Root", parent);
            Directory desktop = new Directory("Desktop", this.root, parent);
            File child1 = new File(
                    "Notepad", desktop, new Icon("Notepad",
                            "https://cdn0.iconfinder.com/data/icons/jfk/512/chrome-512.png", "NOTEPAD", parent),
                    "Notepad File");
            desktop.addFilChild(child1);
            this.root.addDirChild(desktop);

            Directory test = new Directory("oop", this.root, parent);
            Directory test1 = new Directory("oop2", this.root, parent);
            Directory testa = new Directory("oops", test, parent);
            Directory testab = new Directory("oopsie", testa, parent);
            this.root.addDirChild(test);
            this.root.addDirChild(test1);
            test.addDirChild(testa);
            testa.addDirChild(testab);
            //Need to create a way to maintain unique File names/DirectoryNames

            // currentWorkingDirectory = this.root;
        } else {
            System.out.println("A directory manager with an unspecified type was used.");
        }

    }

    // List View Logic Begins Here
    public void showInListView(Directory dir) {
        // Given a directory dir, show all contents of dir, both
        // Directories and Files, in the instance variable (TreeLeaf) listview
        listView.setRoot(dir.getMyTree(false, true));
        setListViewControls();
    }

    public void setListViewControls() {
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    TreeLeaf item = (TreeLeaf) listView.getSelectionModel().getSelectedItem();
                    System.out.println(item.getType());
                    // ((TreeLeaf) newValue).getFilChild().performAction();
                }
            }
        });
    }
}