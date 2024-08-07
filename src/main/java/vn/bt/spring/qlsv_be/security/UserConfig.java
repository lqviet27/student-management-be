//package vn.bt.spring.qlsv_be.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class UserConfig {
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception{
//        http.authorizeHttpRequests(
//                configure -> configure
//                        .requestMatchers(HttpMethod.POST,"/api/student").permitAll()
//                        .requestMatchers(HttpMethod.GET,"/api/students").permitAll()
//                        .requestMatchers(HttpMethod.GET,"/api/student/{id}").permitAll()
//                        .requestMatchers(HttpMethod.PUT,"/api/student/{id}").permitAll()
////                        .requestMatchers(HttpMethod.DELETE,"/api/student/{id}").permitAll()
////                        .requestMatchers(HttpMethod.DELETE,"/student_detail/{id}").permitAll()
//        );
//        http.httpBasic(Customizer.withDefaults());
//        http.csrf(csrf -> csrf.disable  ());
//        return http.build();
//    }
//}
