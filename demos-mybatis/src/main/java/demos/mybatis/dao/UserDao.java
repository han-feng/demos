package demos.mybatis.dao;

import java.util.List;

import demos.mybatis.User;

public interface UserDao {

    User get(int id);

    List<User> find(User user);

    int add(User user);

    int update(User user);

}
