package ca.sheridancollege.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.database.DatabaseAccess;

@Controller
public class SecurityController {
	
	@Autowired
	private DatabaseAccess da;
	
	@GetMapping("/login")
	public String goHome() {
		return "login.html";
	}
	
	@GetMapping("/access-denied")
	public String accessDenied() {
		return "access-denied.html";
	}
	
	@GetMapping("/register")
	public String goRegister() {
		return "register.html";
	}
	
	@PostMapping("/register")
	public String addUser(@RequestParam String name,@RequestParam String password,@RequestParam(required=false) String b,@RequestParam(required=false) String w) {
		
		da.addNewUser(name,password);
		long userID=da.findUserAccount(name).getUserID();
		
		if(b!=null) {
			da.addUserRoles(userID,1);
		}
		
		if(w!=null) {
			da.addUserRoles(userID,2);
		}
		

		return "redirect:/login";
	}
}
