package de.synesthesy.cache;

import java.util.Vector;
import java.util.logging.Logger;

import de.synesthesy.music.Note.Note;

/**
 * This class stores all played notes
 * 
 * @author Marc Koderer
 *
 */
public class CachePerChannel {
	private static final Logger log = Logger.getLogger( CachePerChannel.class.getName() );
	int maxSize = 128;
	int channel;
	public int getChannel() {
		return channel;
	}

	Vector<Note> NoteVector = new Vector<Note>();
	
	public CachePerChannel(int channel){
		this.channel = channel;
	}
	
	public int getMaxSize() {
		return maxSize;
	}
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}
	public Vector<Note> getNoteVector() {
		return NoteVector;
	}
	public void setNoteVector(Vector<Note> noteVector) {
		NoteVector = noteVector;
	}
	
	public void addNote(Note note) {
		if (NoteVector.size() >  maxSize){
			int toBeRemoved = NoteVector.size() - maxSize;
			log.finest("MaxSize reached remove " + toBeRemoved +"Entries");
			for (int i = 0; i < toBeRemoved; i++)
				NoteVector.remove(i);
		}
		NoteVector.add(note);
	}

}
