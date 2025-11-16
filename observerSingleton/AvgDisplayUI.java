import java.util.*;

public class AvgDisplayUI implements Observer {
	
	public void update(List<Integer> grades) {
		if(grades.size() == 0) {
			System.out.println("No grades, Avg -> N/A");
			return;
		}
	
		int total = 0;
		for(int i = 0; i < grades.size(); i++) {
			total += grades.get(i);
		}

		double avg = (double) total / grades.size();
		System.out.printf("Avg: %.2f ", avg);
	}
}
