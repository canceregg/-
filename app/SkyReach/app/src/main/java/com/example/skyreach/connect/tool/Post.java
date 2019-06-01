package com.example.skyreach.connect.tool;

import java.io.Serializable;

public class Post implements Serializable {
	private String postId;
	private String userId;
	private String boardId;
	private String postTitle;
	private String postContent;
	private int postClickCnt;
	private int postLikeCnt;

	public Post(String userId, String boardId, String title, String content, int clickCnt, int likeCnt)
	{
		this.postId = "";
		this.userId = userId;
		this.boardId = boardId;
		this.postTitle = title;
		this.postContent = content;
		this.postClickCnt = clickCnt;
		this.postLikeCnt = likeCnt;
	}
	public Post(){}

	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	public String getPostTitle() {
		return postTitle;
	}
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}
	public String getPostContent() {
		return postContent;
	}
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
	public int getPostClickCnt() {
		return postClickCnt;
	}
	public void setPostClickCnt(int postClickCnt) {
		this.postClickCnt = postClickCnt;
	}
	public int getPostLikeCnt() {
		return postLikeCnt;
	}
	public void setPostLikeCnt(int postLikeCnt) {
		this.postLikeCnt = postLikeCnt;
	}
}
