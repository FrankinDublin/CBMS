package workbench.Service;

import Settings.Dao.UserDao;
import Settings.Domain.User;
import workbench.Dao.PurchaseDao;
import workbench.Domain.Purchase;

public class PurchaseService {
    PurchaseDao purchaseDao=new PurchaseDao();
    UserDao userDao=new UserDao();
    public boolean purchase(Purchase purchase) {
        return purchaseDao.insertPurchase(purchase)==1;
    }

    public Boolean updateBalance(String usercode, String postBalance) {
        return userDao.updateBalance(usercode,postBalance)==1;
    }
}
