package com.skyreach.yinliu.Main.connect.tool;

public class Board {

	public Board(String name, String message, int postCnt, int clickCnt)
	{
		boardId = "";
		boardName = name;
		boardMessage = message;
		boardPostCnt = postCnt;
		boardClickCnt = clickCnt;
	}
	public Board()
	{
		boardId = "";
		boardName = "";
		boardPostCnt = 0;
		boardClickCnt = 0;
	}

	private String boardId;
	private String boardName;
	private int boardPostCnt;
	private int boardClickCnt;
	private String boardMessage;
	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	public String getBoardName() {
		return boardName;
	}
	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}
	public int getBoardPostCnt() {
		return boardPostCnt;
	}
	public void setBoardPostCnt(int boardPostCnt) {
		this.boardPostCnt = boardPostCnt;
	}
	public int getBoardClickCnt() {
		return boardClickCnt;
	}
	public void setBoardClickCnt(int boardClickCnt) {
		this.boardClickCnt = boardClickCnt;
	}
	
	
}
