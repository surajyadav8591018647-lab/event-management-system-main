package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Bookings;
import com.example.demo.entity.User;
import com.example.demo.service.BookingService;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:5173")
public class BookingController {

    private final BookingService bookService;

    public BookingController(BookingService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public Bookings bookEvent(@RequestParam Long eventId) {
        return bookService.bookEvent(eventId);
    }

    @GetMapping("/event/{eventId}")
    public List<Bookings> getBookings(@PathVariable Long eventId) {
        return bookService.getBookingsForEvent(eventId);
    }

    @GetMapping("/event/{eventId}/users")
    public List<User> getUserForEvent(@PathVariable Long eventId) {
        return bookService.getUsersForEvent(eventId);
    }
}