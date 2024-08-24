package com.projectdev.HotelEase.service.impl;


import com.projectdev.HotelEase.dto.LoginRequest;
import com.projectdev.HotelEase.dto.Response;
import com.projectdev.HotelEase.dto.UserDTO;
import com.projectdev.HotelEase.entity.User;
import com.projectdev.HotelEase.exception.MyException;
import com.projectdev.HotelEase.repo.UserRepository;
import com.projectdev.HotelEase.service.interfac.IUserService;
import com.projectdev.HotelEase.utils.JWTUtils;
import com.projectdev.HotelEase.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public Response register(User user) {
        Response response = new Response();

        try {
            if (user.getRole() == null || user.getRole().isBlank()) {
                user.setRole("USER");
            }
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new MyException(user.getEmail() + " " + "Already Exists");
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepository.save(user);
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(savedUser);

            response.setStatusCode(200);
            response.setUser(userDTO);
            response.setMessage("successful");

        } catch (MyException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Saving a User" + e.getMessage());

        }
        return response;
    }

    @Override
    public Response login(LoginRequest loginRequest) {
        Response response = new Response();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new MyException("User Not Found"));
            var token = jwtUtils.generateToken(user);

            response.setToken(token);
            response.setExpirationTime("7 days");
            response.setRole(user.getRole());
            response.setMessage("successful");
            response.setStatusCode(200);

        } catch (MyException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Logging in " + e.getMessage());

        }
        return response;
    }

    @Override
    public Response getAllUsers() {
        Response response = new Response();

        try {
            List<User> userList = userRepository.findAll();
            List<UserDTO> userDTOList = Utils.mapUserListEntityToUserListDTO(userList);

            response.setUserList(userDTOList);
            response.setMessage("successful");
            response.setStatusCode(200);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting all users " + e.getMessage());

        }
        return response;
    }

    @Override
    public Response getUSerBookingHistory(String userId) {
        Response response = new Response();

        try {
            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(()-> new MyException("User Not Found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTOPlusUserBookingsAndRoom(user);

            response.setMessage("successful");
            response.setStatusCode(200);
            response.setUser(userDTO);

        } catch (MyException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting user bookings in " + e.getMessage());

        }
        return response;
    }

    @Override
    public Response deleteUser(String userId) {
        Response response = new Response();

        try {
            userRepository.findById(Long.valueOf(userId)).orElseThrow(()-> new MyException("User Not Found"));
            userRepository.deleteById(Long.valueOf(userId));

            response.setMessage("successful");
            response.setStatusCode(200);

        } catch (MyException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error deleting a user " + e.getMessage());

        }
        return response;
    }

    @Override
    public Response getUserById(String userId) {
        Response response = new Response();

        try {
            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(()-> new MyException("User Not Found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);

            response.setMessage("successful");
            response.setStatusCode(200);
            response.setUser(userDTO);

        } catch (MyException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting a user by id " + e.getMessage());

        }
        return response;
    }

    @Override
    public Response getMyInfo(String email) {
        Response response = new Response();

        try {
            User user = userRepository.findByEmail(email).orElseThrow(()-> new MyException("User Not Found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);

            response.setMessage("successful");
            response.setStatusCode(200);
            response.setUser(userDTO);

        } catch (MyException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting a user info " + e.getMessage());

        }
        return response;
    }
}
