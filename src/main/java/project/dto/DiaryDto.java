package project.dto;

import lombok.Data;

@Data
public class DiaryDto {

	private int diaryId;
	private String memberId;
	private int shareRoomId;
	private String diaryContent;
	private String createdDt;
	private String deletedDt;
	private String deletedYn;
	private String diaryImg;
	private int moodId;
	private int weatherId;
}
