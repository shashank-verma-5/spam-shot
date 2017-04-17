package com.example.shashankverma.smsapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.attr.phoneNumber;
import static android.R.id.input;
import static android.R.id.message;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    ArrayList<String> smsMessagesList = new ArrayList<>();
    Button buttonSend;
    ListView listViewMessages;
    EditText editTextMessages;
    ArrayAdapter arrayAdapter;
    private static final int READ_SMS_PERMISSIONS_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Display Messages
        listViewMessages = (ListView) findViewById(R.id.listViewMessages);
        editTextMessages = (EditText) findViewById(R.id.editTextEnterMessage);
        buttonSend = (Button) findViewById(R.id.buttonSend);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, smsMessagesList);
        listViewMessages.setAdapter(arrayAdapter);

        refreshSmsInbox();  // Updating the list every time a new message arrives

        buttonSend.setOnClickListener(this);

    }


    public void refreshSmsInbox() {
        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        arrayAdapter.clear();
        do {
            String str = "SMS From: " + smsInboxCursor.getString(indexAddress) +
                    "\n" + smsInboxCursor.getString(indexBody) + "\n";
            arrayAdapter.add(str);
        } while (smsInboxCursor.moveToNext());
    }

// Get Permissions


    // Sending messages

    @Override
    public void onClick(View view) {

        SmsManager sms = SmsManager.getDefault();


        // if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
        //       != PackageManager.PERMISSION_GRANTED) {
        //          getPermissionToReadSMS();
        //} //else {
       // smsManager.sendTextMessage("YOUR PHONE NUMBER HERE",null, editTextMessages.getText().toString(),null, null);
        PendingIntent sentPI;
        String SENT = "SMS_SENT";

        sentPI = PendingIntent.getBroadcast(this, 0,new Intent(SENT), 0);

        //sms.sendTextMessage(phoneNumber, null, message, sentPI, null);

        Toast.makeText(this, "Message sent!", Toast.LENGTH_SHORT).show();
        //}
        //}


    }














}