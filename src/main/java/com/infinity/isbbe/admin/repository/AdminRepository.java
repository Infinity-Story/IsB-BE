package com.infinity.isbbe.admin.repository;

import com.infinity.isbbe.admin.aggregate.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    List<Admin> findByAdminCode(int adminCode);
}
