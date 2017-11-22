package ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog;

/**
 * An exception to be thrown if there is a conflict between a {@link ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Person}'s
 * already-submitted hours and a new submission attempt in the {@link ProjectHourLog}.
 *
 * @author Mark Zeagler
 * @version 1.0
 */
public class InvalidSubmissionException extends Exception {

	public InvalidSubmissionException( String s ) {
		super( s );
	}
}
