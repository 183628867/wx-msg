package com.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author tengfei
 * @version 1.0
 * @date 2018/7/13 下午4:15
 */
public class RedisUtil {
    /**
     * redis对象
     */
    private Jedis jedis;

    /**
     * 初始化
     *
     * @param jedisPool
     */
    public RedisUtil(JedisPool jedisPool) {
        super();
        this.jedis = jedisPool.getResource();
    }

    /**
     * 获取集合内byte[]值
     *
     * @param key
     * @param field
     * @return
     */
    public String hgetString(String key, String field) {
        String value = this.jedis.hget(key, field);
        return value;
    }

    /**
     * 获取集合内byte[]值
     *
     * @param key
     * @param field
     * @return
     */
    public byte[] hgetbytes(byte[] key, byte[] field) {
        byte[] bytes = this.jedis.hget(key, field);
        return bytes;
    }

    /**
     * 添加String到集合中
     *
     * @param key
     * @param field
     * @param value
     */
    public void hsetString(String key, String field, String value) {
        this.jedis.hset(key, field, value);
    }

    /**
     * 添加bytes到集合中
     *
     * @param key
     * @param field
     * @param value
     */
    public void hsetBytes(byte[] key, byte[] field, byte[] value) {
        this.jedis.hset(key, field, value);
    }

    /**
     * 添加key
     *
     * @param key
     * @param value
     */
    public void setString(String key, String value) {
        this.jedis.set(key, value);
    }


    /**
     * 添加key
     *
     * @param key
     * @param value
     */
    public void append(String key, String value) {
        this.jedis.append(key, value);
    }


    /**
     * 关闭jedis
     */
    public void closeJedis() {
        this.jedis.close();
    }

    /**
     * 获取value
     */
    public String getString(String key) {
        String value = this.jedis.get(key);
        return value;
    }

    /**
     * 获取反序列化对象
     *
     * @param key
     * @return
     */
    public Object getObject(String key) {
        byte[] b = this.jedis.get(key.getBytes());
        Object obj = unserizlize(b);
        return obj;
    }

    /**
     * 添加序列化对象
     *
     * @param key
     * @param value
     */
    public void setObject(String key, Object value) {
        this.jedis.set(key.getBytes(), serialize(value));
    }

    /**
     * 设置超时，单位秒
     *
     * @param key
     * @param timeOut
     */
    public void expire(String key, int timeOut) {
        jedis.expire(key, timeOut);
    }

    /**
     *判断key是否存在
     * @author cheng
     * @date 2022-8-25 19:37
     * @return boolean
     */
    public boolean exists(String key){
        return jedis.exists(key);
    }


    /**
     * 模糊匹配key值
     *
     * @param key
     * @param
     */
    public List keys(String key) {
        Set<String> keys = this.jedis.keys(key);
        Iterator<String> it = keys.iterator();
        List list = new ArrayList<>();
        while (it.hasNext()) {
            String str = it.next();
            list.add(str);
            System.out.println(str);
        }
        return list;
    }



    /**
     * 对象序列化
     *
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) {
        ObjectOutputStream objectOutputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            byte[] getByte = byteArrayOutputStream.toByteArray();
            return getByte;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对象反序列化
     *
     * @param binaryByte
     * @return
     */
    public static Object unserizlize(byte[] binaryByte) {
        ObjectInputStream objectInputStream = null;
        ByteArrayInputStream byteArrayInputStream = null;
        byteArrayInputStream = new ByteArrayInputStream(binaryByte);
        try {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Object obj = objectInputStream.readObject();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}