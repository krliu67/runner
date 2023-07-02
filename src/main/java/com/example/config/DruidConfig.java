package com.example.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
public class DruidConfig {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }

    //后台监控功能 :web.xml  ServletRegistrationBean
    //因为SpringBoot 内置了servlet容器，所以没有web.xml, 替代方法ServletRegistrationBean
    //Druid 数据源具有监控的功能，并提供了一个 web 界面方便用户查看，类似安装 路由器 时，人家也提供了一个默认的 web 页面。
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");

        HashMap<String, String> initParameter = new HashMap<>();
        //增加配置
        initParameter.put("loginUsername","admin");
        initParameter.put("loginPassword","123456");
        //允许谁可以访问
        initParameter.put("allow","");
        //deny：Druid 后台拒绝谁访问
        //initParams.put("rui", "192.168.1.20");表示禁止此ip访问
        //设置初始化参数
        bean.setInitParameters(initParameter);

        return bean;
    }

    //配置 Druid 监控 之  web 监控的 filter//WebStatFilter：用于配置Web和Druid数据源之间的管理关联监控统计
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());

        HashMap<String, String> initParameter = new HashMap<>();
        //这些东西不进行统计
        initParameter.put("exclusions","*.js,*.css,/druid/*");
        //设置初始化参数
        bean.setInitParameters(initParameter);
        return bean;

    }

}
