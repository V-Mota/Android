package com.example.example3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private static final int DRAW_OVER_OTHER_APP_PERMISSION = 123;
    TextView displayTextView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayTextView = (TextView) findViewById(R.id.displayTextView);
        textView = (TextView) findViewById(R.id.textView);

        Button doButton = (Button) findViewById(R.id.doButton);
        doButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TextView displayTextView = (TextView) findViewById(R.id.displayTextView);
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


        int badge_count = getIntent().getIntExtra("badge_count", 0);

        textView.setText(badge_count + " messages received previously");

        Button GotoQuiz = (Button) findViewById(R.id.GotoQuiz);
        GotoQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askForSystemOverlayPermission();

//                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(MainActivity.this)) {
                if (Settings.canDrawOverlays(MainActivity.this)) {
                        startService(new Intent(MainActivity.this, FloatingWidgetService.class));
                } else {
                    errorToast();
                }



//                Intent GotoQuiz = getPackageManager().getLaunchIntentForPackage("pt.questaocuriosa.gfoundry.app");
//                if (GotoQuiz != null) {
//                    startActivity(GotoQuiz);//null pointer check in case package name was not found
//                } else {
//                    TextView displayTextView = (TextView) findViewById(R.id.displayTextView);
//                    displayTextView.setText("App not found ");
//                }

            }
        });

    }


    private void askForSystemOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(!Settings.canDrawOverlays(this)) {

                //If the draw over permission is not available to open the settings screen
                //to grant the permission.
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, DRAW_OVER_OTHER_APP_PERMISSION);
            } else {
                 displayTextView.setText("Overlay Permission: OK");
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DRAW_OVER_OTHER_APP_PERMISSION) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    //Permission is not available. Display error text.
                    errorToast();
                    finish();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();


        // To prevent starting the service if the required permission is NOT granted.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(this)) {
            startService(new Intent(MainActivity.this, FloatingWidgetService.class).putExtra("activity_background", true));
            finish();
        } else {
            errorToast();
        }
    }


    private void errorToast() {
        Toast.makeText(this, "Draw over other app permission not available. Can't start the application without the permission.", Toast.LENGTH_LONG).show();
    }




}
