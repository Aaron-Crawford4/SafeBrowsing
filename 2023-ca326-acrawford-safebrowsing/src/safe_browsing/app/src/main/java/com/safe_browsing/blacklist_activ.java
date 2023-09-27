package com.safe_browsing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class blacklist_activ extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    ListView listview;
    ArrayList<String> blacklist_data;
    TextView selected_site;
    int selected_position;
    Object item_selected;
    EditText entered_website;
    Button add_button, remove_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blacklist);

        //find views
        listview = findViewById(R.id.blacklisted);
        selected_site = findViewById(R.id.selected);
        entered_website = findViewById(R.id.typed_website);
        add_button = findViewById(R.id.add_btn);
        remove_button = findViewById(R.id.remove_btn);

        selected_position = -1;
        blacklist_data = new ArrayList<String>();

        //display list of blocked websites
        new SftpReadToListTask("squid-proxy", 22, "AaronCiaran","/etc/squid/blocked_sites", listview, this, blacklist_data).execute();

        //set click listeners
        listview.setOnItemClickListener(this);
        add_button.setOnClickListener(this);
        remove_button.setOnClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //store the position clicked in a variable and set selected site text view to the selected site
        selected_position = i;
        item_selected = adapterView.getItemAtPosition(i);
        selected_site.setText(item_selected.toString());
    }

    @Override
    public void onClick(View view) {
        //add button clicked
        if (view.getId() == add_button.getId()) {
            //if there is nothing typed in the edit text, set error message
            if (TextUtils.isEmpty(entered_website.getText().toString())) {
                entered_website.setError("You must enter a website");
            //else, add the entered site to the list data and notify data set change, call background task to add to blocked sites file, clear the edit text view
            }else {
                String entered = entered_website.getText().toString();
                blacklist_data.add(entered);
                ArrayAdapter adapter = (ArrayAdapter) listview.getAdapter();
                adapter.notifyDataSetChanged();
                new Background_Execute().execute(1, entered);
                entered_website.getText().clear();
            }
        //remove button clicked
        } else if (view.getId() == remove_button.getId()){
            //if there is no selected site, make toast message
            if (TextUtils.isEmpty(selected_site.getText().toString()) || selected_position == -1){
                Toast.makeText(blacklist_activ.this, "You must select a Website", Toast.LENGTH_SHORT).show();
            //else, set selected site to empty string, remove selected site from the list data and notify data set change, call background task to remove from blocked sites file
            }else {
                selected_site.setText("");
                blacklist_data.remove(selected_position);
                selected_position = -1;
                ArrayAdapter adapter = (ArrayAdapter) listview.getAdapter();
                adapter.notifyDataSetChanged();
                new Background_Execute().execute(2, item_selected);
            }
        }
    }
}