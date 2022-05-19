package audiopaket;

import java.io.File;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.AudioFileFormat.Type;

public class AudioAufnehmenM extends Thread
{
	private ControllerM controller;
	private int random;
	private File file;
	private Type fileFormat;
	private AudioFormat audioFormat;
	protected TargetDataLine tdl;

	
	public TargetDataLine getTdl()
	{
		return tdl;
	}

	public void setTdl(TargetDataLine tdl)
	{
		this.tdl = tdl;
	}

	
	public AudioAufnehmenM(ControllerM controller)
	{
		this.controller = controller;
	}

	@Override
	public void run()
	{
		try
		{
			random = (int) (Math.random() * 10000000 + 10000000);
			file = new File(random + ".wav");
			fileFormat = AudioFileFormat.Type.WAVE;
			audioFormat = new AudioFormat(16000, 8, 2, true, true);
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
			
			if (!AudioSystem.isLineSupported(info))
			{
				System.out.println("AudioLine nicht supported");
			}
			try
			{
				tdl = (TargetDataLine) AudioSystem.getLine(info);
				tdl.open(audioFormat);
				tdl.start();// start capturing
				//AudioInputstream
			}
			catch (LineUnavailableException ex )
			{
				System.out.println("Line nicht verfügbar");
			}
		}
		catch (Exception e )
		{
			// TODO: handle exception
		}
		
	}
	
	/**
     * Closes the target data line to finish capturing and recording
     */
    public void finish() {
        tdl.stop();
        tdl.close();
        System.out.println("Finished");
    }
}

