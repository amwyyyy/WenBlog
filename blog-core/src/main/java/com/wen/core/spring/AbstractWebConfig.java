package com.wen.core.spring;

import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import com.wen.core.spring.interceptor.AccessOriginInterceptor;
import com.wen.core.spring.interceptor.ActionInfoInterceptor;
import com.wen.core.spring.interceptor.MdcLogInterceptor;
import com.wen.core.spring.version.CustomRequestMappingHandlerMapping;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

/**
 * spring mvc 配置类
 *
 * @author denis.huang
 * @since 2017/3/9
 */
@ComponentScan(basePackages = "com.wen.core")
@EnableAspectJAutoProxy
public abstract class AbstractWebConfig extends DelegatingWebMvcConfiguration {

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    // 下载文件返回ResponseEntity<byte[]>
    converters.add(new ByteArrayHttpMessageConverter());

    // @ResponseBody 转换成Json格式返回
    FastJsonHttpMessageConverter4 converter = new FastJsonHttpMessageConverter4();
    converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON,
        MediaType.APPLICATION_JSON_UTF8, MediaType.APPLICATION_XML, MediaType.TEXT_HTML));

    FastJsonConfig config = new FastJsonConfig();
    config.setCharset(Charset.forName("UTF-8"));
    config.setDateFormat("yyyy-MM-dd HH:mm:ss");
    config.setFeatures(Feature.DisableCircularReferenceDetect);
    converter.setFastJsonConfig(config);

    converters.add(converter);
  }

  /**
   * 添加拦截器
   *
   * @param registry
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new MdcLogInterceptor());
    registry.addInterceptor(new AccessOriginInterceptor());
    registry.addInterceptor(new ActionInfoInterceptor());
  }

  /**
   * 自定义requestMappingHandlerMapping
   *
   * @return
   */
  @Override
  public RequestMappingHandlerMapping requestMappingHandlerMapping() {
    RequestMappingHandlerMapping handlerMapping = new CustomRequestMappingHandlerMapping();
    handlerMapping.setOrder(0);
    handlerMapping.setInterceptors(getInterceptors());
    return handlerMapping;
  }

  /**
   * 文件上传
   *
   * @return
   */
  @Bean(name = "multipartResolver")
  public CommonsMultipartResolver commonsMultipartResolver() {
    CommonsMultipartResolver resolver = new CommonsMultipartResolver();
    resolver.setDefaultEncoding("UTF-8");
    return resolver;
  }

  /**
   * spring 参数验证处理器
   *
   * @return
   */
  @Bean
  public MethodValidationPostProcessor methodValidationPostProcessor() {
    MethodValidationPostProcessor processor = new MethodValidationPostProcessor();

    LocalValidatorFactoryBean factoryBean = getApplicationContext().getBean(LocalValidatorFactoryBean.class);
    processor.setValidator(factoryBean.getValidator());
    return processor;
  }

  /**
   * 初始化spring validatorFactoryBean
   *
   * @return
   */
  @Bean
  public LocalValidatorFactoryBean localValidatorFactoryBean() {
    LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
    // 使用hibernate的validator实现
    factoryBean.setProviderClass(HibernateValidator.class);
    return factoryBean;
  }
}
