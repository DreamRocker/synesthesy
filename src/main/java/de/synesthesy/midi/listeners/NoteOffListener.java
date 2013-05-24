package de.synesthesy.midi.listeners;

import javax.sound.midi.ShortMessage;

import de.synesthesy.Synesthesy;
import de.synesthesy.music.Note.Note;
import de.synesthesy.music.cache.CacheDispatcher;

public class NoteOffListener implements IMidiListener{

	public int getFilter() {
		return ShortMessage.NOTE_OFF;
	}
	
	public void receiveMessage(ShortMessage sm, long ticks) {
		CacheDispatcher cd = CacheDispatcher.getInstance();
		Note nt = new Note(sm.getData1(), sm.getData2(), ticks);
		cd.update(nt, sm.getChannel());	
	}
}
