# Spring IoC控制反转 & DI依赖注入

*Author：Qin Zhenghan*

## 一、SpringIoC

**高内聚、低耦合**：首先设想在一家超市，如果所有商品没有分门别类，杂乱无章的摆放在一起，顾客很难从中挑出所要购买的商品。反之，若将不同种类的商品摆放在同一分区中（生鲜区、奶制品区、日用品区等），顾客可以很快找到中意的商品。再来解释名词，**内聚**：故名思议，表示内部间聚集、关联的程度，那么高内聚就是指要高度的聚集和关联，即上述将商品分门别类；**耦合**：是对模块间关联程度的度量。耦合的强弱取决与模块间接口的复杂性、调用模块的方式以及通过界面传送数据的多少。

- 降低耦合度的方法：
  - 少使用类的继承，多用接口隐藏实现的细节。 Java面向对象编程引入接口除了支持多态外， 隐藏实现细节也是其中一个目的。
  - 模块的功能化分尽可能的单一，道理也很简单，功能单一的模块供其它模块调用的机会就少。（其实这是高内聚的一种说法，高内聚低耦合一般同时出现）。
  - 遵循一个定义只在一个地方出现。
  - 少使用全局变量。
  - 类属性和方法的声明少用public，多用private关键字。
  - 多用设计模式，比如采用MVC的设计模式就可以降低界面与业务逻辑的耦合度。
  - 尽量不用“硬编码”的方式写程序，同时也尽量避免直接用SQL语句操作数据库。
  - 最后当然就是避免直接操作或调用其它模块或类（内容耦合）；如果模块间必须存在耦合，原则上尽量使用数据耦合，少用控制耦合，限制公共耦合的范围，避免使用内容耦合。
- 增强内聚度的方法：
  - 模块只对外暴露最小限度的接口，形成最低的依赖关系。
  - 只要对外接口不变，模块内部的修改，就不得影响其他模块。
  - 删除一个模块，应当只影响有依赖关系的其他模块，而不应该影响其他无关部分。



**控制反转**（Inversion of Control，**IoC**），是Spring的核心概念，用来消减计算机程序的耦合性，从而很好的体现类面向对象设计法则之——**好莱坞法则**：“别找我们，我们找你”，即由IoC容器帮对象找相应的依赖对象并注入，而不是由对象主动去找。举个栗子，当干饭人控制不住自己的时候，就会去觅食，在古代，最普遍的情况就是“自己动手丰衣足食”，食物需要主动制作；而现在，只需在美团APP中相应的店铺中选中自己想吃的美食、一键下单即可，无须自己动手就可以无忧无虑的干饭了。再举个栗子，在传统编程模式下，程序媛通常会采用“ **new **被调用者”的代码方式来创建对象，这种方式会增加调用者与被调用者之间的耦合性，不利于后期代码的升级与维护。

在Spring里，对象的实例不再由调用者来创建，而是由Spring容器来创建，Spring容器会负责控制程序之间的关系，而不是由程序媛的代码直接控制。这样，控制权就由调用者转移到Spring容器，**控制权发生了反转**，这就是Spring的控制反转。



## 二、DI

**依赖注入**（Dependency Injection，**DI**）组件之间依赖关系由容器在运行期决定，形象的说，即由容器动态的将某个依赖关系注入到组件之中。依赖注入的目的并非为软件系统带来更多功能，而是为了提升组件重用的频率，并为系统搭建一个灵活、可扩展的平台。通过依赖注入机制，我们只需要通过简单的配置，而无需任何代码就可指定目标需要的资源，完成自身的业务逻辑，而不需要关心具体的资源来自何处，由谁实现。



## 三、例子

### 1.环境配置

**IDE：IntelliJ IDEA**

![image-20210517202047541](C:\Users\秦政瀚\AppData\Roaming\Typora\typora-user-images\image-20210517202047541.png)

`新建项目` —> `Maven` —> `Next`

![image-20210517202449338](C:\Users\秦政瀚\AppData\Roaming\Typora\typora-user-images\image-20210517202449338.png)

`GroupId: 个人、企业域名` —> `ArtifactId：项目名称` —> 点击`Finish`

**项目大致架构**

![image-20210517230616759](C:\Users\秦政瀚\AppData\Roaming\Typora\typora-user-images\image-20210517230616759.png)

pom.xml为Maven工程的配置文件，双击进入，添加以下依赖（为了方便添加依赖，直接导入了springboot的一些依赖包）。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.2.10.RELEASE</version>
        <relativePath/>
    </parent>
    <groupId>cn.zjutleo</groupId>
    <artifactId>springIoC</artifactId>
    <version>1.0-SNAPSHOT</version>

    <dependencies>
        <!-- Spring核心框架 -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>5.3.2</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-beans</artifactId>
            <version>5.3.3</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.3.3</version>
        </dependency>

        <!-- web项目依赖 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- Lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <!-- Junit -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
    </dependencies>

