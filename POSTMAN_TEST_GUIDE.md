# 🧪 POSTMAN TEST GUIDE - API Testing

## Setup Postman

1. **Create Collection:** "KidClothes API"
2. **Environment Variables:**
```json
{
  "baseUrl": "http://localhost:8080/api",
  "token": "your_jwt_token_here",
  "categoryId": "category_id_from_create",
  "productId": "product_id_from_create",
  "orderId": "order_id_from_create"
}
```

---

## 📁 CATEGORY MANAGEMENT

### 1. Get All Categories
```
GET {{baseUrl}}/categories

Response (200):
{
  "code": 1000,
  "result": [
    {
      "id": "67a1234567...",
      "name": "Áo trẻ em",
      "description": "Các loại áo dành cho trẻ em"
    }
  ]
}
```

### 2. Create Category (STAFF ONLY)
```
POST {{baseUrl}}/categories

Headers:
  - Authorization: Bearer {{token}} (STAFF role)
  - Content-Type: application/json

Body:
{
  "name": "Quần trẻ em",
  "description": "Các loại quần dành cho trẻ em"
}

Response (201):
{
  "code": 1000,
  "result": {
    "id": "67a1234567...",
    "name": "Quần trẻ em",
    "description": "Các loại quần dành cho trẻ em"
  }
}
```

### 3. Update Category (STAFF ONLY)
```
PUT {{baseUrl}}/categories/{{categoryId}}

Headers:
  - Authorization: Bearer {{token}} (STAFF role)

Body:
{
  "name": "Quần áo trẻ em",
  "description": "Updated"
}
```

### 4. Delete Category (STAFF ONLY)
```
DELETE {{baseUrl}}/categories/{{categoryId}}

Headers:
  - Authorization: Bearer {{token}} (STAFF role)

Response (200):
{
  "code": 1000,
  "message": "Xóa danh mục thành công"
}
```

---

## 🛍️ PRODUCT FILTERS

### 1. Get Products By Category
```
GET {{baseUrl}}/products/category/{{categoryId}}

Response (200):
{
  "code": 1000,
  "result": [...]
}
```

### 2. Filter Products By Price
```
GET {{baseUrl}}/products/filter?minPrice=50000&maxPrice=200000
```

### 3. Get Trending Products
```
GET {{baseUrl}}/products/trending?limit=10
```

---

## 🛒 CART OPERATIONS

### 1. Update Cart Item Quantity
```
PUT {{baseUrl}}/cart/items/{{productId}}?size=L&color=RED&quantity=3

Headers:
  - Authorization: Bearer {{token}}

Response (200):
{
  "code": 1000,
  "result": {
    "id": "...",
    "items": [...]
  }
}
```

### 2. Clear Entire Cart
```
DELETE {{baseUrl}}/cart

Headers:
  - Authorization: Bearer {{token}}

Response (200):
{
  "code": 1000,
  "message": "Giỏ hàng đã được xóa"
}
```

---

## 📦 ORDER MANAGEMENT

### 1. Get Order Details
```
GET {{baseUrl}}/orders/{{orderId}}

Headers:
  - Authorization: Bearer {{token}}

Response (200):
{
  "code": 1000,
  "result": {
    "id": "...",
    "userId": "...",
    "status": "PENDING_DELIVERY",
    "totalAmount": 330000
  }
}
```

### 2. Cancel Order
```
DELETE {{baseUrl}}/orders/{{orderId}}

Headers:
  - Authorization: Bearer {{token}}

Response (200):
{
  "code": 1000,
  "message": "Hủy đơn hàng thành công"
}
```

### 3. Get Orders By Status (STAFF)
```
GET {{baseUrl}}/orders/status/PENDING_DELIVERY

Headers:
  - Authorization: Bearer {{token}} (STAFF role)
```

---

## 👤 USER MANAGEMENT

### 1. Change Password
```
PUT {{baseUrl}}/users/change-password

Headers:
  - Authorization: Bearer {{token}}

Body:
{
  "oldPassword": "currentPassword123",
  "newPassword": "newPassword456",
  "confirmPassword": "newPassword456"
}

Response (200):
{
  "code": 1000,
  "message": "Đổi mật khẩu thành công"
}
```

---

## ✅ TEST SCENARIOS

### Scenario 1: Customer Flow
```
1. Sign In → Get token
2. View Products → GET /products
3. Browse by Category → GET /products/category/{id}
4. Add to Cart → POST /cart/items
5. Update Quantity → PUT /cart/items/{productId}
6. Create Order → POST /orders
7. View Order → GET /orders/{orderId}
8. Change Password → PUT /users/change-password
```

### Scenario 2: Staff Flow
```
1. Sign In with STAFF role → Get token
2. Create Category → POST /categories
3. View Orders by Status → GET /orders/status/PENDING_DELIVERY
4. Update Order Status → PUT /orders/{orderId}/status
5. View Statistics → GET /orders/statistics/revenue
```

---

## 🔍 ERROR CODES

| Code | Status | Message |
|------|--------|---------|
| 1000 | 200 | Success |
| 100 | 401 | UNAUTHENTICATION |
| 101 | 403 | UNAUTHORIZED |
| 103 | 400 | INVALID_KEY |
| 104 | 400 | PASSWORD_INVALID |
| 666 | 400 | USER_EXISTED |
| 777 | 404 | USER_UN_EXISTED |
| 999 | 500 | UNCATEGORIZED |

