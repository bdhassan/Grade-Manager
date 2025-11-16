import java.util.*;
import java.io.*;

public class FileManager {
	
	private static FileManager instance = null;
	private final List<Observer> observers = new ArrayList<Observer>();
	private static final Object fileLock = new Object();

	private FileManager() {
		try {
			File f = new File("grades.txt");
			if(!f.exists()) {
				f.createNewFile();
				System.out.println("File Manager created new file -> grades.txt");
			} else {
				System.out.println("FileManager using existing file -> grades.txt");
			}
		} catch (IOException e) {
			System.out.println("Error accessing files");
		}
	}

	public static FileManager getInstance() {
		if(instance == null){
			synchronized(fileLock) {
				if(instance == null){
					instance = new FileManager();
				}
			}
		}
		return instance;
	}

	public void subscribe(Observer o) {
		if (o != null) {
			synchronized(fileLock){
				if (!observers.contains(o)){
					observers.add(o);
				
				}
			}
		}
	}

	private void notifyObservers() {
		ArrayList<Integer> gradesSnap = getAllGrades();
		List<Observer> copy;

		synchronized(fileLock) {
			copy = new ArrayList<>(observers);		
		}

		for(int i = 0; i < copy.size(); i++) {
			Observer obs = copy.get(i);
			ObserverThread t = new ObserverThread(obs, gradesSnap);
			t.start();
		}
	}

	public void addGrade(int grade) {
		synchronized(fileLock) {
			try(BufferedWriter bw = new BufferedWriter(new FileWriter("grades.txt", true))){
				bw.write(String.valueOf(grade));
				bw.newLine();
			} catch (IOException e) {
				System.out.println("Error adding grade");
			}
		}
		notifyObservers();
	}

	public Integer getFirstGrade() {
		synchronized(fileLock) {
			try (BufferedReader br = new BufferedReader(new FileReader("grades.txt"))) {
				String line = br.readLine();

				if (line != null) {
					return Integer.parseInt(line.trim());
				} else {
					System.out.println("No grades found");
					return null;
				}
			} catch (IOException e) {
				System.out.println("Error reading file");
				return null;
			}
		}
        }
        
	

	public ArrayList<Integer> getAllGrades() {
		ArrayList<Integer> grades = new ArrayList<Integer>();
		synchronized (fileLock) {
			try (BufferedReader br = new BufferedReader(new FileReader("grades.txt"))) {
				String line;
				while((line = br.readLine())  != null) {
					line = line.trim();
					if(!line.isEmpty()) {
						grades.add(Integer.parseInt(line));
					}
				}
			} catch (IOException e) {
				System.out.println("Error reading file");
			}
		}
		return grades;
	}

	public void deleteAll() {
		synchronized(fileLock){

			try(BufferedWriter bw = new BufferedWriter(new FileWriter("grades.txt", false))) {	
				
			} catch (IOException e) {
				System.out.println("Error deleting all grades");
			}
				
		}
		System.out.println("All grades deleted from file");
		notifyObservers();
	}
}	
