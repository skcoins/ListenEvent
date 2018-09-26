package com.softisland.bean.utils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;

/**
 * 
 * @author wb
 *
 */
@Slf4j
public class JRedisUtils {

    RedisTemplate<String,Object> redisTemplate;
    /**
     * 现在默认的template,key为字符串,value位字符串
     */
    StringRedisTemplate stringRedisTemplate;
    
    /**
     * 构建RedisTemplate和StringRedisTemplate
     * @param redisProperties
     */
    public static JRedisUtils getInstance(RedisProperties redisProperties){
//    	RedisSentinelConfiguration sentinelConfiguration = new RedisSentinelConfiguration();
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(redisProperties.getJedis().getPool().getMaxIdle());
        poolConfig.setMaxTotal(redisProperties.getJedis().getPool().getMaxActive());
        poolConfig.setMinIdle(redisProperties.getJedis().getPool().getMinIdle());
        poolConfig.setMaxWaitMillis(redisProperties.getJedis().getPool().getMaxWait().getSeconds());
        JedisShardInfo info = new JedisShardInfo(redisProperties.getHost(),redisProperties.getPort());
        if(StringUtils.isNoneBlank(redisProperties.getPassword())){
        	info.setPassword(redisProperties.getPassword());
        }
        info.setConnectionTimeout(20000);
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setShardInfo(info);
        JRedisUtils redisUtils = new JRedisUtils(factory);
        return redisUtils;
    }
    /**
     * 构建RedisTemplate和StringRedisTemplate
     * @param factory
     */
    public JRedisUtils(RedisConnectionFactory factory){
        redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.afterPropertiesSet();
        stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(factory);
        stringRedisTemplate.afterPropertiesSet();
    }
    
    /**
     * 
     * @return
     */
    public String redisDbInfo(){
    	JedisConnectionFactory factory = (JedisConnectionFactory)stringRedisTemplate.getConnectionFactory();
    	JedisShardInfo js = factory.getShardInfo();
    	String ret = "redis db info:===>>"+js.getHost()+":"+js.getPort()+" passwd:"+js.getPassword();
    	log.info(ret);
    	return ret;
    }
    
    
    /**
     * 初始化
     * @param stringRedisTemplate
     * @param redisTemplate
     */
//    public  JRedisUtils(StringRedisTemplate stringRedisTemplate,RedisTemplate<String,Object> redisTemplate){
//    	this.stringRedisTemplate = stringRedisTemplate;
//    	this.redisTemplate = redisTemplate;
//    }
    
    /**
     * 根据key和MAP中的key获取MAP中的值
     * @param key
     * @param hashKey
     * @return
     * @throws Exception
     */
    public Object getHashValueByKey(String key,String hashKey)throws Exception{
        return stringRedisTemplate.opsForHash().get(key,hashKey);
    }

    /**
     * 通过KEY获取HashMap
     * @param key
     * @return
     * @throws Exception
     */
    public Map<Object,Object> getMapEntries(String key)throws Exception{
        return stringRedisTemplate.opsForHash().entries(key);
    }

    /**
     * 将值存入MAP中
     * @param key
     * @param hashKey
     * @param hasValue
     * @throws Exception
     */
    public void putValueToMap(String key,String hashKey,String hasValue)throws Exception{
        stringRedisTemplate.opsForHash().put(key,hashKey,hasValue);
    }
    
    public boolean putValueToMapReturn(String key,String hashKey,String hasValue)throws Exception{
       return stringRedisTemplate.opsForHash().putIfAbsent(key, hashKey, hasValue);
    }

    /**
     * 重新设置某个KEY的值
     * @param key
     * @param value
     * @throws Exception
     */
    public void setValue(String key,String value)throws Exception{
        stringRedisTemplate.opsForValue().set(key,value);
    }


    /**
     * 添加值
     * @param key
     * @param value
     * @throws Exception
     */
    public void addValue(String key,String value)throws Exception{
        stringRedisTemplate.opsForValue().append(key,value);
    }

    /**
     * 添加值到LIST中
     * @param key
     * @param value
     * @throws Exception
     */
    public void addValueToList(String key,String value)throws Exception{
        stringRedisTemplate.boundListOps(key).leftPush(value);
    }

    /**
     * 根据KEY从list中获取所有值
     * @param key
     * @return
     * @throws Exception
     */
    public List<String> getValuesFromList(String key)throws Exception{
        return stringRedisTemplate.boundListOps(key).range(0,-1);
    }
    
    /**
     * 删除LIST中values的数据
     * @param key
     * @param values
     */
    public void removeValuesFromList(String key,String values){
    	stringRedisTemplate.opsForList().remove(key, 0, values);
    }
    
    /**
     * 把值添加到队列的尾部
     * @param key KEY
     * @param value 值
     * @return
     * @throws Exception
     */
    public void appendValueToList(String key, String value) throws Exception{
        stringRedisTemplate.boundListOps(key).rightPush(value);
    }
    
    /**
     * 移除队列头部的值
     * @param key KEY
     * @return
     * @throws Exception
     */
    public String lpopValueFromList(String key) throws Exception{
        return stringRedisTemplate.boundListOps(key).leftPop();
    }
    
