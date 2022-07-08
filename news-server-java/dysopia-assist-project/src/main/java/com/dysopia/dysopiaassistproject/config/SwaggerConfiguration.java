package com.dysopia.dysopiaassistproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author ：Abean
 * @date ：2022/6/23 17:11
 * @description ：TODO
 */

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(Docket.DEFAULT_GROUP_NAME)
                .enable(true)
                .apiInfo(apiInfo())  // 生成API信息
                .select()  // select()函数返回一个ApiSelectorBuilder实例,用来控制接口被swagger做成文档
                .apis(RequestHandlerSelectors.any())  // 指定扫描的接口
                .build();
    }

    /**
     * 用于定义API主界面的信息，比如可以声明所有的API的总标题、描述、版本
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("视障人士友好的咨询辅助软件")
                .description("视障人士友好的咨询辅助软件后端API接口文档")
                .termsOfServiceUrl("")
                .version("1.0")
                .build();
    }
}
