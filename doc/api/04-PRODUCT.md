# Product Module

## Product API Documentation

---

### GET /api/products

> Lấy danh sách tất cả sản phẩm (Public - không cần token).

#### Query Parameters (Optional)

| Tên  | Loại | Mô tả                 |
| :--- | :--- | :-------------------- |
| page | int  | Số trang (default: 1)  |
| size | int  | Số sản phẩm/trang      |

#### Ví dụ Response (200 OK)

```json
{
	"code": 1000,
	"result": [
		{
			"id": "67a1234567...",
			"name": "Áo thun trẻ em",
			"price": 150000,
			"categoryId": "67a1234560...",
			"description": "Áo thun chất lượng cao",
			"images": ["https://example.com/img1.jpg"],
			"variants": [
				{
					"size": "S",
					"color": "RED",
					"stock": 10
				}
			]
		}
	]
}
```

---

### GET /api/products/{id}

> Lấy chi tiết sản phẩm theo ID (Public - không cần token).

#### Path Parameters

| Tên | Loại   | Mô tả      |
| :-- | :----- | :--------- |
| id  | string | ID sản phẩm |

#### Ví dụ Response (200 OK)

```json
{
	"code": 1000,
	"result": {
		"id": "67a1234567...",
		"name": "Áo thun trẻ em",
		"price": 150000,
		"categoryId": "67a1234560...",
		"description": "Áo thun chất lượng cao",
		"images": ["https://example.com/img1.jpg"],
		"variants": [
			{
				"size": "S",
				"color": "RED",
				"stock": 10
			},
			{
				"size": "M",
				"color": "BLUE",
				"stock": 5
			}
		]
	}
}
```

---

### GET /api/products/search

> Tìm kiếm sản phẩm theo tên (Public - không cần token).

#### Query Parameters

| Tên     | Loại   | Bắt buộc | Mô tả              |
| :------ | :----- | :------- | :----------------- |
| keyword | string | Có       | Từ khóa tìm kiếm   |

#### Ví dụ Request

```
GET /api/products/search?keyword=áo
```

#### Ví dụ Response (200 OK)

```json
{
	"code": 1000,
	"result": [
		{
			"id": "67a1234567...",
			"name": "Áo thun trẻ em",
			"price": 150000,
			"categoryId": "67a1234560...",
			"description": "Áo thun chất lượng cao",
			"images": ["https://example.com/img1.jpg"],
			"variants": [...]
		}
	]
}
```

---

### GET /api/products/category/{categoryId}

> Lấy sản phẩm theo danh mục (Public - không cần token).

#### Path Parameters

| Tên        | Loại   | Mô tả              |
| :--------- | :----- | :----------------- |
| categoryId | string | ID danh mục        |

#### Ví dụ Response (200 OK)

```json
{
	"code": 1000,
	"result": [
		{
			"id": "67a1234567...",
			"name": "Áo thun trẻ em",
			"price": 150000,
			"categoryId": "67a1234560...",
			"description": "Áo thun chất lượng cao",
			"images": ["https://example.com/img1.jpg"],
			"variants": [...]
		}
	]
}
```

---

### GET /api/products/filter

> Lọc sản phẩm theo giá (Public - không cần token).

#### Query Parameters

| Tên      | Loại   | Bắt buộc | Mô tả              |
| :------- | :----- | :------- | :----------------- |
| minPrice | double | Có       | Giá tối thiểu      |
| maxPrice | double | Có       | Giá tối đa         |

#### Ví dụ Request

```
GET /api/products/filter?minPrice=100000&maxPrice=200000
```

#### Ví dụ Response (200 OK)

```json
{
	"code": 1000,
	"result": [
		{
			"id": "67a1234567...",
			"name": "Áo thun trẻ em",
			"price": 150000,
			"categoryId": "67a1234560...",
			"description": "Áo thun chất lượng cao",
			"images": ["https://example.com/img1.jpg"],
			"variants": [...]
		}
	]
}
```

---

### GET /api/products/trending

> Lấy sản phẩm bán chạy nhất (Public - không cần token).

