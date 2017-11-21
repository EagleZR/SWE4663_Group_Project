package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.requirements;

import eaglezr.support.errorsystem.ErrorPopupSystem;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.FramedPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements.Priority;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements.Status;

import java.util.function.Consumer;

/**
 * Constructs a pane that creates a {@link RequirementFilter} based on information in local fields.
 *
 * @author Mark Zeagler
 * @version 1.0
 */
public class FilterBuildPane extends FramedPane {

	private Config config;
	private Pane displayPane;
	private ComboBox<RequirementFilter.ComparatorType> filterType;
	private Button addButton;
	private Stage stage;

	/**
	 * Constructs a new {@code FilterBuildPane}.
	 *
	 * @param config This defines some of the physical properties and behavior of this pane.
	 */
	public FilterBuildPane( Config config ) {
		this.config = config;
		setup();
	}

	/**
	 * This sets up the pane and places each of the components in their correct positions.
	 */
	private void setup() {
		// Select Filter label
		Label label = new Label( "Select Filter Type: " );
		label.layoutXProperty().setValue( this.config.buffer );
		label.layoutYProperty().setValue( this.config.buffer );
		this.getChildren().add( label );

		// Select Filter combo box
		this.filterType = new ComboBox<>(
				FXCollections.observableArrayList( RequirementFilter.ComparatorType.values() ) );
		this.filterType.layoutXProperty().bind( label.layoutXProperty() );
		this.filterType.layoutYProperty()
				.bind( label.layoutYProperty().add( label.heightProperty() ).add( this.config.buffer ) );
		this.filterType.setOnAction( e -> {
			if ( this.filterType.getValue() == RequirementFilter.ComparatorType.TITLE
					|| this.filterType.getValue() == RequirementFilter.ComparatorType.DESCRIPTION
					|| this.filterType.getValue() == RequirementFilter.ComparatorType.SOURCE ) {
				setDisplayPane( new StringFilterPane( this.config ) );
			} else if ( this.filterType.getValue() == RequirementFilter.ComparatorType.PRIORITY ) {
				setDisplayPane( new PriorityFilterPane( this.config ) );
			} else if ( this.filterType.getValue() == RequirementFilter.ComparatorType.STATUS ) {
				setDisplayPane( new StatusFilterPane( this.config ) );
			} else if ( this.filterType.getValue() == RequirementFilter.ComparatorType.FULFILLMENT ) {
				setDisplayPane( new BooleanFilterPane( "Is fulfilled?", this.config ) );
			} else if ( this.filterType.getValue() == RequirementFilter.ComparatorType.FUNCTIONALITY ) {
				setDisplayPane( new BooleanFilterPane( "Is a functional requirement?", this.config ) );
			} else if ( this.filterType.getValue() == RequirementFilter.ComparatorType.ID ) {
				setDisplayPane( new IDFilterPane( this.config ) );
			} else {
				setDisplayPane( new Pane() );
			}
		} );
		this.getChildren().add( this.filterType );

		// (empty) Display Pane
		this.displayPane = new Pane();
		setDisplayPane( this.displayPane );

		// Add button
		this.addButton = new Button( "Add" );
		this.addButton.layoutXProperty().bind( this.filterType.layoutXProperty() );
		this.addButton.layoutYProperty().bind( this.heightProperty().subtract( this.addButton.heightProperty() )
				.subtract( this.config.buffer ) );
		this.getChildren().add( this.addButton );

		// Close button
		Button closeButton = new Button( "Close" );
		closeButton.layoutXProperty()
				.bind( this.widthProperty().subtract( closeButton.widthProperty() ).subtract( this.config.buffer ) );
		closeButton.layoutYProperty()
				.bind( this.heightProperty().subtract( closeButton.heightProperty() ).subtract( this.config.buffer ) );
		closeButton.setOnAction( e -> {
			this.stage.close();
		} );
		this.getChildren().add( closeButton );
	}

