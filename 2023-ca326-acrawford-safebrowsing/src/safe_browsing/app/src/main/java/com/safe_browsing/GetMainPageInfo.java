package com.safe_browsing;



import android.os.AsyncTask;

import android.widget.TextView;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.Properties;

//Asynchronous task to get log information for displaying information on main app page
public class GetMainPageInfo extends AsyncTask<Void, Void, ArrayList<String>> {
    private String ip;
    String number_of_sites, number_of_blocked_sites, percentage_blocked_sites;
    TextView num_all_site, num_blocked_site, percentage_blocked;

    public GetMainPageInfo(TextView num_all_site, TextView num_blocked_site, TextView percentage_blocked) {
        this.num_all_site = num_all_site;
        this.num_blocked_site = num_blocked_site;
        this.percentage_blocked = percentage_blocked;
        this.ip = "192.168.1.7";
    }

    @Override
    protected ArrayList<String> doInBackground(Void... voids) {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession("squid-proxy", ip, 22);
            session.setPassword("AaronCiaran");
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();

            //get total number of logs from log file
            String command = "echo 'AaronCiaran' | sudo -S grep 'www.' /var/log/squid/access.log | grep -c 'TCP'";
            System.out.println(command);
            Channel channel1 = session.openChannel("exec");
            ((ChannelExec) channel1).setCommand(command);
            InputStream in1 = channel1.getInputStream();

            channel1.connect();

            BufferedReader reader1 = new BufferedReader(new InputStreamReader(in1));
            String line;

            while((line = reader1.readLine()) != null) {
                number_of_sites = line;
            }
            channel1.disconnect();

            //get number of logs that have been denied from log file
            command = "echo 'AaronCiaran' | sudo -S grep 'www.' /var/log/squid/access.log | grep -c 'TCP_DENIED'";
            System.out.println(command);

            Channel channel2 = session.openChannel("exec");
            ((ChannelExec) channel2).setCommand(command);
            InputStream in2 = channel2.getInputStream();

            channel2.connect();

            BufferedReader reader2 = new BufferedReader(new InputStreamReader(in2));
            while((line = reader2.readLine()) != null) {
                number_of_blocked_sites = line;
            }
            channel2.disconnect();
            session.disconnect();

            //get number of blocked requests as a percentage
            Float percentage = Float.parseFloat(number_of_blocked_sites) / Float.parseFloat(number_of_sites) * 100;
            percentage_blocked_sites = String.valueOf(percentage);
            percentage_blocked_sites = String.format("%.2f", percentage);

            ArrayList lines = new ArrayList<String>();
            lines.add(number_of_sites);
            lines.add(number_of_blocked_sites);
            lines.add(percentage_blocked_sites);
            //return the information to onPostExecute
            return lines;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    protected void onPostExecute(ArrayList<String> lines){
        super.onPostExecute(lines);
        //set the information on the ui
        if (lines != null) {
            num_all_site.setText("Total queries logged this week: " + number_of_sites);
            num_blocked_site.setText("Total queries blocked this week: "+ number_of_blocked_sites);
            percentage_blocked.setText("Percentage of queries blocked: " + percentage_blocked_sites + "%");
        }
    }
}