package ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog;

import eaglezr.support.logs.LoggingTool;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Person;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.ProjectHourLog;

import java.io.Serializable;

/**
 * The class used for storing information about hours submitted to the {@link ProjectHourLog}.<p>All handled issues and
 * exceptions, as well as general runtime information, are printed using the {@link LoggingTool}. Check the default
 * printer's output to read the logs.</p>
 */
public class WorkedHours implements Serializable {

	private static final long serialVersionUID = 1777547981787649391L;
	private Person person;
	private double duration;
	private WorkedHourType workedHourType;

	/**
	 * Constructs a new {@link WorkedHours} using the given {@link Person}, duration, and {@link WorkedHourType}.
	 *
	 * @param person         The person who worked the hours being submitted.
	 * @param duration       The amount of time reported by this submitted hours.
	 * @param workedHourType The type of work that was done for this reporting period.
	 * @throws InvalidWorkedHourTypeException Thrown if the {@link WorkedHourType} used was ANY, or if the {@link
	 *                                        Person} submitting is not a Manager, and used PROJECT_MANAGEMENT.
	 */
	public WorkedHours( Person person, double duration, WorkedHourType workedHourType )
			throws InvalidWorkedHourTypeException {
		LoggingTool.print( "Constructing new WorkedHours." );
		if ( workedHourType == WorkedHourType.ANY ) {
			throw new InvalidWorkedHourTypeException( "WorkedHours of type WorkedHourType.ANY cannot be submitted." );
		}
		if ( !person.isManager() && workedHourType == WorkedHourType.PROJECT_MANAGEMENT ) {
			throw new InvalidWorkedHourTypeException( "A person of class " + person.getClass().getName()
					+ " cannot submit hourlog of type PROJECT_MANAGEMENT." );
		}
		this.person = person;
		this.duration = duration;
		this.workedHourType = workedHourType;
	}

	/**
	 * Retrieves the {@link Person} for this reporting period.
	 *
	 * @return The {@link Person} who worked these hours.
	 */
	public Person getPerson() {
		return this.person;
	}

	/**
	 * The duration of hours worked in this reporting period.
	 *
	 * @return The duration of hours worked in this reporting period.
	 */
	public double getDuration() {
		return this.duration;
	}

	/**
	 * The {@link WorkedHourType} that represents what type of work is being reported in this reporting period.
	 *
	 * @return The type of hours that were reported for this period.
	 */
	public WorkedHourType getType() {
		return workedHourType;
	}

	/**
	 * Determines if the two {@link Object}s are equal.
	 *
	 * @param other The other {@link Object} to be compared against.
	 * @return {@code true} if the two {@link WorkedHours} are equal, {@code false} if the two {@link WorkedHours} are
	 * not equal, or if the other {@link Object} is not a {@link WorkedHours} instance.
	 */
	@Override public boolean equals( Object other ) {
		return other.getClass().equals( WorkedHours.class ) && this.person.equals( ( (WorkedHours) other ).person )
				&& this.duration == ( (WorkedHours) other ).duration
				&& this.workedHourType == ( (WorkedHours) other ).workedHourType;
	}

	/**
	 * Returns a {@link String} representation of this instance. <p>The format is "Worked Hours: Person: {@code person},
	 * Worked Hours Type: {code workedHourType}, Duration: {@code duration}".</p>
	 *
	 * @return A {@link String} representing the data held by this instance.
	 */
	@Override public String toString() {
		return "Worked Hours: Person: " + person.getName() + ", Worked Hours Type: " + workedHourType + ", Duration: "
				+ duration;
	}
}
