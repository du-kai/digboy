package mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisBus {
	private static SqlSessionFactory sqlSessionFactory;
	 
    static {
        InputStream inputStream = null;
        try {
//        	String path=Thread.currentThread().getContextClassLoader().getResource("").toString();  
// 	        path=path.replace("file:", ""); //去掉file:  
// 	        path=path.replace("classes/", ""); //去掉class\  
// 	        path=path.substring(1); //去掉第一个\,如 \D:\JavaWeb...  
 	        String path="db-config.xml";  
        
            inputStream = Resources.getResourceAsStream(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }
    
    public static SqlSession getSession(){
    	 SqlSession session = sqlSessionFactory.openSession();
    	 return session;
    }
    
    
}
	 

