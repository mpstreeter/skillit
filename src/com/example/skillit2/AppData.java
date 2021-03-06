package com.example.skillit2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class AppData 
{
	private ArrayList<Post> posts;
	private ArrayList<Post> filteredPosts;
	
	private static ArrayList<ConvoItem> allMessages = new ArrayList<ConvoItem>();
	
	public AppData()
	{
		posts = new ArrayList<Post>();
		filteredPosts = new ArrayList<Post>();
		generateData();
		
	}
	
	public static ArrayList<ConvoItem> getMessages(Post post)
	{
		ArrayList<ConvoItem> subset = new ArrayList<ConvoItem>();
		for( ConvoItem item: allMessages)
		{
			if(item.getRelatedPost().equals(post))
				subset.add(item);
		}
		if(subset.size() == 0 )
		{
			ConvoItem first = new ConvoItem(post, "Skill-It", "Mike Jones", "You have been matched to work on: " + post.getDescription(), getCurrentTime());
			addMessage(first);
			subset.add(first);
		}
		
		return subset;
	}
	
	public static String getCurrentTime()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public static void addMessage(ConvoItem item)
	{
		allMessages.add(item);
	}
	
	private void generateData()
	{
		addPost(new Post("Mike Jones", Post.TOPICS[0], Post.HEADERS[0], "the latest CS106A pset" ));
		addPost(new Post("Brittany Wallenberg", Post.TOPICS[3], Post.HEADERS[0], "how to swim freestyle so I can pass the swim test" ));
		addPost(new Post("Justin Salloum", Post.TOPICS[0], Post.HEADERS[1], "how to ace CS147" ));
		addPost(new Post("Chris Lee", Post.TOPICS[1], Post.HEADERS[1], "how to read research articles quickly" ));
		addPost(new Post("Andrew Lee", Post.TOPICS[0], Post.HEADERS[0], "the latest CS147 assignment... I have questions about this report" ));
		addPost(new Post("Olivia Pope", Post.TOPICS[8], Post.HEADERS[1], "how to handle everything and anything" ));
		addPost(new Post("Stevie Wonder", Post.TOPICS[4], Post.HEADERS[1], "how to feel the music" ));
		addPost(new Post("Stephanie Jackson", Post.TOPICS[0], Post.HEADERS[0], "picking classes for next quarter. I'm trying to narrow down from 40 units of classes" ));
		addPost(new Post("Stephanie Jackson", Post.TOPICS[2], Post.HEADERS[1], "an easy way to understand telenovelas in Spanish" ));
		addPost(new Post("Bellamy Smith", Post.TOPICS[5], Post.HEADERS[1], "how to cook plantains" ));
		addPost(new Post("Stephanie Jackson", Post.TOPICS[6], Post.HEADERS[0], "using Photoshop on iPhone videos" ));
		addPost(new Post("James Macgyver", Post.TOPICS[7], Post.HEADERS[0], "budgeting for parties and textbooks..." ));
	}
	
	public void addPost( Post post )
	{
		posts.add(0, post);
	}
	
	public Post getPost( int position )
	{
		return getPosts().get(position);
	}
	
	//Filter Posts based on list of topics
	public void filterPosts( ArrayList<String> topicList )
	{	
		filteredPosts.clear();
		for( String topic: topicList )
		{
			for( Post p: posts )
			{
				if( p.getTopic().equals(topic) )
					filteredPosts.add(p);
			}
		}
	}
	
	public ArrayList<Post> getPosts()
	{
		if( filteredPosts.size() > 0 )
			return filteredPosts;
		else
			return posts;
	}
	

}
