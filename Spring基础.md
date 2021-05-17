## 常用依赖
```xml
    <dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.3.3</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.13.1</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>RELEASE</version>
            <scope>test</scope>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.aspectj/aspectjweaver -->
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.9.4</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aop</artifactId>
            <version>5.3.3</version>
            <scope>compile</scope>
        </dependency>
    </dependencies>
```

## 自动装配需要导入的约束
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">

    <!--    开启注解的支持-->
    <context:annotation-config/>
    <!--    指定要扫描的包-->
    <context:component-scan base-package="zjut"/>

</beans>
```

## 注解说明
- @Autowired：自动装配通过类型。
    如果Autowired不能唯一自动装配上属性，则需要通过@Qualifier(value="xxx")
- @Nullable：字段标记了这个注解，说明这个字段可以为null；
- @Resource：自动装配通过名字、类型。
- @Component：组件，放在类上，说明这个类被Spring管理了，就是bean!    

## 属性如何注入
```java
@Component
public class User {

    //相当于<property name="name" value="秦政瀚"/>
    //@Value("秦政瀚")
    public String name;

    //也可将Value("秦政瀚")置于set方法上
    @Value("秦政瀚")
    public void setName(String name) {
        this.name = name;
    }
}

```

## 衍生的注解
@component有几个衍生注解，在web开发中，会按照mvc三层架构分层
- dao 【@Repository】
- serivice 【@Service】
- controller 【@Controller】
这四个注解功能都是一样的，都是代表将某个类注册到Spring中，装配！

## 作用域
```java
@Component
@Scope("singleton")
public class User {

    //相当于<property name="name" value="秦政瀚"/>
    //@Value("秦政瀚")
    public String name;

    //也可将Value("秦政瀚")置于set方法上
    @Value("秦政瀚")
    public void setName(String name) {
        this.name = name;
    }
}
```

## xml与注解
### xml与注解的最佳实践：
- xml用来管理bean；
- 注解只负责完成属性的注入；
- 在使用过程中，只需要注意一个问题：必须让注解生效，就需要开启注解的支持
```xml
<!--指定要扫描的包，这个包下的注解就会生效-->
<context:component-scan  base-package="zjut"/>
<context:annotation-config/>
```

## 使用Java的方式配置Spring
JavaConfig是Spring的一个子项目，在Spring之后成为了核心功能



## 整合MyBatis

步骤：

1. 导入相关jar包
   - junit
   - mybatis
   - mysql数据库
   - spring相关的
   - aop织入
   - mybatis-spring 【new】

2. 编写配置文件
3. 测试



## 回忆mybatis

1. 编写实体类
2. 编写核心配置文件
3. 编写接口
4. 编写Mapper.xml
5. 测试



## MyBatis-Spring



整合方式一：使用SqlSessionTemplate

步骤：

1. 编写数据源配置

2. sqlSessionFactory

3. sqlSessionTemplate

   - spring-dao.xml（可复用）

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:aop="http://www.springframework.org/schema/aop"
          xsi:schemaLocation="http://www.springframework.org/schema/beans
           https://www.springframework.org/schema/beans/spring-beans.xsd
           http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd">
   
       <!--DataSource: 使用Spring的数据源替换Mybatis的配置 c3p0 dbcp druid
       我们这里使用Spring提供的JDBC
       -->
       <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
           <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
           <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=true&amp;useUnicode=true&amp;characterEncoding=UTF-8"/>
           <property name="username" value="root"/>
           <property name="password" value="330800abc"/>
       </bean>
   
       <!--sqlSessionFactory-->
       <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
           <property name="dataSource" ref="dataSource"/>
           <!--绑定MyBatis配置文件-->
           <property name="configLocation" value="classpath:mybatis-config.xml"/>
           <property name="mapperLocations" value="classpath:zjut/mapper/*.xml"/>
       </bean>
   
       <!--SqlSessionTemplate: 就是我们使用的sqlSession-->
       <bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
           <!--只能使用构造器注入sqlSessionFactory，因为它没有set方法-->
           <constructor-arg index="0" ref="sqlSessionFactory"/>
       </bean>
   
   </beans>
   ```

   - mybatis-config.xml

   ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE configuration
           PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
           "http://mybatis.org/dtd/mybatis-3-config.dtd">
   <!--configuration核心配置文件-->
   <configuration>
       
       <typeAliases>
           <package name="zjut.pojo"/>
       </typeAliases>
   
   </configuration>
   ```

4. 需要给接口加实现类 【new】

   ```java
   package zjut.mapper;
   
   import org.mybatis.spring.SqlSessionTemplate;
   import zjut.pojo.User;
   
   import java.util.List;
   
   /**
    * @author : Qin Zhenghan
    * @date : Created in 2021/2/8
    * @description: UserMapperImpl
    */
   public class UserMapperImpl implements UserMapper{
   
       //我们的所有操作，原来都使用sqlSession来执行，现在都使用sqlSessionTemplate
       private SqlSessionTemplate sqlSession;
   
       public void setSqlSession(SqlSessionTemplate sqlSession){
           this.sqlSession = sqlSession;
       }
   
       public List<User> selectUser() {
           UserMapper mapper = sqlSession.getMapper(UserMapper.class);
           return mapper.selectUser();
       }
   }
   ```

5. 将自己写的实现类，注入到Spring中

   - applicationContext.xml

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:aop="http://www.springframework.org/schema/aop"
          xsi:schemaLocation="http://www.springframework.org/schema/beans
           https://www.springframework.org/schema/beans/spring-beans.xsd
           http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd">
   
       <import resource="spring-dao.xml"/>
   
       <bean id="userMapper" class="zjut.mapper.UserMapperImpl">
           <property name="sqlSession" ref="sqlSession"/>
       </bean>
   </beans>
   ```

