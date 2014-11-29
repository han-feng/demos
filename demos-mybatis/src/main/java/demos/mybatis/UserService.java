package demos.mybatis;

import java.util.List;

public interface UserService {

    User getUser(int id);

    List<User> findUser(User user);

    int addUser(User user);

    int updateUser(User user);

    int deleteUser(int id);

}
