package evgenbot.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
@ComponentScan("evgenbot")
@EnableTransactionManagement
@PropertySource("hibernate.properties")
public class HibernateConfiguration {
    @Value("${ComboPooledDataSource.driver}")
    private String driver;
    @Value("${ComboPooledDataSource.user}")
    private String user;
    @Value("${ComboPooledDataSource.password}")
    private String password;
    @Value("${ComboPooledDataSource.jdbcUrl}")
    private String jdbcUrl;
    @Value("${show_sql}")
    private String showSql;

    @Bean("dataSource")
    public DataSource dataSource(){
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(driver);
            dataSource.setUser(user);
            dataSource.setPassword(password);
            dataSource.setJdbcUrl(jdbcUrl);
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        return dataSource;
    }

    @Bean("sessionFactory")
    public LocalSessionFactoryBean sessionFactory(){
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource());
        localSessionFactoryBean.setPackagesToScan("evgenbot.entity");
        return localSessionFactoryBean;
    }

    @Bean("transactionManager")
    public HibernateTransactionManager transactionManagerBean(){
        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
        hibernateTransactionManager.setSessionFactory(sessionFactory().getObject());
        return hibernateTransactionManager;
    }
}
