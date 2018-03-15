/**
 * 
 */
package com.ak.main;

import java.util.Scanner;

import com.ak.service.PlayService;
import com.ak.service.PlayServiceImpl;

/**
 * @author Amitk
 *
 */
public class Main {

	private static String[] getPlayerNames(Scanner scanner) {
		System.out.print("Enter first player name: ");
		String playerOne = scanner.next();
		String playerTwo = "";
		while (playerTwo.equals("")) {
			System.out.print("Enter second player name: ");
			String player = scanner.next();
			if (!playerTwo.equalsIgnoreCase(playerOne)) {
				playerTwo = player;
			} else {
				System.out.println("Chosen player name is in use already. Choose another name.");
			}
		}
		String[] players = { playerOne, playerTwo };
		System.out.println("Starting new game with player: " + playerOne + " and player: " + playerTwo);
		return players;
	}

	public static void main(String[] args) {
		System.out.println("Welcome players! Let's start new game ...");
		Scanner scanner = new Scanner(System.in);

		String[] players = getPlayerNames(scanner);

		PlayService playService = new PlayServiceImpl();

		playService.initializeBoard(players[0], players[1]);

		playService.showBoard();

		System.out.println("Game initialized. Let's play ...");

		playGame(playService, scanner);

		System.out.println("Game over " + playService.isGameOver() + " and the winner is " + playService.getWinner());

		scanner.close();
		System.out.println("Game closed. Thnak you!");

	}

	private static void playGame(PlayService playService, Scanner scanner) {
		while (!playService.isGameOver()) {
			System.out.println("Player "
					+ (playService.isPlayerGotTurn() ? playService.getFirstPlayer() : playService.getSecondPlayer())
					+ " got the turn.");
			int pitID = getValidPitID(playService, scanner);
			playService.play(pitID);
			playService.showBoard();
		}
	}

	private static int getPitIndex(int pitNr) {
		return pitNr - 1;
	}

	private static int getValidPitID(PlayService playService, Scanner scanner) {
		int pitID = -1;
		while (pitID < 0 || pitID > 6) {
			System.out.println("Please enter the valid pit number (1-6) you want to start with.");
			String pitNr = scanner.next();
			if (isValidPitId(playService, pitNr)) {
				pitID = Integer.parseInt(pitNr);

			}
		}
		// During input convert pit nr to array index.
		pitID = getPitIndex(pitID);
		return pitID;
	}

	private static boolean isValidPitId(PlayService playService, String pitNr) {
		boolean validPitId = false;
		try {
			int pitID = Integer.parseInt(pitNr);
			if (0 < pitID && pitID < 7) {
				pitID = getPitIndex(pitID);
				if (playService.getMarbleCount(pitID) > 0) {
					validPitId = true;
				} else {
					System.out.println("Pit is empty. Please choose another pit.");
				}
			} else {
				System.out.println("Pit id must be between and including 1 to 6.");
			}
		} catch (NumberFormatException e) {
			System.out.println(pitNr + " is not a valid pit number.");
		}
		return validPitId;
	}

}
