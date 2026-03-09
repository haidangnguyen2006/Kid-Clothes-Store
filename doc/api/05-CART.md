# Cart Module

## Cart API Documentation

---

### GET /api/cart

> Lấy giỏ hàng của user đang đăng nhập.

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
		"userId": "67a1234500...",
		"items": [
			{
				"productId": "67a1234567...",
				"productName": "Áo thun trẻ em",
				"imageUrl": "https://example.com/img1.jpg",
				"size": "M",
				"color": "RED",
				"quantity": 2,
				"price": 150000,
				"subTotal": 300000
			}
		],
		"totalAmount": 300000
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

### POST /api/cart/items

> Thêm sản phẩm vào giỏ hàng.

#### Headers Required

```
Authorization: Bearer <token>
```

#### Request Body Fields

| Tên trường | Loại   | Bắt buộc | Mô tả                     |
| :--------- | :----- | :------- | :------------------------ |
| productId  | string | Có       | ID sản phẩm               |
| size       | string | Có       | Size sản phẩm (S, M, L)   |
| color      | string | Có       | Màu sản phẩm              |
| quantity   | int    | Có       | Số lượng (≥ 1)            |

#### Ví dụ Request Body

```json
{
	"productId": "67a1234567...",
	"size": "M",
	"color": "RED",
	"quantity": 2
}
```

#### Ví dụ Response (200 OK)

```json
{
	"code": 1000,
	"result": {
		"id": "67a1234567...",
		"userId": "67a1234500...",
		"items": [
			{
				"productId": "67a1234567...",
				"productName": "Áo thun trẻ em",
				"imageUrl": "https://example.com/img1.jpg",
				"size": "M",
				"color": "RED",
				"quantity": 2,
				"price": 150000,
				"subTotal": 300000
			}
		],
		"totalAmount": 300000
	}
}
```

#### Ví dụ Response (400 Bad Request - Vượt quá tồn kho)

```json
{
	"code": 103,
	"message": "Vượt quá số lượng tồn kho. Sản phẩm chỉ còn 5 món",
	"result": null
}
```

---

### PUT /api/cart/items/{productId}

> Cập nhật số lượng sản phẩm trong giỏ hàng.

#### Headers Required

```
Authorization: Bearer <token>
```

#### Path Parameters

| Tên       | Loại   | Mô tả          |
| :-------- | :----- | :------------- |
| productId | string | ID sản phẩm    |

#### Query Parameters

| Tên      | Loại   | Bắt buộc | Mô tả                  |
| :------- | :----- | :------- | :--------------------- |
| size     | string | Có       | Size sản phẩm          |
| color    | string | Có       | Màu sản phẩm           |
| quantity | int    | Có       | Số lượng mới (≥ 1)     |

#### Ví dụ Request

```
PUT /api/cart/items/67a1234567...?size=M&color=RED&quantity=3
```

#### Ví dụ Response (200 OK)

```json
{
	"code": 1000,
	"result": {
		"id": "67a1234567...",
		"userId": "67a1234500...",
		"items": [
			{
				"productId": "67a1234567...",
				"productName": "Áo thun trẻ em",
				"imageUrl": "https://example.com/img1.jpg",
				"size": "M",
				"color": "RED",
				"quantity": 3,
				"price": 150000,
				"subTotal": 450000
			}
		],
		"totalAmount": 450000
	}
}
```

#### Ví dụ Response (400 Bad Request)

```json
{
	"code": 103,
	"message": "Sản phẩm này không có trong giỏ hàng",
	"result": null
}
```

---

### DELETE /api/cart/items/{productId}

> Xóa sản phẩm khỏi giỏ hàng.

#### Headers Required

```
Authorization: Bearer <token>
```

#### Path Parameters

| Tên       | Loại   | Mô tả          |
| :-------- | :----- | :------------- |
| productId | string | ID sản phẩm    |

#### Query Parameters

| Tên   | Loại   | Bắt buộc | Mô tả         |
| :---- | :----- | :------- | :------------ |
| size  | string | Có       | Size sản phẩm |
| color | string | Có       | Màu sản phẩm  |

#### Ví dụ Request

```
DELETE /api/cart/items/67a1234567...?size=M&color=RED
```

#### Ví dụ Response (200 OK)

```json
{
	"code": 1000,
	"result": {
		"id": "67a1234567...",
		"userId": "67a1234500...",
		"items": [],
		"totalAmount": 0
	}
}
```

---

### DELETE /api/cart

> Xóa toàn bộ giỏ hàng.

#### Headers Required

```
Authorization: Bearer <token>
```

#### Ví dụ Response (200 OK)

```json
{
	"code": 1000,
	"message": "Giỏ hàng đã được xóa"
}
```

---

## Error Codes

| Code | Status | Message | Ý nghĩa |
|------|--------|---------|---------|
| 1000 | 200 | Success | Thành công |
| 100 | 401 | UNAUTHENTICATION | Chưa xác thực |
| 103 | 400 | INVALID_KEY | Dữ liệu không hợp lệ |
| 999 | 500 | UNCATEGORIZED | Lỗi không xác định |

---

## Usage Examples

### Thêm sản phẩm vào giỏ (Frontend)

```javascript
const token = localStorage.getItem('token');

fetch('http://localhost:8080/api/cart/items', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${token}`
  },
  body: JSON.stringify({
    productId: '67a1234567...',
    size: 'M',
    color: 'RED',
    quantity: 2
  })
})
  .then(response => response.json())
  .then(data => console.log(data.result))
  .catch(error => console.error('Error:', error));
```

### Cập nhật số lượng sản phẩm trong giỏ

```javascript
const token = localStorage.getItem('token');
const productId = '67a1234567...';

fetch(`http://localhost:8080/api/cart/items/${productId}?size=M&color=RED&quantity=3`, {
  method: 'PUT',
  headers: {
    'Authorization': `Bearer ${token}`
  }
})
  .then(response => response.json())
  .then(data => console.log(data.result))
  .catch(error => console.error('Error:', error));
```

### Xóa sản phẩm khỏi giỏ

```javascript
const token = localStorage.getItem('token');
const productId = '67a1234567...';

fetch(`http://localhost:8080/api/cart/items/${productId}?size=M&color=RED`, {
  method: 'DELETE',
  headers: {
    'Authorization': `Bearer ${token}`
  }
})
  .then(response => response.json())
  .then(data => console.log('Sản phẩm đã được xóa'))
  .catch(error => console.error('Error:', error));
```


