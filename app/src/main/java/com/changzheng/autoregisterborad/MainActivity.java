package com.changzheng.autoregisterborad;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private MyReceiver myReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myReceiver=new MyReceiver();//构建对象
        IntentFilter filter=new IntentFilter();
//        订阅广播事件,wifi状态
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction("com.changzhengsoft.app.EXIT");
        registerReceiver(myReceiver, filter);
    }

//开始方法
    @Override
    protected void onStart() {

        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(myReceiver);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(myReceiver);
        super.onDestroy();
    }

    public void sendMyBroder(View view) {
        Intent intent=new Intent();
        intent.setAction("com.changzhengsoft.app.EXIT");
        sendBroadcast(intent);

    }

    //    私有类
    private class  MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
//            触发的是wifi状态改表事件
            if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)){
//                wifi状态有哪些:wifi服务实现和广播
                int state=intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
                switch (state){
//                    正在关闭wifi
                    /*
                    Wifi 网卡状态

                        1. WIFI_STATE_DISABLED: WIFI网卡不可用

                        2. WIFI_STATE_DISABLING: WIFI正在关闭

                        3. WIFI_STATE_ENABLED:WIFI网卡可用

                        4. WIFI_STATE_ENABLING:WIFI网卡正在打开

                        5. WIFI_STATE_UNKNOWN:未知网卡状态
                     */
                    case WifiManager.WIFI_STATE_DISABLED:
                        Toast.makeText(context,"wifi不可用",Toast.LENGTH_SHORT).show();
                        break;
                    case WifiManager.WIFI_STATE_DISABLING:
                        Toast.makeText(context,"wifi正在关闭",Toast.LENGTH_SHORT).show();
                        break;
                    case WifiManager.WIFI_STATE_UNKNOWN:
                        Toast.makeText(context,"WIFI出现状况",Toast.LENGTH_SHORT).show();
                        break;
                    case WifiManager.WIFI_STATE_ENABLED:
                        Toast.makeText(context,"wifi可用",Toast.LENGTH_SHORT).show();
                        break;
                    case WifiManager.WIFI_STATE_ENABLING:
                        Toast.makeText(context,"wifi正在打开",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;

                }

            }else if (intent.getAction().equals("com.changzhengsoft.app.EXIT")){
                Toast.makeText(getApplicationContext(),"activity马上关闭咯!",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
