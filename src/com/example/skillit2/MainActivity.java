package com.example.skillit2;


import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity 
{
	private AppData data;

	private PostListAdapter newsFeedAdapter;
	private ListView lvNewsFeed;

	private Button addPost;
	private Button filterPosts;

	//Filter Dialog
	private CheckBox cb1, cb2, cb3, cb4, cb5, cb6, cb7, cb8, cb9;
	private ArrayList<Integer> selectedFilters;

	//Filter text on main screen
	private TextView tv_filters;
	
	//Dialog for Add Post
	private Spinner sp_topics;
	private RadioButton rb_help;
	private RadioButton rb_share;
	private RadioGroup rg_buttons;
	private EditText et_description;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Change color of action bar
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3498db")));

		data = new AppData();
		selectedFilters = new ArrayList<Integer>();

		addPost = (Button) findViewById( R.id.btn_addPost );
		filterPosts = (Button) findViewById( R.id.btn_filterPosts );
		
		tv_filters = (TextView) findViewById( R.id.tv_filters );

		lvNewsFeed = (ListView) findViewById( R.id.lv_newsfeed );
		newsFeedAdapter = new PostListAdapter(this, data);
		lvNewsFeed.setAdapter( newsFeedAdapter );

		//Setup click listeners
		setupListViewClickListener();  
		setupFilterClickListener();
		setupAddPostClickListener();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	//Click lets you learn more about post and choose whether to contact post-er
	private void setupListViewClickListener()
	{
		lvNewsFeed.setOnItemClickListener( new AdapterView.OnItemClickListener() 
		{
			private Post selectedPost;
			
			public void onItemClick(AdapterView<?> parentAdapter, View view, int position, long id) 
			{
				selectedPost = data.getPost(position);
				showDialog(view);
			}
			
			public void showDialog( View view )
			{
				new AlertDialog.Builder(view.getContext())
				.setMessage( getMessage() )
				.setPositiveButton("I'm in", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) { 
						Intent nextScreen = new Intent(getApplicationContext(), ConversationActivity.class);
						nextScreen.putExtra("otherPerson",  selectedPost.getAuthor());
						nextScreen.putExtra("header",  selectedPost.getHeader());
						nextScreen.putExtra("description",  selectedPost.getDescription());
						nextScreen.putExtra("topic",  selectedPost.getTopic());
						startActivity(nextScreen);
					}
				})
				.setNegativeButton("Not now...", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) { 
						// do nothing
					}
				})
				.show();
			}

			public String getMessage()
			{
				if( selectedPost.getHeader().contains("help") )
					return selectedPost.getAuthor() + " needs help on: \"" + selectedPost.getDescription() + "\".\n\nCan you help?";
				else
					return selectedPost.getAuthor() + " wants to share: \"" + selectedPost.getDescription() + "\".\n\nWanna learn?";
			}
			

		});
	}

	private void setupFilterClickListener()
	{
		filterPosts.setOnClickListener( new OnClickListener()  
		{
			public void addListenerOnCheckbox(CheckBox cb) {
				cb.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						//is box checked?
						CheckBox box = (CheckBox)v;
						if ( box.isChecked() )
							selectedFilters.add( findId(box.getText().toString()) );
						else
							selectedFilters.remove( new Integer(findId(box.getText().toString())) );
					}
					
					private int findId( String text )
					{
						for( int i=0; i<Post.TOPICS.length; i++ )
						{
							String s = Post.TOPICS[i];
							if( text.equals(s))
								return i;
						}
						return -1;
					}
				});

			}

			public void onClick(View view) 
			{
				LayoutInflater li = LayoutInflater.from(view.getContext());
                View layout = li.inflate(R.layout.dialog_filter_results, null);

                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setView(layout);				
				alert.setTitle("Filter Posts");

				//Checkboxes
				for(int i=1; i<=9; i++)
				{
					int id = getResources().getIdentifier("cb" + i, "id", getPackageName());
					CheckBox cb = (CheckBox)layout.findViewById(id);
					addListenerOnCheckbox(cb);
					
					if( selectedFilters.contains(i-1) )
						cb.setChecked(true);
				}
				
				//Add buttons
				alert.setPositiveButton("Search", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) 
					{ 
						Toast.makeText(getApplicationContext(), "Preferences saved", Toast.LENGTH_LONG).show();
						ArrayList<String> topicList = new ArrayList<String>();
						
						String filters = "Filters: ";
						for( int i: selectedFilters )
						{
							topicList.add(Post.TOPICS[i]);
							filters += Post.TOPICS[i] + ", ";
						}
						
						if(filters.trim().length() > "Filters: ".length())
						{
							tv_filters.setText(filters.substring(0, filters.length()-2));
							tv_filters.setVisibility(View.VISIBLE);
						}
						else
						{
							tv_filters.setVisibility(View.GONE);
						}
						
						newsFeedAdapter.filterPosts(topicList);
						newsFeedAdapter.notifyDataSetInvalidated();
					}
				});
				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) 
					{ 
						// do nothing
					}
				});

				alert.show();
				
			}

		});
	}

	private void setupAddPostClickListener()
	{
		addPost.setOnClickListener( new OnClickListener()  
		{
			public void onClick(View view) 
			{
				LayoutInflater li = LayoutInflater.from(view.getContext());
                View layout = li.inflate(R.layout.dialog_add_post, null);

                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setView(layout);				
				alert.setTitle("Add Post");

				//Spinner 1: Add list of topics to spinner
				sp_topics = (Spinner)layout.findViewById(R.id.spinner_postTopic); 
				ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(view.getContext(),
						android.R.layout.simple_spinner_item, Post.TOPICS);
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				sp_topics.setAdapter(dataAdapter);
				
				//Radio buttons
				rg_buttons = (RadioGroup)layout.findViewById(R.id.rg_radioButtons);
				rb_help = (RadioButton)layout.findViewById(R.id.rb_help);
				rb_share = (RadioButton)layout.findViewById(R.id.rb_share);
				
				//Edit Text
				et_description = (EditText)layout.findViewById(R.id.et_postDescription);
				
				//Add buttons
				alert.setPositiveButton("Post", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) { 
						String topic = sp_topics.getSelectedItem().toString();
						String desc = et_description.getText().toString().trim();
						
						// get selected radio button from radioGroup
						int selectedId = rg_buttons.getCheckedRadioButtonId();
						Post post;
						if( rb_help.getId() == selectedId )
							post = new Post("Mike Jones", topic, Post.HEADERS[0], desc );
						else
							post = new Post("Mike Jones", topic, Post.HEADERS[1], desc );
						
						newsFeedAdapter.addPost(post);
						newsFeedAdapter.notifyDataSetInvalidated();
					}
				});
				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) { 
						// do nothing
					}
				});

				alert.show();	
			}
		});
	}

}
