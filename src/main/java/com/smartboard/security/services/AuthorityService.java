package com.smartboard.security.services;

import com.smartboard.security.model.Authority;
import com.smartboard.security.model.AuthorityName;
import com.smartboard.security.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    public Authority save(Authority authority) {
        return authorityRepository.save(authority);
    }

    public List<Authority> findByName(AuthorityName name) {
        return authorityRepository.findByName(name);
    }
}
