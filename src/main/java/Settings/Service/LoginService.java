package Settings.Service;

import Settings.Dao.UserDao;
import Settings.Domain.User;
import exception.Login.codeRepeatException;
import exception.Login.noSuchCodeException;
import exception.Login.wrongPwdException;

import java.util.List;

public class LoginService {
    private UserDao userDao=new UserDao();
    public User login(User user) throws Exception{
        List<User> userList = userDao.selectAllUsers();
        /*判断是否含该用户名*/
        boolean flag=false;
        User aim = new User();
        for(User t:userList){
            if(t.getUsercode().equals(user.getUsercode())){
                flag=true;
                aim=t;
            }
        }
        if(flag==false) throw new noSuchCodeException("该用户名未注册");
        if(!user.getPassword().equals(aim.getPassword())) throw new wrongPwdException("密码错误");
        return aim;
    }
    public void register(User user) throws Exception{
        System.out.println("Register方法");
        List<User> userList = userDao.selectAllUsers();
        for(User t:userList){
            if(t.getUsercode().equals(user.getUsercode())){
                throw new codeRepeatException("用户名重复");
            }
        }
        int result=userDao.InsertUser(user);
        if(result!=1) throw new Exception("注册失败，未知错误");
    }
}
