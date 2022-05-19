package chatClient;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginGUIController extends Control
{
	@FXML
	private TextField anmeldenField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Button anmeldenButton;
	@FXML
	private Label regestrierenLabel;

	@FXML
	protected void buttonOnClick()
	{
		super.startConnect();
		super.login(anmeldenField.getText(), passwordField.getText(), false);
		super.run();
		if (super.getErfolgreich() == true)
		{
			sceneChange(true);
			super.setErfolgreich(false);
		}
	}

	@FXML
	protected void labelOnClick()
	{

		if (super.read != null)
		{
			if (super.read.isInterrupted() == false)
			{
				super.read.interrupt();
				sceneChange(false);
			}
			else
			{
				sceneChange(false);
			}
		}
		else
		{
			sceneChange(false);
		}
	}

	@FXML
	protected void labelOnHover()
	{
		regestrierenLabel.setTextFill(Color.BLUE);
	}

	@FXML
	protected void labelOnLeave()
	{
		regestrierenLabel.setTextFill(Color.BLACK);
	}

	private void sceneChange(boolean b)
	{
		Stage stageEventChangeStage = (Stage) regestrierenLabel.getScene().getWindow();

		if (b == false)
		{
			try
			{
				stageEventChangeStage.setScene(super.changeScene(true));
				stageEventChangeStage.setTitle("Sign Up");
				stageEventChangeStage.show();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			try
			{
				System.out.println("Klappt");
				// stageEventChangeStage.setScene(super.erfolgreicherLogin());
				// stageEventChangeStage.setTitle("Debug");
				// stageEventChangeStage.show();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	private void makeAlert(String messang)
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText(messang);
		alert.show();
	}
}
