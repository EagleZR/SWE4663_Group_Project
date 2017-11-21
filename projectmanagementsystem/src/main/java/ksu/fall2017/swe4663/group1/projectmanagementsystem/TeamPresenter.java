package ksu.fall2017.swe4663.group1.projectmanagementsystem;

import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Person;

/**
 * Denotes a {@link javafx.scene.layout.Pane} or other entity that displays information about a {@link
 * ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Team}. This interface defines functions that should be used
 * to update the displayed {@link ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Team} when it changes.
 *
 * @author Mark Zeagler
 * @version 1.0
 */
public interface TeamPresenter {

	/**
	 * Adds a new {@link Person} to the project.
	 *
	 * @param person The {@link Person} to be added to the {@link ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Team}
	 *               in this {@link Project}.
	 */
	void addPerson( Person person );

	/**
	 * Removes a {@link Person} from the project.
	 *
	 * @param person The {@link Person} to be removed from the {@link ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Team}
	 *               in this {@link Project}.
	 */
	void removePerson( Person person );

	/**
	 * Updates a change of the {@link ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Team}. This change could
	 * be a change in the {@link Person} members of the team, a change in Manager, or a total change of {@link
	 * ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Team}.
	 */
	void updateTeamChange();
}