	/**
	 * Creates and retrieves a new {@link ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.requirements.RequirementFilter.RequirementComparator}
	 * from the information in the local fields.
	 *
	 * @return A newly-constructed {@link ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.requirements.RequirementFilter.RequirementComparator}.
	 * @throws IndexOutOfBoundsException Thrown if the drop-down does not have a valid value selected.
	 * @throws IncompleteFormException   Thrown if the form has not been completed.
	 */
	private RequirementFilter.RequirementComparator getComparator()
			throws IndexOutOfBoundsException, IncompleteFormException {
		RequirementFilter.RequirementComparator comparator;
		if ( this.filterType.getValue() == RequirementFilter.ComparatorType.TITLE ) {
			StringFilterPane pane = (StringFilterPane) this.displayPane;
			if ( pane.matchType.getValue() == null || pane.textField.getText().equals( "" ) ) {
				throw new IncompleteFormException( "Please complete the form." );
			}
			comparator = new RequirementFilter.TitleComparator( pane.textField.getText(), pane.matchType.getValue() );
		} else if ( this.filterType.getValue() == RequirementFilter.ComparatorType.DESCRIPTION ) {
			StringFilterPane pane = (StringFilterPane) this.displayPane;
			if ( pane.matchType.getValue() == null || pane.textField.getText().equals( "" ) ) {
				throw new IncompleteFormException( "Please complete the form." );
			}
			comparator = new RequirementFilter.DescriptionComparator( pane.textField.getText(),
					pane.matchType.getValue() );
		} else if ( this.filterType.getValue() == RequirementFilter.ComparatorType.SOURCE ) {
			StringFilterPane pane = (StringFilterPane) this.displayPane;
			if ( pane.matchType.getValue() == null || pane.textField.getText().equals( "" ) ) {
				throw new IncompleteFormException( "Please complete the form." );
			}
			comparator = new RequirementFilter.SourceComparator( pane.textField.getText(), pane.matchType.getValue() );
		} else if ( this.filterType.getValue() == RequirementFilter.ComparatorType.PRIORITY ) {
			PriorityFilterPane pane = (PriorityFilterPane) this.displayPane;
			if ( pane.priorityMatchComboBox.getValue() == null || pane.priorityComboBox.getValue() == null ) {
				throw new IncompleteFormException( "Please complete the form." );
			}
			comparator = new RequirementFilter.PriorityComparator( pane.priorityComboBox.getValue(),
					pane.priorityMatchComboBox.getValue() );
		} else if ( this.filterType.getValue() == RequirementFilter.ComparatorType.STATUS ) {
			StatusFilterPane pane = (StatusFilterPane) this.displayPane;
			if ( pane.statusComboBox.getValue() == null ) {
				throw new IncompleteFormException( "Please complete the form." );
			}
			comparator = new RequirementFilter.StatusComparator( pane.statusComboBox.getValue() );
		} else if ( this.filterType.getValue() == RequirementFilter.ComparatorType.FULFILLMENT ) {
			BooleanFilterPane pane = (BooleanFilterPane) this.displayPane;
			comparator = new RequirementFilter.FulfillmentComparator( pane.checkBox.isSelected() );
		} else if ( this.filterType.getValue() == RequirementFilter.ComparatorType.FUNCTIONALITY ) {
			BooleanFilterPane pane = (BooleanFilterPane) this.displayPane;
			comparator = new RequirementFilter.FunctionalityComparator( pane.checkBox.isSelected() );
		} else if ( this.filterType.getValue() == RequirementFilter.ComparatorType.ID ) {
			IDFilterPane pane = (IDFilterPane) this.displayPane;
			comparator = new RequirementFilter.IDComparator( pane.getValue() );
		} else {
			throw new IndexOutOfBoundsException(
					"Invalid Filter Type Selection." ); // LATER Use a more appropriate exception.
		}
		return comparator;
	}

	/**
	 * Sets the display pane for the form.
	 *
	 * @param pane The pane to display.
	 */
	private void setDisplayPane( Pane pane ) {
		this.getChildren().remove( this.displayPane );
		this.displayPane = pane;
		this.displayPane.layoutXProperty().bind( this.filterType.layoutXProperty() );
		this.displayPane.layoutYProperty()
				.bind( this.filterType.layoutYProperty().add( this.filterType.heightProperty() ) );
		this.displayPane.prefWidthProperty().bind( this.widthProperty().subtract( this.config.buffer * 2 ) );

		this.getChildren().add( pane );
	}

