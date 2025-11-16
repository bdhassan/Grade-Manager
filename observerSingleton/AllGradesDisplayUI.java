import java.util.*;

public class AllGradesDisplayUI implements Observer {

	public void update(List<Integer> grades) {
		if(grades.isEmpty()) {
			synchronized(System.out) {
				System.out.println("No grades");
			}
			return;
		}


		System.out.println("Grades: ");
		for(int i = 0; i < grades.size(); i++) {
			System.out.print(grades.get(i));
			if (i < grades.size() - 1) {
				System.out.print(" ");
			}
		}
		System.out.println();
	}
}
