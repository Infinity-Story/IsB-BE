package com.infinity.isbbe.admin.repository;

import com.infinity.isbbe.admin.aggregate.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    List<Admin> findByAdminCode(int adminCode);

    Optional<Admin> findByAdminId(String adminId);
}
