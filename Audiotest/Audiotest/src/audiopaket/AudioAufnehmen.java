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

public class AudioAufnehmen extends Thread
{
	private ControllerM controller;
	private int random;
	private File file;
	private Type fileFormat;
	private AudioFormat audioFormat;
	protected TargetDataLine tdl;

	public AudioAufnehmen(ControllerM controller)
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
				System.out.println("Format nicht unterstützt");
			}
			try
			{
				tdl = (TargetDataLine) AudioSystem.getLine(info);
				tdl.open(audioFormat);
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
}
