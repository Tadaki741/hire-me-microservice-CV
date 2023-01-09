package com.example.hirememicroserviceCV.Service;

import com.example.hirememicroserviceCV.Model.CV;
import com.example.hirememicroserviceCV.Model.CVDTO;
import com.example.hirememicroserviceCV.Repository.CVRepository;
import com.example.hirememicroserviceCV.Utility.NullUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CVService {

    private static final Logger logger = Logger.getLogger(CVService.class.getName());
    private final String CV_CACHE = "CV";
    private final RedisTemplate<String, Object> redisTemplate;
    private final CVRepository cvRepository;

    @Autowired
    public CVService(RedisTemplate<String, Object> redisTemplate, CVRepository cvRepository) {
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
        hashOperations.put(CV_CACHE, newCV.getId(), newCV);

        //save to database
        return newCV;
    }


    public Optional<CV> findById(String id) {
        CV cachedCv = hashOperations.get(CV_CACHE, id);
        if (cachedCv != null) return Optional.of(cachedCv);

        Optional<CV> cvOptional = this.cvRepository.findById(id);
        if (cvOptional.isPresent()) {
            CV cv = cvOptional.get();
            hashOperations.put(CV_CACHE, cv.getId(), cv);
        }

        return cvOptional;
    }

    // Find all cv's operation.
    public List<CV> findAll() {
        return this.cvRepository.findAll();
    }

    // Delete cv by id operation.
    public void deleteById(String id) {
        hashOperations.delete(CV_CACHE, id);
        this.cvRepository.deleteById(id);
    }


    //Find by user provided email
    public List<CV> findAllCVWithEmail(String email) {
        return this.cvRepository.findAllByEmail(email);
    }

    public void updateCV(String id, CVDTO cvdto) throws Exception {

        logger.log(Level.WARNING, "STARTING TO UPDATE CV INSIDE SERVICE LAYER");

        // check if cv exists
        CV cv = cvRepository.findById(id).orElseThrow(() -> new Exception("CV is not found for this id: " + id));

        //Khoa
//        if (cvdto.getName() == null) {
//            this.cvRepository.updateNameAndCvBodyById(cv.getName(), cvdto.getCvBody(), id);
//        } else {
//            this.cvRepository.updateNameAndCvBodyById(cvdto.getName(), cvdto.getCvBody(), id);
//        }


        //Trung
        NullUtils.updateIfChanged(cv::setName, cvdto.getName(), cv::getName);
        NullUtils.updateIfChanged(cv::setEmail, cvdto.getEmail(), cv::getEmail);
        NullUtils.updateIfChanged(cv::setCvBody, cvdto.getCvBody(), cv::getCvBody);

        this.cvRepository.updateNameAndCvBodyById(cv.getName(), cv.getCvBody(), id);

        logger.log(Level.WARNING, "--> FINISHED UPDATING CV INSIDE SERVICE LAYER");
    }

    public CV getCV(String id) throws Exception {
        CV cv = hashOperations.get(CV_CACHE, id);
        if (cv == null) {
            return cvRepository.findById(id).orElse(null);
        }
        return cv;
    }

}
