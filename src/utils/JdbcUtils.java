package utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Chenli
 * @time 2017/5/20 22:47
 */
public class JdbcUtils {


    private static DruidDataSource dataSource;

    static{
        dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/campusappcache?useUnicode=true&characterEncoding=utf8");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
    }

    /**
     * 创建表
     * @param cellWords
     * @return
     * @throws SQLException
     */
    public static String createTable(String[] cellWords) throws SQLException {
        DruidPooledConnection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        String tableName = UUIDUtils.getUUIDHex();
        StringBuilder table = new StringBuilder();
        for (int i = 0;i<cellWords.length;i++){
            table.append(cellWords[i]);
            if (i<cellWords.length-1){
                table.append("\tvarchar(50),\n");
            }else {
                table.append("\tvarchar(50)\n");
            }
        }
        String sql = "CREATE TABLE " + tableName+"("+
                        table.toString()+
                        ")";
        System.out.println(sql);
        statement.execute(sql);
        connection.close();

        return tableName;
    }

    /**
     * 查看表
     */
    public static List<String> showTables(String tableName) throws SQLException {
        DruidPooledConnection connection = dataSource.getConnection();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet colRet= metaData.getColumns(null, "%", "%", tableName);
        List<String> list = new ArrayList();
        while (colRet.next()){
            String columnName = colRet.getString("COLUMN_NAME");
            list.add(columnName);
        }
        return list;
    }
}
