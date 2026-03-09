# User Module

## User API Documentation

---

### POST /api/users

> Tạo tài khoản người dùng mới (Đăng ký).

#### Request Body Fields

| Tên trường | Loại   | Bắt buộc | Mô tả                                    |
| :--------- | :----- | :------- | :--------------------------------------- |
| fullName   | string | Có       | Họ tên người dùng                        |
| email      | string | Có       | Email (phải là email hợp lệ)             |
| password   | string | Có       | Mật khẩu (tối thiểu 8 ký tự)             |
| phone      | string | Không    | Số điện thoại                            |
| address    | string | Không    | Địa chỉ                                  |

#### Ví dụ Request Body

```json
{
	"fullName": "Nguyễn Văn A",
	"email": "user@example.com",
	"password": "password123",
	"phone": "0123456789",
	"address": "123 Đường ABC, TP.HCM"
}
```

#### Ví dụ Response (201 Created)

```json
{
	"code": 1000,
	"result": {
		"id": "67a1234567...",
		"fullName": "Nguyễn Văn A",
		"email": "user@example.com",
		"phone": "0123456789",
		"address": "123 Đường ABC, TP.HCM",
		"role": "USER"
	}
}
```

#### Ví dụ Response (400 Bad Request - Email đã tồn tại)

```json
{
	"code": 666,
	"message": "User existed!!",
	"result": null
}
```

---

### GET /api/users/myInfo

> Lấy thông tin cá nhân của user đang đăng nhập.

#### Headers Required

```
Authorization: Bearer <token>
```

#### Ví dụ Response (200 OK)

