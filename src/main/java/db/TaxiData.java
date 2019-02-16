package db;

import java.util.Random;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import main.Settings;

public class TaxiData {
	private IntegerProperty taxiNo;
	private IntegerProperty status;
	private IntegerProperty statusIfPause;
	private IntegerProperty taxiLocation;
	private IntegerProperty clientLocation;
	private IntegerProperty clinetOrder;
	private DoubleProperty distance;
	private DoubleProperty taxiProgresInterval;

	public TaxiData(int taxiNo) {
		this.status = new SimpleIntegerProperty(1);
		this.statusIfPause = new SimpleIntegerProperty(0);
		this.taxiLocation = new SimpleIntegerProperty(Math.max(1, new Random().nextInt(Settings.initialLocationBorder)));
		this.taxiNo = new SimpleIntegerProperty(taxiNo);
		this.clientLocation = new SimpleIntegerProperty(0);
		this.clinetOrder = new SimpleIntegerProperty(0);
		this.distance = new SimpleDoubleProperty(0);
		this.taxiProgresInterval = new SimpleDoubleProperty(0);
	}

	public void setStatus(int status) {
		if (this.status.get() >= 0 || this.status.get() <= 4) {
			this.status.set(status);
		}
	}

	protected void setClinetOrder(int cL, int cO) {
		this.clientLocation.set(cL);
		this.clinetOrder.set(cO);
		this.distance.set(0.0);
		this.taxiProgresInterval.set( 1 / (Math.abs((double)getClientLocation() - (double)getTaxiLocation()) + Math.abs((double)getClientLocation() - (double)getClinetOrder())));
	}

	public void clientChangeLocationWithTaxi() {
		this.clientLocation.set(this.taxiLocation.get());
	}
	
	public void changeLocation(boolean forward) {
		if (forward) {
			this.taxiLocation.set(this.taxiLocation.get()+1);
		} else {
			this.taxiLocation.set(this.taxiLocation.get()-1);	
		}
		this.distance.set(this.distance.get() + this.taxiProgresInterval.get());
	}
	
	private String statusDescription() {
		switch (getStatus()) {
			case 1: return Settings.free;
			case 2:	return Settings.goingForClient;
			case 3: return Settings.goingWithClient;
			case 4: return Settings.paused;
			default: return Settings.turnOff;
		}
	}
	
	@Override
	public String toString() {
		return "Taxi " + (int)(this.taxiNo.get()+1) + " " + statusDescription() +
				" Positions: taxi - " + getTaxiLocation() +	" , client - " + getClientLocation() + "."+ 
				" Client's order: " + getClinetOrder();
	}
	
	public void setStatusIfPause(int status) {
		this.statusIfPause.set(status);
	}
	
	public int getTaxiNo() {
		return taxiNo.get();
	}
	
	public int getStatus() {
		return status.get();
	}
	
	public int getStatusIfPause() {
		return statusIfPause.get();
	}
	
	public int getTaxiLocation() {
		return taxiLocation.get();
	}
	
	public int getClientLocation() {
		return clientLocation.get();
	}
	
	public int getClinetOrder() {
		return clinetOrder.get();
	}
	
	public double getTaxiDistance()	{
		return distance.get();
	}
}
