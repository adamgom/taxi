package gui.controllers;

import db.TaxiData;
import gui.GuiScreens;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import main.Engine;

public class MainWindowController {
	private AllTaxisController allTaxisController;
	private boolean[] sendCheck = new boolean[2];
	
	@FXML private HBox centralBorderPane;
	@FXML private HBox bottomBorderPane;
	@FXML private Button buttonStart, buttonPause, buttonExit, buttonRestart, buttonSend;
	@FXML private TextField noOfTaxisInput, clientLocationInput, clientDestinationInput;
	@FXML private ListView<TaxiData> listViewSelect;
	@FXML private Label statusTaxiLabel, selectedTaxiLabel;
	@FXML private ProgressBar progressBar;
	
	@FXML
	void initialize() {
		this.buttonExit.addEventHandler(ActionEvent.ACTION, (event) -> exitButtonAction());
		this.buttonPause.addEventHandler(ActionEvent.ACTION, (event) -> pauseButtonAction());
		this.buttonRestart.addEventHandler(ActionEvent.ACTION, (event) -> restartButtonAction());
		this.buttonStart.addEventHandler(ActionEvent.ACTION, (event) -> startButtonAction());
		this.buttonSend.addEventHandler(ActionEvent.ACTION, (event) -> sendButtonAction());
		this.noOfTaxisInput.textProperty().addListener((Observable, oldValue, newValue) -> buttonStart.setDisable(!newValue.matches("[0-9]{2}|[1-9]{1}")));
		this.clientLocationInput.textProperty().addListener((Observable, oldValue, newValue) -> {this.sendCheck[0] = newValue.matches("[0-9]{2}|[1-9]{1}");activeSendButton(true);});
		this.clientDestinationInput.textProperty().addListener((Observable, oldValue, newValue) -> {this.sendCheck[1] = newValue.matches("[0-9]{2}|[1-9]{1}");activeSendButton(true);});
		
		this.allTaxisController = new AllTaxisController();
		GuiScreens.ALL.getLoader().setController(this.allTaxisController);
		GuiScreens.ALL.FXMLLoad();
		this.noOfTaxisInput.setText("2");
		setPane(GuiScreens.INITAL.getPane());
		startSettings();
		startButtonAction();
	}
	
	private void startSettings() {
		this.buttonStart.setText("Start");
		this.buttonPause.setDisable(true);
		this.buttonStart.setDisable(true);
		this.buttonSend.setDisable(true);
		this.noOfTaxisInput.setDisable(false);
	}

	private void startButtonAction() {
		this.buttonPause.setDisable(false);
		this.buttonStart.setDisable(true);
		this.buttonRestart.setDisable(true);

		if (buttonStart.getText().equals("Start")) {
			this.noOfTaxisInput.setDisable(true);
			setPane(GuiScreens.ALL.getPane());
	    	Engine.setInstance(Integer.parseInt(noOfTaxisInput.getText()), this);
	    	Engine.getInstance().startAllTaxis();
	    	Engine.getInstance().getTaxiDataList().setList();
	    	this.allTaxisController.bindListProperty();	
	    	this.selectedTaxiLabel.setText("select taxi");
		} else {
			Engine.getInstance().pauseRestartAllTaxis();	
		}
	}

	private void pauseButtonAction() {
		this.buttonPause.setDisable(true);
		this.buttonStart.setDisable(false);
		this.buttonStart.setText("Wznów");
		this.buttonRestart.setDisable(false);
		Engine.getInstance().pauseRestartAllTaxis();
	}
	
	private void restartButtonAction() {
		startSettings();
		Engine.getInstance().stopAllTaxis();
		setPane(GuiScreens.INITAL.getPane());
		Engine.getInstance().resetEngine();
		this.selectedTaxiLabel.setText("Restarted");
		this.noOfTaxisInput.clear();
	}
	
	private void exitButtonAction() {
		if ( Engine.getInstance() != null ) Engine.getInstance().stopAllTaxis();
		Platform.exit();
	}
	
	public void activeSendButton(boolean activate) {
			this.buttonSend.setDisable(!(activate && this.sendCheck[0] && this.sendCheck[1]));
	}
	
	public void setProgressBar(int selectedTaxi) {
		this.progressBar.setProgress(Engine.getInstance().getTaxiDataList().gTD(selectedTaxi));
	}
	
	public void setTaxiName(int taxiNo) {
		this.selectedTaxiLabel.setText("Selected Taxi: " + taxiNo);
	}
	
	private void sendButtonAction() {
		Engine.getInstance().getTaxi(Engine.getInstance().getSelectedTaxi()).setClinetOrder(Integer.parseInt(clientLocationInput.getText()), Integer.parseInt(clientDestinationInput.getText()));
		activeSendButton(false);
		this.clientDestinationInput.clear();
		this.clientLocationInput.clear();
	}
	
	public void refreshViewList() {
		this.allTaxisController.refreshViewList();
	}
	
	private void setPane(Pane pane) {
		this.centralBorderPane.getChildren().clear();
		this.centralBorderPane.getChildren().add(pane);
	}
}
