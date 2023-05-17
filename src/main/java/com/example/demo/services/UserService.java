package com.example.demo.services;

import com.example.demo.entity.User;
import com.example.demo.repository.IRoleRepository;
import com.example.demo.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    public void save(User user) {
        userRepository.save(user);

        Long userId = userRepository.getUserIdByUsername(user.getUsername());
        Long roleId = roleRepository.getRoleIdByName("USER");

        if (roleId != 0 && userId != 0)
            userRepository.addRoleToUser(userId, roleId);
    }
}

