package de.synesthesy.music;

import themidibus.Note;

public class SampledNote extends Note implements Cloneable {
	private int sampleInterval;
	private long sampleRate;
	private int pauseAfterNote;

	public SampledNote(int key, int strength, long ticks){
		super (key, strength, ticks);
	}
	
	public int getSampleInterval() {
		return sampleInterval;
	}
	public void setSampleInterval(int sampleInterval) {
		this.sampleInterval = sampleInterval;
	}
	public long getSampleRate() {
		return sampleRate;
	}
	public void setSampleRate(long sampleRate) {
		this.sampleRate = sampleRate;
	}
	public int getPauseAfterNote() {
		return pauseAfterNote;
	}

	public void setPauseAfterNote(int pauseAfterNote) {
		this.pauseAfterNote = pauseAfterNote;
	}
	@Override
	public String toString(){
		String str =  super.toString();
		for (int i=0; i<pauseAfterNote; i++){
			str += ".";
		}
		return str;
	}

}
