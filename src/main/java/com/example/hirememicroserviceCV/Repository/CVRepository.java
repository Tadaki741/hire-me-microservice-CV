package com.example.hirememicroserviceCV.Repository;

import com.example.hirememicroserviceCV.Model.CV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CVRepository extends JpaRepository<CV, String> {
    @Modifying
    @Query(value = "INSERT INTO cv (cv_body) VALUES (:cvBody::jsonb)", nativeQuery = true)
    void insertJson(@Param("cvBody") String cvBody);
}
