package com.easycms.cms.dao.mybatis;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.easycms.cms.annotation.Kernel;
import com.easycms.cms.annotation.Mapper;
import com.easycms.cms.utils.CmsAppUtils;

@Kernel
@EnableTransactionManagement
@ComponentScan("com.easycms.cms.dao.mybatis")
@MapperScan(basePackages="/",annotationClass=Mapper.class)
public class MyBatisKernel {

	@Bean
	@Scope("singleton")
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://server:3306/db_ceomall");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		return dataSource;
	}
	
	@Bean
	@Scope("singleton")
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(CmsAppUtils.getBean(DataSource.class));
		return factoryBean.getObject();
	}
	
	@Bean
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(CmsAppUtils.getBean(DataSource.class));
	}
	
}
