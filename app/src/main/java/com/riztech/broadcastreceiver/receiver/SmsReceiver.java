package com.riztech.broadcastreceiver.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.riztech.broadcastreceiver.MainActivity;

public class SmsReceiver extends BroadcastReceiver {

    public static final String SMS_BUNDLE = "pdus";
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();

        if(intentExtras!=null){
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            String smsMessageStr = "";
            for(Object smsData : sms){
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) smsData);
                // read sms
                String smsBody = smsMessage.getMessageBody();
                // phone number
                String address = smsMessage.getOriginatingAddress();

                smsMessageStr = smsMessageStr + "\nfrom : "+address+ " Message : "+smsBody;

            }

            Toast.makeText(context, smsMessageStr, Toast.LENGTH_SHORT).show();

//            MainActivity inst = MainActivity.instance();
//            inst.updateList(smsMessageStr);
        }


    }
}
