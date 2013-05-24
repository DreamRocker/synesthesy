package de.synesthesy.midi.listeners;

import javax.sound.midi.ShortMessage;

public interface IMidiListener {
	public void receiveMessage(ShortMessage sm, long ticks);
	public int getFilter();
}
