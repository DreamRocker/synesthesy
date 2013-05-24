package de.synesthesy.midi;

import java.util.Vector;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Transmitter;

import de.synesthesy.midi.listeners.NoteOffListener;
import de.synesthesy.midi.listeners.NoteOnListener;

public class MidiHandler {
	Vector<MidiReceiver> receivers = new Vector<MidiReceiver>();
	private static MidiHandler midiHandler;
	private MidiHandler(){
	}
	
	static public MidiHandler getInstance(){
		if (midiHandler == null)
			return new MidiHandler();
		return midiHandler;
	}
	
	public void init(){
		attachReceiverToAllOutputDevices();
		registerDefaultListeners();
	}
	
	protected void registerDefaultListeners(){
		for (MidiReceiver receiver : receivers){
			receiver.registerListener(new NoteOnListener());
			receiver.registerListener(new NoteOffListener());
		}
	}
	
	public void attachReceiverToAllOutputDevices() {
		MidiDevice.Info[] available_devices = MidiSystem.getMidiDeviceInfo();
		MidiDevice device;
		
		Vector<MidiDevice.Info> devices_list = new Vector<MidiDevice.Info>();
		
		for(int i = 0;i < available_devices.length;i++) {
			try {
				device = MidiSystem.getMidiDevice(available_devices[i]);
				if (device.getMaxTransmitters() != 0){
					MidiReceiver receiver = new MidiReceiver();
					Transmitter transmitter = device.getTransmitter();
					transmitter.setReceiver(receiver);
					device.open();
					receiver.setChannel(i);
					receivers.add(receiver);
				}
			} catch(MidiUnavailableException e) {
				//Device was unavailable which is fine, we only care about available inputs
			}
		}
	}

	public Vector<MidiReceiver> getReceivers() {
		return receivers;
	}

	public void setReceivers(Vector<MidiReceiver> receivers) {
		this.receivers = receivers;
	}

}
