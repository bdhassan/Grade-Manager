# Grade-Manager

Java applications to manage student grades with single-threaded and mulit-threaded modes, demonstrating file handling, thread safety, and the Observer pattern

**Features**

- Add, delete, and view grades
- Single-threaded mode for basic operations
- Multi-threaded mode to test concurrent additions and deletions
- Observers update automatically on grade changes
- Grades stored in grades.txt

**Components**

1) FileManager.java
    - Singleton managing graeds and notifying observers
3) Observer.java
    - Interface for observer classes
4) AllGradesDisplayUI.java
    - Shows all grades
5) AvgDisplayUI.java
    - Shows average grades
6) DummyObserver.java
    - Example observer
7) ObserverThread.java
    - Updates observers in seperate threads
8) Main.java
    - Entry point with menu interface
  
**Running the Application**

1) Compile all files:
    - javac *.java

2) Run the program
    - java Main

3) Use the menu to
    - Add grades
    - Delete all grades
    - View grades

**Multi-Threaded Mode**

Test 1:
  - Multiple threads add grades concurrently

Test 2:
  - Concurrent additions and deletions

Observers are updated asynchronously using threads

**Notes**

- Grades are stored in grades.txt (one grade per line)
- Thread-safe design with proper synchronization
- Easily extendable with new observers or features 







