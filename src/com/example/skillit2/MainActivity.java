package com.example.skillit2;


import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
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
	private Button filterResults;

	private CheckBox cb1, cb2, cb3, cb4, cb5, cb6, cb7, cb8, cb9;
	private ArrayList<Integer> selectedFilters;

	//	private Spinner topics;
	//	private Spinner starters;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		data = new AppData();
		selectedFilters = new ArrayList<Integer>();

		addPost = (Button) findViewById( R.id.tv_addPost );
		filterResults = (Button) findViewById( R.id.tv_filterResults );

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
			public void onItemClick(AdapterView<?> parentAdapter, View view, int position, long id) 
			{
				RelativeLayout clickedView = (RelativeLayout) view;
				TextView tv = (TextView) clickedView.findViewById(R.id.post_topic);
				String text = (String) tv.getText();

				Post post = data.getPost(position);

				new AlertDialog.Builder(view.getContext())
				.setMessage("Do you want to work with " + post.getAuthor() + " on " + post.getDescription() + "?" )
				.setPositiveButton("I'm in", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) { 
						// continue with delete
					}
				})
				.setNegativeButton("Not now...", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) { 
						// do nothing
					}
				})
				.show();

			}
		});
	}

	private void setupFilterClickListener()
	{
		filterResults.setOnClickListener( new OnClickListener()  
		{
			public void onClick(View view) 
			{
				AlertDialog dialog; 

				AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
				builder.setTitle("Filter Results");
				builder.setMultiChoiceItems(Post.TOPICS, null,
						new DialogInterface.OnMultiChoiceClickListener() {
					// indexSelected contains the index of item (of which checkbox checked)
					@Override
					public void onClick(DialogInterface dialog, int indexSelected,
							boolean isChecked) {
						if (isChecked) {
							// If the user checked the item, add it to the selected items
							// write your code when user checked the checkbox 
							if(!selectedFilters.contains(indexSelected))
								selectedFilters.add(indexSelected);
						} else if (selectedFilters.contains(indexSelected)) {
							// Else, if the item is already in the array, remove it 
							// write your code when user Unchecked the checkbox 
							selectedFilters.remove(Integer.valueOf(indexSelected));
						}
					}
				})
				// Set the action buttons
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						//  Your code when user clicked on OK
						Toast.makeText(getApplicationContext(), "Preferences saved", Toast.LENGTH_LONG).show();
						ArrayList<String> topicList = new ArrayList<String>();
						for( int i: selectedFilters )
							topicList.add(Post.TOPICS[i]);
						
						newsFeedAdapter.filterPosts(topicList);
						newsFeedAdapter.notifyDataSetInvalidated();
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						//  Your code when user clicked on Cancel
						selectedFilters.clear();
					}
				});
				
				dialog = builder.create();//AlertDialog dialog; create like this outside onClick
				dialog.show();
			}

		});
	}

	private void setupAddPostClickListener()
	{
		addPost.setOnClickListener( new OnClickListener()  
		{
			public void onClick(View view) 
			{
				new AlertDialog.Builder(view.getContext())
				.setMessage("Add Post...")
				.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) { 
						// continue with delete
					}
				})
				.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) { 
						// do nothing
					}
				})
				.show();

				//				LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
				//		        View layout = inflater.inflate(R.layout.dialog_add_post, (ViewGroup) findViewById(R.id.root));
				//				
				//				// custom dialog
				//				final Dialog dialog = new Dialog(view.getContext());
				//				dialog.setContentView(R.layout.dialog_add_post);
				//				dialog.setTitle("Add Post");
				//				
				//				// Add list of topics to spinner
				//				topics = (Spinner) findViewById(R.id.spinner_postTopic); 
				//				List<String> list = new ArrayList<String>();
				//				list.add("Cooking");
				//				list.add("Music");
				//				list.add("CS106A");
				//				ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(view.getContext(),
				//						android.R.layout.simple_spinner_item, list);
				//				// dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				//				topics.setAdapter(dataAdapter);
				//
				//				//Add two choice to spinner for post description sentence starter
				//				starters = (Spinner) findViewById(R.id.spinner_postStarter); 
				//				List<String> list2 = new ArrayList<String>();
				//				list2.add("Help me with...");
				//				list2.add("I can share...");
				//				ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(view.getContext(),
				//						android.R.layout.simple_spinner_item, list2);
				//				// dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				//				starters.setAdapter(dataAdapter2);
				//
				//				dialog.show();	
			}
		});
	}

}
