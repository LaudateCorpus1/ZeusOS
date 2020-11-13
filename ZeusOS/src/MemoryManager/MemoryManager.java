package MemoryManager;
/* Overview - 8 / 17 / 2020
Every node on the screen is tied/stored in a File in the DirectoryManger,
and has a Directory != root. Desktop is a special instance of a Directory,
which both handles displaying all Files with Icons stored in them, while
also showing all Files with Apps stored in them with the instance (visible) 
**yet to implemented **. MemoryManager needs to be able to maintain a cohesive
understanding of all states of nodes, but does not need to do this constantly.
There must be a way to identify if objects are unique to one another.

Since all objects are tied to Files, Memory Manager needs only to maintain a seperate
file for each File. The File itself should be able to save and reload its special
form of information. Besides this information, Memory Manager must save the current
Settings implementation, and update this across files as needed. Also, the structure
of Directories also needs to be determined and maintained. In order to simplify
the process of memory management and display, I will write a display manager,
which talks to the desktop and maintains what is displayed or not. The original
app will run this display manager.

All info will be saved into the local folder called Database and will follow the structure:

Database
  - Settings
    - Settings File (which maintains settings per class
    needs to be applied everytime a new app is opened)
  - Directories
    - Directories File (which maintains the directory structure ONLY.
    It will be read and used to initialize the Directory manager.)
  - Files
    - Files File (which maintains the placement of all Files in each Directory
    when initialized each file MUST have a node attached to it, which will 
    read to something from the following folder. Files must know the following:
    Directory located in, Node tied to, If on Desktop, construct an Icon. 
    All files must have an icon tied to them, and no files can store an Icon.
    Icons are considered special instances of Apps, and must be maintained as so.)
  - Apps (Folder that contains information for specific instances of app classes)
    - Notepads Folder
      - All Notepad instances are saved here. Given that any notepad instance that
      is not unique is prevented from being created, it is not necessary to maintain
      uniqueness in this properties section. A Notepad must know it's display on
      screen, and should read from the Settings File to know its display look.

*/




public class MemoryManager {

  public MemoryManager() {}

  public void doStuff() {

  }
    
}