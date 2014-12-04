package com.example.skillit2;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class PostListAdapter extends ArrayAdapter<Post> {

	private Context context;
//	private List<Post> items;
	private TextView topic;
	private TextView details;
	private TextView header;	
	private AppData data;

	public PostListAdapter( Context c, AppData dataSource  )
	{
		super(c, R.layout.single_post_display, dataSource.getPosts());
		context = c;
//		items = dataSource.getPosts();
		data = dataSource;
	}

	@Override
	public int getCount() 
	{
		return data.getPosts().size();
	}

	@Override
	public Post getItem(int position) 
	{	
		return data.getPosts().get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		return position;
	}
	
	public void filterPosts(ArrayList<String> topicList)
	{
		data.filterPosts(topicList);
//		items = data.getPosts();
	}
	
	public void addPost(Post post)
	{
		data.addPost(post);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View vi = LayoutInflater.from(context).inflate(R.layout.single_post_display, parent, false);
		topic = (TextView)vi.findViewById(R.id.post_topic);
		header = (TextView)vi.findViewById(R.id.post_header); 
		details = (TextView)vi.findViewById(R.id.post_details); 
		
		Post item = data.getPosts().get(position);
		
		topic.setText(item.getTopic());
		
		header.setText(item.getHeader());
		if( item.getHeader().contains("help") )
			header.setTextColor(Color.parseColor("#2980b9"));
		else
			header.setTextColor(Color.parseColor("#d35400"));
		
		details.setText(item.getDescription());
		
		return vi;
	}

}
