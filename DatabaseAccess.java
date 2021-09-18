package ca.sheridancollege.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.tree.RowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.beans.Equipment;
import ca.sheridancollege.beans.Store;
import ca.sheridancollege.beans.User;


@Repository
public class DatabaseAccess {
	
	@Autowired
	protected NamedParameterJdbcTemplate jdbc;
	
	DatabaseConfig dc=new DatabaseConfig(); 

	
	//-------------------------------------------Equipment-------------------------------------------------
	public ArrayList<Equipment> getEquipments() {
		String query= "Select * from equ_tb";
		ArrayList<Equipment> equs = (ArrayList<Equipment>)jdbc.query(query,new BeanPropertyRowMapper<Equipment>(Equipment.class));
		return equs;
	}
	
	
	public Equipment getEquipmentByID(int ID){
		MapSqlParameterSource parameters=new MapSqlParameterSource();
		String query="SElECT * FROM equ_tb WHERE ID=:ID";
		parameters.addValue("ID", ID);
		ArrayList<Equipment>equs=(ArrayList<Equipment>)jdbc.query(query, parameters, new BeanPropertyRowMapper<Equipment>(Equipment.class));

		if(equs.size()>0) {
			return equs.get(0);
		}else {
			return null;
		}
	}

	
	public int addEquipment(Equipment equ) {
		MapSqlParameterSource parameters=new MapSqlParameterSource();
		String  query="INSERT INTO equ_tb (name, price, quantity) VALUES(:name,:price,:quantity)";
		parameters.addValue("name",  equ.getName());
		parameters.addValue("price",  equ.getPrice());
		parameters.addValue("quantity",  equ.getQuantity());
		int result=jdbc.update(query, parameters);
		return result;
	}
	
	
	
	public int editEquipment(int ID,Equipment equ) {
		MapSqlParameterSource parameters=new MapSqlParameterSource();
		String  query="UPDATE equ_tb SET name=:name, price=:price, quantity=:quantity WHERE ID=:ID";
		parameters.addValue("ID", equ.getID());
		parameters.addValue("name",  equ.getName());
		parameters.addValue("price",  equ.getPrice());
		parameters.addValue("quantity",  equ.getQuantity());
		int result=jdbc.update(query, parameters);
		return result;
	}
	
	
	public int deleteEquipment(int ID){
		MapSqlParameterSource parameters=new MapSqlParameterSource();
		String query="DELETE FROM equ_tb  WHERE ID=:ID";
		parameters.addValue("ID", ID);
		int result=jdbc.update(query, parameters);
		return result;
	}
	
	public int deleteEquipments(){
		MapSqlParameterSource parameters=new MapSqlParameterSource();
		String query="DELETE FROM equ_tb";
		int result=jdbc.update(query, parameters);
		return result;
	}
	
	
	public void purchaseToys(int ID){
		MapSqlParameterSource parameters=new MapSqlParameterSource();
		String query="UPDATE toy_tb SET quantity=quantity-1 WHERE ID=:ID";
		parameters.addValue("ID", ID);
		jdbc.update(query, parameters);
	}
	
	public void resetIndex() {
		String query="ALTER TABLE toy_tb ALTER COLUMN ID RESTART WITH 1";
		jdbc.update(query, new HashMap());
	}
	
	
	//--------------------------------------------search--------------------------------------
	public ArrayList<Equipment> searchByName(String name){
		MapSqlParameterSource parameters=new MapSqlParameterSource();
		String query="SElECT * FROM equ_tb WHERE name=:name";
		parameters.addValue("name", name);
		ArrayList<Equipment> equs=(ArrayList<Equipment>)jdbc.query(query, parameters, new BeanPropertyRowMapper<Equipment>(Equipment.class));
		
		return equs;
	}
	
	
	public ArrayList<Equipment> searchByPrice(double minPrice,double maxPrice){
		MapSqlParameterSource parameters=new MapSqlParameterSource();
		String query="SElECT * FROM equ_tb WHERE price BETWEEN :minPrice AND :maxPrice";
		parameters.addValue("minPrice", minPrice);
		parameters.addValue("maxPrice", maxPrice);
		ArrayList<Equipment> equs=(ArrayList<Equipment>)jdbc.query(query, parameters, new BeanPropertyRowMapper<Equipment>(Equipment.class));
		
		return equs;
	}
	
	
	public ArrayList<Equipment> searchByQuantity(int minQuantity,int maxQuantity){
		MapSqlParameterSource parameters=new MapSqlParameterSource();
		String query="SElECT * FROM equ_tb WHERE quantity BETWEEN :minQuantity AND :maxQuantity";
		parameters.addValue("minQuantity", minQuantity);
		parameters.addValue("maxQuantity", maxQuantity);
		ArrayList<Equipment> equs=(ArrayList<Equipment>)jdbc.query(query, parameters, new BeanPropertyRowMapper<Equipment>(Equipment.class));
		
		return equs;
	}
	
	
	
	//-----------------------------------------------------Spring Security---------------------------------------------
		public User findUserAccount(String userName) {
			MapSqlParameterSource parameters=new MapSqlParameterSource();
			
			String query="SELECT * FROM SEC_USER WHERE userName=:name";
			parameters.addValue("name",userName);
			ArrayList<User>users=(ArrayList<User>)jdbc.query(query, parameters,new BeanPropertyRowMapper<User>(User.class));
			
			if(users.size()>0) {
				return users.get(0);
			}else {
				return null;
			}
		}
		
		public List<String> getRoleByID(long userID){
			MapSqlParameterSource parameters=new MapSqlParameterSource();
			String query="SELECT user_role.userID,sec_role.roleName FROM user_role, sec_role WHERE user_role.roleID=sec_role.roleID and userID=:userID";
			parameters.addValue("userID",userID);
			ArrayList<String>roles=new ArrayList<String>();
			List<Map<String,Object>> rows=jdbc.queryForList(query,parameters);
			for(Map<String,Object> row: rows) {
				roles.add((String)row.get("roleName"));
			}
			
			return roles;
		}
		
		@Autowired
		public BCryptPasswordEncoder passwordEncoder;
		
		public void addNewUser(String username,String password) {
			MapSqlParameterSource parameters=new MapSqlParameterSource();
			String query="INSERT INTO SEC_USER (userName,encryptedPassword,ENABLED) "+
					"VALUES (:username, :encryptedPassword,1)";
			parameters.addValue("username", username);
			parameters.addValue("encryptedPassword", passwordEncoder.encode(password));
			jdbc.update(query,parameters);
		}
		
		public void addUserRoles(long userID,int roleID) {
			MapSqlParameterSource parameters=new MapSqlParameterSource();
			String query="INSERT INTO USER_ROLE (userID,roleID) VALUES (:userID,:roleID)";
			parameters.addValue("userID", userID);
			parameters.addValue("roleID", roleID);
			jdbc.update(query,parameters);
		}
		
		
}
