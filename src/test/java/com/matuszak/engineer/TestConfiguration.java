package com.matuszak.engineer;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:test.properties")
@Configuration
public class TestConfiguration {
}
