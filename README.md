# KidClothes — Ứng dụng E‑commerce bán quần áo trẻ em

[![Project Status](https://img.shields.io/badge/status-ready-brightgreen.svg)]()
[![Language](https://img.shields.io/badge/language-java-red.svg)]()
[![License](https://img.shields.io/badge/license-MIT-blue.svg)]()


Một ứng dụng backend mẫu (Spring Boot + MongoDB) cho cửa hàng quần áo trẻ em. Dự án được thiết kế để minh họa các luồng nghiệp vụ thực tế: quản lý sản phẩm, danh mục, giỏ hàng, đơn hàng, người dùng, và báo cáo kinh doanh.

Phiên bản hiện tại: **v1.2.0** — hoàn thiện phần lớn các usecase cốt lõi, kèm API documentation cho frontend.

---

## Mục lục
- [Tính năng chính](#tính-năng-chính)
- [Kiến trúc & Công nghệ](#kiến-trúc--công-nghệ)
- [Cài đặt nhanh (Quick Start)](#cài-đặt-nhanh-quick-start)
- [Cấu hình môi trường](#cấu-hình-môi-trường)
- [Chạy & kiểm thử](#chạy--kiểm-thử)
- [Tài liệu API cho Frontend](#tài-liệu-api-cho-frontend)
- [Cấu trúc thư mục](#cấu-trúc-thư-mục)
- [Đóng góp](#đóng-góp)
- [Giấy phép](#giấy-phép)
- [Liên hệ](#liên-hệ)

---

## Tính năng chính
- Quản lý người dùng: đăng ký, đăng nhập (JWT), xem/cập nhật profile, đổi mật khẩu.
- Quản lý sản phẩm & danh mục: CRUD cho sản phẩm, phân loại (size/color), ảnh, lọc và tìm kiếm.
- Giỏ hàng: thêm/xóa/sửa số lượng, kiểm tra tồn kho.
- Đơn hàng: tạo đơn từ giỏ hàng, hủy đơn, cập nhật trạng thái (STAFF), báo cáo doanh thu.
- Thống kê: doanh thu theo tháng, top khách hàng, top sản phẩm.
- Bảo mật: JWT, role-based authorization (USER / STAFF), password hashing (BCrypt).
- Error handling chuẩn hoá: mọi response dùng chung `ApiRespone`.

---

## Kiến trúc & Công nghệ
- Backend: Spring Boot (Java)
- Database: MongoDB
- Authentication: JWT (OAuth2 resource server)
- Mapping DTO: MapStruct
- Validation: Jakarta Validation (Hibernate Validator)
- Build: Maven
- Các thư viện: Lombok, Spring Data MongoDB, Spring Security

Thiết kế theo mô hình nhiều lớp: Controller → Service → Repository + Mapper (DTO), kèm Global Exception Handler.

---

## Cài đặt nhanh (Quick Start)
Yêu cầu:
- Java 17+
- Maven 3.8+
- MongoDB đang chạy (mặc định: localhost:27017)

Clone repo và chạy:

```bash
git clone <repo-url>
cd kidclothes
mvn clean install
mvn spring-boot:run
```

API base URL mặc định: `http://localhost:8080/api`

---

## Cấu hình môi trường
Các cấu hình chính nằm trong `src/main/resources/application.properties`. Một vài biến cần chú ý:

- `spring.data.mongodb.uri` — URI kết nối MongoDB (vd. `mongodb://localhost:27017/kidclothes`)
- JWT signing key / issuer (structure trong `application.properties` hoặc config riêng khi deploy)

Bạn có thể tạo file `.env` hoặc thay đổi `application.properties` trực tiếp cho môi trường local.

---

## Chạy & kiểm thử
- Biên dịch và chạy: `mvn spring-boot:run`.
- Chạy unit tests: `mvn test`.
- Kiểm tra endpoints nhanh với Postman (xem `doc/api/POSTMAN_TEST_GUIDE.md`).

Một số lệnh hữu ích:

```bash
# Build
mvn clean install

# Chạy (development)
mvn spring-boot:run

# Chạy tests
mvn test
```

---

## Tài liệu API cho Frontend
Tài liệu API chi tiết (ví dụ request/response, header, error codes, ví dụ JS) đã được tạo sẵn trong thư mục:

```
doc/api/
```

Các file chính:
- `doc/api/01-AUTH.md` — Authentication
- `doc/api/02-USER.md` — User
- `doc/api/03-CATEGORY.md` — Category
- `doc/api/04-PRODUCT.md` — Product
- `doc/api/05-CART.md` — Cart
- `doc/api/06-ORDER.md` — Order
- `doc/api/README.md` — Hướng dẫn tổng quan cho frontend

Frontend developers hãy bắt đầu từ `doc/api/README.md` → module tương ứng.

---

## Cấu trúc thư mục (tóm tắt)
```
src/
├─ main/
│  ├─ java/com/iuh/kidclothes/
│  │  ├─ controller/        # REST controllers
│  │  ├─ service/           # Business logic
│  │  ├─ repository/        # Mongo repositories
│  │  ├─ mapper/            # MapStruct mappers
│  │  ├─ entity/            # Mongo documents
│  │  └─ configuration/     # Security, Mongo config
│  └─ resources/
│     └─ application.properties
```

---

## Hướng dẫn đóng góp
Rất hoan nghênh mọi đóng góp! Vui lòng tuân thủ:
1. Fork repo → Tạo branch feature: `feature/your-change`.
2. Viết bài test cho tính năng mới.
3. Mở Pull Request mô tả chi tiết thay đổi.
4. Tuân thủ coding style và commit message rõ ràng.

Có thể mở issue nếu muốn thảo luận trước khi implement.

---

## Ghi chú triển khai
- Trước khi deploy production, cần bổ sung: email verification, payment gateway tích hợp, monitoring & logging centralized, backup MongoDB.
- Kiểm tra kỹ cấu hình JWT & secrets trước khi đưa lên production.

---

## License
MIT © 2026 — Bạn có thể sử dụng, sửa đổi và phân phối theo điều khoản MIT.

---

## Liên hệ
Nếu có câu hỏi, liên hệ với nhóm phát triển tại: `dev-team@example.com` (thay bằng địa chỉ thực tế của bạn).

Cảm ơn bạn đã sử dụng KidClothes! ❤️

<!-- END README -->