#### Query Parameters (Optional)

| Tên   | Loại | Bắt buộc | Mô tả                     |
| :---- | :--- | :------- | :------------------------ |
| limit | int  | Không    | Số lượng (default: 10)    |

#### Ví dụ Request

```
GET /api/products/trending?limit=5
```

#### Ví dụ Response (200 OK)

```json
{
	"code": 1000,
	"result": [
		{
			"id": "67a1234567...",
			"name": "Áo thun bán chạy",
			"price": 150000,
			"categoryId": "67a1234560...",
			"description": "Sản phẩm hot nhất",
			"images": ["https://example.com/img1.jpg"],
			"variants": [...]
		}
	]
}
```

---

### POST /api/products

> Tạo sản phẩm mới (Chỉ STAFF có quyền).

#### Headers Required

```
Authorization: Bearer <token> (STAFF role)
```

#### Request Body Fields

| Tên trường | Loại   | Bắt buộc | Mô tả                     |
| :--------- | :----- | :------- | :------------------------ |
| name       | string | Có       | Tên sản phẩm              |
| price      | double | Có       | Giá sản phẩm              |
| categoryId | string | Có       | ID danh mục               |
| description| string | Không    | Mô tả sản phẩm            |
| images     | array  | Không    | Danh sách URL hình ảnh    |
| variants   | array  | Có       | Danh sách size/color/stock|

#### Ví dụ Request Body

```json
{
	"name": "Áo thun trẻ em",
	"price": 150000,
	"categoryId": "67a1234560...",
	"description": "Áo thun chất lượng cao",
	"images": ["https://example.com/img1.jpg"],
	"variants": [
		{
			"size": "S",
			"color": "RED",
			"stock": 10
		},
		{
			"size": "M",
			"color": "BLUE",
			"stock": 5
		}
	]
}
```

#### Ví dụ Response (201 Created)

```json
{
	"code": 1000,
	"result": {
		"id": "67a1234567...",
		"name": "Áo thun trẻ em",
		"price": 150000,
		"categoryId": "67a1234560...",
		"description": "Áo thun chất lượng cao",
		"images": ["https://example.com/img1.jpg"],
		"variants": [...]
	}
}
```

---

### PUT /api/products/{id}

> Cập nhật sản phẩm (Chỉ STAFF có quyền).

#### Headers Required

```
Authorization: Bearer <token> (STAFF role)
```

#### Path Parameters

| Tên | Loại   | Mô tả                  |
| :-- | :----- | :--------------------- |
| id  | string | ID sản phẩm cần cập nhật |

#### Request Body Fields

| Tên trường | Loại   | Bắt buộc | Mô tả                  |
| :--------- | :----- | :------- | :--------------------- |
| name       | string | Không    | Tên sản phẩm mới       |
| price      | double | Không    | Giá mới                |
| description| string | Không    | Mô tả mới              |
| images     | array  | Không    | Danh sách URL mới      |
| variants   | array  | Không    | Danh sách variants mới |

#### Ví dụ Request Body

```json
{
	"name": "Áo thun trẻ em - Updated",
	"price": 160000,
	"description": "Áo thun chất lượng cao - cập nhật"
}
```

#### Ví dụ Response (200 OK)

```json
{
	"code": 1000,
	"result": {
		"id": "67a1234567...",
		"name": "Áo thun trẻ em - Updated",
		"price": 160000,
		"categoryId": "67a1234560...",
		"description": "Áo thun chất lượng cao - cập nhật",
		"images": ["https://example.com/img1.jpg"],
		"variants": [...]
	}
}
```

---

### DELETE /api/products/{id}

> Xóa sản phẩm (Chỉ STAFF có quyền).

#### Headers Required

```
Authorization: Bearer <token> (STAFF role)
```

#### Path Parameters

| Tên | Loại   | Mô tả              |
| :-- | :----- | :----------------- |
| id  | string | ID sản phẩm cần xóa |

#### Ví dụ Response (200 OK)

```json
{
	"code": 1000,
	"message": "Xóa sản phẩm thành công"
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


