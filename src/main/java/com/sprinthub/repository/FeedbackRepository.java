package com.sprinthub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sprinthub.entity.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer>{

}
