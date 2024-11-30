package com.baipiao.api.users;

import com.baipiao.api.users.dto.UserCreateDTO;
import com.baipiao.api.users.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public UserDTO registerWithoutEncoding(User.RegisterRequest registerRequest) {
        // Verify the username
        if (userRepository.existsByUserNameOrEmail(registerRequest.getUsername(), registerRequest.getEmail())) {
            return null;
        }

        // Create user
        User user = new User();
        user.setUserName(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        user.setUserType(registerRequest.getUserType());
        user.setCreatedAt(LocalDateTime.now());

        // Save back to DB
        User savedUser = userRepository.save(user);

        return new UserDTO(savedUser);
    }
    /**
     * Update the password of an existing user.
     *
     * @param id          The ID of the user whose password will be updated.
     * @param newPassword The new password to set for the user.
     */
    public void updatePassword(Long id, String newPassword) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        // Update the password
        user.setPassword(newPassword);
        // Save the updated user back to the database
        userRepository.save(user);
    }
}
