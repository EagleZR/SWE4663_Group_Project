package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui;

import eaglezr.support.logs.LoggingTool;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Person;

import java.util.LinkedList;
import java.util.List;

public class PersonButtonScrollPane extends ScrollPane {

	private Pane buttonPane;
	private LinkedList<PersonButton> buttons;

	public PersonButtonScrollPane() {
		LoggingTool.print( "Constructing a new PersonButtonScrollPane." );
		this.setHbarPolicy( ScrollPane.ScrollBarPolicy.NEVER );
		this.setVbarPolicy( ScrollPane.ScrollBarPolicy.ALWAYS );
		this.setFitToWidth( true );
		this.buttonPane = new Pane();
		this.setContent( this.buttonPane );
		this.buttons = new LinkedList<>();
	}

	public void addButton( PersonButton button ) {
		LoggingTool.print( "PersonButtonScrollPane: Adding Button with text: " + button.getText() );
		button.prefWidthProperty().bind( this.buttonPane.widthProperty() );
		button.layoutXProperty().setValue( 0 );
		if ( this.buttons.size() == 0 ) {
			button.layoutYProperty().setValue( 0 );
		} else {
			Button previous = this.buttons.get( this.buttons.size() - 1 );
			button.layoutYProperty().bind( previous.layoutYProperty().add( previous.heightProperty() ) );
		}
		this.buttons.add( button );
		this.buttonPane.getChildren().add( button );
	}

	public void removeButton( PersonButton button ) {
		LoggingTool.print( "PersonButtonScrollPane: Removing Button with text: " + ( button == null ?
				"<null>" :
				button.getText() ) );
		int index = this.buttons.indexOf( button );
		if ( this.buttons.size() == 1 ) {

		} else if ( index == 0 ) {
			Button next = this.buttons.get( 1 );
			next.layoutYProperty().unbind();
			next.layoutYProperty().setValue( 0 );
		} else if ( index != this.buttons.size() - 1 && index > 0 ) {
			Button next = this.buttons.get( index + 1 );
			Button prev = this.buttons.get( index - 1 );
			next.layoutYProperty().bind( prev.layoutYProperty().add( prev.heightProperty() ) );
		}

		this.buttonPane.getChildren().remove( button );
		this.buttons.remove( button );
	}

	public LinkedList<PersonButton> getButtons() {
		return this.buttons;
	}

	public void addPerson( Person person ) {
		PersonButton button = new PersonButton( person );
		addButton( button );
	}

	public void removePerson( Person person ) {
		if ( containsPerson( person ) ) {
			removeButton( getPersonButton( person ) );
		}
	}

	public boolean containsPerson( Person person ) {
		for ( PersonButton button : this.buttons ) {
			if ( button.getPerson().equals( person ) ) {
				// Updates the name if it's changed.
				if ( !button.getText().equals( person.getName() ) ) {
					LoggingTool
							.print( "PersonButtonScrollPane: Updating the name displayed in the button for: " + person
									.getName() + "." );
					button.setText( person.getName() );
				}
				return true;
			}
		}
		return false;
	}

	public PersonButton getPersonButton( Person person ) {
		for ( PersonButton button : this.buttons ) {
			if ( button.getPerson().equals( person ) ) {
				return button;
			}
		}
		return null;
	}

	public void clear() {
		this.buttonPane.getChildren().clear();
		this.buttons.clear();
	}
}
