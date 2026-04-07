# Order Module

## Order API Documentation

---

### POST /api/orders

> Tạo đơn hàng mới từ giỏ hàng.

#### Headers Required

```
Authorization: Bearer <token>
```

#### Request Body Fields

| Tên trường     | Loại   | Bắt buộc | Mô tả                                  |
| :------------- | :----- | :------- | :------------------------------------- |
| receiverName   | string | Có       | Tên người nhận hàng                    |
| receiverPhone  | string | Có       | Số điện thoại người nhận               |
| shippingAddress| string | Có       | Địa chỉ giao hàng                      |
| paymentMethod  | string | Có       | Phương thức thanh toán (COD/BANKING)   |

#### Ví dụ Request Body

```json
{
	"receiverName": "Nguyễn Văn A",
	"receiverPhone": "0123456789",
	"shippingAddress": "123 Đường ABC, TP.HCM",
	"paymentMethod": "COD"
}
```

#### Ví dụ Response (201 Created)

```json
{
	"code": 1000,
	"result": {
		"id": "67a1234567...",
		"userId": "67a1234500...",
		"receiverName": "Nguyễn Văn A",
		"receiverPhone": "0123456789",
		"shippingAddress": "123 Đường ABC, TP.HCM",
		"items": [
			{
				"productId": "67a1234567...",
				"productName": "Áo thun trẻ em",
				"size": "M",
				"color": "RED",
				"quantity": 2,
				"price": 150000
			}
		],
		"totalAmount": 330000,
		"shippingFee": 30000,
		"paymentMethod": "COD",
		"status": "PENDING_DELIVERY",
		"createdAt": "2026-03-09T10:30:00"
	}
}
```

#### Ví dụ Response (400 Bad Request - Giỏ hàng trống)

```json
{
	"code": 103,
	"message": "Không có sản phẩm trong giỏ hàng",
	"result": null
}
```

---

### GET /api/orders/my-orders

> Lấy danh sách đơn hàng của user đang đăng nhập.

#### Headers Required

```
Authorization: Bearer <token>
```

#### Ví dụ Response (200 OK)

```json
{
	"code": 1000,
	"result": [
		{
			"id": "67a1234567...",
			"userId": "67a1234500...",
			"receiverName": "Nguyễn Văn A",
			"receiverPhone": "0123456789",
			"shippingAddress": "123 Đường ABC, TP.HCM",
			"items": [],
			"totalAmount": 330000,
			"shippingFee": 30000,
			"paymentMethod": "COD",
			"status": "PENDING_DELIVERY",
			"createdAt": "2026-03-09T10:30:00"
		}
	]
}
```

---

### GET /api/orders/{orderId}

> Lấy chi tiết đơn hàng (User sở hữu hoặc STAFF có quyền).

#### Headers Required

```
Authorization: Bearer <token>
```

#### Path Parameters

| Tên     | Loại   | Mô tả          |
| :------ | :----- | :------------- |
| orderId | string | ID đơn hàng    |

#### Ví dụ Response (200 OK)

