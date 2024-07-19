package soohyunj.interviewsimulator.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class SimulationLogRedisRepository extends BaseRedisRepository<String>{

    public SimulationLogRedisRepository(RedisTemplate redisTemplate) {
        this.prefix = REDIS_HEADER.INTERVIEW_LOG + ":";
        this.ttl = 60L * 60L * 24L * 1L; // 1 day
        this.redisTemplate = redisTemplate;
    }
}
