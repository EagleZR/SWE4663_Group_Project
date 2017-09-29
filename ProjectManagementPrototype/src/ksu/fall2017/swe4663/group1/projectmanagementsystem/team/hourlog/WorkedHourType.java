package ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog;

public enum WorkedHourType {

	ANY( "" ), REQUIREMENTS_ANALYSIS( "Requirements Analysis" ), DESIGNING( "Designing" ), CODING( "Coding" ), TESTING(
			"Testing" ), PROJECT_MANAGEMENT( "Project Management" );

	String text;
	double typeHourTotal;
	boolean hoursHaveChanged;

	WorkedHourType( String text ) {
		this.text = text;
	}

	protected double getTypeHourTotal() {
		return this.typeHourTotal;
	}

	protected boolean hasChanged() {
		return hoursHaveChanged;
	}

	protected void setTypeHourTotal( double value ) {
		this.typeHourTotal = value;
	}

	protected void setHasChanged( boolean value ) {
		this.hoursHaveChanged = value;
	}

	@Override public String toString() {
		return this.text;
	}
}
