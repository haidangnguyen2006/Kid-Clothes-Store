# API Documentation Index

## 📚 Complete API Documentation for Frontend

All API endpoints documented in detail with examples, error codes, and usage patterns.

---

## 📖 Documentation Files

### 🔐 **[01-AUTH.md](./01-AUTH.md)** - Authentication Module
Endpoints for user authentication and token verification.

**Endpoints:**
- `POST /api/auth/signin` - Login & get JWT token
- `POST /api/auth/introspect` - Verify token validity

**Topics:**
- User authentication flow
- JWT token usage
- Token expiration
- Security best practices

---

### 👤 **[02-USER.md](./02-USER.md)** - User Management Module
Complete user management APIs including registration, profile updates, and password change.

**Endpoints:**
- `POST /api/users` - Register new account
- `GET /api/users/myInfo` - Get personal info
- `PUT /api/users/myInfo` - Update personal info
- `PUT /api/users/change-password` - ⭐ Change password securely
- `GET /api/users/{email}` - Get user info (STAFF)
- `PUT /api/users/{email}` - Update user (STAFF)
- `DELETE /api/users/{email}` - Delete user (STAFF)
- `GET /api/users/search` - Search users
- `GET /api/users` - Get all users (STAFF)
- `GET /api/users/role/{role}` - Get users by role (STAFF)

**Key Features:**
- User registration validation
- Secure password change
- User search functionality
- Admin user management

---

### 🏷️ **[03-CATEGORY.md](./03-CATEGORY.md)** - Category Management Module
Product category management APIs.

**Endpoints:**
- `GET /api/categories` - List all categories
- `GET /api/categories/{id}` - Get category details
- `POST /api/categories` - Create category (STAFF)
- `PUT /api/categories/{id}` - Update category (STAFF)
- `DELETE /api/categories/{id}` - Delete category (STAFF)

**Use Cases:**
- Browse product categories
- Admin category management

---

### 📦 **[04-PRODUCT.md](./04-PRODUCT.md)** - Product Management Module
Product catalog and management APIs including search, filter, and trending.

**Endpoints:**
- `GET /api/products` - List all products
- `GET /api/products/{id}` - Get product details
- `GET /api/products/search` - Search products by keyword
- `GET /api/products/category/{categoryId}` - ⭐ Get products by category
- `GET /api/products/filter` - ⭐ Filter by price range
- `GET /api/products/trending` - ⭐ Get trending products
- `POST /api/products` - Create product (STAFF)
- `PUT /api/products/{id}` - Update product (STAFF)
- `DELETE /api/products/{id}` - Delete product (STAFF)

**Key Features:**
- Product search and filtering
- Category-based browsing
- Price range filtering
- Trending products discovery
- Stock management

---

### 🛒 **[05-CART.md](./05-CART.md)** - Shopping Cart Module
Shopping cart operations with stock validation.

**Endpoints:**
- `GET /api/cart` - View cart contents
- `POST /api/cart/items` - Add product to cart
- `PUT /api/cart/items/{productId}` - ⭐ Update quantity
- `DELETE /api/cart/items/{productId}` - Remove item from cart
- `DELETE /api/cart` - ⭐ Clear entire cart

**Key Features:**
- Real-time cart management
- Stock validation on updates
- Automatic total calculation
- Easy cart clearing

---

### 📋 **[06-ORDER.md](./06-ORDER.md)** - Order Management Module
Order creation, management, and statistics APIs.

**Endpoints:**
- `POST /api/orders` - Create order
- `GET /api/orders/my-orders` - View my orders
- `GET /api/orders/{orderId}` - ⭐ Get order details
- `DELETE /api/orders/{orderId}` - ⭐ Cancel order
- `GET /api/orders/status/{status}` - ⭐ Filter by status (STAFF)
- `PUT /api/orders/{orderId}/status` - Update status (STAFF)
- `GET /api/orders/statistics/revenue` - Revenue stats (STAFF)
- `GET /api/orders/statistics/top-users` - Top customers (STAFF)
- `GET /api/orders/statistics/top-products` - Top products (STAFF)

