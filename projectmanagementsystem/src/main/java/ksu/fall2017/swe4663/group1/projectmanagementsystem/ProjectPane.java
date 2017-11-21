package ksu.fall2017.swe4663.group1.projectmanagementsystem;

/**
 * Denotes a {@link javafx.scene.layout.Pane} or other entity that displays information about a {@link Project}. This
 * interface defines functions that should be used to update the displayed {@link Project} when it changes.
 *
 * @author Mark Zeagler
 * @version 1.0
 */
public interface ProjectPane {

	/**
	 * Changes the {@link Project} stored, displayed, and edited by this instance.
	 *
	 * @param project The new {@link Project} to display.
	 */
	void loadNewProject( Project project );
}
