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

public class SignInGUIController extends Control implements Runnable
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

	private Thread thread;

	@FXML
	protected void buttonOnClick()
	{
		thread = new Thread(this);
		thread.run();
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

	private void makeAlertInformation(String message)
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.show();
	}

	private void makeAlertWarnig(String message)
	{
		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.show();
	}

	@Override
	public void run()
	{
		do
		{
			try
			{
				Thread.sleep(100);
				if (password.getText().compareTo(password1.getText()) == 0)
				{
					super.login(username.getText(), password.getText(), true);
					if (super.getErfolgreich() == true)
					{
						sceneChange();
						super.setErfolgreich(false);
						thread.interrupt();
					}
					else if (super.getErfolgreich() == false)
					{
						System.out.println(getErfolgreich() + "In Sign Up");
						makeAlertWarnig(getFehlerMeldungString());
					}
				}
				else
				{
					makeAlertInformation("Die Passwörter stimmen nicht überein");
				}
			}
			catch (InterruptedException e)
			{
				Thread.interrupted();
				e.printStackTrace();
			}
		} while (super.isNachrichtEingegangen() == false);
		super.setErfolgreich(false);
	}
}
