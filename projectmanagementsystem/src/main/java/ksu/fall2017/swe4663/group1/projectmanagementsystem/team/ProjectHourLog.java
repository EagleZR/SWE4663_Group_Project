package ksu.fall2017.swe4663.group1.projectmanagementsystem.team;

import eaglezr.support.logs.LoggingTool;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.WorkedHourType;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.WorkedHours;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * A tool used to organize and provide access to the various {@link WorkedHours} submitted for a {@link Project}.
 */
public class ProjectHourLog implements Serializable {

	private static final long serialVersionUID = -7863698898951761080L;
	private LinkedList<WorkedHours> workedHours;
	private LinkedList<Person> contributors;

	/**
	 * Constructs a new {@link ProjectHourLog} using the given {@link WorkedHours}.<p>NOTE: This constructor works with
	 * no arguments as well.</p>
	 *
	 * @param workedHours The list of {@link WorkedHours} to build the new {@link ProjectHourLog} using.
	 */
	protected ProjectHourLog( WorkedHours... workedHours ) {
		LoggingTool.print( "Constructing new ProjectHourLog." );
		this.workedHours = new LinkedList<>( Arrays.asList( workedHours ) );
		this.contributors = new LinkedList<>();
	}

	/**
	 * Retrieves the {@link WorkedHours} by {@link WorkedHourType}.<p>Use {@link WorkedHourType}{@code .ANY} to retrieve
	 * all {@link WorkedHours}.</p>
	 *
	 * @param workedHourType The type of hours to be retrieved.
	 * @return Returns the sum of worked hours by the given type.
	 */
	public double getHours( WorkedHourType workedHourType ) {
		int count = 0;
		for ( WorkedHours workedHours : this.workedHours ) {
			if ( workedHours.getType() == workedHourType || workedHourType == WorkedHourType.ANY ) {
				count += workedHours.getDuration();
			}
		}

		return count;
	}

	/**
	 * Retrieves the {@link WorkedHours} by {@link Person}.
	 *
	 * @param person The {@link Person} whose hours will be retrieved.
	 * @return Returns the sum of worked hours by the given {@link Person}.
	 */
	public double getHours( Person person ) {
		int count = 0;
		for ( WorkedHours workedHours : this.workedHours ) {
			if ( workedHours.getPerson().equals( person ) ) {
				count += workedHours.getDuration();
			}
		}
		return count;
	}

	/**
	 * Retrieves the {@link WorkedHours} of {@link WorkedHourType} by {@link Person}.
	 *
	 * @param person The {@link Person} whose hours will be retrieved.
	 * @param type   The {@link WorkedHourType} of the hours to be retrieved.
	 * @return Returns the sum of worked hours by the given {@link Person}.
	 */
	public double getHours( Person person, WorkedHourType type ) {
		int count = 0;
		for ( WorkedHours workedHour : this.workedHours ) {
			if ( workedHour.getType() == type && workedHour.getPerson().equals( person ) ) {
				count += workedHour.getDuration();
			}
		}
		return count;
	}

	/**
	 * Registers new {@link WorkedHours} submitted by a {@link Person}.
	 *
	 * @param newWorkedHours The new {@link WorkedHours} to add for the {@link Project}.
	 */
	public void registerHours( WorkedHours newWorkedHours ) {
		LoggingTool.print( "ProjectHourLog: Registering new Hours: " + newWorkedHours.toString() + "." );
		this.workedHours.add( newWorkedHours );
		Person contributor = newWorkedHours.getPerson();
		if ( !contributors.contains( contributor ) ) {
			contributors.add( contributor );
		}
	}

	/**
	 * Determines if the two {@link Object}s are equal.
	 *
	 * @param other The other {@link Object} to be compared against.
	 * @return {@code true} if the other {@link ProjectHourLog} is equal to this one, {@code false} if the two {@link
	 * ProjectHourLog}s are not equal, or if the other {@link Object} is not a {@link ProjectHourLog}.
	 */
	@Override public boolean equals( Object other ) {
		ProjectHourLog otherHourLog;
		if ( other.getClass().equals( ProjectHourLog.class ) ) {
			otherHourLog = (ProjectHourLog) other;
		} else {
			return false;
		}
		if ( this.workedHours.size() != otherHourLog.workedHours.size() ) {
			return false;
		}
		for ( int i = 0; i < this.workedHours.size(); i++ ) {
			if ( !this.workedHours.get( i ).equals( otherHourLog.workedHours.get( i ) ) ) {
				return false;
			}
		}
		return true;
	}
}
