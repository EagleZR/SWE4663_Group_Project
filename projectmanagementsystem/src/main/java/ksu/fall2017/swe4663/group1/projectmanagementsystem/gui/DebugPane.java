package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.ProjectPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Person;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.WorkedHourType;

// TODO Set an animation (?) so this updates regularly.
// TODO Make this so it displays in a separate window (On separate thread?)
public class DebugPane extends Pane implements ProjectPane {

	private static int numModules = 4;

	private Project project;
	private Label teamMembers;
	private Label workedHours;
	private Label risks;
	private Label requirements;

	public DebugPane( Project project ) {
		this.project = project;

		this.teamMembers = new Label();
		this.teamMembers.layoutXProperty().setValue( 0 );
		this.teamMembers.layoutYProperty().setValue( 0 );
		this.teamMembers.prefWidthProperty().bind( this.widthProperty().divide( numModules ) );
		this.teamMembers.prefHeightProperty().bind( this.heightProperty() );
		this.getChildren().add( this.teamMembers );

		this.workedHours = new Label();
		this.workedHours.layoutXProperty().bind( this.teamMembers.layoutXProperty().add( this.teamMembers.widthProperty() ) );
		this.workedHours.layoutYProperty().setValue( 0 );
		this.workedHours.prefWidthProperty().bind( this.widthProperty().divide( numModules ) );
		this.workedHours.prefHeightProperty().bind( this.heightProperty() );
		this.getChildren().add( this.workedHours );

		this.risks = new Label();
		this.risks.layoutXProperty().bind( this.workedHours.layoutXProperty().add( this.workedHours.widthProperty() ) );
		this.risks.layoutYProperty().setValue( 0 );
		this.risks.prefWidthProperty().bind( this.widthProperty().divide( numModules ) );
		this.risks.prefHeightProperty().bind( this.heightProperty() );
		this.getChildren().add( this.risks );

		this.requirements = new Label();
		this.requirements.layoutXProperty().bind( this.risks.layoutXProperty().add( this.risks.widthProperty() ) );
		this.requirements.layoutYProperty().setValue( 0 );
		this.requirements.prefWidthProperty().bind( this.widthProperty().divide( numModules ) );
		this.requirements.prefHeightProperty().bind( this.heightProperty() );
		this.getChildren().add( this.requirements );
	}

	private void update() {
		// Team Members' Names
		String names = "Team Members: ";
		for ( Person person : this.project.getTeam().getMembers() ) {
			names += "\n" + person.getName();
		}
		this.teamMembers.setText( names );

		// Worked Hours Totals
		String hours = "Worked Hours: ";
		hours += "\nTotal:\t";
		hours += this.project.getTeam().getHours( WorkedHourType.ANY );
		hours += "\nRequirements:\t";
		hours += this.project.getTeam().getHours( WorkedHourType.REQUIREMENTS_ANALYSIS );
		hours += "\nDesigning:\t";
		hours += this.project.getTeam().getHours( WorkedHourType.DESIGNING );
		hours += "\nCoding:\t";
		hours += this.project.getTeam().getHours( WorkedHourType.CODING );
		hours += "\nTesting:\t";
		hours += this.project.getTeam().getHours( WorkedHourType.TESTING );
		hours += "\nManagement:\t";
		hours += this.project.getTeam().getHours( WorkedHourType.PROJECT_MANAGEMENT );
		this.workedHours.setText( hours );

		// Risks
		String risks = "Risks: ";

		this.risks.setText( risks );

		// Requirements
		String requirements = "Requirements: ";

		this.requirements.setText( requirements );
	}

	@Override public void loadNewProject( Project project ) {
		this.project = project;
	}
}
