package de.synesthesy.music.cache;

import de.synesthesy.music.Note.Note;

public interface ICache {
	
	public boolean intressed(Note note, int channel);
	public boolean intressedOnUpdate(Note note, int channel);
	public void addNote(Note note, int channel);
	public boolean update(Note note, int channel);

}
