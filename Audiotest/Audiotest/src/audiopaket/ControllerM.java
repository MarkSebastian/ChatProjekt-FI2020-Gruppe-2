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
			//this.gui.getBtnAufnehmen().setVisible(false);
			//audioAufnehmen2 = new AudioAufnehmen2(this.guiAudio);
			audioAufnehmen2 = new AudioAufnehmen2();

			audioAufnehmen2.build(format);
			System.out.println("Das ist das audioFormat" +audioAufnehmen2);
			System.out.println("recording");
			audioAufnehmen2.starteRun(audioAufnehmen2);
			
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
		//funktioniert
		//musste line auskommentieren
		audioPlayThread = new AudioPlay(this);
		audioPlayThread.start();
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
