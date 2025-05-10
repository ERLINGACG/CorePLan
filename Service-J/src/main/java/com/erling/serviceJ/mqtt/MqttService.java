package com.erling.serviceJ.mqtt;
import com.erling.pojo.example.DataModel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Getter
public class MqttService {

    private final MessageChannel mqttOutboundChannel;

    private SimpMessagingTemplate messagingTemplate;
    private String topic;
    private String payload;

    @Autowired
    public MqttService(MessageChannel mqttOutboundChannel) {
        this.mqttOutboundChannel = mqttOutboundChannel;
    }
    @Autowired
    public void setMessagingTemplate(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    // 发送消息到默认主题
    public void sendToMqtt(String payload) {
        mqttOutboundChannel.send(MessageBuilder.withPayload(payload).build());
    }

    // 发送消息到指定主题
    public void sendToMqtt(String payload, String topic) {
        mqttOutboundChannel.send(MessageBuilder.withPayload(payload)
                .setHeader("mqtt_topic", topic).build());
    }


    // 接收消息处理（监听mqttInputChannel）
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void handleIncomingMessage(Message<?> message) {
        String topic = message.getHeaders().get("mqtt_receivedTopic", String.class);
        String payload = message.getPayload().toString();
        System.out.printf("收到来自 [%s] 的消息: %s%n", topic, payload);
        this.payload = payload;
        this.topic = topic;
        if (topic != null) {
            this.Waring(topic,payload);
        }
    }

    public void Waring(String topic,String payload) {
        DataModel dataModel = new DataModel();
        float value1 = 0;
        if(topic.equals("/topic/temp")){


            value1 = Float.parseFloat(payload);
            System.out.printf("当前温度:"+value1);
            if(value1>60){
                System.out.println("温度超过60度，需要警告！");
            }
        }else if(topic.equals("/topic/humi")){

        }
        if(topic.equals("/topic/temp_humidity")){
            Pattern pattern = Pattern.compile("温度:([\\d.]+)C 湿度:([\\d.]+)%");
            Matcher matcher = pattern.matcher(payload);
            if(matcher.find()) {
                float temp = Float.parseFloat(matcher.group(1));
                float humidity = Float.parseFloat(matcher.group(2));
                dataModel.setTemp(temp);
                dataModel.setHump(humidity);
                messagingTemplate.convertAndSend("/topic/sensorData",dataModel);
            }

            }


    }
}
