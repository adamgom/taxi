package gui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public enum GuiScreens {
	INITAL("TaxiInitialPane", false),
	ALL("AllTaxisPane", true);
	
	private FXMLLoader screenLoader;
	private Pane pane;
	private boolean isController;
	
	private GuiScreens(String guiFXMLresource, boolean isController) {
		this.isController = isController;
		this.screenLoader = new FXMLLoader(this.getClass().getResource("/fxml/" + guiFXMLresource + ".fxml"));
		if (!isController) try {this.pane = screenLoader.load();} catch (IOException e) {}
	}
	
	public Pane getPane() {
		return this.pane;
	}
	
	public void FXMLLoad() {
		if (isController) try {this.pane = screenLoader.load();} catch (IOException e) {}
	}
	
	public FXMLLoader getLoader() {
		if (isController) {
			return screenLoader;
		} else {
			return null;
		}
	}
}
