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
