package chatClient;

import java.io.IOException;

import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SignInGUIController extends Control
{
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private PasswordField password1;
	@FXML
	private Button btn_einloggen;
	@FXML
	private Hyperlink btn_neuesKonto;

	@FXML
	protected void buttonOnClick()
	{
		//TO-DO: Fehlerbox Leerer Text
		if (password.getText().compareTo(password1.getText()) == 0)
		{
			super.login(username.getText(), password.getText(), true);
			if (super.getErfolgreich() == true)
			{
				sceneChange();
				super.setErfolgreich(false);
			}
			else if (super.getErfolgreich() == false)
			{
					makeAlertWarnig();	
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

	private void sceneChange()
	{
		Stage stageEventChangeStage = (Stage) btn_neuesKonto.getScene().getWindow();
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

	private void makeAlertWarnig()
	{
		String message = "Fehler bei der Registrierung";
		
		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.show();
	}
}
