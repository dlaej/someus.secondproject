package project.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.io.IOException;
import lombok.extern.slf4j.Slf4j;
import project.dto.DiaryDto;
import project.dto.GoalDto;
import project.dto.MemberDto;
import project.dto.MoodDto;
import project.dto.ShareMemberDto;
import project.dto.ShareRoomDto;
import project.dto.WeatherDto;
import project.security.JwtTokenUtil;
import project.service.DiaryService;

@Slf4j
@RestController
public class DiaryApiController {
	@Autowired
	private DiaryService diaryService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	private DiaryDto diaryDto;
	private MemberDto memberDto;
	private ShareMemberDto shareMemberDto;
	private ShareRoomDto shareRoomDto;

	final String UPLOAD_PATH = "C:/java/eclipse-workspace/someus/src/main/resources/static/img/";

	// 1. 개인 일기 목록 조회
	@GetMapping("/api/someus/private/page/{memberId}")
	public ResponseEntity<Map<String, Object>> openPrivateList(@PathVariable("memberId") String memberId)
			throws Exception {

		Map<String, Object> result = new HashMap<>();
		List<DiaryDto> list1 = diaryService.selectPrivateList(memberId);

		result.put("diaryList", list1);

		if (result.size() == 1) {
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
		}
	}

	// 1-1. 날짜별 개인 일기 목록 조회
	@GetMapping("/api/someus/private/page/{memberId}/{createdDt}")
	public ResponseEntity<List<DiaryDto>> openPrivateListByDt(@PathVariable("memberId") String memberId,
			@PathVariable("createdDt") String createdDt) throws Exception {

		List<DiaryDto> list = diaryService.selectPrivateListByDt(memberId, createdDt);

		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	// 2. 개인 일기 작성 화면 요청
	@GetMapping("api/someus/private/write")
	public ResponseEntity<Map<String, Object>> writePrivate() throws Exception {

		Map<String, Object> result = new HashMap<>();
		List<WeatherDto> weatherList = diaryService.weatherList();
		List<MoodDto> moodList = diaryService.moodList();

		result.put("weatherList", weatherList);
		result.put("moodList", moodList);

		if (weatherList != null && moodList != null) {
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	// 3. 개인 일기 작성
	@PostMapping("/api/someus/private/write")
	public ResponseEntity<Map<String, Object>> insertPrivate(
			@RequestPart(value = "files", required = false) MultipartFile[] files,
			@RequestPart(value = "data", required = false) DiaryDto diaryDto, HttpServletRequest request)
			throws Exception {

		String jwtToken = null;
		String subject = null;
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
			jwtToken = authorizationHeader.substring(7);
			subject = jwtTokenUtil.getSubjectFromToken(jwtToken);
		}
		diaryDto.setMemberId(subject);
		log.debug(">>>>>>>>>>>>>>>>>>" + diaryDto.toString());

		String FileNames = "";
		int insertedCount = 0;

		try {
			for (MultipartFile mf : files) {
				String originFileName = mf.getOriginalFilename(); // 원본 파일 명
				long fileSize = mf.getSize(); // 파일 사이즈

				System.out.println("originFileName : " + originFileName);
				System.out.println("fileSize : " + fileSize);
				String safeFile = System.currentTimeMillis() + originFileName;
				diaryDto.setDiaryImg(safeFile);

				try {
					File f1 = new File(UPLOAD_PATH + safeFile);
					mf.transferTo(f1);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			insertedCount = diaryService.insertPrivate(diaryDto);
			if (insertedCount > 0) {
				Map<String, Object> result = new HashMap<>();
				result.put("message", "정상적으로 등록되었습니다.");
				result.put("count", insertedCount);
				result.put("diaryId", diaryDto.getDiaryId());
				return ResponseEntity.status(HttpStatus.OK).body(result);
			} else {
				Map<String, Object> result = new HashMap<>();
				result.put("message", "등록된 내용이 없습니다.");
				result.put("count", insertedCount);
				return ResponseEntity.status(HttpStatus.OK).body(result);
			}
		} catch (Exception e) {
			Map<String, Object> result = new HashMap<>();
			result.put("message", "등록 중 오류가 발생했습니다.");
			result.put("count", insertedCount);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}
	}

	// 다이어리 이미지 다운로드
	@GetMapping("/api/getImage/{diaryImg}")
	public void getImage(@PathVariable("diaryImg") String diaryImg, HttpServletResponse response) throws Exception {
		// reviewImage를 읽어서 전달
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		try {
			response.setHeader("Content-Disposition", "inline;");

			byte[] buf = new byte[1024];
			fis = new FileInputStream(UPLOAD_PATH + diaryImg);
			bis = new BufferedInputStream(fis);
			bos = new BufferedOutputStream(response.getOutputStream());
			int read;
			while ((read = bis.read(buf, 0, 1024)) != -1) {
				bos.write(buf, 0, read);
			}
		} finally {
			bos.close();
			bis.close();
			fis.close();
		}
	}

	// 4. 개인 일기 상세 조회
	@GetMapping("/api/someus/private/{diaryId}")
	public ResponseEntity<DiaryDto> openPrivateDetail(@PathVariable("diaryId") int diaryId,
			HttpServletResponse response) throws Exception {
		DiaryDto diaryDto = diaryService.selectPrivateDetail(diaryId);
		String diaryImg = diaryDto.getDiaryImg();

		if (diaryDto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(diaryDto);
		}
	}

	// 5. 개인 일기 수정
	@PutMapping("/api/someus/private/{diaryId}")
	public ResponseEntity<Integer> updatePrivate(@PathVariable("diaryId") int diaryId, @RequestBody DiaryDto diaryDto)
			throws Exception {
		diaryDto.setDiaryId(diaryId);
		int updatedCount = diaryService.updatePrivate(diaryDto);
		if (updatedCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updatedCount);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(updatedCount);
		}
	}

	// 6. 개인 일기 삭제
	@DeleteMapping("/api/someus/private/{diaryId}")
	public ResponseEntity<Integer> deletePrivate(@PathVariable("diaryId") int diaryId) throws Exception {

		int deletedCount = diaryService.deletePrivate(diaryId);

		if (deletedCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(deletedCount);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(deletedCount);
		}
	}

	// 7. 교환 일기 그룹 목록 조회
	@GetMapping("/api/someus/share/grouplist/{memberId}")
	public ResponseEntity<List<ShareRoomDto>> openGroupList(@PathVariable("memberId") String memberId)
			throws Exception {

		List<ShareRoomDto> list = diaryService.selectPublicList(memberId);

		if (list != null && list.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(list);
		}
	}

	// 8. 교환 일기 목록 조회
	@GetMapping("/api/someus/shareroom/{shareRoomId}")
	public ResponseEntity<List<Map<String, Object>>> openPublicDetail(@PathVariable("shareRoomId") int shareRoomId)
			throws Exception {

		List<Map<String, Object>> result = diaryService.selectPublicShareList(shareRoomId);

		if (result == null) {
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(result);
		}
	}

	// 8-1. 교환일기별 멤버 목록 조회
	@GetMapping("/api/someus/shareroom/member/{shareRoomId}")
	public ResponseEntity<List<ShareRoomDto>> selectShareRoomMemberList(@PathVariable("shareRoomId") int shareRoomId)
			throws Exception {

		List<ShareRoomDto> list = diaryService.selectShareRoomMemberList(shareRoomId);

		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	// 8-2. 날짜별 교환 일기 목록 조회
	@GetMapping("/api/someus/shareroom/{shareRoomId}/{createdDt}")
	public ResponseEntity<List<Map<Object, Object>>> selectPublicShareListByDt(
			@PathVariable("shareRoomId") int shareRoomId, @PathVariable("createdDt") String createdDt)
			throws Exception {

		List<Map<Object, Object>> result = diaryService.selectPublicShareListByDt(shareRoomId, createdDt);

		if (result != null) {
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(result);
		}
	}

	// 9. 교환 일기 작성 화면 요청
	@GetMapping("api/someus/share/{shareRoomId}/write")
	public ResponseEntity<Map<String, Object>> writePublic(@PathVariable("shareRoomId") String shareRoomId)
			throws Exception {

		Map<String, Object> result = new HashMap<>();
		List<WeatherDto> weatherList = diaryService.weatherList();
		List<MoodDto> moodList = diaryService.moodList();

		result.put("weatherList", weatherList);
		result.put("moodList", moodList);

		if (weatherList != null && moodList != null) {
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	// 10. 교환 일기 작성
	@PostMapping("/api/someus/share/{shareRoomId}/write")
	public ResponseEntity<Map<String, Object>> insertPublic(@PathVariable("shareRoomId") String shareRoomId,
			@RequestPart(value = "files", required = false) MultipartFile[] files,
			@RequestPart(value = "data", required = false) DiaryDto diaryDto, HttpServletRequest request)
			throws Exception {

		String jwtToken = null;
		String subject = null;
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
			jwtToken = authorizationHeader.substring(7);
			subject = jwtTokenUtil.getSubjectFromToken(jwtToken);
		}

		String FileNames = "";
		int insertedCount = 0;

		try {

			for (MultipartFile mf : files) {
				String originFileName = mf.getOriginalFilename(); // 원본 파일 명
				long fileSize = mf.getSize(); // 파일 사이즈

				System.out.println("originFileName : " + originFileName);
				System.out.println("fileSize : " + fileSize);
				String safeFile = System.currentTimeMillis() + originFileName;
				diaryDto.setDiaryImg(safeFile);

				try {
					File f1 = new File(UPLOAD_PATH + safeFile);
					mf.transferTo(f1);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			insertedCount = diaryService.insertPublic(diaryDto);
			diaryDto.setMemberId(subject);
			diaryDto.setShareRoomId(Integer.parseInt(shareRoomId));
			if (insertedCount > 0) {
				Map<String, Object> result = new HashMap<>();
				result.put("message", "정상적으로 등록되었습니다.");
				result.put("count", insertedCount);
				result.put("diaryId", diaryDto.getDiaryId());
				return ResponseEntity.status(HttpStatus.OK).body(result);
			} else {
				Map<String, Object> result = new HashMap<>();
				result.put("message", "등록된 내용이 없습니다.");
				result.put("count", insertedCount);
				return ResponseEntity.status(HttpStatus.OK).body(result);
			}
		} catch (Exception e) {
			Map<String, Object> result = new HashMap<>();
			result.put("message", "등록 중 오류가 발생했습니다.");
			result.put("count", insertedCount);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}
	}

	// 11. 교환 일기 상세 조회
	@GetMapping("/api/someus/sharelist/{shareRoomId}/{createdDt}")
	public ResponseEntity<List<DiaryDto>> selectPublicDetail(@PathVariable Map<String, String> val) throws Exception {

		int shareRoomId = Integer.parseInt(val.get("shareRoomId"));
		String createdDt = val.get("createdDt");
		List<DiaryDto> list = diaryService.selectPublicDetail(shareRoomId, createdDt);

		if (list != null) {
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	// 12. 교환 일기 목록 추가
	@PostMapping("/api/someus/addgroup")
	public ResponseEntity<Integer> addGroup(@RequestBody ShareRoomDto shareRoomDto) throws Exception {

		int insertGroupCount = diaryService.addGroup(shareRoomDto);

		if (insertGroupCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(insertGroupCount);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(insertGroupCount);
		}
	}

	// 12-1. 멤버 추가 전 아이디를 기준으로 번호 조회
	@GetMapping("/api/someus/addgroup/{memberId}")
	public ResponseEntity<Integer> selectShareRoomId(@PathVariable("memberId") String memberId) throws Exception {

		int shareRoomId = diaryService.selectShareRoomId(memberId);

		return ResponseEntity.status(HttpStatus.OK).body(shareRoomId);
	}

	// 13. 교환 일기 멤버 추가
	@PostMapping("/api/someus/addgroupnext")
	public ResponseEntity<Integer> addGroupNext(@RequestBody List<Map<String, Object>> requestData) throws Exception {
		int insertMemberCount = diaryService.addGroupNext(requestData);
		if (insertMemberCount == 2) {
			return ResponseEntity.status(HttpStatus.OK).body(insertMemberCount);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(insertMemberCount);
		}
	}

	// 17. 개인 목표 목록 조회
	@GetMapping("/api/someus/private/list/goal/{memberId}/{goalDate}")
	public ResponseEntity<List<GoalDto>> selectGoalList(@PathVariable(value = "memberId") String memberId,
			@PathVariable(value = "goalDate") String goalDate) throws Exception {

		List<GoalDto> list = diaryService.selectGoalList(memberId, goalDate);

		if (list.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(list);
		}
	}

	// 18. 개인 목표 쓰기
	@PostMapping("/api/someus/private/list/goal")
	public ResponseEntity<Integer> insertGoal(@RequestBody GoalDto goalDto) throws Exception {

		int insertCount = diaryService.insertGoal(goalDto);

		if (insertCount != 0) {
			return ResponseEntity.status(HttpStatus.OK).body(insertCount);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	// 19. 개인 목표 수정
	@PutMapping("/api/someus/private/list/goal/{goalId}")
	public ResponseEntity<Integer> updateGoal(@PathVariable("goalId") int goalId, @RequestBody GoalDto goalDto)
			throws Exception {

		goalDto.setGoalId(goalId);
		int updateCount = diaryService.updateGoal(goalDto);

		if (updateCount != 0) {
			return ResponseEntity.status(HttpStatus.OK).body(updateCount);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	// 20. 개인 목표 삭제
	@DeleteMapping("/api/someus/private/list/goal/{goalId}")
	public ResponseEntity<Integer> deleteGoal(@PathVariable("goalId") int goalId) throws Exception {
		int result = diaryService.deleteGoal(goalId);
		if (result != 0) {
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

}
