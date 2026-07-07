import com.sap.gateway.ip.core.customdev.util.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


def Message processData(Message message) {
    
    Logger log = LoggerFactory.getLogger(this.getClass());
    
    try {
            
   		def map = message.getProperties();
		def ex = map.get("CamelExceptionCaught");

        if (ex!=null) {
            if (ex.getClass().getCanonicalName().equals("org.apache.camel.component.ahc.AhcOperationFailedException")) {
                def responseBody = ex.getResponseBody();
                def statusText = ex.getStatusText();
                def statusCode = ex.getStatusCode();
                
                message.setBody(responseBody);
                
                if (statusCode in [301,302,307,308]){
                    def originalUrl = message.getHeader("Location",String);
                    def httpMethod = message.getHeader("CamelHttpMethod",String);
                    
                    if((originalUrl.indexOf("https") != -1) || (originalUrl.indexOf("http") != -1)){
                        message.setProperty('originalUrl',originalUrl);
                    } else if(message.getProperty("concurAddress")){
                        originalUrl = message.getProperty("concurAddress") + originalUrl;
                        message.setProperty('originalUrl',originalUrl);
                    } else{
                        throw new RuntimeException('Failed to retrive concur address');
                    }
                    
                } else{
                    message.setProperty('originalUrl','');
                }
            }
        }
        
    } catch (Exception ex) {
        
        def messageLog = messageLogFactory.getMessageLog(message);
        if(messageLog != null)
        {
            messageLog.addAttachmentAsString("http_handler exception", ex.getMessage(), "plain/text");
        }
    }
    
    return message;
}
