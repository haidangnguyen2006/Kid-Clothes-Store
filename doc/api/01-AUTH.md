# Auth Module

## Auth API Documentation

---

### POST /api/auth/signin

> Đăng nhập hệ thống, trả về JWT token và thông tin quyền.

#### Request Body Fields

| Tên trường | Loại   | Bắt buộc | Mô tả                        |
| :--------- | :----- | :------- | :--------------------------- |
| email      | string | Có       | Email của người dùng         |
| password   | string | Có       | Mật khẩu (tối thiểu 8 ký tự) |

#### Ví dụ Request Body

```json
{
	"email": "user@example.com",
	"password": "password123"
}
```

#### Ví dụ Response (200 OK)

```json
{
	"code": 1000,
	"message": "Success",
	"result": {
		"token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
		"authenticated": true,
		"type": "Bearer"
	}
}
```

#### Ví dụ Response (401 Unauthorized)

```json
{
    "code": 100,
    "message": "Your account cannot authenticate",
    "result": null
}
```

---

### POST /api/auth/introspect

> Xác minh token JWT, kiểm tra tính hợp lệ của token.

#### Request Body Fields

| Tên trường | Loại   | Bắt buộc | Mô tả                    |
| :--------- | :----- | :------- | :----------------------- |
| token      | string | Có       | JWT token cần xác minh    |

#### Ví dụ Request Body

```json
{
	"token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

#### Ví dụ Response (200 OK - Token hợp lệ)

```json
{
	"code": 1000,
	"message": "Token is valid",
	"result": true
}
```

#### Ví dụ Response (200 OK - Token không hợp lệ)

```json
{
	"code": 1000,
	"message": "Token is invalid",
	"result": false
}
```

---

## Headers Required

Để sử dụng các API cần authentication, thêm header:

```
Authorization: Bearer <token>
```

Ví dụ:
```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

---

## Error Codes

| Code | Status | Message | Ý nghĩa |
|------|--------|---------|---------|
| 1000 | 200 | Success | Thành công |
| 100 | 401 | UNAUTHENTICATION | Không xác thực |
| 101 | 403 | UNAUTHORIZED | Không có quyền truy cập |
| 104 | 400 | PASSWORD_INVALID | Mật khẩu không hợp lệ |
| 777 | 404 | USER_UN_EXISTED | Người dùng không tồn tại |

---

## Token Expiration

- **Access Token:** 1 giờ
- Sau khi hết hạn, client cần đăng nhập lại

---

## Security Best Practices

1. **Lưu token an toàn:** Lưu token vào secure storage (localStorage với encryption, sessionStorage, hay HTTP-only cookie)
2. **Gửi token:** Luôn gửi token trong Authorization header
3. **Token rotation:** Thay đổi token định kỳ
4. **Mật khẩu mạnh:** Sử dụng mật khẩu tối thiểu 8 ký tự với kết hợp chữ, số, ký tự đặc biệt


