package com.example.skyreach.connect.tool;

public class Comment {

	public Comment(String postId, String observerId, String commentContent)
	{
		this.postId = postId;
		this.observerId = observerId;
		this.commentContent = commentContent;
	}
	public Comment(){}
	private String commentId;
	private String postId;
	private String observerId;
	private String commentContent;
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public String getObserverId() {
		return observerId;
	}
	public void setObserverId(String observerId) {
		this.observerId = observerId;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	
	
}
