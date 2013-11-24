package com.controller;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
	public String month = "";
	boolean powerCheck = false;
	boolean tempCheck = false;
	boolean details = false;
	int year= -1;
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model, HttpSession session) {
		
		//model.addAttribute("message","Test");
		return "showMessage";
	}
	
	@RequestMapping(method = RequestMethod.POST, value ="/request")
	public String getResult(Model model, HttpSession session,
			@RequestParam("speech") String speech) throws JSONException {
	    session.setAttribute("previousRequest", speech);
		if(parseInput(speech,session))
		{
			model.addAttribute("result",CassandraModel.getDataByMonth(month,year,location).toString());
			System.out.println(location);
		}
		else
		{
			model.addAttribute("result","[]");
			
		}
	    return "result";
	}
	
	public boolean parseInput(String speech,HttpSession session) {
		String data[];
		
		if(speech.toLowerCase().matches("(.*)details(.*)"))
		{
			if(!((Boolean) session.getAttribute("properRequest")).booleanValue())
			{ return false;}
			
			this.details = true;
			if(speech.matches("(.*)room 1(.*)"))
			{
				location = CassandraModel.LOC_ROOM1;
			}
			else if(speech.matches("(.*)room 2(.*)"))
			{
				location = CassandraModel.LOC_ROOM2;
			}
			else if(speech.matches("(.*)living room(.*)"))
			{
				location = CassandraModel.LOC_LIVINGROOM;
			}
			else if(speech.matches("(.*)washroom(.*)|(.*)bathroom(.*)"))
			{
				location = CassandraModel.LOC_BATHROOM;
			}
			else
			{
				return false;
			}
			
			this.powerCheck = ((Boolean) session.getAttribute("powerCheck")).booleanValue();
			this.month = (String)session.getAttribute("month");
			this.year = ((Integer)session.getAttribute("year")).intValue();
			
		}
		else
		{
			//Sentence structure "show me consumption IN location(kitchen,livingroom,etc)
			data = speech.toLowerCase().split("for");
			if(data.length != 2)
				return false;
			
			System.out.println(data[0]+"  "+data[1]);
			
			if(data[0].matches("(.*)consumption(.*)|(.*)energy usage(.*)|(.*)power(.*)") )
			{
				powerCheck = true;
			}
			else if(data[0].matches("(.*)temperature(.*)|(.*)weather(.*)|(.*)warm(.*)"))
			{
				tempCheck = true;
			}
			else
				return false; //fail
			

			//find more details
			//specific year
			Pattern p = Pattern.compile("(.*)([12][0-9]{3})(.*)");
			Matcher m = p.matcher(speech);
			if(m.matches())
			{
				this.year = Integer.parseInt(m.group(2));
			}
			p = Pattern.compile("(.*)(january)(.*)|(.*)(february)(.*)|(.*)(march)(.*)|(.*)(april)(.*)|"+
								"(.*)(may)(.*)|(.*)(june)(.*)|(.*)(july)(.*)|(.*)(august)(.*)|(.*)(september)(.*)|(.*)(october)(.*)|(.*)(december)(.*)");
			m = p.matcher(data[1]);
			if(m.matches())
			{
				this.month = m.group(2);
			}
			
			session.setAttribute("properRequest", "true");
			session.setAttribute("powerCheck", this.powerCheck);
			session.setAttribute("month", this.month);
			session.setAttribute("year", this.year);
		}
		
		return true;
	}
	
	
}