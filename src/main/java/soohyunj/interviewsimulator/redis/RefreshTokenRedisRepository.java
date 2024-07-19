package soohyunj.interviewsimulator.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import soohyunj.interviewsimulator.domain.refreshtoken.RefreshToken;

@Component
public class RefreshTokenRedisRepository extends BaseRedisRepository<RefreshToken>{

    public RefreshTokenRedisRepository(RedisTemplate redisTemplate) {
        this.prefix = REDIS_HEADER.REFRESH_TOKEN + ":";
        this.ttl = 60L * 60L * 24L * 7L; // 7 days
        this.redisTemplate = redisTemplate;
    }


}
