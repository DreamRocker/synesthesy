package de.synesthesy.music;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is the representation of a musical key
 * 
 * @author Marc Koderer
 *
 */
public class MusicKey {
	private static final Map<Integer, String> KeyMap = Collections
			.unmodifiableMap(new HashMap<Integer, String>() {
				private static final long serialVersionUID = 1L;
				{
					put(0, "C");
					put(1, "C#");
					put(2, "D");
					put(3, "D#");
					put(4, "E");
					put(5, "F");
					put(6, "F#");
					put(7, "G");
					put(8, "G#");
					put(9, "A");
					put(10, "A#");
					put(11, "B");
				}
			});

	String key;
	boolean isMinor;
	boolean is7;
	boolean isMaj7;
	MusicKey dominant;
	MusicKey subDominant;
	
	public MusicKey(int keyValue){
		key = KeyMap.get(keyValue);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (is7 ? 1231 : 1237);
		result = prime * result + (isMaj7 ? 1231 : 1237);
		result = prime * result + (isMinor ? 1231 : 1237);
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MusicKey other = (MusicKey) obj;
		if (is7 != other.is7)
			return false;
		if (isMaj7 != other.isMaj7)
			return false;
		if (isMinor != other.isMinor)
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String addValues = "";
		if (is7) addValues += "7";
		if (isMaj7) addValues += "Maj7";
		if (isMinor) addValues += " minor";
		return key + addValues;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public boolean isMinor() {
		return isMinor;
	}

	public void setMinor(boolean isMinor) {
		this.isMinor = isMinor;
	}

	public boolean isIs7() {
		return is7;
	}

	public void setIs7(boolean is7) {
		this.is7 = is7;
	}

	public boolean isMaj7() {
		return isMaj7;
	}

	public void setMaj7(boolean isMaj7) {
		this.isMaj7 = isMaj7;
	}

	public MusicKey getDominant() {
		return dominant;
	}

	public void setDominant(MusicKey dominant) {
		this.dominant = dominant;
	}

	public MusicKey getSubDominant() {
		return subDominant;
	}

	public void setSubDominant(MusicKey subDominant) {
		this.subDominant = subDominant;
	}


}
