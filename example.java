package com.example.example2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addButton = (Button) findViewById(R.id.AddButton);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText num1EditText = (EditText) findViewById(R.id.num1EditText);
                EditText num2EditText = (EditText) findViewById(R.id.num2EditText);

                int num1 = Integer.parseInt(num1EditText.getText().toString());
                int num2 = Integer.parseInt(num2EditText.getText().toString());

                int res = num1 + num2;

                TextView resultTextView = (TextView) findViewById(R.id.ResultViewText);
                resultTextView.setText(" " + res);

            }
        });



    }
}

