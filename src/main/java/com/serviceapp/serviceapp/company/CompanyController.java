package com.serviceapp.serviceapp.company;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.serviceapp.serviceapp.company.Dto.SignUp1;

@RestController
@RequestMapping(path = "api/company")
public class CompanyController {
  private final CompanyService companyService;

  public CompanyController(CompanyService companyService) {
      this.companyService = companyService;
  }


  @PostMapping(path = "auth/signup")
  public void signUp(@RequestBody SignUp1 signup) {
    companyService.signUp(signup);
  }

}
