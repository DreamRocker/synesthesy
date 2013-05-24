package de.synesthesy.music.rhythm;

import java.util.Vector;
import java.util.logging.Logger;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.TransformType;

import de.synesthesy.cache.CachePerChannel;
import de.synesthesy.music.Note.Note;
import de.synesthesy.music.Note.SampledNote;
import de.synesthesy.music.util.ErrorFunction;

/**
 * This class detects a rhythm out of a given note vector
 * 
 * @author Marc Koderer
 *
 */
public class RhythmAnalyzer {
	private static final Logger log = Logger.getLogger( CachePerChannel.class.getName() );
	Vector <SampledNote> playedNotes = new Vector <SampledNote>();
	int sampleRate = 512;
	int looseFactor = 2;
	
	public void registerNote(Note note){
		SampledNote nt = new SampledNote(note.getPitch(), note.getStrength(), note.getTimeStamp());
		playedNotes.add(nt);
	}
	
	public Rhythm analyze(){
		double[] sampledNotes = sampleDataFlat(playedNotes);
		/*
		FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);
		Complex[] cmp = fft.transform (sampledNotes, TransformType.FORWARD);
		for (int i=0; i<cmp.length; i++){
			System.out.println(cmp[i].getReal() + "\t"+cmp[i].getImaginary());
		}
 		*/	
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
		
		long firstTick = notes.get(0).getTimeStamp();
		long lastTick =  notes.get(notes.size()-1).getTimeStamp();
		long delta = lastTick - firstTick;
		//double[] corrVec = new double[(int) ((delta / sampleRate)+1)];
		double[] corrVec = new double[sampleRate];
		long tickRate = delta / sampleRate;
		int noteCnt = 0;
		//for(long tick=firstTick; tick <= lastTick; tick += tickRate){
		for(int i = 0; i < corrVec.length; i++){
			long tick = firstTick + (i*tickRate);
			for (int n = noteCnt; n<notes.size(); n++){
				if ((tick+tickRate)>notes.get(n).getTimeStamp()){
					log.finest("Put "+notes.get(n).toString()+" to "+i + " "+(tick+sampleRate)+">"+notes.get(n).getTimeStamp()+" "+ (notes.get(n).getTimeStamp()-notes.get((n==0)? 0:(n-1)).getTimeStamp()));
					corrVec[i] += notes.get(n).getStrength();
					notes.get(n).setSampleInterval(i);
					notes.get(n).setSampleRate(sampleRate);
					noteCnt++;
				} else{
					break;
				}
			}
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

	public int getSampleRate() {
		return sampleRate;
	}

	public void setSampleRate(int sampleRate) {
		this.sampleRate = sampleRate;
	}
}


