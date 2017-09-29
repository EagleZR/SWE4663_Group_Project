package ksu.fall2017.swe4663.group1.projectmanagementsystem;

import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Person;

public interface TeamPresenter {

	void addPerson( Person person );
	void removePerson( Person person );
	void updateTeamChange();

}