</project>
```

![image-20210517232057446](C:\Users\秦政瀚\AppData\Roaming\Typora\typora-user-images\image-20210517232057446.png)

添加以上依赖后，Maven工程会自动下载这些依赖，如果没有自动下载，可点击右侧工具栏的`Maven`，点击如图刷新图标。默认会从此链接仓库下载依赖包[Maven Repository: Search/Browse/Explore (mvnrepository.com)](https://mvnrepository.com/)，如果嫌弃下载太慢，可自行配置镜像库。

- spring-boot-starter-web依赖导入了web项目所需的大多数依赖包（具体依赖包可一路Ctrl+单击父工程回溯查看）。
- Lombok是一款Java开发插件，主要用于简化实体类的编写，如通过`@Data`注解可自动实现setter、getter等方法。
- Junit用作单元化测试。

### 2.主体代码

首先在src根目录下根据域名创建包名，如我的域名为zjutleo.cn，则创建cn.zjutleo包。然后创建dao层，创建UserDao接口类：

```java
package cn.zjutleo.dao;

/**
 * @author : Qin Zhenghan
 * @date : Created in 2021/5/17
 * @description: 用户DAO接口类
 */
public interface UserDao {
    void addUser();
}
```

UserDaoImpl实现类：

```java
package cn.zjutleo.dao;

import lombok.extern.slf4j.Slf4j;

/**
 * @author : Qin Zhenghan
 * @date : Created in 2021/5/17
 * @description: 用户DAO实现类
 */
@Slf4j
public class UserDaoImpl implements UserDao {

    @Override
    public void addUser() {
        log.info("添加一个用户...");
    }
}
```

新建Service层，创建UserService接口类：

```java
package cn.zjutleo.service;

/**
 * @author : Qin Zhenghan
 * @date : Created in 2021/5/17
 * @description: 用户服务接口类
 */
public interface UserService {
    void doSomething();
}
```

创建UserServiceImpl实现类：

```java
package cn.zjutleo.service;

import cn.zjutleo.dao.UserDao;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : Qin Zhenghan
 * @date : Created in 2021/5/17
 * @description: 用户服务实现类
 */
@Slf4j
public class UserServiceImpl implements UserService {

    /**
     * 需要注入的对象
     */
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void doSomething() {
        log.info("做了些什么...");
        userDao.addUser();
    }
}
```

以上创建了dao层和service层的接口类及其实现类，其中service层的操作依赖于dao层（但并未在service层中new一个dao层对象），下面通过spring的IoC容器创建并注入这些类。在src根目录下创建spring配置文件applicationContext.xml，代码如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        ">

    <!-- 声明userDao对象,由spring容器创建 -->
    <bean name="userDao" class="cn.zjutleo.dao.UserDaoImpl"/>

    <!-- 声明userService对象,由spring容器创建 -->
    <bean name="userService" class="cn.zjutleo.service.UserServiceImpl">
        <!-- 通过set方法注入上面声明的userDao对象 -->
        <property name="userDao" ref="userDao"/>
    </bean>

</beans>
```

xml文件中，我们需要声明一个`beans`顶级标签，同时需要引入核心命名空间，spring的功能在使用时都需要声明相应的命名空间。然后通过`bean`子标签声明那些需要IoC容器创建注入的类，其中`name`是指明IoC创建后该对象的名称，class则是告诉IoC这个类的完全限定名，IoC就会通过这组信息利用反射机制创建对应的类对象。`property`标签指向了刚才声明的userDao对象，它的作用是把userDao对象传递给userService实现类中的userDao属性，该属性必须拥有set方法才能注入成功，我们把这种往类（userService）对象中注入其他对象（userDao）的操作称为**依赖注入**，其中的`name`必须与userService实现类中的变量名称相同（映射）。通过这种方式spring容器可以成功知道我们需要创建哪些bean实例。

接着在test的java包下创建UserTest测试类：

```java
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import cn.zjutleo.pojo.User;
import cn.zjutleo.service.UserService;

/**
 * @author : Qin Zhenghan
 * @date : Created in 2021/5/17
 * @description: 测试类
 */
@Slf4j
public class UserTest {

    @Test
    public void testUserService() {
        // 加载xml配置文件
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        // 通过反射机制获取userService对象
        UserService userService = (UserService) applicationContext.getBean("userService");
        userService.doSomething();
    }
}
```

`@Slf4j`用于日志输出。通过ClassPathXmlApplicationContext去加载spring的配置文件，接着获取想要的实例bean并调用相应的方法执行。对于ClassPathXmlApplicationContext默认加载classpath路径下的文件，只需指明对应文件的classpath路径即可。

