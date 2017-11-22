package ksu.fall2017.swe4663.group1.projectmanagementsystem.risks;

import java.io.Serializable;

/**
 * A class to represent the risks associated with a development project.
 *
 * @author Mark Zeagler
 * @version 1.0
 */
public class Risk implements Serializable {
	private static final long serialVersionUID = 7199320253820717175L;

	private String description;
	private double dollarAmount;
	private double percentageLikelihood;

	/**
	 * Constructs a risk with the given attributes.
	 *
	 * @param description          A short description of the failure that is likely to occur, and what actions could
	 *                             lead to it.
	 * @param dollarAmount         The dollar amount it would take to recover from the failure. This must be a string
	 *                             representation of a decimal value which has no values less than the hundredths ( 0.00
	 *                             ) place.
	 * @param percentageLikelihood The likelihood in a percentage (value less than 1) that recovery is possible
	 *                             following the failure.
	 * @throws InvalidPercentageException   Thrown if the value is greater than 1 or less than 0.
	 * @throws InvalidDollarAmountException Thrown if the value is less than 0 or has too many decimal places.
	 */
	public Risk( String description, double dollarAmount, double percentageLikelihood )
			throws InvalidPercentageException, InvalidDollarAmountException {
		if ( percentageLikelihood < 0 ) {
			throw new InvalidPercentageException( "Percentage < 0." );
		}
		if ( percentageLikelihood > 1 ) {
			throw new InvalidPercentageException( "Percentage > 1." );
		}
		if ( dollarAmount < 0 ) {
			throw new InvalidDollarAmountException( "Dollar amount < 0." );
		}
		if ( ( dollarAmount * 100 ) % 1 > 0 ) {
			throw new InvalidDollarAmountException( "Dollar amount has too many decimal places." );
		}
		this.description = description;
		this.dollarAmount = dollarAmount;
		this.percentageLikelihood = percentageLikelihood;
	}

	/**
	 * Sets the description for the risk.
	 *
	 * @param description The new description to be assigned to the risk.
	 */
	public void setDescription( String description ) {
		this.description = description;
	}

	/**
	 * Retrieves the description of this risk.
	 *
	 * @return The description of this risk.
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Sets the dollar amount for the risk.
	 *
	 * @param dollarAmount The new description to be assigned to the risk.
	 * @throws InvalidDollarAmountException Thrown if the value is less than 0 or has too many decimal places.
	 */
	public void setDollarAmount( double dollarAmount ) throws InvalidDollarAmountException {
		if ( dollarAmount < 0 ) {
			throw new InvalidDollarAmountException( "Dollar amount < 0." );
		}
		this.dollarAmount = dollarAmount;
	}

	/**
	 * Retrieves the dollar amount of this risk.
	 *
	 * @return The dollar amount of this risk.
	 */
	public double getDollarAmount() {
		return this.dollarAmount;
	}

	/**
	 * Sets the percentage likelihood for the risk.
	 *
	 * @param percentageLikelihood The new description to be assigned to the risk.
	 * @throws InvalidPercentageException Thrown if the value is greater than 1 or less than 0.
	 */
	public void setPercentageLikelihood( double percentageLikelihood ) throws InvalidPercentageException {
		if ( percentageLikelihood < 0 ) {
			throw new InvalidPercentageException( "Percentage < 0." );
		}
		if ( percentageLikelihood > 1 ) {
			throw new InvalidPercentageException( "Percentage > 1." );
		}
		this.percentageLikelihood = percentageLikelihood;
	}

	/**
	 * Retrieves the percentage likelihood of this risk.
	 *
	 * @return The percentage likelihood of this risk.
	 */
	public double getPercentageLikelihood() {
		return this.percentageLikelihood;
	}
}
