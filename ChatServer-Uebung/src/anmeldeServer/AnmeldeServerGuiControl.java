package anmeldeServer;


import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AnmeldeServerGuiControl extends Control
{
	
	
	@FXML
	private Button startButton;
	private Button stopButton;
	
	@FXML
	protected void startenOnClick()
	{
		System.out.println("A");
		super.anmeldeServerStarten();
	}
	
	@FXML
	protected void stoppenOnClick()
	{
		
	}
}
