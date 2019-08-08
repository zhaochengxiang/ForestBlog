package com.zcx.blog.service;

import com.zcx.blog.entity.Session;
import com.zcx.blog.util.GsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.apache.commons.codec.binary.Hex;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

@Component
public class SessionService {
    private String key = "s:";
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private ThreadLocal<Session> sessionIdLocal = new ThreadLocal<>();
    private SecureRandom random;

    {
        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            random = new SecureRandom();
        }
        random.nextInt();
    }

    public Session get() {
        return sessionIdLocal.get();
    }

    public int getUid() {
        return get().getUid();
    }

    public void delete() {
        Session session = sessionIdLocal.get();
        this.delete(session.getId());
    }


    public Session bind(String id) {
        Session session = get(id);
        if (session == null) {
            session = new Session();
            session.setId(id);
        }
        sessionIdLocal.set(session);
        return session;
    }


    public String getId() {
        byte bytes[] = new byte[16];
        random.nextBytes(bytes);
        String id = Hex.encodeHexString(bytes);
        if (exists(key + id)) {
            return getId();
        }
        return id;
    }

    public Session get(String id) {
        String value = stringRedisTemplate.opsForValue().get(key + id);
        if (value != null) {
            Session session = GsonUtils.fromJson(value, Session.class);
            if (session.getExpire() == 1) {
                expire(id);
            }
            return session;
        }
        return null;
    }

    public String save(Session session) {
        String id = session.getId();
        stringRedisTemplate.opsForValue().set(key + id, GsonUtils.toJson(session));
        if (session.getExpire() == 1) {
            expire(id);
        }
        return id;
    }

    public void update(Session session) {
        String id = session.getId();
        stringRedisTemplate.opsForValue().set(key + id, GsonUtils.toJson(session));
        if (session.getExpire() == 1) {
            expire(id);
        }
    }

    public void delete(String id) {
        stringRedisTemplate.delete(key + id);
    }

    private void expire(String id) {
        stringRedisTemplate.expire(key + id, 30, TimeUnit.MINUTES);
    }

    public boolean exists(final String key) {
        return stringRedisTemplate.execute((RedisCallback<Boolean>) connection -> connection.exists(key.getBytes()));
    }
}
