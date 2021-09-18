package ca.sheridancollege.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.beans.PurchasedToys;
import ca.sheridancollege.beans.Store;
import ca.sheridancollege.beans.Equipment;
import ca.sheridancollege.database.DatabaseAccess;
import ca.sheridancollege.emails.EmailServiceImpl;


@Controller
public class HomeController {
	
	@Autowired
	private DatabaseAccess da;
	
	@Autowired
	private EmailServiceImpl esi;
	
	@GetMapping("/")
	public String goHome() {
		return "home.html";
	}
	
	//------------------------------------------------------------toy-------------------------------------------------------
	//Go to add toy page
	@GetMapping("/GoAddEquipment")
	public String goAddEquipment(Model model) {
		Equipment equ=new Equipment();
		model.addAttribute("equ", equ);

		return "addEquipment.html";
	}
		
	@GetMapping("/AddEquipment")
	public String addEquipment(Model model, @ModelAttribute Equipment equ) {
		
		da.addEquipment(equ);
		model.addAttribute("equ", new Equipment());

		return "addEquipment.html";
	}
	
	@GetMapping("/ViewEquipments")
	public String getEquipments(Model model) {
		ArrayList<Equipment> equs = da.getEquipments();
		model.addAttribute("equs",equs);
		return "viewEquipment.html";
	}

	@GetMapping("/edit/{ID}")
	public String goEditEquipment(Model model, @PathVariable int ID){
		Equipment equ = da.getEquipmentByID(ID);
		model.addAttribute("equ", equ);
		return "editEquipment.html";
	}
	
	@GetMapping("/edit")
	public String editViewEquipment(Model model, @ModelAttribute Equipment equ) {
		da.editEquipment(equ.getID(),equ);
		model.addAttribute("equs", da.getEquipments());
		return "redirect:/ViewEquipments";
	}
	
	@GetMapping("/delete/{ID}")
	public String deleteViewEquipment(Model model, @PathVariable int ID){
		da.deleteEquipment(ID);
		return "redirect:/ViewEquipments";
	}
	

	
	//---------------------------------------------------------search---------------------------------------------------
	@GetMapping("/goSearch")
	public String goSearch() {
		return "search.html";
	}
	
	@GetMapping("/SearchByName")
	public String search(Model model,@RequestParam String name) {
		ArrayList<Equipment> equList=da.searchByName(name);
		model.addAttribute("equs", equList);
		return "viewEquipment.html";
	}
	
	@GetMapping("/SearchByPrice")
	public String searchByPrice(Model model,@RequestParam double minPrice,@RequestParam double maxPrice) {
		ArrayList<Equipment> equList=da.searchByPrice(minPrice,maxPrice);
		model.addAttribute("equs", equList);
		return "viewEquipment.html";
	}
	
	@GetMapping("/SearchByQuantity")
	public String searchByQuantity(Model model,@RequestParam int minQuantity,@RequestParam int maxQuantity) {
		ArrayList<Equipment> equList=da.searchByQuantity(minQuantity,maxQuantity);
		model.addAttribute("equs", equList);
		return "viewEquipment.html";
	}
	
	//--------------------------------------------------------Email-------------------------------------------------
	@GetMapping("/goSendEmail")
	public String goSendEmailPage() {
		return "sendEmail.html";
	}
	
	@GetMapping("/sendEmail")
	public String sendMessage(Model model,@RequestParam String email) {
		//esi.sendSimpleMsg("frankfang111@gmail.com","Test Mail","This is a Test Mail.");
		try {
			
			esi.sendDynamicMsg(email,"Equipment Information","Footer!");
		} catch (MessagingException e) {
			System.out.println(e);
		}
		
		return "emailSuccess.html";
	}
}
