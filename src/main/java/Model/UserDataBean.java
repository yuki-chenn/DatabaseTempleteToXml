package Model;

import XmlUtil.XmlManager;

import java.util.HashMap;
import java.util.Map;

public class UserDataBean {

    public static final String[] USER_DATA_KEY  = {"dbType","ip","port","username","password","dbName","tableName","path","excelPath"};
    public static final String USER_DATA_PATH = "./userdata/data.xml";

    public static Map<String,String> userData = XmlManager.getInstance().loadUserCacheXmlData();

}
