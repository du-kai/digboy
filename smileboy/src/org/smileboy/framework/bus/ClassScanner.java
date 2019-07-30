package org.smileboy.framework.bus;
import java.io.File;  
import java.io.FilenameFilter;  
import java.io.IOException;  
import java.net.JarURLConnection;  
import java.net.URL;  
import java.util.ArrayList;
import java.util.Enumeration;  
import java.util.HashMap;  
import java.util.List;
import java.util.Map;  
import java.util.jar.JarEntry;  
import java.util.jar.JarFile;  

import org.smileboy.util.StringUtil;

/** 
 * @Author 
 * @Description ��ɨ���� 
 * @CopyRight  
 */  
public class ClassScanner{  
    private Map<String, Class<?>> classes           = new HashMap<String, Class<?>>();  
    private FilenameFilter        javaClassFilter;                                    // ���ļ�������,ֻɨ��һ����  
    private final String          CLASS_FILE_SUFFIX = ".class";                       // Java�ֽ����ļ���׺  
    private String                packPrefix;                                         // ��·����·��  
  
    public ClassScanner(){  
        javaClassFilter = new FilenameFilter(){  
            @Override  
            public boolean accept(File dir, String name){  
                // �ų��ڲ���  
                return !name.contains("$");  
            }  
        };  
    }  
  
    /** 
     * @Title: scanning 
     * @Description ɨ��ָ����(����) 
     * @param basePath �����ڵĸ�·�� 
     * @param packagePath Ŀ���·�� 
     * @return Integer ��ɨ�赽��������� 
     * @throws ClassNotFoundException 
     */  
    public Integer scanning(String basePath, String packagePath) throws ClassNotFoundException{  
        packPrefix = basePath;  
  
        String packTmp = packagePath.replace('.', '/');  
        File dir = new File(basePath, packTmp);  
  
        // �����ļ���  
        if(dir.isDirectory()){  
            scan0(dir);  
        }  
  
        return classes.size();  
    }  
  
    /** 
     * @Title: scanning 
     * @Description ɨ��ָ����, Jar�򱾵� 
     * @param packagePath ��·�� 
     * @param recursive �Ƿ�ɨ���Ӱ� 
     * @return Integer ������ 
     */  
    public Integer scanning(String packagePath, boolean recursive){  
        Enumeration<URL> dir;  
        String filePackPath = packagePath.replace('.', '/');  
        try{  
            // �õ�ָ��·�������е���Դ�ļ�  
            dir = Thread.currentThread().getContextClassLoader().getResources(filePackPath);  
            packPrefix = Thread.currentThread().getContextClassLoader().getResource("").getPath();  
            if(System.getProperty("file.separator").equals("\\")){  
                packPrefix = packPrefix.substring(1);  
            }  
  
            // ������Դ�ļ�  
            while(dir.hasMoreElements()){  
                URL url = dir.nextElement();  
                String protocol = url.getProtocol();  
  
                if("file".equals(protocol)){  
                    File file = new File(url.getPath().substring(1));  
                    scan0(file);  
                } else if("jar".equals(protocol)){  
                    scanJ(url, recursive);  
                }  
            }  
        }  
        catch(Exception e){  
            throw new RuntimeException(e);  
        }  
  
        return classes.size();  
    }  
  
    /** 
     * @Title: scanJ 
     * @Description ɨ��Jar��������class 
     * @param url jar-url·�� 
     * @param recursive �Ƿ�ݹ�����Ӱ� 
     * @throws IOException 
     * @throws ClassNotFoundException 
     */  
    private void scanJ(URL url, boolean recursive) throws IOException, ClassNotFoundException{  
        JarURLConnection jarURLConnection = (JarURLConnection)url.openConnection();  
        JarFile jarFile = jarURLConnection.getJarFile();  
  
        // ����Jar��  
        Enumeration<JarEntry> entries = jarFile.entries();  
        while(entries.hasMoreElements()){  
            JarEntry jarEntry = (JarEntry)entries.nextElement();  
            String fileName = jarEntry.getName();  
  
            if (jarEntry.isDirectory()) {  
                if (recursive) {  
                }  
                continue;  
            }  
              
            // .class  
            if(fileName.endsWith(CLASS_FILE_SUFFIX)){  
                String className = fileName.substring(0, fileName.indexOf('.')).replace('/', '.');  
                classes.put(className, Class.forName(className));  
            }  
  
        }  
    }  
  
    /** 
     * @Title: scan0 
     * @Description ִ��ɨ�� 
     * @param dir Java���ļ��� 
     * @throws ClassNotFoundException 
     */  
    private void scan0(File dir) throws ClassNotFoundException{  
        File[] fs = dir.listFiles(javaClassFilter);  
        for(int i = 0; fs != null && i < fs.length; i++){  
            File f = fs[i];  
            String path = f.getAbsolutePath();  
              
            // ���������ļ�  
            if(path.endsWith(CLASS_FILE_SUFFIX)){  
                String className = StringUtil.getPackageByPath(f, packPrefix); // ��ȡ����  
                classes.put(className, Class.forName(className));  
            }  
        }  
    }  
  
    /** 
     * @Title: getClasses 
     * @Description ��ȡ���������� 
     * @return Map&lt;String,Class&lt;?&gt;&gt; K:��ȫ��, V:Class�ֽ��� 
     */  
    public Map<String, Class<?>> getClasses(){  
        return classes;  
    }
    
    public List<Class<?>> getClassesList(){
    	List<Class<?>> list = new ArrayList<Class<?>>();
    	if(classes!=null){
    		for(Class<?> cls:classes.values()){
    			list.add(cls);
    		}
    	}
    	return list;
    }
}  