6. 测试使用即可

   ```java
   public class MyTest {
       @Test
       public void test() throws IOException {
           ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
           UserMapper userMapper = context.getBean("userMapper", UserMapper.class);
           for (User user : userMapper.selectUser()) {
               System.out.println(user);
           }
       }
   }
   ```



整合方式二：使用SqlSessionDaoSupport

配置步骤与方式一类似

1. UserMapperImpl2实现类

```java
public class UserMapperImpl2 extends SqlSessionDaoSupport implements UserMapper{
    public List<User> selectUser() {

//        SqlSession sqlSession = getSqlSession();
//        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
//        return mapper.selectUser();

        return getSqlSession().getMapper(UserMapper.class).selectUser();
    }
}
```

2. spring-dao.xml【不需要注入SqlSessionTemplate】

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd">

    <!--DataSource: 使用Spring的数据源替换Mybatis的配置 c3p0 dbcp druid
    我们这里使用Spring提供的JDBC
    -->
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=true&amp;useUnicode=true&amp;characterEncoding=UTF-8"/>
        <property name="username" value="root"/>
        <property name="password" value="330800abc"/>
    </bean>

    <!--sqlSessionFactory-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <!--绑定MyBatis配置文件-->
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
        <property name="mapperLocations" value="classpath:zjut/mapper/*.xml"/>
    </bean>

</beans>
```

3. applicationContext.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd">

    <import resource="spring-dao.xml"/>

    <!--整合mybatis方式二-->
    <bean id="userMapper2" class="zjut.mapper.UserMapperImpl2">
        <property name="sqlSessionFactory" ref="sqlSessionFactory"/>
    </bean>
</beans>
```

4. 测试



## 声明式事务

### 1、回顾事务

- 把一组业务当成一个业务来做，要么都成功，要么都失败
- 事务在项目开发中，十分重要，涉及到数据的一致性
- 确保完整性和一致性



事务的ACID原则：

- 原子性
- 一致性
- 隔离性
  - 多个业务可能操作同一个资源，防止数据损坏
- 持久性
  - 事务一旦提交，无论系统发生什么问题，结果都不会再被影响，被持久化的写到存储器中



### 2、spring中的事务管理

- 声明式事务：AOP

  ```xml
  <!--配置声明式事务-->
  <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
      <property name="dataSource" ref="dataSource"/>
  </bean>
  
  <!--结合AOP实现事务的织入-->
  <!--配置事务通知-->
  <tx:advice id="txAdvice" transaction-manager="transactionManager">
      <!--给哪些方法配置事务-->
      <!--配置事务的传播特性：REQUIRED-->
      <tx:attributes>
          <tx:method name="*" propagation="REQUIRED"/>
          <tx:method name="add*" propagation="REQUIRED"/>
          <tx:method name="delete*" propagation="REQUIRED"/>
          <tx:method name="update*" propagation="REQUIRED"/>
          <tx:method name="query*" read-only="true"/>
      </tx:attributes>
  </tx:advice>
  
  <!--配置事务切入-->
  <aop:config>
      <!--切入点-->
      <aop:pointcut id="txPointCut" expression="execution(* zjut.mapper.*.*(..))"/>
      <aop:advisor advice-ref="txAdvice" pointcut-ref="txPointCut"/>
  </aop:config>
  ```

- 编程式事务：需要在代码中，进行事务的管理



思考：

为什么需要事务？

- 如果不配置事务，可能存在数据提交不一致的情况
- 如果我们不在spring中去配置声明式事务，我们就需要在代码中手动配置事物
- 事物在项目开发中十分重要，涉及到数据的一致性和完整性