package com.sprinthub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sprinthub.entity.Feedback;
import com.sprinthub.repository.FeedbackRepository;

@Service
@Transactional
public class FeedbackService {

	@Autowired
	private FeedbackRepository feedbackRepository;
	
	public void addFeedback(Feedback feedback) {
		feedbackRepository.save(feedback);
	}
	
}
