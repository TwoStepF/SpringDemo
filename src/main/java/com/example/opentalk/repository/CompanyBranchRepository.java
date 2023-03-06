package com.example.opentalk.repository;

import com.example.opentalk.entity.Company_branch;
import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CompanyBranchRepository extends JpaRepository<Company_branch, String> {
    Company_branch findCompany_branchById(String Id);
}
