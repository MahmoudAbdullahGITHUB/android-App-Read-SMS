package com.example.beso.read_sms;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private static final int R_per = 123;

    public void BuLoad(View view) {
        if ((int) Build.VERSION.SDK_INT>=23){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)!= PackageManager.PERMISSION_GRANTED){
                if(!shouldShowRequestPermissionRationale(Manifest.permission.READ_SMS)){
                    requestPermissions(new String []{Manifest.permission.READ_SMS},R_per);
                }
                return;
            }

        }

        LoadInboxMessages();

    }


    public void onRequestPermissionsResult(int requestCode, String[] Permission, int[] grantRequest) {
        switch (requestCode) {
            case R_per:
                if (grantRequest[0] == PackageManager.PERMISSION_GRANTED) {
                    LoadInboxMessages();
                } else {

                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, Permission, grantRequest);
        }
    }


    void LoadInboxMessages() {

        System.out.println("here you 1:");

        try {

            String sms = "";
            Uri uriSMSURI = Uri.parse("content://sms/inbox");

            Cursor cur = getContentResolver().query(uriSMSURI, null, null, null, null);
            System.out.println("cursor "+cur);
            cur.moveToPosition(0);
            System.out.println("here you 2:");
            while (cur.moveToNext()) {
                sms += "From : " + cur.getString(cur.getColumnIndex("address")) + cur.getString(cur.getColumnIndex("body")) + "\n";
            }

            System.out.println("here you" + sms);
            TextView textView = (TextView) findViewById(R.id.txtv);
            textView.setText(sms);
        } catch (Exception ex) {

        }

    }


}
