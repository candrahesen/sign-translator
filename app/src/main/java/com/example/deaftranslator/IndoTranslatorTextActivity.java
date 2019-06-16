package com.example.deaftranslator;

import android.content.Intent;
import android.media.MediaPlayer;
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

public class IndoTranslatorTextActivity extends AppCompatActivity {

    private TextView tvWordToTranslateText;

    private LinearLayout llTextTerjemahanContainer;

    private VideoView videoResultSignTranslate;
    private VideoView videoResultKataSignTranslate;
    private VideoView videoResultKata2;
    private VideoView videoResultKata3;
    private VideoView videoResultPlayList;

    private RelativeLayout videoTerjemahanTeksContainer;
    private RelativeLayout videoTerjemahanKataContainer;

    private TextView teksGerakan;
    private LinearLayout deskripsiTeksGerakan;
    private Button fullScreen;

    MediaController mediaController;
    MediaController mediaControllerKata;
    MediaController mediaControllerKata2;
    MediaController mediaControllerKata3;
    MediaController mediaControllerPlayList;

    private int url = 0;
    private ArrayList<String> videoList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indo_translator_text);

        llTextTerjemahanContainer = (LinearLayout) findViewById(R.id.ll_text_terjemahan_teks_container);

        videoTerjemahanTeksContainer = (RelativeLayout) findViewById(R.id.ll_video_terjemahan_teks_container);
        tvWordToTranslateText = (TextView) findViewById(R.id.tv_word_to_translate_teks);

//        videoTerjemahanKataContainer = (RelativeLayout) findViewById(R.id.rl_video_terjemahan_kata_container);


        mediaController = new MediaController(this);
        mediaControllerKata = new MediaController(this);
        mediaControllerKata2 = new MediaController(this);
        mediaControllerKata3 = new MediaController(this);
        mediaControllerPlayList = new MediaController(this);

        String wordToTranslateText = (String) (getIntent().getExtras().getSerializable("teksTerjemahan"));
        tvWordToTranslateText.setText(wordToTranslateText);

        videoResultSignTranslate = (VideoView) findViewById(R.id.vv_result_sign_translate_teks);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.dimana_kumpul2;
        videoResultSignTranslate.setVideoURI(Uri.parse(path));

        videoResultKataSignTranslate = (VideoView) findViewById(R.id.vv_terjemahan_kata);
        String pathKata = "android.resource://" + getPackageName() + "/" + R.raw.dimana;
        videoResultKataSignTranslate.setVideoURI(Uri.parse(pathKata));

        videoResultKata2 = (VideoView) findViewById(R.id.vv_terjemahan_kata2);
        String pathKata2 = "android.resource://" + getPackageName() + "/" + R.raw.tugas;
        videoResultKata2.setVideoURI(Uri.parse(pathKata2));

        videoResultKata3 = (VideoView) findViewById(R.id.vv_terjemahan_kata3);
        String pathKata3 = "android.resource://" + getPackageName() + "/" + R.raw.kumpul;
        videoResultKata3.setVideoURI(Uri.parse(pathKata3));

        videoList.add("android.resource://" + getPackageName() + "/" + R.raw.dimana);
        videoList.add("android.resource://" + getPackageName() + "/" + R.raw.tugas);
        videoList.add("android.resource://" + getPackageName() + "/" + R.raw.kumpul);

        videoResultPlayList = (VideoView) findViewById(R.id.vv_result_sign_translate_video_playlist);



//        fullScreen = (Button) findViewById(R.id.bt_fullscreen);
//        fullScreen.setOnClickListener(fullScreenListener);

        mediaController.setAnchorView(videoResultSignTranslate);
        videoResultSignTranslate.setMediaController(mediaController);
        videoResultSignTranslate.start();

        mediaControllerKata.setAnchorView(videoResultKataSignTranslate);
        videoResultKataSignTranslate.setMediaController(mediaControllerKata);
        videoResultKataSignTranslate.start();

        mediaControllerKata2.setAnchorView(videoResultKata2);
        videoResultKata2.setMediaController(mediaControllerKata2);
        videoResultKata2.start();

        mediaControllerKata3.setAnchorView(videoResultKata3);
        videoResultKata3.setMediaController(mediaControllerKata3);
        videoResultKata3.start();

        String pathPlayList = videoList.get(0);
        videoResultPlayList.setVideoURI(Uri.parse(pathPlayList));
        videoResultPlayList.start();

        //Video Loop
        videoResultPlayList.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                String pathPlayList = videoList.get(url);
                videoResultPlayList.setVideoURI(Uri.parse(pathPlayList));
                videoResultPlayList.start();

                if(url==videoList.size()-1)
                {
                    url=0;
                }
                else{
                    url++;
                }

            }
        });


//        mediaControllerPlayList.setAnchorView(videoResultPlayList);
//        videoResultPlayList.setMediaController(mediaControllerPlayList);
//        videoResultPlayList.start();
    }


    public View.OnClickListener fullScreenListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (teksGerakan.getVisibility() == View.VISIBLE && deskripsiTeksGerakan.getVisibility() == View.VISIBLE) {
                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);
                android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) videoTerjemahanTeksContainer.getLayoutParams();
                params.width =  metrics.widthPixels;
                params.height = metrics.heightPixels;
                params.leftMargin = 0;
                videoResultSignTranslate.setLayoutParams(params);
                teksGerakan.setVisibility(View.GONE);
                deskripsiTeksGerakan.setVisibility(View.GONE);
            } else {
                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);
                android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) videoTerjemahanTeksContainer.getLayoutParams();
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
