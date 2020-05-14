package project.akhir.danapprentechteam3.login.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import project.akhir.danapprentechteam3.login.security.services.UserDetailsServiceImpl;
import project.akhir.danapprentechteam3.login.security.jwt.AuthEntryPointJwt;
import project.akhir.danapprentechteam3.login.security.jwt.AuthTokenFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		 securedEnabled = true,
		 jsr250Enabled = true,
		prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
    UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter()
	{
		return new AuthTokenFilter();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception
	{
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable()
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS).and()
			.authorizeRequests()
				.antMatchers("/api/auth/signup").permitAll()
				.antMatchers("/api/auth/**").permitAll()
				.antMatchers("/api/auth/confirmation-forgotPassword/{mobileNumber}/otp").permitAll()
				.antMatchers("/api/auth/confirmation-otp/{mobileNumber}/otp").permitAll()
				.antMatchers("/api/auth/confirmation-otp/**").permitAll()
				.antMatchers("/api/auth/signin").permitAll()
				.antMatchers("/api/auth/confirmation-account/{token}").permitAll()
				.antMatchers("/api/auth/signout").permitAll()
				.antMatchers("/api/auth/forgot-password").permitAll()
				.antMatchers("/api/auth/confirm-password/{token}").permitAll()
				.antMatchers("/api/auth/change-password").permitAll()
				.antMatchers("/api/provider/cek-paket").permitAll()
				.antMatchers("/api/transaksi/E-wallet").permitAll()
				.antMatchers("/api/transaksi/choice").permitAll()
				.antMatchers("/api/transaksi/virtual-account").permitAll()
				.antMatchers("/api/auth/reset-password-inapplication").permitAll()
				//"/signin"
				//http://localhost:8080/swagger-ui.html
			.antMatchers("/api/test/**").permitAll()
			.anyRequest().authenticated();

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**",
				"/swagger-ui.html", "/webjars/**");
	}

}
