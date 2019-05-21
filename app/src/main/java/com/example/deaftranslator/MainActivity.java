package com.example.deaftranslator;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.input.InputManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static int VIDEO_REQUEST = 101;
    private Uri videoUri = null;
    ArrayList<String> wordToTranslateVoice;
    private Button doneTeks;
    private SharedPreferences savedTeksTerjemahan;
    private EditText teksEditor;
    private String teksTobeTranslate;
    private TextView signLanguage;
    private TextView indoLanguage;
    private ImageView switchSign;
    private LinearLayout isyaratEditor;
    private LinearLayout indoEditor;

    private String originValue;
    private String destValue;

    private boolean isSwap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signLanguage = (TextView) findViewById(R.id.tv_sign_language);
        indoLanguage = (TextView) findViewById(R.id.tv_indo_language);

        isyaratEditor = (LinearLayout) findViewById(R.id.ll_isyarat_editor);
        indoEditor = (LinearLayout) findViewById(R.id.ll_indo_editor);
        indoEditor.setVisibility(View.GONE);

        originValue = signLanguage.getText().toString();
        destValue = indoLanguage.getText().toString();

        switchSign = (ImageView) findViewById(R.id.iv_switch);
        switchSign.setOnClickListener(switchLanguageListener);

        doneTeks = (Button) findViewById(R.id.bt_done_teks);
        teksEditor = (EditText) findViewById(R.id.et_teks_editor);
        savedTeksTerjemahan = getSharedPreferences("teksTerjemahan", MODE_PRIVATE);

        doneTeks.setOnClickListener(doneButtonListener);

    }

    public void setOriginValue(String value) {
        originValue = value;
        signLanguage.setText(value);
    }

    public String getOriginValue() {
        return originValue;
    }

    public void setDestValue(String value) {
        destValue = value;
        indoLanguage.setText(value);
    }

    public String getDestValue() {
        return destValue;
    }

    private void makeTag(String tag) {
        SharedPreferences.Editor preferencesEditor = savedTeksTerjemahan.edit();
        preferencesEditor.putString("tag", tag);
        preferencesEditor.apply();
    }

    public View.OnClickListener switchLanguageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String temp = originValue;
            setOriginValue(destValue);
            setDestValue(temp);
            if (isyaratEditor.getVisibility() == View.VISIBLE) {
                isyaratEditor.setVisibility(View.GONE);
                indoEditor.setVisibility(View.VISIBLE);
            } else {
                isyaratEditor.setVisibility(View.VISIBLE);
                indoEditor.setVisibility(View.GONE);
            }
        }
    };

    public View.OnClickListener doneButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (teksEditor.getText().length() > 0) {
                makeTag(teksEditor.getText().toString());

                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(teksEditor.getWindowToken(),0);
                teksTobeTranslate = teksEditor.getText().toString();
                Intent teksTerjemahan = new Intent(MainActivity.this, IndoTranslatorTextActivity.class);
                teksTerjemahan.putExtra("teksTerjemahan", teksTobeTranslate);
                startActivity(teksTerjemahan);
            }
        }
    };

    public void captureVideo(View view) {
        Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        if(videoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(videoIntent, VIDEO_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==VIDEO_REQUEST && resultCode==RESULT_OK) {
            videoUri = data.getData();
            Intent uriVideo = new Intent(this, SignTranslatorActivity.class);
            uriVideo.putExtra("videoUri", videoUri.toString());
            startActivity(uriVideo);
        }

        if(requestCode==100 && resultCode==RESULT_OK && data!=null) {
            wordToTranslateVoice = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            Intent wToTranslateVoice = new Intent(this, IndoTranslatorVoiceActivity.class);
            wToTranslateVoice.putExtra("wordToTranslateVoice", wordToTranslateVoice);
            startActivity(wToTranslateVoice);
        }
    }

    public void recordAudio(View view) {
        if(view.getId() == R.id.rl_suara_icon_container) {
            promptSpeechInput();
        }
    }

    public void promptSpeechInput() {
        Intent speechInput = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechInput.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechInput.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "id-ID");
        speechInput.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something!");

        try {
            startActivityForResult(speechInput, 100);
        }
        catch (ActivityNotFoundException a) {
            Toast.makeText(MainActivity.this, "Sorry! your device doesn't support speech language!", Toast.LENGTH_LONG).show();
        }
    }


}
