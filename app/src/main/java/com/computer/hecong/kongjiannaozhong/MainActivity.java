package com.computer.hecong.kongjiannaozhong;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.utils.DistanceUtil;

import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 此demo用来展示如何结合定位SDK实现定位，并使用MyLocationOverlay绘制定位位置 同时展示如何使用自定义图标绘制并点击时弹出泡泡
 */
public class MainActivity extends Activity implements OnGetGeoCoderResultListener{

    private String str = null;
    String str11 = null;
    private TextView juli;
    private TextView juli1;
    private TextView cai;
    private EditText tixing;

    private double location11;
    private double location12;
    private double location21;
    private double location22;
    private double distancejuli = 0.0;

    private BDLocation location1;
    private LatLng location2;

    private int dis;

    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;
    private int aa=0;

    GeoCoder mSearch =null;//搜索模块，也可去掉地图模块单独使用
    MapView mMapView;
    BaiduMap mBaiduMap;
    boolean isFirstLoc = true;// 是否首次定位
    private MyLocationData locData;
    Button requestLocButton;
    Button weatherButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        weatherButton = (Button)findViewById(R.id.button2);
        cai = findViewById(R.id.cai);
        juli = findViewById(R.id.juli);
        juli1 = findViewById(R.id.juli1);


        final EditText editCity = (EditText)findViewById(R.id.editText);
        final EditText editGeoCodeKey = (EditText)findViewById(R.id.editText2);
        tixing = findViewById(R.id.tixingjuli);

        // 地图初始化
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();// 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);// 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);//设置间隔性定位的时间为1秒
        mLocClient.setLocOption(option);
        mLocClient.start();

        //初始化搜索模块，注册事件监听
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);
        requestLocButton = (Button) findViewById(R.id.button);
        requestLocButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String strCity;
                String strGeo;
                strCity = editCity.getText().toString();
                strGeo = editGeoCodeKey.getText().toString();
                if(strCity.length() == 0){
                    Toast.makeText(MainActivity.this,"城市不能为空",Toast.LENGTH_LONG).show();
                }else if(strGeo.length() == 0){
                    Toast.makeText(MainActivity.this,"具体地址不能为空",Toast.LENGTH_LONG).show();
                }else {
                    mSearch.geocode(new GeoCodeOption().city(editCity.getText().toString()).address(editGeoCodeKey.getText().toString()));
                }
                str11 = tixing.getText().toString();
            }
        });
      /*
      设置Button监听器，当按下《查询天气》按钮后，跳转到天气活动页面
       */
        weatherButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String city = editCity.getText().toString();
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                intent.putExtra("extra_city",city);
                startActivity(intent);
            }
        });
        /*
         彩蛋
         */
        cai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CaiDan_Activity.class);
                startActivity(intent);
            }
        });
    }

    /*
    地址返回结果
     */

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
        if (geoCodeResult == null||geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR){
            Toast.makeText(MainActivity.this,"抱歉，未能找到结果",Toast.LENGTH_LONG).show();
            return;
        }
        mBaiduMap.clear();
        mBaiduMap.addOverlay(
                new MarkerOptions()
                        .position(geoCodeResult.getLocation())                                     //坐标位置
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.dingwei))            //图标
                        .title(geoCodeResult.getAddress())                                         //标题

        );
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(geoCodeResult.getLocation()));
        location2 = geoCodeResult.getLocation();
        String strlinfo = String.format("纬度：%f经度：%f",geoCodeResult.getLocation().latitude,geoCodeResult.getLocation().longitude);

        location21 = geoCodeResult.getLocation().latitude;
        location22 = geoCodeResult.getLocation().longitude;

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {

    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null ||  mMapView== null) {
                return;
            }
            mCurrentLat = location.getLatitude();//获取纬度坐标
            mCurrentLon = location.getLongitude();//获取经度坐标
            location11 = location.getLatitude();
            location12 = location.getLongitude();
            mCurrentAccracy = location.getRadius();//  获取定位精度
            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            /*
            是否第一次定位
             */
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                location11 = location.getLatitude();
                location12 = location.getLongitude();
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }

            double lat1 = (Math.PI/180)*location11;
            double lat2 = (Math.PI/180)*location21;


            double lon1 = (Math.PI/180)*location12;
           double lon2 = (Math.PI/180)*location22;


            //地球半径
            double R = 6371.000;



            //两点间距离 km，如果想要米的话，结果*1000
            double d =  Math.acos(Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon1-lon2))*R;
            if(location21==0||location22==0){}
            else{
                dis = (int) (d * 1000);
                juli.setText("直线距离为："+Integer.toString(dis)+"米");
                juli1.setText("提醒距离为："+str11);
                distancejuli = Double.parseDouble(str11);
            }
   /*         if(distancejuli>dis){
                tixing.setText("");
                Intent intent1 = new Intent(MainActivity.this, BoFang_Activity.class);
                startActivityForResult(intent1,1);
                onStop();
            }*/
            if(distancejuli>dis/*&&aa==0*/)
            {
              //  aa = 1;
                tixing.setText("");
                str11 = "";
                    Intent intent1 = new Intent(MainActivity.this, BoFang_Activity.class);
                    startActivityForResult(intent1,1);
            }

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode ,Intent data)
    {
        switch (requestCode){
            case 1:
            if(requestCode == RESULT_OK) {
                str = data.getStringExtra("str");
                if(str.length()>0){
                    Toast.makeText(this,aa,Toast.LENGTH_SHORT);
                    aa=0;
                    Toast.makeText(this,aa,Toast.LENGTH_SHORT);
                    str = "";
                }
            }
            break;
            default:break;
        }
    }


    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }


}


