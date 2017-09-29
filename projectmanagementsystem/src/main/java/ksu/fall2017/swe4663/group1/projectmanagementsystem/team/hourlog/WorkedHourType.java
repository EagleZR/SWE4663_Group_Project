package ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog;

import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Person;

/**
 * An enum used to represent the different types of {@link WorkedHours} for a {@link Project}.
 */
public enum WorkedHourType {

	/**
	 * Used to represent all other types. <p>NOTE: An {@link InvalidWorkedHourTypeException} should be thrown if a
	 * {@link WorkedHours} is constructed using this type.</p>
	 */
	ANY( "" ), REQUIREMENTS_ANALYSIS( "Requirements Analysis" ), DESIGNING( "Designing" ), CODING( "Coding" ), TESTING(
			"Testing" ), /**
	 * Used to represent actions done by a {@link Person} Manager fulfilling management duties.<p>NOTE: An exception
	 * should be thrown if any non-Manager {@link Person} attempts to use it.</p>
	 */
	PROJECT_MANAGEMENT( "Project Management" );

	String text;
	double typeHourTotal;
	boolean hoursHaveChanged;

	WorkedHourType( String text ) {
		this.text = text;
	}

	// FIXME Enums are static. Remove this from the enum.
	protected double getTypeHourTotal() {
		return this.typeHourTotal;
	}

	// FIXME Enums are static. Remove this from the enum.
	protected boolean hasChanged() {
		return hoursHaveChanged;
	}

	// FIXME Enums are static. Remove this from the enum.
	protected void setTypeHourTotal( double value ) {
		this.typeHourTotal = value;
	}

	// FIXME Enums are static. Remove this from the enum.
	protected void setHasChanged( boolean value ) {
		this.hoursHaveChanged = value;
	}

	@Override public String toString() {
		return this.text;
	}
}
