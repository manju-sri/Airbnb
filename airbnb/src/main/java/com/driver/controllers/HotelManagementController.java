package com.driver.controllers;

import com.driver.Service.HotelManagementService;
import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/hotel")
public class HotelManagementController {
    @Autowired
    HotelManagementService hotelManagementService;
    @PostMapping("/add-hotel")
    public String addHotel(@RequestBody Hotel hotel){
        String response = hotelManagementService.addHotel(hotel);
        return response;
    }

    @PostMapping("/add-user")
    public Integer addUser(@RequestBody User user){
        hotelManagementService.addUser(user);
        return user.getaadharCardNo();
    }

    @GetMapping("/get-hotel-with-most-facilities")
    public String getHotelWithMostFacilities(){
        String hotelName = hotelManagementService.getHotelWithMostFacilities();
        return hotelName;
    }

    @PostMapping("/book-a-room")
    public int bookARoom(@RequestBody Booking booking){

        int totalAmount = hotelManagementService.bookARoom(booking);
        return totalAmount;
    }

    @GetMapping("/get-bookings-by-a-person/{aadharCard}")
    public int getBookings(@PathVariable("aadharCard")Integer aadharCard)
    {
        int totalBookings = hotelManagementService.getBookingDetails(aadharCard);
        return totalBookings;
    }

    @PutMapping("/update-facilities")
    public Hotel updateFacilities(List<Facility> newFacilities,String hotelName){
        Hotel hotel = hotelManagementService.updateFacilities(newFacilities, hotelName);
        return hotel;
    }

}
