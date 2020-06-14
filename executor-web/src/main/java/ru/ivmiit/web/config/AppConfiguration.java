package ru.ivmiit.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@EnableAsync
@PropertySources({
        @PropertySource(value = "classpath:executor-web.properties"),
        @PropertySource(value = "file:${catalina.home}/conf/executor-web.properties", ignoreResourceNotFound = true)
})
public class AppConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/js/**")
                .addResourceLocations("/WEB-INF/classes/js/");
        registry.addResourceHandler("/css/**")
                .addResourceLocations("/WEB-INF/classes/css/");
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/WEB-INF/classes/resources/");
    }

    @Bean("threadPoolTaskExecutor")
    public TaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(1000);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setThreadNamePrefix("Async-");
        return executor;
    }

}
