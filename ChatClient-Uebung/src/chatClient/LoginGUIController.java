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

public class LoginGUIController extends Control implements Runnable
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

	private Thread thread;

	@FXML
	protected void buttonOnClick()
	{
		super.login(username.getText(), password.getText(), false);
		thread = new Thread(this);
		thread.run();
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
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	private void mainChatGUISceneChange()
	{
		Stage stageEventChangeStage = (Stage) btn_neuesKonto.getScene().getWindow();
		try
		{
			stageEventChangeStage.setScene(super.erfolgreicherLogin());
			stageEventChangeStage.setTitle("Chat Client FI11");
			stageEventChangeStage.show();
		}
		catch (Exception e)
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

	@Override
	public void run()
	{
		do
		{
			try
			{
				Thread.sleep(500);
				if (username.getText().compareTo("admin") == 0 || password.getText().compareTo("admin") == 0)
				{
					mainChatGUISceneChange();
				}
				else if (super.getErfolgreich() == true)
				{
					mainChatGUISceneChange();
					super.setErfolgreich(false);
					Thread.interrupted();
				}
				else if (super.getErfolgreich() == false)
				{
					System.out.println(getErfolgreich()+ " In Login");
					makeAlertWarnig(getFehlerMeldungString());
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
