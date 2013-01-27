package com.example.debts.model;

import java.util.ArrayList;

import com.example.debts.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class DebtAdapter extends ArrayAdapter<Debt>
{
    Context context; 
    int layoutResourceId;    
    
    /**
     * Constructor.
     */
    public DebtAdapter(Context context, int layoutResourceId, ArrayList<Debt> data)
    {
    	super(context, layoutResourceId, data);
        
        this.layoutResourceId = layoutResourceId;
        this.context = context;
    }
    
    /**
     * Removes all selected items.
     */
    public void removeSelectedItems()
    {
    	ArrayList<Debt> itemsToRemove = new ArrayList<Debt>();
    	for(int i = 0; i < getCount(); i++)
		{
			Debt d = getItem(i);
			if (d.isChecked())
			{
				itemsToRemove.add(d);
			}
		}
    	
    	for (int i = 0; i < itemsToRemove.size(); i++)
    	{
    		remove(itemsToRemove.get(i));
    	}
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        DebtHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new DebtHolder();
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            holder.deleteCheckbox = (CheckBox)row.findViewById(R.id.deleteCheckbox);
            
            row.setTag(holder);
        }
        else
        {
            holder = (DebtHolder)row.getTag();
        }
        holder.deleteCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int position = (Integer) buttonView.getTag();  // Here we get the position that we have set for the checkbox using setTag.
                getItem(position).setChecked(isChecked); // Set the value of checkbox to maintain its state. 
            }
        });
        
        holder.deleteCheckbox.setTag(position);
        
        Debt debt = getItem(position);
        String text = debt.getName() + ": " + debt.getSum();
        holder.txtTitle.setText(text);
        holder.deleteCheckbox.setFocusable(false);
        holder.deleteCheckbox.setChecked(getItem(position).isChecked());
        
        if (debt.give())
        {
        	holder.txtTitle.setTextColor(Color.RED);
        }
        else
        {
        	holder.txtTitle.setTextColor(Color.GREEN);
        }
        
        return row;
    }
    
    static class DebtHolder
    {
        TextView txtTitle;
        CheckBox deleteCheckbox;
    }
}
