package com.safe_browsing;


import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.Button;

import androidx.core.view.ViewCompat;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.Properties;

//Asynchronous task to check if a user has individual blacklist on or off and update ui button colours based on result
public class checkStatusOfUser extends AsyncTask<Void, Void, String> {
    private String name, ip;
    String status;
    Button ib_on_button, ib_off_button;

    public checkStatusOfUser(String name, Button on_button, Button off_button, String status) {
        this.name = name;
        this.ib_off_button = off_button;
        this.ib_on_button = on_button;
        this.ip = "192.168.1.7";
        this.status = status;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            String command = "grep \"http_access deny " + name + " " + name + "WebsitesBlockedFrom\" /etc/squid/squid.conf";
            JSch jsch = new JSch();
            Session session = jsch.getSession("squid-proxy", ip, 22);
            session.setPassword("AaronCiaran");
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();

            Channel channel1 = session.openChannel("exec");
            ((ChannelExec) channel1).setCommand(command);
            InputStream in1 = channel1.getInputStream();

            channel1.connect();

            BufferedReader reader1 = new BufferedReader(new InputStreamReader(in1));
            String line;

            while((line = reader1.readLine()) != null) {
                status = line;
            }
            //if the individual blacklist is commented out, status is false
            if(status.charAt(0) == '#') {
                status = "false";
            }
            //else, status is true
            else {
                status = "true";
            }
            channel1.disconnect();

            session.disconnect();
            //return status to onPostExecute
            return status;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    protected void onPostExecute(String status) {
        super.onPostExecute(status);
        if (status != null) {
            //if individual blacklist is on, change on button colour
            if (status.equals("true")) {
                ViewCompat.setBackgroundTintList(ib_on_button, ColorStateList.valueOf(Color.parseColor("#1A2A33")));
            //if individual blacklist is off, change off button colour
            } else if (status.equals("false")) {
                ViewCompat.setBackgroundTintList(ib_off_button, ColorStateList.valueOf(Color.parseColor("#1A2A33")));
            }
        }
    }
}
