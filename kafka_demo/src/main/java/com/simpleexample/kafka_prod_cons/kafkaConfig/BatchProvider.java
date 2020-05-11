package com.simpleexample.kafka_prod_cons.kafkaConfig;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.simpleexample.kafka_prod_cons.utils.MongoDbOperater;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import com.simpleexample.kafka_prod_cons.utils.Constants;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

@Slf4j
@RestController
@RequestMapping("/api/kafka")
public class BatchProvider {

    private KafkaTemplate<String, String> kafkaTemplate;
    private Gson jsonConverter;
@Value("${spring.kafka.consumer.topic}")
private String topic;

    @Autowired
    public BatchProvider(KafkaTemplate<String, String> kafkaTemplate, Gson jsonConverter) {
        this.kafkaTemplate = kafkaTemplate;
        this.jsonConverter = jsonConverter;
    }

    public BatchProvider() {
    }

    /**
     * 发送消息
     **/

    @PostMapping
    //@Scheduled(fixedRate = 5000)//定时任务
    public void post() {
        //for(int i=0;i<5;i++){
        // kafkaTemplate.send("topic1", "我是第["+i+"]个批处理消息");
//            try {
//                Thread.sleep(1000*2);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        //}
        String msg = "{\"data\": {\"payload\": \"{'name':'wangwu'}\" },\"messageType\": \"report\",\"timestamp\": \"1586941127955\" ,\"severity\":\"WARNING\"}";
        kafkaTemplate.send(topic,
                msg);

    }

    //"{\"data\": {\"payload\": \"{'name':'wangwu'}\" },\"messageType\": \"report\",\"timestamp\": \"1586941127955\" ,\"severity\":\"WARNING\"}"
    //
//    @PostMapping("/v2")
//    public void post(@RequestBody List<String> msgs) {
//        msgs.forEach(msg -> kafkaTemplate.send("topic5", msg));
//    }
@Resource(name="consumerQueueThreadPool")
private ExecutorService consumerQueueThreadPool;
    @KafkaListener(topics = "topic3")
    public void getMes(List<ConsumerRecord<?,?>> records) {
        log.info("---------------begin11--------");
        //创建线程池
for(int i=0;i<5;i++){
    final int j=i;
    consumerQueueThreadPool.execute(()-> System.out.println(j));
}
        ThreadPoolExecutor pool=new ThreadPoolExecutor(2,3,10,TimeUnit.SECONDS,new LinkedBlockingQueue<>(10));

        //        //任务1
//            pool.execute(() -> {
//                    try {
//                        Thread.sleep(1000);
//                        System.out.println(records.get(0).value() + "-1-" + Thread.currentThread().getName());
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//            });
//            pool.execute(()->{
//            System.out.println(records.get(0).value() + "-2-" + Thread.currentThread().getName());
//        });
//        pool.execute(()->{
//            System.out.println(records.get(0).value() + "-3-" + Thread.currentThread().getName());
//        });
//        pool.execute(()->{
//            System.out.println(records.get(0).value() + "-4-" + Thread.currentThread().getName());
//        });
//pool.shutdown();
               ExecutorService ec = Executors.newFixedThreadPool(10);
               for(int i=0;i<5;i++) {
                   ec.execute(() -> {
                       System.out.println(records.get(0).value() + "-1-" + Thread.currentThread().getName());
                   });
               }
                   //            FutureTask futureTask = new FutureTask<String>(()->{
//                System.out.println("beign ----");
//                Thread.sleep(1000);
//                int sum=0;
//                for(int i=0;i<10;i++){
//                    sum+=i;
//                }
//                return "sum="+sum;});
//        ec.submit(futureTask);
//        ec.shutdown();
//        try {
//            System.out.println("结果："+futureTask.get());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
        //输出有序
//        FutureTask<Integer> integerFutureTask = new FutureTask<Integer>(()-> {
//            for(int i=0;i<10;i++){
//                System.out.println(records.get(0).value());
//            System.out.println("线程："+i);}
//            },3);
//        ec.submit(integerFutureTask);
//        ec.shutdown();
//        try{
//            Thread.sleep(1000);
//            System.out.println("执行结束");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

//    @KafkaListener(topics = "topic3")
//    public void getMes2(ConsumerRecord<?,?> record){
//        System.out.println(record.value()+"-------2-----------");
//    }
    /**
     * 单条消费
     **/
//    @KafkaListener(topics = "topic1"
//            //topicPartitions = {
//          //  @TopicPartition(topic = "myTopic",partitions = {"0","2"},partitionOffsets = @PartitionOffset(partition = "1",initialOffset = "4"))
//)
//    public void getFromKafka(String record){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for(int i=0;i<10;i++){
//                    System.out.println(record+"---"+Thread.currentThread().getName());
//                }
//            }
//        }).start();
//
//    }

//    @KafkaListener(topics = "topic1")
//   public void getFromKafka2(ConsumerRecord<?,?> record){
//        System.out.println("msg:"+record.toString()+"^^");
//    }

    /**
     * 批量消费
     *
     **/
//    @KafkaListener(topics = "topic5",containerFactory = "kafkaListenerContainerFactory")
//    public void listen(List<String> records){
//        records.forEach(record -> System.out.println(record+"========="));
//    }
//    @KafkaListener(topics = "topic5",containerFactory = "kafkaListenerContainerFactory")
//    public void listen2(List<String> records){
//        records.forEach(record -> System.out.println(record+" "));
//    }

}
