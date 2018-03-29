package edu.ggc.scott.ggcstudentpicker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.action_settings:
                Intent About = new Intent(this,About.class);
                startActivity(About);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       final TextView textOne =  findViewById(R.id.textView1);
        ImageButton pushMe =  findViewById(R.id.imageButton3);

        final String[] myNames = getResources().getStringArray(R.array.class_list);

            populateListView();

        pushMe.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                final int random = (int) (Math.random() * 24);
                textOne.setText(myNames[random]);
            }
                                                             }

        );}

    private void populateListView(){
        String [] myList = getResources().getStringArray(R.array.class_list);
        ArrayList<String> myListArrayList = new ArrayList<>(Arrays.asList(myList));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.itemlist2,myList);

        ListView list =  findViewById(R.id.ListView11);
        Arrays.sort(myList);

        Collections.sort(myListArrayList);
        list.setAdapter(adapter);

    }
    }

