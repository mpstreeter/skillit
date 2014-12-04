package com.example.skillit2;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ConversationActivity extends Activity 
{	
	ListView lv_conversation;
	ConversationListAdapter listAdapter;

	Post post;

	TextView tv_title;
	EditText et_message;
	ImageButton ib_writeMessage;


	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conversation);

		//Change color of action bar
		ActionBar bar = getActionBar(); 
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3498db")));

		tv_title = (TextView) findViewById( R.id.tv_title );
		et_message = (EditText) findViewById( R.id.et_writeMessage );
		ib_writeMessage = (ImageButton) findViewById( R.id.ib_sendMessage );
		setWriteMessageClickListener();

		Intent i = getIntent();

		String otherPerson = i.getExtras().getString("otherPerson");
		String header = i.getExtras().getString("header");
		String description = i.getExtras().getString("description");
		String topic = i.getExtras().getString("topic");

		post = new Post(otherPerson, topic, header, description);

		ArrayList<ConvoItem> messages = AppData.getMessages(post);

		tv_title.setText( "Conversation with " + otherPerson );

		lv_conversation = (ListView) findViewById( R.id.lv_conversation );
		listAdapter = new ConversationListAdapter(this, messages);
		lv_conversation.setAdapter( listAdapter );

	}

	private void setWriteMessageClickListener()
	{
		ib_writeMessage.setOnClickListener( new OnClickListener() {

			@Override
			public void onClick(View view) 
			{
				String message = et_message.getText().toString();
				if( message.trim().length() == 0 )
				{
					Toast.makeText(view.getContext(), "Please enter text.", Toast.LENGTH_SHORT).show();
				}
				else
				{
					ConvoItem item = new ConvoItem(post, "Mike Jones", post.getAuthor(), message, AppData.getCurrentTime());
					AppData.addMessage(item);
					listAdapter.add(item);
					listAdapter.notifyDataSetInvalidated();
					et_message.setText("");
				}
			}

		});
	}

}
