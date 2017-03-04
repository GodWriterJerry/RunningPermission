package com.example.administrator.runningpermission;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button mButton1,mButton2;
    private EditText mEditText1,mEditText2,mEditText3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText1= (EditText) findViewById(R.id.edit_call);
        mButton1 = (Button) findViewById(R.id.make_call);
        mEditText2= (EditText) findViewById(R.id.edit_smsnum);
        mEditText3= (EditText) findViewById(R.id.edit_sms);
        mButton2= (Button) findViewById(R.id.btn_send);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    call();
                }
            }
        });
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 2);
                } else {
                    sms();
                }
            }
        });
    }

    private void call() {
        try {
            String str1=mEditText1.getText().toString();
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:"+str1));
            startActivity(intent);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void sms(){
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("smsto:" + mEditText2.getText().toString().trim()));
            intent.putExtra("sms_body", mEditText3.getText().toString().trim());
            startActivity(intent);
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

            switch (requestCode) {
                case 1:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        call();
                    } else {
                        Toast.makeText(this, "打电话授权被拒绝", Toast.LENGTH_SHORT).show();
                    }
                case 2:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        sms();
                    } else {
                        Toast.makeText(this, "发短信授权被拒绝", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
            }
        }

    }
