package de.synesthesy.music.cache;

import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import de.synesthesy.music.Note.Note;

/**
 * This class stores all played notes
 * 
 * @author Marc Koderer
 * 
 */
public class PerChannel implements ICache {
	private static final Logger log = Logger.getLogger(PerChannel.class
			.getName());
	private Map<Integer, Vector<Note>> caches = 
			new ConcurrentHashMap<Integer, Vector<Note>>();

	int maxSize = 128;
	
	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}
	
	public Map<Integer, Vector<Note>> getCaches() {
		return caches;
	}
	
	public Vector<Note> getCache(Integer channel) {
		return caches.get(channel);
	}

	/**
	 * Register an "pressNote" event for a given channel and stores it in the
	 * corresponding cache
	 * 
	 * @param nt
	 *            the note
	 * @param channel
	 *            the channel
	 */
	public void addNote(Note note, int channel) {
		if (caches.get(channel) == null) {
			caches.put(new Integer(channel), new Vector<Note>());
		}
		Vector<Note> currVector = caches.get(channel);
		if (currVector.size() > maxSize) {
			int toBeRemoved = currVector.size() - maxSize;
			log.finest("Maximal size reached: remove " + toBeRemoved + "Entries");
			for (int i = 0; i < toBeRemoved; i++)
				currVector.iterator().remove();
			
		}
		currVector.add(note);
	}

	@Override
	public boolean intressed(Note note, int channel) {
		return true;
	}

	@Override
	public boolean intressedOnUpdate(Note note, int channel) {
		return true;
	}

	/**
	 * This searches for a pressed key and closes it.
	 * 
	 * @param nt
	 * @param channel
	 */
	@Override
	public boolean update(Note note, int channel) {
		if (caches.get(channel) == null) {
			log.warning("Cannot find releated note object");
			return false;
		}
		for (Note cachedNote : caches.get(channel)) {
			if (cachedNote.isPressed() && cachedNote.equals(note)) {
				cachedNote.registerRelease(note.getTimeStamp());
				return true;
			}
		}
		return false;
	}

}
