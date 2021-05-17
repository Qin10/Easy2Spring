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
