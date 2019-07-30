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
// 	        path=path.replace("file:", ""); //ȥ��file:  
// 	        path=path.replace("classes/", ""); //ȥ��class\  
// 	        path=path.substring(1); //ȥ����һ��\,�� \D:\JavaWeb...  
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
	 

