package com.sprinthub.dto;

public class TaskAssignmentDTO {
	   private int taskId;
	    private String title;
	    private int employeeId;
		public int getTaskId() {
			return taskId;
		}
		public void setTaskId(int taskId) {
			this.taskId = taskId;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public int getEmployeeId() {
			return employeeId;
		}
		public void setEmployeeId(int employeeId) {
			this.employeeId = employeeId;
		}
	    
}
