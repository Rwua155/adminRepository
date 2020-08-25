package com.bjpowernode.redis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;

/**
 * @date 2020/8/25 17:09
 */
public class JedisDemo {

    private static String host = "192.168.254.130";//虚拟机的IP地址
    private static int port = 6379;//默认的redis端口号

    private static JedisPool jedisPool;//连接池信息

    public static void main(String[] args) {
//        //创建jedis对象
//        Jedis jedis = new Jedis(host,port);
//
//        jedis.set("username","zhangsan");
//
//        String usernmae = jedis.get("username");
//        System.out.println(usernmae);

        //创建容器对象
        ApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
        jedisPool = app.getBean("jedisPool", JedisPool.class);
        Jedis jedis = jedisPool.getResource();

        List<String> name = jedis.lrange("name", 0, -1);
        System.out.println(name);


//        Jedis jedis = JedisDemo.getJedis(host, port);
//        //jedis.flushDB();
//        jedis.lpush("name","zhangsan","lisi","caixukun");
//
//        List<String> name = jedis.lrange("name", 0, -1);
//        System.out.println(name);
//
//        JedisDemo.close();
    }

    public static Jedis getJedis(String host,Integer port){
        //封装一个方法，返回jedis对象
        //配置redis的相关信息
        //创建JedisPoolConfig对象，该对象配置连接池的参数
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(10);
        poolConfig.setMaxIdle(10);

        //使用安全设置密码
        //方式1：GenericObjectPoolConfig poolConfig, String host, int port, int timeout, String password
        jedisPool = new JedisPool(poolConfig,host,port,0);

        return jedisPool.getResource();
    }


    public static Jedis getJedis(String host,Integer port,String password){
        //封装一个方法，返回jedis对象
        //配置redis的相关信息
        //创建JedisPoolConfig对象，该对象配置连接池的参数
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(10);
        poolConfig.setMaxIdle(10);

        //使用安全设置密码
        //方式1：GenericObjectPoolConfig poolConfig, String host, int port, int timeout, String password
        JedisPool jedisPool = new JedisPool(poolConfig,host,port,0,password);

        return jedisPool.getResource();
    }

    public static Jedis getJedis(String host,Integer port,String password,Integer database){

        //配置redis的相关信息
        //创建JedisPoolConfig对象，该对象配置连接池的参数
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(10);
        poolConfig.setMaxIdle(10);

        //可以选择数据库，默认操作的是第一个数据库，索引为0
        //方式2：GenericObjectPoolConfig poolConfig, String host, int port, int timeout, String password, int database
        JedisPool jedisPool = new JedisPool(poolConfig, host, port, 0, "123456", database);

        return jedisPool.getResource();
    }

    public static void close(){
        //将jedis连接放入到连接池中
        jedisPool.close();
    }
    }

















