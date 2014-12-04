package com.example.skillit2;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ConversationListAdapter extends ArrayAdapter<ConvoItem> 
{
	private ArrayList<ConvoItem> messages;
	private Context context;
	
	private TextView tv_author;
	private TextView tv_message;
	private TextView tv_timestamp;
	
	public ConversationListAdapter(Context c, ArrayList<ConvoItem> messages) {
		super(c, R.layout.single_convo_display, messages);
		context = c;
		this.messages = messages;
	}

	@Override
	public int getCount() 
	{
		return messages.size();
	}

	@Override
	public ConvoItem getItem(int position) 
	{	
		return messages.get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		return position;
	}
	
	public void addMessage(ConvoItem item)
	{
		messages.add(item);
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View vi = LayoutInflater.from(context).inflate(R.layout.single_convo_display, parent, false);
		tv_author = (TextView)vi.findViewById(R.id.tv_name);
		tv_message = (TextView)vi.findViewById(R.id.tv_message); 
		tv_timestamp = (TextView)vi.findViewById(R.id.tv_timestamp); 
		
		ConvoItem item = messages.get(position);
		
		tv_author.setText(item.getAuthor());
		tv_message.setText(item.getMessage());
		tv_timestamp.setText(item.getTimestamp());
		
		return vi;
	}

}
