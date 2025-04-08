## 1、sentinel 组件接入
### 1.1、SpringBoot 项目配置
#### 1.1.1、引入 maven 依赖
```xml
<dependency>
    <groupId>com.belle.cloud</groupId>
    <artifactId>belle-sentinel-boot-starter</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```
> 流量哨兵组件依赖sentinel-core最低版本为`1.8.7`，检查自己工程有没有被低版本的替代

#### 1.1.2 配置文件
> 以下默认无需配置
```yaml
belle:
  sentinel:
    enable: true
```

### 1.2、SpringMvc 项目配置
#### 1.2.1、引入 maven 依赖
```xml
<dependency>
    <groupId>com.belle.cloud</groupId>
    <artifactId>belle-sentinel-springmvc</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```
> 流量哨兵组件依赖sentinel-core最低版本为`1.8.7`，检查自己工程有没有被低版本的替代

#### 1.2.2 配置文件
> 以下默认无需配置
```yaml
belle:
  sentinel:
    enable: true
```

### 1.3、配置 Dubbo 组件
`目前 Dubbo 有多个分支，要按工程的版本引入不同组件`

#### 1.3.1、Alibaba Dubbo-2.6.x
```xml
<dependency>
    <groupId>com.alibaba.csp</groupId>
    <artifactId>sentinel-dubbo-adapter</artifactId>
    <version>1.8.7</version>
</dependency>
```

#### 1.3.2、Apache Dubbo 2.7.x
```xml
<dependency>
    <groupId>com.alibaba.csp</groupId>
    <artifactId>sentinel-apache-dubbo-adapter</artifactId>
    <version>1.8.7</version>
</dependency>
```

#### 1.3.3、Apache Dubbo 3.0.x
```xml
<dependency>
    <groupId>com.alibaba.csp</groupId>
    <artifactId>sentinel-apache-dubbo3-adapter</artifactId>
    <version>1.8.7</version>
</dependency>
```

#### 1.3.4 Dubbo 使用注意事项
dubbo sdk 默认会在每个方法加上资源保护，如果需在在方法内部自定义资源保护，参考：
```java
public String dubboResourceF() {
    Entry entry;
    try {
        entry = SphU.entry("dubboResourceF");
    } catch (BlockException e) {
        // 限流异常处理
    }  finally {
        if (entry != null) {
            entry.exit();   // 退出代码注意不能遗漏，会影响整个方法调用
        }
    }
    
    // 或者使用 try-resource 语法
    try (Entry entry = SphU.entry("dubboResourceG")) {
        // 被保护的资源   
    } catch (BlockException e) {
        // 限流异常处理
    }
}
```

### 1.4 更多组件待续...

## 2、默认拦截消息
默认的拦截消息如下:
```json
{
  "code": 429,
  "data": null,
  "message": "Blocked by Sentinel (flow limiting)"
}
```
#### 2.1 自定义默认拦截消息
> 如需自定义默认拦截消息，需要自行实现接口`com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler`  
> 创建目录`resource/META-INF/services`，然后创建文件`com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler`  
> 文件的内容为实现`BlockExceptionHandler`接口的完整类名。（Java SPI 机制）
 
#### 2.2 自定义拦截消息
> 如果需要自定义单个资源的拦截消息，配置注解`@SentinelResource`的`blockHandler`，返回自定义消息：
```java
@SentinelResource(value = "getUserName", blockHandler = "blockHandlerForGetUserName")
public String getUserName(String userNo) {
    return "hello " + userNo;
}

public String blockHandlerForGetUserName(String userNo, BlockException ex) {
    return "block " + userNo;
}
```

## 3、控制台使用说明
### 3.1、资源管理
![资源管理](./assets/res1.jpg)

## 4、注意事项
1. `belle-fastapi-client-starter` 版本需要大于 `2.0.0-SNAPSHOT` 否则无法查询指标看板
