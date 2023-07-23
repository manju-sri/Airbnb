package com.driver.Repository;

import com.driver.model.Booking;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class HotelManagementRepository {

    HashMap<String, Hotel> hotelDb = new HashMap<>();
    HashMap<Integer, User> userDb = new HashMap<>();
    HashMap<String, Booking> bookingDb = new HashMap<>();

    public void saveHotel(Hotel hotel) {
        String hotelName = hotel.getHotelName();
        hotelDb.put(hotelName, hotel);
    }

    public Hotel getHotelById(String hotelId){
        if(hotelDb.containsKey(hotelId)){
            return hotelDb.get(hotelId);
        }
        return null;
    }

    public void saveUser(User user) {
        userDb.put(user.getaadharCardNo(),user);
    }

    public List<Hotel> getListOfHotel() {
        ArrayList<Hotel> hotelList = new ArrayList<>();
        for (Hotel hotelName: hotelDb.values()) {
            hotelList.add(hotelName);
        }
        return hotelList;
    }

    public void bookARoomAndSave(Booking booking) {
        bookingDb.put(booking.getBookingId(), booking);
    }

    public int availableRoomsInHotel(Booking booking) {
        int totalRooms = hotelDb.get(booking.getHotelName()).getAvailableRooms();
        return totalRooms;
    }

    public List<Booking> getBookingsList(Integer aadharCard) {
        ArrayList<Booking> bookings = new ArrayList<>();

        for (Booking booking : bookingDb.values()){
            bookings.add(booking);
        }
        return bookings;
    }

    public boolean isHotelExist(Hotel hotel) {
        return hotelDb.containsKey(hotel);
    }

    public Hotel getHotelByBooking(Booking booking) {
        Hotel hotel = hotelDb.get(booking.getHotelName());
        return hotel;
    }
}
