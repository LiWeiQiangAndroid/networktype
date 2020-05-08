package com.wsecar.networktype;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import static android.telephony.TelephonyManager.NETWORK_TYPE_1xRTT;
import static android.telephony.TelephonyManager.NETWORK_TYPE_CDMA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EDGE;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EVDO_0;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EVDO_A;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EVDO_B;
import static android.telephony.TelephonyManager.NETWORK_TYPE_GPRS;
import static android.telephony.TelephonyManager.NETWORK_TYPE_HSDPA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_HSPA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_HSPAP;
import static android.telephony.TelephonyManager.NETWORK_TYPE_IDEN;
import static android.telephony.TelephonyManager.NETWORK_TYPE_LTE;
import static android.telephony.TelephonyManager.NETWORK_TYPE_NR;
import static android.telephony.TelephonyManager.NETWORK_TYPE_UMTS;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private TelephonyManager telephonyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, PackageManager.PERMISSION_GRANTED);
        telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            switch (telephonyManager.getDataNetworkType()) {
                case NETWORK_TYPE_EDGE:
                case NETWORK_TYPE_GPRS:
                case NETWORK_TYPE_CDMA:
                case NETWORK_TYPE_IDEN:
                case NETWORK_TYPE_1xRTT:
                    textView.setText("2G");
                    break;
                case NETWORK_TYPE_UMTS:
                case NETWORK_TYPE_HSDPA:
                case NETWORK_TYPE_HSPA:
                case NETWORK_TYPE_HSPAP:
                case NETWORK_TYPE_EVDO_0:
                case NETWORK_TYPE_EVDO_A:
                case NETWORK_TYPE_EVDO_B:
                    textView.setText("3G");
                    break;
                case NETWORK_TYPE_LTE:
                    textView.setText("4G");
                    break;
                case NETWORK_TYPE_NR:
                    textView.setText("5G");
                    break;
                default:
                    ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo activeNetInfo = cm.getActiveNetworkInfo();
                    if (activeNetInfo != null && activeNetInfo.isConnectedOrConnecting()) {
                        if ("wifi".equals(activeNetInfo.getTypeName().toLowerCase())) {
                            textView.setText("wlan网络");
                        }
                    } else {

                        textView.setText("无网络");
                    }


            }
        }
    }

}
