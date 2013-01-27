package com.example.debts.model;

import java.util.ArrayList;

import com.example.debts.R;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class DebtAdapter extends SimpleCursorAdapter
{
    Context context;
    int layoutResourceId;
    ArrayList<Boolean> checked;
    
    /**
     * Constructor.
     */
	public DebtAdapter(Context context, int layoutResourceId, Cursor c,
            String[] from, int[] to, int flags)
    {
    	super(context, layoutResourceId, c, from, to, flags);
        
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.checked = new ArrayList<Boolean>();
        
        for (int i = 0; i < c.getCount(); i++)
        {
        	checked.add(false);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	if(convertView == null)
    	{
            convertView = View.inflate(context, R.layout.listview_item_layout, null);
    	}
        View row = convertView;
  
        this.getCursor().moveToPosition(position);
        
        TextView main = (TextView) convertView.findViewById(R.id.txtTitle);
        main.setText(this.getCursor().getString(1));
        
        TextView sumTextView = (TextView) convertView.findViewById(R.id.txtSumTitle);
        sumTextView.setText(this.getCursor().getString(2));
        
        String debtType = this.getCursor().getString(3);
        int isMyDebt = Integer.parseInt(debtType);
        if (isMyDebt == 1)
        {
        	main.setTextColor(Color.RED);
        }
        else
        {
        	main.setTextColor(Color.GREEN);
        }
        
        CheckBox deleteCheckbox = (CheckBox)row.findViewById(R.id.deleteCheckbox);
        deleteCheckbox.setTag(position);
        if (checked.get(position))
        {
        	deleteCheckbox.setChecked(true);
        }
        else
        {
        	deleteCheckbox.setChecked(false);
        }
        deleteCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				int position = (Integer) buttonView.getTag();  // Here we get the position that we have set for the checkbox using setTag.
				checked.set(position, isChecked);
			}
        });  
           	
        return row;
    }
    
    public void addChecked()
    {
    	checked.add(false);
    }
    
    public void removeChecked(int index)
    {
    	checked.remove(index);
    }
    
    public Boolean getChecked(int index)
    {
    	return checked.get(index);
    }
    
    public int getCheckedCount()
    {
    	return checked.size();
    }
}
