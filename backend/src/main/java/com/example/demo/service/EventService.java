package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Event;
import com.example.demo.repository.EventRepository;

@Service
public class EventService {

	private final EventRepository eRepo;

	public EventService(EventRepository eRepo) {
		this.eRepo = eRepo;
	}
	
	public Event createEvent(Event e) {
		return eRepo.save(e);
	}
	
	public List<Event> getAllEvents(){
		return eRepo.findAll();
	}
}
