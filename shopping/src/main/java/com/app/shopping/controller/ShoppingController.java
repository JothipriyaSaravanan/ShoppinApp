package com.app.shopping.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.app.shopping.exception.InvalidCouponException;
import com.app.shopping.exception.InvalidQuantityException;
import com.app.shopping.exception.PaymentException;
import com.app.shopping.model.ErrorResponse;
import com.app.shopping.model.InventoryStatusVO;
import com.app.shopping.model.OrderPlaceVO;
import com.app.shopping.model.OrderVO;
import com.app.shopping.model.PaymentResponse;
import com.app.shopping.model.User;
import com.app.shopping.service.ShoppingService;

@RestController
public class ShoppingController {

	@Autowired
	private ShoppingService shoppingService;

	@GetMapping("/inventory")
	public ResponseEntity<InventoryStatusVO> getInventoryStatus() {
		InventoryStatusVO inventoryStatus = shoppingService.getInventoryStatus();
		return ResponseEntity.ok().body(inventoryStatus);
	}

	@GetMapping("/fetchCoupons")
	public ResponseEntity<Map<String, Integer>> fetchCoupons() {
		Map<String, Integer> coupons = shoppingService.fetchCoupons();
		return ResponseEntity.ok().body(coupons);
	}

	@PostMapping("/{userId}/order")
	public ResponseEntity<?> placeOrder(@PathVariable Long userId, @RequestParam int qty, @RequestParam String coupon) {
	    try {
	        OrderPlaceVO orderPlaceVO = shoppingService.placeOrder(userId, qty, coupon);
	        return ResponseEntity.ok(orderPlaceVO);
	    } catch (InvalidQuantityException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body(new ErrorResponse("Invalid quantity", HttpStatus.BAD_REQUEST.value()));
	    } catch (InvalidCouponException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body(new ErrorResponse("Invalid coupon", HttpStatus.BAD_REQUEST.value()));
	    }
	}

	@PostMapping("/{userId}/{orderId}/pay")
	public ResponseEntity<?> processPayment(@PathVariable Long userId, @PathVariable Long orderId,
			@RequestParam double amount) {

		try {

			
			PaymentResponse paymentResponse = shoppingService.processPayment(userId, orderId);
			if ("200".equals(paymentResponse.getStatus())) {
			    paymentResponse.setStatus("successful");
			    return ResponseEntity.ok(paymentResponse);
			} else {
			    HttpStatus httpStatus = getHttpStatus(Integer.valueOf(paymentResponse.getStatus()));
			    paymentResponse.setStatus("failed");
			    return ResponseEntity.status(httpStatus).body(paymentResponse);
			}


		} catch (PaymentException e) {
			return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
		}
	}
	
	private HttpStatus getHttpStatus(int statusCode) {
        switch (statusCode) {
            case 200:
                return HttpStatus.OK;
            case 400:
                return HttpStatus.BAD_REQUEST;
            case 504:
                return HttpStatus.GATEWAY_TIMEOUT;
            case 405:
                return HttpStatus.METHOD_NOT_ALLOWED;
            default:
                return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
	
	@GetMapping("/{userId}/orders")
    public ResponseEntity<List<OrderVO>> getOrdersByUserId(@PathVariable Long userId) {
        List<OrderVO> orders = shoppingService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }
	
	@GetMapping("/{userId}/orders/{orderId}")
	public ResponseEntity<?> getOrderById(@PathVariable Long userId, @PathVariable Long orderId) {
	    OrderVO orderVO = shoppingService.getOrderById(userId, orderId);
	    if (orderVO != null) {
	        return ResponseEntity.ok(orderVO);
	    } else {
	        Map<String, Object> response = new HashMap<>();
	        response.put("orderId", orderId);
	        response.put("description", "Order not found");
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    }
	}


    @PostMapping("/users")
    public ResponseEntity<String> insertUser(@RequestBody User userRequest) {
        shoppingService.insertUser(userRequest.getUsername(), userRequest.getPassword());
        return ResponseEntity.ok("Successfull");
    }

}
