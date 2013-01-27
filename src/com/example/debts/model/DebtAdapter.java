package com.example.debts.model;

import com.example.debts.R;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

public class DebtAdapter extends SimpleCursorAdapter
{
    Context context;
    Cursor c;
    int layoutResourceId;    
    
    /**
     * Constructor.
     */
	public DebtAdapter(Context context, int layoutResourceId, Cursor c,
            String[] from, int[] to, int flags)
    {
    	super(context, layoutResourceId, c, from, to, flags);
        
    	this.c = c;
        this.layoutResourceId = layoutResourceId;
        this.context = context;
    }
    
    /**
     * Removes all selected items.
     */
    public void removeSelectedItems()
    {
    /*	ArrayList<Debt> itemsToRemove = new ArrayList<Debt>();
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
    	} */
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	if(convertView == null)
            convertView = View.inflate(context, R.layout.listview_item_layout, null);
        View row = convertView;

        c.moveToPosition(position);
        
        TextView main = (TextView) convertView.findViewById(R.id.txtTitle);
        main.setText(c.getString(0) + " " +
        		c.getString(1) + " " +
        		c.getString(2));
        
        CheckBox deleteCheckbox = (CheckBox) convertView.findViewById(R.id.deleteCheckbox);
    	deleteCheckbox.setChecked(false);
        
/*    	View row = convertView;
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
                ((Debt)getItem(position)).setChecked(isChecked); // Set the value of checkbox to maintain its state. 
            }
        });
        
        holder.deleteCheckbox.setTag(position);
        
        Debt debt = (Debt)getItem(position);
        String text = debt.getName() + ": " + debt.getSum();
        holder.txtTitle.setText(text);
        holder.deleteCheckbox.setFocusable(false);
        holder.deleteCheckbox.setChecked(((Debt)getItem(position)).isChecked());
        
        if (debt.isMyDebt())
        {
        	holder.txtTitle.setTextColor(Color.RED);
        }
        else
        {
        	holder.txtTitle.setTextColor(Color.GREEN);
        } */
        
        return row;
    }
    
    static class DebtHolder
    {
        TextView txtTitle;
        CheckBox deleteCheckbox;
    }
}
