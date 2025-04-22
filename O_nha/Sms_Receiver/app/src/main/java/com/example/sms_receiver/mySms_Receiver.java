package com.example.sms_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class mySms_Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ProcessSms(context, intent);
    }

    private void ProcessSms(Context context, Intent intent) {
        Bundle mybundle = intent.getExtras();
        String mesage = "";
        String body = "";
        String address = "";
        if (mybundle != null){
            Object[] mysms = (Object[]) mybundle.get("pdus");
            for (int i=0;i<mysms.length;i++){
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) mysms[i]);
                body += sms.getMessageBody(); // Lấy nội dung tin nhắn
                address = sms.getOriginatingAddress(); // Lấy sdt
            }
            mesage = "Có 1 tin nhắn đến từ "+ address+"\n" + body + "vừa gửi đến";
            Toast.makeText(context, mesage, Toast.LENGTH_LONG).show();
        }
    }
}