package com.riztech.broadcastreceiver;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsMessage;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private static final int PERMISSION_REQUEST = 1001;



    TextView txtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtMessage = findViewById(R.id.txtMessage);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS},
                    1001);

        }

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(mySMSReciever, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       unregisterReceiver(mySMSReciever);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }




    BroadcastReceiver mySMSReciever = new BroadcastReceiver()
    {
        public static final String SMS_BUNDLE = "pdus";

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle intentExtras = intent.getExtras();

            if (intentExtras != null) {
                Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
                String smsMessageStr = "";
                for (Object smsData : sms) {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) smsData);
                    // read sms
                    String smsBody = smsMessage.getMessageBody();
                    // phone number
                    String address = smsMessage.getOriginatingAddress();

                    smsMessageStr = smsMessageStr + "\nfrom : " + address + " Message : " + smsBody;

                }

                txtMessage.setText(smsMessageStr);

                Toast.makeText(context, smsMessageStr, Toast.LENGTH_SHORT).show();

            }


        }
    };
}
