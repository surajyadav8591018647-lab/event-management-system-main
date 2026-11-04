package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Event;
import com.example.demo.service.EventService;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "http://localhost:5173")
public class EventController {

	private final EventService eServ;

	public EventController(EventService eServ) {
		this.eServ = eServ;
	}
	
	@PostMapping
	public Event createEvent(@RequestBody Event e) {
		return eServ.createEvent(e);
	}
	
	@GetMapping
	public List<Event> getAllEvents(){
		return eServ.getAllEvents();
	}
	
	
	
}
