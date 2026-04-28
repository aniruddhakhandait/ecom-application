package com.app.ecom_application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
//    private List<User> userList = new ArrayList<>();

    // Adding uuid
    private Long nextId = 1L;



//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    public List<User> fetchAllUsers(){
//           return userList;
        return userRepository.findAll();
    }

    public void addUsers(User user){
//        user.setId(nextId ++);
//        userList.add(user);
           userRepository.save(user);

    }

    public Optional<User> fetchUser (Long id){
//          return userList.stream()
//                  .filter(user -> user.getId().equals(id))
//                  .findFirst();
        return userRepository.findById(id);

    }

    // Updating the current user
    public boolean updateUser(Long id, User updatedUser ){
//         return userList.stream().filter(user -> user.getId().equals(id))
//                 .findFirst()
//                 .map( existingUser ->{
//                       existingUser.setFirstName(updatedUser.getFirstName());
//                       existingUser.setLastName(updatedUser.getLastName());
//
//                     return true;
//                 }).orElse(false);

      return userRepository.findById(id)
              .map(existingUser -> {
                  existingUser.setFirstName(updatedUser.getFirstName());
                  existingUser.setLastName(updatedUser.getLastName());
                  userRepository.save(existingUser);
                  return true;

              }).orElse(false);

    }
}
