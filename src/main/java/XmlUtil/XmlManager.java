package XmlUtil;

import Model.UserDataBean;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class XmlManager {

    private static XmlManager instance = null;

    private Document document;




    private XmlManager(){

    }

    public static XmlManager getInstance(){
        if(instance == null) instance = new XmlManager();
        return instance;
    }

    public Document getDocument(){
         document = DocumentHelper.createDocument();
         return document;
    }

    public void generateXml(String path){
        String fileName = path.substring(path.lastIndexOf('/') + 1);
        if(document == null){
            System.out.println("xml文件创建失败，document=null");
            return;
        }

        // 格式化模板
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");

        // 生成xml文件
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            XMLWriter writer = new XMLWriter(out, format);
            writer.write(document);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("生成xml文件失败。文件名【" + fileName + "】");
            return;
        }

        // 利用文件输出流输出到文件
        String pathPrefix = path.substring(0,path.lastIndexOf('/') + 1);
        File file = new File(pathPrefix);
        if(!file.exists()){
            file.mkdirs();
            System.out.println("创建文件夹:" + pathPrefix);
        }
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(out.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("xml文件写入失败。文件名【" + fileName + "】");
            return;
        }

        System.out.println("文件导出成功，位置：" + path);
    }

    public UserDataBean loadUserCacheXmlData(){
        UserDataBean userCache = new UserDataBean();
        String pathPrefix = UserDataBean.USER_DATA_PATH.substring(0,UserDataBean.USER_DATA_PATH.lastIndexOf('/') + 1);
        File parfile = new File(pathPrefix);
        if(!parfile.exists()){
            parfile.mkdirs();
            System.out.println("创建文件夹:" + pathPrefix);
        }
        File file = new File(UserDataBean.USER_DATA_PATH);
        Reader reader = null;
        StringBuilder sb = new StringBuilder("");
        try {
            reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
            int tempchar = 0;
            while ((tempchar = reader.read()) != -1) {
                sb.append((char) tempchar);
            }
            reader.close();

            SAXReader saxReader = new SAXReader();
            Document doc = saxReader.read(new StringReader(sb.toString()));
            Element rootElement = doc.getRootElement();

            for (String str: UserDataBean.USER_DATA_KEY) {
                userCache.userData.put(str,rootElement.elementText(str));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return userCache;

    }

}
