package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.requirements;

import eaglezr.javafx.stages.PopupStage;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.FramedPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements.Priority;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements.Requirement;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements.RequirementsList;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements.Status;

public class RequirementDetailsPane extends FramedPane {

	private Requirement requirement;
	private Config config;
	private Role role;

	private Button commitButton;
	private Button removeButton;
	private Button cancelButton;

	private TextField titleField;
	private TextField sourceField;
	private ComboBox<Status> statusComboBox;
	private ComboBox<Priority> priorityComboBox;
	private CheckBox functionalCheckbox;
	private CheckBox completeCheckbox;
	private TextArea descriptionArea;

	/**
	 * Constructor used by the "Add Requirement" button in the RequirementsListPane
	 */
	private RequirementDetailsPane( Config config ) {
		this.role = Role.ADD;
		this.config = config;
		setup();
	}

	/**
	 * Constructor used by the "Edit Requirement" action in the RequirementPane
	 *
	 * @param requirement The {@link Requirement} to be edited.
	 */
	private RequirementDetailsPane( Requirement requirement, Config config ) {
		this.role = Role.EDIT;
		this.requirement = requirement;
		this.config = config;
		setup();
	}

	/**
	 * Displays a new Requirements Details Pane over the given {@link Stage}. This pane will be used to add a new {@link
	 * Requirement} to the given {@link RequirementsList}.
	 *
	 * @param requirementsPane The {@link RequirementsListPane} which displays the {@link RequirementsList} that the new
	 *                         {@link Requirement} will be added to.
	 * @param stage            The {@link Stage} over which this window will be displayed.
	 * @param config           This defines some of the physical properties and behavior of this pane.
	 */
	protected static void showAddPane( RequirementsListPane requirementsPane, Stage stage, Config config ) {
		RequirementDetailsPane detailsPane = new RequirementDetailsPane( config );
		detailsPane.commitButton.setOnAction( e -> {
			Requirement newRequirement = new Requirement( detailsPane.titleField.getText(),
					detailsPane.descriptionArea.getText(), detailsPane.sourceField.getText(),
					detailsPane.priorityComboBox.getValue(), detailsPane.statusComboBox.getValue(),
					detailsPane.functionalCheckbox.isSelected(), detailsPane.completeCheckbox.isSelected() );
			requirementsPane.getRequirements().add( newRequirement );
			requirementsPane.update();
			detailsPane.cancelButton.fire();
		} );
		showPane( detailsPane, stage );
	}

	/**
	 * Displays a new Requirements Details Pane over the given {@link Stage}. This pane is used to edit the {@link
	 * Requirement} displayed by the given {@link RequirementPane}.
	 *
	 * @param requirementPane  The {@link RequirementPane} whose {@link Requirement} will be edited.
	 * @param requirementsPane
	 * @param stage            The {@link Stage} over which this window will be displayed.
	 * @param config
	 */
	protected static void showEditPane( RequirementPane requirementPane, RequirementsListPane requirementsPane,
			Stage stage, Config config ) {
		RequirementDetailsPane detailsPane = new RequirementDetailsPane( requirementPane.getRequirement(), config );
		detailsPane.commitButton.setOnAction( e -> {
			Requirement requirement = requirementPane.getRequirement();
			requirement.setSource( detailsPane.sourceField.getText() );
			requirement.setStatus( detailsPane.statusComboBox.getValue() );
			requirement.setPriority( detailsPane.priorityComboBox.getValue() );
			requirement.setIsFunctional( detailsPane.functionalCheckbox.isSelected() );
			requirement.setFulfilled( detailsPane.completeCheckbox.isSelected() );
			requirement.setDescription( detailsPane.descriptionArea.getText() );
			requirementPane.update();
			detailsPane.cancelButton.fire(); // It's not stupid if it works :D
		} );

		detailsPane.removeButton.setOnAction( e -> {
			requirementsPane.removePane( requirementPane );
			detailsPane.cancelButton.fire();
		} );
		showPane( detailsPane, stage );
	}

	private static void showPane( RequirementDetailsPane detailsPane, Stage stage ) {
		Scene scene = new Scene( detailsPane );
		PopupStage popupStage = new PopupStage( scene, stage );
		detailsPane.cancelButton.setOnAction( e -> popupStage.close() );
		popupStage.setTitle( detailsPane.role.getText() );
		popupStage.setWidth( 300 );
		popupStage.setHeight( 400 );
		popupStage.setResizable( false );
		popupStage.show();
	}

