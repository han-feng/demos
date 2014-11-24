package demos.mybatis;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import demos.mybatis.dao.UserDao;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public int addUser(User user) {
        return userDao.add(user);
    }

    @Override
    public List<User> findUser(User user) {
        return userDao.find(user);
    }

    @Override
    public User getUser(int id) {
        return userDao.get(id);
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public int updateUser(User user) {
        return userDao.update(user);
    }

}
