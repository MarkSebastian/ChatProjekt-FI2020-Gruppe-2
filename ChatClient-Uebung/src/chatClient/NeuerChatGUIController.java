package chatClient;

import java.awt.Label;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class NeuerChatGUIController extends Control
{
	@FXML
	private Button btnUserHinzufügen;

	@FXML
	protected void sceneChange()
	{
		Stage stageEventChangeStage = (Stage) btnUserHinzufügen.getScene().getWindow();
		try
		{
			stageEventChangeStage.setScene(super.backToZero());
			stageEventChangeStage.setTitle("Chat Client FI11");
			stageEventChangeStage.show();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
