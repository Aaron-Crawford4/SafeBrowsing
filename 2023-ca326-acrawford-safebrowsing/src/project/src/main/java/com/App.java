package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;

public class App {

  public static void main( String[] args ) throws JSchException, IOException
  {

      String squidAddress = "172.27.39.179";
      int choice = Integer.parseInt(args[0]);

      if(choice == 1) {
        addWebsiteToBlacklist(args[1], squidAddress);
      }
      if(choice == 2) {
        removeWebsiteFromBlacklist(args[1], squidAddress);
      }
      if(choice == 3) {
        addWebsiteToWhitelist(args[1], squidAddress);
      }
      if(choice == 4) {
        removeWebsiteFromWhitelist(args[1], squidAddress);
      }
      if(choice == 5) {
        SwitchingToWhitelisting(squidAddress);
      }
      if(choice == 6) {
        SwitchingToBlacklisting(squidAddress);
      }
      if(choice == 7) {
        createUser(args[1], args[2], squidAddress);
      }
      if(choice == 8){
        addWebsiteToUserBlacklist(args[1], args[2], squidAddress);
      }
      if(choice == 9){
        removeWebsiteFromUserBlacklist(args[1], args[2], squidAddress);
      }
      if(choice == 10){
        addTimeToUser(args[1], args[2], squidAddress);
      }
      if(choice == 11){
        userBlacklist(args[1], args[2], squidAddress);
      }
      if(choice == 12){
        readUserLogs(args[1], args[2], squidAddress);
      }
      if(choice == 13){
        checkStatusOfUserBlacklist(args[1], squidAddress);
      }
    
  }
  public static void addWebsiteToBlacklist(String website, String squidAddress) throws JSchException, IOException {

    String command = "echo " + website + " >> /etc/squid/blocked_sites";
    JSch jsch = new JSch();
    Session session = jsch.getSession("aaron", squidAddress, 22);
    //getSession("aaron", host: 172.30.20.67, 3128);
    session.setPassword("Maianh23");
    Properties config = new Properties();
    config.put("StrictHostKeyChecking", "no");
    session.setConfig(config);
    session.connect();
    
    Channel channel1 = session.openChannel("exec");
    Channel channel2 = session.openChannel("exec");
    ((ChannelExec) channel1).setCommand(command);
   // ((ChannelExec) channel).setCommand("echo 'Maianh23' | sudo -S service squid reload");
  // ((ChannelExec) channel).setCommand("echo 'test' >> /etc/squid/test.txt");
    channel1.connect();
    channel1.disconnect();

    InputStream in = channel2.getInputStream();
    ((ChannelExec) channel2).setCommand("echo Maianh23'' | sudo -S service squid reload");

    channel2.connect();

    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    String line;

    while((line = reader.readLine()) != null) {
      System.out.println(line);
    }

    channel2.disconnect();
    session.disconnect();
  }
  public static void removeWebsiteFromBlacklist(String website, String squidAddress) throws JSchException, IOException {

    String command = "sed '/" + website + "/d' blocked_sites > test.txt";
    System.out.println(command);
    JSch jsch = new JSch();
    Session session = jsch.getSession("aaron", squidAddress, 22);
    //getSession("aaron", host: 172.30.20.67, 3128);
    session.setPassword("Maianh23");
    Properties config = new Properties();
    config.put("StrictHostKeyChecking", "no");
    session.setConfig(config);
    session.connect();
    
    Channel channel1 = session.openChannel("exec");
    Channel channel2 = session.openChannel("exec");
    Channel channel3 = session.openChannel("exec");
    ((ChannelExec) channel1).setCommand(command);
    channel1.connect();
    channel1.disconnect();

    ((ChannelExec) channel2).setCommand("cp /etc/squid/test.txt /etc/squid/blocked_sites");
    channel2.connect();
    channel2.disconnect();

    InputStream in = channel3.getInputStream();
    ((ChannelExec) channel3).setCommand("echo 'Maianh23' | sudo -S service squid reload");

    channel3.connect();

    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    String line;

    while((line = reader.readLine()) != null) {
      System.out.println(line);
    }

    channel3.disconnect();
    session.disconnect();
  }
  public static void addWebsiteToWhitelist(String website, String squidAddress) throws JSchException, IOException {

    String command = "echo " + website + " >> /etc/squid/unblocked_sites";
    JSch jsch = new JSch();
    Session session = jsch.getSession("aaron", squidAddress, 22);
    session.setPassword("Maianh23");
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
    ((ChannelExec) channel2).setCommand("echo 'Maianh23' | sudo -S service squid reload");

    channel2.connect();

    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    String line;

    while((line = reader.readLine()) != null) {
      System.out.println(line);
    }

    channel2.disconnect();
    session.disconnect();
  }
  public static void removeWebsiteFromWhitelist(String website, String squidAddress) throws JSchException, IOException {

    String command = "sed '/" + website + "/d' unblocked_sites > test.txt";
    System.out.println(command);
    JSch jsch = new JSch();
    Session session = jsch.getSession("aaron", squidAddress, 22);
    //getSession("aaron", host: 172.30.20.67, 3128);
    session.setPassword("Maianh23");
    Properties config = new Properties();
    config.put("StrictHostKeyChecking", "no");
    session.setConfig(config);
    session.connect();
    
    Channel channel1 = session.openChannel("exec");
    Channel channel2 = session.openChannel("exec");
    Channel channel3 = session.openChannel("exec");
    ((ChannelExec) channel1).setCommand(command);
    channel1.connect();
    channel1.disconnect();

    ((ChannelExec) channel2).setCommand("cp /etc/squid/test.txt /etc/squid/unblocked_sites");
    channel2.connect();
    channel2.disconnect();

    InputStream in = channel3.getInputStream();
    ((ChannelExec) channel3).setCommand("echo 'Maianh23' | sudo -S service squid reload");

    channel3.connect();

    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    String line;

    while((line = reader.readLine()) != null) {
      System.out.println(line);
    }

    channel3.disconnect();
    session.disconnect();
  }
  public static void SwitchingToWhitelisting(String squidAddress) throws JSchException, IOException { 

    JSch jsch = new JSch();
    Session session = jsch.getSession("aaron", squidAddress, 22);
    session.setPassword("Maianh23");
    Properties config = new Properties();
    config.put("StrictHostKeyChecking", "no");
    session.setConfig(config);
    session.connect();

    Channel channel1 = session.openChannel("exec");
    ((ChannelExec) channel1).setCommand("echo 'Maianh23' | sudo -S sed -i '/acl blocked_sites url_regex/s/^#*/#/g' /etc/squid/squid.conf");
    InputStream in1 = channel1.getInputStream();
    channel1.connect();
    BufferedReader reader1 = new BufferedReader(new InputStreamReader(in1));
    String line1;
    while((line1 = reader1.readLine()) != null) {
      System.out.println(line1);
     }
    channel1.disconnect();

    Channel channel2 = session.openChannel("exec");
    ((ChannelExec) channel2).setCommand("echo 'Maianh23' | sudo -S sed -i '/http_access deny blocked_sites/s/^#*/#/g' /etc/squid/squid.conf");
    InputStream in2 = channel2.getInputStream();
    channel2.connect();
    BufferedReader reader2 = new BufferedReader(new InputStreamReader(in2));
    String line2;
    while((line2 = reader2.readLine()) != null) {
      System.out.println(line2);
     }
    channel2.disconnect();

    Channel channel3 = session.openChannel("exec");
    ((ChannelExec) channel3).setCommand("echo 'Maianh23' | sudo -S sed -i '/http_access allow all/s/^#*/#/g' /etc/squid/squid.conf");
    InputStream in3 = channel3.getInputStream();
    channel3.connect();
    BufferedReader reader3 = new BufferedReader(new InputStreamReader(in3));
    String line3;
    while((line3 = reader3.readLine()) != null) {
      System.out.println(line3);
     }
    channel3.disconnect();

    Channel channel4 = session.openChannel("exec");
    ((ChannelExec) channel4).setCommand("echo 'Maianh23' | sudo -S sed -i '/acl unblocked_sites url_regex/s/^#*//g' /etc/squid/squid.conf");
    InputStream in4 = channel4.getInputStream();
    channel4.connect();
    BufferedReader reader4 = new BufferedReader(new InputStreamReader(in4));
    String line4;
    while((line4 = reader4.readLine()) != null) {
      System.out.println(line4);
     }
    channel4.disconnect();

    Channel channel5 = session.openChannel("exec");
    ((ChannelExec) channel5).setCommand("echo 'Maianh23' | sudo -S sed -i '/http_access allow unblocked_sites/s/^#*//g' /etc/squid/squid.conf");
    InputStream in5 = channel5.getInputStream();
    channel5.connect();
    BufferedReader reader5 = new BufferedReader(new InputStreamReader(in5));
    String line5;
    while((line5 = reader5.readLine()) != null) {
      System.out.println(line5);
     }
    channel5.disconnect();

    Channel channel6 = session.openChannel("exec");
    ((ChannelExec) channel6).setCommand("echo 'Maianh23' | sudo -S sed -i '/http_access deny all/s/^#*//g' /etc/squid/squid.conf");
    InputStream in6 = channel6.getInputStream();
    channel6.connect();
    BufferedReader reader6 = new BufferedReader(new InputStreamReader(in6));
    String line6;
    while((line6 = reader6.readLine()) != null) {
      System.out.println(line6);
     }
    channel6.disconnect();

     Channel channel7 = session.openChannel("exec");
    InputStream in = channel7.getInputStream();
     ((ChannelExec) channel7).setCommand("echo 'Maianh23' | sudo -S service squid reload");

    channel7.connect();

    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    String line;

    while((line = reader.readLine()) != null) {
      System.out.println(line);
    }

    channel7.disconnect();

    session.disconnect();

  }

