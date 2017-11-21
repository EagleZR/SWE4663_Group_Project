package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.general.risks;

import eaglezr.javafx.stages.PopupStage;
import eaglezr.support.errorsystem.ErrorPopupSystem;
import eaglezr.support.logs.LoggingTool;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.risks.InvalidDollarAmountException;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.risks.InvalidPercentageException;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.risks.Risk;

/**
 * This pane allows for the construction and editing of a {@link RiskPane}.
 *
 * @author Mark Zeagler
 * @version 1.0
 */
public class RiskDetailsPane extends Pane {

	private Role role;

	private Risk risk;

	private TextField likelihoodField;
	private TextField costField;
	private TextArea descriptionArea;

	private Button commitButton;
	private Button removeButton;
	private Button cancelButton;

	/**
	 * Constructs a blank {@link RiskDetailsPane} used to construct a new {@link Risk}.
	 *
	 * @param config This defines some of the physical properties and behavior of this pane.
	 */
	private RiskDetailsPane( Config config ) {
		LoggingTool.print( "Creating new RiskDetailsPane for adding a Risk." );
		this.role = Role.ADD;
		setup( config );
	}

	/**
	 * Constructs a new {@link RiskDetailsPane} used to edit a given {@link Risk}.
	 *
	 * @param risk   The {@link Risk} whose information will be displayed and edited by this pane.
	 * @param config This defines some of the physical properties and behavior of this pane.
	 */
	private RiskDetailsPane( Risk risk, Config config ) {
		LoggingTool.print( "Creating new RiskDetailsPane for editing a Risk." );
		this.role = Role.EDIT;
		this.risk = risk;
		setup( config );
	}

	/**
	 * Displays a pop-up to create a new {@link Risk}.
	 *
	 * @param risksPane The pane where the newly-constructed {@link RiskPane} will be added to once the new {@link Risk}
	 *                  has been created.
	 * @param stage     The stage over which pop-ups will be displayed.
	 * @param config    This defines some of the physical properties and behavior of this pane.
	 */
	protected static void showAddPane( RisksPane risksPane, Stage stage, Config config ) {
		RiskDetailsPane detailsPane = new RiskDetailsPane( config );
		detailsPane.commitButton.setOnAction( e -> {
			LoggingTool.print( "RiskDetailsPane: Adding a new Risk." );
			try {
				Risk newRisk = new Risk( detailsPane.descriptionArea.getText(),
						Double.parseDouble( detailsPane.costField.getText() ),
						Double.parseDouble( detailsPane.likelihoodField.getText() ) );
				risksPane.addRisk( new RiskPane( newRisk, risksPane, stage, config ) );
				detailsPane.cancelButton.fire();
			} catch ( InvalidPercentageException | InvalidDollarAmountException exc ) {
				LoggingTool.print( "RisksDetailsPane: The Risk could not be added." );
				ErrorPopupSystem.displayMessage( exc.getMessage() );
			}
		} );
		showPane( detailsPane, stage );
	}

	/**
	 * Displays a pop-up to edit the {@link Risk} of a given {@link RiskPane}.
	 *
	 * @param riskPane  The pane which holds the {@link Risk} to be edited.
	 * @param risksPane The pane which holds the {@link RiskPane} whose {@link Risk} will be edited.
	 * @param stage     The stage over which pop-ups will be displayed.
	 * @param config    This defines some of the physical properties and behavior of this pane.
	 */
	protected static void showEditPane( RiskPane riskPane, RisksPane risksPane, Stage stage, Config config ) {
		Risk risk = riskPane.getRisk();
		RiskDetailsPane detailsPane = new RiskDetailsPane( risk, config );

		detailsPane.commitButton.setOnAction( e -> {
			LoggingTool.print( "RiskDetailsPane: Committing the edited Risk." );
			try {
				Risk newRisk = new Risk( detailsPane.descriptionArea.getText(),
						Double.parseDouble( detailsPane.costField.getText() ),
						Double.parseDouble( detailsPane.likelihoodField.getText() ) );
				RiskPane newPane = new RiskPane( risk, risksPane, stage, config );
				risksPane.removeRisk( riskPane );
				risksPane.addRisk( newPane );
				detailsPane.cancelButton.fire();
			} catch ( InvalidPercentageException | InvalidDollarAmountException exc ) {
				LoggingTool.print( "RisksDetailsPane: The Risk could not be edited." );
				ErrorPopupSystem.displayMessage( exc.getMessage() );
			}
		} );

		detailsPane.removeButton.setOnAction( e -> {
			LoggingTool.print( "RisksDetailsPane: Removing the Risk." );
			risksPane.removeRisk( riskPane );
			detailsPane.cancelButton.fire();
		} );
		showPane( detailsPane, stage );
	}

