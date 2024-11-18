package com.icia.recipe.dao;

import com.icia.recipe.dto.Member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, String> {
}
