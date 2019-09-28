package com.keanu.cloud.auth.config;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

	private final Logger log = LoggerFactory.getLogger(OAuth2AuthorizationConfig.class);
	
	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private RedisConnectionFactory connectionFactory;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public TokenStore tokenStore() {
		return new RedisTokenStore(connectionFactory);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
				.withClient("browser")
				.authorizedGrantTypes("password", "refresh_token")
				.scopes("ui")
				.and()
				.withClient("account-service")
				.secret(env.getProperty("ACCOUNT_SERVICE_PASSWORD"))
				.authorizedGrantTypes("client_credentials", "refresh_token")
				.scopes("server");
		log.info("ClientDetailsServiceCOnfigurer 配置完成。");
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
			.tokenStore(tokenStore())
			.tokenServices(tokenServices())
			.authenticationManager(authenticationManager)
			.userDetailsService(userDetailsService);
		log.info("AuthorizationServerEndpointsConfigurer 配置完成。");
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security
			.tokenKeyAccess("permitAll()")
			.checkTokenAccess("isAuthenticated()")
			.passwordEncoder(passwordEncoder);
		log.info("AuthorizationServerSecurityConfigurer 配置完成。");
	}
	
	
	@Bean
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(true);
		defaultTokenServices.setAccessTokenValiditySeconds((int)TimeUnit.MINUTES.toSeconds(60));
		defaultTokenServices.setRefreshTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(1));
		return defaultTokenServices;
		
	}
	
	
	
}
