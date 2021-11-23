public class Word {

	private String str;
	private int frequency;

	// Parameterized constructor
	public Word(String str) {
		this.str = str;
		this.updateFrequency();
	}

	// *************************
	// Getter and setter methods
	// *************************

	public String getStr() {
		return str;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	// ***************
	// Utility methods
	// ***************

	// Method to update frequency after every match
	public void updateFrequency() {
		this.frequency = this.frequency + 1;
	}

	@Override
	public String toString() {
		return String.format(str);
	}
}