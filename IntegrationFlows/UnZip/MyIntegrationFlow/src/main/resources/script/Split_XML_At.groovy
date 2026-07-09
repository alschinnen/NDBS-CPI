import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import groovy.json.JsonSlurper;

def Message parseJsonMessage(Message message) {
    //Get message and parse to json
    def json = message.getBody(java.io.Reader);
    def data  = new JsonSlurper().parse(json);

    //get fields of the payload (service, ressource & id)
    message.setProperty("entry", data.query.entry);
    message.setProperty("content", data.query.entry.content);
    message.setProperty("properties", data.query.entry.content["m:properties"]);
    var entry = data.query.entry.content["m:properties"]);
    message.setProperty("CustomerID", entry["d:CustomerID"]);
    message.setProperty("CustomerName", entry["d:CustomerName"]);
    //get columns to be read
    //def numFields = data.query.entity.fields.size();
    //def fields = "";
    //for (int i=0; i<numFields; i++) {
    //    fields += data.query.entity.fields[i].name;
    //    if (i<numFields-1) fields += ",";
    //}
    //message.setProperty("fields", fields);

    return message;
}