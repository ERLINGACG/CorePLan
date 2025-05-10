package com.erling.controllerJ.mqtt;

import com.erling.serviceJ.mqtt.MqttService;
import com.erling.utilJ.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MqttController {
    private final MqttService  mqttService;

    @Autowired
    public MqttController(MqttService mqttService) {
        this.mqttService = mqttService; // 注入 MqttService
    }

    @GetMapping("/mqtt/publish")
    public ResponseEntity<Result<?>> publish(
            @RequestParam String topic,
            @RequestParam String payload) {
        mqttService.sendToMqtt(payload, topic);
        return ResponseEntity.ok(Result.SUCCESS("publish test"));
    }


}
