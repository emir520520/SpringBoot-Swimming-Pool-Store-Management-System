package ca.sheridancollege.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Equipment {
	
	private int ID;
	private String name;
	private double price;
	private int quantity;
	private String toyStore;
}
