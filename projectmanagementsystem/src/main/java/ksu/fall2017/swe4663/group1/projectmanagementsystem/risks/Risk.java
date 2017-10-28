package ksu.fall2017.swe4663.group1.projectmanagementsystem.risks;

import java.io.Serializable;

public class Risk implements Serializable {
	private static final long serialVersionUID = 7199320253820717175L;

	private String description;
	private double dollarAmount;
	private double percentageLikelihood;

	public Risk( String description, double dollarAmount, double percentageLikelihood )
			throws InvalidPercentageException, InvalidDollarAmountException {
		if (percentageLikelihood < 0 ) {
			throw new InvalidPercentageException( "Percentage < 0." );
		}
		if (percentageLikelihood > 1) {
			throw new InvalidPercentageException( "Percentage > 1." );
		}
		if (dollarAmount < 0) {
			throw new InvalidDollarAmountException( "Dollar amount < 0." );
		}
		this.description = description;
		this.dollarAmount = dollarAmount;
		this.percentageLikelihood = percentageLikelihood;
	}

	public void setDescription( String description ) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDollarAmount( double dollarAmount ) throws InvalidDollarAmountException {
		if (dollarAmount < 0) {
			throw new InvalidDollarAmountException( "Dollar amount < 0." );
		}
		this.dollarAmount = dollarAmount;
	}

	public double getDollarAmount() {
		return this.dollarAmount;
	}

	public void setPercentageLikelihood( double percentageLikelihood ) throws InvalidPercentageException {
		if (percentageLikelihood < 0 ) {
			throw new InvalidPercentageException( "Percentage < 0." );
		}
		if (percentageLikelihood > 1) {
			throw new InvalidPercentageException( "Percentage > 1." );
		}
		this.percentageLikelihood = percentageLikelihood;
	}

	public double getPercentageLikelihood() {
		return this.percentageLikelihood;
	}
}