```json
{
	"code": 1000,
	"result": {
		"id": "67a1234567...",
		"fullName": "Nguyễn Văn A",
		"email": "user@example.com",
		"phone": "0123456789",
		"address": "123 Đường ABC, TP.HCM",
		"role": "USER"
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

### GET /api/users/{email}

> Lấy thông tin người dùng theo email (Chỉ STAFF có quyền).

#### Path Parameters

| Tên         | Loại   | Mô tả          |
| :---------- | :----- | :------------- |
| email       | string | Email cần tìm   |

#### Headers Required

```
Authorization: Bearer <token> (STAFF role)
```

#### Ví dụ Response (200 OK)

```json
{
	"code": 1000,
	"result": {
		"id": "67a1234567...",
		"fullName": "Nguyễn Văn A",
		"email": "user@example.com",
		"phone": "0123456789",
		"address": "123 Đường ABC, TP.HCM",
		"role": "USER"
	}
}
```

#### Ví dụ Response (404 Not Found)

```json
{
	"code": 777,
	"message": "User not existed!!",
	"result": null
}
```

---

### PUT /api/users/myInfo

> Cập nhật thông tin cá nhân của user đang đăng nhập.

#### Headers Required

```
Authorization: Bearer <token>
```

#### Request Body Fields

| Tên trường | Loại   | Bắt buộc | Mô tả                         |
| :--------- | :----- | :------- | :---------------------------- |
| fullName   | string | Không    | Họ tên mới                    |
| password   | string | Không    | Mật khẩu mới (8+ ký tự)       |
| phone      | string | Không    | Số điện thoại mới             |
| address    | string | Không    | Địa chỉ mới                   |

#### Ví dụ Request Body

```json
{
	"fullName": "Nguyễn Văn A Updated",
	"phone": "0987654321",
	"address": "456 Đường XYZ, TP.HCM"
}
```

#### Ví dụ Response (200 OK)

```json
{
	"code": 1000,
	"result": {
		"id": "67a1234567...",
		"fullName": "Nguyễn Văn A Updated",
		"email": "user@example.com",
		"phone": "0987654321",
		"address": "456 Đường XYZ, TP.HCM",
		"role": "USER"
	}
}
```

---

### PUT /api/users/change-password

> Đổi mật khẩu người dùng.

#### Headers Required

```
Authorization: Bearer <token>
```

#### Request Body Fields

| Tên trường      | Loại   | Bắt buộc | Mô tả                      |
| :-------------- | :----- | :------- | :------------------------- |
| oldPassword     | string | Có       | Mật khẩu cũ                |
| newPassword     | string | Có       | Mật khẩu mới (8+ ký tự)    |
| confirmPassword | string | Có       | Xác nhận mật khẩu mới      |

#### Ví dụ Request Body

```json
{
	"oldPassword": "oldPassword123",
	"newPassword": "newPassword456",
	"confirmPassword": "newPassword456"
}
```

#### Ví dụ Response (200 OK)

```json
{
	"code": 1000,
	"message": "Đổi mật khẩu thành công"
}
```

#### Ví dụ Response (400 Bad Request - Mật khẩu cũ sai)

```json
{
	"code": 103,
	"message": "Mật khẩu cũ không chính xác",
	"result": null
}
```

#### Ví dụ Response (400 Bad Request - Mật khẩu không khớp)

```json
{
	"code": 104,
	"message": "PASSWORD_INVALID",
	"result": null
}
```

---

### PUT /api/users/{email}

> Cập nhật thông tin người dùng (Chỉ STAFF có quyền).

#### Headers Required

```
Authorization: Bearer <token> (STAFF role)
```

#### Path Parameters

| Tên   | Loại   | Mô tả                 |
| :---- | :----- | :-------------------- |
| email | string | Email người dùng cần cập nhật |

#### Request Body Fields

| Tên trường | Loại   | Bắt buộc | Mô tả                    |
| :--------- | :----- | :------- | :----------------------- |
| fullName   | string | Không    | Họ tên                   |
| password   | string | Không    | Mật khẩu (8+ ký tự)      |
| phone      | string | Không    | Số điện thoại            |
| address    | string | Không    | Địa chỉ                  |

#### Ví dụ Response (200 OK)

```json
{
	"code": 1000,
	"result": {
		"id": "67a1234567...",
		"fullName": "Nguyễn Văn A",
		"email": "user@example.com",
		"phone": "0123456789",
		"address": "123 Đường ABC, TP.HCM",
		"role": "USER"
	}
}
```

---

### DELETE /api/users/{email}

> Xóa người dùng (Chỉ STAFF có quyền).

#### Headers Required

```
Authorization: Bearer <token> (STAFF role)
```

#### Path Parameters

| Tên   | Loại   | Mô tả             |
| :---- | :----- | :---------------- |
| email | string | Email cần xóa     |

#### Ví dụ Response (200 OK)

```json
{
	"code": 1000,
	"message": "User deleted successfully"
}
```

---

### GET /api/users/search

> Tìm kiếm người dùng theo tên.

#### Headers Required

```
Authorization: Bearer <token>
```

#### Query Parameters

| Tên     | Loại   | Bắt buộc | Mô tả                |
| :------ | :----- | :------- | :------------------- |
| keyword | string | Có       | Từ khóa tìm kiếm      |

#### Ví dụ Request

```
GET /api/users/search?keyword=Nguyễn
```

#### Ví dụ Response (200 OK)

```json
{
	"code": 1000,
	"result": [
		{
			"id": "67a1234567...",
			"fullName": "Nguyễn Văn A",
			"email": "user1@example.com",
			"phone": "0123456789",
			"address": "123 Đường ABC, TP.HCM",
			"role": "USER"
		},
		{
			"id": "67a1234568...",
			"fullName": "Nguyễn Văn B",
			"email": "user2@example.com",
			"phone": "0987654321",
			"address": "456 Đường XYZ, TP.HCM",
			"role": "USER"
		}
	]
}
```

---

### GET /api/users

> Lấy danh sách tất cả người dùng (Chỉ STAFF có quyền).

#### Headers Required

```
Authorization: Bearer <token> (STAFF role)
```

#### Ví dụ Response (200 OK)

```json
{
	"code": 1000,
	"result": [
		{
			"id": "67a1234567...",
			"fullName": "Nguyễn Văn A",
			"email": "user1@example.com",
			"phone": "0123456789",
			"address": "123 Đường ABC, TP.HCM",
			"role": "USER"
		}
	]
}
```

---

### GET /api/users/role/{role}

> Lấy danh sách người dùng theo vai trò (Chỉ STAFF có quyền).

#### Headers Required

```
Authorization: Bearer <token> (STAFF role)
```

#### Path Parameters

| Tên  | Loại   | Mô tả               |
| :--- | :----- | :------------------ |
| role | string | Vai trò (USER/STAFF) |

#### Ví dụ Response (200 OK)

```json
{
	"code": 1000,
	"result": [
		{
			"id": "67a1234567...",
			"fullName": "Staff Member",
			"email": "staff@example.com",
			"phone": "0123456789",
			"address": "123 Đường ABC, TP.HCM",
			"role": "STAFF"
		}
	]
}
```

---

## Error Codes

| Code | Status | Message | Ý nghĩa |
|------|--------|---------|---------|
| 1000 | 200 | Success | Thành công |
| 100 | 401 | UNAUTHENTICATION | Chưa xác thực |
| 101 | 403 | UNAUTHORIZED | Không có quyền |
| 103 | 400 | INVALID_KEY | Dữ liệu không hợp lệ |
| 104 | 400 | PASSWORD_INVALID | Mật khẩu không hợp lệ |
| 666 | 400 | USER_EXISTED | Email đã tồn tại |
| 777 | 404 | USER_UN_EXISTED | Người dùng không tồn tại |


