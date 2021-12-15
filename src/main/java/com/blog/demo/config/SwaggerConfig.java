package com.blog.demo.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger2配置類
 * 在與spring boot整合時，放在與Application.java同級的目錄下。
 * 通過@RestController註解，讓Spring來載入該類配置。
 * 再通過@EnableSwagger2註解來啟用Swagger2。
 */


@Configuration
public class SwaggerConfig {
	
	public static final String AUTHORIZATION_HEADER = "Authorization";
	
	private ApiKey apiKey() {
		return new ApiKey("JWT", AUTHORIZATION_HEADER, "header"); //產生ApiKey(key名字, key內容, 放在哪)
	}
	
	/*
	 * public ApiKey(String name, String keyname, String passAs) {
		    this(name, keyname, passAs, new ArrayList<VendorExtension>());
		}
	 */
	
	private ApiInfo apiInfo() {
		return new ApiInfo(
				"Spring Boot Blog REST APIs",
                "Spring Boot Blog REST API Documentation",
                "1",
                "Terms of service",
                new Contact("Tammy", "www.javaguides.net", "tammy@gmail.com"),
                "License of API",
                "API license URL",
                Collections.emptyList()
				
				);
	}
	
	
	/*
	 *
	public ApiInfo(
      String title,
      String description,
      String version,
      String termsOfServiceUrl,
      String contactName,
      String license,
      String licenseUrl) {
    this(title, description, version, termsOfServiceUrl, new Contact(contactName, "", ""), license, licenseUrl, new ArrayList<VendorExtension>());
  }

	 */
	
	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.securityContexts(Arrays.asList(securityContext()))
				.securitySchemes(Arrays.asList(apiKey()))
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
		
	}
	
	
	
	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).build();
	}
	
	
	private List<SecurityReference> defaultAuth(){
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");//(String scope, String description)
		//因為securityReferences要傳入List陣列,所以在此建立陣列, 雖然我們只有一個authentication
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1]; 
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
	}

}
