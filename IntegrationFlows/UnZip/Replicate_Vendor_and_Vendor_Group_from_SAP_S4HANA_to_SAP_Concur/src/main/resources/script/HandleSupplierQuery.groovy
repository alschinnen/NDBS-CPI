import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import com.sap.it.api.msglog.MessageLogFactory;
import groovy.util.XmlSlurper;

def Message processData(Message message) {
	
	def messageLog = messageLogFactory.getMessageLog(message);
	def body = message.getBody(java.lang.String) as String;
	def count = 0;
    def querylist = new ArrayList();
	def supplierList = new XmlSlurper().parseText(body);
	def supplierQuery = '';
	def map = message.getProperties();
	def VendorCompanyCodeList = "";
	
	
	


//get supplier and company code relationship

    supplierList.A_SupplierCompanyType.each{
        entry ->
        querylist.add(entry.Supplier);
        VendorCompanyCodeList = VendorCompanyCodeList + "<Vendor><Supplier>" + entry.Supplier + "</Supplier><CompanyCode>" + entry.CompanyCode + "</CompanyCode></Vendor>"
        
    }




	VendorCompanyCodeList = "<Vendors>" + VendorCompanyCodeList + "</Vendors>"
	message.setProperty("VendorCompanyCodeList" , VendorCompanyCodeList)

	querylist.unique()



//build the query with unique supplier  

    def queryXML = ''
    
    for(int j = 0;j<querylist.size();j++){
	    
	    queryXML = queryXML + "<Vendor><Supplier>" + querylist.get(j) + "</Supplier></Vendor>"
	}
	
	queryXML = "<Vendors>" + queryXML + "</Vendors>"
	
	
	message.setBody(queryXML);
	
	return message;
}