package gui.controllers;

import db.TaxiData;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import main.Engine;

public class AllTaxisController {
	@FXML private ListView<TaxiData> listView;
	
	@FXML
	private void initialize() {
		this.listView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> Engine.getInstance().setSelectedTaxi(this.listView.getSelectionModel().getSelectedIndex()));
	}
	
	protected void bindListProperty() {
		this.listView.itemsProperty().bind(Engine.getInstance().getListProperty());
	}
	
	protected void refreshViewList() {
		this.listView.refresh();
	}
}