**Key Features:**
- Order creation from cart
- Order tracking and management
- Order cancellation with inventory restoration
- Business statistics and analytics

---

### 📖 **[README.md](./README.md)** - Getting Started Guide
Quick start guide, common patterns, usage examples, and troubleshooting.

**Contents:**
- Base URL and setup
- Authentication flow
- Response format
- User roles and permissions
- Common patterns
- JavaScript examples
- Troubleshooting guide

---

## 🎯 Quick Navigation

### By Use Case

**Authentication & User Management**
→ Start with [README.md](./README.md), then [01-AUTH.md](./01-AUTH.md) and [02-USER.md](./02-USER.md)

**Shopping**
→ [03-CATEGORY.md](./03-CATEGORY.md) → [04-PRODUCT.md](./04-PRODUCT.md) → [05-CART.md](./05-CART.md) → [06-ORDER.md](./06-ORDER.md)

**Admin/Staff**
→ [02-USER.md](./02-USER.md), [03-CATEGORY.md](./03-CATEGORY.md), [04-PRODUCT.md](./04-PRODUCT.md), [06-ORDER.md](./06-ORDER.md)

---

## 📊 Complete API Summary

| Module | Endpoints | Public | Auth | STAFF | Status |
|--------|-----------|--------|------|-------|--------|
| Auth | 2 | 2 | 0 | 0 | ✅ Complete |
| User | 10 | 1 | 6 | 3 | ✅ Complete |
| Category | 5 | 2 | 0 | 3 | ✅ Complete |
| Product | 9 | 6 | 0 | 3 | ✅ Complete |
| Cart | 5 | 0 | 5 | 0 | ✅ Complete |
| Order | 9 | 0 | 5 | 4 | ✅ Complete |
| **TOTAL** | **41** | **11** | **16** | **13** | ✅ |

---

## 🔑 Key Features Documented

✅ **JWT Authentication** - Secure token-based auth  
✅ **Role-Based Access** - USER and STAFF roles  
✅ **Input Validation** - Complete validation rules  
✅ **Error Handling** - Detailed error responses  
✅ **Stock Management** - Real-time inventory  
✅ **Order Management** - Complete order lifecycle  
✅ **Analytics** - Revenue and product statistics  
✅ **Search & Filter** - Product discovery features  

---

## 💡 Documentation Standards

Every endpoint includes:
- ✅ HTTP method & URL
- ✅ Vietnamese & English description
- ✅ Required/Optional parameters
- ✅ Request body example
- ✅ Success response example
- ✅ Error response examples
- ✅ Authorization requirements
- ✅ Related error codes

---

## 🚀 Getting Started

1. **Read [README.md](./README.md)** - Understand base concepts
2. **Check your module** - Find your API documentation
3. **Copy examples** - Use JavaScript examples
4. **Test with Postman** - Verify endpoints work
5. **Build frontend** - Implement in your app

---

## 📌 Important Links

- **Base URL:** `http://localhost:8080/api`
- **Auth Header:** `Authorization: Bearer <token>`
- **Response Format:** `{ "code": 1000, "result": {...} }`

---

## 🎓 Response Codes

| Code | Meaning | Status |
|------|---------|--------|
| 1000 | Success | 200 |
| 100 | Not authenticated | 401 |
| 101 | Not authorized | 403 |
| 103 | Invalid data | 400 |
| 666 | User exists | 400 |
| 777 | User not found | 404 |
| 999 | Unknown error | 500 |

---

## 📞 Support

If you have questions:
1. Check the documentation in relevant `.md` file
2. Review error codes and responses
3. Look at JavaScript examples
4. Check troubleshooting section in README.md

---

**Documentation Version:** 1.0  
**Last Updated:** March 9, 2026  
**Status:** ✅ Ready for Frontend Development  

🚀 **Happy coding!**

