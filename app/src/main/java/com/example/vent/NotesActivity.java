package com.example.vent;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vent.eliza.Eliza;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

public class NotesActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        SharedPreferences prefs = getSharedPreferences("vents", MODE_PRIVATE);
        EditText editText = (EditText) findViewById(R.id.ventEditText);
        editText.setText(prefs.getString("today", ""));
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(start != 0)
                if(s.charAt(s.length()-1) == '\n') {

                    String[] lines = s.toString().split("\n");

                    thelmaTalk(lines[lines.length-1]);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        thelmaText = (TextView) findViewById(R.id.thelma);

        try {
            thelma = new Eliza(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        thelmaText.setText(Html.fromHtml("Let it <b>all</b> out.",Html.FROM_HTML_MODE_LEGACY));
    }
    Eliza thelma;
    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences prefs = getSharedPreferences("vents", MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();
        ed.putString("today", ((EditText) findViewById(R.id.ventEditText)).getText().toString());
        ed.apply();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences prefs = getSharedPreferences("vents", MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();
        ed.putString("today", ((EditText) findViewById(R.id.ventEditText)).getText().toString());
        ed.apply();
    }
    boolean blocked = false;
    TextView thelmaText;
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void thelmaTalk (String line) {
        if(!blocked) {
            Log.d("OKKKKKK", "start");

            thelmaText.animate().alpha(0).setDuration(1000).start();
            Log.d("OKKKKKK", "anim " + line);

            blocked = true;
            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                blocked = false;
                Log.d("OKKKKKK", "unblocked");

            }, 5000);
            thelmaText.setText(Html.fromHtml(thelma.processInput(line),Html.FROM_HTML_MODE_LEGACY));
            Log.d("OKKKKKK", "thelmad");
            thelmaText.animate().alpha(1).setDuration(1000).start();
            Log.d("OKKKKKK", "unthelmad");

        }
    }
}