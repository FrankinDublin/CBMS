package workbench.Dao;

import Utils.DBUtil;
import Utils.InsertUtil;
import Utils.SelectUtil;
import workbench.Domain.TicketSet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TicketSetDao {
    public List<TicketSet> selectTicketSetByOwnership(String ownerId){
        String sql = "select * from tbl_ticketset where ownerId=?";
        SelectUtil selectUtil = new SelectUtil();
        return selectUtil.selectByOneParam(sql,ownerId,new TicketSet());
    }

    public int insertTicketSet(TicketSet ticketSet) {
        String sql = "insert into tbl_ticketset(id,setInclude,price,ownerId) values(?,?,?,?)";
        return new InsertUtil().insertHelper(sql,ticketSet);
    }

    public List<TicketSet> selectAllSets() {
        String sql = "select a.id,b.name ownerId,a.setInclude,a.price from tbl_ticketset a join tbl_user b on a.ownerId=b.usercode";
        return new SelectUtil().selectAll(sql,new TicketSet());
    }

    public List<TicketSet> selectSetById(String id) {
        String sql = "select * from tbl_ticketset where id = ?";
        return new SelectUtil().selectByOneParam(sql,id,new TicketSet());
    }

    public int deleteSetByIds(String[] ids, String questions) {
        String sql = "delete from tbl_ticketset where id in "+questions;
        System.out.println("拼接语句："+sql);
        Connection conn = null;
        PreparedStatement ps = null;
        int result=0;
        try {
            conn= DBUtil.getConnection();
            ps=conn.prepareStatement(sql);
            int i=1;
            for(String s:ids){
                ps.setString(i++,s);
            }
            result = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public int updateSet(String setId, String setInclude, String setPrice) {
        String sql = "update tbl_ticketset set setInclude=?,price=? where id=?";
        Connection conn = null;
        PreparedStatement ps = null;
        int result=0;
        try {
            conn= DBUtil.getConnection();
            ps=conn.prepareStatement(sql);
            ps.setString(1,setInclude);
            ps.setString(2,setPrice);
            ps.setString(3,setId);
            result = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
}
