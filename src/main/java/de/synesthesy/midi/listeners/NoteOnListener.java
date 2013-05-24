package de.synesthesy.midi.listeners;

import javax.sound.midi.ShortMessage;

import de.synesthesy.Synesthesy;
import de.synesthesy.music.Note.Note;

public class NoteOnListener implements IMidiListener {

	@Override
	public int getFilter() {
		return ShortMessage.NOTE_ON;
	}

	@Override
	public void receiveMessage(ShortMessage sm, long ticks) {
		Synesthesy sy = Synesthesy.getInstance();
		Note nt = new Note(sm.getData1(), sm.getData2(), ticks);
		sy.registerNoteEvent(nt, sm.getChannel());
	}

}
