package ru.practicum.explorewithme.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.practicum.explorewithme.stats.client.StatsClient;

@Configuration
public class StatsClientConfig {

    @Value("${stats.service.url}")
    private String statsServiceUrl;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public StatsClient statsClient(RestTemplate restTemplate) {
        return new StatsClient(restTemplate, statsServiceUrl);
    }
}