package com.simpleexample.kafka_prod_cons.kafkaConfig;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.simpleexample.kafka_prod_cons.utils.Constants;
import com.simpleexample.kafka_prod_cons.utils.MongoDbOperater;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName BatchConsumer
 * @Description TODO 消费者监听
 * @Author wzw
 * @Date 2020/4/28 15:23
 * @Version 1.0
 **/
@Component
@Slf4j
public class BatchConsumer {

    @Autowired
    private MongoDbOperater mongoDbOperater;

    private String getCollectionName(String messageType){
        if("report".equals(messageType)){
            return "alarm";
        }
        if("singleRide".equals(messageType)){
            return "singleride";
        }
        if("statisticsDaily".equals(messageType)
                ||"statisticsSingleRide".equals(messageType)){
            return "statistics";
        }
        if("statisticsElevator".equals(messageType)){
            return "kpis";
        }
        if("breakdownProbability".equals(messageType)){
            return "breakdownProbability";
        }
        return "othertype";
    }
    @KafkaListener(topics ="${spring.kafka.consumer.topic}", containerFactory = "kafkaListenerContainerFactory")
    public void consumerBatch(List<ConsumerRecord<?, ?>> records) {
        System.out.println("接收到消息数量：{" + records.size() + "}");
        for (ConsumerRecord record : records) {
            Optional<?> kafkaMessage = Optional.ofNullable(record.value());
            System.out.println("Received: " + record);
            if (kafkaMessage.isPresent()) {
                JSONObject message = JSON.parseObject(record.value().toString());
                String topic = record.topic();
                System.out.println("接收到消息：" + message);
                try {
                    if (message != null && message.getJSONObject("data").getJSONObject("payload") != null) {
                        JSONObject obj = message.getJSONObject("data").getJSONObject("payload");
                        message.putAll(obj);
                        message.put("_id", new Date().getTime());
                        String messageType = message.getString("messageType");
                        if ("singleRide".equals(messageType) || "statisticsElevator".equals(messageType) || "report".equals(messageType)) {
                            message.put("timestamp", message.getDate("timestamp"));
                        } else if ("statisticsDaily".equals(messageType)) {
                            message.put("firstRideTimeStamp", message.getDate("firstRideTimeStamp"));
                            message.put("interpolationTimestamp", message.getDate("interpolationTimestamp"));
                            message.put("lastRideTimeStamp", message.getDate("lastRideTimeStamp"));
                        } else if ("statisticsSingleRide".equals(messageType)) {
                            message.put("firstRideTimeStamp", message.getDate("firstRideTimeStamp"));
                        }
                        if ("report".equals(messageType)) {// 自动给发过来的alarm 设置状态为0
                            message.put("status", 0); // 多条件查询时,字符串容易处理
                            message.put("alarmLevel", Constants.alarmCode.get(message.getString("severity")));
                        }
                        message.remove("data");
                        message.put("createtime", new Date());//用于导出数据
                        log.info("-------------------Start insert to the MongoDB---------------");
                        // mongoDbOperater.insert(getCollectionName(message.getString("messageType")), JSON.toJSONString(message));//存入数据库
                        log.info("message save success!===>data=" + JSON.toJSONString(message));
                    }
                } catch (Exception e) {
                    log.error("Parse data failed!,is not a json data");
                }

                //用于调试时的备份的数据
                try {
                    JSONObject backdata = new JSONObject();
                    backdata.put("_id", new Date().getTime());
                    backdata.put("topic", topic);
                    backdata.put("payload", JSON.parseObject(message.toString()));
                    backdata.put("createtime", new Date());
                    // mongoDbOperater.insert("iotdata", JSON.toJSONString(bakdata));
                    log.info("backup data success!=====>" + backdata);
                } catch (Exception e) {
                    log.error("save back data failed, data is :" + JSON.toJSONString(message));
                }
            }
        }
    }
}
