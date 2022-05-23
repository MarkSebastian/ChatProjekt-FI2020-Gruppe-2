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
		this.gui.setBtnAufnehmen(e -> audioRecord());
		this.gui.setBtnAbspielen(e -> audioPlay());
	}

	public void audioRecord()
	{
		try
		{
			AudioFormat format = setzeSoundEinstellungen();
			audioAufnehmen2 = new AudioAufnehmen2();

			audioAufnehmen2.build(format);
			System.out.println("Das ist das audioFormat" +audioAufnehmen2);
			System.out.println("recording");
			audioAufnehmen2.setName("AudioHauptThread");
			//thread audioAufnehmen
			System.out.println("In Controllert Thread Name "+audioAufnehmen2.getName());
			audioAufnehmen2.aufzeichneThread(audioAufnehmen2);
			
			Thread.sleep(20000);
		    audioAufnehmen2.interrupt();

		    wavspeichern =new WavSpeichern();
			//schreibt wav 
		    wavspeichern.speichereInWav(audioAufnehmen2.getAis());
			System.out.println("Hier nicht 6");
			//hier fehler
			guiAudio.setVisible(true);
			
			//audioAufnehmen.interrupt();
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
		System.out.println("In ControllerM Aktueller Thread Name "+audioPlayThread.getName());
		audioPlayThread.start();
		//thread beenden hier ? oder bei run ?
	}

	public static AudioFormat setzeSoundEinstellungen() {
        SoundEinstellungen settings = new SoundEinstellungen();
        AudioFormat.Encoding encoding = settings.ENCODING;
        float rate = settings.RATE;
        int channels = settings.CHANNELS;
        int sampleSize = settings.SAMPLE_SIZE;
        boolean bigEndian = settings.BIG_ENDIAN;

        return new AudioFormat(encoding, rate, sampleSize, channels, (sampleSize / 8) * channels, rate, bigEndian);
    }
	
	
	 
}
