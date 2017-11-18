package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.general;

import javafx.scene.control.Label;
import javafx.stage.Stage;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.FramedPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.risks.Risk;

public class RiskPane extends FramedPane {

	private Risk risk;

	private Label likelihoodLabel;
	private Label costLabel;
	private Label descriptionArea;

	protected RiskPane( Risk risk, RisksPane parentPane, Stage stage, Config config ) {
		this.risk = risk;
		this.setHeight( 150 );
		this.setMaxHeight( 150 );
		this.setMinHeight( 150 );
		this.setMinWidth( 200 );

		this.setOnMouseClicked( e -> {
			RiskDetailsPane.showEditPane( this, parentPane, stage, config );
		} );

		setup( config );
		update();
	}

	private void setup( Config config ) {
		// Likelihood label
		this.likelihoodLabel = new Label( "Likelihood: " );
		this.likelihoodLabel.layoutXProperty().setValue( config.buffer );
		this.likelihoodLabel.layoutYProperty().setValue( config.buffer );
		this.getChildren().add( this.likelihoodLabel );

		// Cost label
		this.costLabel = new Label( "Cost: " );
		this.costLabel.layoutXProperty()
				.bind( this.widthProperty().subtract( this.costLabel.widthProperty() ).subtract( config.buffer ) );
		this.costLabel.layoutYProperty().bind( this.likelihoodLabel.layoutYProperty() );
		this.getChildren().add( this.costLabel );

		// Description label
		Label descriptionLabel = new Label( "Description: " );
		descriptionLabel.layoutXProperty().bind( this.likelihoodLabel.layoutXProperty() );
		descriptionLabel.layoutYProperty()
				.bind( this.likelihoodLabel.layoutYProperty().add( this.likelihoodLabel.heightProperty() )
						.add( config.buffer ) );
		this.getChildren().add( descriptionLabel );

		// Description area
		this.descriptionArea = new Label( "" );
		this.descriptionArea.layoutXProperty().bind( descriptionLabel.heightProperty() );
		this.descriptionArea.layoutYProperty()
				.bind( descriptionLabel.layoutYProperty().add( descriptionLabel.heightProperty() )
						.add( config.buffer / 2 ) );
		this.descriptionArea.setWrapText( true );
		this.getChildren().add( this.descriptionArea );
	}

	protected void update() {
		this.likelihoodLabel.setText( "Likelihood: " + this.risk.getPercentageLikelihood() + "%" );
		this.costLabel.setText( "Cost: $" + this.risk.getDollarAmount() );
		this.descriptionArea.setText( this.risk.getDescription() );
	}

	protected Risk getRisk() {
		return this.risk;
	}

	protected void setRisk( Risk risk ) {
		this.risk = risk;
		update();
	}
}