```json
{
	"code": 1000,
	"result": {
		"id": "67a1234567...",
		"userId": "67a1234500...",
		"receiverName": "Nguyễn Văn A",
		"receiverPhone": "0123456789",
		"shippingAddress": "123 Đường ABC, TP.HCM",
		"items": [
			{
				"productId": "67a1234567...",
				"productName": "Áo thun trẻ em",
				"size": "M",
				"color": "RED",
				"quantity": 2,
				"price": 150000
			}
		],
		"totalAmount": 330000,
		"shippingFee": 30000,
		"paymentMethod": "COD",
		"status": "PENDING_DELIVERY",
		"createdAt": "2026-03-09T10:30:00"
	}
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

### DELETE /api/orders/{orderId}

> Hủy đơn hàng (User sở hữu hoặc STAFF có quyền).

#### Headers Required

```
Authorization: Bearer <token>
```

#### Path Parameters

| Tên     | Loại   | Mô tả              |
| :------ | :----- | :----------------- |
| orderId | string | ID đơn hàng cần hủy |

#### Ví dụ Response (200 OK)

```json
{
	"code": 1000,
	"message": "Hủy đơn hàng thành công"
}
```

#### Ví dụ Response (400 Bad Request - Không thể hủy)

```json
{
	"code": 103,
	"message": "Không thể hủy đơn hàng đã hoàn thành",
	"result": null
}
```

---

### GET /api/orders/status/{status}

> Lấy danh sách đơn hàng theo trạng thái (Chỉ STAFF có quyền).

#### Headers Required

```
Authorization: Bearer <token> (STAFF role)
```

#### Path Parameters

| Tên    | Loại   | Mô tả                              |
| :----- | :----- | :--------------------------------- |
| status | string | Trạng thái (PENDING_PAYMENT, PENDING_DELIVERY, DELIVERING, COMPLETED, CANCELED) |

#### Ví dụ Response (200 OK)

```json
{
	"code": 1000,
	"result": [
		{
			"id": "67a1234567...",
			"userId": "67a1234500...",
			"receiverName": "Nguyễn Văn A",
			"shippingAddress": "123 Đường ABC, TP.HCM",
			"totalAmount": 330000,
			"status": "PENDING_DELIVERY",
			"createdAt": "2026-03-09T10:30:00"
		}
	]
}
```

---

### PUT /api/orders/{orderId}/status

> Cập nhật trạng thái đơn hàng (Chỉ STAFF có quyền).

#### Headers Required

```
Authorization: Bearer <token> (STAFF role)
```

#### Path Parameters

| Tên     | Loại   | Mô tả          |
| :------ | :----- | :------------- |
| orderId | string | ID đơn hàng    |

#### Query Parameters

| Tên    | Loại   | Bắt buộc | Mô tả                              |
| :----- | :----- | :------- | :--------------------------------- |
| status | string | Có       | Trạng thái mới (PENDING_PAYMENT, PENDING_DELIVERY, DELIVERING, COMPLETED, CANCELED) |

#### Ví dụ Request

```
PUT /api/orders/67a1234567.../status?status=DELIVERING
```

#### Ví dụ Response (200 OK)

```json
{
	"code": 1000,
	"result": {
		"id": "67a1234567...",
		"userId": "67a1234500...",
		"receiverName": "Nguyễn Văn A",
		"shippingAddress": "123 Đường ABC, TP.HCM",
			"items": [],
		"totalAmount": 330000,
		"shippingFee": 30000,
		"paymentMethod": "COD",
		"status": "DELIVERING",
		"createdAt": "2026-03-09T10:30:00"
	}
}
```

---

### GET /api/orders/statistics/revenue

> Thống kê doanh thu theo tháng (Chỉ STAFF có quyền).

#### Headers Required

```
Authorization: Bearer <token> (STAFF role)
```

#### Query Parameters

| Tên  | Loại | Bắt buộc | Mô tả         |
| :--- | :--- | :------- | :------------ |
| year | int  | Có       | Năm thống kê  |

#### Ví dụ Request

```
GET /api/orders/statistics/revenue?year=2026
```

#### Ví dụ Response (200 OK)

```json
{
	"code": 1000,
	"result": [
		{
			"month": 1,
			"year": 2026,
			"totalRevenue": 10000000,
			"totalOrders": 50
		},
		{
			"month": 2,
			"year": 2026,
			"totalRevenue": 12000000,
			"totalOrders": 60
		}
	]
}
```

---

### GET /api/orders/statistics/orders

> Lấy danh sách thống kê đơn hàng trong khoảng thời gian (dùng cho việc vẽ biểu đồ — mỗi mục tương ứng một đơn hàng với thông tin doanh thu và thời điểm tạo). Chỉ STAFF có quyền truy cập.

#### Headers Required

```
Authorization: Bearer <token> (STAFF role)
```

#### Query Parameters

| Tên  | Loại   | Bắt buộc | Mô tả |
| :--- | :----- | :------- | :---- |
| from | string | Có       | Thời điểm bắt đầu (ISO-8601, ví dụ: 2026-01-01T00:00:00) |
| to   | string | Có       | Thời điểm kết thúc (ISO-8601, ví dụ: 2026-03-31T23:59:59) |

> Lưu ý: server sử dụng `LocalDateTime.parse(...)` trong controller — đảm bảo format đúng (yyyy-MM-dd'T'HH:mm:ss). Nếu cần timezone, frontend nên truyền theo UTC hoặc server sẽ dùng LocalDateTime không kèm timezone.

#### Ví dụ Request

```
GET /api/orders/statistics/orders?from=2026-01-01T00:00:00&to=2026-03-31T23:59:59
Authorization: Bearer eyJ... (STAFF)
```

#### Ví dụ Response (200 OK)

```json
{
  "code": 1000,
  "result": [
    {
      "totalRevenue": 330000,
      "createdAt": "2026-03-09T10:30:00"
    },
    {
      "totalRevenue": 150000,
      "createdAt": "2026-03-08T14:20:00"
    }
  ]
}
```

#### Mô tả trường trả về

| Tên | Loại | Mô tả |
|-----|------|-------|
| totalRevenue | number | Tổng giá trị đơn hàng (totalAmount) tại thời điểm createdAt |
| createdAt | string (ISO) | Thời gian tạo đơn hàng (dùng để vẽ trục X cho biểu đồ) |

#### Ví dụ sử dụng

- Frontend có thể nhóm hoặc gộp theo ngày/tháng để vẽ biểu đồ (sum totalRevenue theo ngày). Endpoint trả về list đơn hàng; nếu cần dữ liệu đã gộp, frontend hoặc backend có thể thực hiện bước gộp thêm.

#### Ví dụ Response (400 Bad Request - Thiếu/parse lỗi)

```json
{
  "code": 103,
  "message": "Invalid date format for 'from' or 'to'",
  "result": null
}
```


### GET /api/orders/statistics/top-users

> Lấy danh sách khách hàng chi tiêu cao nhất (Chỉ STAFF có quyền).

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
			"userId": "67a1234500...",
			"totalSpent": 5000000
		},
		{
			"userId": "67a1234501...",
			"totalSpent": 3000000
		}
	]
}
```

