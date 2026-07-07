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

def Message processData(Message message) {
    Logger log = LoggerFactory.getLogger(this.getClass());
      		def pmap = message.getProperties();

         def props = message.getProperties();
         def body_string = message.getBody(String.class);
         message.setBody(body_string);
     
         def headers = message.getHeaders();         
         def target_uri = headers.get("targetURL");       
         def splitIdx = target_uri.indexOf("?"); 
         if (splitIdx != -1){
           message.setProperty('concurAddress',target_uri.substring(0,splitIdx));
	       message.setProperty('concurQuery',target_uri.substring(splitIdx+1,target_uri.length()));
	     } else {
	       message.setProperty('concurAddress',target_uri);
	       message.setProperty('concurQuery','');
	     }
                
         def target_access_token = headers.get("targetAuthorization");
         message.setHeader("Authorization", target_access_token);       
                
    return message;
}
