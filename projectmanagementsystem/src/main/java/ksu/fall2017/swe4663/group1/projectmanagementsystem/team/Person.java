package ksu.fall2017.swe4663.group1.projectmanagementsystem.team;

import eaglezr.support.logs.LoggingTool;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.InvalidWorkedHourTypeException;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.WorkedHourType;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.WorkedHours;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Random;

/**
 * A class for representing the people that will make up a {@link Team}. All instances will come with a unique ID.
 * <p>All handled issues and exceptions, as well as general runtime information, are printed using the {@link
 * LoggingTool}. Check the default printer's output to read the logs.</p>
 */
public class Person implements Serializable {

	private static final long serialVersionUID = 904457401957708182L;
	/**
	 * To ensure that no 2 {@link Person} instances have the same ID.
	 */
	private static LinkedList<Long> personIDs;
	protected final long ID;
	protected String name;
	protected Team team;
	protected boolean isManager;

	/**
	 * Constructs a new {@link Person} using the given name. The person will have to be added to the {@link Team}
	 * separately.
	 *
	 * @param name The name of the new person.
	 */
	public Person( String name ) {
		LoggingTool.print( "Constructing new Person with name: " + name + "." );
		this.name = name;

		if ( personIDs == null ) {
			personIDs = new LinkedList<>();
		}

		// Generate unique ID
		Random random = new Random();
		long ID;
		do {
			ID = random.nextLong();
		} while ( personIDs.contains( ID ) );
		this.ID = ID;
		personIDs.add( ID );
	}

	/**
	 * Changes the name of this person.
	 *
	 * @param name The new name of the person.
	 */
	public void changeName( String name ) {
		LoggingTool.print( "Person: Changing Person's name from " + this.name + " to " + name + "." );
		this.name = name;
		if ( team != null ) {
			team.notifyDistro();
		}
	}

	/**
	 * Retrieves the name of this person.
	 *
	 * @return The name of this person.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Retrieves the ID of this person.<p>Unique key for differentiating between people, even if they have the same
	 * name.</p>
	 *
	 * @return The unique ID of this person.
	 */
	public long getID() {
		return this.ID;
	}

	/**
	 * Returns if this person is a Manager or not.
	 *
	 * @return {@code true} if this person is a Manager, {@code false} if this person is not a Manager.
	 */
	public boolean isManager() {
		return this.isManager;
	}

	/**
	 * Adds this person to the indicated {@link Team}.
	 *
	 * @param team The {@link Team} this person will be added to.
	 */
	protected void addToTeam( Team team ) {
		LoggingTool.print( "Person: Added to team." );
		this.team = team;
	}

	/**
	 * Reports new hours to the {@link Team} to be added to the {@link ProjectHourLog}.
	 *
	 * @param duration       The duration of the new hours to report.
	 * @param workedHourType The type of work that was done for those hours.
	 * @throws PersonNotOnTeamException       Thrown if this person is not on a {@link Team}.
	 * @throws InvalidWorkedHourTypeException Thrown if an invalid {@link WorkedHourType} is used. <p>Normally thrown if
	 *                                        {@link WorkedHourType}{@code .ANY} is used by anyone, or if {@link
	 *                                        WorkedHourType}{@code .PROJECT_MANAGEMENT} is used by a non-Manager.</p>
	 */
	public void reportHours( double duration, WorkedHourType workedHourType )
			throws PersonNotOnTeamException, InvalidWorkedHourTypeException {
		LoggingTool.print( "Person: Reporting hours." );
		if ( this.team == null ) {
			throw new PersonNotOnTeamException( this.name + " has not yet been added to a team" );
		}
		team.registerHours( new WorkedHours( this, duration, workedHourType ) );
	}

	/**
	 * Promotes this person to Manager.
	 */
	public void promote() {
		LoggingTool.print( "Person: " + name + " has been promoted." );
		this.isManager = true;
	}

	/**
	 * Demotes this person from Manager.
	 */
	public void demote() {
		LoggingTool.print( "Person: " + name + " has been demoted." );
		this.isManager = false;
	}

	/**
	 * Determines if the two {@link Object}s are equal.
	 *
	 * @param other The other {@link Object} to be compared against this one.
	 * @return {@code true} if the two {@link Person}s are equal, {@code false} if the two {@link Person}s are not
	 * equal, or if the other {@link Object} is not a {@link Person}.
	 */
	@Override public boolean equals( Object other ) {
		return other.getClass().equals( Person.class ) && this.ID == ( (Person) other ).ID;
	}
}
