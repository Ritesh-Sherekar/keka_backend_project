# 📋 Project Implementation Plan Any 

## 💡 Phase 1 – Employee CRUD + Role Management

### Entities
- **Employee**
- **Role**

### APIs

| Method | Endpoint                | Access       | Description          |
|--------|-------------------------|--------------|----------------------|
| GET    | `/api/employees`        | ADMIN/HR     | List all employees   |
| GET    | `/api/employees/{id}`   | ADMIN/HR     | Get employee details |
| POST   | `/api/employees`        | ADMIN/HR     | Create employee      |
| PUT    | `/api/employees/{id}`   | ADMIN/HR     | Update employee      |
| DELETE | `/api/employees/{id}`   | ADMIN/HR     | Soft delete employee |
| GET    | `/api/roles`            | ADMIN        | List all roles       |
| POST   | `/api/roles`            | ADMIN        | Create role          |

### Key Points
- Use Spring Data JPA annotations: `@Entity`, `@Repository`, `@Service`, `@RestController`
- Implement DTOs for requests and responses
- Add validation using `@Valid`, `@NotBlank`, `@Email`
- Use `ResponseEntity` for proper HTTP status codes

---

## 🔐 Phase 2 – Authentication & Authorization

### Technologies
- Spring Security + JWT

### Implementation Steps
1. Create **User** entity and repository
2. Implement `UserDetailsService` to load user by username
3. Add JWT filter:
   - Generate token on login
   - Validate token on each request
4. Configure `SecurityConfig`:
   - Permit all to `/api/auth/**` (login/register)
   - Secure `/api/employees/**` based on roles

### APIs

| Method | Endpoint              | Description                    |
|--------|-----------------------|--------------------------------|
| POST   | `/api/auth/register`  | Create user with role          |
| POST   | `/api/auth/login`     | Returns JWT token              |
| GET    | `/api/auth/me`        | Get logged-in user profile     |

---

## 🕒 Phase 3 – Attendance & Leave Management

### Entities
- **Attendance**
- **LeaveRequest**

### APIs

| Method | Endpoint                         | Access       | Description                    |
|--------|----------------------------------|--------------|--------------------------------|
| POST   | `/api/attendance/checkin`        | EMPLOYEE     | Employee check-in              |
| POST   | `/api/attendance/checkout`       | EMPLOYEE     | Employee check-out             |
| GET    | `/api/attendance/{employeeId}`   | HR/ADMIN     | Get attendance by employee     |
| POST   | `/api/leave/apply`               | EMPLOYEE     | Apply for leave                |
| GET    | `/api/leave/my`                  | EMPLOYEE     | Get my leave requests          |
| PUT    | `/api/leave/approve/{id}`        | HR/ADMIN     | Approve leave request          |
| PUT    | `/api/leave/reject/{id}`         | HR/ADMIN     | Reject leave request           |

### Business Logic
- Automatically set date on check-in/check-out
- Prevent multiple check-ins on the same day
- HR/ADMIN can update leave request status (approve/reject)





















# Database Schema Documentation

## Table of Contents

