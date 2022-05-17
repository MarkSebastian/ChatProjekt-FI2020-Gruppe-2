package chatClient;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;

import com.gluonhq.charm.glisten.control.Avatar;

public class LoginGUIController extends Control
{
	@FXML
	private Avatar avatarIcon;
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
		super.login(anmeldenField.getText(),passwordField.getText(),false);
	}
	
	@FXML
	protected void labelOnClick()
	{
			Stage stageEventChangeStage = (Stage) regestrierenLabel.getScene().getWindow();
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
}
