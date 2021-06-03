package Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import workbench.Domain.Condition;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class WebUtil {
    /*
     * 		此方法用于将多个request获取的parameter封装入一个对象中，便于调用
     * 		使用条件：
     * 			调用的方法参数只有一个String
     */
    public static void ParaToObject(HttpServletRequest request, Object object) {
        //注册驱动
        Class c=object.getClass();
        Field[] fields = c.getDeclaredFields();
        for(Field e:fields) {
            String Fieldname = e.getName();
            //获取方法名，set+属性名，首字母大写
            String Methodname = "set"+Fieldname.toUpperCase().charAt(0)+Fieldname.substring(1);
            //获取方法
            try {
                Method method=c.getDeclaredMethod(Methodname, String.class);
                method.invoke(object, request.getParameter(Fieldname));
            } catch (Exception e1) {
                //e1.printStackTrace();
            }

        }
    }
    /*
        将数据库查询的数据封装成对象（单个）
    */
    public static void SingleDaoToObject(ResultSet rs,Object object){
        //注册驱动
        Class c=object.getClass();
        Field[] fields = c.getDeclaredFields();
        for(Field e:fields) {
            String Fieldname = e.getName();
            //获取方法名，set+属性名，首字母大写
            String Methodname = "set"+Fieldname.toUpperCase().charAt(0)+Fieldname.substring(1);
            //获取方法
            try {
                Method method=c.getDeclaredMethod(Methodname, String.class);
                method.invoke(object, rs.getString(Fieldname));
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        }
    }
    /*
     * 根据初始sql语句与条件对象拼接出含条件的sql语句
     * 使用条件：1.初始sql语句不能有where和orderby
     *         2.查询条件只有不定长参数，没有其他
     * 例：
     *  传入语句：select * from tbl_movie
     *  传入参数，producer,movieType
     *  输出：select * from tbl_movie where producer like ? and movieType like ?
     * */
    public static String connectSqlStatement(String sql,Object object,int count){
        /*count为0，返回原语句*/
        if(count==0) return sql;
        /*count不为0的情况*/
        /*记录原count值*/
        int originalCount = count;
        StringBuffer sb = new StringBuffer(sql);
        /*当前语句：select * from tbl where */
        sb.append(" where ");
        Class c=object.getClass();
        Field[] fields = c.getDeclaredFields();
        for(Field e:fields) {
            String Fieldname = e.getName();
            //获取方法名，get+属性名，首字母大写
            String Methodname = "get"+Fieldname.toUpperCase().charAt(0)+Fieldname.substring(1);
            //获取方法
            try {
                /*object.get属性()*/
                Method method=c.getDeclaredMethod(Methodname);
                /*获取属性值，判断是否为空*/
                String value= (String) method.invoke(object);
                System.out.println(Methodname+":"+value);
                if(!value.equals("")){
                    /*当前数等于初始数，表示第一个参数，不加and*/
                    if(count==originalCount)
                        sb.append(Fieldname+" like ? ");
                    /*当前数小于初始数，表示第二个之后参数，加and*/
                    else
                        sb.append("and "+Fieldname+" like ? ");
                    count--;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return sb.toString();
    }
    /*
    * 将对象中的多个属性取出，为PreparedStatement赋值
    * 前提：属性顺序与Insert语句顺序相同
    * */
    public static void insertHelper(Object object, PreparedStatement ps){
        //注册驱动
        Class c=object.getClass();
        Field[] fields = c.getDeclaredFields();
        int i=1;
        for(Field e:fields) {
            String Fieldname = e.getName();
            //获取方法名，get+属性名，首字母大写
            String Methodname = "get"+Fieldname.toUpperCase().charAt(0)+Fieldname.substring(1);
            //获取方法
            try {
                Method method=c.getDeclaredMethod(Methodname);
                ps.setString(i++, (String) method.invoke(object));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
    /*将对象转为json对象，用printWriter输出*/
    public static void toJsonAndWrite(HttpServletResponse resp,Object o){
        ObjectMapper om = new ObjectMapper();
        String result="";
        try {
            result=om.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            resp.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*将condition对象转化为数组*/
    public static List<String> transferConditionIntoArray (Condition condition){
        List<String> result = new ArrayList<>();
        /*顺序要和Condition对象属性定义顺序相同*/
        if(!condition.getName().equals(""))
            result.add(condition.getName());
        if(!condition.getProducer().equals(""))
            result.add(condition.getProducer());
        if(!condition.getShowTime().equals(""))
            result.add(condition.getShowTime());
        if(!condition.getMovieType().equals(""))
            result.add(condition.getMovieType());
        return result;
    }
    /*用于in字段的问号生成*/
    public static String questionMarkSpawner(int count){
        StringBuffer sb = new StringBuffer("(");
        for(int i=0;i<count;i++){
            sb.append("?,");
        }
        String result = sb.substring(0,sb.length()-1);
        result+=")";
        return result;
    }
}
