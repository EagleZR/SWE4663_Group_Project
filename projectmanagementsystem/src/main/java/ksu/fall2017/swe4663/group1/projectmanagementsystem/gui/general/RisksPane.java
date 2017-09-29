package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.general;

import eaglezr.support.logs.LoggingTool;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.ProjectPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.FramedPane;

public class RisksPane extends FramedPane implements ProjectPane {

	public RisksPane( Config config ) {
		super( "Risks Pane" );
		LoggingTool.print( "Constructing new RisksPane." );
	}

	public void loadNewProject( Project project ) {
		LoggingTool.print( "RisksPane: Loading new project." );
	}
}
