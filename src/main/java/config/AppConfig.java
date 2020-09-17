package config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by CoT on 10/14/17.
 */
@Configuration
@EnableTransactionManagement
@EnableWebMvc
@ComponentScan({"controller", "service"})
public class AppConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("/WEB-INF/classes/static/");
    }


    @Bean
    public LocalSessionFactoryBean sessionFactory(){

        Properties properties = new Properties();
        //For Postgresql
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        //For mysql
        //properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.hbm2ddl.auto", "create-drop");

        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();

        sessionFactoryBean.setPackagesToScan("model");

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("postgres://ldssmhjwwwmpri:b5e9c9ff23b95d248650d0044e5342f95fc64c177b7e6d624aaa301a9d1dbf23@ec2-52-200-134-180.compute-1.amazonaws.com:5432/de2rb9845gu4g9");
        dataSource.setUsername("ldssmhjwwwmpri");
        dataSource.setPassword("b5e9c9ff23b95d248650d0044e5342f95fc64c177b7e6d624aaa301a9d1dbf23");

        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setHibernateProperties(properties);

        return sessionFactoryBean;
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory){
        HibernateTransactionManager tx = new HibernateTransactionManager(sessionFactory);
        return tx;
    }

}


