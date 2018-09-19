package com.shawtravel.booking.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.shawtravel.booking.service.SmsService;

@Service("SmsServiceImpl")
public class SmsServiceImpl implements SmsService{
	
	public static final Logger logger = Logger.getLogger("SmsServiceImpl.class");
	
	@Autowired
    public Environment environment;

	@Override
	public void sendSms(String recipients, String message) throws Exception {
         URLConnection myURLConnection=null;
         URL myURL=null;
         BufferedReader reader=null;

         //encoding message
         String encoded_message=URLEncoder.encode(message);

         //Send SMS API
         String mainUrl=environment.getProperty("sms.apiurl");

         //Prepare parameter string
         StringBuilder sbPostData= new StringBuilder(mainUrl);
         sbPostData.append("authkey="+environment.getProperty("sms.authkey"));
         sbPostData.append("&mobiles="+recipients);
         sbPostData.append("&message="+encoded_message);
         sbPostData.append("&route="+environment.getProperty("sms.route"));
         sbPostData.append("&sender="+environment.getProperty("sms.senderid"));

         //final string
         mainUrl = sbPostData.toString();
         try
         {
             //prepare connection
             myURL = new URL(mainUrl);
             myURLConnection = myURL.openConnection();
             myURLConnection.connect();
             reader= new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
             //reading response
             String response;
             while ((response = reader.readLine()) != null)
             //print response
             logger.info(response);

             //finally close connection
             reader.close();
         }
         catch (IOException e)
         {
                 e.printStackTrace();
                 throw new Exception("Unable to send sms due to error "+ e.getMessage());
         }
         catch (Exception e)
         {
                 e.printStackTrace();
                 throw new Exception("Unable to send sms due to error "+ e.getMessage());
         }
     }		
}
