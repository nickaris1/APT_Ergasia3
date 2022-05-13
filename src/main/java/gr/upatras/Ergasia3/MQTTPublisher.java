package gr.upatras.Ergasia3;

import org.eclipse.paho.client.mqttv3.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class MQTTPublisher implements MqttCallback {
    MqttClient myClient;
    MqttConnectOptions connOpt;
    // IMqttClient publisher = new MqttClient("tcp://iot.eclipse.org:1883",publisherId);
    static final String M2MIO_THING = UUID.randomUUID().toString();
    static final String BROKER_URL = "tcp://test.mosquitto.org:1883";

    private static final Logger log = LoggerFactory.getLogger(MQTTPublisher.class);
    public String TOPIC = "";

    public MQTTPublisher(String topic) {
        this.TOPIC = topic;
    }

    /**
     * connectionLost This callback is invoked upon losing the MQTT connection.
     */
    public void connectionLost(Throwable t) {
        log.info("Connection lost!");
        String clientID = M2MIO_THING;
        // code to reconnect to the broker would go here if desired
        connOpt = new MqttConnectOptions();
        connOpt.setCleanSession(true);
        connOpt.setKeepAliveInterval(30);

        try {
            myClient = new MqttClient(BROKER_URL, clientID);
            myClient.setCallback(this);
            myClient.connect(connOpt);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        log.info("Connected to " + BROKER_URL);
    }

    /**
     * deliveryComplete This callback is invoked when a message published by this
     * client is successfully received by the broker.
     */
    public void deliveryComplete(IMqttDeliveryToken token) {
        log.info("Message Delivered");
    }

    /**
     * messageArrived This callback is invoked when a message is received on a
     * subscribed topic.
     */
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        log.info("\n");
        log.info("-------------------------------------------------");
        log.info("| Topic:" + topic);
        log.info("| Message: " + new String(message.getPayload()));
        log.info("-------------------------------------------------");
        log.info("\n");
    }

    /**
     * sendMqttMessage The main functionality of this simple example. Create a MQTT
     * client, connect to broker, pub/sub, disconnect.
     */
    public void sendMqttMessage(String jsonData) {
        // setup MQTT Client
        String clientID = M2MIO_THING;
        connOpt = new MqttConnectOptions();
        connOpt.setCleanSession(true);
        connOpt.setKeepAliveInterval(30);

        // connOpt.setUserName(M2MIO_USERNAME);
        // connOpt.setPassword(M2MIO_PASSWORD_MD5.toCharArray());
        // Connect to Broker

        try {
            myClient = new MqttClient(BROKER_URL, clientID);
            myClient.setCallback(this);
            myClient.connect(connOpt);
        } catch (MqttException e) {
            e.printStackTrace();
//            System.exit(-1);
            return;
        }

        log.info("Connected to " + BROKER_URL);
        String myTopic = TOPIC;
        MqttTopic topic = myClient.getTopic(myTopic);

//        publish messages if publisher

        int pubQoS = 0;
        MqttMessage message = new MqttMessage(jsonData.getBytes());
        message.setQos(pubQoS);
        message.setRetained(false);
        // Publish the message
        log.info("Publishing to topic \"" + topic + "\" qos " + pubQoS + "\" value " + jsonData);
        MqttDeliveryToken token = null;
        try {
            // publish message to broker
            token = topic.publish(message);
            // Wait until the message has been delivered to the broker
            token.waitForCompletion();
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // disconnect
        try {
            myClient.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
