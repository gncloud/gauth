package io.swagger.configuration;

import io.swagger.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-09-08T07:13:42.158Z")

@Configuration
public class SwaggerDocumentationConfig extends WebMvcConfigurerAdapter{

    @Autowired
    private TokenService tokenService;

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("gncloud auth server")
            .description("gncloud Authentication Server")
            .license("Apache 2.0")
            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
            .termsOfServiceUrl("")
            .version("1.0.0")
            .contact(new Contact("","", "support@gncloud.kr"))
            .build();
    }

    @Bean
    public Docket customImplementation(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                    .apis(RequestHandlerSelectors.basePackage("io.swagger.api"))
                    .build()
                .directModelSubstitute(org.joda.time.LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(org.joda.time.DateTime.class, java.util.Date.class)
                .apiInfo(apiInfo());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        TokenValidateInterceptor tokenValidateInterceptor = new TokenValidateInterceptor();
        tokenValidateInterceptor.setTokenService(tokenService);
        registry.addInterceptor(tokenValidateInterceptor);
        super.addInterceptors(registry);
    }
}
