package ksu.fall2017.swe4663.group1.projectmanagementsystem;

import eaglezr.support.logs.LoggingTool;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements.Requirement;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.risks.Risk;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Team;

import java.io.*;
import java.util.LinkedList;

public class Project implements Serializable {

	private static final long serialVersionUID = 4321222192282058099L;
	private static String saveDirectory = "saves/";
	private Team team;
	private String description;
	private LinkedList<Requirement> requirements;
	private LinkedList<Risk> risks;

	public Project() {
		LoggingTool.print( "Constructing new Project." );
		this.team = new Team();
		this.requirements = new LinkedList<>();
		this.risks = new LinkedList<>();
	}

	public static void save( Project project, String fileName ) throws IOException {
		File file = new File( saveDirectory + fileName );
		save( project, file );
	}

	public static void save( Project project, File saveFile ) throws IOException {
		LoggingTool.print( "Project: Saving project as: " + saveFile.getAbsolutePath() + "." );
		if ( !saveFile.exists() ) {
			saveFile.createNewFile();
		}
		ObjectOutputStream out = new ObjectOutputStream( new FileOutputStream( saveFile ) );
		out.writeObject( project );
	}

	public static Project load( String fileName ) throws IOException, ClassNotFoundException {
		File file = new File( saveDirectory + fileName ); // LATER FileNotFoundException ?
		return load( file );
	}

	public static Project load( File loadFile ) throws IOException, ClassNotFoundException {
		LoggingTool.print( "Project: Loading project from: " + loadFile.getAbsolutePath() + "." );
		if ( !loadFile.exists() ) {
			LoggingTool.print( "Project: File does not exist." );
			throw new FileNotFoundException( "The File, " + loadFile.getName() + " could not be located." );
		}
		ObjectInputStream in = new ObjectInputStream( new FileInputStream( loadFile ) );
		return (Project) in.readObject();
	}

	public void setTeam( Team team ) {
		LoggingTool.print( "Project: New team has been set." );
		this.team = team;
	}

	public Team getTeam() {
		return team;
	}

	public void setDescription( String description ) {
		LoggingTool.print( "Project: New description has been set." );
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public final LinkedList<Risk> getRisks() {
		return risks;
	}

	public final LinkedList<Requirement> getRequirements() {
		return requirements;
	}

	@Override public boolean equals( Object other ) {
		return other.getClass().equals( Project.class ) && getTeam().equals( ( (Project) other ).getTeam() )
				&& getRisks().equals( ( (Project) other ).getRisks() ) && getRequirements()
				.equals( ( (Project) other ).getRequirements() );
	}
}
