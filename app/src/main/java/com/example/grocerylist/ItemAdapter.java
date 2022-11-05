package com.example.grocerylist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.grocerylist.datamodels.Item;
import com.example.grocerylist.helpers.ItemDbAdapter;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<Item> {

    ItemDbAdapter dbAdapter;

    private static class ViewHolder {
        TextView quantity;
        TextView name;
        TextView deleteIcon;
    }

    public ItemAdapter(Context context, ArrayList<Item> data, ItemDbAdapter adapter){
        super(context, R.layout.list_item, data);
        this.dbAdapter = adapter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Item item = (Item)getItem(position);
        ViewHolder viewHolder;
        final View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.quantity = (TextView) convertView.findViewById(R.id.quantity);
            viewHolder.deleteIcon = (TextView) convertView.findViewById(R.id.deleteIcon);
            result=convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }
        viewHolder.deleteIcon.setOnClickListener(l -> {
            dbAdapter.deleteByName(item.getName());
            this.remove(item);
        });
        viewHolder.name.setText(item.getName());
        viewHolder.quantity.setText("Quantity: " + item.getQuantity().toString());
        return convertView;
    }
}
