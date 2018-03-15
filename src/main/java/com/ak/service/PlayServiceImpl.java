package com.ak.service;

import com.ak.dao.service.DataService;
import com.ak.dao.service.DataServiceImpl;
import com.ak.data.Board;
import com.ak.data.Pit;

public class PlayServiceImpl implements PlayService {

	private DataService dataService;

	public PlayServiceImpl() {
		if (this.dataService == null) {
			this.dataService = new DataServiceImpl();
		}
	}

	@Override
	public boolean isGameOver() {
		Board board = dataService.getBoard();
		boolean gameOver = false;
		int firstPlayerCount = getMarbleCount(board.getFirstPlayerPits(), false);
		int secondPlayerCount = getMarbleCount(board.getSecondPlayerPits(), false);
		if (board != null) {
			if (firstPlayerCount == 0 || secondPlayerCount == 0) {
				gameOver = true;
				board.setWinner(
						getMarbleCount(board.getFirstPlayerPits(), true) > getMarbleCount(board.getSecondPlayerPits(),
								true) ? board.getFirstPlayer() : board.getSecondPlayer());
			}

		}
		return gameOver;
	}

	@Override
	public void showBoard() {
		Board board = dataService.getBoard();
		if (board != null) {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			System.out.println(board);
			System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		} else {
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			System.out.println("Board2 display error");
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		}
	}

	@Override
	public void play(int startIndex) {
		Board board = dataService.getBoard();
		// consider startIndex as array index (0-6). During input convert pit nr
		// to array index.
		if (board != null) {
			String player = board.getFirstPlayer();
			String opponent = board.getSecondPlayer();
			Pit[] playerPits = board.getFirstPlayerPits();
			Pit[] opponentPits = board.getSecondPlayerPits();
			if (!board.isFirstPlayerGotTurn()) {
				playerPits = board.getSecondPlayerPits();
				opponentPits = board.getFirstPlayerPits();
				player = board.getSecondPlayer();
				opponent = board.getFirstPlayer();
			}
			// Collect marbles.
			int marbles = playerPits[startIndex].collectMarbles();

			int pitIndex = startIndex + 1;

			// Distribute marbles.
			while (marbles > 0) {
				// If player pit? marble collection done at start index. Proceed
				// with distribution from next pit.
				marbles = distribute(marbles, board, pitIndex, playerPits, player);
				// If marbles still left
				if (marbles == 0) {
					break;
				}
				// Reset start index if marbles still left to distribute
				pitIndex = 0;
				// If opponent pit? distribute from start index.
				marbles = distribute(marbles, board, pitIndex, opponentPits, opponent);
			}
			manageTurn(board);
		}
		// return board;
	}

	private int distribute(int marbles, Board board, int pitArrIndex, Pit[] playerPits, String player) {
		String playerWithTurn = board.isFirstPlayerGotTurn() ? board.getFirstPlayer() : board.getSecondPlayer();
		while (marbles > 0 && pitArrIndex < playerPits.length) {
			if ((!playerWithTurn.equals(player)) && (playerPits[pitArrIndex].isMasterPit())) {
				pitArrIndex++;
				continue;
			} else {
				marbles--;
				playerPits[pitArrIndex].addMarble(1);
			}
			pitArrIndex++;
		}
		board.setLastPitID(pitArrIndex - 1);
		board.setLastMarblePitHolder(player);
		return marbles;
	}

	private void manageTurn(Board board) {
		// Hey! whose turn is this?
		String plyr = board.isFirstPlayerGotTurn() ? board.getFirstPlayer() : board.getSecondPlayer();
		Pit[] playerPits = board.isFirstPlayerGotTurn() ? board.getFirstPlayerPits() : board.getSecondPlayerPits();
		Pit[] opponentPits = board.isFirstPlayerGotTurn() ? board.getSecondPlayerPits() : board.getFirstPlayerPits();
		// if last marble in opponent pit, switch the turn.
		// if last marble in player pit and pit is master pit, dont switch the
		// turn.
		// if last marble in player pit and pit count is 1, collect pit and
		// corresponding opponent pit and add to player's master pit.
		if (!board.getLastMarblePitHolder().equals(plyr)) {
			board.setFirstPlayerGotTurn(!board.isFirstPlayerGotTurn());
		} else {
			Pit lastPit = playerPits[board.getLastPitID()];
			if (!lastPit.isMasterPit()) {
				if (lastPit.getMarbleCount() == 1) {
					Pit oppositePit = opponentPits[opponentPits.length - board.getLastPitID() - 2];
					int collectedMarbles = oppositePit.collectMarbles();
					if (collectedMarbles > 0) {
						collectedMarbles += lastPit.collectMarbles();
						playerPits[board.getMasterPitLocation()].addMarble(collectedMarbles);
					}
				}
				board.setFirstPlayerGotTurn(!board.isFirstPlayerGotTurn());
			} else {
				System.out.println("Last marble in player's master pit");
			}
		}
	}

	@Override
	public String getWinner() {
		return this.dataService.getBoard().getWinner();
	}

	private int getMarbleCount(Pit[] firstPlayerPits, boolean includeMasterPit) {
		int count = 0;
		for (Pit pit : firstPlayerPits) {
			if (pit.isMasterPit() && !includeMasterPit) {
				continue;
			} else {
				count += pit.getMarbleCount();
			}
		}
		return count;
	}

	@Override
	public void initializeBoard(String firstPlayer, String secondPlayer) {
		this.dataService.initializeBoard(firstPlayer, secondPlayer);

	}

	@Override
	public String getFirstPlayer() {
		return this.dataService.getBoard().getFirstPlayer();
	}

	@Override
	public boolean isPlayerGotTurn() {
		return this.dataService.getBoard().isFirstPlayerGotTurn();
	}

	@Override
	public String getSecondPlayer() {
		return this.dataService.getBoard().getSecondPlayer();
	}

	@Override
	public int getMarbleCount(int pitID) {
		Pit[] playerPit = this.dataService.getBoard().isFirstPlayerGotTurn()
				? this.dataService.getBoard().getFirstPlayerPits() : this.dataService.getBoard().getSecondPlayerPits();
		return playerPit[pitID].getMarbleCount();
	}

}
