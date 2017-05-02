package com.example.client.oauth2.security.spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.stereotype.Controller;

import java.util.List;

@Configuration
public class SpringSecurityOauth2Config {
    // for twitter
/*    @Bean
    @ConfigurationProperties("security.oauth2.client")
    @Primary
    public ClientCredentialsResourceDetails oauth2RemoteResource() {
        return new ClientCredentialsResourceDetails();
    }*/

    // for salesforce
/*    @Bean
    @ConfigurationProperties("security.oauth2.client")
    @Primary
    public ResourceOwnerPasswordResourceDetails resourceOwnerPasswordResourceDetails() {
        return new ResourceOwnerPasswordResourceDetails();
    }*/

    // for salesforce
/*    @Bean
    public OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext oauth2ClientContext,
                                                 ResourceOwnerPasswordResourceDetails details) {
        return new OAuth2RestTemplate(details, oauth2ClientContext);
    }*/

    // for GitHub
    @Bean
    public OAuth2RestTemplate oauth2RestTemplateGithub(OAuth2ClientContext oauth2ClientContext,
                                                       OAuth2ProtectedResourceDetails details) {
        return new OAuth2RestTemplate(details, oauth2ClientContext);
    }
}
