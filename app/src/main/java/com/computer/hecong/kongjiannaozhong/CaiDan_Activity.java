package com.computer.hecong.kongjiannaozhong;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class CaiDan_Activity extends Activity implements View.OnClickListener {

    private int i =0;
    private TextView luan;
    private Button fanhui;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caidan);
        luan = findViewById(R.id.luandian);
        fanhui = findViewById(R.id.fanhui);
        luan.setOnClickListener(this);
        fanhui.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fanhui:
                if(i == 0){
                    luan.setText("还乱点！");
                    i++;
                } else if(i == 1){
                    luan.setText("点坏了咋整！！！");
                    i++;
                } else if(i == 2){
                    luan.setText("你咋这么不听话呢？");
                    i++;
                } else if(i == 3){
                    luan.setText("就是不按要求来，唉！");
                    i++;
                } else if(i == 4){
                    luan.setText("无药可救了你！！！");
                    i++;
                } else if(i == 5){
                    Toast.makeText(this,"程序已经崩溃！请点击退出！",Toast.LENGTH_LONG).show();
                    i++;
                } else if(i == 10){
                    luan.setText("不玩了！拜拜了您！");
                    i++;
                } else if(i == 15){
                    finish();
                } else{
                    i++;
                }
                break;
            default:break;
        }
    }
}
