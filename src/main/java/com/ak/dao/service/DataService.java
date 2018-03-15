package com.ak.dao.service;

import com.ak.data.Board;

public interface DataService {

	public void initializeBoard(String firstPlayer, String secondPlayer);

	public Board getBoard();

}
