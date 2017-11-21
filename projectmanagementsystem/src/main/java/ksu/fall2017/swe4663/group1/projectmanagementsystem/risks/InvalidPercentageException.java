package ksu.fall2017.swe4663.group1.projectmanagementsystem.risks;

/**
 * An exception to be thrown when the {@link Double#parseDouble(String)} method results in a number greater than 1.0,
 * indicating an invalid percentage.
 *
 * @author Mark Zeagler
 * @version 1.0
 */
public class InvalidPercentageException extends Exception {

	public InvalidPercentageException( String s ) {
		super( s );
	}
}
