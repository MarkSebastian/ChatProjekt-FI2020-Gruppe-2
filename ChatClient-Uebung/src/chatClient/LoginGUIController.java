package chatClient;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginGUIController extends Control
{
	@FXML
	private ImageView profile_picture;
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private Button btn_einloggen;
	@FXML
	private Hyperlink btn_neuesKonto;
	
	@FXML
	protected void buttonOnClick()
	{
		super.login(username.getText(), password.getText(), false);
		if (super.getErfolgreich() == true)
		{
			try
			{
				erfolgreicherLogin();
				super.setErfolgreich(false);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
		}
		else 
		{
			makeAlertWarnig(getFehlerMeldungString());
		}
	}

	@FXML
	protected void labelOnClick()
	{
		sceneChange(false);
	}

	private void sceneChange(boolean b)
	{
		Stage stageEventChangeStage = (Stage) btn_neuesKonto.getScene().getWindow();

		if (b == false)
		{
			try
			{
				stageEventChangeStage.setScene(super.changeScene(true));
				stageEventChangeStage.setTitle("Registrieren");
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
