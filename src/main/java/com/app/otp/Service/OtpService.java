package com.app.otp.Service;

import com.app.otp.Configuration.TwilioConfig;
import com.google.common.cache.LoadingCache;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Random;

@Service
public class OtpService {
    private LoadingCache<String,String> otpCache;
    @Autowired
    private TwilioConfig twilioConfig;
    public String generateOtp(String phoneNo){
        PhoneNumber to = new PhoneNumber(phoneNo);
        PhoneNumber from = new PhoneNumber(twilioConfig.getPhoneNumber());
        String otp = getRandomOTP(phoneNo);
        String otpMessage = "Dear Customer , Your OTP is " + otp + ". Use this otp";
        Message message = Message
                .creator(to, from,
                        otpMessage)
                .create();
        return  otp;
    }

    private String getRandomOTP(String phoneNo) {
        String otp =  new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
        otpCache.put(phoneNo,otp);
        return otp;
    }
    public String getCacheOtp(String key){
        try{
            return otpCache.get(key);
        }catch (Exception e){
            return "";
        }
    }
    public void clearOtp(String key){
        otpCache.invalidate(key);
    }
}
