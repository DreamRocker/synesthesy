package de.synesthesy.music.cache;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import de.synesthesy.music.Note.Note;

public class PressedNotes implements ICache {
	Set <Note> cache = Collections.synchronizedSet(new LinkedHashSet<Note>());

	public synchronized Set<Note> getCache() {
		return cache;
	}

	@Override
	public boolean intressed(Note note, int channel) {
		if (note.isPressed()) return true;
		return false;
	}

	@Override
	public synchronized void addNote(Note note, int channel) {
		cache.add(note);
	}

	@Override
	public boolean intressedOnUpdate(Note note, int channel) {
		return !(intressed(note, channel));
	}

	@Override
	public synchronized boolean update(Note note, int channel) {
		for (Iterator<Note> it = cache.iterator(); it.hasNext();){
			if (it.next().equals(note)){
				it.remove();
				return true;
			}
		}
		return false;
	}

}
