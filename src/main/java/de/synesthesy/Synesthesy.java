package de.synesthesy;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Logger;

import de.synesthesy.cache.CachePerChannel;
import de.synesthesy.csv.CSVTestSetInOutput;
import de.synesthesy.csv.CSVTestSetLoader;
import de.synesthesy.music.MusicKey;
import de.synesthesy.music.Note.Note;
import de.synesthesy.nn.*;

/**
 * Synesthesy is the main class to access all functionality.
 * All pressed or realeased key are stored in the cache and can be accessed from here.
 * @author Marc Koderer
 *
 */
public class Synesthesy {
	private static final Logger log = Logger.getLogger( Synesthesy.class.getName() );
	private Map<Integer, CachePerChannel> caches = new HashMap<Integer, CachePerChannel>();
	private int nnInputs = 12;
	private int nnOutputs = 15;
	private int[] nnHidden = { 24 };
	private IMusicKeyNN keyDetermination; 
	private static Synesthesy synesthesy;
	private Synesthesy() {
		keyDetermination = new PreTrainedMusicKeyNN();
	}
	
	public static Synesthesy getInstance(){
		if (synesthesy == null){
			synesthesy = new Synesthesy();
			synesthesy.init();
		}
		return synesthesy;
	}
	
	public void init(){
		
	}
	
/*	public Synesthesy(String path) {
		keyDetermination = new MusicKeyNN(nnInputs, nnOutputs, nnHidden);
		CSVTestSetInOutput csv;
		try {
			csv = new CSVTestSetLoader(path).readCSV(
					nnInputs, nnOutputs);
			keyDetermination.train(csv);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

	public MusicKey getMusicKey(Vector<Note> notes) {
		double[] inp = new double[12];
		double[] output = new double[nnOutputs];
		for (Note note : notes) {
			int value = note.getPitch() % 12;
			inp[value] = 1.0;
		}
		output = keyDetermination.compute(inp);
		double highestValue = -1;
		int key = -1;
		for (int i = 0; i < 12; i++) {
			if (highestValue < output[i]) {
				highestValue = output[i];
				key = i;
			}
		}
		MusicKey mKey = new MusicKey(key);
		if (output[12] > 0.5){
			mKey.setIs7(true);
		}
		if (output[13] > 0.5){
			mKey.setMaj7(true);
		}
		if (output[14] > 0.5){
			mKey.setMinor(true);
		}
		
		return mKey;
	}
	/**
	 * Register an "pressNote" event for a given channel and stores it in the corresponing cache
	 * @param nt the note
	 * @param channel the channel
	 */
	public void registerNoteEvent(Note nt, int channel){
		log.fine("Received event "+nt + "from Channel "+ channel);
		if (caches.get(channel) == null){
			caches.put(new Integer(channel), new CachePerChannel(channel));
		}
		caches.get(channel).addNote(nt);
	}
	
	/**
	 * This searches for a pressed key and closes it.
	 * @see Note
	 * @see NoteValue  
	 * @param nt
	 * @param channel
	 */
	public void closeNoteEvent(Note nt, int channel){
		log.fine("Received release "+nt + "from Channel "+ channel);
		if (caches.get(channel) == null){
			log.warning("Cannot find releated note object");
			return ;
		}
		for (Note cachedNote : caches.get(channel).getNoteVector()){
			if (cachedNote.isPressed() && cachedNote.equals(nt)){
				cachedNote.registerRelease(nt.getTimeStamp());
			}
		}
	}
	
	/** 
	 * @return the full cache map
	 */
	public Map<Integer, CachePerChannel> getCaches() {
		return caches;
	}
	
	/**
	 * @return all played notes (unsorted)
	 */
	public Vector<Note> getAllPlayedNotes(){
		Vector<Note> result = new Vector<Note>();
		for (int channel:caches.keySet()){
			result.addAll(caches.get(channel).getNoteVector());
		}
		return result;
	}


}
