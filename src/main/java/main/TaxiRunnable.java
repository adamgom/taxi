package main;

public interface TaxiRunnable extends Runnable {
	public void setClinetOrder(int clientLocation, int clientOrder);
	public void pauseRestartTaxi();
	public void powerOfTaxi();
}
