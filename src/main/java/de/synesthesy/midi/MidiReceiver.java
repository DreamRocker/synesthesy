package de.synesthesy.midi;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
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
	Vector<MidiMessage> sustain_cache = new Vector<MidiMessage>();
	int channel;
	boolean sustain_pressed = false;
	
	@Override
	public void send(MidiMessage message, long timeStamp) {
		try{
			log.finest("Received message: "+ message);
			if (message instanceof ShortMessage){
				ShortMessage sm = (ShortMessage) message;				
				log.fine(String.format("Received message [Channel: %d]:"+
									   "cmd %d, d1 %d, d2 %d, stat %d", 
									   sm.getChannel(), sm.getCommand(),
									   sm.getData1(), sm.getData2(), 
									   sm.getStatus()));
				byte[] arr = message.getMessage();
				int i = message.getStatus();
				if(message.getStatus() == ShortMessage.NOTE_ON && message.getMessage()[2] == 0) {
					try {
						sm.setMessage(ShortMessage.NOTE_OFF, sm.getChannel(), sm.getData1(), sm.getData2());
					} catch (InvalidMidiDataException e) {
						return;
					}
				}
				if (message.getStatus() == ShortMessage.CONTROL_CHANGE && message.getMessage()[1] == 64){
					/* if sustain is pressed we have to bypass all note_off's
					 * if it's released we have to dispatch all note_off's
					 */
					if (message.getMessage()[2] > 63){
						this.sustain_pressed = true;
					}
					if (message.getMessage()[2] < 64){
						if (this.sustain_pressed){
							log.finest("Sustain released dispatch all note off messages");
							this.sustain_pressed = false;
							for (MidiMessage m : sustain_cache){
								this.send(m, timeStamp);
							}
							sustain_cache.clear();
						}
					}
				}
				if (message.getStatus() == ShortMessage.NOTE_OFF && this.sustain_pressed){
					sustain_cache.add(message);
					log.finest("Sustain pressed - store message in cache");
					return;
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
			log.log(Level.SEVERE, "Error in midi processing", e);
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
