package Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertUtil {
    public int insertHelper(String sql,Object object){
        Connection conn=null;
        PreparedStatement ps=null;
        int result=0;
        try {
            conn= DBUtil.getConnection();
            ps=conn.prepareStatement(sql);
            WebUtil.insertHelper(object,ps);
            result=ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
}
