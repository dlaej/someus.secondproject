package project.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import project.dto.DiaryDto;
import project.dto.GoalDto;
import project.dto.MoodDto;
import project.dto.ShareRoomDto;
import project.dto.WeatherDto;
import project.mapper.DiaryMapper;

@Slf4j
@Service
public class DiaryServiceImpl implements DiaryService {

	@Autowired
	private DiaryMapper diaryMapper;
	
	// 1. 개인 일기 목록 조회
	@Override
	public List<DiaryDto> selectPrivateList(String memberId) throws Exception{
		return diaryMapper.selectPrivateList(memberId);
	}
	
	// 1-1. 날짜별 개인 일기 목록 조회
	@Override
	public List<DiaryDto> selectPrivateListByDt(String memberId, String createdDt) throws Exception {
		return diaryMapper.selectPrivateListByDt(memberId, createdDt);
	}
	
	// 2, 9. 개인/교환 일기 작성 화면 - 기분, 날씨 선택
	public List<WeatherDto> weatherList() throws Exception {
		return diaryMapper.weatherList();
	}
	 
	public List<MoodDto> moodList() throws Exception{
		return diaryMapper.moodList();
	}
	
	// 3. 개인 일기 작성
	@Override
	public int insertPrivate(DiaryDto diaryDto) throws Exception{
		return diaryMapper.insertPrivate(diaryDto);
	}
	
	// 4. 개인 일기 상세 조회
	@Override
	public DiaryDto selectPrivateDetail(int diaryId) throws Exception {
		return diaryMapper.selectPrivateDetail(diaryId);
	}
	
	// 5. 개인 일기 수정
	@Override
	public int updatePrivate(DiaryDto diaryDto) throws Exception {
		return diaryMapper.updatePrivate(diaryDto);
	}
	
	// 6. 개인 일기 삭제
	@Override
	public int deletePrivate(int diaryId) throws Exception {
		return diaryMapper.deletePrivate(diaryId);
	}
	
	// 7. 교환 일기 그룹 목록 조회
	@Override
	public List<ShareRoomDto> selectPublicList(String memberId) throws Exception {
		return diaryMapper.selectPublicList(memberId);
	}
	
	// 8. 교환 일기 목록 조회
	@Override
	public List<Map<String, Object>> selectPublicShareList(int shareRoomId) throws Exception {
		return diaryMapper.selectPublicShareList(shareRoomId);
	}

	// 8-1. 교환일기별 멤버 목록 조회
	@Override
	public List<ShareRoomDto> selectShareRoomMemberList(int shareRoomId) throws Exception {
		return diaryMapper.selectShareRoomMemberList(shareRoomId);
	}
	
	// 8-2. 날짜별 교환 일기 목록 조회
	@Override
	public List<Map<Object, Object>> selectPublicShareListByDt(int shareRoomId, String createdDt) throws Exception {
		return diaryMapper.selectPublicShareListByDt(shareRoomId, createdDt); 
	}
	
	// 10. 교환 일기 작성
	@Override
	public int insertPublic(DiaryDto diaryDto) throws Exception {
		return diaryMapper.insertPublic(diaryDto);
	}
	
	// 11. 교환 일기 상세 조회
	@Override
	public List<DiaryDto> selectPublicDetail(int shareRoomId, String createdDt) throws Exception {
		return diaryMapper.selectPublicDetail(shareRoomId, createdDt);
	}
	
	// 12. 교환 일기 그룹 추가
	@Override
	public int addGroup(ShareRoomDto shareRoomDto) throws Exception {
		return diaryMapper.addGroup(shareRoomDto);
	}

	// 12-1. 멤버 추가 전 아이디, 이름을 기준으로 번호 조회
	@Override
	public int selectShareRoomId(String memberId) throws Exception {
		return diaryMapper.selectShareRoomId(memberId);
	}
	 
	// 13. 교환 일기 멤버 추가
	@Override
	public int addGroupNext(List<Map<String, Object>> result) throws Exception {
		
		int count = 0;
		Iterator<Map<String, Object>> resultList = result.iterator();
		
		while(resultList.hasNext()) {
			Map<String, Object> temp = resultList.next();
			diaryMapper.addGroupNext(temp);
			count ++;
		}
		return count;
	}
	
	// 17. 목표 조회
	@Override
	public List<GoalDto> selectGoalList(String memberId, String goalDate) throws Exception {
		return diaryMapper.selectGoalList(memberId, goalDate);
	}

	// 18. 목표 입력
	@Override
	public int insertGoal(GoalDto goalDto) throws Exception {
		return diaryMapper.insertGoal(goalDto);
	}

	// 19. 목표 업데이트
	@Override
	public int updateGoal(GoalDto goalDto) throws Exception {
		return diaryMapper.updateGoal(goalDto);			
	}

	// 20. 개인 목표 삭제
	@Override
	public int deleteGoal(int goalId) throws Exception {
		return diaryMapper.deleteGoal(goalId);
	}

}