package com.serviceapp.serviceapp.company;


import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.serviceapp.serviceapp.company.Dto.BusinessHourDto;
import com.serviceapp.serviceapp.company.Dto.RegisterStep2;
import com.serviceapp.serviceapp.company.Dto.SignUp1;
import org.springframework.http.MediaType;


@RestController
@RequestMapping("/api/company")
public class CompanyController {

    private final CompanyService companyService;


    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping(path = "auth/signup")
    public Object signUp(@RequestBody SignUp1 signUp) {
        return companyService.signUp(signUp);
    }

    @PostMapping(path = "auth/resend-otp")
    public Object resendOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        return companyService.resendOtp(email);
    }

    
    @PostMapping(path = "auth/verify")
    public Object registerStep2(@RequestBody Map<String, String> request) {
      String email = request.get("email");
      String otp = request.get("otp");
      System.out.println(email);
      System.out.println(otp);
      return companyService.verify(email, otp);
    }

    @PostMapping(path = "auth/signup-2", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Object verify(@ModelAttribute RegisterStep2 request, @RequestPart(value = "file") MultipartFile file) {
        return companyService.registerStep2(request, file);
    }

    @PostMapping(path = "auth/busines-hours")
    public Object businessHours(@RequestBody List<BusinessHourDto> businessHourDtos) {
        return companyService.registerBusinessHours(businessHourDtos);
    }
}