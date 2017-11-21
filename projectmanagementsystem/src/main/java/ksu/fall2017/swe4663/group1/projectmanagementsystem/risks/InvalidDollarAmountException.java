package ksu.fall2017.swe4663.group1.projectmanagementsystem.risks;

/**
 * An exception to be thrown when the {@link Double#parseDouble(String)} method results in a number that has values
 * smaller than the hundredths place.
 *
 * @author Mark Zeagler
 * @version 1.0
 */
public class InvalidDollarAmountException extends Exception {

	public InvalidDollarAmountException( String s ) {
		super( s );
	}
}
