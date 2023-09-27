package com.safe_browsing;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.List;
import java.util.Properties;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;


public class Jsch_code {
    static String ip = "192.168.1.7";

    //method for adding website to blacklist
    public static void addWebsiteToBlacklist(String website) throws JSchException, IOException {

        String command = "echo " + website + " >> /etc/squid/blocked_sites";
        JSch jsch = new JSch();
        Session session = jsch.getSession("squid-proxy", ip, 22);
        session.setPassword("AaronCiaran");
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();

        Channel channel1 = session.openChannel("exec");
        Channel channel2 = session.openChannel("exec");
        ((ChannelExec) channel1).setCommand(command);
        channel1.connect();
        channel1.disconnect();

        InputStream in = channel2.getInputStream();
        ((ChannelExec) channel2).setCommand("echo 'AaronCiaran' | sudo -S service squid reload");

        channel2.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;

        while((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        channel2.disconnect();
        session.disconnect();
    }
    //method for removing website from blacklist
    public static void removeWebsiteFromBlacklist(String website) throws JSchException, IOException {

        String command = "echo 'AaronCiaran' | sudo -S sed '/" + website + "/d' /etc/squid/blocked_sites > /etc/squid/test.txt";
        System.out.println(command);
        JSch jsch = new JSch();
        Session session = jsch.getSession("squid-proxy", ip, 22);
        session.setPassword("AaronCiaran");
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();

        Channel channel1 = session.openChannel("exec");
        Channel channel2 = session.openChannel("exec");
        Channel channel3 = session.openChannel("exec");
        ((ChannelExec) channel1).setCommand(command);
        channel1.connect();
        while(true){
            if(channel1.isClosed()){
                break;
            }
        }
        channel1.disconnect();

        ((ChannelExec) channel2).setCommand("echo 'AaronCiaran' | sudo -S cp /etc/squid/test.txt /etc/squid/blocked_sites");
        channel2.connect();
        while(true){
            if(channel2.isClosed()){
                break;
            }
        }
        channel2.disconnect();

        InputStream in = channel3.getInputStream();
        ((ChannelExec) channel3).setCommand("echo 'AaronCiaran' | sudo -S service squid reload");

        channel3.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;

        while((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        channel3.disconnect();
        session.disconnect();
    }
    //method for creating a user
    public static void createUser(String name, String password) throws JSchException, IOException {

        String command = "echo 'AaronCiaran' | sudo -S htpasswd -b /etc/squid/htpasswd " + name + " " + password + "";
        JSch jsch = new JSch();
        Session session = jsch.getSession("squid-proxy", ip, 22);
        session.setPassword("AaronCiaran");
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();

        Channel channel1 = session.openChannel("exec");
        Channel channel3 = session.openChannel("exec");
        Channel channel5 = session.openChannel("exec");
        Channel channel6 = session.openChannel("exec");
        Channel channel7 = session.openChannel("exec");
        Channel channel8 = session.openChannel("exec");
        Channel channel9 = session.openChannel("exec");
        Channel channel10 = session.openChannel("exec");
        Channel channel11 = session.openChannel("exec");

        System.out.println(command);
        ((ChannelExec) channel1).setCommand(command);
        channel1.connect();
        while(true){
            if(channel1.isClosed()){
                break;
            }
        }
        channel1.disconnect();


        ((ChannelExec) channel3).setCommand("echo 'AaronCiaran' | sudo -S touch /etc/squid/" + name + ".txt");
        channel3.connect();
        while(true){
            if(channel3.isClosed()){
                break;
            }
        }
        channel3.disconnect();
        ((ChannelExec) channel7).setCommand("echo 'AaronCiaran' | sudo -S chmod 777 " + name + " /etc/squid/" + name + ".txt");
        channel7.connect();
        while(true){
            if(channel7.isClosed()){
                break;
            }
        }
        channel7.disconnect();

        command = "echo " + name + " > /etc/squid/" + name + ".txt";
        ((ChannelExec) channel9).setCommand(command);
        channel9.connect();
        while(true){
            if(channel9.isClosed()){
                break;
            }
        }
        channel9.disconnect();


        ((ChannelExec) channel5).setCommand("echo 'AaronCiaran' | sudo -S touch /etc/squid/" + name + "WebsitesBlockedFrom.txt");
        channel5.connect();
        while(true){
            if(channel5.isClosed()){
                break;
            }
        }
        channel5.disconnect();

        ((ChannelExec) channel11).setCommand("echo 'AaronCiaran' | sudo -S chmod 777 " + name + " /etc/squid/" + name + "WebsitesBlockedFrom.txt");
        channel11.connect();
        while(true){
            if(channel11.isClosed()){
                break;
            }
        }
        channel11.disconnect();

        command = "echo 'AaronCiaran' | sudo -S sed -i '/http_access deny blocked_sites all/a\\\\nacl " + name + "WebsitesBlockedFrom url_regex \"/etc/squid/" + name + "WebsitesBlockedFrom.txt\"\\n#http_access deny " + name + " " + name + "WebsitesBlockedFrom" + "\\nacl " + name + "TimeLimit time 00:00-23:59\\nhttp_access deny " + name + " !" + name + "TimeLimit' /etc/squid/squid.conf";
        System.out.println(command);
        ((ChannelExec) channel6).setCommand(command);
        channel6.connect();
        while(true){
            if(channel6.isClosed()){
                break;
            }
        }
        channel6.disconnect();

        command = "echo 'AaronCiaran' | sudo -S sed -i '/auth_param basic casesensitive off/a\\\\nacl " + name + " proxy_auth \"/etc/squid/" + name + ".txt\"" + "' /etc/squid/squid.conf";
        ((ChannelExec) channel10).setCommand(command);
        channel10.connect();
        while(true){
            if(channel10.isClosed()){
                break;
            }
        }
        channel10.disconnect();

        InputStream in = channel8.getInputStream();
        ((ChannelExec) channel8).setCommand("echo 'AaronCiaran' | sudo -S service squid reload"
        );

        channel8.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;

        while((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        while(true){
            if(channel8.isClosed()){
                break;
            }
        }

        channel8.disconnect();

        session.disconnect();
    }
    //method for adding website to individual user blacklist
    public static void addWebsiteToUserBlacklist(String website, String name) throws JSchException, IOException {

        String command = "echo " + website + " >> /etc/squid/" + name + "WebsitesBlockedFrom.txt";
        JSch jsch = new JSch();
        Session session = jsch.getSession("squid-proxy", ip, 22);
        //getSession("aaron", host: 172.30.20.67, 3128);
        session.setPassword("AaronCiaran");
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();

        Channel channel1 = session.openChannel("exec");
        Channel channel2 = session.openChannel("exec");
        ((ChannelExec) channel1).setCommand(command);
        channel1.connect();
        while(true){
            if(channel1.isClosed()){
                break;
            }
        }
        channel1.disconnect();

        InputStream in = channel2.getInputStream();
        ((ChannelExec) channel2).setCommand("echo AaronCiaran | sudo -S service squid reload");

        channel2.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;

        while((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        channel2.disconnect();
        session.disconnect();
    }
    //method for removing website from individual user blacklist
    public static void removeWebsiteFromUserBlacklist(String website, String name) throws JSchException, IOException {

        String command = "echo 'AaronCiaran' | sudo -S sed '/" + website + "/d' /etc/squid/" + name + "WebsitesBlockedFrom.txt > /etc/squid/test.txt";
        System.out.println(command);
        JSch jsch = new JSch();
        Session session = jsch.getSession("squid-proxy", ip, 22);
        //getSession("aaron", host: 172.30.20.67, 3128);
        session.setPassword("AaronCiaran");
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();

        Channel channel1 = session.openChannel("exec");
        Channel channel2 = session.openChannel("exec");
        Channel channel3 = session.openChannel("exec");
        ((ChannelExec) channel1).setCommand(command);
        channel1.connect();
        while (true){
            if(channel1.isClosed()) {
                break;
            }
        }
        channel1.disconnect();

        ((ChannelExec) channel2).setCommand("cp /etc/squid/test.txt /etc/squid/" + name + "WebsitesBlockedFrom.txt");
        channel2.connect();
        while(true){
            if(channel2.isClosed()){
                break;
            }
        }
        channel2.disconnect();

        InputStream in = channel3.getInputStream();
        ((ChannelExec) channel3).setCommand("echo 'AaronCiaran' | sudo -S service squid reload");

        channel3.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;

        while((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        channel3.disconnect();
        session.disconnect();
    }
    //method for changing user time limits
    public static void addTimeToUser(String time, String name) throws JSchException, IOException {

        if(time.equals("off")) {
            time = "00:00-23:59";
        }
        String command = "echo 'AaronCiaran' | sudo -S sed -i 's/acl " + name + "TimeLimit time.*/acl " + name + "TimeLimit time " + time + "/' /etc/squid/squid.conf";

        JSch jsch = new JSch();
        Session session = jsch.getSession("squid-proxy", ip, 22);
        //getSession("aaron", host: 172.30.20.67, 3128);
        session.setPassword("AaronCiaran");
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();

        Channel channel1 = session.openChannel("exec");
        Channel channel2 = session.openChannel("exec");
        ((ChannelExec) channel1).setCommand(command);
        channel1.connect();
        while(true){
            if(channel1.isClosed()){
                break;
            }
        }
        channel1.disconnect();

        InputStream in = channel2.getInputStream();
        ((ChannelExec) channel2).setCommand("echo AaronCiaran | sudo -S service squid reload");

        channel2.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;

        while((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        channel2.disconnect();
        session.disconnect();
    }
    //method for switching individual user blacklist on or off
    public static void userBlacklist(String swapString, String name) throws JSchException, IOException {

        JSch jsch = new JSch();
        Session session = jsch.getSession("squid-proxy", ip, 22);
        session.setPassword("AaronCiaran");
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();

        if(swapString.equals("off")) {

            Channel channel2 = session.openChannel("exec");
            ((ChannelExec) channel2).setCommand("echo 'AaronCiaran' | sudo -S sed -i '/http_access deny " + name + " " + name + "WebsitesBlockedFrom/s/^#*/#/g' /etc/squid/squid.conf");
            channel2.connect();
            while(true){
                if(channel2.isClosed()){
                    break;
                }}
            channel2.disconnect();

        }
        else if(swapString.equals("on")) {

            Channel channel5 = session.openChannel("exec");
            ((ChannelExec) channel5).setCommand("echo 'AaronCiaran' | sudo -S sed -i '/http_access deny " + name + " " + name + "WebsitesBlockedFrom/s/^#*//g' /etc/squid/squid.conf");
            InputStream in5 = channel5.getInputStream();
            channel5.connect();
            BufferedReader reader5 = new BufferedReader(new InputStreamReader(in5));
            String line5;
            while((line5 = reader5.readLine()) != null) {
                System.out.println(line5);
            }
            channel5.disconnect();

        }

        Channel channel7 = session.openChannel("exec");
        InputStream in = channel7.getInputStream();
        ((ChannelExec) channel7).setCommand("echo 'AaronCiaran' | sudo -S service squid reload");

        channel7.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;

        while((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        channel7.disconnect();

        session.disconnect();

    }
    //method for reading individual user log information
    public static void readUserLogs(String name, String time) throws JSchException, IOException {

        String command;
        if(time.equals("week")) {
            command = "echo 'AaronCiaran' | sudo -S cat /var/log/squid/access.log | perl -p -e 's/^([0-9]*)/\"[\".localtime($1).\"]\"/e' | grep -a " + name + " | grep -a 'www.' | grep -a 'TCP_TUNNEL' | awk '{print $1\" \"$2\" \"$3\"] \"substr($11, 1, length($11) - 4)\" \"$12}' | awk '{print $4}' | sort | uniq -c | sort -nr | head  -9999 > '/etc/squid/templog.txt'";
        }
        else{
            command = "echo 'AaronCiaran' | sudo -S cat /var/log/squid/access.log | perl -p -e 's/^([0-9]*)/\"[\".localtime($1).\"]\"/e' | grep -a " + name + " | grep -a 'www.' | grep -a 'TCP_TUNNEL' | awk '{print $1\" \"$2\" \"$3\"] \"substr($11, 1, length($11) - 4)\" \"$12}' | grep -a \"$(date +\"%d\")\" | awk '{print $4}' | sort | uniq -c | sort -nr | head  -9999 > '/etc/squid/templog.txt'";
        }
        System.out.println(command);
        JSch jsch = new JSch();
        Session session = jsch.getSession("squid-proxy", ip, 22);
        session.setPassword("AaronCiaran");
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();

        Channel channel1 = session.openChannel("exec");
        ((ChannelExec) channel1).setCommand(command);
        channel1.connect();
        while(true){
            if(channel1.isClosed()){
                break;
            }}
        channel1.disconnect();

        session.disconnect();
    }
}
//Asynchronous tasks that use Jsch but do not require an onPostExecute
class Background_Execute extends AsyncTask{
    @Override
    protected List<String> doInBackground(Object[] objects) {
        try{
            switch((int) objects[0]) {
                case 1:
                    Jsch_code.addWebsiteToBlacklist(objects[1].toString());
                    break;
                case 2:
                    Jsch_code.removeWebsiteFromBlacklist(objects[1].toString());
                    break;
                case 7:
                    Jsch_code.createUser(objects[1].toString(), objects[2].toString());
                    break;
                case 8:
                    Jsch_code.addWebsiteToUserBlacklist(objects[1].toString(), objects[2].toString());
                    break;
                case 9:
                    Jsch_code.removeWebsiteFromUserBlacklist(objects[1].toString(), objects[2].toString());
                    break;
                case 10:
                    Jsch_code.addTimeToUser(objects[1].toString(), objects[2].toString());
                    break;
                case 11:
                    Jsch_code.userBlacklist(objects[1].toString(), objects[2].toString());
                    break;
                case 12:
                    Jsch_code.readUserLogs(objects[1].toString(), objects[2].toString());
                    break;
            }
        } catch (JSchException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
