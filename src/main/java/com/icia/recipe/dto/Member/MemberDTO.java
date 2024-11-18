package com.icia.recipe.dto.Member;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MemberDTO {
    private String MId;
    private String MPw;
    private String MName;
    private String MBirth;
    private String MGender;
    private String MEmail;
    private String MPhone;
    private String MAddr;
    private MultipartFile MProfile;
    private String MProfileName;

    public static MemberDTO toDTO(MemberEntity entity){
        MemberDTO dto = new MemberDTO();

        dto.setMId(entity.getMId());
        dto.setMPw(entity.getMPw());
        dto.setMName(entity.getMName());
        dto.setMBirth(entity.getMBirth());
        dto.setMGender(entity.getMGender());
        dto.setMEmail(entity.getMEmail());
        dto.setMPhone(entity.getMPhone());
        dto.setMAddr(entity.getMAddr());
        dto.setMProfileName(entity.getMProfileName());

        return dto;

    }


}
