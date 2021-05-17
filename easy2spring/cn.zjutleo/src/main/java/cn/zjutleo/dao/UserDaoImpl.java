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
