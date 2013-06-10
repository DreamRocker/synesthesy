package de.synesthesy;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Logger;

import de.synesthesy.csv.CSVTestSetInOutput;
import de.synesthesy.csv.CSVTestSetLoader;
import de.synesthesy.midi.MidiHandler;
import de.synesthesy.music.Note.Note;
import de.synesthesy.music.cache.CacheDispatcher;
import de.synesthesy.music.cache.PerChannel;
import de.synesthesy.music.key.MusicKey;
import de.synesthesy.music.key.nn.*;

/**
 * Synesthesy is the main class to access all functionality.
 * All pressed or realeased key are stored in the cache and can be accessed from here.
 * @author Marc Koderer
 *
 */
public class Synesthesy {
	private static final Logger log = Logger.getLogger( Synesthesy.class.getName() );
	int bpm = 120;
	
	private static Synesthesy synesthesy;
	private Synesthesy() {
	}
	
	public static Synesthesy getInstance(){
		if (synesthesy == null){
			synesthesy = new Synesthesy();
			synesthesy.init();
		}
		return synesthesy;
	}
	
	/**
	 * The main initialization function
	 */
	private void init(){
		MidiHandler.getInstance().init();
		CacheDispatcher.getInstance().init();
	}
	

	public int getBpm() {
		return bpm;
	}

	public void setBpm(int bpm) {
		this.bpm = bpm;
	}

}
