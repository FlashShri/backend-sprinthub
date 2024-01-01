package com.sprinthub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sprinthub.dto.FeedbackStatus;
import com.sprinthub.entity.Feedback;
import com.sprinthub.service.FeedbackService;


@RestController
@CrossOrigin
public class FeedbackController {

	@Autowired
    private FeedbackService feedbackService ;
	
	@PostMapping("/feedback")
    public ResponseEntity<FeedbackStatus> addFeedback(@RequestBody Feedback feedback) {
        try {
            feedbackService.addFeedback(feedback);

            FeedbackStatus status = new FeedbackStatus();
            status.setStatus("Feedback added successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(status);
        } catch (Exception e) {
            // Handle other exceptions if needed
        	FeedbackStatus status = new FeedbackStatus();
            status.setStatus("Error adding Feedback");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(status);
        }
    }
	
}
