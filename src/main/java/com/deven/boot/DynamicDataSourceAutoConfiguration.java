package com.deven.boot;


import com.deven.boot.datasource.MyHikariConfig;
import com.deven.boot.datasource.transaction.MultiTransactionalInterceptor;
import com.deven.boot.datasource.util.RedisUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties(MyHikariConfig.class)
@Import(MultiTransactionalInterceptor.class)
public class DynamicDataSourceAutoConfiguration {

    @Value("${redis.host.uri}")
    private String REDIS_HOST_URI;

	public static void main(String[] args) {
		SpringApplication.run(DynamicDataSourceAutoConfiguration.class, args);
	}

    @Bean
    public RedisUtil redisUtil() {
        RedisUtil redisUtil = new RedisUtil();
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(1024);
        jedisPoolConfig.setMaxIdle(200);
        jedisPoolConfig.setMaxWaitMillis(1000L);
        jedisPoolConfig.setTestOnBorrow(false);
        List<JedisShardInfo> shards = Lists.newArrayList();
        JedisShardInfo jedisShardInfo = new JedisShardInfo(this.REDIS_HOST_URI);
        shards.add(jedisShardInfo);
        redisUtil.setShardedJedisPool(new ShardedJedisPool(jedisPoolConfig, shards));
        return redisUtil;
    }
}
