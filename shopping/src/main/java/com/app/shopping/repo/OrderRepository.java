package com.app.shopping.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.app.shopping.model.Order;
import com.app.shopping.model.User;

public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findByUser(User user);

	List<Order> findByUserId(Long userId);

	Order findByUserIdAndOrderId(Long userId, Long orderId);

	@Transactional
    @Modifying
    @Query(value = "UPDATE orders SET status = :status, transaction_id = :transactionId WHERE order_id = :orderId", nativeQuery = true)
    void updateStatusAndTransactionId(@Param("orderId") Long orderId, @Param("status") String status, @Param("transactionId") String transactionId);
}
