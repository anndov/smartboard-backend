package com.smartboard.security.services;

import com.smartboard.security.model.Authority;
import com.smartboard.security.model.User;
import com.smartboard.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityService authorityService;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Page findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Long count() {
        return userRepository.count();
    }

    public User save(User user) {
        Set<Authority> authorities = new HashSet<Authority>();
        if(user.getAuthorities() != null)
        user.getAuthorities().forEach(authority -> {
            authorities.add(authorityService.findByName(authority.getName()).size() == 0 ?
                    authorityService.save(new Authority(authority.getName())) : authorityService.findByName(authority.getName()).get(0));
        });
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (user.getPassword() != null)
            user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthorities(authorities);
        return userRepository.save(user);
    }

    public User update(User user) {
        Set<Authority> authorities = new HashSet<Authority>();
        if(user.getAuthorities() != null)
        user.getAuthorities().forEach(authority -> {
            authorities.add(authorityService.findByName(authority.getName()).size() == 0 ?
                    authorityService.save(new Authority(authority.getName())) : authorityService.findByName(authority.getName()).get(0));
        });
        user.setAuthorities(authorities);
        /*if(userRepository.getOne(user.getId()).getBoards() !=null)
        user.setBoards(userRepository.getOne(user.getId()).getBoards());*/
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.delete(id);
    }

    public void deleteAllInBatch() {
        userRepository.deleteAllInBatch();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean changePassword(Long id, String oldPassword, String newPassword) {
        User user = userRepository.findOne(id);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean passMatch = passwordEncoder.matches(oldPassword, user.getPassword());
        if (passMatch) {
            user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
            userRepository.save(user);
        }

        return passMatch;
    }

    public User profileUpdate(Long id, String firstName, String lastName) {
        User user = userRepository.findOne(id);
        user.setFirstname(firstName);
        user.setLastname(lastName);
        return userRepository.save(user);
    }

}
