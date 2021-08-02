package com.example.todolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText item;
    Button add;
    ListView listView;
    ArrayList<String> itemlist;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        item=findViewById(R.id.editText);
        add=findViewById(R.id.button);
        listView=findViewById(R.id.list);

        itemlist=FileHelper.readData(this);
        arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1,itemlist);
        listView.setAdapter(arrayAdapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName=item.getText().toString();
                itemlist.add(itemName);
                item.setText("");
                FileHelper.writeData(itemlist,getApplicationContext());
                arrayAdapter.notifyDataSetChanged();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Delete");
                alert.setMessage("Do you want to delete this item from list");
                alert.setCancelable(false);
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        itemlist.remove(position);
                        arrayAdapter.notifyDataSetChanged();
                        FileHelper.writeData(itemlist,getApplicationContext());
                    }
                });
                AlertDialog alertDialog=alert.create();
                alertDialog.show();
            }
        });
    }
}