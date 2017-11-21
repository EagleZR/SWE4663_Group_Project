package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.general.risks;

import eaglezr.support.logs.LoggingTool;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.ProjectPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.FramedPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.risks.Risk;

import java.util.LinkedList;

/**
 * A pane which displays all of the {@link Risk}s of a {@link Project} through {@link RiskPane}s.
 *
 * @author Mark Zeagler
 * @version 1.0
 */
public class RisksPane extends FramedPane implements ProjectPane {

	private Project project;
	private Stage stage;
	private Config config;
	private LinkedList<RiskPane> riskPanes;
	private Pane contentPane;

	/**
	 * Constructs a {@link RisksPane} using the {@link Risk}s of the given {@link Project}.
	 *
	 * @param project The project currently being viewed/edited.
	 * @param stage   The stage over which pop-ups will be displayed.
	 * @param config  This defines some of the physical properties and behavior of this pane.
	 */
	public RisksPane( Project project, Stage stage, Config config ) {
		LoggingTool.print( "Constructing new RisksPane." );
		this.project = project;
		this.stage = stage;
		this.config = config;
		this.riskPanes = new LinkedList<>();
		setup( stage, config );
		update();
	}

	/**
	 * This sets up the pane and places each of the components in their correct positions.
	 *
	 * @param stage  The stage over which pop-ups will be displayed.
	 * @param config This defines some of the physical properties and behavior of this pane.
	 */
	private void setup( Stage stage, Config config ) {
		// Risk Label
		Label riskLabel = new Label( "Risks: " );
		riskLabel.layoutXProperty().setValue( config.buffer );
		riskLabel.layoutYProperty().setValue( config.buffer );
		this.getChildren().add( riskLabel );

		// Add Risk button
		Button addRiskButton = new Button( "Add Risk" );
		addRiskButton.layoutXProperty()
				.bind( this.widthProperty().subtract( addRiskButton.widthProperty() ).subtract( config.buffer ) );
		addRiskButton.layoutYProperty()
				.bind( this.heightProperty().subtract( addRiskButton.heightProperty() ).subtract( config.buffer ) );
		addRiskButton.setOnAction( e -> RiskDetailsPane.showAddPane( this, stage, config ) );
		this.getChildren().add( addRiskButton );

		// ContentPane
		this.contentPane = new Pane();

		// ScrollPane
		ScrollPane scrollPane = new ScrollPane( this.contentPane );
		scrollPane.layoutXProperty().setValue( config.buffer );
		scrollPane.layoutYProperty()
				.bind( riskLabel.layoutYProperty().add( riskLabel.heightProperty() ).add( config.buffer / 2 ) );
		scrollPane.prefWidthProperty().bind( this.widthProperty().subtract( config.buffer * 2 ) );
		scrollPane.prefHeightProperty().bind( addRiskButton.layoutYProperty().subtract( scrollPane.layoutYProperty() )
				.subtract( config.buffer ) );
		scrollPane.setHbarPolicy( ScrollPane.ScrollBarPolicy.NEVER );
		scrollPane.setVbarPolicy( ScrollPane.ScrollBarPolicy.ALWAYS );
		scrollPane.setFitToWidth( true );
		this.getChildren().add( scrollPane );
	}

	/**
	 * This updates each field to display their current value.
	 */
	protected void update() {
		// Flush and re-add in descending order
		// Simpler way of keeping things ordered when the values affecting the order can be directly edited
		this.contentPane.getChildren().clear();

		for ( Risk risk : this.project.getRisks() ) {
			boolean hasRisk = false;
			for ( RiskPane riskPane : this.riskPanes ) {
				if ( riskPane.getRisk().equals( risk ) ) {
					hasRisk = true;
				}
			}
			if ( !hasRisk ) {
				this.riskPanes.add( new RiskPane( risk, this, this.stage, this.config ) );
			}
		}

		while ( !this.contentPane.getChildren().containsAll( this.riskPanes ) ) {
			RiskPane riskiestRisk = this.riskPanes.get( 0 );
			double mostRisk = riskiestRisk.getRisk().getPercentageLikelihood();
			for ( RiskPane risk : this.riskPanes ) {
				if ( !this.contentPane.getChildren().contains( risk )
						&& risk.getRisk().getPercentageLikelihood() >= mostRisk ) {
					mostRisk = risk.getRisk().getDollarAmount();
					riskiestRisk = risk;
				}
			}
			riskiestRisk.layoutXProperty().unbind();
			riskiestRisk.layoutYProperty().unbind();
			if ( this.contentPane.getChildren().size() == 0 ) {
				riskiestRisk.layoutXProperty().setValue( 0 );
				riskiestRisk.layoutYProperty().setValue( 0 );
			} else {
				RiskPane previous = (RiskPane) this.getChildren().get( this.getChildren().size() - 1 );
				riskiestRisk.layoutXProperty().bind( previous.layoutXProperty() );
				riskiestRisk.layoutYProperty().bind( previous.layoutYProperty().add( previous.heightProperty() ) );
			}
			this.contentPane.getChildren().add( riskiestRisk );
		}
	}

	/**
	 * Adds the given {@link RiskPane} to the displayed list.
	 *
	 * @param risk The new {@link RiskPane} to be displayed.
	 */
	protected void addRisk( RiskPane risk ) {
		this.riskPanes.add( risk );
		this.project.getRisks().add( risk.getRisk() );
		update();
	}

	/**
	 * Removes the indicated {@link RiskPane} from the list.
	 *
	 * @param risk The {@link RiskPane} to be removed.
	 */
	protected void removeRisk( RiskPane risk ) {
		this.riskPanes.remove( risk );
		this.project.getRisks().remove( risk.getRisk() );
		update();
	}

	@Override public void loadNewProject( Project project ) {
		LoggingTool.print( "RisksPane: Loading new project." );
		this.project = project;
		update();
	}
}
