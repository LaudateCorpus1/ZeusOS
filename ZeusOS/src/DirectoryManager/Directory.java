package DirectoryManager;

import java.util.*;

import Desktop.Desktop;

public class Directory {
    Directory parent;
    private String name;
    ArrayList<Directory> dirChildren;
    ArrayList<File> filChildren;
    Desktop gui;
    private TreeLeaf myTree;

    public Directory(String name, Desktop gui) {
        this.name = name;
        parent = null;
        dirChildren = new ArrayList<Directory>();
        filChildren = new ArrayList<File>();
        this.gui = gui;
    }

    public Directory(String name, Directory parent, Desktop gui) {
        this.name = name;
        this.parent = parent;
        dirChildren = new ArrayList<Directory>();
        filChildren = new ArrayList<File>();
        this.gui = gui;
    }

    public String getName() {
        return name;
    }

    public Directory getParent() {
        return parent;
    }

    public String getParentName() {
        if (parent == null) {
            return "null";
        }
        return parent.getName();
    }

    public Desktop getGui() {
        return gui;
    }

    public void setDirChildren(ArrayList<Directory> children) {
        dirChildren = children;
    }

    public void setFilChildren(ArrayList<File> children) {
        filChildren = children;
    }

    public void addDirChild(Directory child) {
        dirChildren.add(child);
    }

    public void addFilChild(File child) {
        filChildren.add(child);
    }

    public ArrayList<Directory> getDirChildren() {
        return dirChildren;
    }

    public ArrayList<File> getFilChildren() {
        return filChildren;
    }

    public Directory getDirChild(String name) {
        String child = name;
        for (int i = 0; i < dirChildren.size(); i++) {
            if (dirChildren.get(i).getName().equals(child)) {
                return dirChildren.get(i);
            }
        }
        return null;
    }

    public File getFilChild(String name) {
        String child = name;
        for (int i = 0; i < filChildren.size(); i++) {
            if (filChildren.get(i).getName().equals(child)) {
                return filChildren.get(i);
            }
        }
        return null;
    }

    /**
     * Helper Function which generates a TreeItem with the contents of a given
     * directory. **Can possibly extend by choosing if child directories display
     * files or Directories exclusively.
     * 
     * @param includeDirs  choose whether or not to include Directories
     * @param includeFiles choose whether or not to include Files
     * @return A TreeLeaf which extends a TreeView
     */
    public TreeLeaf getMyTree(boolean includeDirs, boolean includeFiles) {
        myTree = new TreeLeaf(this);
        myTree.setExpanded(true);
        if (includeDirs) {
            for (Directory dir : this.getDirChildren()) {
                myTree.getChildren().add(dir.getMyTree(includeDirs, includeFiles));
            }
        }
        if (includeFiles) {
            for (File child : this.getFilChildren()) {
                myTree.getChildren().add(new TreeLeaf(child));
            }
        }
        return myTree;
    }

    public ArrayList<String> getChildArr() {
        ArrayList<String> arr = new ArrayList<String>();
        for (Directory child : this.getDirChildren()) {
            arr.add(child.getName());
        }
        for (File child : this.getFilChildren()) {
            arr.add(child.getName());
        }
        return arr;
    }
}