新建pojo包，创建User实体类：

```java
package cn.zjutleo.pojo;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author : Qin Zhenghan
 * @date : Created in 2021/5/17
 * @description: 用户实体类
 */
@Data
public class User {

    private String name;

    private List<String> hobbyList;

    private Set<String> sonSet;

    private Map<Integer,String> bookMap;

    private String[] array;
}
```

`@Data`注解自动完成setter、getter等方法。

除了上面对象注入，还可以注入简单值和Map、Set、List、数组等，简单值注入使用`property`的`value`属性。在applicationContext.xml中添加以下代码：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        ">

    <!-- 声明userDao对象,由spring容器创建 -->
    <bean name="userDao" class="cn.zjutleo.dao.UserDaoImpl"/>

    <!-- 声明userService对象,由spring容器创建 -->
    <bean name="userService" class="cn.zjutleo.service.UserServiceImpl">
        <!-- 通过set方法注入上面声明的userDao对象 -->
        <property name="userDao" ref="userDao"/>
    </bean>

    <bean id="user" scope="prototype" class="cn.zjutleo.pojo.User">
        <property name="name" value="Leo"/>
        <!-- 注入List -->
        <property name="hobbyList">
            <list>
                <value>干饭</value>
                <value>睡觉</value>
                <value>浪</value>
            </list>
        </property>
        <!-- 注入Set -->
        <property name="sonSet">
            <set>
                <value>SZB</value>
                <value>PCY</value>
            </set>
        </property>
        <!-- 注入Map -->
        <property name="bookMap">
            <map>
                <entry key="01" value="《霸道总裁爱上我》"/>
                <entry key="02" value="《让富婆爱上你的一百种方法》"/>
                <entry key="03" value="《九阴真经》"/>
            </map>
        </property>
        <!-- 注入Array -->
        <property name="array">
            <array>
                <value>Messi</value>
                <value>歪比巴卜</value>
            </array>
        </property>
    </bean>

</beans>
```

在UserTest测试类中补充对应测试方法：

```java
@Test
public void testAssemble() {
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    User user = (User) applicationContext.getBean("user");
    log.info(user.toString());
}
```

进行单元化测试一：

![image-20210518001331125](C:\Users\秦政瀚\AppData\Roaming\Typora\typora-user-images\image-20210518001331125.png)

点击绿色图标，即可启动项目，进行单元化测试，结果如下：

```
00:14:19.902 [main] DEBUG org.springframework.context.support.ClassPathXmlApplicationContext - Refreshing org.springframework.context.support.ClassPathXmlApplicationContext@351d0846
00:14:20.015 [main] DEBUG org.springframework.beans.factory.xml.XmlBeanDefinitionReader - Loaded 3 bean definitions from class path resource [applicationContext.xml]
00:14:20.038 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'userDao'
00:14:20.047 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'userService'
00:14:20.083 [main] INFO cn.zjutleo.service.UserServiceImpl - 做了些什么...
00:14:20.083 [main] INFO cn.zjutleo.dao.UserDaoImpl - 添加一个用户...


Process finished with exit code 0
```

进行单元化测试二:

![image-20210518001611916](C:\Users\秦政瀚\AppData\Roaming\Typora\typora-user-images\image-20210518001611916.png)

测试结果如下：

```
00:16:28.555 [main] DEBUG org.springframework.context.support.ClassPathXmlApplicationContext - Refreshing org.springframework.context.support.ClassPathXmlApplicationContext@351d0846
00:16:28.671 [main] DEBUG org.springframework.beans.factory.xml.XmlBeanDefinitionReader - Loaded 3 bean definitions from class path resource [applicationContext.xml]
00:16:28.698 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'userDao'
00:16:28.708 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'userService'
00:16:28.760 [main] INFO UserTest - User(name=Leo, hobbyList=[干饭, 睡觉, 浪], sonSet=[SZB, PCY], bookMap={1=《霸道总裁爱上我》, 2=《让富婆爱上你的一百种方法》, 3=《九阴真经》}, array=[Messi, 歪比巴卜])


Process finished with exit code 0
```



## 四、小结

1. 假设，跳过接口类，直接编写实现类，实现类是相对变化的，是极易受调用者所影响的。而利用接口类或者抽象类将高层模块（如一个类的调用者）与具体的被操作者（如一个具体类）隔离开，从而使具体类在发生变化时不至于对调用者产生影响。
2. 假设，是通过在UserServiceImpl类中new一个UserDao对象来封装业务层方法，若UserDao对象发生变动，势必会引发UserServiceImpl中封装的方法的变动，而且代码的复用性与可扩展性并不好。因此采用**DIP**思路设计软件系统更利于软件系统的迭代开发。