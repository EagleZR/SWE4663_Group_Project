package ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements;

/**
 * An enumeration of the different statuses a {@link Requirement} may possess.
 *
 * @author Mark Zeagler
 * @version 1.0
 */
public enum Status {

	ACCEPTED_CURRENT( "Accepted (Current release)" ),
	ACCEPTED_POST_RELEASE( "Accepted (Later release)" ),
	REJECTED( "Rejected" );

	String message;

	Status( String message ) {
		this.message = message;
	}

	@Override public String toString() {
		return this.message;
	}
}
