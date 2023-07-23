package com.driver.Service;

import com.driver.Repository.HotelManagementRepository;
import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HotelManagementService {

    @Autowired
    HotelManagementRepository hotelManagementRepository;

    public String addHotel(Hotel hotel) {

        if(hotel.getHotelName()==null || hotel==null){
            return "FAILURE";
        }
        if(hotelManagementRepository.isHotelExist(hotel)){
            return "FAILURE";
        }
        hotelManagementRepository.saveHotel(hotel);
        return "SUCCESS";
    }

    public void addUser(User user) {
        hotelManagementRepository.saveUser(user);
    }

    public String getHotelWithMostFacilities() {

        List<Hotel> hotelList =  hotelManagementRepository.getListOfHotel();
        int maxFacilities =0;
        String hotelWithMostFacilities= "";

        for (Hotel hotel: hotelList){
            int numberOfFacilities = hotel.getFacilities().size();
            if(numberOfFacilities>maxFacilities){
                maxFacilities = numberOfFacilities;
                hotelWithMostFacilities = hotel.getHotelName();
            }
            else if (numberOfFacilities==maxFacilities && hotel.getHotelName().compareTo(hotelWithMostFacilities)<0){
                hotelWithMostFacilities= hotel.getHotelName();
            }
        }
        return hotelWithMostFacilities.isEmpty()?"":hotelWithMostFacilities;
    }

    public int bookARoom(Booking booking) {
        Hotel hotel = hotelManagementRepository.getHotelByBooking(booking);
        int totalAmountPaid = booking.getNoOfRooms()*hotel.getPricePerNight();
        UUID uuid = UUID.randomUUID();
        String id = String.valueOf(uuid);
        int availableRooms = hotelManagementRepository.availableRoomsInHotel(booking);

        if (booking.getNoOfRooms()<=availableRooms){
            booking.setBookingId(id);
            booking.setAmountToBePaid(totalAmountPaid);
            hotelManagementRepository.bookARoomAndSave(booking);
            return totalAmountPaid;
        }
        return -1;
    }

    public int getBookingDetails(Integer aadharCard) {
        List<Booking> bookingsList = hotelManagementRepository.getBookingsList(aadharCard);
        int bookingByPerson =0;
        for (Booking booking: bookingsList){
            if (booking.getBookingAadharCard()==aadharCard){
                bookingByPerson++;
            }
        }
        return bookingByPerson;
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        // hotelManagementRepository.addFacilitiesToHotel(newFacilities, hotelName);
        Hotel hotel = hotelManagementRepository.getHotelById(hotelName);
        if(hotel==null){
            return null;
        }
        Set<Facility> existingFacilities = new HashSet<>(hotel.getFacilities());
        for (Facility facility: newFacilities){
            if(!existingFacilities.contains(facility)){
                existingFacilities.add(facility);
            }
        }

        hotel.setFacilities(new ArrayList<>(existingFacilities));
        hotelManagementRepository.saveHotel(hotel);
        return hotel;
    }
}
