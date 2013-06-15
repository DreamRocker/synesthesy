/**
 * 
 */
package de.synesthesy.music.cache;

import java.util.Vector;

import de.synesthesy.music.Note.Note;

/**
 * Dispatches events to the corresponding caches
 * @author Marc Koderer
 *
 */
public class CacheDispatcher {
	private static CacheDispatcher cacheDispatcher;
	Vector <ICache> caches = new Vector<ICache>();
	private CacheDispatcher(){}
	
	public static CacheDispatcher getInstance(){
		if (cacheDispatcher == null){
			cacheDispatcher = new CacheDispatcher();
		}
		return cacheDispatcher;
	}
	
	public void init(){
		/* no all caches should be registered from the outside
		caches.add(new PerChannel());
		caches.add(new PressedNotes());
		*/
	}

	public void registerCache(String cache) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class c = Class.forName(cache);
		caches.add((ICache)c.newInstance());
	}

	
	public void registerCache(ICache cache) {
		caches.add(cache);
	}
	
	public void add(Note note, int channel){
		for (ICache cache:caches){
			if (cache.intressed(note, channel)){
				cache.addNote(note, channel);
			}
		}
	}
	
	public void update(Note note, int channel){
		for (ICache cache:caches){
			if (cache.intressedOnUpdate(note, channel)){
				cache.update(note, channel);
			}
		}
	}

}
