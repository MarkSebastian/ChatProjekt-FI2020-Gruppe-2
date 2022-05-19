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
	//protected Thread captureThread;
	protected Thread audioAufnehmen;
	protected Thread audioPlayThread;
	protected Thread audioAufnehmenM;

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
			this.gui.getBtnStop().setVisible(true);
			this.gui.getBtnAufnehmen().setVisible(false);
			audioAufnehmenM = new AudioAufnehmenM(this);
			audioAufnehmenM.start();
			
			//captureThread = new CaptureThread(this);
			//captureThread.start();
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

	
	public void audioPlay()
	{
		//funktioniert
		//musste line auskommentieren
		audioPlayThread = new AudioPlay(this);
		audioPlayThread.start();
	}

	/**
     * Closes the target data line to finish capturing and recording
     */
	public void recordStop()
	{
		//Beendet den Thread nicht, funktioniert noch nicht
		//grade nicht gebraucht
		//thread wird beendet
		//captureThread.interrupt();
		gui.getBtnAufnehmen().setVisible(true);
		gui.getBtnStop().setVisible(false);
		//interrupt des threads mit abchecken ob der schläft oder so ?
		
	}	
	
	 
}
