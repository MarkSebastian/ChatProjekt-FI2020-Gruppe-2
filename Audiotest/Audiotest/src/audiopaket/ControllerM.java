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
	private AudioAufnehmen2 audioAufnehmen2;
	protected GUIAudio guiAudio;
	private WavSpeichern wavspeichern;

	public ControllerM()
	{
		gui = new Gui();
		guiAudio= new GUIAudio();
		setButtons();
	}
	

	private void setButtons()
	{
		this.gui.setBtnAufnehmen(e -> uebergabeAnGuiAudio());
		//this.gui.setBtnAufnehmen(e -> audioRecord());
		this.gui.setBtnAbspielen(e -> audioPlay());
		this.guiAudio.setBtnStop(e -> stoppeAufnahmeGUIAudio());
	}

	
	public void stoppeAufnahmeGUIAudio()
	{
		//AudioHauptThread
		audioAufnehmen2.stoppeThread();
		System.out.println("Beendet");
	}

	public void uebergabeAnGuiAudio()
	{
		guiAudio.setVisible(true);
		audioRecord();
		//erst wenn kleine gui offen ist audioAufnehmen erstellen
	}
	
	public void audioRecord()
	{
		try
		{
			AudioFormat format = setzeSoundEinstellungen();
			audioAufnehmen2 = new AudioAufnehmen2(guiAudio);

			audioAufnehmen2.build(format);
			System.out.println("recording");
			audioAufnehmen2.setName("AudioHauptThreadAWT");
			//audioHauptThread Thread
			audioAufnehmen2.aufzeichneThread();
		
		}
		catch (Exception e )
		{
			System.out.println(e);
		}
	}

	public void audioPlay()
	{
		//thread
		audioPlayThread = new AudioPlay(this);
		//System.out.println("In ControllerM Aktueller Thread Name "+audioPlayThread.getName());
		System.out.println("Play...");
		audioPlayThread.start();
		try
		{
			Thread.sleep(2000);
			audioPlayThread.interrupt();
		}
		catch (InterruptedException e )
		{
			e.printStackTrace();
		}
	}

	public static AudioFormat setzeSoundEinstellungen() 
	{
        SoundEinstellungen settings = new SoundEinstellungen();
        AudioFormat.Encoding encoding = settings.ENCODING;
        float rate = settings.RATE;
        int channels = settings.CHANNELS;
        int sampleSize = settings.SAMPLE_SIZE;
        boolean bigEndian = settings.BIG_ENDIAN;

        return new AudioFormat(encoding, rate, sampleSize, channels, (sampleSize / 8) * channels, rate, bigEndian);
    }
}
