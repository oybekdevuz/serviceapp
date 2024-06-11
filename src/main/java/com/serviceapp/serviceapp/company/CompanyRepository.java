package com.serviceapp.serviceapp.company;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company,  Long> {
  Optional<Company> findByEmail(String email);
  Company findByEmailAndVerifyFalse(String email);
  Company findByIdAndVerifyTrue(Long id);
}
