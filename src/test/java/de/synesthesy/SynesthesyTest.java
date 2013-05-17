package de.synesthesy;

import java.util.Vector;

import de.synesthesy.music.MusicKey;

import junit.framework.TestCase;
import themidibus.Note;

public class SynesthesyTest extends TestCase {

	public void testSynesthesy() {
		Synesthesy syn = new Synesthesy();
	}

	public void testGetMusicKey() {
		Synesthesy syn = new Synesthesy();
		Vector<Note> notes = new Vector <Note>();
		notes.add(new Note (0,127,(long)1));
		notes.add(new Note (4,127,(long)1));
		notes.add(new Note (7,127,(long)1));
		
		assertEquals(new MusicKey(0),syn.getMusicKey(notes));
	}

}
