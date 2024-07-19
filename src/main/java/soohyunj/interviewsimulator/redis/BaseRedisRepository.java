package soohyunj.interviewsimulator.redis;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public abstract class BaseRedisRepository<T> {
    protected RedisTemplate<String, T> redisTemplate;
    protected String prefix;
    protected Long ttl;

    public void save(String id, T data) {
        redisTemplate.opsForValue().set(generateKeyFromId(id), data, ttl, TimeUnit.SECONDS);
    }

    public Optional<T> findById(String id) {
        try {
            T value = redisTemplate.opsForValue().get(generateKeyFromId(id));
            return Optional.of(value);
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }

    public Boolean delete(String id) {
        return redisTemplate.delete(generateKeyFromId(id));
    }

    private String generateKeyFromId(String id) {
        return prefix + id;
    }
}
