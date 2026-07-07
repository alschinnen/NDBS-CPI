import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.URLEncoder; 

def Message processData(Message message) {

    Logger log = LoggerFactory.getLogger(this.getClass());

    def props = message.getProperties();

    //encode POST Body as x-www-form-urlencoded
	
	
	def query = "client_id=bc0d8ce4-f21b-498e-8f5f-714b57fd66e1&client_secret=ab12e7a3-6ba2-4324-9607-00c994623be4&grant_type=refresh_token&refresh_token=aea51e87-4754-445e-8f45-47f25ff8066a"
    def body_keyvalue_string = query.split("&");

    def boolean first = true;
    def StringBuilder result = new StringBuilder();

    for (int i = 0; i < body_keyvalue_string.length; i++) {

         if (first)
            first = false;
         else
            result.append("&");

         result.append(URLEncoder.encode(body_keyvalue_string[i].split("=")[0], "UTF-8"));
         result.append("=");
         result.append(URLEncoder.encode(body_keyvalue_string[i].split("=")[1], "UTF-8"));

     }

     message.setBody(result.toString());
    
        
     def target_uri = props.get("targetURL");      
     def splitIdx = target_uri.indexOf("?");

     if (splitIdx != -1){

           message.setProperty('concurAddress',target_uri.substring(0,splitIdx));
           message.setProperty('concurQuery',target_uri.substring(splitIdx+1,target_uri.length()));

     } else {

           message.setProperty('concurAddress',target_uri);
           message.setProperty('concurQuery','');

     }


     message.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8"); 
     message.setHeader("Connection", "close");             

     return message;

}