	/**
	 * Retrieves the function that will consume the {@link ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.requirements.RequirementFilter.RequirementComparator}
	 * once it has been constructed.
	 *
	 * @param consumer A {@link Consumer} (or lambda) which takes appropriate actions on the {@link
	 *                 ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.requirements.RequirementFilter.RequirementComparator}
	 *                 once it has been completed. If another class needs to do something with the comparator once it
	 *                 has been completed, use this to pass it over.
	 */
	protected void setConsumer( Consumer<RequirementFilter.RequirementComparator> consumer ) {
		this.addButton.setOnAction( e -> {
			try {
				consumer.accept( getComparator() );
				this.stage.close();
			} catch ( IndexOutOfBoundsException exc ) {
				ErrorPopupSystem.displayMessage( "Please select a type of filter to add.", this.stage );
			} catch ( IncompleteFormException exc ) {
				ErrorPopupSystem.displayMessage( "Please complete the form.", this.stage );
			} catch ( NumberFormatException exc ) {
				ErrorPopupSystem.displayMessage( "Please enter a valid number.", this.stage );
			}
		} );
	}

	/**
	 * Sets the stage for this instance
	 *
	 * @param stage The stage over which pop-ups will be displayed.
	 */
	public void setStage( Stage stage ) {
		this.stage = stage;
	}

	/**
	 * A pane that lets the user input a {@link String} for the construction of a new filter.
	 */
	private class StringFilterPane extends Pane {

		private ComboBox<RequirementFilter.StringMatch> matchType;
		private TextField textField;

		StringFilterPane( Config config ) {
			// ComboBox
			this.matchType = new ComboBox<>(
					FXCollections.observableArrayList( RequirementFilter.StringMatch.values() ) );
			this.matchType.layoutXProperty().setValue( config.buffer );
			this.matchType.layoutYProperty().setValue( config.buffer );
			this.getChildren().add( this.matchType );

			// TextField
			this.textField = new TextField();
			this.textField.layoutXProperty().bind( this.matchType.layoutXProperty() );
			this.textField.layoutYProperty()
					.bind( this.matchType.layoutYProperty().add( this.matchType.heightProperty() )
							.add( config.buffer ) );
			this.textField.prefWidthProperty().bind( this.widthProperty().subtract( config.buffer * 2 ) );
			this.getChildren().add( this.textField );
		}

	}

	/**
	 *
	 */
	private class PriorityFilterPane extends Pane {

		private ComboBox<RequirementFilter.PriorityMatch> priorityMatchComboBox;
		private ComboBox<Priority> priorityComboBox;

		PriorityFilterPane( Config config ) {
			// Priority Match ComboBox
			this.priorityMatchComboBox = new ComboBox<>(
					FXCollections.observableArrayList( RequirementFilter.PriorityMatch.values() ) );
			this.priorityMatchComboBox.layoutXProperty().setValue( config.buffer );
			this.priorityMatchComboBox.layoutYProperty().setValue( config.buffer );
			this.getChildren().add( this.priorityMatchComboBox );

			// Priority Combo Box
			this.priorityComboBox = new ComboBox<>( FXCollections.observableArrayList( Priority.values() ) );
			this.priorityComboBox.layoutXProperty().bind( this.priorityMatchComboBox.layoutXProperty() );
			this.priorityComboBox.layoutYProperty().bind( this.priorityMatchComboBox.layoutYProperty()
					.add( this.priorityMatchComboBox.heightProperty() ).add( config.buffer ) );
			this.getChildren().add( this.priorityComboBox );
		}
	}

	private class StatusFilterPane extends Pane {
		private ComboBox<Status> statusComboBox;

		StatusFilterPane( Config config ) {
			this.statusComboBox = new ComboBox<>( FXCollections.observableArrayList( Status.values() ) );
			this.statusComboBox.layoutXProperty().setValue( config.buffer );
			this.statusComboBox.layoutYProperty().setValue( config.buffer );
			this.getChildren().add( this.statusComboBox );
		}
	}

	private class BooleanFilterPane extends Pane {

		CheckBox checkBox;

		BooleanFilterPane( String label, Config config ) {
			// Checkbox
			this.checkBox = new CheckBox( label );
			this.checkBox.layoutXProperty().setValue( config.buffer );
			this.checkBox.layoutYProperty().setValue( config.buffer );
			this.getChildren().add( this.checkBox );
		}
	}

	private class IDFilterPane extends Pane {

		TextField textField;

		IDFilterPane( Config config ) {
			this.textField = new TextField();
			this.textField.layoutXProperty().setValue( config.buffer );
			this.textField.layoutYProperty().setValue( config.buffer );
			this.getChildren().add( this.textField );
		}

		short getValue() {
			return Short.parseShort( this.textField.getText() );
		}
	}

	private class IncompleteFormException extends Exception {
		public IncompleteFormException( String message ) {
			super( message );
		}
	}
}
