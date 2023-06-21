package project.dto;

import lombok.Data;

@Data
public class GoalDto {

	private int goalId;
	private String memberId;
	private String goalContents;
	private String goalDate;
	private String goalState;
}
