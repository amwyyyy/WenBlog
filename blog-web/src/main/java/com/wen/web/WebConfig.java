package com.wen.web;

import com.wen.core.spring.AbstractWebConfig;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

/**
 * springmvc配置
 *
 * @author denis.huang
 * @since 2017/3/9
 */
@Configuration
@ComponentScan(basePackages = "com.wen.web.controller", includeFilters = {
    @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Controller.class)
})
@EnableAsync
public class WebConfig extends AbstractWebConfig {
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // 不拦截静态资源
    registry.addResourceHandler("/").addResourceLocations("**.html");
    registry.addResourceHandler("/static/").addResourceLocations("/static/**");
  }

  /**
   * 初始化spring validatorFactoryBean
   *
   * @return
   */
  @Override
  @Bean
  public LocalValidatorFactoryBean localValidatorFactoryBean() {
    LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
    // 使用hibernate的validator实现
    factoryBean.setProviderClass(HibernateValidator.class);
    return factoryBean;
  }

  /**
   * spring 参数验证处理器
   *
   * @return
   */
  @Override
  @Bean
  public MethodValidationPostProcessor methodValidationPostProcessor() {
    MethodValidationPostProcessor processor = new MethodValidationPostProcessor();

    LocalValidatorFactoryBean factoryBean = getApplicationContext().getBean(LocalValidatorFactoryBean.class);
    processor.setValidator(factoryBean.getValidator());
    return processor;
  }
}
