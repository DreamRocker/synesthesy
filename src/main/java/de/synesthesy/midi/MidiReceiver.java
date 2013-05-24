package de.synesthesy.midi;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

import de.synesthesy.midi.listeners.IMidiListener;

/**
 * This functions receives all midi events and distribute it to all registered listeners
 * @author Marc Koderer
 *
 */
public class MidiReceiver implements Receiver {
	Map <Integer, Vector<IMidiListener>> listeners= new HashMap <Integer, Vector<IMidiListener>>();
	int channel;
	@Override
	public void send(MidiMessage message, long timeStamp) {
		if (message instanceof ShortMessage){
			ShortMessage sm = (ShortMessage) message;
			if (listeners.get(sm.getCommand()) != null){
				for (IMidiListener listener : listeners.get(sm.getCommand())){
					listener.receiveMessage(sm, timeStamp);
				}
			} else if (listeners.get(0) != null){
				/* catch all listeners */
				for (IMidiListener listener : listeners.get(0)) {
					listener.receiveMessage(sm, timeStamp);
				}
			}
		}
		
	}
	/**
	 * This function registers a listener
	 * @param listener
	 */
	public void registerListener(IMidiListener listener){
		if (listeners.get(listener.getFilter()) != null){
			listeners.get(listener.getFilter()).add(listener);
		} else{
			Vector<IMidiListener> ml = new Vector<IMidiListener>();
			ml.add(listener);
			listeners.put(listener.getFilter(), ml);
		}
	}

	@Override
	public void close() {
		
	}
	public int getChannel() {
		return channel;
	}
	public void setChannel(int channel) {
		this.channel = channel;
	}

}
