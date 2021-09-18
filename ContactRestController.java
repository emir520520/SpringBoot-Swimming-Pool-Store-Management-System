package ca.sheridancollege.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.sheridancollege.beans.Toy;
import ca.sheridancollege.database.DatabaseAccess;

@RestController
public class ContactRestController {
	@Autowired
	private DatabaseAccess da;
	
	/*
	 @GetMapping("/toys")
	 public List<Toy> getStudents(){
		return da.getToys();
	 }
	 

	 @PutMapping(value="/toys", headers= {"Content-type=application/json"})
		public String updateContact(@RequestBody List<Toy> toys){
			int deleteResult=da.deleteToys();
			da.resetIndex();
			int addResult=0;
			for(Toy toy : toys) {
				addResult+=da.addToy(toy);
			}
			return deleteResult+" records were deleted and "+addResult+" were added";
		}
	 
	 
	 @PostMapping(value="/toys", headers= {"Content-type=application/json"})
		public String addContact(@RequestBody Toy toy){
		 int result=da.addToy(toy);
			if(result>0) {
				return "Toy added successfully!";
			}else {
				return "Toy added failed!";
			}
		}
	 

	 @DeleteMapping(value="/toys", headers= {"Content-type=application/json"})
		public String deleteStudents() {
			int deleteResult =da.deleteToys();
			return deleteResult + " records are deleted ";
	}
	 

	 @GetMapping("/toys/{ID}")
	 public Toy getStudentByID(@PathVariable int ID){
		return da.getToyByID(ID);
	 }
	

	 @PutMapping(value="/toys/{id}",headers= {"Content-type=application/json"})
		public String updateStudentById(@PathVariable int id,@RequestBody Toy toy) {
			int updateResult = da.editToy(id,toy);
			return updateResult + " record is updated";
		}
	 

	 @DeleteMapping(value="/toys/{id}", headers= {"Content-type=application/json"})
		public String deleteStudentById(@PathVariable int id) {
			int deleteResult =da.deleteToy(id);
			return deleteResult + " record is deleted ";
			
		}
		
		*/
}
