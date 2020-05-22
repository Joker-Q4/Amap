package com.computer.hecong.kongjiannaozhong;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

public class BoFang_Activity extends Activity implements View.OnClickListener{
    private MediaPlayer mediaPlayer1;

    private Button bofang;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bofang);
        bofang = findViewById(R.id.bofang);
        bofang.setOnClickListener(this);
        mediaPlayer1  = MediaPlayer.create(this,R.raw.naoling);
        mediaPlayer1.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bofang:
                mediaPlayer1.stop();
                Intent intent = new Intent();
                intent.putExtra("str","str");
                setResult(RESULT_OK,intent);
                finish();
                break;
                default:break;
        }
    }

    @Override
    public void onBackPressed(){
        mediaPlayer1.stop();
        Intent intent = new Intent();
        intent.putExtra("str","str");
        setResult(RESULT_OK,intent);
        finish();
    }
}
