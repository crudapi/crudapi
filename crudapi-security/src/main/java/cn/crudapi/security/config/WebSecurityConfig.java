package cn.crudapi.security.config;

import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

import cn.crudapi.security.authentication.JwtAuthenticationFilter;
import cn.crudapi.security.authentication.JwtAuthorizationFilter;
import cn.crudapi.security.authentication.SmsCodeAuthenticationSecurityConfig;
import cn.crudapi.security.component.DynamicSecurityFilter;
import cn.crudapi.security.component.IgnoreUrlsConfig;
import cn.crudapi.security.service.ThirdAuthenticationService;
import cn.crudapi.security.component.CaWebAuthenticationDetails;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled =true)
public class WebSecurityConfig<S extends Session> extends WebSecurityConfigurerAdapter {
	private static final Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);
	
	public static String REMEMBEE_KEY = "69642b60e16c4083ab92dee057a8319f";
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;
	
	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Autowired
	private LogoutSuccessHandler logoutSuccessHandler;
	
	@Autowired
	private InvalidSessionStrategy invalidSessionStrategy;
	
	@Autowired
	private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;
	
	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
	private AccessDeniedHandler accessDeniedHandler;
	
	@Autowired
	private AuthenticationDetailsSource<HttpServletRequest, CaWebAuthenticationDetails> authenticationDetailsSource;
	
	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private FindByIndexNameSessionRepository<S> sessionRepository;

	@Autowired
	private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

	@Autowired(required = false)
	private ThirdAuthenticationService thirdAuthenticationService;
	
	@Autowired
	private IgnoreUrlsConfig ignoreUrlsConfig;
	
	@Autowired
	private DynamicSecurityFilter dynamicSecurityFilter;

	@Bean
	public Producer captcha() {
		Properties properties = new Properties();
		properties.setProperty("kaptcha.image.width", "150");
		properties.setProperty("kaptcha.image.height", "50");
		properties.setProperty("kaptcha.textproducer.char.string", "0123456789");
		properties.setProperty("kaptcha.textproducer.char.length", "4");
		
		Config config = new Config(properties);
		
		DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
		defaultKaptcha.setConfig(config);
		
		return defaultKaptcha;
	}
	
	@Bean
	public SpringSessionBackedSessionRegistry<S> sessionRegistry() {
		return new SpringSessionBackedSessionRegistry<>(this.sessionRepository);
	}
	
	@Bean
    public PersistentTokenRepository getPersistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl= new JdbcTokenRepositoryImpl();
        jdbcTokenRepositoryImpl.setDataSource(dataSource);
        return jdbcTokenRepositoryImpl;
    }   
	
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
		.antMatchers("/**/css/**", 
				"/**/js/**", 
				"/**/fonts/**", 
				"/**/statics/**",
				"/**/crudapi", "/**/crudapi/", 
				"/**/crudapi/**", 
				"/**/download/**",
				"/swagger-ui.html",
				"/swagger-ui.html/**",
				"/**/springfox-swagger-ui/**",
				"/**/swagger-resources/**",
				"/**/api-docs/**",
				"/favicon.ico");
		
		//不需要保护的资源路径允许访问
		for (String ignoreUrl : ignoreUrlsConfig.getUrls()) {
        	String[] ignoreUrlArray = ignoreUrl.split(",");
        	if (ignoreUrlArray.length > 1) {
        		log.info("web ignoring:[" + ignoreUrl + "]");
        		web.ignoring().antMatchers(ignoreUrlArray[0], ignoreUrlArray[1]);
        	} else {
        		log.info("web ignoring:[" + ignoreUrl + "]");
        		web.ignoring().antMatchers(ignoreUrl);
        	}
        }
	}
	
	@Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = null;
		List<SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>>  thirdSecurityConfigurerAdapters = null;
		
		if (thirdAuthenticationService != null) {
			thirdSecurityConfigurerAdapters = thirdAuthenticationService.getSecurityConfigurerAdapters();
		}
		
		httpSecurity.apply(smsCodeAuthenticationSecurityConfig);
		
		if (thirdSecurityConfigurerAdapters != null) {
			for (SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> thirdSecurityConfigurerAdapter : thirdSecurityConfigurerAdapters) {
				log.info("WebSecurityConfig Apply ThirdAuthenticationService: " + thirdSecurityConfigurerAdapter.getClass().getName());
				httpSecurity.apply(thirdSecurityConfigurerAdapter);
			}
		}
		registry = httpSecurity.authorizeRequests();

		
        //允许跨域请求的OPTIONS请求
        registry.antMatchers(HttpMethod.OPTIONS)
                .permitAll();
        
        registry
	        .antMatchers("/api/auth/**/login",
	        		"/api/auth/logout").permitAll()
	        .anyRequest().authenticated()
        .and()
	        .formLogin()
	        .authenticationDetailsSource(authenticationDetailsSource)
	        .loginProcessingUrl("/api/auth/login")
	        .successHandler(authenticationSuccessHandler)
	        .failureHandler(authenticationFailureHandler)
	        .permitAll()
        .and()
	        .logout()
	        .logoutUrl("/api/auth/logout")
	        .logoutSuccessHandler(logoutSuccessHandler)
        .and()
	        .addFilter(new JwtAuthenticationFilter(authenticationManager()
	        		, authenticationSuccessHandler,
	        		authenticationFailureHandler))
	        .addFilter(new JwtAuthorizationFilter(authenticationManager()))
	        .sessionManagement()
	        //.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        .invalidSessionStrategy(invalidSessionStrategy)
	        .maximumSessions(9999).maxSessionsPreventsLogin(false)
	        .expiredSessionStrategy(sessionInformationExpiredStrategy)
	        .sessionRegistry(sessionRegistry())
	        .and()
        .and()
        	.httpBasic()
        .and()
        	.csrf().disable()
        	.exceptionHandling()
        	.authenticationEntryPoint(authenticationEntryPoint)
        	.accessDeniedHandler(accessDeniedHandler)
        .and()
	        .rememberMe()
	        .userDetailsService(userDetailsService)
	        .tokenRepository(getPersistentTokenRepository())
	        .key(REMEMBEE_KEY)
	     .and().addFilterBefore(dynamicSecurityFilter, FilterSecurityInterceptor.class);
    }
	
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}
}
