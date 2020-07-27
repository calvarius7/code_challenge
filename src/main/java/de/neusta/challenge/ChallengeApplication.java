package de.neusta.challenge;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class ChallengeApplication {

  public static void main(final String[] args) {

    final SpringApplication springApplication = new SpringApplication(ChallengeApplication.class);

    springApplication.run(args);
  }

  @Bean
  public Docket swaggerConfig() {
    return new Docket(DocumentationType.SWAGGER_2).groupName("api")
        .useDefaultResponseMessages(false).apiInfo(this.apiInfo()).select().paths(regex("/api/.*"))
        .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder().title("Neusta code challenge REST API").version("1.0").build();
  }
}
