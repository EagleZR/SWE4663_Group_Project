package ksu.fall2017.swe4663.group1.projectmanagementsystem.team;

import eaglezr.support.logs.LoggingTool;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.TeamPresenter;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.ProjectHourLog;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.WorkedHours;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * A representation of a project team. The team consists of {@link Person} instances, some of which are flagged as a
 * manager. The team also comes with an associated {@link ProjectHourLog} that records the submitted {@link
 * WorkedHours}s submitted by each of the {@link Person}s on this team.
 */
public class Team implements Serializable {

	private static final long serialVersionUID = -105595693545291325L;
	private LinkedList<Person> teamMembers;
	private ProjectHourLog projectHourLog;
	private transient LinkedList<TeamPresenter> distro;

	public Team( Person... teamMembers ) {
		LoggingTool.print( "Constructing new Team." );
		this.teamMembers = new LinkedList<>();
		distro = new LinkedList<>();
		addToTeam( teamMembers );
	}

	/**
	 * Returns the {@link ProjectHourLog} associated with this team.
	 *
	 * @return The {@link ProjectHourLog} that belongs to this team.
	 */
	public ProjectHourLog getProjectHourLog() {
		if ( projectHourLog == null ) {
			projectHourLog = new ProjectHourLog();
		}
		return projectHourLog;
	}

	public void addToTeam( Person... teamMembers ) {
		for ( Person person : teamMembers ) {
			LoggingTool.print( "Team: " + person.getName() + " was added to the team." );
			this.teamMembers.add( person );
			person.addToTeam( this );
		}
		notifyDistro();
	}

	public void removeFromTeam( Person person ) {
		LoggingTool.print( "Team: " + person.getName() + " was removed from the team." );
		this.teamMembers.remove( person );
		notifyDistro();
	}

	/**
	 * Takes a submitted {@link WorkedHours} from one of this team's {@link Person} members.
	 *
	 * @param workedHours The newly-submitted {@link WorkedHours} from one of this team's members.
	 * @throws PersonNotOnTeamException If the {@link Person} who completed the {@link WorkedHours} is not on this
	 *                                  team.
	 */
	void registerHours( WorkedHours workedHours ) throws PersonNotOnTeamException {
		LoggingTool.print( "Team: Hours submitted from " + workedHours.getPerson().name + "." );
		if ( !teamMembers.contains( workedHours.getPerson() ) ) {
			throw new PersonNotOnTeamException( workedHours.getPerson() + " is not on this team." );
		}
		getProjectHourLog().registerHours( workedHours );
	}

	/**
	 * Retrieves the list of {@link Person} members of this team.
	 *
	 * @return All of the {@link Person} members on this team.
	 */
	public LinkedList<Person> getMembers() {
		return teamMembers;
	}

	public Person getManager() throws PersonNotOnTeamException {
		for ( Person person : teamMembers ) {
			if ( person.isManager() ) {
				return person;
			}
		}
		throw new PersonNotOnTeamException( "There is no manager on this team." );
	}

	public LinkedList<Person> getManagers() {
		LinkedList<Person> managers = new LinkedList<>();
		for ( Person person : teamMembers ) {
			if ( person.isManager() ) {
				managers.add( person );
			}
		}
		return managers;
	}

	public void promote( Person person ) {
		LoggingTool.print( "Team: " + person.name + " has been promoted." );
		person.promote();
		notifyDistro();
	}

	public void demote( Person person ) {
		LoggingTool.print( "Team: " + person.name + " has been demoted." );
		person.demote();
		notifyDistro();
	}

	public void addToDistro( TeamPresenter presenter ) {
		LoggingTool.print( "Team: Adding " + presenter.getClass() + " to distro." );
		if (distro == null) {
			LoggingTool.print( "Team: Distro was uninitialized. Initializing." );
			distro = new LinkedList<>();
		}
		this.distro.add( presenter );
	}

	public void notifyDistro() {
		LoggingTool.print( "Team: Notifying distro of change." );
		if ( distro.size() > 0 ) {
			for ( TeamPresenter presenter : distro ) {
				presenter.updateTeamChange();
			}
		}
	}

	@Override public boolean equals( Object other ) {
		Team team;
		if ( other.getClass().equals( Team.class ) ) {
			team = (Team) other;
		} else {
			return false;
		}
		if ( teamMembers.size() != team.teamMembers.size() ) {
			return false;
		} else {
			for ( int i = 0; i < teamMembers.size(); i++ ) {
				if ( !teamMembers.contains( team.teamMembers.get( i ) ) ) {
					return false;
				}
			}
		}
		return this.getProjectHourLog().equals( team.getProjectHourLog() );
	}
}
