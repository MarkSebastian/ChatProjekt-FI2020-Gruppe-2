package audiopaket;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

public class Controller
{
	protected Gui gui;
	protected Thread captureThread;
	protected Thread audioPlayThread;

	public Controller()
	{
		gui = new Gui();
		setButtons();
	}

	private void setButtons()
	{
		this.gui.setBtnAbspielen(e -> audioPlay());
		this.gui.setBtnAufnehmen(e -> audioRecord());
		this.gui.setBtnStop(e -> recordStop());
		this.gui.getBtnStop().setVisible(false);
	}

	//line auskommentiert, ändern, wenn auf Controller
	public void audioRecord()
	{
		try
		{
			
			System.out.println("Starte Aufnahme");
			this.gui.getBtnStop().setVisible(true);
			this.gui.getBtnAufnehmen().setVisible(false);
			//This beim neuen CaptureThread übergeben sonst friert GUI ein
			
			
			
			//musste line auskommentieren sonst läuft programm nicht
		//	captureThread = new CaptureThread(this);
			captureThread.start();
			JOptionPane.showMessageDialog(null,"Press ok to stop recording");
			captureThread.interrupt();
		}

		catch (Exception e )
		{
			System.out.println(e);
		}
	}

	//line auskommentiert, ändern, wenn auf Controller
	public void audioPlay()
	{
		//musste line auskommentieren sonst läuft programm nicht
		
		
		
		//audioPlayThread = new AudioPlay(this);
		audioPlayThread.start();
		
	}

	public void recordStop()
	{
		//Beendet den Thread nicht, funktioniert noch nicht
		captureThread.interrupt();
		gui.getBtnAufnehmen().setVisible(true);
		gui.getBtnStop().setVisible(false);
	}

}
