package com.cdut;

import com.cdut.dao.common.repository.CommonRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.Filter;

/*
 ......................我佛慈悲......................
                        _oo0oo_
                       o8888888o
                       88" . "88
                       (| -_- |)
                       0\  =  /0
                     ___/`---'\___
                   .' \\|     |// '.
                  / \\|||  :  |||// \
                 / _||||| -卍-|||||- \
                |   | \\\  -  /// |   |
                | \_|  ''\---/''  |_/ |
                \  .-\__  '-'  ___/-. /
              ___'. .'  /--.--\  `. .'___
           ."" '<  `.___\_<|>_/___.' >' "".
          | | :  `- \`.;`\ _ /`;.`/ - ` : | |
          \  \ `_.   \_ __\ /__ _/   .-` /  /
      =====`-.____`.___ \_____/___.-`___.-'=====
                        `=---='

 ..................佛祖开光 ,永无BUG...................
*/

@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = "com.cdut", repositoryFactoryBeanClass = CommonRepositoryFactoryBean.class)
@ComponentScan(basePackages = "com.cdut")
@EnableScheduling
@EntityScan(basePackages = "com.cdut")
@EnableTransactionManagement
@EnableAsync
public class MeetRoomApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {

		SpringApplication.run(MeetRoomApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(MeetRoomApplication.class);
	}

	@Bean
	public Filter openEntityManagerInViewFilter() {
		return new OpenEntityManagerInViewFilter();
	}

}
