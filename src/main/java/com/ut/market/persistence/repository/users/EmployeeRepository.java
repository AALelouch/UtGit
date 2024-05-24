package com.ut.market.persistence.repository.users;

import com.ut.market.persistence.entity.users.Client;
import com.ut.market.persistence.entity.users.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByNameContaining(String name);

}
