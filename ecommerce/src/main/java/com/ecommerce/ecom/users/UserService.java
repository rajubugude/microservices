package com.ecommerce.ecom.users;


import com.ecommerce.ecom.users.dto.AddressDTO;
import com.ecommerce.ecom.users.dto.UserRequest;
import com.ecommerce.ecom.users.dto.UserResponse;
import com.ecommerce.ecom.users.models.Address;
import com.ecommerce.ecom.users.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserResponse> fetchAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .toList();
    }

    public void addUser(UserRequest userRequest) {
        User user = new User();
        mapUserRequestToUser(user, userRequest);
        userRepository.save(user);
    }

    public Optional<UserResponse> fetchUser(Long id) {
        return userRepository.findById(id).
                map(this::mapToUserResponse);
    }

    public boolean updateUser(Long id, UserRequest userRequest) {
        User userDetails = new User();
        mapUserRequestToUser(userDetails, userRequest);
        return userRepository.findById(id)
                .map(existingUser -> {
                    mapUserRequestToUser(existingUser, userRequest);
                    userRepository.save(existingUser);
                    return true;
                })
                .isPresent();
    }

    private void mapUserRequestToUser(User user, UserRequest userRequest) {
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        if(userRequest.getAddress() != null){
            Address address = new Address();
            address.setCity(userRequest.getAddress().getCity());
            address.setCountry(userRequest.getAddress().getCountry());
            address.setStreet(userRequest.getAddress().getStreet());
            address.setZipCode(userRequest.getAddress().getZipCode());
            address.setState(userRequest.getAddress().getState());
            user.setAddress(address);
        }
    }
    private UserResponse mapToUserResponse(User user){
        UserResponse userResponse = new UserResponse();

        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setId(user.getId().toString());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhone(user.getPhone());
        if(user.getAddress() != null){
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setZipCode(user.getAddress().getZipCode());
            userResponse.setAddress(addressDTO);
        }
        return userResponse;
    }
}