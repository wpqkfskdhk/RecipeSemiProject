package com.icia.recipe.service.ub;

import com.icia.recipe.dao.UserBoardRepository;
import com.icia.recipe.dto.Board.UserBoardDTO;
import com.icia.recipe.dto.Board.UserBoardEntity;
import com.icia.recipe.dto.SearchDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserBoardService {

    // Repository, HttpSession 등 필요한 의존성을 주입
    private final UserBoardRepository ubrepo;

    private ModelAndView mav;

    // 파일 저장 경로 설정
    Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/userupload");

    private final HttpServletRequest request;

    private final HttpServletResponse response;

    private final HttpSession session;

    // 게시글 작성
    public ModelAndView writePost(UserBoardDTO uboard) {
        System.out.println("[2] controller → service : " + uboard);
        mav = new ModelAndView();

        // 업로드 파일 처리
        MultipartFile uBFile = uboard.getUBFile();
        String userSavePath = "";

        if (!uBFile.isEmpty()) {
            // 파일이 존재하면 파일명을 생성
            String uuid = UUID.randomUUID().toString().substring(0, 8);
            String fileName = uBFile.getOriginalFilename();

            if (fileName != null && !fileName.isEmpty()) {
                String uBFileName = uuid + "_" + fileName;
                uboard.setUBFileName(uBFileName);


                userSavePath = path + "//" + uBFileName;
            } else {
                // 파일명이 비어있을 경우 기본값으로 설정
                uboard.setUBFileName("default.jpg");
            }
        } else {
            // 파일이 없을 경우 기본 이미지를 설정
            uboard.setUBFileName("default.jpg");
        }

        try {
            // UserBoardEntity로 변환 후 저장
            UserBoardEntity entity = UserBoardEntity.toEntity(uboard);
            ubrepo.save(entity);

            // 실제 파일 저장
            if (!uBFile.isEmpty()) {
                File saveFile = new File(userSavePath);
                uBFile.transferTo(saveFile);
                System.out.println("파일이 저장되었습니다: " + saveFile.getAbsolutePath());
            }
        } catch (Exception e) {
            System.out.println("게시글 등록 실패!");
            e.printStackTrace();
            mav.addObject("error", e.getMessage());
        }

        mav.setViewName("redirect:/blogpost");
        return mav;
    }

    // 게시글 목록 조회
    public List<UserBoardDTO> uBoardList() {
        List<UserBoardDTO> dtoList = new ArrayList<>();
        List<UserBoardEntity> entityList = ubrepo.findAllByOrderByUBNumDesc();

        for (UserBoardEntity entity : entityList) {
            dtoList.add(UserBoardDTO.toDTO(entity));
        }

        return dtoList;
    }

    // 게시글 검색
    public List<UserBoardDTO> usersearchList(SearchDTO search) {
        List<UserBoardDTO> dtoList = new ArrayList<>();
        List<UserBoardEntity> entityList = new ArrayList<>();

        // 카테고리별 검색
        if (search.getCategory().equals("UBWriter")) {
            entityList = ubrepo.findByMember_MIdContainingOrderByUBNumDesc(search.getKeyword());
        } else if (search.getCategory().equals("UBTitle")) {
            entityList = ubrepo.findByUBTitleContainingOrderByUBNumDesc(search.getKeyword());
        } else if (search.getCategory().equals("UBContent")) {
            entityList = ubrepo.findByUBContentContainingOrderByUBNumDesc(search.getKeyword());
        }

        for (UserBoardEntity entity : entityList) {
            dtoList.add(UserBoardDTO.toDTO(entity));
        }

        return dtoList;
    }

    // 게시글 상세보기
    public ModelAndView uBView(int UBNum) {
        System.out.println("[2] controller → service : " + UBNum);
        mav = new ModelAndView();

        // 로그인 사용자 ID 확인
        String loginId = (String) session.getAttribute("loginId");
        if (loginId == null) {
            loginId = "Guest";
        }

        // 쿠키 처리
        Cookie[] cookies = request.getCookies();
        Cookie uViewCookie = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("cookie_" + loginId + "_" + UBNum)) {
                    uViewCookie = cookie;
                }
            }
        }

        // 조회수 증가 처리
        if (uViewCookie == null) {
            String cookie = "cookie_" + loginId + "_" + UBNum;
            Cookie newCookie = new Cookie(cookie, cookie);
            newCookie.setMaxAge(60 * 60);
            response.addCookie(newCookie);
            ubrepo.incrementUBHit(UBNum);
        }

        // 게시글 조회
        Optional<UserBoardEntity> entity = ubrepo.findById(UBNum);
        if (entity.isPresent()) {
            UserBoardDTO dto = UserBoardDTO.toDTO(entity.get());
            mav.addObject("uBView", dto);
            mav.setViewName("blog-view");
        } else {
            mav.setViewName("redirect:/uBList");
        }

        return mav;
    }

    // 게시글 수정
    public ModelAndView uBModify(UserBoardDTO uboard) {
        System.out.println("[2] controller → service : " + uboard);
        mav = new ModelAndView();

        // 기존 파일 삭제
        if (uboard.getUBFileName() != null) {
            String delPath = path + "//" + uboard.getUBFileName();
            File delFile = new File(delPath);
            if (delFile.exists()) {
                delFile.delete();
            }
        }

        // 새 파일 저장 처리
        MultipartFile uBFile = uboard.getUBFile();
        String userSavePath = "";

        if (!uBFile.isEmpty()) {
            String uuid = UUID.randomUUID().toString().substring(0, 8);
            String fileName = uBFile.getOriginalFilename();
            String uBFileName = uuid + "_" + fileName;
            uboard.setUBFileName(uBFileName);
            userSavePath = path + "//" + uBFileName;
        } else {
            uboard.setUBFileName("default.jpg");
        }

        try {
            UserBoardEntity entity = UserBoardEntity.toEntity(uboard);
            ubrepo.save(entity);
            uBFile.transferTo(new File(userSavePath));
            mav.setViewName("redirect:/blogpost");
        } catch (Exception e) {
            System.out.println("게시글 수정 실패!");
            mav.setViewName("redirect:/blogpost");
        }

        return mav;
    }

    // 게시글 삭제
    public ModelAndView uBDelete(UserBoardDTO uboard) {
        System.out.println("[2] controller → service : " + uboard);
        mav = new ModelAndView();

        // 파일 삭제
        if (uboard.getUBFileName() != null) {
            String delPath = path + "\\" + uboard.getUBFileName();
            File delFile = new File(delPath);
            if (delFile.exists()) {
                delFile.delete();
            }
        }

        // 게시글 삭제
        ubrepo.deleteById(uboard.getUBNum());
        mav.setViewName("redirect:/blogpost");

        return mav;
    }
}