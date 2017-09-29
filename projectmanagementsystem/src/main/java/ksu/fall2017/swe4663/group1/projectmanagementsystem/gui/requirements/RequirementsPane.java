package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.requirements;

import eaglezr.support.logs.LoggingTool;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.ProjectPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.FramedPane;

public class RequirementsPane extends FramedPane implements ProjectPane {

	public RequirementsPane( Config config ) {
		super( "Requirements Pane" );
		LoggingTool.print( "Constructing new RequirementsPane." );
	}

	@Override public void loadNewProject( Project project ) {

	}
}
