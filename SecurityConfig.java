package ca.sheridancollege.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private LoginAccessDeniedHandler accessDeniedHandler;
	
	@Override 
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable();
		http.headers().frameOptions().disable();
		
		http
			//What URLs are restricted to what user
			.authorizeRequests()
				//restrict URL's not HTML
				.antMatchers("/goSearch").hasAnyRole("BOSS","WORKER")
				.antMatchers("/goSendEmail").hasAnyRole("BOSS","WORKER")
				.antMatchers("/GoAddEquipment").hasRole("BOSS")
				.antMatchers("/goPurchaseToy").hasRole("CASHIER")
				.antMatchers("//edit/{ID}").hasRole("BOSS")
				.antMatchers("/delete/{ID}").hasRole("BOSS")
				//Add more antMatchers for restricted pages
				.antMatchers("/","/ViewEquipments","/access-denied","/images/**","/css/**","/js/**","/**","/h2-console/**").permitAll()
				.antMatchers("/h2-console/**").permitAll()
				.anyRequest().authenticated()
			//Define custom login page
			.and()
				.formLogin()
				.loginPage("/login")
				.permitAll()
			//Define logout page
			.and()
				.logout()
				.invalidateHttpSession(true)
				.clearAuthentication(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login?logout")
				.permitAll()
			//Define Unauthorized access
				.and()
					.exceptionHandling()
					.accessDeniedHandler(accessDeniedHandler);
					
					
	}
	
	/*
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance())
		.withUser("owner").password("owner").roles("OWNER")
		.and()
		.withUser("manager").password("manager").roles("MANAGER")
		.and()
		.withUser("employee").password("employee").roles("EMPLOYEE")
		.and()
		.withUser("admin").password("admin").roles("OWNER","MANAGER","EMPLOYEE");
	}
	*/
	
	@Autowired
	private UserDetailService userDetailsService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
}
