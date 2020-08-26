package constants;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.EditText;

import androidx.annotation.RequiresApi;

public class Otp_Reciver extends BroadcastReceiver {
    private  static EditText editText;

    public void setEditText(EditText editText) {
        Otp_Reciver.editText = editText;
    }

    // OnReceive will keep trace when sms is been received in mobile
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent intent) {
        //message will be holding complete sms that is received
        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        for (SmsMessage sms : messages) {

            String msg = sms.getMessageBody();
            // here we are spliting the sms using " : " symbol
            String otp = msg.split("is ")[1];
            editText.setText(otp);
        }
    }

}