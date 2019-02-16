package db;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Engine;

public class TaxiDataList {
	private List<TaxiData> taxiDatas;
	private ObservableList<TaxiData> observableList;
	private ListProperty<TaxiData> listProperty;

	public TaxiDataList() {
		this.taxiDatas = new ArrayList<>();
		this.listProperty = new SimpleListProperty<>();
	}

	public void addTaxiData(int taxiNo) {
		this.taxiDatas.add(new TaxiData(taxiNo));
	}

	public void setList() {
		this.observableList = FXCollections.observableArrayList(this.taxiDatas);
		this.listProperty.set(this.observableList);
	}

	public void	setClinetOrder(int tN, int cL, int cO) {
		gT(tN).setClinetOrder(cL, cO);
		switchStatus(tN);
	}

	public void changeLocation(int tN) {
		switch (gS(tN)) {
			case 1:
				switchStatus(tN);
				break;
			case 2:
				gT(tN).changeLocation(gCL(tN) > gTL(tN));
				switchStatus(tN);
				break;
			case 3:
				gT(tN).changeLocation(gCO(tN) > gTL(tN));
				gT(tN).clientChangeLocationWithTaxi();
				switchStatus(tN);
				break;
			default:
				break;
		}
		Engine.getInstance().refreshListProperty();
		if (Engine.getInstance().getSelectedTaxi() == tN) Engine.getInstance().updateProgressBar();
	}
	
	public void switchStatus(int tN) {
		if ( gCL(tN) == gCO(tN) ) gT(tN).setStatus(1);
		if ( gCL(tN) != gTL(tN) && gCL(tN) != gCO(tN) ) gT(tN).setStatus(2);
		if ( gTL(tN) != gCO(tN) && gCL(tN) == gTL(tN) ) gT(tN).setStatus(3);
	}
	
	public void switchStatus(int tN, boolean stop) {
		switchStatus(tN);
		if ( stop ) gT(tN).setStatus(0);
	}
	
	public void turnOff(int tN, boolean turnOffAllTaxis) {
		if (turnOffAllTaxis) {
			for (int i = 0 ; i <= this.taxiDatas.size() ; i++ ) {
				switchStatus(i, true);
			}
		} else {
			switchStatus(tN, true);
		}
	}
	
	public void pauseRestartTaxi(int tN) {
		if (gSIP(tN) == 0) {
			gT(tN).setStatusIfPause(gS(tN));
			gT(tN).setStatus(4);
		} else {
			gT(tN).setStatus(gSIP(tN));
			gT(tN).setStatusIfPause(0);
		}
		Engine.getInstance().refreshListProperty();
	}

	public int gS(int tN) {
		return this.taxiDatas.get(tN).getStatus();
	}

	public int gSIP(int tN) {
		return this.taxiDatas.get(tN).getStatusIfPause();
	}

	public int gTL(int tN) {
		return this.taxiDatas.get(tN).getTaxiLocation();
	}

	public int gCO(int tN) {
		return this.taxiDatas.get(tN).getClinetOrder();
	}

	public int gCL(int tN) {
		return this.taxiDatas.get(tN).getClientLocation();
	}

	public double gTD(int tN) {
		return this.taxiDatas.get(tN).getTaxiDistance();
	}

	public TaxiData gT(int tN) {
		return this.taxiDatas.get(tN);
	}

	public ListProperty<TaxiData> getListProperty() {
		return this.listProperty;
	}
}
