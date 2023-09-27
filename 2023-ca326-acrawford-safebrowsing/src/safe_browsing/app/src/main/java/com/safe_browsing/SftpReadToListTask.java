 package com.safe_browsing;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

//Asynchronous task to read from a file using sftp and display as a list
public class SftpReadToListTask extends AsyncTask<Void, Void, List<String>> {
    private String user;
    private String host;
    private String pass;
    private int port;
    private String remoteFile;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private Activity activity;
    ArrayList lines;

    public SftpReadToListTask(String user, int port, String pass, String remoteFile,ListView listView,Activity activity, ArrayList lines) {
        this.user = user;
        this.host = "192.168.1.7";
        this.port = port;
        this.pass = pass;
        this.remoteFile = remoteFile;
        this.listView = listView;
        this.activity = activity;
        this.lines = lines;
    }

    @Override
    protected List<String> doInBackground(Void... voids) {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            session.setPassword(pass);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;

            InputStream stream = sftpChannel.get(remoteFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            String line;
            while ((line = reader.readLine()) != null) {
                //if the file is htpasswd, split the line by ":" and only add the user name part
                if (remoteFile.equals("/etc/squid/htpasswd")){
                    line = line.split(":", 2)[0];
                    lines.add(line);
                //else, add the line
                }else {
                    lines.add(line);
                }
            }

            reader.close();
            sftpChannel.exit();
            session.disconnect();
            //return lines to onPostExecute
            return lines;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<String> lines) {
        super.onPostExecute(lines);
        //if lines is not null, display the list
        if (lines != null) {
            adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, lines);
            listView.setAdapter(adapter);
        }
    }
}

