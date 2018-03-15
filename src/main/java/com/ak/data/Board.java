package com.ak.data;

import java.util.Arrays;

public class Board {

	private Pit[] firstPlayerPits = { new Pit(false, 4, 1), new Pit(false, 4, 2), new Pit(false, 4, 3),
			new Pit(false, 4, 4), new Pit(false, 4, 5), new Pit(false, 4, 6), new Pit(true, 0, 7) };
	private Pit[] secondPlayerPits = { new Pit(false, 4, 1), new Pit(false, 4, 2), new Pit(false, 4, 3),
			new Pit(false, 4, 4), new Pit(false, 4, 5), new Pit(false, 4, 6), new Pit(true, 0, 7) };
	private String firstPlayer, secondPlayer, lastMarblePitHolder, winner = "No Winner";
	private int lastPitID = 0;
	private boolean isFIrstPlayerGotTurn = true;
	private static final int MASTER_PIT_LOCATION = 6;
	private static final int START_PIT_LOCATION = 0;

	public Board(String player1, String player2) {
		super();
		this.firstPlayer = player1;
		this.secondPlayer = player2;
	}

	public Pit[] getFirstPlayerPits() {
		return firstPlayerPits;
	}

	public Pit[] getSecondPlayerPits() {
		return secondPlayerPits;
	}

	public String getSecondPlayer() {
		return secondPlayer;
	}

	public String getLastMarblePitHolder() {
		return lastMarblePitHolder;
	}

	public int getLastPitID() {
		return lastPitID;
	}

	public void setLastPitID(int lastPitID) {
		this.lastPitID = lastPitID;
	}

	public void setLastMarblePitHolder(String lastMarblePitHolder) {
		this.lastMarblePitHolder = lastMarblePitHolder;
	}

	public String getFirstPlayer() {
		return firstPlayer;
	}

	public boolean isFirstPlayerGotTurn() {
		return isFIrstPlayerGotTurn;
	}

	public void setFirstPlayerGotTurn(boolean playerGotTurn) {
		this.isFIrstPlayerGotTurn = playerGotTurn;
	}

	public int getMasterPitLocation() {
		return MASTER_PIT_LOCATION;
	}

	@Override
	public String toString() {
		Pit[] seconPlayerPitCopy = Arrays.copyOf(secondPlayerPits, secondPlayerPits.length);
		return "SecondPlayerPits=" + Arrays.toString(revertPits(seconPlayerPitCopy)) + "\nFirstPlayerPits=\t\t     "
				+ Arrays.toString(firstPlayerPits) + "\n" + "--------------------------------------" + "\n"
				+ "First Player = " + firstPlayer + ", Second Player = " + secondPlayer + ", Last Marble Pit Holder = "
				+ lastMarblePitHolder + ", Last Pit ID = " + lastPitID + ", Does Player Got Turn? = "
				+ isFIrstPlayerGotTurn;
	}

	private Pit[] revertPits(Pit[] playerPits) {
		int start = getStartPitLocation();
		int end = getMasterPitLocation();
		while (start != end) {
			Pit pit = playerPits[start];
			playerPits[start] = playerPits[end];
			playerPits[end] = pit;
			start++;
			end--;
		}
		return playerPits;
	}

	public int getStartPitLocation() {
		return START_PIT_LOCATION;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}
}
