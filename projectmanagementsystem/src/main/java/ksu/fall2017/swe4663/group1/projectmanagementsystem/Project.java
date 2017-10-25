package ksu.fall2017.swe4663.group1.projectmanagementsystem;

import eaglezr.support.logs.LoggingTool;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements.Requirement;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements.RequirementsList;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.risks.Risk;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Person;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Team;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.ProjectHourLog;

import java.io.*;
import java.util.LinkedList;

/**
 * A class used to organize and associate the various aspects of a project, including the {@link Team} with its member
 * {@link Person}s, the {@link ProjectHourLog}, the {@link Requirement}s, and the {@link Risk}s. <p>The instances of the
 * class are designed to be saved and loaded using the static methods {@code save(Project project, String fileName)} or
 * {@code save(Project project, File file)} and {@code load(String fileName)} or {@code load(File file)}.</p><p>All
 * handled issues and exceptions, as well as general runtime information, are printed using the {@link LoggingTool}.
 * Check the default printer's output to read the logs.</p>
 */
public class Project implements Serializable {

	private static final long serialVersionUID = 4321222192282058099L;
	private static String saveDirectory = "saves/";
	private Team team;
	private String description;
	private RequirementsList requirements;
	private LinkedList<Risk> risks;

	/**
	 * Constructs an empty project.
	 */
	public Project() {
		LoggingTool.print( "Constructing new Project." );
		this.team = new Team();
		this.requirements = new RequirementsList();
		this.risks = new LinkedList<>();
	}

	/**
	 * Saves the project using the given fileName. Saves by fileName go, by default, in the "saves" directory associated
	 * with this class's "src" folder.
	 *
	 * @param project  The {@link Project} to save.
	 * @param fileName The name of the file to be saved.
	 * @throws IOException Thrown if there is any issue involved with saving the file. Check the log for details.
	 */
	public static void save( Project project, String fileName ) throws IOException {
		File file = new File( saveDirectory + fileName );
		save( project, file );
	}

	/**
	 * Saves the project using the given {@link File}.
	 *
	 * @param project  The {@link Project} to save.
	 * @param saveFile The file to be saved into. If the file does not yet exist, it will be created. If it does exist,
	 *                 it will be overwritten.
	 * @throws IOException Thrown if there is any issue involved with saving the file. Check the log for details.
	 */
	public static void save( Project project, File saveFile ) throws IOException {
		LoggingTool.print( "Project: Saving project as: " + saveFile.getAbsolutePath() + "." );
		if ( !saveFile.exists() ) {
			if ( !saveFile.createNewFile() ) {
				LoggingTool.print( "Project: Could not create a new file for saving." );
			}
		}
		ObjectOutputStream out = new ObjectOutputStream( new FileOutputStream( saveFile ) );
		out.writeObject( project );
	}

	/**
	 * Loads the project from the given fileName. This loads from the default, "saves", directory.
	 *
	 * @param fileName The name of the file to be loaded.
	 * @return The loaded project.
	 * @throws IOException            Thrown if there are any issues with reading the data from the file. Check the log
	 *                                for details.
	 * @throws ClassNotFoundException Thrown if there are issues with converting the loaded data into the appropriate
	 *                                class instances.
	 */
	public static Project load( String fileName ) throws IOException, ClassNotFoundException {
		File file = new File( saveDirectory + fileName ); // LATER FileNotFoundException ?
		return load( file );
	}

	/**
	 * Loads the project from the given file.
	 *
	 * @param loadFile The file to be loaded.
	 * @return The loaded project.
	 * @throws IOException            Thrown if there are any issues with reading the data from the file. Check the log
	 *                                for details.
	 * @throws ClassNotFoundException Thrown if there are issues with converting the loaded data into the appropriate
	 *                                class instances.
	 */
	public static Project load( File loadFile ) throws IOException, ClassNotFoundException {
		LoggingTool.print( "Project: Loading project from: " + loadFile.getAbsolutePath() + "." );
		if ( !loadFile.exists() ) {
			LoggingTool.print( "Project: File does not exist." );
			throw new FileNotFoundException( "The File, " + loadFile.getName() + " could not be located." );
		}
		ObjectInputStream in = new ObjectInputStream( new FileInputStream( loadFile ) );
		return (Project) in.readObject();
	}

	/**
	 * Sets the {@link Team} for this project. The previous team is discarded.
	 *
	 * @param team The new {@link Team} for the project.
	 */
	public void setTeam( Team team ) {
		LoggingTool.print( "Project: New team has been set." );
		this.team = team;
	}

	/**
	 * Retrieves the {@link Team} for this project.
	 *
	 * @return The {@link Team} for this project.
	 */
	public Team getTeam() {
		return team;
	}

	/**
	 * Sets the description for this project.
	 *
	 * @param description The new description for this project.
	 */
	public void setDescription( String description ) {
		LoggingTool.print( "Project: New description has been set." );
		this.description = description;
	}

	/**
	 * Retrieves the description for this project.
	 *
	 * @return The description for this project.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Retrieves the {@link Risk}s for this project.
	 *
	 * @return The {@link Risk}s for this project.
	 */
	public final LinkedList<Risk> getRisks() {
		return risks;
	}

	/**
	 * Retrieves the {@link RequirementsList} for this project. The individual {@link Requirement}s are contained within
	 * the returned list.
	 *
	 * @return The {@link RequirementsList} for this project.
	 */
	public final RequirementsList getRequirements() {
		return requirements;
	}

	/**
	 * Determines if the two {@link Project}s are equal.
	 *
	 * @param other The other {@link Object} to be compared against.
	 * @return Returns true if the two {@link Project}s are the same. Returns false if the two are different, or if the
	 * other is not a {@link Project}.
	 */
	@Override public boolean equals( Object other ) {
		return other.getClass().equals( Project.class ) && getTeam().equals( ( (Project) other ).getTeam() )
				&& getRisks().equals( ( (Project) other ).getRisks() ) && getRequirements()
				.equals( ( (Project) other ).getRequirements() );
	}
}
