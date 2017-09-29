package ksu.fall2017.swe4663.group1.projectmanagementsystem.team;

import eaglezr.support.logs.LoggingTool;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.InvalidWorkedHourTypeException;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.WorkedHourType;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.WorkedHours;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Random;

public class Person implements Serializable {

	private static final long serialVersionUID = 904457401957708182L;
	/**
	 * To ensure that no 2 {@link Person} instances have the same ID, at least in the same program.
	 */
	private static LinkedList<Long> personIDs;
	protected final long ID;
	protected String name;
	protected Team team;
	protected boolean isManager;

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

	public void changeName( String name ) {
		LoggingTool.print( "Person: Changing Person's name from " + this.name + " to " + name + "." );
		this.name = name;
		if ( team != null ) {
			team.notifyDistro();
		}
	}

	public String getName() {
		return this.name;
	}

	public long getID() {
		return this.ID;
	}

	public boolean isManager() {
		return this.isManager;
	}

	protected void addToTeam( Team team ) {
		LoggingTool.print( "Person: Added to team." );
		this.team = team;
	}

	public void reportHours( double duration, WorkedHourType workedHourType )
			throws PersonNotOnTeamException, InvalidWorkedHourTypeException {
		LoggingTool.print( "Person: Reporting hours." );
		if ( this.team == null ) {
			throw new PersonNotOnTeamException( this.name + " has not yet been added to a team" );
		}
		team.registerHours( new WorkedHours( this, duration, workedHourType ) );
	}

	@Override public boolean equals( Object other ) {
		return other.getClass().equals( Person.class ) && this.ID == ( (Person) other ).ID;
	}

	public void promote() {
		LoggingTool.print( "Person: " + name + " has been promoted." );
		this.isManager = true;
	}

	public void demote() {
		LoggingTool.print( "Person: " + name + " has been demoted." );
		this.isManager = false;
	}
}
