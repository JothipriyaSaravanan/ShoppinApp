package com.app.shopping.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.shopping.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
}
