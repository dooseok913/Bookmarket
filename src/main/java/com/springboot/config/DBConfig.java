package com.springboot.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages ="com.springboot.repository.mybatis")
public class DBConfig {
    // my batis 환경 설정

    @Bean
    protected DataSource dataSource () {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("org.mariadb.jdbc.Driver");
        hikariConfig.setJdbcUrl("jdbc:mariadb://localhost:3307/learning1");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("1234");
        return  new HikariDataSource(hikariConfig);
    }
    @Bean
    public SqlSessionFactory sqlSessionFactory()  throws  Exception {
        // mybatis에서 사용할 sqlsession 빈 생성
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        sqlSessionFactoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver()
                        .getResources("classpath:/mappers/**/*.xml"));

        return sqlSessionFactoryBean.getObject();
    }




}
