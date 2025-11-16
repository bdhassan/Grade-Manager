import java.util.*;

public class DummyObserver implements Observer { 
	
	public void update(List<Integer> grades) {
		synchronized(System.out) {
			System.out.println("Dummy Observer triggered, grades count: " + grades.size());
		}
	}
}

