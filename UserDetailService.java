package ca.sheridancollege.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ca.sheridancollege.database.DatabaseAccess;



/*
 * This class is used to get user data from database and this class will be used by SecurityConfig class
 */

@Service			//allow this class to be autowired in security config
public class UserDetailService implements UserDetailsService {
	
	@Autowired
	@Lazy
	private DatabaseAccess da;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		ca.sheridancollege.beans.User user=da.findUserAccount(username);
		
		if(user==null) {
			System.out.println("User "+username+" was not found");throw new UsernameNotFoundException("User "+username+ "was not found");
		}
		
		List<String>roleNames=da.getRoleByID(user.getUserID());
		
		List<GrantedAuthority>grantedList=new ArrayList<GrantedAuthority>();
		if(roleNames!=null) {
			for(String role:roleNames) {
				grantedList.add(new SimpleGrantedAuthority(role));
			}
		}
		
		UserDetails userDetails=(UserDetails)new User(user.getUserName(),user.getEncryptedPassword(),grantedList);
		return userDetails;
	}
}
