package com.example.skillit2;

public class Post 
{
	private String author;
	private String topic;
	private String header; //"I want to learn", "I want to share"
	private String description;
	
	public static String[] TOPICS = { "Engineering", "Humanities", "Languages", "Sports", "Music", "Cooking", "Design", 
			"Personal Finance", "Other" };
	public static String[] HEADERS = { "I need help with...", "I want to share..." };
	
	public Post( String author, String topic, String header, String description )
	{
		this.author = author;
		this.topic = topic;
		this.header = header;
		this.description = description;
	}
	
	public String getAuthor()
	{
		return author;
	}
	
	public String getTopic()
	{
		return topic;
	}
	
	public String getHeader()
	{
		return header;
	}
	
	public String getDescription()
	{
		return description;
	}

}
