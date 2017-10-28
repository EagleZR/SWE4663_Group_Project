package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.general;

import eaglezr.javafx.stages.PopupStage;
import eaglezr.support.errorsystem.ErrorPopupSystem;
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

public class RiskDetailsPane extends Pane {

	private Role role;

	private Risk risk;

	private TextField likelihoodField;
	private TextField costField;
	private TextArea descriptionArea;

	private Button commitButton;
	private Button removeButton;
	private Button cancelButton;

	private RiskDetailsPane( Config config ) {
		this.role = Role.ADD;
		setup( config );
	}

	private RiskDetailsPane( Risk risk, Config config ) {
		this.role = Role.EDIT;
		this.risk = risk;
		setup( config );
	}

	public static void showAddPane( RisksPane risksPane, Stage stage, Config config ) {
		RiskDetailsPane detailsPane = new RiskDetailsPane( config );
		detailsPane.commitButton.setOnAction( e -> {
			try {
				Risk newRisk = new Risk( detailsPane.descriptionArea.getText(),
						Double.parseDouble( detailsPane.costField.getText() ),
						Double.parseDouble( detailsPane.likelihoodField.getText() ) );
				risksPane.addRisk( new RiskPane( newRisk, risksPane, stage, config ) );
				detailsPane.cancelButton.fire();
			} catch ( InvalidPercentageException | InvalidDollarAmountException exc ) {
				ErrorPopupSystem.displayMessage( exc.getMessage() );
			}
		} );
		showPane( detailsPane, stage );
	}

	public static void showEditPane( RiskPane riskPane, RisksPane risksPane, Stage stage, Config config ) {
		Risk risk = riskPane.getRisk();
		RiskDetailsPane detailsPane = new RiskDetailsPane( risk, config );

		detailsPane.commitButton.setOnAction( e -> {
			try {
				Risk newRisk = new Risk( detailsPane.descriptionArea.getText(),
						Double.parseDouble( detailsPane.costField.getText() ),
						Double.parseDouble( detailsPane.likelihoodField.getText() ) );
				riskPane.setRisk( newRisk );
				risksPane.update();
				detailsPane.cancelButton.fire();
			} catch ( InvalidPercentageException | InvalidDollarAmountException exc ) {
				ErrorPopupSystem.displayMessage( exc.getMessage() );
			}
		} );

		detailsPane.removeButton.setOnAction( e -> {
			risksPane.removeRisk( riskPane );
			detailsPane.cancelButton.fire();
		} );
		showPane( detailsPane, stage );
	}

	private static void showPane( RiskDetailsPane detailsPane, Stage stage ) {
		Scene scene = new Scene( detailsPane );
		PopupStage popupStage = new PopupStage( scene, stage );
		detailsPane.cancelButton.setOnAction( e -> popupStage.close() );
		popupStage.setTitle( detailsPane.role.getText() );
		popupStage.setWidth( 300 );
		popupStage.setHeight( 400 );
		popupStage.setResizable( false );
		popupStage.show();
	}

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

	private enum Role {
		ADD( "Add Risk" ), EDIT( "Edit Risk" );

		private String displayText;

		Role( String displayText ) {
			this.displayText = displayText;
		}

		public String getText() {
			return this.displayText;
		}
	}
}
