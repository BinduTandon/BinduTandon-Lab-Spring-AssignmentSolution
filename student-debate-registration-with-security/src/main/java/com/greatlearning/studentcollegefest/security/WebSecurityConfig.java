package com.greatlearning.studentcollegefest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.greatlearning.studentcollegefest.service.MyUserDetailsService;

@EnableWebSecurity

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	MyUserDetailsService service;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(service);

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/h2-console", "/h2-console/**");

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests()
				.antMatchers("/list", "/deleteStudent", "/showFormForAdd", "/save", "/showFormForUpdate")
				.hasRole("ADMIN").antMatchers("/list", "/save", "/showFormForUpdate").hasAnyRole("ADMIN", "USER")
				.anyRequest().authenticated().and().formLogin().and().logout().and().exceptionHandling()
				.accessDeniedPage("/403").and().cors().and().csrf().disable();

	}

}
