package audiopaket;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JOptionPane;

public class ControllerM
{
	protected Gui gui;
	protected Thread captureThread;
	protected Thread audioPlayThread;

	public ControllerM()
	{
		gui = new Gui();
		setButtons();
	}

	private void setButtons()
	{
		this.gui.setBtnAufnehmen(e -> audioRecord());
		this.gui.setBtnAbspielen(e -> audioPlay());
		this.gui.setBtnStop(e -> recordStop());
		this.gui.getBtnStop().setVisible(false);
	}

	public void audioRecord()
	{
		try
		{
			
			System.out.println("Starte Aufnahme");
			this.gui.getBtnStop().setVisible(true);
			this.gui.getBtnAufnehmen().setVisible(false);
			//This beim neuen CaptureThread übergeben sonst friert GUI ein
			//musste die line auskommentieren
			System.out.println("Hier nicht 4");
			captureThread = new CaptureThread(this);
			System.out.println("Hier nicht 5");
			//run() wird aufgerufen
			captureThread.start();
			System.out.println("Hier nicht 6");
			//hier fehler
			JOptionPane.showMessageDialog(null,"Press ok to stop recording");
			//captureThread.interrupt();
		}

		catch (Exception e )
		{
			System.out.println(e);
		}
	}

	//funktioniert
	public void audioPlay()
	{

		//musste line auskommentieren
		audioPlayThread = new AudioPlay(this);
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
