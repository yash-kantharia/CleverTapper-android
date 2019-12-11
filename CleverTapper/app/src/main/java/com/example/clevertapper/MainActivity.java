package com.example.clevertapper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.clevertap.android.sdk.CTInboxActivity;
import com.clevertap.android.sdk.CTInboxListener;
import com.clevertap.android.sdk.CTInboxStyleConfig;
import com.clevertap.android.sdk.CTInboxTabAdapter;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.CleverTapInstanceConfig;


import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Boolean.TRUE;

public class MainActivity extends AppCompatActivity implements CTInboxListener {

    private CleverTapAPI cleverTapDefaultInstance;
    Button app_inbox_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button_fire;
        Button push_profile;
        final EditText eventiptxt;
        final EditText eventprop;
        final EditText eventpropval;
        Button getCTIDbutton;
        Button login;

        final EditText name;
        final EditText email;
        final EditText phone;
        final EditText id;


        setContentView(R.layout.activity_main);

        eventiptxt = (EditText) findViewById(R.id.eventiptxt);
        eventprop = (EditText) findViewById(R.id.eventprop);
        eventpropval = (EditText) findViewById(R.id.eventpropval);
        button_fire = (Button) findViewById(R.id.button_fire);

        getCTIDbutton = (Button) findViewById(R.id.getctidbutton);
        final TextView ctid = (TextView) findViewById(R.id.ctidtext);

        login = (Button) findViewById(R.id.loginbutton);
        name = (EditText) findViewById(R.id.uname);
        email = (EditText) findViewById(R.id.emailtxt);
        phone = (EditText) findViewById(R.id.phonetxt);
        id = (EditText) findViewById(R.id.identity);

        app_inbox_button = findViewById(R.id.app_inbox_button);
        push_profile = findViewById(R.id.push_profile);


        //Clevertap
        cleverTapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());


        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.DEBUG);   //Set Log level to DEBUG log warnings or other important messages

        //--------LOCATION---------
        Location location = cleverTapDefaultInstance.getLocation();
        cleverTapDefaultInstance.setLocation(location);




/*
        HashMap<String, Object> profileUpdate = new HashMap<String, Object>();

//Update pre-defined profile properties
        profileUpdate.put("Name", "Tony Stark");
        profileUpdate.put("Email", "tony@stark.com");
        profileUpdate.put("id", 123456789);
//Update custom profile properties
        profileUpdate.put("Plan Type", "Silver");
        profileUpdate.put("Favorite Food", "Pizza");

        clevertapDefaultInstance.pushProfile(profileUpdate);
        */


        cleverTapDefaultInstance.enableDeviceNetworkInfoReporting(true);


        cleverTapDefaultInstance.createNotificationChannel(getApplicationContext(), "112233", "test", "testchannel", NotificationManager.IMPORTANCE_MAX, true);

        cleverTapDefaultInstance.createNotificationChannel(getApplicationContext(), "123456", "123456", "Your Channel Description", NotificationManager.IMPORTANCE_MAX, true);
// Creating a Notification Channel With Sound Support
        cleverTapDefaultInstance.createNotificationChannel(getApplicationContext(), "got", "Game of Thrones", "Game Of Thrones", NotificationManager.IMPORTANCE_MAX, true, "gameofthrones.mp3");

// How to add a sound file to your app : https://developer.clevertap.com/docs/add-a-sound-file-to-your-android-app

        //APP inbox
            //Set the Notification Inbox Listener
            cleverTapDefaultInstance.setCTNotificationInboxListener(this);
            //Initialize the inbox and wait for callbacks on overridden methods
            cleverTapDefaultInstance.initializeInbox();


        button_fire.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                // event with properties
                HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();
                prodViewedAction.put(String.valueOf(eventprop.getText().toString()), eventpropval.getText().toString());

                cleverTapDefaultInstance.pushEvent(eventiptxt.getText().toString(), prodViewedAction);

            }
        });

        getCTIDbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                // event with properties
                ctid.setText(cleverTapDefaultInstance.getCleverTapID());
                

            }
        });



        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                // event with properties
                // each of the below mentioned fields are optional
// if set, these populate demographic information in the Dashboard
                HashMap<String, Object> profileUpdate = new HashMap<String, Object>();
                profileUpdate.put("Name", name.getText().toString());                  // String
                profileUpdate.put("Identity", id.getText().toString());                    // String or number
                profileUpdate.put("Email", email.getText().toString());               // Email address of the user
                profileUpdate.put("Phone", phone.getText().toString());                 // Phone (with the country code, starting with +)


                cleverTapDefaultInstance.onUserLogin(profileUpdate);


            }
        });

        push_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> profileUpdate = new HashMap<String, Object>();
                profileUpdate.put("Name", name.getText().toString());                  // String
                profileUpdate.put("Identity", id.getText().toString());                    // String or number
                profileUpdate.put("Email", email.getText().toString());               // Email address of the user
                profileUpdate.put("Phone", phone.getText().toString());                 // Phone (with the country code, starting with +)


                cleverTapDefaultInstance.pushProfile(profileUpdate);
            }
        });


    }

    public void inboxDidInitialize() {

        app_inbox_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> tabs = new ArrayList<>();
                tabs.add("Promotions");
                tabs.add("Offers");
                //We support upto 2 tabs only. Additional tabs will be ignored

                CTInboxStyleConfig styleConfig = new CTInboxStyleConfig();
                styleConfig.setTabs(tabs);//Do not use this if you don't want to use tabs
                styleConfig.setTabBackgroundColor("#a6a6a6");//provide Hex code in string ONLY
                styleConfig.setSelectedTabIndicatorColor("#00b300");
                styleConfig.setSelectedTabColor("#d1d1e0");
                styleConfig.setUnselectedTabColor("#FFFFFF");
                styleConfig.setBackButtonColor("#FFFFFF");
                styleConfig.setNavBarTitleColor("#FFFFFF");
                styleConfig.setNavBarTitle("MY INBOX");
                styleConfig.setNavBarColor("#000000");
                styleConfig.setInboxBackgroundColor("#bfbfbf");

                cleverTapDefaultInstance.showAppInbox(styleConfig); //Opens activity with Tabs

            }
        });
    }

    @Override
    public void inboxMessagesDidUpdate(){

    }
}



