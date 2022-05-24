package chatServer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainServer extends Application
{
	public static void main(String[] args)
	{
		new Control();
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception
	{
		Parent serverParent = FXMLLoader.load(getClass().getResource("Server.fxml"));
		Scene scene = new Scene(serverParent);
		stage.setTitle("Server");
		stage.setScene(scene);
		stage.show();
	}
}
