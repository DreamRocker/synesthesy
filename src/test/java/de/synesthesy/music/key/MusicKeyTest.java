package de.synesthesy.music.key;

import junit.framework.TestCase;

public class MusicKeyTest extends TestCase {
	
	public void testMusicKey(){
		MusicKey m = new MusicKey(0);
		m.determineChordFunctions();
		System.out.println("Dominant: "+ m.dominant + " Sub: " + m.subDominant + " tP: " + m.tonicaParallel + " dP: "+ m.dominantParallel + " sP: " + m.subDominantParallel);
		assertEquals(m.getDominant(), new MusicKey(7));
		assertEquals(m.getSubDominant(), new MusicKey(5));
		assertEquals(m.getTonicaParallel(), new MusicKey(9, true));
		assertEquals(m.getDominantParallel(), new MusicKey(4, true));
		assertEquals(m.getSubDominantParallel(), new MusicKey(2, true));
		
		assertEquals(m.getDominant().isMinor(), false);
		assertEquals(m.getSubDominant().isMinor(), false);
		assertEquals(m.getTonicaParallel().isMinor(), true);
		assertEquals(m.getDominantParallel().isMinor(), true);
		assertEquals(m.getSubDominantParallel().isMinor, true);
		
		m = new MusicKey(9, true);
		m.determineChordFunctions();
		System.out.println("Dominant: "+ m.dominant + " Sub: " + m.subDominant + " tP: " + m.tonicaParallel + " dP: "+ m.dominantParallel + " sP: " + m.subDominantParallel);
		assertEquals(m.getDominant(), new MusicKey(4, true));
		assertEquals(m.getSubDominant(), new MusicKey(2, true));
		assertEquals(m.getTonicaParallel(), new MusicKey(0));
		assertEquals(m.getDominantParallel(), new MusicKey(7));
		assertEquals(m.getSubDominantParallel(), new MusicKey(5));
		
		assertEquals(m.getDominant().isMinor(), true);
		assertEquals(m.getSubDominant().isMinor(), true);
		assertEquals(m.getTonicaParallel().isMinor(), false);
		assertEquals(m.getDominantParallel().isMinor(), false);
		assertEquals(m.getSubDominantParallel().isMinor, false);
		
		
	}

}
