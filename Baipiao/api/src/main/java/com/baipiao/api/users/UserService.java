package com.baipiao.api.users;

import com.baipiao.api.users.dto.UserCreateDTO;
import com.baipiao.api.users.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<UserDTO> getAll() {
            return userRepository.findAllUsers().stream().map(user -> {
                System.out.println(user);
                return new UserDTO(user);
            }).collect(Collectors.toList());
    }

    public UserDTO find(Long id) {
        return userRepository.findById(id).map(user -> {
            return new UserDTO(user);
        }).orElse(null);
    }

    public void deleteById(Long id) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        userRepository.deleteUserById(id);
    }

    public void save(UserCreateDTO newUser) {  
        User user = new User();
        user.setEmail(newUser.getEmail());
        user.setUserName(newUser.getUserName());
        user.setPassword(newUser.getPassword());
        user.setPhoneNumber(newUser.getPhoneNumber()); 
        user.setDisplayName(newUser.getDisplayName());
        user.setUserType(newUser.getUserType()); 
        user.setCreatedAt(newUser.getCreatedAt());
        userRepository.insertUser( user.getEmail(), user.getUserName(), user.getPassword(), user.getPhoneNumber(), user.getDisplayName(), user.getUserType(), user.getCreatedAt());
        
    }

    public void update(UserCreateDTO newUser, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        System.out.println("Update user: " + user);
        user.setEmail(newUser.getEmail());
        user.setUserName(newUser.getUserName());
        user.setPassword(newUser.getPassword());
        user.setPhoneNumber(newUser.getPhoneNumber()); 
        user.setDisplayName(newUser.getDisplayName());
        user.setUserType(newUser.getUserType()); 
        user.setCreatedAt(newUser.getCreatedAt());

        userRepository.updateUser( user.getId(), user.getEmail(), user.getUserName(), user.getPassword(), user.getPhoneNumber(), user.getDisplayName(), user.getUserType(), user.getCreatedAt());
    }

    public UserDTO login(String userName, String password) {
        User user = userRepository.findUserByUserName(userName);
        if (user != null && user.getPassword().equals(password)) {
            return new UserDTO(user);
        }
        // Invalid username or password
        return null;
    }
}
