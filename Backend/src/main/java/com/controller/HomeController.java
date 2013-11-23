package com.controller;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.model.CassandraModel;



/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	public String location="";
	boolean powerCheck = false;
	boolean tempCheck = true;
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model, HttpSession session) {
		System.out.print("HELLO WORLD");
		
		model.addAttribute("message",CassandraModel.getResult());
		return "showMessage";
	}
	public String parseInput(String speech) {
		
		//Sentence structure "show me consumption IN location(kitchen,livingroom,etc)
		String data[] = speech.toLowerCase().split("in");
		
		if(data[0].matches("consumption") || data[0].matches("energy usage") || data[0].matches("power") )
		{
			powerCheck = true;
		}
		else if(data[1].matches("temperature") || data[0].matches("weather") || data[0].matches("warm"))
		{
			tempCheck = true;
		}
		else
			return null; //fail
		
		if(data[1].matches("room 1"))
		{
			location = CassandraModel.LOC_ROOM1;
		}
		else if(data[1].matches("room 2"))
		{
			location = CassandraModel.LOC_ROOM2;
		}
		else if(data[1].matches("living room"))
		{
			location = CassandraModel.LOC_LIVINGROOM;
		}
		else if(data[1].matches("washroom") || data[1].matches("bathroom"))
		{
			location = CassandraModel.LOC_BATHROOM;
		}
		else
		{
			location = 
		}
	}
	
	
}