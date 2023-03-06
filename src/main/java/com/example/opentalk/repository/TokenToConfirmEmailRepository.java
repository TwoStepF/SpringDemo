package com.example.opentalk.repository;

import com.example.opentalk.entity.Employee;
import com.example.opentalk.entity.TokenToConfirmEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenToConfirmEmailRepository extends JpaRepository<TokenToConfirmEmail, String> {
    @Query(value = "select * from tokentoconfirmemail as t join employee as e on t.employee_id = e.id where e.name = :name", nativeQuery = true)
    TokenToConfirmEmail findByEmployeeName(@Param("name") String name);

    @Query(value = "select * from token_to_confirm_email where token_id = :id", nativeQuery = true)
    TokenToConfirmEmail findTokenToConfirmEmailByToken_id(@Param("id") String id);
}
