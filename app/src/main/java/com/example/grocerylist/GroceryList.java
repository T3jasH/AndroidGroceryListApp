package com.example.grocerylist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import com.example.grocerylist.datamodels.Item;
import com.example.grocerylist.helpers.ItemDbAdapter;

import java.util.ArrayList;

public class GroceryList extends AppCompatActivity {

    ItemAdapter arrayAdapter;
    ItemDbAdapter itemDbAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);
        ListView list = findViewById(R.id.groceries_list);
        AppCompatButton btn = findViewById(R.id.add_item_button);
        itemDbAdapter = new ItemDbAdapter(this);
        itemDbAdapter.open();
        btn.setOnClickListener((l) -> {
            EditText item = findViewById(R.id.new_item_field);
            EditText quantity = findViewById(R.id.quantity_field);
            Item listItem = new Item(item.getText().toString(), Integer.parseInt(quantity.getText().toString()));
            itemDbAdapter.insertItem(listItem);
            arrayAdapter.add(listItem);
            item.setText("");
            quantity.setText("");
        });
        ArrayList arrayList = new ArrayList( itemDbAdapter.getAllItems() );
        arrayAdapter = new ItemAdapter(this, arrayList, itemDbAdapter);
        list.setAdapter(arrayAdapter);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        itemDbAdapter.close();
    }
}