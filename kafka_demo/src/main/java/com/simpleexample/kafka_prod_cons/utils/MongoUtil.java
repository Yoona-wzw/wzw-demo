package com.simpleexample.kafka_prod_cons.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.bson.json.JsonWriterSettings;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MongoUtil {

    public static <T> T toBean(BasicDBObject dbObject, Class<T> clzss){

        String realJson = dbObject.toJson(JsonWriterSettings.builder().build());

        T obj = JSON.parseObject(realJson,clzss);

        return obj;

    }

    public static <T> T toBean(Document document, Class<T> clzss){

        /*String realJson = document.toJson(JsonWriterSettings.builder().build());

        T obj = JSON.parseObject(realJson,clzss);*/

        return parseToObject(document, clzss);

    }

    public static <T> List<T> toBean(List<Document> documents, Class<T> clzss){
        ArrayList<T> result = new ArrayList<>();
        for (Document document : documents){
            /*String realJson = document.toJson(JsonWriterSettings.builder().build());
            T obj = JSON.parseObject(realJson,clzss);*/
            result.add(parseToObject(document, clzss));
        }

        return result;

    }

    public static <T> BasicDBObject toDBObject(T object){

        String json = JSON.toJSONString(object);

        BasicDBObject basicDBObject = BasicDBObject.parse(json);

        return basicDBObject;

    }

    public static <T> Document toDocument(T object){

        String json = JSON.toJSONString(object);

        Document document = Document.parse(json);

        return document;

    }

    public static <Q> Q parseToObject(Document document, Class<Q> target) {
        try {
            if (document == null) return null;
            Q result = target.newInstance();
            Field[] fields = target.getDeclaredFields();
            for (Field f : fields) {
                f.setAccessible(true);
                Object value = document.get(f.getName());
                if (value == null)
                    continue;
                else if (f.getType() == Integer.class)
                    f.set(result, ((Number) value).intValue());
                else if (f.getType() == Long.class)
                    f.set(result, ((Number) value).longValue());
                else if (f.getType() == Date.class)
                    f.set(result, (Date) value);
                else if (f.getType() == Double.class)
                    f.set(result, Double.valueOf(String.valueOf(value)));
                else if (f.getType() == String.class)
                    f.set(result, String.valueOf(value));
                else{
//                    System.out.println(f.getName());
                    f.set(result, document.get(f.getName(), f.getType()));
                }
            }
            return result;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<JSONObject> HashMapConverterJsonObject(FindIterable<HashMap> datas){
        if (datas == null)return null;
        HashMap first = datas.first();
        if (first == null)return null;
        List<JSONObject> result = new ArrayList<>();
        for (HashMap data : datas) {
            String json = JSON.toJSONString(data);
            JSONObject object = JSON.parseObject(json);
            result.add(object);
        }
        return result;
    }
}
