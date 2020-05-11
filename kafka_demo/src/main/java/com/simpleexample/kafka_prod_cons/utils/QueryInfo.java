package com.simpleexample.kafka_prod_cons.utils;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import org.bson.BsonDocument;
import org.bson.conversions.Bson;

/**
 *
 * @author xiaolie
 */

public class QueryInfo {
    public String collName;
    public String action;
    public Bson keys;
    public Bson query;
    public Long limit;
    public Long skip;
    public BsonDocument order;
    public BsonDocument updateObj;

    public void setQuery(String json) {
        query = BsonDocument.parse(json);
    }

    public void setSort(String json) {
        order = BsonDocument.parse(json);
    }

    public String debugStr() {
        return "  collName:" + collName +
                "  action:" + action + 
                " query:" + query + 
                "  limit:" + limit + 
                "  sort:" + order +
                "  updateObj:" + updateObj;
    }
}
