import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import com.sap.it.api.msglog.MessageLogFactory;
import groovy.util.XmlSlurper;

def Message processData(Message message) {
	
	def body = message.getBody(java.lang.String) as String;
	def props = message.getProperties();
	def CompanyCodeQuery = ''
	def BusinessPartnerGroupingQuery = ''
	String CompanyCodeStr = props.get("CompanyCode") as String
	String BusinessPartnerGroupingStr = props.get("BusinessPartnerGrouping") as String
	String LastRunDate = props.get("LastRunDate") as String
	String LastRunTime = props.get("LastRunTime") as String
	String InitialLoad = props.get("InitialLoad") as String
	String DeltaQuery = ''
	
	
	
	
	def CompanyCodeList = new ArrayList();
	def BusinessPartnerGroupingList = new ArrayList();

    CompanyCodeList = CompanyCodeStr.tokenize(',');

//Build Company Code Query 
	
	for(int j = 0;j<CompanyCodeList.size();j++){
	    if (j == 0){
	        CompanyCodeQuery = "CompanyCode eq '" + CompanyCodeList.get(j) + "' ";
	    }
	    else{
	        CompanyCodeQuery =  CompanyCodeQuery + "or CompanyCode eq '" + CompanyCodeList.get(j) + "' "; 
	    }
		
	}

//Build BusinessPartnerGrouping Query 
	BusinessPartnerGroupingList = BusinessPartnerGroupingStr.tokenize(',');
	
	for(int j = 0;j<BusinessPartnerGroupingList.size();j++){
	    if (j == 0){
	        BusinessPartnerGroupingQuery = "BusinessPartnerGrouping ne '" + BusinessPartnerGroupingList.get(j) + "' ";
	    }
	    else{
	        BusinessPartnerGroupingQuery =  BusinessPartnerGroupingQuery + "and BusinessPartnerGrouping ne '" + BusinessPartnerGroupingList.get(j) + "' "; 
	    }
		
	}
	
	
	message.setProperty("CompanyCodeQuery", CompanyCodeQuery)
	message.setProperty("BusinessPartnerGroupingQuery", BusinessPartnerGroupingQuery)
	
//handle delta load
//add the creation date logic
	
	if(InitialLoad == 'false'){
        DeltaQuery = " and (((LastChangeDate eq datetime'" + LastRunDate + "' and LastChangeTime ge time'" + LastRunTime + "') or LastChangeDate ge datetime'" + LastRunDate + "') or ((CreationDate eq datetime'" + LastRunDate + "' and CreationTime ge time'" + LastRunTime + "') or CreationDate ge datetime'" + LastRunDate + "'))"
	}
	
	message.setProperty("DeltaQuery", DeltaQuery)
	

	
	message.setBody(body);
	return message;
}