/* Refer the link below to learn more about the use cases of script.
https://help.sap.com/viewer/368c481cd6954bdfa5d0435479fd4eaf/Cloud/en-US/148851bf8192412cba1f9d2c17f4bd25.html

If you want to know more about the SCRIPT APIs, refer the link below
https://help.sap.com/doc/a56f52e1a58e4e2bac7f7adbf45b2e26/Cloud/en-US/index.html */
importClass(com.sap.gateway.ip.core.customdev.util.Message);
importClass(java.util.HashMap);

function processData(message) {
    //Body
    var body = message.getBody().getProperties();
    var value = "";
    /*To set the body, you can use the following method. Refer SCRIPT APIs document for more detail*/
    //message.setBody(body + " modified from js");
    //Headers
    //value = body.split('<content type="application/xml">')[1];
    //body = value.split('</content>')[0];
    body.forEach( function (entry) {
        
    });
    message.setBody(body);
    return message;
}