package ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements;

/**
 * An enumeration for the different priority levels a {@link Requirement} can have.
 *
 * @author Mark Zeagler
 * @version 1.0
 */
public enum Priority {

	CRITICAL( 3 ), HIGH( 2 ), MEDIUM( 1 ), LOW( 0 );

	private final int weight;

	Priority( int weight ) {
		this.weight = weight;
	}

	public int getWeight() {
		return this.weight;
	}
}
