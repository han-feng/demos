package demos.mybatis.dao;

import java.util.List;

import demos.mybatis.User;

public interface UserDao {

	User get(int id);

	List<User> find(User user);

	int save(User user);

	int update(User user);

}
