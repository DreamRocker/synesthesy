package de.synesthesy.music.cache;

import java.util.LinkedHashSet;

import de.synesthesy.music.Note.Note;

public class PressedNotes implements ICache {
	LinkedHashSet <Note> cache = new LinkedHashSet<Note>();
	@Override
	public boolean intressed(Note note, int channel) {
		if (note.isPressed()) return true;
		return false;
	}

	@Override
	public void addNote(Note note, int channel) {
		cache.add(note);
	}

	@Override
	public boolean intressedOnUpdate(Note note, int channel) {
		return !(intressed(note, channel));
	}

	@Override
	public boolean update(Note note, int channel) {
		return cache.remove(note);
	}

}
