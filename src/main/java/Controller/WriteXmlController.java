package Controller;

import Model.UserDataBean;
import XmlUtil.XmlManager;
import org.dom4j.Document;
import org.dom4j.Element;

public class WriteXmlController {

    public static void writeUserData(UserDataBean cache){
        Document document = XmlManager.getInstance().getDocument();
        Element root = document.addElement("root");
        for(String key: UserDataBean.USER_DATA_KEY){
            Element element = root.addElement(key);
            if(cache.userData.containsKey(key)){
                element.setText(cache.userData.get(key));
            }else{
                element.setText("");
            }
        }
        XmlManager.getInstance().generateXml(UserDataBean.USER_DATA_PATH);
    }

}
