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
 * WorkedHours}s submitted by each of the {@link Person}s on this team.<p>All
 * handled issues and exceptions, as well as general runtime information, are printed using the {@link LoggingTool}.
 * Check the default printer's output to read the logs.</p>
 */
public class Team implements Serializable {

	private static final long serialVersionUID = -105595693545291325L;
	private LinkedList<Person> teamMembers;
	private ProjectHourLog projectHourLog;
	private transient LinkedList<TeamPresenter> distro;

	/**
	 * Constructs a new {@link Team} from the given list of {@link Person} members. <p>NOTE: This constructor works with
	 * no arguments as well.</p>
	 *
	 * @param teamMembers The list of {@link Person} members to be added to the newly-created {@link Team}. Can be
	 *                    empty.
	 */
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

	/**
	 * Adds the list of members to the {@link Team}.
	 *
	 * @param teamMembers The {@link Person} members to add to the {@link Team}. This will do nothing if there are no
	 *                    arguments.
	 */
	public void addToTeam( Person... teamMembers ) {
		for ( Person person : teamMembers ) {
			LoggingTool.print( "Team: " + person.getName() + " was added to the team." );
			this.teamMembers.add( person );
			person.addToTeam( this );
		}
		notifyDistro();
	}

	/**
	 * Removes the indicated {@link Person} from the team.
	 *
	 * @param person The {@link Person} to be removed from the team.
	 */
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

	/**
	 * Retrieves the first Manager found on this team.
	 *
	 * @return The first Manager found on this team.
	 * @throws PersonNotOnTeamException Thrown if there is no manager found on the team.
	 */
	public Person getManager() throws PersonNotOnTeamException {
		for ( Person person : teamMembers ) {
			if ( person.isManager() ) {
				return person;
			}
		}
		throw new PersonNotOnTeamException( "There is no manager on this team." );
	}

	/**
	 * Retrieves a {@link LinkedList}<{@link Person}> of Managers on this team.
	 *
	 * @return A {@link LinkedList}<{@link Person}> of Managers on this team.
	 */
	public LinkedList<Person> getManagers() {
		LinkedList<Person> managers = new LinkedList<>();
		for ( Person person : teamMembers ) {
			if ( person.isManager() ) {
				managers.add( person );
			}
		}
		return managers;
	}

	/**
	 * Promotes the indicated {@link Person} to Manager.
	 *
	 * @param person The {@link Person} to be promoted to Manager.
	 */
	public void promote( Person person ) {
		LoggingTool.print( "Team: " + person.name + " has been promoted." );
		person.promote();
		notifyDistro();
	}

	/**
	 * Demotes the indicated {@link Person} from Manager.
	 *
	 * @param person The {@link Person} to be demoted from Manager.
	 */
	public void demote( Person person ) {
		LoggingTool.print( "Team: " + person.name + " has been demoted." );
		person.demote();
		notifyDistro();
	}

	/**
	 * Adds the indicated {@link TeamPresenter} to the list for notification of changes to this {@link Team}.
	 *
	 * @param presenter The {@link TeamPresenter} to be added to the distro of those who are notified of changes to the
	 *                  {@link Team}.
	 */
	public void addToDistro( TeamPresenter presenter ) {
		LoggingTool.print( "Team: Adding " + presenter.getClass() + " to distro." );
		if ( distro == null ) {
			LoggingTool.print( "Team: Distro was uninitialized. Initializing." );
			distro = new LinkedList<>();
		}
		this.distro.add( presenter );
	}

	/**
	 * Notifies all {@link TeamPresenter}s on the distro that there has been a change to the team. This is normally
	 * called internally, but can be called externally as well. <p>To add a {@link TeamPresenter} to the team, use the
	 * {@code addToDistro()} method for this {@link Team} instance.</p>
	 */
	public void notifyDistro() {
		LoggingTool.print( "Team: Notifying distro of change." );
		if ( distro.size() > 0 ) {
			for ( TeamPresenter presenter : distro ) {
				presenter.updateTeamChange();
			}
		}
	}

	/**
	 * Checks if this instance equals the other instance.
	 *
	 * @param other The other {@link Object} to be compared against.
	 * @return Returns {@code true} if the two {@link Team}s are equal. Returns {@code false} if the two {@link Team}s
	 * are not equal, or if the other {@link Object} is not a {@link Team}.
	 */
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
