package it.live.crm.service.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisHelper {

    private final RedisTemplate<String, Object> redisTemplate;

    public void save(String key, Object value) {
        redisTemplate.opsForValue().set(key, value, Duration.ofMinutes(1));
    }

    public Object find(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
