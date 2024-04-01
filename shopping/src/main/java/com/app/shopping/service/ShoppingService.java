package com.app.shopping.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.shopping.exception.InvalidCouponException;
import com.app.shopping.exception.InvalidQuantityException;
import com.app.shopping.model.Coupon;
import com.app.shopping.model.InventoryStatusVO;
import com.app.shopping.model.Order;
import com.app.shopping.model.OrderPlaceVO;
import com.app.shopping.model.OrderVO;
import com.app.shopping.model.PaymentResponse;
import com.app.shopping.model.Product;
import com.app.shopping.model.Transaction;
import com.app.shopping.model.User;
import com.app.shopping.repo.CouponRepository;
import com.app.shopping.repo.OrderRepository;
import com.app.shopping.repo.ProductRepository;
import com.app.shopping.repo.TransactionRepository;
import com.app.shopping.repo.UserRepository;

@Component
public class ShoppingService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CouponRepository couponRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	TransactionRepository transactionRepository;

	public InventoryStatusVO getInventoryStatus() {

		if (productRepository.count() == 0) { // Check if the product table is empty
			// If empty, insert initial values
			Product product = new Product();
			product.setName("Product Name");
			product.setPrice(Double.valueOf(100)); // Set the initial price
			product.setAvailableQuantity(100); // Set the initial available quantity
			productRepository.save(product);
		}

		// Fetch total ordered quantity
		List<Order> allOrders = orderRepository.findAll();
		int orderedQuantity = 0;

		for (Order order : allOrders) {
			orderedQuantity += order.getQuantity();
		}

		int availableQuantity = getAvailableQuantity();

		// Calculate remaining available quantity
		int remainingQuantity = availableQuantity - orderedQuantity;

		// Fetch the price of the product (assuming all products have the same price)
		Double price = productRepository.findAll().get(0).getPrice();

		InventoryStatusVO inv = new InventoryStatusVO();
		inv.setOrdered(orderedQuantity);
		inv.setPrice(price);
		inv.setAvailable(remainingQuantity);

		return inv;
	}

	private int getAvailableQuantity() {
		// Fetch total available quantity
		List<Product> allProducts = productRepository.findAll();
		int availableQuantity = 0;

		for (Product product : allProducts) {
			availableQuantity += product.getAvailableQuantity();
		}
		return availableQuantity;
	}

	public Map<String, Integer> fetchCoupons() {
		if (couponRepository.count() == 0) {
			List<Coupon> defaultCoupons = Arrays.asList(new Coupon("OFF5", 5), new Coupon("OFF10", 10)
			// Add more default coupons as needed
			);
			couponRepository.saveAll(defaultCoupons);
		}

		List<Coupon> coupons = couponRepository.findAll();
		Map<String, Integer> couponMap = new HashMap<>();
		for (Coupon coupon : coupons) {
			couponMap.put(coupon.getCode(), coupon.getDiscountPercentage());
		}
		return couponMap;
	}

	public boolean isCouponAlreadyUsedByUser(Long userId, String coupon) {

		List<String> orders = getUsersWhoUsedCoupon(userId);
		return orders.contains(coupon);
	}

	// Method to fetch list of user IDs who used the coupon
	private List<String> getUsersWhoUsedCoupon(Long userId) {
		List<Order> orders = orderRepository.findByUserId(userId);

		List<String> coupons = new ArrayList<>();
		for (Order order : orders) {
			coupons.add(order.getCoupon().getCode());
		}

		return coupons;
	}

	public OrderPlaceVO placeOrder(Long userId, int quantity, String couponCode)
			throws InvalidCouponException, InvalidQuantityException {
		// Check if the quantity is valid
		if (quantity < 1 || quantity > getAvailableQuantity()) {
			throw new InvalidQuantityException();
		}

		// Check if the coupon is valid
		Optional<Coupon> optionalCoupon = getCouponByCode(couponCode);
		if (optionalCoupon.isEmpty()) {
			throw new InvalidCouponException();
		}
		if (isCouponAlreadyUsedByUser(userId, couponCode)) {
			throw new InvalidCouponException();
		}
		// assume product price
		int productPrice = 100;

		Coupon coupon = optionalCoupon.get();
		int discountPercentage = coupon.getDiscountPercentage();
		double price = productPrice * quantity;
		double discount = (price * discountPercentage) / 100;
		double totalPrice = price - discount;

		Order order = new Order();
		User user = new User();
		user.setId(userId); // Set the user ID
		order.setUser(user);
		order.setQuantity(quantity);
		order.setCoupon(coupon);
		order.setAmount(totalPrice);

		order.setProduct(productRepository.findAll().get(0));
		Order savedOrder = orderRepository.save(order);

		// Convert the saved order to OrderPlaceVO
		return convertToOrderPlaceVO(savedOrder);
	}

	private OrderPlaceVO convertToOrderPlaceVO(Order order) {
		return new OrderPlaceVO(order.getOrderId(), order.getUser().getId(), order.getQuantity(), order.getAmount(),
				order.getCoupon().getCode());
	}

	public Optional<Coupon> getCouponByCode(String couponCode) {
		return couponRepository.findByCode(couponCode);
	}


	public PaymentResponse processPayment(Long userId, Long orderId) {
		// Mocking payment processing with random status code
		int statusCode = generateRandomStatusCode();

		String transactionId = generateTransactionId();
		String statusDescription = getStatusDescription(statusCode);
		
		String status = "";

		if (statusCode == 200) {
			status = "successful";
			orderRepository.updateStatusAndTransactionId(orderId, status , transactionId);
		
		} else {
			status = "failed";
			orderRepository.updateStatusAndTransactionId(orderId, status, transactionId);
		}
		
	
	    
		  
		
		Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionId);
        transaction.setOrder(orderRepository.findByUserIdAndOrderId(userId, orderId));
        transaction.setStatus(status);
        transaction.setDescription(statusDescription);
	    // Save the transaction
	    transactionRepository.save(transaction);

		return new PaymentResponse(userId, orderId, transactionId, String.valueOf(statusCode), statusDescription);
	}

	private String getStatusDescription(int statusCode) {
	    switch (statusCode) {
	        case 200:
	            return "Successful";
	        case 400:
	            return "Payment failed due to an invalid amount";
	        case 504:
	            return "No response from the payment server";
	        case 405:
	            return "Order is already paid for";
	        default:
	            return "Payment failed from the bank";
	    }
	
	}

	private int generateRandomStatusCode() {
		Random random = new Random();
		int[] statusCodes = { 200, 400, 504, 405 };
		return statusCodes[random.nextInt(statusCodes.length)];
	}

	private String generateTransactionId() {
		// Generate transaction ID logic here
		return "tran" + System.currentTimeMillis();
	}

	public List<OrderVO> getOrdersByUserId(Long userId) {
		

		List<Order> orders = orderRepository.findByUserId(userId);
		List<OrderVO> orderDtos = new ArrayList<>();
		for (Order order : orders) {
			OrderVO orderDto = convertToDto(order);
			orderDtos.add(orderDto);
		}
		return orderDtos;
	}

	private OrderVO convertToDto(Order order) {
		OrderVO orderVO = new OrderVO();
		orderVO.setOrderId(order.getOrderId());
		orderVO.setAmount(order.getAmount());
		orderVO.setDate(formatDate(new Date()));
		orderVO.setCoupon(order.getCoupon().getCode());
		return orderVO;
	}

	private String formatDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		return formatter.format(date);
	}
	


	public OrderVO getOrderById(Long userId, Long orderId) {
		Order order = orderRepository.findByUserIdAndOrderId(userId, orderId);
		if (order != null) {

			OrderVO ordervo = new OrderVO();
			ordervo.setOrderId(order.getOrderId());
			ordervo.setAmount(order.getAmount());
			ordervo.setDate(formatDate(new Date())); // Assuming you want to format the date
			ordervo.setCoupon(order.getCoupon().getCode());
			ordervo.setTransactionId(order.getTransactionId());
			ordervo.setStatus(order.getStatus());

			return ordervo;
		} else {
			return null;
		}
	}
	public void insertUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userRepository.save(user);
    }

}
