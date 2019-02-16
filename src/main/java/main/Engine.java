package main;

import java.util.concurrent.locks.ReentrantLock;

import db.TaxiData;
import db.TaxiDataList;
import gui.controllers.MainWindowController;
import javafx.beans.property.ListProperty;

public class Engine {
	public static Engine instance;
	public TaxiRunnable[] taxiFleet;
	private TaxiDataList taxiDataList;
	ReentrantLock dataLock;
	private MainWindowController mainWindowController;
	private int selectedTaxi;
	
	private Engine(int noOfTaxis, MainWindowController mainWindowController) {
		this.taxiFleet = new TaxiRunnable[noOfTaxis];
		taxiDataList = new TaxiDataList();
		this.dataLock = new ReentrantLock();
		this.mainWindowController = mainWindowController;
		this.selectedTaxi = 0;
	}
	
	public static Engine setInstance(int noOfTaxis, MainWindowController mainWindowController) {
		if (instance == null) {
			instance = new Engine(noOfTaxis, mainWindowController);
			return null;
		} else {
			return instance;
		}
	}
	
	public static Engine getInstance() {
		if (instance == null) {
			return null;
		} else {
			return instance;
		}
	}
	
	public void startTaxi(int taxiNo) {
		try {
			taxiFleet[taxiNo] = new TaxiCar(taxiNo, this.dataLock);
			new Thread(taxiFleet[taxiNo]).start();
		} catch (Exception e) {}
	}
	
	public void startAllTaxis() {
		for (int i = 0 ; i < this.taxiFleet.length ; i++ )	{
			startTaxi(i);
    	}
	}
	
	public void stopTaxi(int taxiNo) {
		taxiFleet[taxiNo].powerOfTaxi();
	}

	public void stopAllTaxis() {
		for (int i = 0 ; i < taxiFleet.length; i++ )	{
    		taxiFleet[i].powerOfTaxi();
    	}
	}
	
	public TaxiRunnable getTaxi(int noOFTaxi) {
		return taxiFleet[noOFTaxi];
	}

	public void pauseRestartAllTaxis() {
		for (int i = 0 ; i < taxiFleet.length; i++ )	{
    		this.taxiFleet[i].pauseRestartTaxi();
    	}
	}
	
	public void resetEngine() {
		stopAllTaxis();
		instance = null;
	}
	
	public TaxiDataList getTaxiDataList() {
		return taxiDataList;
	}
	
	public ListProperty<TaxiData> getListProperty() {
		return taxiDataList.getListProperty();
	}
	
	public void refreshListProperty() {
		this.mainWindowController.refreshViewList();
	}
	
	public void setSelectedTaxi(int selectedTaxi) {
		this.selectedTaxi = selectedTaxi;
		this.mainWindowController.setTaxiName(selectedTaxi+1);
		this.mainWindowController.activeSendButton(this.taxiDataList.gT(this.selectedTaxi).getStatus() == 1);
		this.mainWindowController.setProgressBar(selectedTaxi);
	}
	
	public void updateProgressBar() {
		this.mainWindowController.setProgressBar(this.selectedTaxi);
	}
	
	public int getSelectedTaxi() {
		return this.selectedTaxi;
	}
}
