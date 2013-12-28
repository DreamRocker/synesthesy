package de.synesthesy.midi;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Logger;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

import de.synesthesy.Synesthesy;
import de.synesthesy.midi.listeners.IMidiListener;

/**
 * This functions receives all midi events and distribute it to all registered listeners
 * @author Marc Koderer
 *
 */
public class MidiReceiver implements Receiver {
	private static final Logger log = Logger.getLogger( MidiReceiver.class.getName() );
	Map <Integer, Vector<IMidiListener>> listeners= new HashMap <Integer, Vector<IMidiListener>>();
	int channel;
	@Override
	public void send(MidiMessage message, long timeStamp) {
		try{
			log.finest("Received message: "+message);
			if (message instanceof ShortMessage){
				ShortMessage sm = (ShortMessage) message;
				if(message.getStatus() == ShortMessage.NOTE_ON && message.getMessage()[2] == 0) {
					try {
						sm.setMessage(ShortMessage.NOTE_OFF, sm.getChannel(), sm.getData1(), sm.getData2());
					} catch (InvalidMidiDataException e) {
						return;
					}
				}
				if (listeners.get(sm.getCommand()) != null){
					for (IMidiListener listener : listeners.get(sm.getCommand())){
						listener.receiveMessage(sm, timeStamp);
					}
				} 
				if (listeners.get(0) != null){
					/* catch all listeners */
					for (IMidiListener listener : listeners.get(0)) {
						listener.receiveMessage(sm, timeStamp);
					}
				}
			}
		}
		catch(Exception e){
			log.warning(e.getMessage());
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