	private void setup() {
		// Update\Add Button
		this.commitButton = new Button( ( this.role == Role.EDIT ? "Edit" : "Add" ) );
		this.getChildren().add( this.commitButton );
		this.commitButton.layoutXProperty()
				.bind( this.widthProperty().divide( 4 ).subtract( this.commitButton.widthProperty().divide( 2 ) ) );
		this.commitButton.layoutYProperty().bind( this.heightProperty().subtract( this.commitButton.heightProperty() )
				.subtract( this.config.buffer ) );

		// Remove Button
		if ( this.role == Role.EDIT ) {
			this.removeButton = new Button( "Remove" );
			this.removeButton.layoutXProperty()
					.bind( this.widthProperty().divide( 2 ).subtract( this.removeButton.widthProperty().divide( 2 ) ) );
			this.removeButton.layoutYProperty().bind( this.commitButton.layoutYProperty() );
			this.getChildren().add( this.removeButton );
		}

		// Cancel Button
		this.cancelButton = new Button( "Cancel" );
		this.getChildren().add( this.cancelButton );
		this.cancelButton.layoutXProperty()
				.bind( this.widthProperty().divide( 2 ).add( this.widthProperty().divide( 4 ) )
						.subtract( this.cancelButton.widthProperty().divide( 2 ) ) );
		this.cancelButton.layoutYProperty().bind( this.heightProperty().subtract( this.cancelButton.heightProperty() )
				.subtract( this.config.buffer ) );

		// Title Field
		this.titleField = new TextField( ( this.role == Role.EDIT ? this.requirement.getTitle() : "" ) );
		this.getChildren().add( this.titleField );
		this.titleField.layoutXProperty().bind( this.widthProperty().subtract( this.titleField.widthProperty() )
				.subtract( this.config.buffer ) );
		this.titleField.layoutYProperty().bind( this.layoutYProperty().add( this.config.buffer ) );

		// Title Label
		Label titleLabel = new Label( "Title:" );
		this.getChildren().add( titleLabel );
		titleLabel.layoutXProperty().setValue( this.config.buffer );
		titleLabel.layoutYProperty().bind( this.titleField.layoutYProperty() );

		// Source Field
		this.sourceField = new TextField( ( this.role == Role.EDIT ? this.requirement.getSource() : "" ) );
		this.getChildren().add( this.sourceField );
		this.sourceField.layoutXProperty().bind( this.widthProperty().subtract( this.sourceField.widthProperty() )
				.subtract( this.config.buffer ) );
		this.sourceField.layoutYProperty()
				.bind( this.titleField.layoutYProperty().add( this.titleField.heightProperty() )
						.add( this.config.buffer ) );

		// Source Label
		Label sourceLabel = new Label( "Source:" );
		this.getChildren().add( sourceLabel );
		sourceLabel.layoutXProperty().bind( titleLabel.layoutXProperty() );
		sourceLabel.layoutYProperty().bind( this.sourceField.layoutYProperty() );

		// Status Drop-Down
		this.statusComboBox = new ComboBox<>( FXCollections.observableArrayList( Status.values() ) );
		this.getChildren().add( this.statusComboBox );
		if ( this.role == Role.EDIT ) {
			this.statusComboBox.setValue( this.requirement.getStatus() );
		}
		this.statusComboBox.layoutXProperty().bind( this.widthProperty().subtract( this.statusComboBox.widthProperty() )
				.subtract( this.config.buffer ) );
		this.statusComboBox.layoutYProperty()
				.bind( this.sourceField.layoutYProperty().add( this.sourceField.heightProperty() )
						.add( this.config.buffer / 2 ) );

		// Status Label
		Label statusLabel = new Label( "Status:" );
		this.getChildren().add( statusLabel );
		statusLabel.layoutXProperty().bind( sourceLabel.layoutXProperty() );
		statusLabel.layoutYProperty().bind( this.statusComboBox.layoutYProperty() );

		// Priority Drop-Down
		this.priorityComboBox = new ComboBox<>( FXCollections.observableArrayList( Priority.values() ) );
		this.getChildren().add( this.priorityComboBox );
		if ( this.role == Role.EDIT ) {
			this.priorityComboBox.setValue( this.requirement.getPriority() );
		}
		this.priorityComboBox.layoutXProperty()
				.bind( this.widthProperty().subtract( this.priorityComboBox.widthProperty() )
						.subtract( this.config.buffer ) );
		this.priorityComboBox.layoutYProperty()
				.bind( this.statusComboBox.layoutYProperty().add( this.statusComboBox.heightProperty() )
						.add( this.config.buffer / 2 ) );

		// Priority Label
		Label priorityLabel = new Label( "Priority:" );
		this.getChildren().add( priorityLabel );
		priorityLabel.layoutXProperty().bind( statusLabel.layoutXProperty() );
		priorityLabel.layoutYProperty().bind( this.priorityComboBox.layoutYProperty() );

		// Functional Checkbox
		this.functionalCheckbox = new CheckBox();
		this.functionalCheckbox.layoutXProperty()
				.bind( this.widthProperty().subtract( this.functionalCheckbox.widthProperty() )
						.subtract( this.config.buffer ) );
		this.functionalCheckbox.layoutYProperty()
				.bind( this.priorityComboBox.layoutYProperty().add( priorityLabel.heightProperty() )
						.add( this.config.buffer ) );
		if ( this.role == Role.EDIT ) {
			this.functionalCheckbox.setSelected( this.requirement.isFunctional() );
		}
		this.getChildren().add( this.functionalCheckbox );

		// Functional Label
		Label functionalLabel = new Label( "Is a functional requirement:" );
		functionalLabel.layoutXProperty().bind( priorityLabel.layoutXProperty() );
		functionalLabel.layoutYProperty().bind( this.functionalCheckbox.layoutYProperty() );
		this.getChildren().add( functionalLabel );

		// Complete Checkbox
		this.completeCheckbox = new CheckBox();
		this.completeCheckbox.layoutXProperty().bind( this.functionalCheckbox.layoutXProperty() );
		this.completeCheckbox.layoutYProperty()
				.bind( this.functionalCheckbox.layoutYProperty().add( this.functionalCheckbox.heightProperty() )
						.add( this.config.buffer ) );
		if ( this.role == Role.EDIT ) {
			this.completeCheckbox.setSelected( this.requirement.isComplete() );
		}
		this.getChildren().add( this.completeCheckbox );

		// Complete Label
		Label completeLabel = new Label( "Is complete: " );
		completeLabel.layoutXProperty().bind( functionalLabel.layoutXProperty() );
		completeLabel.layoutYProperty().bind( this.completeCheckbox.layoutYProperty() );
		this.getChildren().add( completeLabel );

		// Description Label
		Label descriptionLabel = new Label( "Description:" );
		this.getChildren().add( descriptionLabel );
		descriptionLabel.layoutXProperty().bind( completeLabel.layoutXProperty() );
		descriptionLabel.layoutYProperty().bind( completeLabel.layoutYProperty().add( completeLabel.heightProperty() )
				.add( this.config.buffer / 2 ) );

		// Description Area
		this.descriptionArea = new TextArea( ( this.role == Role.EDIT ? this.requirement.getDescription() : "" ) );
		this.getChildren().add( this.descriptionArea );
		this.descriptionArea.layoutXProperty().bind( descriptionLabel.layoutXProperty() );
		this.descriptionArea.layoutYProperty()
				.bind( descriptionLabel.layoutYProperty().add( descriptionLabel.heightProperty() )
						.add( this.config.buffer / 4 ) );
		this.descriptionArea.prefWidthProperty().bind( this.widthProperty().subtract( this.config.buffer * 2 ) );
		this.descriptionArea.prefHeightProperty()
				.bind( this.heightProperty().subtract( this.descriptionArea.layoutYProperty() )
						.subtract( this.commitButton.heightProperty() ).subtract( this.config.buffer * 2 ) );
		this.descriptionArea.setWrapText( true );
	}

	private enum Role {
		ADD( "Add Requirement" ), EDIT( "Edit Requirement" );

		private String displayText;

		Role( String displayText ) {
			this.displayText = displayText;
		}

		public String getText() {
			return this.displayText;
		}
	}
}
