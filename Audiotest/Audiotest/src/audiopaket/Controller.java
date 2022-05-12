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

	public void audioRecord()
	{
		try
		{
			
			System.out.println("Starte Aufnahme");
			this.gui.getBtnStop().setVisible(true);
			this.gui.getBtnAufnehmen().setVisible(false);
			//This beim neuen CaptureThread übergeben sonst friert GUI ein
			captureThread = new CaptureThread(this);
			captureThread.start();
			JOptionPane.showMessageDialog(null,"Press ok to stop recording");
			captureThread.interrupt();
			//JOptionPane.getDesktopPaneForComponent(gui).get
		}

		catch (Exception e )
		{
			System.out.println(e);
		}
	}

	public void audioPlay()
	{

		audioPlayThread = new AudioPlay(this);
		audioPlayThread.start();
		
		// -->Ausgelagert in AudioPlay Klasse
		/*
		 * try { File file = new File(temp); audioStream =
		 * AudioSystem.getAudioInputStream(file); audioFormat = audioStream.getFormat();
		 * DataLine.Info info = new DataLine.Info(Clip.class, audioFormat); Clip clip =
		 * (Clip)AudioSystem.getLine(info);
		 * 
		 * 
		 * clip.open(audioStream); clip.start(); } catch (UnsupportedAudioFileException
		 * e ) { System.out.println("AudioFormat nicht unterstützt" + e); } catch
		 * (IOException e ) { System.out.println("IOException" + e); } catch
		 * (LineUnavailableException e ) { System.out.println("LineUnavailableException"
		 * + e); }
		 * 
		 * catch (Exception e ) { System.out.println("Exception e " + e); } finally { if
		 * ((audioStream != null)) { try {
		 * 
		 * audioStream.close(); } catch (IOException e ) {
		 * System.out.println("IOException-Close()" + e); } } }
		 */
	}

	public void recordStop()
	{
		//Beendet den Thread nicht, funktioniert noch nicht
		captureThread.interrupt();
		gui.getBtnAufnehmen().setVisible(true);
		gui.getBtnStop().setVisible(false);
	}

}
