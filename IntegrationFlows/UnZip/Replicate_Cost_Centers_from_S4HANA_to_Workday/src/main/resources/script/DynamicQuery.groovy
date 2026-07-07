/*
 The integration developer needs to create the method processData 
 This method takes Message object of package com.sap.gateway.ip.core.customdev.util 
which includes helper methods useful for the content developer:
The methods available are:
    public java.lang.Object getBody()
	public void setBody(java.lang.Object exchangeBody)
    public java.util.Map<java.lang.String,java.lang.Object> getHeaders()
    public void setHeaders(java.util.Map<java.lang.String,java.lang.Object> exchangeHeaders)
    public void setHeader(java.lang.String name, java.lang.Object value)
    public java.util.Map<java.lang.String,java.lang.Object> getProperties()
    public void setProperties(java.util.Map<java.lang.String,java.lang.Object> exchangeProperties) 
    public void setProperty(java.lang.String name, java.lang.Object value)
    public java.util.List<com.sap.gateway.ip.core.customdev.util.SoapHeader> getSoapHeaders()
    public void setSoapHeaders(java.util.List<com.sap.gateway.ip.core.customdev.util.SoapHeader> soapHeaders) 
       public void clearSoapHeaders()
 */
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
def Message processData(Message message) {
        //Body 
       def body = message.getBody();
     
       //Headers 
       def map = message.getProperties()
       String prop_CostCenter = map.get("CostCenters")
       String prop_LastRunDateTime  = map.get("LastRunDateTime")
       String prop_InitialQueryDate = map.get("InitialQueryDate")
       String customQuery = ""
       
       if((prop_CostCenter != '') && (prop_CostCenter != null))
       {
           CostCenters_split = prop_CostCenter.split(",");
           if (CostCenters_split.size() == 0)
           {
               throw new Exception("Configuration Error: Please enter the Cost Center in expected format");
           }
           else if (CostCenters_split.size() > 0)
           {
               for (int j=0; j < CostCenters_split.length; j++) {
    			if (j==0) 
    				customQuery = "\$filter=CostCenter eq '" + CostCenters_split[j].trim() + "'";
    			else
    				customQuery = customQuery + " or CostCenter eq '" + CostCenters_split[j].trim() + "'"; 	        		        	
		        }
           }
       }
      else if ((prop_InitialQueryDate != '') && (prop_InitialQueryDate != null)){
          customQuery = "\$filter=CostCenterCreationDate ge datetime'" + prop_InitialQueryDate + "'"
          
      }
      else if ((prop_LastRunDateTime != '') && (prop_LastRunDateTime != null)){
          customQuery = "\$filter=CostCenterCreationDate ge datetime'" + prop_LastRunDateTime + "'"
      }
       
       else{
            throw new Exception("Configuration Error: Please check the configuration");
      }
      message.setProperty("CustomQuery", customQuery);
      return message;
}