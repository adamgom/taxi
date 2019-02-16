package main;

public class Settings {
	public static final String DbUrl = "jdbc:mysql://localhost?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	public static final String dbUrlEnd = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	public static final String dbUrl = "jdbc:mysql://localhost" + dbUrlEnd;
	public static final String user = "root";
	public static final String password = "7diablo7";
	public static final String dbNameSQL = "abc";
	
	public static final String MainWindowPaneFXML = "/fxml/MainWindowPane.fxml";
	
	public static final String free = "free, waiting for next client.";
	public static final String goingForClient = "busy, going to pick up clien.";
	public static final String goingWithClient = "busy, going with client to it's destination.";
	public static final String freeAgain = "free, delivered client to it's destination, waiting for next order.";
	public static final String turnOff= "turned off.";
	public static final String paused = "paused.";
	
	public static final int interval = 100;
	public static final int speed = 1000 - interval;
	public static final int initialLocationBorder = 10;
}
