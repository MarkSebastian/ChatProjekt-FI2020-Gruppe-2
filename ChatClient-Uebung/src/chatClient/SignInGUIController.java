package chatClient;

import java.io.IOException;

import com.gluonhq.charm.glisten.control.Avatar;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
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
	private Avatar avatarIcon;
	@FXML
	private Label anmeldeLabel;

	@FXML
	protected void labelOnClick()
	{
			Stage stageEventChangeStage = (Stage) anmeldeLabel.getScene().getWindow();
			try
			{
				stageEventChangeStage.setScene(super.changeScene(false));
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
		anmeldeLabel.setTextFill(Color.BLUE);
	}
	
	@FXML
	protected void labelOnLeave()
	{
		anmeldeLabel.setTextFill(Color.BLACK);
	}
}