---

### GET /api/orders/statistics/top-products

> Lấy danh sách sản phẩm bán chạy nhất (Chỉ STAFF có quyền).

#### Headers Required

```
Authorization: Bearer <token> (STAFF role)
```

#### Query Parameters

| Tên       | Loại   | Bắt buộc | Mô tả                    |
| :-------- | :----- | :------- | :----------------------- |
| startDate | string | Có       | Ngày bắt đầu (ISO format)|
| endDate   | string | Có       | Ngày kết thúc (ISO format)|
| limit     | int    | Không    | Số lượng (default: 10)   |

#### Ví dụ Request

```
GET /api/orders/statistics/top-products?startDate=2026-01-01T00:00:00&endDate=2026-03-09T23:59:59&limit=5
```

#### Ví dụ Response (200 OK)

```json
{
	"code": 1000,
	"result": [
		{
			"productId": "67a1234567...",
			"productName": "Áo thun trẻ em",
			"totalQuantitySold": 150
		},
		{
			"productId": "67a1234568...",
			"productName": "Quần jean trẻ em",
			"totalQuantitySold": 120
		}
	]
}
```

---

## Order Statuses

| Status | Mô tả |
|--------|-------|
| PENDING_PAYMENT | Chờ thanh toán |
| PENDING_DELIVERY | Chờ giao hàng |
| DELIVERING | Đang giao |
| COMPLETED | Hoàn thành |
| CANCELED | Hủy |

---

## Error Codes

| Code | Status | Message | Ý nghĩa |
|------|--------|---------|---------|
| 1000 | 200 | Success | Thành công |
| 100 | 401 | UNAUTHENTICATION | Chưa xác thực |
| 101 | 403 | UNAUTHORIZED | Không có quyền |
| 103 | 400 | INVALID_KEY | Dữ liệu không hợp lệ |
| 999 | 500 | UNCATEGORIZED | Lỗi không xác định |


