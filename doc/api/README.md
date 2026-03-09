# API Documentation

Tài liệu hướng dẫn sử dụng API cho các Frontend developers.

---

## 📚 Danh sách API Modules

| Module | File | Endpoint | Mô tả |
|--------|------|----------|-------|
| **Auth** | [01-AUTH.md](./01-AUTH.md) | `/api/auth` | Đăng nhập, Xác minh token |
| **User** | [02-USER.md](./02-USER.md) | `/api/users` | Quản lý tài khoản người dùng |
| **Category** | [03-CATEGORY.md](./03-CATEGORY.md) | `/api/categories` | Quản lý danh mục sản phẩm |
| **Product** | [04-PRODUCT.md](./04-PRODUCT.md) | `/api/products` | Quản lý sản phẩm |
| **Cart** | [05-CART.md](./05-CART.md) | `/api/cart` | Quản lý giỏ hàng |
| **Order** | [06-ORDER.md](./06-ORDER.md) | `/api/orders` | Quản lý đơn hàng & thống kê |

---

## 🚀 Quick Start

### 1. Base URL

```
http://localhost:8080/api
```

### 2. Authentication

Tất cả các endpoint (trừ những endpoint công khai) yêu cầu JWT token trong header:

```
Authorization: Bearer <token>
```

#### Cách lấy token

1. Gọi `POST /api/auth/signin` với email và password
2. Lấy `token` từ response
3. Lưu token vào localStorage/sessionStorage
4. Gửi token trong mỗi request authenticated

### 3. Response Format

Tất cả API response tuân theo format:

```json
{
	"code": 1000,
	"message": "Optional message",
	"result": {
		"data": "..."
	}
}
```

- **code:** Mã trạng thái API
  - `1000`: Thành công
  - `100`: Chưa xác thực
  - `101`: Không có quyền
  - `103`: Dữ liệu không hợp lệ
  - `666`: User đã tồn tại
  - `777`: User không tồn tại
  - `999`: Lỗi không xác định

- **message:** Thông báo (có thể không có)
- **result:** Dữ liệu trả về (có thể null nếu thất bại)

---

## 🔐 User Roles

Các endpoint yêu cầu quyền hạn cụ thể:

| Role | Mô tả |
|------|-------|
| **USER** | Người dùng thông thường |
| **STAFF** | Nhân viên/Admin |

---

## 📋 Common Patterns

### 1. Request Body Validation

Mỗi request body sẽ được validate:
- **Required fields:** Không được để trống
- **Type validation:** Kiểm tra kiểu dữ liệu
- **Length validation:** Kiểm tra độ dài chuỗi
- **Format validation:** Kiểm tra định dạng (email, URL, etc)

### 2. Error Handling

Khi có lỗi, API trả về:

```json
{
	"code": 103,
	"message": "Error message",
	"result": null
}
```

Mô tả lỗi sẽ trong field `message`.

### 3. Pagination

Một số endpoint hỗ trợ pagination:

```
GET /api/products?page=1&size=10
```

---

## 💡 Usage Examples

### Login Flow

```javascript
// 1. Đăng nhập
const response = await fetch('http://localhost:8080/api/auth/signin', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    email: 'user@example.com',
    password: 'password123'
  })
});

const data = await response.json();
const token = data.result.token;

// 2. Lưu token
localStorage.setItem('token', token);

// 3. Sử dụng token trong request sau
const headers = new Headers();
headers.append('Authorization', `Bearer ${token}`);
headers.append('Content-Type', 'application/json');
```

### Get User Info

```javascript
const token = localStorage.getItem('token');

const response = await fetch('http://localhost:8080/api/users/myInfo', {
  headers: {
    'Authorization': `Bearer ${token}`
  }
});

const data = await response.json();
console.log(data.result); // Thông tin user
```

### Add Product to Cart

```javascript
const token = localStorage.getItem('token');

const response = await fetch('http://localhost:8080/api/cart/items', {
  method: 'POST',
  headers: {
    'Authorization': `Bearer ${token}`,
    'Content-Type': 'application/json'
  },
  body: JSON.stringify({
    productId: 'product-id-here',
    size: 'M',
    color: 'RED',
    quantity: 1
  })
});

const data = await response.json();
console.log(data.result); // Giỏ hàng được cập nhật
```

---

## 🛠️ Common Issues & Solutions

### Issue 1: 401 Unauthorized

**Nguyên nhân:** Token hết hạn hoặc không hợp lệ

**Giải pháp:**
1. Kiểm tra token có trong localStorage không
2. Token có format đúng không: `Bearer <token>`
3. Đăng nhập lại để lấy token mới

### Issue 2: 403 Forbidden

**Nguyên nhân:** User không có quyền thực hiện action

**Giải pháp:**
1. Kiểm tra user có role STAFF không
2. Một số action chỉ STAFF mới thực hiện được

### Issue 3: 400 Bad Request

**Nguyên nhân:** Dữ liệu request không hợp lệ

**Giải pháp:**
1. Kiểm tra tất cả required fields
2. Kiểm tra kiểu dữ liệu
3. Kiểm tra định dạng (email, URL, etc)

### Issue 4: Email Already Exists (666)

**Nguyên nhân:** Email đã được đăng ký

**Giải pháp:**
1. Sử dụng email khác
2. Hoặc đăng nhập với email đó

---

## 📞 Support

Nếu gặp bất kỳ vấn đề nào, vui lòng:

1. Kiểm tra lại request format
2. Xem các example trong documentation
3. Kiểm tra error code và message
4. Liên hệ với team backend

---

## 🔗 Related Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [JWT Documentation](https://jwt.io/)
- [RESTful API Best Practices](https://restfulapi.net/)

---

**Last Updated:** March 9, 2026  
**Version:** 1.0  
**Status:** ✅ Active

