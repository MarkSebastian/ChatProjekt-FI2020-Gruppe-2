package chatClient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainClient extends Application
{
	public static void main(String[] args)
	{
		new Control();
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception
	{
		Parent LogInParent = FXMLLoader.load(getClass().getResource("Login.fxml"));
		Scene scene = new Scene(LogInParent);
		stage.setTitle("Login");
		stage.setScene(scene);
		stage.show();
		
	}
}
