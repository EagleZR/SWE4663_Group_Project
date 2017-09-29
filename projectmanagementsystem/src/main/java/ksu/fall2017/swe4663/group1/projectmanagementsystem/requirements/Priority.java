package ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements;

public enum Priority {

	CRITICAL(3),
	HIGH(2),
	MEDIUM(1),
	LOW(0);

	private final int weight;

	Priority(int weight){
		this.weight = weight;
	}

	public int getWeight() {
		return weight;
	}
}
