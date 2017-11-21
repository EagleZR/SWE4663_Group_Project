package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.general.risks;

import eaglezr.support.logs.LoggingTool;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.FramedPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.risks.Risk;

/**
 * Displays the information about a single {@link Risk} in the {@link ksu.fall2017.swe4663.group1.projectmanagementsystem.Project}.
 *
 * @author Mark Zeagler
 * @version 1.0
 */
public class RiskPane extends FramedPane {

	private Risk risk;

	private Label likelihoodLabel;
	private Label costLabel;
	private Label descriptionArea;

	/**
	 * Constructs a new {@link RiskPane} from the given {@link Risk}.
	 *
	 * @param risk       The {@link Risk} to be displayed by this pane.
	 * @param parentPane The {@link RisksPane} that will hold this pane.
	 * @param stage      The stage over which pop-ups will be displayed.
	 * @param config     This defines some of the physical properties and behavior of this pane.
	 */
	protected RiskPane( Risk risk, RisksPane parentPane, Stage stage, Config config ) {
		LoggingTool.print( "Constructing new RiskPane." );
		this.risk = risk;
		this.setHeight( 150 );
		this.setMaxHeight( 150 );
		this.setMinHeight( 150 );
		this.setMinWidth( 200 );

		this.setOnMouseClicked( e -> {
			LoggingTool.print( "RiskPane: Showing Edit Pane." );
			RiskDetailsPane.showEditPane( this, parentPane, stage, config );
		} );

		setup( config );
		update();
	}

	/**
	 * This sets up the pane and places each of the components in their correct positions.
	 *
	 * @param config This defines some of the physical properties and behavior of this pane.
	 */
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

	/**
	 * This updates each field to display their current value.
	 */
	protected void update() {
		this.likelihoodLabel.setText( "Likelihood: " + this.risk.getPercentageLikelihood() + "%" );
		this.costLabel.setText( "Cost: $" + this.risk.getDollarAmount() );
		this.descriptionArea.setText( this.risk.getDescription() );
	}

	/**
	 * Retrieves the {@link Risk} displayed by this pane.
	 *
	 * @return The {@link Risk} displayed by this pane.
	 */
	protected Risk getRisk() {
		return this.risk;
	}

	/**
	 * Sets a new {@link Risk} for this pane to display.
	 *
	 * @param risk The new {@link Risk} to display.
	 */
	protected void setRisk( Risk risk ) { // TODO Should the risk be edited instead of discarding it?
		LoggingTool.print( "RiskPane: Setting new Risk." );
		this.risk = risk;
		update();
	}
}
