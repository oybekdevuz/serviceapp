package com.serviceapp.serviceapp.company;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.serviceapp.serviceapp.company.Dto.SignUp1;
import com.serviceapp.serviceapp.utils.Bcrypt;
import com.serviceapp.serviceapp.utils.EmailService;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final EmailService emailService;

    public CompanyService(CompanyRepository companyRepository, RedisTemplate<String, String> redisTemplate, EmailService emailService) {
        this.companyRepository = companyRepository;
        this.redisTemplate = redisTemplate;
        this.emailService = emailService; 
    }

    public Object signUp(SignUp1 signUp) {
      String email = signUp.getEmail();
      String password = signUp.getPassword();
      Optional<Company> existingCompany = companyRepository.findByEmail(email);
      if (existingCompany.isPresent()) {
          System.out.println("Company already exists");
          return "Company already exists";
      }

      String hashedPassword = Bcrypt.hash(password);
      password = hashedPassword;
      Company company = new Company(email, password);
      companyRepository.save(company);
      int otp = generateOTP();

      redisTemplate.opsForValue().set(email, String.valueOf(otp), 180, TimeUnit.SECONDS);
      System.out.println("OTP: " + otp);
      String text = "<b style='font-size: 40px'>Sizning tasdiqlash kodingiz: " + otp + "</b>";
      emailService.sendEmail(email, "Tasdiqlash Kodi", text);

      return Map.of("message", "Success", "company", Map.of("id", company.getId(), "email", company.getEmail()));
  }

    private int generateOTP() {
        return (int) (Math.random() * (999999 - 100000 + 1) + 100000);
    }
}
