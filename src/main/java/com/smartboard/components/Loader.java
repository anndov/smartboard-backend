package com.smartboard.components;

import com.smartboard.security.model.Authority;
import com.smartboard.security.model.AuthorityName;
import com.smartboard.security.model.User;
import com.smartboard.security.services.AuthorityService;
import com.smartboard.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class Loader implements ApplicationRunner {

    private UserService userService;
    private AuthorityService authorityService;

    @Autowired
    public Loader(UserService userService, AuthorityService authorityService) {
        this.userService = userService;
        this.authorityService = authorityService;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        createDefaultUsers();
    }

    private void createDefaultUsers() {
        User admin;
        Authority authority;

        if (authorityService.findByName(AuthorityName.ROLE_ADMIN).size() == 0)
            authority = new Authority(AuthorityName.ROLE_ADMIN);
        else
            authority = authorityService.findByName(AuthorityName.ROLE_ADMIN).get(0);

        Set<Authority> authorities = new HashSet<Authority>();
        authorities.add(authorityService.save(authority));

        if (userService.findByUsername("admin@admin.com") == null) {
            admin = new User("admin@admin.com", "admin", true);
            admin.setAuthorities(authorities);
            admin.setValidated(true);
            userService.save(admin);
        }

        if (userService.findByUsername("user@user.com") == null) {
            admin = new User("user@user.com", "user", true);
            admin.setValidated(true);
            userService.save(admin);
        }

    }

}
