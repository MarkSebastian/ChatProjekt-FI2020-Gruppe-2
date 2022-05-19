package chatClient;

import java.io.IOException;

import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SignInGUIController extends Control
{
	@FXML
	private TextField anmeldenField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private PasswordField passwordFieldBestaetigung;
	@FXML
	private Label anmeldeLabel;

	@FXML
	protected void buttonOnClick()
	{
		if (passwordField.getText().compareTo(passwordFieldBestaetigung.getText()) == 0)
		{
			super.startConnect();
			super.login(anmeldenField.getText(), passwordField.getText(), true);
			super.run();
			if (super.getErfolgreich() == true)
			{
				sceneChange();
				super.setErfolgreich(false);
			}
		}
		else
		{
			makeAlert("Die Passwörter stimmen nicht überein");
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
				sceneChange();
			}
			else
			{
				sceneChange();
			}
		}
		else
		{
			sceneChange();
		}

	}

	@FXML
	protected void labelOnHover()
	{
		anmeldeLabel.setTextFill(Color.BLUE);
	}

	@FXML
	protected void labelOnLeave()
	{
		anmeldeLabel.setTextFill(Color.BLACK);
	}

	private void sceneChange()
	{
		Stage stageEventChangeStage = (Stage) anmeldeLabel.getScene().getWindow();
		try
		{
			stageEventChangeStage.setScene(super.changeScene(false));
			stageEventChangeStage.setTitle("Login");
			stageEventChangeStage.show();
		}
		catch (IOException e)
		{
			e.printStackTrace();
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
