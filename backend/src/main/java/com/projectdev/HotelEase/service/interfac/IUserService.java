package com.projectdev.HotelEase.service.interfac;

import com.projectdev.HotelEase.dto.LoginRequest;
import com.projectdev.HotelEase.dto.Response;
import com.projectdev.HotelEase.entity.User;

public interface IUserService {

    Response register(User user);
    Response login(LoginRequest loginRequest);
    Response getAllUsers();
    Response getUSerBookingHistory(String userId);
    Response deleteUser(String userId);
    Response getUserById(String userId);
    Response getMyInfo(String email);
}