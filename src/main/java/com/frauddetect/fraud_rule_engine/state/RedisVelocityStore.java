package com.frauddetect.fraud_rule_engine.state;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Component
public class RedisVelocityStore implements VelocityStore{
    private static final String SCRIPT = """
            local now = tonumber(ARGV[1])
            local window = tonumber(ARGV[2])
            redis.call('ZREMRANGEBYSCORE', KEYS[1], '-inf', now - window)
            redis.call('ZADD', KEYS[1], now, ARGV[3])
            redis.call('PEXPIRE', KEYS[1], window)
            return redis.call('ZCARD', KEYS[1])
            """;

    private static final RedisScript<Long> RECORD_AND_COUNT = RedisScript.of(SCRIPT, Long.class);

    private final StringRedisTemplate redis;

    public RedisVelocityStore(StringRedisTemplate redis) {
        this.redis = redis;
    }

    @Override
    public long recordAndCount(String cardToken, Instant timestamp, Duration window) {
        String key = "velocity:" + cardToken;
        String attemptId = UUID.randomUUID().toString();

        Long count = redis.execute(
                RECORD_AND_COUNT,
                List.of(key),
                String.valueOf(timestamp.toEpochMilli()),
                String.valueOf(window.toMillis()),
                attemptId);

        return count == null ? 0 : count;
    }
}
