package main;

import java.util.concurrent.locks.ReentrantLock;
import db.TaxiDataList;

public class TaxiCar implements TaxiRunnable {
	private int tN;
	private ReentrantLock dataLock;
	private TaxiDataList tdl;

	public TaxiCar(int taxiNumber, ReentrantLock dataLock) {
		this.tdl = Engine.getInstance().getTaxiDataList();
		this.dataLock = dataLock;
		this.tdl.addTaxiData(taxiNumber);
		this.tN = taxiNumber;
	}
	
	public void run() {
		while (true) {
			dataLock.lock();
			if (tdl.gS(tN) == 1 || tdl.gS(tN) == 4) {
				dataLock.unlock();
			} else if (tdl.gS(tN) == 0) {
				dataLock.unlock();
				break;
			} else {
				tdl.changeLocation(tN);
				dataLock.unlock();
				try {Thread.sleep(Settings.speed);} catch (InterruptedException e) {}
			}
			try {Thread.sleep(Settings.interval);} catch (InterruptedException e) {}			
		}
	}
	
	@Override
	public void powerOfTaxi() {
		dataLock.lock();
		tdl.turnOff(tN, false);
		dataLock.unlock();
	}

	@Override
	public void pauseRestartTaxi() {
		dataLock.lock();
		tdl.pauseRestartTaxi(tN);
		dataLock.unlock();
	}
	
	@Override
	public void setClinetOrder(int cL, int cO) {
		dataLock.lock();
		tdl.setClinetOrder(this.tN, cL, cO);
		dataLock.unlock();
	}
}