  public static void SwitchingToBlacklisting(String squidAddress) throws JSchException, IOException { 

    JSch jsch = new JSch();
    Session session = jsch.getSession("aaron", squidAddress, 22);
    session.setPassword("Maianh23");
    Properties config = new Properties();
    config.put("StrictHostKeyChecking", "no");
    session.setConfig(config);
    session.connect();

    Channel channel1 = session.openChannel("exec");
    ((ChannelExec) channel1).setCommand("echo 'Maianh23' | sudo -S sed -i '/acl unblocked_sites url_regex/s/^#*/#/g' /etc/squid/squid.conf");
    InputStream in1 = channel1.getInputStream();
    channel1.connect();
    BufferedReader reader1 = new BufferedReader(new InputStreamReader(in1));
    String line1;
    while((line1 = reader1.readLine()) != null) {
      System.out.println(line1);
     }
    channel1.disconnect();

    Channel channel2 = session.openChannel("exec");
    ((ChannelExec) channel2).setCommand("echo 'Maianh23' | sudo -S sed -i '/http_access allow unblocked_sites/s/^#*/#/g' /etc/squid/squid.conf");
    InputStream in2 = channel2.getInputStream();
    channel2.connect();
    BufferedReader reader2 = new BufferedReader(new InputStreamReader(in2));
    String line2;
    while((line2 = reader2.readLine()) != null) {
      System.out.println(line2);
     }
    channel2.disconnect();

    Channel channel3 = session.openChannel("exec");
    ((ChannelExec) channel3).setCommand("echo 'Maianh23' | sudo -S sed -i '/http_access deny all/s/^#*/#/g' /etc/squid/squid.conf");
    InputStream in3 = channel3.getInputStream();
    channel3.connect();
    BufferedReader reader3 = new BufferedReader(new InputStreamReader(in3));
    String line3;
    while((line3 = reader3.readLine()) != null) {
      System.out.println(line3);
     }
    channel3.disconnect();

    Channel channel4 = session.openChannel("exec");
    ((ChannelExec) channel4).setCommand("echo 'Maianh23' | sudo -S sed -i '/acl blocked_sites url_regex/s/^#*//g' /etc/squid/squid.conf");
    InputStream in4 = channel4.getInputStream();
    channel4.connect();
    BufferedReader reader4 = new BufferedReader(new InputStreamReader(in4));
    String line4;
    while((line4 = reader4.readLine()) != null) {
      System.out.println(line4);
     }
    channel4.disconnect();

    Channel channel5 = session.openChannel("exec");
    ((ChannelExec) channel5).setCommand("echo 'Maianh23' | sudo -S sed -i '/http_access deny blocked_sites/s/^#*//g' /etc/squid/squid.conf");
    InputStream in5 = channel5.getInputStream();
    channel5.connect();
    BufferedReader reader5 = new BufferedReader(new InputStreamReader(in5));
    String line5;
    while((line5 = reader5.readLine()) != null) {
      System.out.println(line5);
     }
    channel5.disconnect();

    Channel channel6 = session.openChannel("exec");
    ((ChannelExec) channel6).setCommand("echo 'Maianh23' | sudo -S sed -i '/http_access allow all/s/^#*//g' /etc/squid/squid.conf");
    InputStream in6 = channel6.getInputStream();
    channel6.connect();
    BufferedReader reader6 = new BufferedReader(new InputStreamReader(in6));
    String line6;
    while((line6 = reader6.readLine()) != null) {
      System.out.println(line6);
     }
    channel6.disconnect();

     Channel channel7 = session.openChannel("exec");
    InputStream in = channel7.getInputStream();
     ((ChannelExec) channel7).setCommand("echo 'Maianh23' | sudo -S service squid reload");

    channel7.connect();

    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    String line;

    while((line = reader.readLine()) != null) {
      System.out.println(line);
    }

    channel7.disconnect();

    session.disconnect();

  }

