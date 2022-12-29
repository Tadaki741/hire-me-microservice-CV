package com.example.hirememicroserviceCV.Service;

import com.example.hirememicroserviceCV.Model.CV;
import com.example.hirememicroserviceCV.Repository.CVRepository;
import com.example.hirememicroserviceCV.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class CVService {
    private final UserRepository userRepository;
    private final String CV_CACHE = "CV";
    private final RedisTemplate<String, Object> redisTemplate;
    private final CVRepository cvRepository;

    @Autowired
    public CVService(UserRepository userRepository, RedisTemplate<String, Object> redisTemplate, CVRepository cvRepository){
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
        this.cvRepository = cvRepository;
    }
    private HashOperations<String, String, CV> hashOperations;
    // This annotation makes sure that the method needs to be executed after
    // dependency injection is done to perform any initialization.
    @PostConstruct
    private void initializeHashOperations() {
        hashOperations = redisTemplate.opsForHash();
    }

    // Save operation.
    public CV save(final CV cv) {
        CV newCV = this.cvRepository.save(cv);
        //Update data to redis
        this.saveUserToCache(newCV);

        //save to database
        return newCV;
    }

    public void saveUserToCache(final CV cv) {
        hashOperations.put(CV_CACHE, cv.getId(), cv);
    }

    public Optional<CV> findById(String id) {
        CV redisUser = hashOperations.get(CV_CACHE, id);
        if (redisUser != null) return Optional.of(redisUser);
        Optional<CV> cv = this.cvRepository.findById(id);
        if (cv == null) {
            return null;
        }
        return cv;
    }

    // Find all cv's operation.
    public List<CV> findAll() {
        List<CV> cv = hashOperations.values(CV_CACHE);

        if (!cv.isEmpty()){
            return cv;
        }

        return this.cvRepository.findAll();
    }

    // Delete cv by id operation.
    public void deleteById(String id) {
        hashOperations.delete(CV_CACHE, id);
        this.cvRepository.deleteById(id);
    }
}