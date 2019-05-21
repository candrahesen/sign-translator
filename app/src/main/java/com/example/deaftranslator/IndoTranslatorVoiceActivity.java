package com.example.deaftranslator;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;

public class IndoTranslatorVoiceActivity extends AppCompatActivity {

    private TextView tvWordToTranslateVoice;
    private VideoView videoResultSignTranslate;
    private RelativeLayout videoTerjemahanVoiceContainer;

    private TextView teksGerakan;
    private LinearLayout deskripsiTeksGerakan;
    private Button fullScreen;

    MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indo_translator_voice);

        tvWordToTranslateVoice = (TextView) findViewById(R.id.tv_word_to_translate_voice);

        ArrayList<String> wordToTranslateVoice = (ArrayList<String>)(getIntent().getExtras().getSerializable("wordToTranslateVoice"));
        tvWordToTranslateVoice.setText(wordToTranslateVoice.get(0));

        videoTerjemahanVoiceContainer = (RelativeLayout) findViewById(R.id.ll_video_terjemahan_voice_container);

        teksGerakan = (TextView) findViewById(R.id.tv_hasil_terjemahan_gerakan_voice);
        deskripsiTeksGerakan = (LinearLayout) findViewById(R.id.ll_deskripsi_gerakan_voice_container);

        mediaController = new MediaController(this);

        videoResultSignTranslate = (VideoView) findViewById(R.id.vv_result_sign_translate_voice);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.apa_tugas;
        videoResultSignTranslate.setVideoURI(Uri.parse(path));

//        fullScreen = (Button) findViewById(R.id.bt_fullscreen);
//        fullScreen.setOnClickListener(fullScreenListener);

        mediaController.setAnchorView(videoResultSignTranslate);
        videoResultSignTranslate.setMediaController(mediaController);
        videoResultSignTranslate.start();
    }

    public View.OnClickListener fullScreenListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (teksGerakan.getVisibility() == View.VISIBLE && deskripsiTeksGerakan.getVisibility() == View.VISIBLE) {
                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);
                android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) videoTerjemahanVoiceContainer.getLayoutParams();
                params.width =  metrics.widthPixels;
                params.height = metrics.heightPixels;
                params.leftMargin = 0;
                videoResultSignTranslate.setLayoutParams(params);
                teksGerakan.setVisibility(View.GONE);
                deskripsiTeksGerakan.setVisibility(View.GONE);
            } else {
                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);
                android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) videoTerjemahanVoiceContainer.getLayoutParams();
                params.width =  RelativeLayout.LayoutParams.MATCH_PARENT;
                params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                params.topMargin = 4;
                videoResultSignTranslate.setLayoutParams(params);
                teksGerakan.setVisibility(View.VISIBLE);
                deskripsiTeksGerakan.setVisibility(View.VISIBLE);
            }
        }
    };
}
