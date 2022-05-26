package chatClient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

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

public class AudioAufnehmen extends Thread
{
	private GUIAudio gui;
	private AudioFormat format;
	protected TargetDataLine tdl;
	private AudioInputStream ais;
	private ByteArrayInputStream bais;
	private Thread thread;

	// gui und GuiAudio sind auch threads
	public AudioInputStream getAis()
	{
		return ais;
	}

	public void setAis(AudioInputStream ais)
	{
		this.ais = ais;
	}

	public TargetDataLine getTdl()
	{
		return tdl;
	}

	public void setTdl(TargetDataLine tdl)
	{
		this.tdl = tdl;
	}

	public void aufzeichneThread(AudioAufnehmen audioAufnehmen)
	{
		thread = new Thread(this);
		thread.setName("Aufzeichnen");
		System.out.println("In AudioAufnehmen2 Thread Name " + thread.getName());
		thread.start();
	}

	public void stopThread()
	{
		thread = null;
	}

	// threads in einzelnen methoden

	public AudioFormat build(AudioFormat format)
	{
		this.format = format;
		System.out.println("das is in build " + format);
		System.out.println(this.format + "Das ist this");
		return this.format;
	}

	/**
	 * Closes the target data line to finish capturing and recording
	 */
	public void finish(TargetDataLine tdl)
	{
		tdl.stop();
		tdl.close();
		System.out.println("Finished");
	}

	@Override
	public void run()
	{
		try (final ByteArrayOutputStream out = new ByteArrayOutputStream();
				final TargetDataLine tdl = getTargetDataLineForRecord();)
		{
			System.out.println(tdl);
			int frameSizeInBytes = format.getFrameSize();
			int bufferLengthInFrames = tdl.getBufferSize() / 8;
			final int bufferLengthInBytes = bufferLengthInFrames * frameSizeInBytes;

			schreibeOutputStream(out, tdl, frameSizeInBytes, bufferLengthInBytes);
			ais = new AudioInputStream(tdl);
			setAis(convertToAudioIStream(out, frameSizeInBytes));
			System.out.println("Hallo Liebling");
			// ob wir die line hier brauchen wissen wir nicht
			ais.reset();
		}
		catch (Exception e )
		{
			e.printStackTrace();
		}

	}

	public void schreibeOutputStream(final ByteArrayOutputStream out, final TargetDataLine tdl, int frameSizeInBytes,
			final int bufferLengthInBytes) throws IOException
	{
		final byte[] data = new byte[bufferLengthInBytes];
		int numBytesRead;

		System.out.println("1");
		tdl.start();// start capturing
		System.out.println("2");
		// hier k�nnte das problem liegen
		// while (this.currentThread() != null)
		while (thread != null)
		{
			if ((numBytesRead = tdl.read(data, 0, bufferLengthInBytes)) == -1)
			{
				break;
			}
			out.write(data, 0, numBytesRead);
			System.out.println("I love you");
		}
		finish(tdl);
	}

	public AudioInputStream convertToAudioIStream(final ByteArrayOutputStream out, int frameSizeInBytes)
	{
		byte audioBytes[] = out.toByteArray();
		bais = new ByteArrayInputStream(audioBytes);
		AudioInputStream audioStream = new AudioInputStream(bais, format, audioBytes.length / frameSizeInBytes);
		long milliseconds = (long) ((ais.getFrameLength() * 1000) / format.getFrameRate());
		// brauchen wir vllt
		// duration = milliseconds / 1000.0;
		return audioStream;
	}

	// Methode von Git
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
