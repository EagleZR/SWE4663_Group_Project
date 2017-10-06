package ksu.fall2017.swe4663.group1.projectmanagementsystem;

import ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements.TEST_Requirement;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements.TEST_RequirementsList;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.TEST_WorkedHours;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.TEST_ProjectHourLog;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.risks.TEST_Risk;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.TEST_Person;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.TEST_Team;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith( Suite.class ) @Suite.SuiteClasses( { TEST_Requirement.class, TEST_WorkedHours.class,
		TEST_ProjectHourLog.class, TEST_Person.class, TEST_Team.class, TEST_Risk.class,
		TEST_Project.class, TEST_RequirementsList.class } ) public class Test_All {
}
