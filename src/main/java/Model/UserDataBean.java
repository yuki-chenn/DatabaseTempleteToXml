package Model;

import java.util.HashMap;
import java.util.Map;

public class UserDataBean {

    public static final String[] USER_DATA_KEY  = {"dbType","ip","port","username","password","dbName","tableName"};
    public static final String USER_DATA_PATH = "./userdata/data.xml";

    public Map<String,String> userData = null;

    public UserDataBean(){
        if(userData == null) userData = new HashMap<>();
    }
}
