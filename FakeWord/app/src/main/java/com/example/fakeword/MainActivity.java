package com.example.fakeword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    private TextView txvResult;
    private boolean isfound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txvResult = (TextView) findViewById(R.id.txvResult);
    }

    public void getSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txvResult.setText(result.get(0));
                    String str;
                    String sentence;
                    StringTokenizer sb = new StringTokenizer(result.get(0));

                    for (int i = 0; i < sb.countTokens(); i++) {

                        str = String.valueOf(sb.nextToken());

                        if ((!str.contains("password please") && !str.contains("pin please") && !str.contains("transaction") && !str.contains("process"))) {
                            isfound = true;
                            Toast.makeText(MainActivity.this, "This is a Spam Alert", Toast.LENGTH_SHORT).show();
                            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            // Vibrate for 500 milliseconds
                            v.vibrate(400);

                        } else {
                            isfound = false;
                            Toast.makeText(MainActivity.this, "This is a not Spam Alert", Toast.LENGTH_SHORT).show();
                        }

                    }

                } else {
                    Toast.makeText(MainActivity.this, "Click the Google Audio Track", Toast.LENGTH_SHORT).show();
                }


                break;
        }
    }

}
