package com.ems.StaffFlow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI employeeManagementOpenAPI() {
        OpenAPI openAPI = new OpenAPI();
        openAPI.info(new Info()
                .title("Employee Management System API")
                .description("Production-ready REST API for Managing Employees (CRUD, Soft Delete, Paginated Search)")
                .version("1.0.0")
                .contact(new Contact()
                        .name("Kareem Bahy")
                        .email("kareem.bahy24@gmail.com"))
                .license(new License()
                        .name("Apache 2.0")
                        .url("https://www.apache.org/licenses/LICENSE-2.0")));
        return openAPI;
    }
}
