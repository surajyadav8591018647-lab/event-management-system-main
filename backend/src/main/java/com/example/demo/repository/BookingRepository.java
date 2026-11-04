package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Bookings;
import com.example.demo.entity.Event;

import java.util.List;

public interface BookingRepository extends JpaRepository<Bookings, Long>{
	
	boolean existsByUser_IdAndEvent_Id(Long userId, Long eventId) ;
		
		List<Bookings> findByEvent(Event event);
	
		

}
