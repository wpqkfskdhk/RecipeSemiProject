package com.icia.recipe.service.board;

import com.icia.recipe.dao.BoardRepository;
import com.icia.recipe.dto.Board.BoardDTO;
import com.icia.recipe.dto.Board.BoardEntity;
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
public class BoardService {

    private final BoardRepository brepo;

    /*// 요청
    private final HttpServletRequest request;

    // 응답
    private final HttpServletResponse response;

    private final HttpSession session;*/

    private ModelAndView mav;

    Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/upload");

    private final HttpServletRequest request;

    private final
    HttpServletResponse response;

    private final HttpSession session;

    public ModelAndView bWrite(BoardDTO board) {
        System.out.println("[2] controller → service : " + board);
        mav = new ModelAndView();

        // 파일 가져오기
        MultipartFile bFile = board.getBFile();
        String savePath = "";

        if(!bFile.isEmpty()){
            String uuid = UUID.randomUUID().toString().substring(0,8);
            String fileName = bFile.getOriginalFilename();
            String bFileName = uuid + "_" + fileName;

            board.setBFileName(bFileName);

            savePath = path + "\\" + bFileName;
        } else {
            board.setBFileName("default.jpg");
        }

        try {
            BoardEntity entity = BoardEntity.toEntity(board);
            brepo.save(entity);
            bFile.transferTo(new File(savePath));
        } catch (Exception e) {
            System.out.println("게시글 등록 실패!");
        }

        mav.setViewName("redirect:/list");

        return mav;
    }

    public List<BoardDTO> boardList() {
        List<BoardDTO> dtoList = new ArrayList<>();

        //List<BoardEntity> entityList = brepo.findAllByOrderBybNumDesc();
        //List<BoardEntity> entityList = brepo.findAll(Sort.by(Sort.Direction.DESC, "BNum"));
        List<BoardEntity> entityList = brepo.findAllByOrderByBNumDesc();

        for (BoardEntity entity : entityList){
            dtoList.add(BoardDTO.toDTO(entity));
        }

        return dtoList;
    }

    public List<BoardDTO> searchList(SearchDTO search) {
        List<BoardDTO> dtoList = new ArrayList<>();
        List<BoardEntity> entityList = new ArrayList<>();

        // category : bWriter, bContent, bTitle
        if(search.getCategory().equals("BWriter")){
            entityList = brepo.findByMember_MIdContainingOrderByBNumDesc(search.getKeyword());
            // SELECT * FROM BJPA WHERE BWRITER LIKE '%keyword%' ORDER BY BNUM DESC
        } else if(search.getCategory().equals("BTitle")) {
            entityList = brepo.findByBTitleContainingOrderByBNumDesc(search.getKeyword());
        } else if(search.getCategory().equals("BContent")) {
            entityList = brepo.findByBContentContainingOrderByBNumDesc(search.getKeyword());
        }

        for(BoardEntity entity : entityList){
            dtoList.add(BoardDTO.toDTO(entity));
        }

        return dtoList;
    }

    public ModelAndView bView(int BNum) {
        System.out.println("[2] controller → service : " + BNum);
        mav = new ModelAndView();

        // 로그인 아이디
        String loginId = (String)session.getAttribute("loginId");

        if(loginId == null){
            loginId = "Guest";
        }

        // 현재 쿠키를 담을 배열 생성
        Cookie[] cookies = request.getCookies();

        Cookie viewCookie = null;

        // 쿠키가 존재한다면
        if(cookies != null && cookies.length > 0){

            // 쿠키만큼 반복문을 실행 => cookie : cookies[i]
            for(Cookie cookie : cookies){

                // ex) 쿠키 이름이 "cookie_Geust_1" 이라면
                if(cookie.getName().equals("cookie_" + loginId + "_" + BNum)){
                    System.out.println(cookie.getName() + "존재!");

                    // viewCookie에 "cookie_Geust_1" 을 담는다
                    viewCookie = cookie;
                }
            }
        }

        // 쿠키가 존재하지 않으면 새로운 쿠키를 만든다.
        if(viewCookie == null){
            String cookie = "cookie_" + loginId + "_" + BNum;
            Cookie newCookie = new Cookie(cookie, cookie);

            newCookie.setMaxAge(60 * 60 * 1);
            response.addCookie(newCookie);

            System.out.println("새로운 쿠기 생성 : " + newCookie.getName());

            // 방법(1) 쿼리문을 작성해서 조회수 1증가
            brepo.incrementBHit(BNum);

            // 방법(2) 게시글 번호로 데이터 조회 후 조회수 1증가 후 다시 저장
            // Optional<BoardEntity> entity = brepo.findById(BNum);
            // entity.get().setBHit(entity.get().getBHit() + 1);
            // brepo.save(entity.get());

        }

        Optional<BoardEntity> entity = brepo.findById(BNum);
        if(entity.isPresent()){
            BoardDTO dto = BoardDTO.toDTO(entity.get());
            mav.addObject("view", dto);
            mav.setViewName("/view");
        } else {
            mav.setViewName("redirect:/bList");
        }

        return mav;
    }

    public ModelAndView bModify(BoardDTO board){
        System.out.println("[2] controller → service : " + board);
        mav = new ModelAndView();

        // 기존 파일 삭제하기
        if(board.getBFileName() != null){
            String delPath = path + "\\" + board.getBFileName();

            File delFile = new File(delPath);
            if(delFile.exists()){
                delFile.delete();

            }
        }

        // 파일 가져오기
        MultipartFile bFile = board.getBFile();
        String savePath = "";

        if(!bFile.isEmpty()){
            String uuid = UUID.randomUUID().toString().substring(0,8);
            String fileName = bFile.getOriginalFilename();
            String bFileName = uuid + "_" + fileName;

            board.setBFileName(bFileName);

            savePath = path + "\\" + bFileName;
        } else {
            board.setBFileName("default.jpg");
        }

        try {
            BoardEntity entity = BoardEntity.toEntity(board);
            brepo.save(entity);
            bFile.transferTo(new File(savePath));
            mav.setViewName("redirect:/bList");
        } catch (Exception e) {
            System.out.println("게시글 수정 실패!");
            mav.setViewName("redirect:/index");
        }

        return mav;
    }


    public ModelAndView bDelete(BoardDTO board) {
        System.out.println("[2] controller → service : " +board);
        mav = new ModelAndView();
        // 기존 파일 삭제하기
        if(board.getBFileName() != null){
            String delPath = path + "\\" + board.getBFileName();

            File delFile = new File(delPath);
            if(delFile.exists()){
                delFile.delete();

            }
        }
        brepo.deleteById(board.getBNum());
        mav.setViewName("redirect:/bList");

        return mav;
    }


}
