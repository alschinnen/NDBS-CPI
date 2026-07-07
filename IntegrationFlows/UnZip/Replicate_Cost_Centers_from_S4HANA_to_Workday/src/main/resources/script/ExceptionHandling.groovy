import com.sap.gateway.ip.core.customdev.util.Message;

def Message processData(Message message) {
                
                // get a map of iflow properties
                def map = message.getProperties();
                def refernceID = map.get("SAPReferenceId")
                def logException = map.get("ExceptionLogging")
                
                // get an exception java class instance
                def ex = map.get("CamelExceptionCaught");
                if (ex!=null) {
                 // save the error response as a message attachment 
                def messageLog = messageLogFactory.getMessageLog(message);
                def errordetails = "The replication of Account (Business Partner) '" + refernceID + "' failed because of the following error:  " + ex.toString()
                def attachID  = "Error Details for Business Partner '" + refernceID + "'"
                if (logException != null && logException.equalsIgnoreCase("TRUE")) {
                messageLog.addAttachmentAsString(attachID, errordetails, "text/plain");
                }
                }

                return message;
}