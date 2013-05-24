package de.synesthesy.midi.listeners;

import javax.sound.midi.ShortMessage;

import de.synesthesy.Synesthesy;
import de.synesthesy.music.Note.Note;
import de.synesthesy.music.cache.CacheDispatcher;

public class NoteOnListener implements IMidiListener {

	@Override
	public int getFilter() {
		return ShortMessage.NOTE_ON;
	}

	@Override
	public void receiveMessage(ShortMessage sm, long ticks) {
		CacheDispatcher cd = CacheDispatcher.getInstance();
		Note nt = new Note(sm.getData1(), sm.getData2(), ticks);
		cd.add(nt, sm.getChannel());	
	}

}
