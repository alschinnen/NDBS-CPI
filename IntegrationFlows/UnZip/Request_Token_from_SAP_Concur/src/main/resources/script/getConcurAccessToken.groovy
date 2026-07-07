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
    def client_id = props.get("ClientID");   
    def client_secret = props.get("ClientSecret");   
    def refresh_token = props.get("RefreshToken");   
    

    //encode POST Body as x-www-form-urlencoded
	
	
	def query = "client_id=" + client_id + "&client_secret=" + client_secret + "&grant_type=refresh_token&refresh_token=" + refresh_token
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