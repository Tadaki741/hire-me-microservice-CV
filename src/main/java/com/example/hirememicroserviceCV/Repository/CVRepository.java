package com.example.hirememicroserviceCV.Repository;

import com.example.hirememicroserviceCV.Model.CV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CVRepository extends JpaRepository<CV, String> {
//    @Modifying
//    @Query(value = "INSERT INTO cv (cv_body) VALUES (:cvBody::jsonb)", nativeQuery = true)
//    void insertJson(@Param("cvBody") String cvBody);


    //Find with email
    List<CV> findAllByEmail(String email);

    @Transactional
    @Modifying
    @Query("update CV c set c.name = ?1, c.cvBody = ?2 where c.id = ?3")
    void updateNameAndCvBodyById(@Nullable String name, @Nullable String cvBody, String id);


}
