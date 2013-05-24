package de.synesthesy.music.key;

import java.util.Vector;

import de.synesthesy.music.Note.Note;
import junit.framework.TestCase;

public class MusicKeyAnalyzerTest extends TestCase {
	
	public void testGetMusicKey() {
		MusicKeyAnalyzer mka = new MusicKeyAnalyzer();
		Vector<Note> notes = new Vector <Note>();
		notes.add(new Note (0,127,(long)1));
		notes.add(new Note (4,127,(long)1));
		notes.add(new Note (7,127,(long)1));
		
		assertEquals(new MusicKey(0),mka.getMusicKey(notes));
	}

}
