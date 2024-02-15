package com.sprinthub.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TaskCreateDTO {
	
	private String title;
    private String description;
    private String domain;
   
    //private LocalDate createDate ;
    private LocalDate deadlineTaskDate;
}
