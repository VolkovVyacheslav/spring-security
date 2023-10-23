package com.volkov.spring.security.configs;


import com.volkov.spring.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/authenticated/**").authenticated()
                .antMatchers("/only_for_admins/**").hasRole("ADMIN")
                .antMatchers("/read_profile/**").hasAuthority("READ_PROFILE")
                .and()
                .formLogin()
                .and()
                .logout().logoutSuccessUrl("/");
    }

// in memory
//    @Bean
//    public UserDetailsService users(){
//        UserDetails user = User.builder()
//                .username("user")
//                .password("{bcrypt}$2a$12$1nL9tbSyOA/Ys8.ifMx2DOSmFVcEypE2e95OK.MWIoH3AAJijogkS")
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("{bcrypt}$2a$12$1nL9tbSyOA/Ys8.ifMx2DOSmFVcEypE2e95OK.MWIoH3AAJijogkS")
//                .roles("ADMIN","USER")
//                .build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }

//    @Bean
//    public JdbcUserDetailsManager users(DataSource dataSource){
//        UserDetails user = User.builder()
//                .username("user")
//                .password("{bcrypt}$2a$12$1nL9tbSyOA/Ys8.ifMx2DOSmFVcEypE2e95OK.MWIoH3AAJijogkS")
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("{bcrypt}$2a$12$1nL9tbSyOA/Ys8.ifMx2DOSmFVcEypE2e95OK.MWIoH3AAJijogkS")
//                .roles("ADMIN","USER")
//                .build();
//        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
//        if(!users.userExists(user.getUsername())) {
//            users.createUser(user);
//        }
//        if(!users.userExists(admin.getUsername())) {
//            users.createUser(admin);
//        }
//        return users;
//    }

    @Bean
    public PasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder();
      }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userService);
        return authenticationProvider;
    }
}
