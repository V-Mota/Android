package com.example.example3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button doButton = (Button) findViewById(R.id.doButton);

        doButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView displayTextView = (TextView) findViewById(R.id.displayTextView);
                displayTextView.setText("Hello Vitor!");

            }
        });


        Button goto2ndButton = (Button) findViewById(R.id.goto2ndButton);
        goto2ndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent secondActIntent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(secondActIntent);
            }
        });


        Button GotoQuiz = (Button) findViewById(R.id.GotoQuiz);
        GotoQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent GotoQuiz = getPackageManager().getLaunchIntentForPackage("pt.questaocuriosa.gfoundry.app");
                if (GotoQuiz != null) {
                    startActivity(GotoQuiz);//null pointer check in case package name was not found
                } else {
                    TextView displayTextView = (TextView) findViewById(R.id.displayTextView);
                    displayTextView.setText("App not found ");
                }

            }
        });


    }
}
