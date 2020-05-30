package com.example.radiolocator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AntenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anten);
        Bundle args = getIntent().getExtras();
        final String mark = args.get("mark").toString();
        final String model = args.get("model").toString();
        final String[] antens;
        final ListView antenList = findViewById(R.id.antenList);
        ArrayAdapter<String> adapter;
        switch (model){
            case "RF-7800H-MR":
                antens = new String[]{"OE-505 Whip Antena Kit штирь 3 метри", "RF-1942-AT001 штирь 4,9м", "RF-1942-AT001 штирь 3,5м", "RF-1936 штирь 4,1 метр", "RF-1938 штирь 8,7 метр"};
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, antens);
                antenList.setAdapter(adapter);
                break;
            case "RF-7850M":
                antens = new String[]{"ANTENNA, BLADE, VHF/UHF, HH, 45 INCH 114 см стрічкова антена довга", "ANTENNA, BLADE, 20M MB HH 10W 34,5 см стрічкова антена коротка"};
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, antens);
                antenList.setAdapter(adapter);
                break;
            case "RF-7800H-TM 150w":
                antens = new String[]{"OE-505 Whip Antena Kit штирь 3 метри", "RF-1942-AT001 штирь 4,9м", "RF-1942-AT001 штирь 3,5м", "RF-1936 штирь 4,1 метр", "RF-1938 штирь 8,7 метр"};
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, antens);
                antenList.setAdapter(adapter);
                break;
            case "MPR-9600-MP 20w":
                antens = new String[]{"OE-505 Whip Antena Kit штирь 3 метри", "RF-1942-AT001 штирь 4,9м", "RF-1942-AT001 штирь 3,5м", "RF-1936 штирь 4,1 метр", "RF-1938 штирь 8,7 метр"};
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, antens);
                antenList.setAdapter(adapter);
                break;
            case "MPR-9600-MP 125w":
                antens = new String[]{"OE-505 Whip Antena Kit штирь 3 метри", "RF-1942-AT001 штирь 4,9м", "RF-1942-AT001 штирь 3,5м", "RF-1936 штирь 4,1 метр", "RF-1938 штирь 8,7 метр"};
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, antens);
                antenList.setAdapter(adapter);
                break;
            default:
                antens = new String[]{};
                break;
            case "DP 4400 портативна":
                antens = new String[]{"АНТЕНА - ТРОС", "MOTOROLA PMAD4147 VHF"};
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, antens);
                antenList.setAdapter(adapter);
                break;
            case "DP 4800 портативна":
                antens = new String[]{"АНТЕНА - ТРОС", "MOTOROLA PMAD4147 VHF"};
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, antens);
                antenList.setAdapter(adapter);
                break;
            case "DM 4600 в спец. виконанні":
                antens = new String[]{"Антена штирьова 1/4"};
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, antens);
                antenList.setAdapter(adapter);
                break;
        }
        antenList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
               intent.putExtra("anten", antens[position]);
               intent.putExtra("model", model);
               intent.putExtra("mark", mark);
               startActivity(intent);
            }
        });
    }
}
