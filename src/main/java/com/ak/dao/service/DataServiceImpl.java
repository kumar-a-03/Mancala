package com.ak.dao.service;

import com.ak.data.Board;

public class DataServiceImpl implements DataService {

	private Board borad;

	@Override
	public void initializeBoard(String firstPlayer, String secondPlayer) {
		this.borad = new Board(firstPlayer, secondPlayer);

	}

	@Override
	public Board getBoard() {
		return this.borad;
	}

}
