# Category Module

## Category API Documentation

---

### GET /api/categories

> Lấy danh sách tất cả danh mục (Public - không cần token).

#### Ví dụ Response (200 OK)

```json
{
	"code": 1000,
	"result": [
		{
			"id": "67a1234567...",
			"name": "Áo trẻ em",
			"description": "Các loại áo dành cho trẻ em"
		},
		{
			"id": "67a1234568...",
			"name": "Quần trẻ em",
			"description": "Các loại quần dành cho trẻ em"
		}
	]
}
```

---

### GET /api/categories/{id}

> Lấy chi tiết danh mục theo ID (Public - không cần token).

#### Path Parameters

| Tên | Loại   | Mô tả          |
| :-- | :----- | :------------- |
| id  | string | ID danh mục    |

#### Ví dụ Response (200 OK)

```json
{
	"code": 1000,
	"result": {
		"id": "67a1234567...",
		"name": "Áo trẻ em",
		"description": "Các loại áo dành cho trẻ em"
	}
}
```

#### Ví dụ Response (404 Not Found)

```json
{
	"code": 999,
	"message": "Danh mục không tồn tại",
	"result": null
}
```

---

### POST /api/categories

> Tạo danh mục mới (Chỉ STAFF có quyền).

#### Headers Required

```
Authorization: Bearer <token> (STAFF role)
```

#### Request Body Fields

| Tên trường  | Loại   | Bắt buộc | Mô tả                        |
| :---------- | :----- | :------- | :--------------------------- |
| name        | string | Có       | Tên danh mục                 |
| description | string | Không    | Mô tả danh mục               |

#### Ví dụ Request Body

```json
{
	"name": "Quần áo thể thao",
	"description": "Các loại quần áo dành cho hoạt động thể thao"
}
```

#### Ví dụ Response (201 Created)

```json
{
	"code": 1000,
	"result": {
		"id": "67a1234569...",
		"name": "Quần áo thể thao",
		"description": "Các loại quần áo dành cho hoạt động thể thao"
	}
}
```

#### Ví dụ Response (400 Bad Request)

```json
{
	"code": 103,
	"message": "Tên danh mục không được để trống",
	"result": null
}
```

#### Ví dụ Response (403 Forbidden - Không có quyền)

```json
{
	"code": 101,
	"message": "Your account do not have permission",
	"result": null
}
```

---

### PUT /api/categories/{id}

> Cập nhật danh mục (Chỉ STAFF có quyền).

#### Headers Required

```
Authorization: Bearer <token> (STAFF role)
```

#### Path Parameters

| Tên | Loại   | Mô tả              |
| :-- | :----- | :----------------- |
| id  | string | ID danh mục cần cập nhật |

#### Request Body Fields

| Tên trường  | Loại   | Bắt buộc | Mô tả                    |
| :---------- | :----- | :------- | :----------------------- |
| name        | string | Không    | Tên danh mục mới         |
| description | string | Không    | Mô tả danh mục mới       |

#### Ví dụ Request Body

```json
{
	"name": "Quần áo thể thao - Updated",
	"description": "Cập nhật mô tả"
}
```

#### Ví dụ Response (200 OK)

```json
{
	"code": 1000,
	"result": {
		"id": "67a1234569...",
		"name": "Quần áo thể thao - Updated",
		"description": "Cập nhật mô tả"
	}
}
```

---

### DELETE /api/categories/{id}

> Xóa danh mục (Chỉ STAFF có quyền).

#### Headers Required

```
Authorization: Bearer <token> (STAFF role)
```

#### Path Parameters

| Tên | Loại   | Mô tả          |
| :-- | :----- | :------------- |
| id  | string | ID danh mục cần xóa |

#### Ví dụ Response (200 OK)

```json
{
	"code": 1000,
	"message": "Xóa danh mục thành công"
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
| 999 | 500 | UNCATEGORIZED | Lỗi không xác định |

---

## Usage Examples

### Lấy danh sách danh mục (Frontend)

```javascript
// GET danh sách danh mục
fetch('http://localhost:8080/api/categories')
  .then(response => response.json())
  .then(data => console.log(data.result))
  .catch(error => console.error('Error:', error));
```

### Tạo danh mục mới (Admin/Staff)

```javascript
// POST tạo danh mục
const token = localStorage.getItem('token');

fetch('http://localhost:8080/api/categories', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${token}`
  },
  body: JSON.stringify({
    name: 'Quần áo thể thao',
    description: 'Các loại quần áo dành cho hoạt động thể thao'
  })
})
  .then(response => response.json())
  .then(data => console.log(data.result))
  .catch(error => console.error('Error:', error));
```


