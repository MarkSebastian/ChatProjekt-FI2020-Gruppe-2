package chatClient;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatController extends Control implements Initializable
{

	@FXML
	private Button button_send;
	@FXML
	private TextField textField_messages;
	@FXML
	private VBox vbox_messages;
	@FXML
	private ScrollPane scrollpane_messages;
	@FXML
	private ImageView image_show;
	@FXML
	private AnchorPane side_pane_b;
	@FXML
	private AnchorPane menu_bg_pane;
	@FXML
	private AnchorPane transparent_pane;
	@FXML
	private Button button_neu;
	private Thread thread;
	private GetNachrichten gNachrichten;

	public ChatController()
	{
		gNachrichten = new GetNachrichten(this);
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{

		menu_bg_pane.setVisible(false);
		transparent_pane.setVisible(false);

		textField_messages.setOnKeyPressed(new EventHandler<KeyEvent>()
		{
			@Override
			public void handle(KeyEvent event)
			{
				if (event.getCode().equals(KeyCode.ENTER))
				{
					send();
				}
			}
		});

		vbox_messages.heightProperty().addListener(new ChangeListener<Number>()
		{
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
			{
				scrollpane_messages.setVvalue((Double) newValue);
			}
		});

		TranslateTransition tTrans = new TranslateTransition(Duration.seconds(0.5), side_pane_b);
		tTrans.setByX(-600);
		tTrans.play();

		FadeTransition fade = new FadeTransition(Duration.seconds(0.5), transparent_pane);
		fade.setFromValue(1);
		fade.setToValue(0);
		fade.play();

		image_show.setOnMouseClicked(event ->
		{
			menu_bg_pane.setVisible(true);
			transparent_pane.setVisible(true);
			fade.setFromValue(0);
			fade.setToValue(1);
			fade.play();
			tTrans.setByX(+600);
			tTrans.play();

		});

		menu_bg_pane.setOnMouseClicked(event ->
		{
			menu_bg_pane.setVisible(false);
			tTrans.setByX(-600);
			tTrans.play();
			fade.setFromValue(1);
			fade.setToValue(0);
			fade.play();
			transparent_pane.setVisible(false);
		});

		button_send.setOnAction(event ->
		{
			send();
			gNachrichten.run();
		});

		button_neu.setOnAction(endmysuffering ->
		{
			Stage stageEventChangeStage = (Stage) textField_messages.getScene().getWindow();
			try
			{
				stageEventChangeStage.setScene(fabisGui());
				stageEventChangeStage.setTitle("Neue Chat");
				stageEventChangeStage.show();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	public void send()
	{
		String textToSend = textField_messages.getText();
		if (!textToSend.isEmpty())
		{
			sendMessage(textToSend);
			HBox hBox = new HBox();
			hBox.setAlignment(Pos.CENTER_RIGHT);
			hBox.setPadding(new Insets(5, 5, 5, 10));

			Text text = new Text(textToSend);
			TextFlow textFlow = new TextFlow(text);
			textFlow.setMaxWidth(310);

			textFlow.setStyle("-fx-color: rgb(239,242,255);" + "-fx-background-color: rgb(15,125,242);"
					+ "-fx-background-radius: 20px;" + "-fx-font-size: 14px;");

			textFlow.setPadding(new Insets(5, 10, 5, 10));
			text.setFill(Color.color(0.934, 0.945, 0.996));

			hBox.getChildren().add(textFlow);
			vbox_messages.getChildren().add(hBox);
			vbox_messages.setMinHeight(vbox_messages.getPrefHeight() + textFlow.getHeight());
			textField_messages.clear();
		}
	}

	public void bekommeNachricht(String newText)
	{
		HBox hBox = new HBox();
		hBox.setAlignment(Pos.CENTER_LEFT);
		hBox.setPadding(new Insets(5, 5, 5, 10));

		Text text = new Text(newText);
		TextFlow textFlow = new TextFlow(text);
		textFlow.setMaxWidth(310);

		textFlow.setStyle("-fx-background-color: rgb(233,233,235);" + "-fx-background-radius: 20px;");

		textFlow.setPadding(new Insets(5, 10, 5, 10));
		hBox.getChildren().add(textFlow);

		vbox_messages.getChildren().add(hBox);
	}
}
