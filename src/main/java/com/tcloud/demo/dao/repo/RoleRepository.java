package com.tcloud.demo.dao.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tcloud.demo.model.Role;


public interface RoleRepository extends JpaRepository<Role,Integer>{
	Role findByNameLike(String name);
}
