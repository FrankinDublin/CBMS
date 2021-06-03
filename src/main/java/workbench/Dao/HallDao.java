package workbench.Dao;

import Utils.SelectUtil;
import workbench.Domain.Hall;

import java.util.List;

public class HallDao {
    public List<Hall> selectAllHalls(){
        String sql = "select * from tbl_hall";
        SelectUtil selectUtil = new SelectUtil();
        return selectUtil.selectAll(sql,new Hall());
    }
}
