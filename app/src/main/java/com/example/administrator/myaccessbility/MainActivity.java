package com.example.administrator.myaccessbility;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button start = findViewById(R.id.start);
        Button remove = findViewById(R.id.remove);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AirButtonService.start(MainActivity.this);
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AirButtonService.stop(MainActivity.this);
            }
        });
    }

}
