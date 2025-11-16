import java.util.*;

public class Main {
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		FileManager fm = FileManager.getInstance();

		AllGradesDisplayUI allGradesUI = new AllGradesDisplayUI();
		AvgDisplayUI averageUI = new AvgDisplayUI();

		fm.subscribe(allGradesUI);
		fm.subscribe(averageUI);

		while (true) {
		    System.out.println("\n--- Grade Manager ---");
		    System.out.println("1. Run Single-Threaded Mode");
		    System.out.println("2. Run Multi-Threaded Mode");
		    System.out.println("3. Quit");
		    System.out.print("Enter choice: ");

		    int choice = sc.nextInt();
		    sc.nextLine();

		    switch (choice) {
			    case 1:
				runSingleThreaded(fm, sc);
				break;
			    case 2:
			   	runMultiThreaded(fm);
				break;
			    case 3:
			    	System.out.println("Exiting...");
			    	sc.close();
			    	return;
			
		    	    default:
				System.out.println("Invalid choice. Try again.");
		    }
		}
	}

    // ---------- SINGLE-THREADED ----------
    private static void runSingleThreaded(FileManager fm, Scanner sc) {
        System.out.println("\n--- SINGLE THREADED MODE ---");

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Add grade");
            System.out.println("2. Delete all grades");
            System.out.println("3. Get first grade");
            System.out.println("4. Show all grades");
            System.out.println("5. Return to main menu");
            System.out.print("Enter choice: ");

            int option = sc.nextInt();
	    sc.nextLine();
           
            switch (option) {
		    case 1:
			System.out.print("Enter grade: ");
			try {
				int g = Integer.parseInt(sc.nextLine());
				fm.addGrade(g);
			} catch (Exception e) {
				System.out.println("Invalid grade.");
			    }
			break;

		    case 2:
	    		fm.deleteAll();
			break;

		    case 3: 
                    	Integer first = fm.getFirstGrade();
                    		if (first != null)
                       			System.out.println("First grade: " + first);
				break;
	    	    case 4: 
		    	System.out.println("All grades: " + fm.getAllGrades());
			break;
		    
		    case 5:
			return; 
		    
		    default:
		    	System.out.println("Invalid option.");
            }
	}
    }

    // ---------- MULTI-THREADED ----------
    private static void runMultiThreaded(FileManager fm) {
	    System.out.println("\n--- MULTI-THREADED MODE (Stress Tests) ---");
	    fm.deleteAll();

	    System.out.println("--- Starting Test 1: Concurrent Additions ---");
	    System.out.println("\n5 threads will each add 3 grades concurrently...");

	    Thread[] threads = new Thread[5];
	    Random rand = new Random();

	    for (int i = 0; i < 5; i++) {
		threads[i] = new Thread() {
		    @Override
		    public void run() {
			for (int j = 1; j <= 3; j++) {
			    int grade = rand.nextInt(50) + 50; 
			    synchronized (System.out) { 
				System.out.println("Adding grade: " + grade);
			    }
			    fm.addGrade(grade);
			    try {
				Thread.sleep(50); 
			    } catch(InterruptedException ignored) {}
			}
		    }
		};
		threads[i].start();
	    }

	    for(int i = 0; i < threads.length; i++) {
		try {
		    threads[i].join();
		} catch(InterruptedException ignored) {}
	    }
	    
	    System.out.println("\nConcurrent additions complete. Final grades count: " + fm.getAllGrades().size());
	    
	    // --- Test 2: Concurrent Delete + Add ---
	    System.out.println("\nConcurrent Delete + Add ---");
	    
	    // Thread 1: Continuous additions
	    Thread adder = new Thread() {
		@Override
		public void run() {
		    for (int i = 0; i < 10; i++) {
			int grade = 100 + i; 
			synchronized (System.out) {
			    System.out.println("Adding grade: " + grade);
			}
			fm.addGrade(grade);
			try {
			    Thread.sleep(30); 
			} catch(InterruptedException ignored) {}
		    }
		}
	    };

	    
	    Thread deleter = new Thread() {
		@Override
		public void run() {
		    try {
			Thread.sleep(150); 
		    } catch(InterruptedException ignored) {}
		    
		    synchronized (System.out) {
			System.out.println("Calling deleteAll()...");
		    }
		    fm.deleteAll();
		    
		    try {
			Thread.sleep(150);
		    } catch(InterruptedException ignored) {}
		    
		    synchronized (System.out) {
			System.out.println("Calling deleteAll() again...");
		    }
		    fm.deleteAll();
		}
	    };

	    adder.start();
	    deleter.start();
	    
	    try {
		adder.join();
		deleter.join();
	    } catch(InterruptedException ignored) {}

	    System.out.println("\nConcurrent Delete + Add run complete");
	    System.out.println("Final grades: " + fm.getAllGrades());
	    System.out.println("----------------------------------------------------\n");
	}
			
	}
