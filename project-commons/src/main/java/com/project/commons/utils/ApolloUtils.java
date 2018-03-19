package com.project.commons.utils;

import com.typesafe.config.Config;
import lombok.extern.slf4j.Slf4j;
import org.fusesource.stomp.jms.StompJmsConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;

@Slf4j
public class ApolloUtils {

    private static Connection connection = null;

    public static Connection getConnection() {

        Config conf = CommonUtils.getConf();

        if(connection == null){

            String user = conf.getString("apollo.userName");
            String password = conf.getString("apollo.password");
            String host = conf.getString("apollo.host");
            int port = conf.getInt("apollo.port");
            try {

                StompJmsConnectionFactory factory = new StompJmsConnectionFactory();
                factory.setBrokerURI("tcp://" + host + ":" + port);
                connection = factory.createConnection(user, password);
                log.info("阿波罗 - 连接进行中");
                connection.start();
            } catch (JMSException e) {
                log.error("connection apollo error",e);
                return null;
            }
        }
        return connection;
    }
}

