package audiopaket;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringBufferInputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.AudioFileFormat.Type;

public class AudioAufnehmen2 extends Thread
{
	private ControllerM controllerM;
	private int random;
	private File file;
	private AudioFormat format;
	private Type fileFormat;
	protected TargetDataLine tdl;
	private AudioInputStream ais;
	private int frameSizeInBytes;
	private int bufferLengthInFrames;
	private ByteArrayOutputStream out = new ByteArrayOutputStream();
	private ByteArrayInputStream bais;
	private Thread thread;
	private AudioInputStream audioStream;
	private AudioAufnehmen2 audioAufnehmen2;
	private GUIAudio guiAudio;
	private double dauer;

	// gui und GuiAudio sind auch threads
	public AudioInputStream getAis()
	{
		return ais;
	}

	public void setAis(AudioInputStream ais)
	{
		this.ais = ais;
	}

	public AudioAufnehmen2(ControllerM controllerM)
	{
		this.controllerM = controllerM;
	}

	public AudioAufnehmen2()
	{
		// TODO Auto-generated constructor stub
	}

	public AudioAufnehmen2(GUIAudio guiAudio)
	{
		this.guiAudio = guiAudio;
		// guiAudio.setVisible(true);
	}

	public TargetDataLine getTdl()
	{
		return tdl;
	}

	public void setTdl(TargetDataLine tdl)
	{
		this.tdl = tdl;
	}

	public void aufzeichneThread()
	{
		thread = new Thread(this);
		thread.setName("Aufzeichnen");
		thread.start();
	}

	public void stoppeThread()
	{
		// Aufzeichnen Thread
		// hier wird Aufzeihcnen beendet
		thread = null;
	}

	public void run()
	{
		try (final ByteArrayOutputStream out = new ByteArrayOutputStream();
				final TargetDataLine tdl = getTargetDataLineForRecord();)
		{
			int frameSizeInBytes = format.getFrameSize();
			int bufferLengthInFrames = tdl.getBufferSize() / 8;
			final int bufferLengthInBytes = bufferLengthInFrames * frameSizeInBytes;
			schreibeOutputStream(out, tdl, frameSizeInBytes, bufferLengthInBytes);

			this.ais = new AudioInputStream(tdl);
			setAis(convertToAudioIStream(out, frameSizeInBytes));

			WavSpeichern wavspeichern = new WavSpeichern();

			wavspeichern.speichereInWav(getAis());

		}
		
		catch (Exception e )
		{
			System.out.println(e);
			e.printStackTrace();
		}
	}

	public AudioFormat build(AudioFormat format)
	{
		this.format = format;
		return this.format;
	}

	public void schreibeOutputStream(final ByteArrayOutputStream out, final TargetDataLine tdl, int frameSizeInBytes,
			final int bufferLengthInBytes) throws IOException
	{// Aufzeichnen Thread
		final byte[] data = new byte[bufferLengthInBytes];
		int numBytesRead;
		tdl.start();// start capturing
		while (thread != null)
		{
			if ((numBytesRead = tdl.read(data, 0, bufferLengthInBytes)) == -1)
			{
				break;
			}
			// schreibt daten in byteArrayOutputStream in numBytesRead
			out.write(data, 0, numBytesRead);
			System.out.println(out.size());
		}
		finish(tdl);
	}

	public AudioInputStream convertToAudioIStream(final ByteArrayOutputStream out, int frameSizeInBytes)
	{

		byte audioBytes[] = out.toByteArray();
		bais = new ByteArrayInputStream(audioBytes);

		AudioInputStream audioStream = new AudioInputStream(bais, format, audioBytes.length / frameSizeInBytes);
		long milliseconds = (long) ((ais.getFrameLength() * 1000) / format.getFrameRate());
/*		dauer = milliseconds / 1000.0;

		System.out.println("Dauer der Aufnahme in Sekunden: " + dauer);
*/
		return audioStream;

	}

	/**
	 * Closes the target data line to finish capturing and recording
	 * 
	 * @param tdl
	 */
	public void finish(TargetDataLine tdl)
	{
		tdl.stop();
		tdl.close();
	}

	public TargetDataLine getTargetDataLineForRecord()
	{
		TargetDataLine tdl;
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		if (!AudioSystem.isLineSupported(info))
		{
			return null;
		}
		try
		{

			tdl = (TargetDataLine) AudioSystem.getLine(info);
			tdl.open(format, tdl.getBufferSize());
		}
		catch (final Exception ex )
		{
			return null;
		}
		return tdl;

	}

}
