package com.icia.recipe.service.mem;

import com.icia.recipe.dao.BoardRepository;
import com.icia.recipe.dao.CommentRepository;
import com.icia.recipe.dao.MemberRepository;
import com.icia.recipe.dto.Member.MemberDTO;
import com.icia.recipe.dto.Member.MemberEntity;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    // 이메일 인증
    private final JavaMailSender mailSender;

    // 저장 경로
    Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/profile");

    // 비밀번호 암호화
    private BCryptPasswordEncoder pwEnc = new BCryptPasswordEncoder();

    // 로그인
    private final HttpSession session;

    private ModelAndView mav;

    private final MemberRepository mrepo;

    private final BoardRepository brepo;
    private final CommentRepository crepo;

    public String idCheck(String mId){
        String result = "";
        Optional<MemberEntity> entity = mrepo.findById(mId);


        if(entity.isPresent()){
            result = "NO";
        } else {
            result = "OK";
        }

        return result;
    }

    public String emailCheck(String mEmail){
        String uuid = null;

        // 인증번호
        uuid = UUID.randomUUID().toString().substring(0,8);

        // 이메일 발송
        MimeMessage mail = mailSender.createMimeMessage();

        String message = "<h2>안녕하세요. 흑미백미 레시피 입니다.</h2>"
                + "<p>인증번호는 <b>" + uuid + "</b> 입니다.</p>";

        try {
            // 이메일 전송 준비
            mail.setSubject("흑미백미 인증번호");
            mail.setText(message, "UTF-8", "html");
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(mEmail));

            // 이메일 전송
            // mailSender.send(mail);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return uuid;
    }


    public ModelAndView mJoin(MemberDTO member) {

        mav = new ModelAndView();

        // (1) 파일 업로드
        MultipartFile mProfile = member.getMProfile();

        if(!mProfile.isEmpty()){
            String uuid = UUID.randomUUID().toString().substring(0,8);
            String fileName = mProfile.getOriginalFilename();
            String mProfileName = uuid + "_" + fileName;

            member.setMProfileName(mProfileName);

            String savePath = path + "\\" + mProfileName;
            //System.out.println("savePath : " + savePath);   // 확인하고 주석처리

            try {
                mProfile.transferTo(new File(savePath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            member.setMProfileName("default.jpg");
        }

        // (2) 비밀번호 암호화
        member.setMPw(pwEnc.encode(member.getMPw()));

       // System.out.println("암호화 이후 : " + member);


        MemberEntity entity = MemberEntity.toEntity(member);

        // (4) db저장
        try {
            mrepo.save(entity);
            mav.setViewName("redirect:/mLoginForm");
        } catch (Exception e){
            mav.setViewName("redirect:/mJoinForm");
            throw new RuntimeException(e);
        }

        return mav;
    }

    public ModelAndView mLogin(MemberDTO member) {
        mav = new ModelAndView();

        // (1) 아이디가 존재하는지 확인
        Optional<MemberEntity> entity = mrepo.findById(member.getMId());
        if(entity.isPresent()){
            // (2) 해당 아이디의 암호화 된 비밀번호와 로그인 페이지에서 입력한 비밀번호가 일치하는지 확인
            if(pwEnc.matches(member.getMPw(), entity.get().getMPw())){
                // (3) entity → dto
                MemberDTO login = MemberDTO.toDTO(entity.get());

                session.setAttribute("loginId", login.getMId());
                session.setAttribute("loginProfile", login.getMProfileName());
            } else {
                System.out.println("비밀번호가 일치하지 않습니다.");
            }
        } else {
            System.out.println("아이디 존재하지 않습니다.");
        }

        mav.setViewName("redirect:/index");
        return mav;
    }
    public ModelAndView mView(String mId) {
        System.out.println("[2] controller → service : " + mId);
        mav = new ModelAndView();

        Optional<MemberEntity> entity = mrepo.findById(mId);

        if(entity.isPresent()){
            MemberDTO member  = MemberDTO.toDTO(entity.get());
            System.out.println(member);
            mav.addObject("view", member);
            mav.setViewName("/mView");
        } else {
            mav.setViewName("redirect:/mList");
        }

        return mav;
    }

    public ModelAndView mList() {
        System.out.println("[2] controller → service ");
        mav = new ModelAndView();

        // (1) db에서 리스트를 가져온다(entity타입)
        List<MemberEntity> entityList = mrepo.findAll(Sort.by(Sort.Direction.ASC, "MName"));


        // (2) entity타입의 리스트를 dto타입의 리스트로 변환
        List<MemberDTO> dtoList = new ArrayList<>();

        for (MemberEntity entity : entityList){
            dtoList.add(MemberDTO.toDTO(entity));
        }

        mav.setViewName("/mList");
        mav.addObject("memberList", dtoList);

        return mav;
    }
    public ModelAndView mModify(MemberDTO member) {
        System.out.println("[2] controller → service : " + member);
        mav = new ModelAndView();

        // (0) 기존 파일 삭제
        // 기존 프로필 사진이 존재하거나 프로필 이름이 "default.jpg"가 아니라면
        if(member.getMProfileName()!=null && !member.getMProfileName().equals("default.jpg")){
            String delPath = path + "\\" + member.getMProfileName();

            File delFile = new File(delPath);

            if(delFile.exists()){
                delFile.delete();
            }
        }

        // (1) 파일 업로드
        MultipartFile mProfile = member.getMProfile();

        if(!mProfile.isEmpty()){
            String uuid = UUID.randomUUID().toString().substring(0,8);
            String fileName = mProfile.getOriginalFilename();
            String mProfileName = uuid + "_" + fileName;

            member.setMProfileName(mProfileName);

            String savePath = path + "\\" + mProfileName;
//            System.out.println("savePath : " + savePath);   // 확인하고 주석처리

            try {
                mProfile.transferTo(new File(savePath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            member.setMProfileName("default.jpg");
        }

        // (2) 비밀번호 암호화
        member.setMPw(pwEnc.encode(member.getMPw()));

//        System.out.println("암호화 이후 : " + member);

        // (3) dto(MemberDTO) → entity(MemberEntity)
        MemberEntity entity = MemberEntity.toEntity(member);

        // (4) db저장
        try {
            mrepo.save(entity);
            mav.setViewName("redirect:/mView/"+member.getMId());
        } catch (Exception e){
            mav.setViewName("redirect:/mList");
            throw new RuntimeException(e);
        }

        return mav;
    }
    @Transactional
    public ModelAndView mDelete(MemberDTO member) {
        System.out.println("[2] controller → service : " + member);
        mav = new ModelAndView();

        // 기존 프로필 사진이 존재하거나 프로필 이름이 "default.jpg"가 아니라면
        if(member.getMProfileName()!=null && !member.getMProfileName().equals("default.jpg")){
            String delPath = path + "\\" + member.getMProfileName();

            File delFile = new File(delPath);

            if(delFile.exists()){
                delFile.delete();
            }
        }
        MemberEntity memberEntity = mrepo.findById(member.getMId()).orElse(null);
        if (memberEntity != null) {

            crepo.deleteAll(memberEntity.getComments());
            brepo.deleteAll(memberEntity.getBoards());
        }
        mrepo.deleteById(member.getMId());
        session.invalidate();

        mav.setViewName("redirect:/mList");

        return mav;
    }

}
