import java.util.*;

public class ObserverThread extends Thread {

	private Observer observer;
	private List<Integer> grades;

	public ObserverThread(Observer observer, List<Integer> grades) {
		this.observer = observer;
		this.grades = new ArrayList<>(grades);
	}

	public void run() {
		observer.update(grades);
	}
}