    /**
     * 移除队列头部的值
     * @param key KEY
     * @return
     * @throws Exception
     */
    public String getValueFromList(String key, long index) throws Exception{
        return stringRedisTemplate.boundListOps(key).index(index);
    }

    /**
     * 通过key从LIST中获取指定范围的值
     * @param key
     * @param start
     * @param end
     * @return
     * @throws Exception
     */
    public List<String> getValuesFromList(String key,int start,int end)throws Exception{
        return stringRedisTemplate.boundListOps(key).range(start,end);
    }

    /**
     * 获取LIST的大小
     * @param key
     * @return
     * @throws Exception
     */
    public Long getListSize(String key)throws Exception{
        return stringRedisTemplate.boundListOps(key).size();
    }

    /**
     * 从redis中获取值
     * @param key
     * @return
     * @throws Exception
     */
    public String getValue(String key)throws Exception{
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 添加一组值
     * @param values
     * @throws Exception
     */
    public void addValues(Map<String,String> values)throws Exception{
        stringRedisTemplate.opsForValue().multiSet(values);
    }

    /**
     * 获取某个KEY的值的数量
     * @param key
     * @return
     * @throws Exception
     */
    public Long getSize(String key)throws Exception{
        return stringRedisTemplate.opsForValue().size(key);
    }

    /**
     * 删除某个KEY的所有值
     * @param key
     * @throws Exception
     */
    public void deleteValue(String key)throws Exception{
        stringRedisTemplate.delete(key);
    }

    /**
     *
     * @param keys
     * @throws Exception
     */
    public void deleteValues(List<String> keys)throws Exception{
        stringRedisTemplate.delete(keys);
    }

    /**
     * 根据KEY移除某个KEY下面的MAP中某几个HashKey的值
     * @param key
     * @param hashKeys
     * @throws Exception
     */
    public void removeMapValues(String key,String... hashKeys)throws Exception{
        stringRedisTemplate.opsForHash().delete(key,hashKeys);
    }

    /**
     * 获取hash的大小
     * @param key
     * @return
     * @throws Exception
     */
    public Long getHashSize(String key)throws Exception{
        return stringRedisTemplate.opsForHash().size(key);
    }

    /**
     * 对现有的KEY进行重命名
     * @param oldKey
     * @param newKey
     * @throws Exception
     */
    public void renameKey(String oldKey,String newKey)throws Exception{
        stringRedisTemplate.rename(oldKey, newKey);

    }

    /**
     * 检查MAP中是否包含已存在的值
     * @param key
     * @param hashKey
     * @return
     * @throws Exception
     */
    public boolean hasKeyInMap(String key,String hashKey)throws Exception{
        return stringRedisTemplate.opsForHash().hasKey(key,hashKey);
    }

    /**
     * 根据KEY值自增
     * @param key
     * @return
     * @throws Exception
     */
    public long incr(String key) {
    	try{
    		return stringRedisTemplate.opsForValue().increment(key, 1);
    	}catch(Exception e){
    		return 0;
    	}
    }
    
    public long decrby(String key,Integer num) {
    	try{
    		return stringRedisTemplate.execute(new RedisCallback<Long>() {
				@Override
				public Long doInRedis(RedisConnection connection) throws DataAccessException {
					final byte[] rawKey = stringRedisTemplate.getStringSerializer().serialize(key);
					return connection.decrBy(rawKey,num);
				}
			});
    	}catch(Exception e){
    		return -1;
    	}
    }
    
    public long hDecrby(String key,String field,Integer num) {
    	try{
    		return stringRedisTemplate.execute(new RedisCallback<Long>() {
				@Override
				public Long doInRedis(RedisConnection connection) throws DataAccessException {
					final byte[] rawKey = stringRedisTemplate.getStringSerializer().serialize(key);
					final byte[] rawField = stringRedisTemplate.getStringSerializer().serialize(field);
					return connection.hIncrBy(rawKey, rawField, num);
				}
			});
    	}catch(Exception e){
    		return -1;
    	}
    }
    
    public boolean hashKey(String key) {
    	try{
    		return stringRedisTemplate.hasKey(key);
    	}catch(Exception e){
    		return false;
    	}
    }
    
    /**
     *，当且仅当 key 不存在，不存在,set
     * @param key
     * @param value
     * @param timeout
     * @return 成功 = true;已经存在=false
     * @throws Exception
     */
    public boolean setIfAbsent(String key,String value,long timeout){
       return setIfAbsent(key,value,timeout,TimeUnit.MILLISECONDS);
    }
    
    /**
     * 原子check&set操作
     * @param redisKey
     * @param value
     * @param timeout
     * @param unit
     * @return
     */
    public boolean setIfAbsent(String redisKey,String value,long timeout, TimeUnit unit) {
        boolean result = false;
            try {
                BoundValueOperations boundValueOperations = stringRedisTemplate.boundValueOps(redisKey);
                result = boundValueOperations.setIfAbsent(value);
                if(result){
                    boundValueOperations.expire(timeout, unit);
                }
            } catch (Exception e) {
            	e.printStackTrace();
            }
        return result;
    }
    /**
     * 设置超时时间
     * @param key
     * @param timeOut
     * @param unit
     * @return
     */
    public boolean setExpire(String key,Long timeOut,TimeUnit unit){
    	return stringRedisTemplate.expire(key, timeOut,unit);
    }
    
 
}
