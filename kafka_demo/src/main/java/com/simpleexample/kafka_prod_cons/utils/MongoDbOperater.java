package com.simpleexample.kafka_prod_cons.utils;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;


/**
 * @author xiaolie
 */
public class MongoDbOperater {
    static {
        ConvertUtils.register(new LongConverter(null), Long.class);
        ConvertUtils.register(new ShortConverter(null), Short.class);
        ConvertUtils.register(new IntegerConverter(null), Integer.class);
        ConvertUtils.register(new DoubleConverter(null), Double.class);
        ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
        ConvertUtils.register(new DateConverter(null), Date.class);
    }
    CodecRegistry pojoCodecRegistry;
    Log logger = LogFactory.getLog(MongoDbOperater.class);

    @Value("${spring.data.mongodb.database}")
    private String dbName;

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public MongoDbOperater() {
    }

    public MongoDbOperater(String address) {
        pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        mongoClient = MongoClients.create(address);
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public void setMongoClient(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    private MongoClient mongoClient;
    
    public MongoDatabase getDatabase(String dbName) {
        return mongoClient.getDatabase(dbName).withCodecRegistry(pojoCodecRegistry);
    }

    public MongoDatabase getDatabase() {
        return mongoClient.getDatabase(dbName).withCodecRegistry(pojoCodecRegistry);
    }

    public void insert(String dbName, String colName, Document doc) {
        MongoDatabase db = getDatabase(dbName);
        MongoCollection coll = db.getCollection(colName);
        coll.insertOne(doc);
    }
    
    public void insert(String dbName, String colName, Map map) {
        MongoDatabase db = getDatabase(dbName);
        MongoCollection coll = db.getCollection(colName);
        Document doc = map2Document(map);
        coll.insertOne(doc);
    }

    public void insert(String dbName, String colName, Object obj) throws Exception {
        MongoDatabase db = getDatabase(dbName);
        if (obj instanceof Document) {
            MongoCollection coll = db.getCollection(colName);
            coll.insertOne((Document)obj);
        } else if (obj instanceof Map) {
            MongoCollection coll = db.getCollection(colName);
            coll.insertOne(map2Document((Map)obj));
        } else {
            MongoCollection coll = db.getCollection(colName, obj.getClass());
            coll.insertOne(obj);
        }
        //insert(dbName, colName, BeanUtilEx.transBean2Map(obj));
    }

    public void insert(String colName, String json) {
        MongoDatabase db = getDatabase(dbName);
        MongoCollection coll = db.getCollection(colName);
        Document doc = Document.parse(json);
        coll.insertOne(doc);
    }

    public UpdateResult update(String dbName, String colName, Map findMap, Document recordMap) throws Exception {
        return update(dbName, colName, map2Bson(findMap), recordMap);
    }

    public UpdateResult update(String dbName, String colName, Bson findMap, Document recordMap) throws Exception {
        MongoDatabase db = getDatabase(dbName);
        MongoCollection coll = db.getCollection(colName);
        UpdateResult result = coll.updateMany(findMap, new Document("$set", recordMap));
        logger.debug(result.toString());
        return result;
    }

    public UpdateResult update(String dbName, String colName, Bson findMap, BsonDocument recordMap) throws Exception {
        MongoDatabase db = getDatabase(dbName);
        MongoCollection coll = db.getCollection(colName);
        UpdateResult result = coll.updateMany(findMap, new Document("$set", recordMap));
        logger.debug(result.toString());
        return result;
    }

    public UpdateResult updateByBean(String dbName, String colName, Bson findMap, Object obj) throws Exception {
        MongoDatabase db = getDatabase(dbName);
        MongoCollection coll = db.getCollection(colName, obj.getClass());
        UpdateResult result = coll.updateMany(findMap, new Document("$set", obj));
        logger.debug(result.toString());
        return result;
    }
    /*
    public UpdateResult updateByBean(String dbName, String colName, Bson findMap, Object obj) throws Exception {
        MongoDatabase db = getDatabase(dbName);
        MongoCollection coll = db.getCollection(colName);
        UpdateResult result = coll.updateMany(findMap, new Document("$set", map2Document(BeanUtilEx.transBean2Map(obj))));
        logger.debug(result.toString());
        return result;
    }*/
    public UpdateResult update(String dbName, String colName, Map findMap, Map recordMap) throws Exception {
        return update(dbName, colName, map2Bson(findMap), map2Document(recordMap));
    }

    public UpdateResult updateOne(String dbName, String colName, Bson findMap, Map recordMap) throws Exception {
        return updateOne(dbName, colName, findMap, map2Document(recordMap));
    }

    public UpdateResult updateOne(String dbName, String colName, Bson findMap, Document recordMap) throws Exception {
        MongoDatabase db = getDatabase(dbName);
        MongoCollection coll = db.getCollection(colName);
        UpdateResult result = coll.updateOne(findMap, new Document("$set", recordMap));
        logger.debug(result.toString());
        return result;
    }

    public UpdateResult updateOne(String colName, Bson findMap, Document recordMap) throws Exception {
        MongoDatabase db = getDatabase(dbName);
        MongoCollection coll = db.getCollection(colName);
        UpdateResult result = coll.updateOne(findMap, new Document("$set", recordMap));
        logger.debug(result.toString());
        return result;
    }

    public UpdateResult update(String dbName, String colName, Bson findMap, Map map) throws Exception {
        MongoDatabase db = getDatabase(dbName);
        MongoCollection coll = db.getCollection(colName);
        return coll.updateMany(findMap, new Document("$set", map2Document(map)));
    }

    public UpdateResult update(String dbName, String colName, String findJson, String json) {
        MongoDatabase db = getDatabase(dbName);
        MongoCollection coll = db.getCollection(colName);
        return coll.updateMany(BsonDocument.parse(findJson), new Document("$set", BsonDocument.parse(json)));
    }

    public UpdateResult updateOne(String dbName, String colName, String findJson, String json) {
        MongoDatabase db = getDatabase(dbName);
        MongoCollection coll = db.getCollection(colName);
        return coll.updateOne(BsonDocument.parse(findJson), new Document("$set", BsonDocument.parse(json)));
    }
    
    /*
    public UpdateResult update(String dbName, String sql, Object obj) throws Exception {
        QueryInfo query = sql2QueryInfo(dbName, sql);
        return update(dbName, query.collName, query.query, obj);
    } */

    public DeleteResult remove(String colName, Map query) {
        MongoDatabase db = getDatabase(dbName);
        MongoCollection coll = db.getCollection(colName);
        return coll.deleteMany(new Document(query));
    }
    
    public Document get(String dbName, String collName, String hexId) {
        MongoDatabase db = getDatabase(dbName);
        MongoCollection coll = db.getCollection(collName);
        ObjectId id = new ObjectId(hexId);
        return (Document)coll.find(Filters.eq("_id", id)).first();
    }

    public <T> T get(Class<T> clazz, String dbName, String collName, String hexId) {
        MongoDatabase db = getDatabase(dbName);
        MongoCollection coll = db.getCollection(collName, clazz);
        ObjectId id = new ObjectId(hexId);
        return (T)coll.find(Filters.eq("_id", id), clazz).first();
    }
    
    public <T> List<T> findByKeyValue(Class<T> clazz, String dbName, String collName, String field, Object value) {
        MongoDatabase db = getDatabase(dbName);
        MongoCollection coll = db.getCollection(collName, clazz);
        FindIterable cur = coll.find(Filters.eq(field, value), clazz);
        return cursor2list(cur);
    }

    public <T> T findOneByKeyValue(Class<T> clazz, String dbName, String collName, String field, Object value) {
        MongoDatabase db = getDatabase(dbName);
        MongoCollection coll = db.getCollection(collName, clazz);
        return (T)coll.find(Filters.eq(field, value), clazz).first();
    }

    public Document findOne(QueryInfo queryInfo) {
        MongoDatabase db = getDatabase(dbName);
        MongoCollection coll = db.getCollection(queryInfo.collName);
        Document cur = (Document)coll.find(queryInfo.query).first();
        return cur;
    }

    public Object findOneByOrder(QueryInfo queryInfo) {
        MongoDatabase db = getDatabase(dbName);
        MongoCollection coll = db.getCollection(queryInfo.collName);
        FindIterable cursor = null;
        if (queryInfo.query != null) {
            cursor = coll.find(queryInfo.query);
        } else {
            cursor = coll.find();
        }
        if (queryInfo.order != null) {
            cursor = cursor.sort(queryInfo.order);
        }
        if (queryInfo.limit != null) {
            cursor.limit(queryInfo.limit.intValue());
        }
        if (queryInfo.skip != null) {
            cursor.skip(queryInfo.skip.intValue());
        }
        List list = cursor2list(cursor);
        return list.isEmpty() ? null : list.get(0);
    }
    
    public <T> T findOne(Class<T> clazz, QueryInfo queryInfo) {
        MongoDatabase db = getDatabase(dbName);
        MongoCollection coll = db.getCollection(queryInfo.collName);
        if (queryInfo.order == null){
            Document cur = (Document)coll.find(queryInfo.query).first();
            return MongoUtil.toBean(cur, clazz);
        }else{
            FindIterable cursor = null;
            if (queryInfo.query != null) {
                cursor = coll.find(queryInfo.query);
            } else {
                cursor = coll.find();
            }
            if (queryInfo.order != null) {
                cursor = cursor.sort(queryInfo.order);
            }
            List<T> list = MongoUtil.toBean(cursor2list(cursor), clazz);
            return list.isEmpty() ? null : list.get(0);
        }
    }

    public <T> T findOne(Class<T> clazz, String dbName, String collName, Map queryMap) {
        return findOne(clazz, dbName, collName, map2Bson(queryMap));
    }

    public <T> T findOne(Class<T> clazz, String dbName, String collName, Bson queryMap) {
        MongoDatabase db = getDatabase(dbName);
        MongoCollection coll = db.getCollection(collName, clazz);
        return (T)coll.find(queryMap, clazz).first();
    }

    /**
     * 根据条件排序
     * @param clazz
     * @param collName
     * @param filters
     * @param sort
     * @param <T>
     * @return
     */
    public <T> T findone(Class<T> clazz,  String collName, Bson filters, BsonDocument sort){
        Document doc = null;
        if (filters == null && sort != null){
            doc = getDatabase().getCollection(collName).find().sort(sort).first();
        }else if(filters != null && sort == null){
            doc = getDatabase().getCollection(collName).find(filters).first();
        }else if(filters != null && sort != null){
            doc = getDatabase().getCollection(collName).find(filters).sort(sort).first();
        }else if(filters == null && sort == null){
            doc = getDatabase().getCollection(collName).find().first();
        }
        return MongoUtil.parseToObject(doc, clazz);
    }

    public <T> List<T> findByPage(Class<T> clazz,  String collName, Bson filters, BsonDocument sort, int skip, int limit){
        FindIterable<Document> fid = null;
        if (filters == null && sort != null){
            fid = getDatabase().getCollection(collName).find().sort(sort).skip(skip).limit(limit);
        }else if(filters != null && sort == null){
            fid = getDatabase().getCollection(collName).find(filters).skip(skip).limit(limit);
        }else if(filters != null && sort != null){
            fid = getDatabase().getCollection(collName).find(filters).sort(sort).skip(skip).limit(limit);
        }else if(filters == null && sort == null){
            fid = getDatabase().getCollection(collName).find().skip(skip).limit(limit);
        }
        List<Document> list = cursor2list(fid);
        return MongoUtil.toBean(list, clazz);
    }

    public Map findOne(String dbName, String collName, Map queryMap) {
        MongoDatabase db = getDatabase(dbName);
        MongoCollection coll = db.getCollection(collName, Map.class);
        return (Map)coll.find( map2Bson(queryMap), Map.class).first();
    }

    /**
     *
     * @param collName
     * @param filters
     * @param sort
     * @return
     */
    public List<Document> find(String collName, Bson filters, BsonDocument sort){
        FindIterable<Document> fid = null;
        if (filters == null && sort != null){
            fid = getDatabase().getCollection(collName).find().sort(sort);
        }else if(filters != null && sort == null){
            fid = getDatabase().getCollection(collName).find(filters);
        }else if(filters != null && sort != null){
            fid = getDatabase().getCollection(collName).find(filters).sort(sort);
        }else if(filters == null && sort == null){
            fid = getDatabase().getCollection(collName).find();
        }
        return cursor2list(fid);
    }

    /**
     *
     * @param clazz
     * @param collName
     * @param filters
     * @param sort
     * @param <T>
     * @return
     */
    public <T> List<T> find(Class<T> clazz, String collName, Bson filters, BsonDocument sort){
        FindIterable<Document> fid = null;
        if (filters == null && sort != null){
            fid = getDatabase().getCollection(collName).find().sort(sort);
        }else if(filters != null && sort == null){
            fid = getDatabase().getCollection(collName).find(filters);
        }else if(filters != null && sort != null){
            fid = getDatabase().getCollection(collName).find(filters).sort(sort);
        }else if(filters == null && sort == null){
            fid = getDatabase().getCollection(collName).find();
        }
        return MongoUtil.toBean(cursor2list(fid), clazz);
    }

    public List<Document> find(QueryInfo queryInfo) {
        MongoDatabase db = getDatabase(dbName);
        MongoCollection coll = db.getCollection(queryInfo.collName);
        FindIterable cursor = null;
        if (queryInfo.query != null) {
            cursor = coll.find(queryInfo.query);
        } else {
            cursor = coll.find();
        }
        if (queryInfo.order != null) {
            cursor = cursor.sort(queryInfo.order);
        }
        if (queryInfo.limit != null) {
            cursor.limit(queryInfo.limit.intValue());
        }
        if (queryInfo.skip != null) {
            cursor.skip(queryInfo.skip.intValue());
        }
        return cursor2list(cursor);
    }

    public <T> List<T> find(Class<T> clazz, QueryInfo queryInfo) {
        MongoDatabase db = getDatabase(dbName);
        MongoCollection coll = db.getCollection(queryInfo.collName);
        FindIterable cursor = null;
        if (queryInfo.query != null) {
            cursor = coll.find(queryInfo.query);
        } else {
            cursor = coll.find();
        }
        if (queryInfo.order != null) {
            cursor = cursor.sort(queryInfo.order);
        }
        if (queryInfo.limit != null) {
            cursor.limit(queryInfo.limit.intValue());
        }
        if (queryInfo.skip != null) {
            cursor.skip(queryInfo.skip.intValue());
        }
        List<Document> list = cursor2list(cursor);
        return MongoUtil.toBean(list, clazz);
    }
    
    public List find(String dbname, String collName, Map queryMap, int start, int limit) {
        MongoDatabase db = getDatabase(dbname);
        MongoCollection coll = db.getCollection(collName);
        return find(coll, map2Bson(queryMap), start, limit);
    }

    public List find(String dbname, String collName, Map queryMap, Map orderMap, int start, int limit) {
        MongoDatabase db = getDatabase(dbname);
        MongoCollection coll = db.getCollection(collName);
        return find(coll, map2Bson(queryMap), map2Bson(orderMap), start, limit);
    }
    
    public List find(String dbname, String collName, Map queryMap) {
        MongoDatabase db = getDatabase(dbname);
        MongoCollection coll = db.getCollection(collName);
        return find(coll, map2Bson(queryMap));
    }

    public List<Document> findAll(String dbname, String collName) {
        MongoDatabase db = getDatabase(dbname);
        MongoCollection coll = db.getCollection(collName);
        return find(coll, null);
    }

    public long count(String dbname, String collName, Map queryMap) {
        MongoDatabase db = getDatabase(dbname);
        MongoCollection coll = db.getCollection(collName);
        long count;
        if (queryMap == null) {
            count = coll.countDocuments();
        } else {
            count = coll.countDocuments(map2Bson(queryMap));
        }
        return count;
    }
    
    public long count(String dbname, String collName, String field, Object value) {
        MongoDatabase db = getDatabase(dbname);
        MongoCollection coll = db.getCollection(collName);
        return coll.countDocuments(Filters.eq(field, value));
    }

    public long count(String dbname, String collName) {
        return count(dbname, collName, null);
    }


    public List<Document> find(MongoCollection coll, Bson query) {
        FindIterable cursor = null;
        if (query == null) {
            cursor = coll.find();
        } else {
            cursor = coll.find(query);
        }
        return cursor2list(cursor);
    }

    public <T> List<T> find(Class<T> clazz,String collName, Bson query) {
        FindIterable cursor = null;
        if (query == null) {
            cursor = getDatabase().getCollection(collName).find();
        } else {
            cursor = getDatabase().getCollection(collName).find(query);
        }
        return MongoUtil.toBean(cursor2list(cursor), clazz);
    }

    public List<Document> find(MongoCollection coll, Bson query, int start, int limit) {
        FindIterable cursor = null;
        if (query == null) {
            cursor = coll.find();
        } else if (start == 0) {
            cursor = coll.find(query).limit(limit);
        } else {
            cursor = coll.find(query).skip(start).limit(limit);
        }
        return cursor2list(cursor);
    }

    public List<Document> find(MongoCollection coll, Bson query, Bson order, int start, int limit) {
        FindIterable cursor = null;
        if (query == null) {
            cursor = coll.find();
        } else {
            cursor = coll.find(query);
        }
        if (order != null) cursor = cursor.sort(order);

        if (start != 0 && limit != 0) {
            cursor.skip(start).limit(limit);
        }
        if (start == 0 && limit != 0) {
            cursor.limit(limit);
        }

        return cursor2list(cursor);
    }

    public <T> List<T> find(Class<T> clazz, String collName, Bson query, Bson order, int start, int limit) {
        FindIterable cursor = null;
        MongoDatabase database = getDatabase();
        if (query == null) {
            cursor = database.getCollection(collName).find();
        } else {
            cursor = database.getCollection(collName).find(query);
        }
        if (order != null) cursor = cursor.sort(order);

        if (start != 0 && limit != 0) {
            cursor.skip(start).limit(limit);
        }
        if (start == 0 && limit != 0) {
            cursor.limit(limit);
        }
        return MongoUtil.toBean(cursor2list(cursor), clazz);
    }

    /*
    public Map group(String dbName, String collName, Map keys, Map cond, Map initial, String reduce) {
        DB db = mongoClient.getDB(dbName);
        DBCollection coll = db.getCollection(collName);

        if (initial == null || initial.isEmpty()) {
            initial = new HashMap();
            initial.put("count", 0);
        }
        if (StringUtils.isBlank(reduce)) {
            reduce = "function(obj,out) {}";
        }
        return group(coll, new BasicDBObject(keys), new BasicDBObject(cond), new BasicDBObject(initial), reduce);
    }

    public Map group(DBCollection coll, BasicDBObject keys, BasicDBObject cond, BasicDBObject initial, String reduce) {
        DBObject obj = coll.group(keys, cond, initial, reduce);
        return obj.toMap();
    }
    */

    private List cursor2list(FindIterable cursor) {
        final List list = new ArrayList();
        cursor.forEach(new Consumer() {
            @Override
            public void accept(Object t) {
                list.add(t);
            }
        });
        return list;
    }

    public static Pattern dateP = Pattern.compile("^[12][09][0-9][0-9]\\-[0-9][0-9]\\-[0-9][0-9]$");
    public static Pattern timeP = Pattern.compile("^[12][09][0-9][0-9]\\-[0-9][0-9]\\-[0-9][0-9]\\s[0-9][0-9]\\:[0-9][0-9]\\:[0-9][0-9]$");


    /**
     * 上传文件到mongodb
     *
     * @param dbName     数据库名称
     * @param file       要上传的文件。
     * @param fsFileName 在GridFS中的文件名，如果是null，就是file的名称（不带路径）。
     * @throws FileNotFoundException
     * @throws IOException
     */
    public ObjectId upload2GridFS(String dbName, File file, String fsFileName) throws FileNotFoundException, IOException {
        if (fsFileName == null) {
            fsFileName = file.getName();
        }
        InputStream input = new FileInputStream(file);
        ObjectId id = upload2GridFS(dbName, input, fsFileName);
        input.close();
        return id;
    }

    /**
     * 上传文件到mongodb
     *
     * @param dbname
     * @param input      文件数据流
     * @param fsFileName 在GridFS中的文件名
     * @return 
     * @throws FileNotFoundException
     * @throws IOException
     */
    public ObjectId upload2GridFS(String dbname, InputStream input, String fsFileName) throws FileNotFoundException, IOException {
        MongoDatabase db = getDatabase(dbname);
        GridFSBucket bucket = GridFSBuckets.create(db);
        ObjectId fileId = bucket.uploadFromStream(fsFileName, input);
        return fileId;
    }

    public ObjectId upload2GridFS(String dbName, byte[] bytes, String fsFileName) throws FileNotFoundException, IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        return upload2GridFS(dbName, in, fsFileName);
    }

    public ObjectId upload2GridFS(String dbName, String content, String fsFileName, String encoding) throws FileNotFoundException, IOException {
        return upload2GridFS(dbName, content.getBytes(encoding), fsFileName);
    }

    /**
     * 从GridFS中下载文件
     *
     * @param fsFileName 在GridFS中的文件名
     * @param fileName   保存下载的文件名，如果为空，就以fsFileName作为文件名。如果是目录，就以fsFileName作为文件名放到此目录中
     * @return 下载下来的本地文件名
     * @throws FileNotFoundException
     * @throws IOException
     */
    public File downloadFsFile(String dbname, String fsFileName, String fileName) throws FileNotFoundException, IOException {
        File outFile = new File(fileName);
        FileOutputStream out = new FileOutputStream(outFile);
        GridFSBucket bucket = GridFSBuckets.create(getDatabase(dbname));
        bucket.downloadToStream(fsFileName, out);
        return outFile;
    }

    /**
     * 从GridFS中删除文件
     *
     * @param fileName
     * @param queryMap GridFS文件的检索条件
     */
    public void removeFsFile(String dbname, String fileName, Map queryMap) {
        GridFSBucket bucket = GridFSBuckets.create(getDatabase(dbname));
        MongoCursor<GridFSFile> cursor = bucket.find(Filters.eq("filename", fileName)).iterator();
        while(cursor.hasNext()) {
            GridFSFile fileInfo = cursor.next();
            bucket.delete(fileInfo.getObjectId());
        }
    }



    private static void print(Document rec) {
        ObjectId id = rec.getObjectId("_id");
        System.out.println(id.toHexString() + " name:" + rec.get("name") + " age:" + rec.get("age"));
    }
    
    private static void print(List<Document> list) {
        for (Document rec : list) {
            print(rec);
        }
    }

    private Bson map2Bson(Map findMap) {
        Bson result = null;
        for (Object key : findMap.keySet()) {
            if (result == null) {
                result = Filters.eq(key.toString(), findMap.get(key));
            } else {
                result = Filters.and(result, Filters.eq(key.toString(), findMap.get(key)));
            }
        }
        return result;
    }

    private Document map2Document(Map recordMap) {
        Document doc = new Document();
        for (Object key : recordMap.keySet()) {
            doc.append((String)key, recordMap.get(key));
        }
        return doc;
    }


}
