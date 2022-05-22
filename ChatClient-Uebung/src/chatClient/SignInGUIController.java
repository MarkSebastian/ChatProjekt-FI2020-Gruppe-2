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
			super.login(anmeldenField.getText(), passwordField.getText(), true);
			if (super.getErfolgreich() == true)
			{
				sceneChange();
				super.setErfolgreich(false);
			}
			else
			{
				makeAlertWarnig(getFehlerMeldungString());
			}
		}
		else
		{
			makeAlertInformation("Die Passwörter stimmen nicht überein");
		}
	}

	@FXML
	protected void labelOnClick()
	{
		sceneChange();
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

	private void makeAlertInformation(String messang)
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText(messang);
		alert.show();
	}
	
	private void makeAlertWarnig(String messang)
	{
		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText(null);
		alert.setContentText(messang);
		alert.show();
	}
}
