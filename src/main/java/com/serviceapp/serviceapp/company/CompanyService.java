package com.serviceapp.serviceapp.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.serviceapp.serviceapp.company.Dto.BusinessHourDto;
import com.serviceapp.serviceapp.company.Dto.RegisterStep2;
import com.serviceapp.serviceapp.company.Dto.SignUp1;
import com.serviceapp.serviceapp.utils.Bcrypt;
import com.serviceapp.serviceapp.utils.CustomError;
import com.serviceapp.serviceapp.utils.EmailService;
import com.serviceapp.serviceapp.utils.FileUploadHelper;
import com.serviceapp.serviceapp.utils.TokenGenerator;

import java.util.Map;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    @Autowired
    private final CompanyRepository companyRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final EmailService emailService;
    private final TokenGenerator tokenGenerator;
    @Autowired
    private FileUploadHelper fileUploadHelper;

    public CompanyService(CompanyRepository companyRepository, RedisTemplate<String, String> redisTemplate, EmailService emailService, TokenGenerator tokenGenerator) {
        this.companyRepository = companyRepository;
        this.redisTemplate = redisTemplate;
        this.emailService = emailService; 
        this.tokenGenerator = tokenGenerator;
    }

    public Company registerBusinessHours(List<BusinessHourDto> businessHourDtos) throws CustomError {
      if (businessHourDtos == null || businessHourDtos.isEmpty()) {
          throw new CustomError(400, "Business hours data is empty");
      }
  
      Long companyId = businessHourDtos.get(0).getCompanyId();
      Company company = companyRepository.findByIdAndVerifyTrue(companyId);
      if (company == null) {
          throw new CustomError(404, "Company not found or not verified");
      }
  
      List<BusinessHour> businessHours = businessHourDtos.stream().map(dto -> new BusinessHour(
              dto.getDayOfWeek(),
              dto.getOpenTime(),
              dto.getCloseTime(),
              dto.getIsClosed(),
              company
      )).collect(Collectors.toList());
  
      company.getBusinessHours().clear();
      company.getBusinessHours().addAll(businessHours);
  
      companyRepository.save(company);
  
      return company;
  }
  

    public Object signUp(SignUp1 signUp) {
        try {
            String email = signUp.getEmail();
            String password = signUp.getPassword();
            Optional<Company> existingCompany = companyRepository.findByEmail(email);
            if (existingCompany.isPresent()) {
                throw new CustomError(403, "Company already exists");
            }
    
            String hashedPassword = Bcrypt.hash(password);
            password = hashedPassword;
            Company company = new Company(email, password);
            companyRepository.save(company);
    
            int otp = generateOTP();
            redisTemplate.opsForValue().set(email, String.valueOf(otp), 180, TimeUnit.SECONDS);
    
            String text = "Sizning tasdiqlash kodingiz: " + otp ;
            emailService.sendEmail(email, "Tasdiqlash Kodi", text);
    
            return Map.of("message", "Success", "company", Map.of("id", company.getId(), "email", company.getEmail()));
          
        } catch (CustomError e) {
            return Map.of("message", e.getMessage());
        } catch (Exception e) {
            return Map.of("message", "Internal server error");
        }  
    }
    

    private int generateOTP() {
        return (int) (Math.random() * (999999 - 100000 + 1) + 100000);
    }

    public Map<String, Object> verify(String email, String otp) {
    try {

      String cache = redisTemplate.opsForValue().get(email);
      if (cache == null || !cache.equals(otp)) {
          throw new CustomError(403, "Invalid OTP");
      }

      redisTemplate.delete(email);

      Company company = companyRepository.findByEmailAndVerifyFalse(email);
      if (company == null) {
          throw new CustomError(403, "Company not found or already verified");
      }

      company.setVerify(true);
      companyRepository.save(company);

      String token = tokenGenerator.generateToken(company.getId(), company.getEmail(), company.getVerify());

      return Map.of(
          "message", "Success",
          "company", company,
          "token", token
      );
    } catch (CustomError e) {
      return Map.of("message", e.getMessage());
    } catch (Exception e) {
        return Map.of("message", "Internal server error");
    } 
    }

    public Object resendOtp(String email) {
      try {
        Company company = companyRepository.findByEmailAndVerifyFalse(email);
        if (company == null) {
            throw new CustomError(403, "Company not found or already verified");
        }

        int otp = generateOTP();
        redisTemplate.opsForValue().set(email, String.valueOf(otp), 180, TimeUnit.SECONDS);

        String text = "Sizning tasdiqlash kodingiz: " + otp ;
        emailService.sendEmail(email, "Tasdiqlash Kodi", text);

        return Map.of("message", "Success", "company", Map.of("id", company.getId(), "email", company.getEmail()));

      } catch (CustomError e) {
        return Map.of("message", e.getMessage());
      } catch (Exception e) {
          return Map.of("message", "Internal server error");
      } 
    }
    
    public Object registerStep2(RegisterStep2 body, MultipartFile file) {
      try {
        Long id = body.getId();
        Company company = companyRepository.findByIdAndVerifyTrue(id);
        System.out.println(company);
        if (company == null) {
          throw new CustomError(403, "Company not found or not verified");
          }
          
        String companyName = body.getCompanyName();
        String businessEIN = body.getBusinessEIN();
        String companyPhoneNumber = body.getCompanyPhoneNumber();
        Integer yearFounded = body.getYearFounded();
        String officeName = body.getOfficeName();
        String servingArea = body.getServingArea();
        String addressLine1 = body.getAddressLine1();
        String addressLine2 = body.getAddressLine2();
        String city = body.getCity();
        String state = body.getState();
        String zipCode = body.getZipCode();
        String ownerFirstName = body.getOwnerFirstName();
        String ownerLastName = body.getOwnerLastName();
        String ownerPhoneNumber = body.getOwnerPhoneNumber();
        String username = body.getUsername();
        String ownerDateOfBirth = body.getOwnerDateOfBirth();
        company.setCompanyName(companyName);
        company.setBusinessEIN(businessEIN);
        company.setCompanyPhoneNumber(companyPhoneNumber);
        company.setAddressLine1(addressLine1);
        company.setAddressLine2(addressLine2);
        company.setYearFounded(yearFounded);
        company.setOfficeName(officeName);
        company.setServingArea(servingArea);
        company.setCity(city);
        company.setState(state);
        company.setZipCode(zipCode);
        company.setOwnerFirstName(ownerFirstName);
        company.setOwnerLastName(ownerLastName);
        company.setOwnerPhoneNumber(ownerPhoneNumber);
        company.setUsername(username);
        company.setOwnerDateOfBirth(ownerDateOfBirth);
        String filePath = null;
        if (file != null && !file.isEmpty()) {
            filePath = fileUploadHelper.uploadFile(file);
            company.setLogoUrl(filePath);
        }

        companyRepository.save(company);
        return Map.of("message", "Success", "company", Map.of("company", company));

      } catch (CustomError e) {
        return Map.of("message", e.getMessage());
      } catch (Exception e) {
          return Map.of("message", "Internal server error");
      } 
    }

    public Object signInCompany(String email, String password) {
        try {

          Company company = companyRepository.findByEmailAndVerifyTrue(email);
            if (company == null) {
                throw new CustomError(403, "Email or password incorrect");
            }

            Boolean isMatch = Bcrypt.compare(password, company.getPassword());
            if (!isMatch) {
                throw new CustomError(403, "Email or password incorrect");
            }

            String token = tokenGenerator.generateToken(company.getId(), company.getEmail(), company.getVerify());
            

            return Map.of(
              "message", "Success",
              "company", company,
              "token", token
          );
        } catch (CustomError e) {
          return Map.of("message", e.getMessage());
        } catch (Exception e) {
          return Map.of("message", "Internal server error");
        }
    }

}