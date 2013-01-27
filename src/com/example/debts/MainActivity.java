package com.example.debts;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;

import com.example.debts.model.*;
import com.example.debts.repository.DebtsDbAdapter;

public class MainActivity extends Activity {
	private DebtAdapter adapter;
	private ListView listView;
	private DebtsDbAdapter dbHelper;
	private String[] columns;
	private int[] to;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		dbHelper = new DebtsDbAdapter(this);
		dbHelper.open();
		 
		//Clean all data
		dbHelper.deleteAllDebts();
		//Add some data
		dbHelper.insertSomeDebts();
		
		setupListView();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.deleteSelectedMenuItem:
	        deleteSelected();
	        return true;
	    case R.id.deleteAllMenuItem:
	        deleteAll();
	        return true;
	    case R.id.addNewDebtMenuItem:
	    	addDebt();
	    	return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	private void setupListView()
	{
		Cursor cursor = dbHelper.fetchAllDebts();
		 
		// The desired columns to be bound
		columns = new String[] {
				DebtsDbAdapter.KEY_NAME,
				DebtsDbAdapter.KEY_SUM,
				DebtsDbAdapter.KEY_TYPE
		};
		
		// the XML defined views which the data will be bound to
		to = new int[] { 
				R.id.txtTitle,
				R.id.deleteCheckbox,
		};
		
		//debts = new DebtCollection();
		//debts.generateRandomCollection();
		
		listView = (ListView) findViewById(R.id.mylist);
		
		// create the adapter using the cursor pointing to the desired data 
		//as well as the layout information
		adapter = new DebtAdapter(this, R.layout.listview_item_layout,
				cursor, columns, to, 0);
		// Assign adapter to ListView
		listView.setAdapter(adapter);
	}

	private void deleteSelected()
	{
		adapter.removeSelectedItems();
	}
	
	private void deleteAll()
	{
		dbHelper.deleteAllDebts();
	}
	
	private void addDebt()
	{
		// custom dialog
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.add_debt_dialog_layout);
		dialog.setTitle("Add new debt");

		Button dialogAddButton = (Button) dialog.findViewById(R.id.dialogButtonAdd);
		// if button is clicked, close the custom dialog
		dialogAddButton.setOnClickListener(new android.widget.Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText nameEditText = (EditText) dialog.findViewById(R.id.nameEditText);
				EditText sumEditText = (EditText) dialog.findViewById(R.id.sumEditText);
				CheckBox typeCheckBox = (CheckBox) dialog.findViewById(R.id.debtTypeCheckbox);
				
				if (!nameEditText.getText().toString().equals("") &&
					!sumEditText.getText().toString().equals(""))
				{
					String sum = sumEditText.getText().toString();
					Debt newDebt = new Debt(nameEditText.getText().toString(),
											Double.parseDouble(sum),
											typeCheckBox.isChecked());
					
					dbHelper.createDebt(newDebt);
					refreshListView();
					dialog.dismiss();
				}
				else
				{
					if (nameEditText.getText().toString().equals(""))
					{
						nameEditText.setError("Please add a name.");
					}
					if (sumEditText.getText().toString().equals(""))
					{
						sumEditText.setError("Please add a sum.");
					}
				}
			}
		});
		
		Button dialogCancelButton = (Button) dialog.findViewById(R.id.dialogButtonCancel);
		// if button is clicked, close the custom dialog
		dialogCancelButton.setOnClickListener(new android.widget.Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.show();
	}
	
	/**
	 * Refreshes the list view.
	 */
	private void refreshListView()
	{
		Cursor cursor = dbHelper.fetchAllDebts();   

		adapter = new DebtAdapter(this, R.layout.listview_item_layout,
				cursor, columns, to, 0);

		listView.setAdapter(adapter);
	}
}
