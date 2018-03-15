package com.ak.service;

public interface PlayService {

	public void initializeBoard(String firstPlayer, String secondPlayer);

	public boolean isGameOver();

	public void showBoard();

	public void play(int startIndex);

	public String getWinner();

	public String getFirstPlayer();

	public boolean isPlayerGotTurn();

	public String getSecondPlayer();

	public int getMarbleCount(int pitID);

}
