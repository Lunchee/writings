package com.github.lunchee.writings.command.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.github.lunchee.writings.command")
@EntityScan(basePackages = "com.github.lunchee.writings.command")
public class CommandConfiguration {
}