* [Role Table](#role-table)
* [Employee Table](#employee-table)
* [User Table](#user-table)
* [Attendance Table](#attendance-table)
* [Leave Request Table](#leave-request-table)
* [Relationships Summary](#relationships-summary)
* [Notes](#notes)

---

## Role Table

**Table Name:** `role`

| Column Name | Data Type                     | Constraints                  | Description                            |
| ----------- | ----------------------------- | ---------------------------- | -------------------------------------- |
| id          | BIGINT                        | PRIMARY KEY, AUTO\_INCREMENT | Unique identifier for the role         |
| name        | ENUM('ADMIN','HR','EMPLOYEE') | UNIQUE, NOT NULL             | Role name with specific allowed values |
| description | VARCHAR(255)                  | -                            | Description of the role                |

**Indexes**

* Primary Key: `id`
* Unique Constraint: `name`

**DDL**

```sql
CREATE TABLE role (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name ENUM('ADMIN','HR','EMPLOYEE') UNIQUE NOT NULL,
  description VARCHAR(255)
);
```

---

## Employee Table

**Table Name:** `employee`

| Column Name    | Data Type     | Constraints                                             | Description                        |
| -------------- | ------------- | ------------------------------------------------------- | ---------------------------------- |
| id             | BIGINT        | PRIMARY KEY, AUTO\_INCREMENT                            | Unique identifier for the employee |
| employee\_code | VARCHAR(20)   | UNIQUE, NOT NULL                                        | Unique employee code               |
| first\_name    | VARCHAR(100)  | NOT NULL                                                | Employee's first name              |
| last\_name     | VARCHAR(100)  | -                                                       | Employee's last name               |
| email          | VARCHAR(150)  | UNIQUE, NOT NULL                                        | Employee's email address           |
| phone          | VARCHAR(20)   | -                                                       | Employee's phone number            |
| designation    | VARCHAR(100)  | -                                                       | Employee's job title               |
| department     | VARCHAR(100)  | -                                                       | Employee's department              |
| join\_date     | DATE          | NOT NULL                                                | Employee's join date               |
| salary         | DECIMAL(10,2) | -                                                       | Employee's salary                  |
| active         | BOOLEAN       | DEFAULT TRUE                                            | Whether the employee is active     |
| created\_at    | TIMESTAMP     | DEFAULT CURRENT\_TIMESTAMP                              | Record creation timestamp          |
| updated\_at    | TIMESTAMP     | DEFAULT CURRENT\_TIMESTAMP ON UPDATE CURRENT\_TIMESTAMP | Record last update timestamp       |

**Indexes**

* Primary Key: `id`
* Unique Constraints: `employee_code`, `email`

**DDL**

```sql
CREATE TABLE employee (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  employee_code VARCHAR(20) UNIQUE NOT NULL,
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100),
  email VARCHAR(150) UNIQUE NOT NULL,
  phone VARCHAR(20),
  designation VARCHAR(100),
  department VARCHAR(100),
  join_date DATE NOT NULL,
  salary DECIMAL(10,2),
  active BOOLEAN DEFAULT TRUE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

---

## User Table

**Table Name:** `user`

| Column Name  | Data Type    | Constraints                  | Description                         |
| ------------ | ------------ | ---------------------------- | ----------------------------------- |
| id           | BIGINT       | PRIMARY KEY, AUTO\_INCREMENT | Unique identifier for the user      |
| username     | VARCHAR(50)  | UNIQUE, NOT NULL             | User's login username               |
| password     | VARCHAR(255) | NOT NULL                     | User's encrypted password           |
| employee\_id | BIGINT       | FOREIGN KEY                  | Reference to `employee` table       |
| role\_id     | BIGINT       | FOREIGN KEY, NOT NULL        | Reference to `role` table           |
| enabled      | BOOLEAN      | DEFAULT TRUE                 | Whether the user account is enabled |
| created\_at  | TIMESTAMP    | DEFAULT CURRENT\_TIMESTAMP   | Record creation timestamp           |

**Indexes**

* Primary Key: `id`
* Unique Constraint: `username`

**Foreign Keys**

* `employee_id` → `employee(id)`
* `role_id` → `role(id)`

**DDL**

```sql
CREATE TABLE user (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  employee_id BIGINT,
  role_id BIGINT NOT NULL,
  enabled BOOLEAN DEFAULT TRUE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (employee_id) REFERENCES employee(id),
  FOREIGN KEY (role_id) REFERENCES role(id)
);
```

---

## Attendance Table

**Table Name:** `attendance`

| Column Name      | Data Type                                    | Constraints                  | Description                      |
| ---------------- | -------------------------------------------- | ---------------------------- | -------------------------------- |
| id               | BIGINT                                       | PRIMARY KEY, AUTO\_INCREMENT | Unique identifier for attendance |
| employee\_id     | BIGINT                                       | FOREIGN KEY, NOT NULL        | Reference to `employee` table    |
| attendance\_date | DATE                                         | NOT NULL                     | Date of attendance record        |
| check\_in\_time  | TIMESTAMP                                    | -                            | Employee check-in time           |
| check\_out\_time | TIMESTAMP                                    | -                            | Employee check-out time          |
| status           | ENUM('PRESENT','ABSENT','LEAVE','HALF\_DAY') | DEFAULT 'PRESENT'            | Attendance status                |
| created\_at      | TIMESTAMP                                    | DEFAULT CURRENT\_TIMESTAMP   | Record creation timestamp        |

**Indexes**

* Primary Key: `id`
* Unique Constraint: `uniq_attendance (employee_id, attendance_date)`

**Foreign Key**

* `employee_id` → `employee(id)`

**DDL**

```sql
CREATE TABLE attendance (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  employee_id BIGINT NOT NULL,
  attendance_date DATE NOT NULL,
  check_in_time TIMESTAMP,
  check_out_time TIMESTAMP,
  status ENUM('PRESENT','ABSENT','LEAVE','HALF_DAY') DEFAULT 'PRESENT',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY uniq_attendance (employee_id, attendance_date),
  FOREIGN KEY (employee_id) REFERENCES employee(id)
);
```

---

## Leave Request Table

**Table Name:** `leave_request`

| Column Name  | Data Type                             | Constraints                                             | Description                         |
| ------------ | ------------------------------------- | ------------------------------------------------------- | ----------------------------------- |
| id           | BIGINT                                | PRIMARY KEY, AUTO\_INCREMENT                            | Unique identifier for leave request |
| employee\_id | BIGINT                                | FOREIGN KEY, NOT NULL                                   | Reference to `employee` table       |
| type         | ENUM('SICK','CASUAL','PAID','UNPAID') | NOT NULL                                                | Type of leave                       |
| start\_date  | DATE                                  | NOT NULL                                                | Leave start date                    |
| end\_date    | DATE                                  | NOT NULL                                                | Leave end date                      |
| reason       | VARCHAR(255)                          | -                                                       | Reason for leave                    |
| status       | ENUM('PENDING','APPROVED','REJECTED') | DEFAULT 'PENDING'                                       | Leave request status                |
| approved\_by | BIGINT                                | FOREIGN KEY                                             | Reference to `user` who approved    |
| created\_at  | TIMESTAMP                             | DEFAULT CURRENT\_TIMESTAMP                              | Record creation timestamp           |
| updated\_at  | TIMESTAMP                             | DEFAULT CURRENT\_TIMESTAMP ON UPDATE CURRENT\_TIMESTAMP | Record last update timestamp        |

**Indexes**

* Primary Key: `id`

**Foreign Keys**

* `employee_id` → `employee(id)`
* `approved_by` → `user(id)`

**DDL**

```sql
CREATE TABLE leave_request (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  employee_id BIGINT NOT NULL,
  type ENUM('SICK','CASUAL','PAID','UNPAID') NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  reason VARCHAR(255),
  status ENUM('PENDING','APPROVED','REJECTED') DEFAULT 'PENDING',
  approved_by BIGINT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (employee_id) REFERENCES employee(id),
  FOREIGN KEY (approved_by) REFERENCES user(id)
);
```

---

## Relationships Summary

* **Role → User:** One-to-Many (One role can be assigned to multiple users)
* **Employee → User:** One-to-One (One employee can have one user account)
* **Employee → Attendance:** One-to-Many (One employee can have multiple attendance records)
* **Employee → Leave Request:** One-to-Many (One employee can have multiple leave requests)
* **User → Leave Request:** One-to-Many (One user can approve multiple leave requests)

---

## Notes

* All tables use **BIGINT** for primary keys with **AUTO\_INCREMENT**.
* Timestamp fields use **DEFAULT CURRENT\_TIMESTAMP** for automatic timestamping.
* Enum fields restrict values to predefined options.
* Unique constraints ensure data integrity for critical fields.
* Foreign keys maintain referential integrity between related tables.
