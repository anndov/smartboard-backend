package com.smartboard.security.repository;

import com.smartboard.security.model.Authority;
import com.smartboard.security.model.AuthorityName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    List<Authority> findByName(AuthorityName name);
}
