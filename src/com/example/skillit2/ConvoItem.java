package com.example.skillit2;

public class ConvoItem 
{

	private String author;
	private String recipient;
	private String message;
	private String timestamp;
	private Post relatedPost;
	
	public ConvoItem( Post p, String a, String r, String m, String time )
	{
		relatedPost = p;
		author = a;
		recipient = r;
		message = m;
		timestamp = time;
	}
	
	public Post getRelatedPost()
	{
		return relatedPost;
	}
	
	public String getAuthor()
	{
		return author;
	}
	
	public String getRecipient()
	{
		return recipient;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public String getTimestamp()
	{
		return timestamp;
	}
	
}
