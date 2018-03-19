package com.project.start.listener;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.project.commons.utils.ApolloUtils;
import org.fusesource.stomp.jms.StompJmsDestination;
import org.fusesource.stomp.jms.message.StompJmsBytesMessage;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DashBoardListener implements Runnable{
    @Override
    public void run() {

        List<List<String>> listFiles = new ArrayList<>();

        File root = new File(DashBoardListener.class.getClassLoader().getResource("collectData/").getPath());
        Arrays
                .stream(Objects.requireNonNull(root.listFiles( )))
                .filter(file -> file.getName().endsWith("json"))
                .map(File::toPath)
                .forEach(path -> {
                    try {
                        //List<String> lines = Files.readAllLines(path);
                        //System.out.println("lines = " + lines);
                        List<String> lines = Files.readAllLines(path);
                        listFiles.add(lines);
                    } catch (IOException e) {
                        e.printStackTrace( );
                    }
                });
                

        // 开始发送消息

        Session session = null;

        Connection connection = null;

        try {

            String destination = "/topic/bausch-receive";
            connection = ApolloUtils.getConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination dest = new StompJmsDestination(destination);

            MessageConsumer consumer = session.createConsumer(dest);

            log.debug("Waiting for messages from dashboard");
            while(true) {
                try {

                    Message msg = consumer.receive();

                    if( msg instanceof TextMessage) {
                        String body = ((TextMessage) msg).getText();
                        log.debug("revisevi messages from dashboard"+body);

                    } else {
                        log.info("Unexpected message type: "+msg.getClass());
                        StompJmsBytesMessage mess = (StompJmsBytesMessage)msg;

                        String topic = new String(mess.getContent().toByteArray());
                        JSONObject json = JSON.parseObject(topic);
                        int interval = json.getIntValue("interval")>0?json.getIntValue("interval"):1;
                        String ids = json.getString("ids");
                        String receiveTopic  = json.getString("topic");
                        log.info("receive = "+ receiveTopic + " - " + interval + " - " + ids);

                        Destination receiveDest = new StompJmsDestination(receiveTopic);
                        MessageProducer producer = session.createProducer(receiveDest);

                        String dataBody = listToString(listFiles.get(0));

                        log.info(dataBody);
                        TextMessage msgSend = session.createTextMessage(dataBody);
                        msgSend.setStringProperty("topic", receiveTopic);

                        producer.send(msgSend);
                        log.info("body: {}", new String(mess.getContent().toByteArray()));
                    }

                } catch (Exception e) {

                    log.error("get data from dashboard error",e);
                }
            }
        } catch (JMSException e) {
            log.error("error",e);
        }finally{
            if(session != null){
                try {
                    session.close();
                } catch (JMSException e) {
                    //
                }
            }

        }
    }

    public static String readFileAsString(File file) throws IOException {
        return Files.lines(file.toPath()).collect(Collectors.joining(""));
    }

    public static String readFileAsString(String filePath) throws IOException {
        return readFileAsString(new File(filePath));
    }

    public static String readFileBufferAsString(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder content = new StringBuilder();

            for (String line; (line = reader.readLine()) != null;) {
                content.append(line);
            }

            return content.toString();
        }
    }

    public static String listToString(List<String> stringList){
        if (stringList == null) {
            return null;
        }

        StringBuilder result=new StringBuilder();
        boolean flag=false;
        for (String string : stringList) {
            if (flag) {
                result.append("");
            }else {
                flag=true;
            }
            result.append(string);
        }
        return result.toString();
    }

}
