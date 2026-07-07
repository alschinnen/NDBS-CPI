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

    def pmap = message.getProperties();

    def props = message.getProperties();

    def access_token = new String ("");

    def body_string = message.getBody(String.class);


    //extract Concur access_token

    def body_keyvalue_string = body_string.split(",");

    for (int i = 0; i < body_keyvalue_string.length; i++) {

        if (body_keyvalue_string[i].indexOf("access_token") != -1 ) {

            def access_token_field = body_keyvalue_string[i].split(":");
            if (access_token_field.length > 1) {
                access_token = access_token_field[1].replace("\"", "");

            }
         }
     }
 
     message.setProperty("ConcurToken", access_token)
     
     return message;

}