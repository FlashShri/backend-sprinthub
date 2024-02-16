package com.sprinthub.dto;

import java.time.LocalDate;

import com.sprinthub.entity.Task.TaskStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
	private int taskId;
    private String title;
    private String description;
    private String domain;
    private TaskStatus status;
    private LocalDate startTaskDate;
    private LocalDate deadlineTaskDate;
    private int projectId;  // assuming the ID is enough for project
    private int employeeId; // assuming the ID is enough for employee
    private String employeeName;
    private String projectName;
}
