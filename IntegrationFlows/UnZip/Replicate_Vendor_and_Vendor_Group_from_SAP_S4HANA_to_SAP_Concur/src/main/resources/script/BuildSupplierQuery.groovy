import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import com.sap.it.api.msglog.MessageLogFactory;
import groovy.util.XmlSlurper;

def Message processData(Message message) {
	
	def messageLog = messageLogFactory.getMessageLog(message);
	def body = message.getBody(java.lang.String) as String;
	def count = 0;
	def supplierList = new XmlSlurper().parseText(body);
	def supplierQuery = '';
	def prop = message.getProperties();

	
	

    supplierList.Vendor.each{
        entry ->
       
        if (count == 0){
	        supplierQuery = "Supplier eq '" + entry.Supplier + "' ";
	        count = count + 1
	    }
	    else{
	        supplierQuery =  supplierQuery + "or Supplier eq '" + entry.Supplier + "' "; 
	    }
		
        
    }

	message.setProperty("SupplierQuery" , supplierQuery)
	
	
	
	return message;
}