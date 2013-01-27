package com.example.debts;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity;
import android.app.Dialog;

import com.example.debts.model.*;

public class MainActivity extends Activity {
	private DebtCollection debts;
	private DebtAdapter adapter;
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
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
		debts = new DebtCollection();
		debts.generateRandomCollection();
		
		listView = (ListView) findViewById(R.id.mylist);

		// Define a new Adapter
		// First parameter - Context
		// Second parameter - Layout for the row
		// Third parameter - ID of the TextView to which the data is written
		// Forth - the Array of data

		adapter = new DebtAdapter(this, 
                R.layout.listview_item_layout, debts.getDebts());
		// Assign adapter to ListView
		listView.setAdapter(adapter);
	}

	private void deleteSelected()
	{
		adapter.removeSelectedItems();
	}
	
	private void deleteAll()
	{
		adapter.clear();
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
					
					adapter.add(newDebt);
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
}
