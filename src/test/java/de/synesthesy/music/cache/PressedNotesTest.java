package de.synesthesy.music.cache;

import de.synesthesy.music.Note.Note;
import junit.framework.TestCase;

public class PressedNotesTest extends TestCase {

	public void testGetCache() {
		PressedNotes pNt = new PressedNotes();
	    assertNotNull(pNt.getCache());
	}

	public void testAddNote() {
		PressedNotes pNt = new PressedNotes();
		Note nt = new Note(5,1,500);
		pNt.addNote(nt, 0);
	    assertEquals(pNt.getCache().size(), 1);
	    nt.setPitch(9);
	    pNt.addNote(nt, 0);
	    assertEquals(pNt.getCache().size(), 2);
	}

	public void testUpdate() {
		PressedNotes pNt = new PressedNotes();
		Note nt = new Note(5,1,500);
		pNt.addNote(nt, 0);
		nt.setPressed(false);
		pNt.update(nt,1);
	    assertEquals(pNt.getCache().size(), 0);
	    
	    /*add more notes*/
	    Note nt1 = new Note(5,1,500);
	    Note nt2 = new Note(6,1,500);
	    Note nt3 = new Note(7,1,500);
		
	    pNt.addNote(nt1, 0);
	    pNt.addNote(nt2, 1);
	    pNt.addNote(nt3, 2);
	    
	    assertEquals(pNt.getCache().size(), 3);
	    nt2.setPressed(false);
		pNt.update(nt2,1);
		assertEquals(pNt.getCache().size(), 2);
		}

}
