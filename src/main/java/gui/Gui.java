package gui;

import java.io.IOException;

import gui.controllers.MainWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Settings;

public class Gui {
	private FXMLLoader mainWindowLoader;
	private Scene scene;
	
	public Gui(Stage primaryStage) throws IOException {
		this.mainWindowLoader = new FXMLLoader(this.getClass().getResource(Settings.MainWindowPaneFXML));
		this.mainWindowLoader.setController(new MainWindowController());
//		BorderPane borderPane = mainWindowLoader.load();
		
		this.scene = new Scene(mainWindowLoader.load());
//		scene.getStylesheets().add("/css/styles.css");
		primaryStage.setScene(scene);
		primaryStage.setTitle("Taxi dispatcher");
		primaryStage.show();
		
//		primaryStage.setFullScreen(false);
//		primaryStage.setResizable(false);
//		primaryStage.setFullScreen(true);
//		primaryStage.setX(50);
//		primaryStage.setY(50);
//		primaryStage.initStyle(StageStyle.UNDECORATED);
//		primaryStage.setOpacity(.8);
//		primaryStage.setWidth(700);
//		primaryStage.setHeight(500);
//		primaryStage.setScene(new MainScene());
//		primaryStage.show();
		
	}
}
