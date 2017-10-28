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

public class FilterBuildPane extends FramedPane {

	private Config config;
	private Pane displayPane;
	private ComboBox<RequirementFilter.ComparatorType> filterType;
	private Button addButton;
	private Button closeButton;
	private Stage stage;

	public FilterBuildPane( Config config ) {
		this.config = config;
		setup();
	}

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
		this.closeButton = new Button( "Close" );
		this.closeButton.layoutXProperty().bind( this.widthProperty().subtract( this.closeButton.widthProperty() )
				.subtract( this.config.buffer ) );
		this.closeButton.layoutYProperty().bind( this.heightProperty().subtract( this.closeButton.heightProperty() )
				.subtract( this.config.buffer ) );
		this.closeButton.setOnAction( e -> {
			this.stage.close();
		} );
		this.getChildren().add( this.closeButton );
	}

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

	private void setDisplayPane( Pane pane ) {
		this.getChildren().remove( this.displayPane );
		this.displayPane = pane;
		this.displayPane.layoutXProperty().bind( this.filterType.layoutXProperty() );
		this.displayPane.layoutYProperty()
				.bind( this.filterType.layoutYProperty().add( this.filterType.heightProperty() ) );
		this.displayPane.prefWidthProperty().bind( this.widthProperty().subtract( this.config.buffer * 2 ) );

		this.getChildren().add( pane );
	}

	public void setConsumer( Consumer<RequirementFilter.RequirementComparator> consumer ) {
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

	public void setStage( Stage stage ) {
		this.stage = stage;
	}

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
