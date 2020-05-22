package com.computer.hecong.kongjiannaozhong;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.baidu.parsexml.GetWeInFrBaidu;
import com.baidu.parsexml.ParseBaiDu_Xml;
import com.baidu.parsexml.WeatherInfo;
import org.apache.http.client.ClientProtocolException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Main2Activity extends Activity {
    Button returnBtn, getBtn, XMLBtn;
    TextView showInfoTextView;
    List<WeatherInfo> all;
    public static String temp;
    public static StringBuffer buffer;
    List<WeatherInfo> weatherInfos;
    String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main2);
    getBtn = (Button) findViewById(R.id.HttpGet);
    XMLBtn = (Button) findViewById(R.id.XMLGet);
    showInfoTextView = (TextView) findViewById(R.id.showInfo);
        getBtn.setOnClickListener(new BtnOnclickListener());
        XMLBtn.setOnClickListener(new BtnOnclickListener());
    Intent intent = getIntent();
    city = intent.getStringExtra("extra_city");
}

    private final class BtnOnclickListener implements View.OnClickListener {

        @Override
       public void onClick(View v) {
            if (v == getBtn) {
                System.err.println("事件监听----->" + "getBtn");
                new Thread() {
                    public void run() {
                        try {
                            temp = GetWeInFrBaidu.myPostFun(city);
                        } catch (ClientProtocolException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    };
                }.start();
                showInfoTextView.setText(temp);
            } else if (v == XMLBtn) {
                try {
                    System.err.println("事件监听----->" + "XMLBtn");
                    xmlJieX();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void xmlJieX() throws Exception {
        InputStream input = GetWeInFrBaidu.getStringInputStream(temp);
        ParseBaiDu_Xml xml = new ParseBaiDu_Xml(input);
        weatherInfos = xml.getWeatherInfos();
        StringBuffer infobuffer = new StringBuffer();
        for (int i = 0; i < weatherInfos.size(); i++) {
            infobuffer.append("---------天气情况--------\n"
                    + weatherInfos.get(i).getDate() + " \n"
                    + weatherInfos.get(i).getWeather() + " \n"
                    + weatherInfos.get(i).getWind() + " \n"
                    + weatherInfos.get(i).getTemperature() + " \n"
            );
        }
        showInfoTextView.setText(infobuffer);
    }
}
