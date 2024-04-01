package com.app.shopping.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.shopping.model.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long>{
	Optional<Coupon> findByCode(String code);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM Coupon WHERE code = :couponCode", nativeQuery = true)
	void deleteByCode(@Param("couponCode") String couponCode);
	
}
