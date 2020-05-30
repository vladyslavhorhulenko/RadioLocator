package com.example.radiolocator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ModelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model);
        Bundle args = getIntent().getExtras();
        final String mark = args.get("mark").toString();
        final String[] models;
        final ListView modelList = findViewById(R.id.modelList);
        ArrayAdapter<String> adapter;
        switch (mark){
                case "HARRIS":
                    models = new String[]{"RF-7850M", "RF-7800H-MR", "RF-7800H-TM 150w", "MPR-9600-MP 20w", "MPR-9600-MP 125w"};
                    adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, models);
                    modelList.setAdapter(adapter);
                    break;
                case "MOTOROLA":
                    models = new String[]{"DP 4400 портативна", "DP 4800 портативна", "DM 4600 в спец. виконанні "};
                    adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, models);
                    modelList.setAdapter(adapter);
                    break;
                default:
                    models = new String[]{};
                    break;
            }
        modelList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (mark) {
                    case "HARRIS":
                        intent = new Intent(getApplicationContext(), AntenActivity.class);
                        intent.putExtra("model", models[position]);
                        intent.putExtra("mark", mark);
                        startActivity(intent);
                        break;

                    case "MOTOROLA":
                        intent = new Intent(getApplicationContext(), AntenActivity.class);
                        intent.putExtra("model", models[position]);
                        intent.putExtra("mark", mark);
                        startActivity(intent);
                        break;
                }
            }
        });
    }
}