  public static void createUser(String name, String password, String squidAddress) throws JSchException, IOException {

    String command = "echo 'Maianh23' | sudo -S htpasswd -b /etc/squid/htpasswd " + name + " " + password + "";
    JSch jsch = new JSch();
    Session session = jsch.getSession("aaron", squidAddress, 22);
    session.setPassword("Maianh23");
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
    ((ChannelExec) channel1).setCommand(command);
    channel1.connect();
    while(true){
      if(channel1.isClosed()){
          break;
      }
  }
    channel1.disconnect();

    ((ChannelExec) channel3).setCommand("echo 'Maianh23' | sudo -S touch /etc/squid/" + name + ".txt");
    channel3.connect();
    while(true){
      if(channel3.isClosed()){
          break;
      }
  }
    channel3.disconnect();
    ((ChannelExec) channel7).setCommand("echo 'Maianh23' | sudo -S chmod 777 " + name + " /etc/squid/" + name + ".txt");
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

    ((ChannelExec) channel5).setCommand("echo 'Maianh23' | sudo -S touch /etc/squid/" + name + "WebsitesBlockedFrom.txt");
    channel5.connect();
    while(true){
      if(channel5.isClosed()){
          break;
      }
  }
    channel5.disconnect();

    ((ChannelExec) channel3).setCommand("echo 'Maianh23' | sudo -S chmod 777 /etc/squid/" + name + "WebsitesBlockedFrom.txt");
    channel3.connect();
    while(true){
      if(channel3.isClosed()){
          break;
      }
  }
    channel3.disconnect();

    command = "echo 'Maianh23' | sudo -S sed -i '/allow flash_ads/a\\\\nacl " + name + "WebsitesBlockedFrom url_regex \"/etc/squid/" + name + "WebsitesBlockedFrom.txt\"\\n#http_access deny " + name + " " + name + "WebsitesBlockedFrom" + "\\nacl " + name + "TimeLimit time 00:00-23:59\\nhttp_access deny " + name + " !" + name + "TimeLimit' /etc/squid/squid.conf";
    System.out.println(command);
    ((ChannelExec) channel6).setCommand(command);
    channel6.connect();
    while(true){
      if(channel6.isClosed()){
          break;
      }
  }
    channel6.disconnect();

    command = "echo 'Maianh23' | sudo -S sed -i '/auth_param basic casesensitive off/a\\\\nacl " + name + " proxy_auth \"/etc/squid/" + name + ".txt\"" + "' /etc/squid/squid.conf";
    //System.out.println(command);
    ((ChannelExec) channel10).setCommand(command);
    channel10.connect();
    while(true){
      if(channel10.isClosed()){
          break;
      }
  }
    channel10.disconnect();

    InputStream in = channel8.getInputStream();
    ((ChannelExec) channel8).setCommand("echo 'Maianh23' | sudo -S service squid reload"
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

  public static void addWebsiteToUserBlacklist(String website, String name, String squidAddress) throws JSchException, IOException {

    String command = "echo " + website + " >> /etc/squid/" + name + "WebsitesBlockedFrom.txt";
    JSch jsch = new JSch();
    Session session = jsch.getSession("aaron", squidAddress, 22);
    //getSession("aaron", host: 172.30.20.67, 3128);
    session.setPassword("Maianh23");
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
    ((ChannelExec) channel2).setCommand("echo 'Maianh23' | sudo -S service squid reload");

    channel2.connect();

    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    String line;

    while((line = reader.readLine()) != null) {
      System.out.println(line);
    }

    channel2.disconnect();
    session.disconnect();
  }

  public static void removeWebsiteFromUserBlacklist(String website, String name, String squidAddress) throws JSchException, IOException {

    String command = "echo 'Maianh23' | sudo -S sed '/" + website + "/d' /etc/squid/" + name + "WebsitesBlockedFrom.txt > /etc/squid/test.txt";
    System.out.println(command);
    JSch jsch = new JSch();
    Session session = jsch.getSession("aaron", squidAddress, 22);
    //getSession("aaron", host: 172.30.20.67, 3128);
    session.setPassword("Maianh23");
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

     ((ChannelExec) channel2).setCommand("echo 'Maianh23' | sudo -S cp /etc/squid/test.txt /etc/squid/" + name + "WebsitesBlockedFrom.txt");
     channel2.connect();
     while(true){
       if(channel2.isClosed()){
           break;
       }
   }
     channel2.disconnect();

    InputStream in = channel3.getInputStream();
    ((ChannelExec) channel3).setCommand("echo 'Maianh23' | sudo -S service squid reload");

    channel3.connect();

    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    String line;

    while((line = reader.readLine()) != null) {
      System.out.println(line);
    }

    channel3.disconnect();
    session.disconnect();
  }

  public static void addTimeToUser(String time, String name, String squidAddress) throws JSchException, IOException {

    if(time.equals("off")) {
      time = "00:00-23:59";
    }
    String command = "echo 'Maianh23' | sudo -S sed -i 's/acl " + name + "TimeLimit time.*/acl " + name + "TimeLimit time " + time + "/' /etc/squid/squid.conf";
    JSch jsch = new JSch();
    Session session = jsch.getSession("aaron", squidAddress, 22);
    //getSession("aaron", host: 172.30.20.67, 3128);
    session.setPassword("Maianh23");
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
    ((ChannelExec) channel2).setCommand("echo Maianh23'' | sudo -S service squid reload");

    channel2.connect();

    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    String line;

    while((line = reader.readLine()) != null) {
      System.out.println(line);
    }

    channel2.disconnect();
    session.disconnect();
  }

  public static void userBlacklist(String swapString, String name, String squidAddress) throws JSchException, IOException { 

    JSch jsch = new JSch();
    Session session = jsch.getSession("aaron", squidAddress, 22);
    session.setPassword("Maianh23");
    Properties config = new Properties();
    config.put("StrictHostKeyChecking", "no");
    session.setConfig(config);
    session.connect();

    if(swapString.equals("off")) {

    Channel channel2 = session.openChannel("exec");
    ((ChannelExec) channel2).setCommand("echo 'Maianh23' | sudo -S sed -i '/http_access deny " + name + " " + name + "WebsitesBlockedFrom/s/^#*/#/g' /etc/squid/squid.conf");
    channel2.connect();
    while(true){
      if(channel2.isClosed()){
          break;
      }}
    channel2.disconnect();
    }
    else if(swapString.equals("on")) {

    Channel channel5 = session.openChannel("exec");
    ((ChannelExec) channel5).setCommand("echo 'Maianh23' | sudo -S sed -i '/http_access deny " + name + " " + name + "WebsitesBlockedFrom/s/^#*//g' /etc/squid/squid.conf");
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
     ((ChannelExec) channel7).setCommand("echo 'Maianh23' | sudo -S service squid reload");

    channel7.connect();

    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    String line;

    while((line = reader.readLine()) != null) {
      System.out.println(line);
    }

    channel7.disconnect();

    session.disconnect();

  }
  public static void readUserLogs(String name, String time, String squidAddress) throws JSchException, IOException {

    String command;
    if(time.equals("week")) {
      command = "echo 'Maianh23' | sudo -S cat /var/log/squid/access.log | perl -p -e 's/^([0-9]*)/\"[\".localtime($1).\"]\"/e' | grep -a " + name + " | grep -a 'www.' | grep -a 'TCP_TUNNEL' | awk '{print $1\" \"$2\" \"$3\" \"substr($5,1,5)\" \"substr($11, 1, length($11) - 4)\" \"$12}' | awk '{print $5}' | sort | uniq -c | sort -nr | head  -9999 > '/etc/squid/templog.txt'";
    }
    else{
      command = "echo 'Maianh23' | sudo -S cat /var/log/squid/access.log | perl -p -e 's/^([0-9]*)/\"[\".localtime($1).\"]\"/e' | grep -a " + name + " | grep -a 'www.' | grep -a 'TCP_TUNNEL' | awk '{print $1\" \"$2\" \"$3\" \"substr($5,1,5)\" \"substr($11, 1, length($11) - 4)\" \"$12}' | grep -a \"$(date +\"%d\")\" | awk '{print $5}' | sort | uniq -c | sort -nr | head  -9999 > '/etc/squid/templog.txt'";
    }
    System.out.println(command);
    JSch jsch = new JSch();
    Session session = jsch.getSession("aaron", squidAddress, 22);
    session.setPassword("Maianh23");
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
      }}
    channel1.disconnect();

    InputStream in = channel2.getInputStream();
    ((ChannelExec) channel2).setCommand("echo 'Maianh23' | sudo -S service squid reload");

    channel2.connect();

    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    String line;

    while((line = reader.readLine()) != null) {
      System.out.println(line);
    }

    channel2.disconnect();
    session.disconnect();
  }

  public static void checkStatusOfUserBlacklist(String name, String squidAddress) throws JSchException, IOException {

    String command = "grep \"http_access allow " + name + " " + name + "WebsitesLimitedTo\" /etc/squid/squid.conf";
    JSch jsch = new JSch();
    Session session = jsch.getSession("aaron", squidAddress, 22);
    session.setPassword("Maianh23");
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
    String status = "";

    while((line = reader1.readLine()) != null) {
      status = line;
    }
    if(status.charAt(0) == '#') {
      status = "false";
    }
    else {
      status = "true";
    }
    //System.out.println(status);
    channel1.disconnect();

    session.disconnect();
  }
}
