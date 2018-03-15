package com.ak.data;

public class Pit {

	private boolean isMasterPit;
	private int marbleCount, pitID;

	public boolean isMasterPit() {
		return isMasterPit;
	}

	public Pit(boolean isMasterPit, int marbleCount, int pitId) {
		super();
		this.isMasterPit = isMasterPit;
		this.marbleCount = marbleCount;
		this.pitID = pitId;
	}

	public int getMarbleCount() {
		return marbleCount;
	}

	public void addMarble(int marbles) {
		marbleCount += marbles;
	}

	public int collectMarbles() {
		int marbles = this.marbleCount;
		this.marbleCount = 0;
		return marbles;
	}

	public int getPitID() {
		return pitID;
	}

	@Override
	public String toString() {
		return (isMasterPit ? "Master Pit {" : "Pit {") + pitID + " : " + marbleCount + "}";
	}

	public void setMarbleCount(int marbleCount) {
		this.marbleCount = marbleCount;
	}

}
