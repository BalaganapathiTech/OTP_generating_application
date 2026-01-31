package com.app.otp.Controller;

import com.app.otp.Entity.AuthRequest;
import com.app.otp.Service.OtpService;
import com.app.otp.Service.UserDetailsService;
import com.app.otp.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/client/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private OtpService otpService;

    @GetMapping("requestOtp/{phoneNo}")
    public Map<String,Object> getOtp(@PathVariable String phoneNo) {
        Map<String,Object> returnMap = new HashMap<>();
        try {
            otpService.generateOtp(phoneNo);
            returnMap.put("status","success");
            returnMap.put("message","OTP sent successfully");
        } catch (Exception e) {
            returnMap.put("status","failed");
            returnMap.put("message",e.getMessage());
        }
        return returnMap;
    }

    @PostMapping("verifyOtp")
    public Map<String,Object> verifyOtp(@RequestBody AuthRequest authenticationRequest) {
        Map<String,Object> returnMap = new HashMap<>();
        try {
            String cachedOtp = otpService.getCacheOtp(authenticationRequest.getPhoneNo());

            if (cachedOtp != null && cachedOtp.equals(authenticationRequest.getOtp())) {
                String jwtToken = createAuthenticationToken(authenticationRequest);
                returnMap.put("status","success");
                returnMap.put("message","OTP verified successfully");
                returnMap.put("jwt", jwtToken);
                otpService.clearOtp(authenticationRequest.getPhoneNo());
            } else {
                returnMap.put("status","failed");
                returnMap.put("message","OTP is either expired or incorrect");
            }

        } catch (Exception e) {
            returnMap.put("status","failed");
            returnMap.put("message",e.getMessage());
        }
        return returnMap;
    }

    private String createAuthenticationToken(AuthRequest authenticationRequest) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getPhoneNo(), "")
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getPhoneNo());
        return jwtTokenUtil.generateToken(userDetails);
    }
}
