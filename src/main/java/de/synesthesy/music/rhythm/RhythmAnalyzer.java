package de.synesthesy.music.rhythm;

import java.util.Vector;

import org.apache.commons.math.stat.correlation.PearsonsCorrelation;

import de.synesthesy.music.SampledNote;
import de.synesthesy.music.util.ErrorFunction;

import themidibus.Note;

/**
 * This class detects a rhythm out of a given note vector
 * 
 * @author Marc Koderer
 *
 */
public class RhythmAnalyzer {
	Vector <SampledNote> playedNotes = new Vector <SampledNote>();
	long sampleRate = 10000;
	int looseFactor = 2;
	
	public void registerNote(Note note){
		SampledNote nt = new SampledNote(note.getPitch(), note.getStrength(), note.getTicks());
		System.out.println("Register note:" +nt.toString()+playedNotes.size());
		playedNotes.add(nt);
	}
	
	public Rhythm analyze(){
		double[] sampledNotes = sampleDataFlat(playedNotes);
		int sample = findInterval(sampledNotes);
		Vector <SampledNote> rhythm = new Vector <SampledNote>();
		for(SampledNote sm:playedNotes){
			if(sm.getSampleInterval() <= sample){
				rhythm.add(sm);
				sm.setPauseAfterNote(0);
			}
			else break;
		}
		int lastNoteSample=rhythm.get(rhythm.size()-1).getSampleInterval();
		if (lastNoteSample < sample){
			rhythm.get(rhythm.size()-1).setPauseAfterNote(sample-lastNoteSample);
		}
		return new Rhythm(rhythm);
	}
	
	/**
	 * This functions samples data with the given
	 * sample rate.
	 * 
	 * @param notes
	 */
	public double[] sampleDataFlat(Vector <SampledNote> notes){
		
		long firstTick = notes.get(0).getTicks();
		long lastTick =  notes.get(notes.size()-1).getTicks();
		long delta = lastTick - firstTick;
		double[] corrVec = new double[(int) (delta / sampleRate)+1];
		
		int arrayCnt = 0;
		int noteCnt = 0;
		for(long tick=firstTick; tick < lastTick; tick += sampleRate){
			for (int n = noteCnt; n<notes.size(); n++){
				if ((tick+sampleRate)>notes.get(n).getTicks()){
					System.out.println("Put "+notes.get(n).toString()+" to "+arrayCnt + " "+(tick+sampleRate)+">"+notes.get(n).getTicks()+" "+ (notes.get(n).getTicks()-notes.get((n==0)? 0:(n-1)).getTicks()));
					corrVec[arrayCnt] += notes.get(n).getStrength();
					notes.get(n).setSampleInterval(arrayCnt);
					notes.get(n).setSampleRate(sampleRate);
					noteCnt++;
				} else{
					break;
				}
			}
			arrayCnt++;
		}
		return corrVec;
	}
	/** 
	 * Returns the best matching interval
	 * 
	 * @param in an array of rhythmic values
	 * @return the best matching array key
	 *         if no match was found return = in.length
	 */
	public int  findInterval(double[] in){
		System.out.println("Input:");
		for (int m=0; m< in.length; m++){
			if (in[m] > 0.0) {
				System.out.print("\n"+in[m]);
			}else{
				System.out.print(".");
	
			}
		}
		System.out.println("");
		
		double lastErr = -1;
		double highestDelta = 0;
		int maxFall = 0;
		
		for (int i=1; i<in.length; i++){			
			double err = ErrorFunction.getInstance().calculateError(in, i, looseFactor);
			if (lastErr != -1){
				if ((lastErr-err) > highestDelta){
					highestDelta = lastErr-err;
					maxFall = i;
				}
			} 
			System.out.println(i+"\t"+ErrorFunction.getInstance().calculateError(in, i)+"\t"+ErrorFunction.getInstance().calculateError(in, i,1)+"\t"+ErrorFunction.getInstance().calculateError(in, i,2)+"\t");
			if (in.length*0.1 < i){
				// ignore the first 10 % of the values
				lastErr = err;
			}
		}
		System.out.println("Return " + maxFall);	
		return maxFall;
	}
	
	public int getLooseFactor() {
		return looseFactor;
	}

	public void setLooseFactor(int looseFactor) {
		this.looseFactor = looseFactor;
	}

	public Vector<SampledNote> getPlayedNotes() {
		return playedNotes;
	}

	public void setPlayedNotes(Vector<SampledNote> playedNotes) {
		this.playedNotes = playedNotes;
	}

	public long getSampleRate() {
		return sampleRate;
	}

	public void setSampleRate(long sampleRate) {
		this.sampleRate = sampleRate;
	}
}


