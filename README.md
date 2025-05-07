# ☕ Cafe Management System  
*A full-stack application for managing coffee shop inventory, categories, and products with Spring Boot backend and MySQL database.*  

---

## 🛠️ **Technology Stack**  
| **Layer**       | **Technology**          | **Purpose**                              |  
|------------------|-------------------------|------------------------------------------|  
| **Frontend**     | Vanilla JS (ES Modules) | Dynamic UI with Bootstrap 5               |  
| **Backend**      | Spring Boot 3.x         | REST API development (Java 17+)          |  
| **Database**     | MySQL 8                 | Persistent data storage                  |  
| **ORM**          | Spring Data JPA         | Database interactions                    |  
| **Build Tool**   | Maven                   | Dependency management & packaging        |  
| **API Docs**     | Swagger UI              | Interactive API documentation            |  
| **Styling**      | Bootstrap 5             | Responsive layout & components            |  

---

## 🌐 **Architecture Overview**  
### **Frontend**  
- **Modular JavaScript**  
  - ES Modules (`categories.js`, `products.js`) for separation of concerns.  
  - Shared utilities (`escapeHtml()` in `shared.js`) for XSS protection.  
- **Dynamic UI**  
  - Bootstrap modals for CRUD operations.  
  - Event delegation for efficient button handling:  ```javascript  
    tableBody.addEventListener('click', handleCategoryActions);  
    ```  
- **API Integration**  
  ```javascript  
  await fetch('/api/categories', { 
    method: 'POST', 
    headers: { 'Content-Type': 'application/json' } 
  });  
