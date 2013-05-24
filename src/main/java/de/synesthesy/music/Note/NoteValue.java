package de.synesthesy.music.Note;
/**
 * This class holds a duration of a note
 * @see Note
 * @author Marc Koderer
 *
 */
public class NoteValue {
	long duration;

	/**
	 * Gets the raw duration in ms
	 * @return duration
	 */
	public long getDuration() {
		return duration;
	}

	/**
	 * Sets the raw duration in ms
	 * 
	 * @param duration 
	 */
	public void setDuration(long duration) {
		this.duration = duration;
	}
	

}
