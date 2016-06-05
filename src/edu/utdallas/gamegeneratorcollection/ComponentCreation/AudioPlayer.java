package edu.utdallas.gamegeneratorcollection.ComponentCreation;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

/**
 * Provides methods for playing audio. Source was included with original
 * GameGenerator code.
 *
 */
public final class AudioPlayer {

	// The clip being played
	private static Clip currentClip;

	/**
	 * Plays the audio at the given path.
	 * @param path The path to the audio clip
	 */
	public static void playAudio(final String path) {
		stopAudio();
		
		try {
			// Load the audio clip and relevant info
			File audioFile = new File(path);
			AudioInputStream stream = AudioSystem.getAudioInputStream(audioFile);
			AudioFormat format = stream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			final Clip clip = (Clip) AudioSystem.getLine(info);
			
			currentClip = clip;

			clip.addLineListener(new LineListener() {
				@Override
				public void update(final LineEvent event) {
					if (event.getType() == LineEvent.Type.STOP) {
						clip.close();
					}
				}
			});

			clip.open(stream);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Stop the audio and close the resource
	 */
	public static void stopAudio() {
		if (currentClip != null && currentClip.isRunning()) {
			currentClip.stop();
			currentClip.close();
		}
	}
}
