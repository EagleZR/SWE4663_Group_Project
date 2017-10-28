package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.requirements;

import eaglezr.support.errorsystem.ErrorPopupSystem;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.ProjectPane;

import java.util.ArrayList;

public class FilterButtonScrollPane extends ScrollPane implements ProjectPane {

	RequirementFilter filter;
	Pane filterButtonPane;
	ArrayList<FilterButton> buttons;
	FilterButton selectedButton;

	FilterButtonScrollPane( RequirementFilter filter ) {
		super();
		this.filter = filter;
		this.filterButtonPane = new Pane();
		super.setContent( this.filterButtonPane );
		this.buttons = new ArrayList<>();
	}

	public void addComparator( RequirementFilter.RequirementComparator comparator )
			throws RequirementFilter.RedundantComparatorException {
		if ( findButton( comparator ) == null ) {
			this.filter.addComparator( comparator );
			FilterButton button = new FilterButton( comparator );
			button.setOnAction( e -> {
				for ( FilterButton button1 : this.buttons ) {
					button1.setDefaultButton( false );
				}
				button.setDefaultButton( true );
				this.selectedButton = button;
			} );
			if ( this.buttons.size() == 0 ) {
				button.layoutXProperty().setValue( 0 );
				button.layoutYProperty().setValue( 0 );
			} else {
				FilterButton previous = this.buttons.get( this.buttons.size() - 1 );
				button.layoutXProperty().bind( previous.layoutXProperty() );
				button.layoutYProperty().bind( previous.layoutYProperty().add( previous.heightProperty() ) );
			}
			this.buttons.add( button );
			this.filterButtonPane.getChildren().add( button );
		}
	}

	public void removeComparator( RequirementFilter.RequirementComparator comparator ) {
		removeButton( findButton( comparator ) );
	}

	public void removeButton( FilterButton button ) {
		if ( this.buttons.contains( button ) ) {
			if ( this.buttons.indexOf( button ) == this.buttons.size() - 1 ) {
				this.buttons.remove( button );
				this.filterButtonPane.getChildren().remove( button );
			} else if ( this.buttons.indexOf( button ) == 0 ) {
				FilterButton next = this.buttons.get( this.buttons.indexOf( button ) + 1 );
				next.layoutXProperty().unbind();
				next.layoutXProperty().setValue( 0 );
				next.layoutYProperty().unbind();
				next.layoutYProperty().setValue( 0 );
				this.buttons.remove( button );
				this.filterButtonPane.getChildren().remove( button );
			} else {
				int index = this.buttons.indexOf( button );
				FilterButton previous = this.buttons.get( index - 1 );
				FilterButton next = this.buttons.get( index + 1 );
				next.layoutXProperty().unbind();
				next.layoutYProperty().unbind();
				next.layoutXProperty().bind( previous.layoutXProperty() );
				next.layoutYProperty().bind( previous.layoutYProperty().add( previous.heightProperty() ) );
				this.buttons.remove( button );
				this.filterButtonPane.getChildren().remove( button );
			}
		}
	}

	public FilterButton findButton( RequirementFilter.RequirementComparator comparator ) {
		for ( FilterButton button : this.buttons ) {
			if ( button.comparator.equals( comparator ) ) {
				return button;
			}
		}
		return null;
	}

	void update() {
		// Make sure all Requirements have a RequirementPane and add as needed
		for ( RequirementFilter.RequirementComparator comparator : this.filter.getComparators() ) {
			boolean hasButton = false;
			for ( FilterButton button : this.buttons ) {
				if ( button.getComparator().equals( comparator ) ) {
					hasButton = true;
					break;
				}
			}
			if ( !hasButton ) {
				try {
					addComparator( comparator );
				} catch ( RequirementFilter.RedundantComparatorException e ) {
					ErrorPopupSystem.displayMessage( "There was an error while updating the filter pane." );
				}
			}
		}

		// Remove any RequirementPanes that do not correspond to a Requirement currently in the list
		for ( FilterButton button : this.buttons ) {
			boolean isInFilter = false;
			for ( RequirementFilter.RequirementComparator comparator : this.filter.getComparators() ) {
				if ( button.getComparator().equals( comparator ) ) {
					isInFilter = true;
				}
			}
			if ( !isInFilter ) {
				removeButton( button );
			}
		}
	}

	void clear() {
		this.filterButtonPane.getChildren().clear();
		this.buttons.clear();
		this.filter.resetFilter();
	}

	@Override public void loadNewProject( Project project ) {
		clear();
	}

	private class FilterButton extends Button {

		RequirementFilter.RequirementComparator comparator;

		FilterButton( RequirementFilter.RequirementComparator comparator ) {
			super( comparator.toString() );
			this.comparator = comparator;
		}

		RequirementFilter.RequirementComparator getComparator() {
			return this.comparator;
		}
	}
}
