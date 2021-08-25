package cn.crudapi.service.app;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.*;

@Configuration
@EnableSwagger2
public class Swagger2SpringBoot {
    @Bean
    public Docket caMetadataApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("元数据")
                .apiInfo(apiInfo())
                .select()
                .paths(caMetadataPaths())
                .build();
    }

    @Bean
    public Docket caBusinessApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("业务数据")
                .apiInfo(apiInfo())
                .select()
                .paths(caBusinessPaths())
                .build();
    }
    
    @Bean
    public Docket fileApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("文件管理")
                .apiInfo(apiInfo())
                .select()
                .paths(filePaths())
                .build();
    }
    
    @Bean
    public Docket userApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("用户")
                .apiInfo(apiInfo())
                .select()
                .paths(userPaths())
                .build();
    }
  
    @Bean
    public Docket captchaApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("验证码")
                .apiInfo(apiInfo())
                .select()
                .paths(captchaPaths())
                .build();
    }
    
    @Bean
    public Docket weixinApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("微信")
                .apiInfo(apiInfo())
                .select()
                .paths(weixinPaths())
                .build();
    }
    
    
    private Predicate<String> caMetadataPaths() {
        return regex("/api/metadata.*");
    }

    private Predicate<String> caBusinessPaths() {
        return regex("/api/business.*");
    }
    
    private Predicate<String> filePaths() {
        return regex("/api/file.*");
    }
    
    private Predicate<String> weixinPaths() {
        return regex("/api/weixin.*");
    }
    
    private Predicate<String> userPaths() {
        return regex("/api/user.*");
    }
    
    
    private Predicate<String> captchaPaths() {
        return regex("/api/captcha.*");
    }
    
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("crudapi增删改查接口零代码产品")
                .description("crudapi是crud+api组合，表示增删改查接口，是一款零代码产品。无需编程，通过配置元数据自动生成crud增删改查RESTful API，提供后台UI管理业务数据，支持Java SDK集成或Restful API集成。\n"
                		+ "使用crudapi可以告别枯燥无味的增删改查代码，让您更加专注业务，节约大量成本，从而提高工作效率。crudapi的目标是让处理数据变得更简单，所有人都可以免费使用！")
                .termsOfServiceUrl("https://crudapi.cn")
                .contact(new Contact("crudapi", "https://crudapi.cn", "admin@crudapi.cn"))
                .version("1.0.0")
                .build();
    }
}