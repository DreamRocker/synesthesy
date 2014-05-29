package de.synesthesy.midi.file;

import java.io.File;
import java.util.Enumeration;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
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
	boolean withSound;
	Sequencer sequencer1, sequencer2;
	MidiReceiver player_receiver;
	
	public MidiFile(String file){
		this(file, true, null);
	}
		
	public MidiFile(String file, boolean withSound, MidiReceiver player_receiver){
		this.file = new File(file);
		this.withSound = withSound;
		this.player_receiver = player_receiver;
		
		try{
			sequencer1 = MidiSystem.getSequencer(false);
	        Transmitter tx1 = sequencer1.getTransmitter();
	        Receiver rx1 = MidiHandler.getInstance().getReceivers().firstElement();
	        tx1.setReceiver(rx1);
	
	        if (withSound){
			    sequencer2 = MidiSystem.getSequencer(true);
	        }
	        load_file();
		}
		catch(Exception e){
			log.log(Level.SEVERE, "Cannot open play midi file", e);
		}
	}
	
	public void start(){
		sequencer1.start();
		if (this.withSound){
			if (this.player_receiver != null){
				Transmitter tx1;
				try {
					tx1 = sequencer2.getTransmitter();
				    tx1.setReceiver(player_receiver);
				} catch (MidiUnavailableException e) {
					e.printStackTrace();
				}
			}
			sequencer2.start();
		}
	}
		
	private void load_file(){
		try{
			Sequence sequence1 = MidiSystem.getSequence(this.file);
			this.sequencer1.open();
			this.sequencer1.setSequence(sequence1);
			if (sequencer2 != null){
				this.sequencer2.open();
				this.sequencer2.setSequence(sequence1);
			}
		}
		catch(Exception e){
			log.log(Level.SEVERE, "Cannot play midi file", e);			
		}
	}
	
	public void stop(){
		this.pause();
		load_file();
	}
	
	public void pause(){
		this.sequencer1.stop();
		if(this.sequencer2 != null)
			this.sequencer2.stop();

		
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
		MidiFile m = new MidiFile("/Users/Marc/Downloads/teddybear.mid");
		m.start();
		try {
			System.out.println("Test1");
			Thread.sleep(2000);

			System.out.println("Test2");
			m.pause();
			Thread.sleep(1000);
			m.start();

			Thread.sleep(1000);
			m.stop();
			
			Thread.sleep(1000);
			m.start();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
