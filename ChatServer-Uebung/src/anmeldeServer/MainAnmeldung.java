package anmeldeServer;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainAnmeldung extends Application
{

	public static void main(String[] args)
	{
		System.out.println("A");
		launch(args);
		new Control();
	}

	@Override
	public void start(Stage stage) throws Exception
	{
		try
		{
			//FXMLLoader fxmlLoader;
			Parent rootContainer = FXMLLoader.load(getClass().getResource("TestGui.fxml"));
			Scene scene = new Scene(rootContainer);
			stage.setTitle("Anmelde Server");
			stage.setScene(scene);
			stage.show();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}

}
