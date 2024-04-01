# ShoppinApp
 This shopping web app allows users to perform various actions, including placing orders, applying coupons, making payments, and viewing order status

1.Check Inventory Status (GET /inventory):

Users can check the current status of the product inventory. The response includes the total ordered quantity, price per item, and available quantity.

When the application is initially started, the endpoint returns the inventory status with no items ordered.

![img 1](https://github.com/JothipriyaSaravanan/ShoppinApp/assets/155729866/2805200f-af63-4abb-8801-2a8a23f177c0)


When we have ordered 50 items 


![img 12](https://github.com/JothipriyaSaravanan/ShoppinApp/assets/155729866/0926d119-0b92-477f-a85e-31af9eb88522)


2.Fetch Coupons (GET /fetchCoupons):

The /fetchCoupons endpoint allows users to retrieve information about available coupons along with their corresponding discount percentages. 
This endpoint assists users in identifying and applying valid coupons during the checkout process to avail discounts on their purchases.


![img 2](https://github.com/JothipriyaSaravanan/ShoppinApp/assets/155729866/bf3672a2-a8eb-41b4-bb75-679ad5f83fc7)




3.Place Order (POST/{userId}/order?qty=10&coupon=OFF5):

Users can place an order by specifying the quantity and applying a coupon (if valid).

The response includes the order ID, user ID, quantity, calculated amount after applying the coupon, and the applied coupon code.


![img 3](https://github.com/JothipriyaSaravanan/ShoppinApp/assets/155729866/91136a24-b1fc-4674-8ce4-3f615a1f41f2)



Error responses include descriptions for cases of invalid quantity or coupon.

1.invalid coupon


![img 4](https://github.com/JothipriyaSaravanan/ShoppinApp/assets/155729866/e852373d-9f4b-4b33-a116-dc275138efe5)


2.invalid quantity


![img 5](https://github.com/JothipriyaSaravanan/ShoppinApp/assets/155729866/ba744bb9-72a5-4b8a-99c6-a1a3c21d33f0)





4.Process Payment (POST /{userId}/{orderId}/pay):

Users can make payments for their orders, simulating payment processing with various status codes.

The response includes the payment status, transaction ID, and description of the payment result.

 
![img 6](https://github.com/JothipriyaSaravanan/ShoppinApp/assets/155729866/71f4b3a3-b010-4162-a600-1c71b888ef5b)



The response status code varies based on the payment status.

![img 7](https://github.com/JothipriyaSaravanan/ShoppinApp/assets/155729866/22e77b2a-54e8-4598-9977-dc7354b43033)


![img 8](https://github.com/JothipriyaSaravanan/ShoppinApp/assets/155729866/ecd4c836-bdc6-4639-a4c0-19503504c382)



5.View User Orders (GET /{userId}/orders):

Users can view all their orders, including order details such as ID, amount, date, and applied coupon.



![img 9](https://github.com/JothipriyaSaravanan/ShoppinApp/assets/155729866/071f17e0-fc08-444e-baf1-46bf730d96cb)



6.View Order Details (GET /{userId}/orders/{orderId}):

Users can view details of a specific order identified by its ID.

The response includes details such as order ID, amount, date, applied coupon, transaction ID, and payment status.


![img 10](https://github.com/JothipriyaSaravanan/ShoppinApp/assets/155729866/4b0cd31a-8c41-413b-bdcc-56a544240872)


![img12](https://github.com/JothipriyaSaravanan/ShoppinApp/assets/155729866/630b4489-adc4-4c3b-92ad-894846832cd1)




If orderId not found that will view the orderIdand discription


![img 11](https://github.com/JothipriyaSaravanan/ShoppinApp/assets/155729866/87a6f705-1dfe-4fb0-b7a1-adac23646656)
