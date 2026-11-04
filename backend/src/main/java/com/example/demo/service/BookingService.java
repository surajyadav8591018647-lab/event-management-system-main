package com.example.demo.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Bookings;
import com.example.demo.entity.Event;
import com.example.demo.entity.User;
import com.example.demo.exception.BookingException;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.UserRepository;

@Service
public class BookingService {

    private final BookingRepository bookRepo;
    private final UserRepository userRepo;
    private final EventRepository eventRepo;

    public BookingService(
            BookingRepository bookRepo,
            UserRepository userRepo,
            EventRepository eventRepo) {

        this.bookRepo = bookRepo;
        this.userRepo = userRepo;
        this.eventRepo = eventRepo;
    }

    public Bookings bookEvent(Long eventId) {

        // Get currently authenticated user
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        // Find user using email from JWT
        User usr = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        // Find event
        Event e = eventRepo.findById(eventId)
                .orElseThrow(() ->
                        new RuntimeException("Event not found"));

        //Check whether user already booked this event
        boolean alreadyBooked = bookRepo.existsByUser_IdAndEvent_Id(usr.getId(), e.getId());

        if( alreadyBooked ){
                throw new BookingException("You have already booked this event");
        }


        // Create booking
        Bookings book = new Bookings();

        book.setUser(usr);
        book.setEvent(e);

        return bookRepo.save(book);
    }

    public List<Bookings> getBookingsForEvent(Long eventId) {

        Event e = eventRepo.findById(eventId)
                .orElseThrow(() ->
                        new RuntimeException("Event not found"));

        return bookRepo.findByEvent(e);
    }

    public List<User> getUsersForEvent(Long eventId) {

        Event e = eventRepo.findById(eventId)
                .orElseThrow(() ->
                        new RuntimeException("Event not found"));

        List<Bookings> bookings = bookRepo.findByEvent(e);

        return bookings.stream()
                .map(Bookings::getUser)
                .toList();
    }
}