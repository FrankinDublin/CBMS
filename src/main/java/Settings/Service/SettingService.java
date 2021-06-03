package Settings.Service;

import Settings.Dao.UserDao;
import vo.UserCommentInfo;
import vo.UserPurchaseInfo;
import workbench.Dao.MovieCommentDao;
import workbench.Dao.PurchaseDao;

import java.util.List;

public class SettingService {
    PurchaseDao purchaseDao=new PurchaseDao();
    MovieCommentDao movieCommentDao = new MovieCommentDao();
    UserDao userDao = new UserDao();
    public List<UserPurchaseInfo> loadBookInfo(String userId) {
       return purchaseDao.selectPurchaseByUserId(userId);
    }

    public List<UserCommentInfo> loadUserComments(String userId) {
        return movieCommentDao.selectCommentsByUid(userId);
    }

    public boolean charge(String usercode, String postBalance) {
        return userDao.updateBalance(usercode,postBalance)==1?true:false;
    }

    public String loadBalance(String usercode) {
        return userDao.selectUserBalanceById(usercode);
    }
}
