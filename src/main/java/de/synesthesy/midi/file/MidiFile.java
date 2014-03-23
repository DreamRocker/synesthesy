package de.synesthesy.midi.file;

import java.io.File;
import java.util.Enumeration;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Transmitter;

import de.synesthesy.Synesthesy;
import de.synesthesy.midi.MidiHandler;
import de.synesthesy.midi.MidiReceiver;
import de.synesthesy.music.cache.CacheDispatcher;
import de.synesthesy.music.cache.PressedNotes;

public class MidiFile{
	private static final Logger log = Logger.getLogger(MidiFile.class.getName());
	File file;
	
	public MidiFile(String file){
		this.file = new File(file);
	}
	
	public void start(){
		try{
		    Sequence sequence1 = MidiSystem.getSequence(this.file);
	        Sequencer sequencer1 = MidiSystem.getSequencer(false);
	        Transmitter tx1 = sequencer1.getTransmitter();
	        //Receiver rx1 = new MidiReceiver();
	        Receiver rx1 = MidiHandler.getInstance().getReceivers().firstElement();
	        tx1.setReceiver(rx1);
	
	        sequencer1.open();
	        sequencer1.setSequence(sequence1);
	        sequencer1.start();
		}
		catch(Exception e){
			log.log(Level.SEVERE, "Cannot play midi file", e);
		}
	}
	
	public static void main(String[] args){
		Enumeration<String> a = LogManager.getLogManager().getLoggerNames();
		for (;a.hasMoreElements();){
			String name = a.nextElement();
			if (name.contains("de.synesthesy")){
				Logger cu = LogManager.getLogManager().getLogger(name);
				cu.setLevel(Level.ALL);
			}
		}
		for (Handler h : LogManager.getLogManager().getLogger("").getHandlers()) {
		h.setLevel(Level.ALL);
		}
		
		Synesthesy synesthesy = Synesthesy.getInstance();
		PressedNotes pressedNotes = new PressedNotes();
		CacheDispatcher.getInstance().registerCache(pressedNotes);
		
		
		new MidiFile("/Users/Marc/Downloads/teddybear.mid").start();
	}

}