	/**
	 * The abstract add/edit pane called by the {@link #showAddPane(RisksPane, Stage, Config)} and {@link
	 * #showEditPane(RiskPane, RisksPane, Stage, Config)} methods.
	 *
	 * @param detailsPane The {@link RiskDetailsPane} to be displayed.
	 * @param stage       The stage over which pop-ups will be displayed.
	 */
	private static void showPane( RiskDetailsPane detailsPane, Stage stage ) {
		LoggingTool.print( "RiskDetailsPane: Displaying the " + detailsPane.role + " pane." );
		Scene scene = new Scene( detailsPane );
		PopupStage popupStage = new PopupStage( scene, stage );
		detailsPane.cancelButton.setOnAction( e -> popupStage.close() );
		popupStage.setTitle( detailsPane.role.getText() );
		popupStage.setWidth( 300 );
		popupStage.setHeight( 400 );
		popupStage.setResizable( false );
		popupStage.show();
	}

	/**
	 * This sets up the pane and places each of the components in their correct positions with the appropriate values
	 * displayed.
	 *
	 * @param config This defines some of the physical properties and behavior of this pane.
	 */
	private void setup( Config config ) {
		// Add Button
		this.commitButton = new Button( ( this.role == Role.ADD ? "Add" : "Edit" ) );
		this.commitButton.layoutXProperty()
				.bind( this.widthProperty().divide( 4 ).subtract( this.commitButton.widthProperty().divide( 2 ) ) );
		this.commitButton.layoutYProperty()
				.bind( this.heightProperty().subtract( this.commitButton.heightProperty() ).subtract( config.buffer ) );
		this.getChildren().add( this.commitButton );

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
		this.cancelButton.layoutYProperty()
				.bind( this.heightProperty().subtract( this.cancelButton.heightProperty() ).subtract( config.buffer ) );

		// Likelihood Field
		this.likelihoodField = new TextField(
				( this.role == Role.EDIT ? "" + this.risk.getPercentageLikelihood() : "" ) );
		this.likelihoodField.layoutXProperty()
				.bind( this.widthProperty().subtract( this.likelihoodField.widthProperty() )
						.subtract( config.buffer ) );
		this.likelihoodField.layoutYProperty().setValue( config.buffer );
		this.getChildren().add( this.likelihoodField );

		// Likelihood Label
		Label likelihoodLabel = new Label( "Likelihood: " );
		likelihoodLabel.layoutXProperty().setValue( config.buffer );
		likelihoodLabel.layoutYProperty().bind( this.likelihoodField.layoutYProperty() );
		this.getChildren().add( likelihoodLabel );

		// Cost Field
		this.costField = new TextField( ( this.role == Role.EDIT ? "" + this.risk.getDollarAmount() : "" ) );
		this.costField.layoutXProperty()
				.bind( this.widthProperty().subtract( this.costField.widthProperty() ).subtract( config.buffer ) );
		this.costField.layoutYProperty()
				.bind( this.likelihoodField.layoutYProperty().add( this.likelihoodField.heightProperty() )
						.add( config.buffer ) );
		this.getChildren().add( this.costField );

		// Cost Label
		Label costLabel = new Label( "Cost: " );
		costLabel.layoutXProperty().bind( likelihoodLabel.layoutXProperty() );
		costLabel.layoutYProperty().bind( this.costField.layoutYProperty() );
		this.getChildren().add( costLabel );

		// Description Label
		Label descriptionLabel = new Label( "Description:" );
		descriptionLabel.layoutXProperty().bind( costLabel.layoutXProperty() );
		descriptionLabel.layoutYProperty()
				.bind( costLabel.layoutYProperty().add( costLabel.heightProperty() ).add( config.buffer ) );
		this.getChildren().add( descriptionLabel );

		// Description Area
		this.descriptionArea = new TextArea( ( this.role == Role.EDIT ? this.risk.getDescription() : "" ) );
		this.descriptionArea.layoutXProperty().bind( descriptionLabel.layoutXProperty() );
		this.descriptionArea.layoutYProperty()
				.bind( descriptionLabel.layoutYProperty().add( descriptionLabel.heightProperty() )
						.add( config.buffer / 2 ) );
		this.descriptionArea.prefWidthProperty().bind( this.widthProperty().subtract( config.buffer * 2 ) );
		this.descriptionArea.prefHeightProperty()
				.bind( this.commitButton.layoutYProperty().subtract( this.descriptionArea.layoutYProperty() )
						.subtract( config.buffer ) );
		this.descriptionArea.setWrapText( true );
		this.getChildren().add( this.descriptionArea );

	}

	/**
	 * An enumeration that differentiates the role of an instance of the {@link RiskDetailsPane} class.
	 */
	private enum Role {
		ADD( "Add" ), EDIT( "Edit" );

		private String displayText;

		Role( String displayText ) {
			this.displayText = displayText;
		}

		public String getText() {
			return this.displayText + " Risk";
		}
	}
}
