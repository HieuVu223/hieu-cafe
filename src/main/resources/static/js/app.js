// // DOM Elements
// const tableBody = document.getElementById('categoryTableBody');
// const addBtn = document.getElementById('addCategoryBtn');
// const modal = new bootstrap.Modal('#categoryModal');
// const saveBtn = document.getElementById('saveCategoryBtn');
// const form = document.getElementById('categoryForm');
//
// // Load categories on page load
// document.addEventListener('DOMContentLoaded', loadCategories);
//
// // Fetch all categories from your REST API
// async function loadCategories() {
//     try {
//         const response = await fetch('/api/categories');
//         const categories = await response.json();
//         renderCategories(categories);
//     } catch (error) {
//         console.error('Error loading categories:', error);
//     }
// }
//
// // Render categories in the table
// function renderCategories(categories) {
//     tableBody.innerHTML = '';
//     categories.forEach((category, index) => {
//         const row = document.createElement('tr');
//         row.innerHTML = `
//             <td>${index + 1}</td>
//             <td>${category.name}</td>
//             <td>${category.description}</td>
//             <td class="action-btns">
//                 <button onclick="editCategory(${category.id}, '${category.name}')"
//                         class="btn btn-sm btn-warning me-2">Edit</button>
//                 <button onclick="deleteCategory(${category.id})"
//                         class="btn btn-sm btn-danger">Delete</button>
//             </td>
//         `;
//         tableBody.appendChild(row);
//     });
// }
//
// // Add new category
// addBtn.addEventListener('click', () => {
//     form.reset();
//     document.getElementById('modalTitle').textContent = 'Add Category';
//     modal.show();
// });
//
// // Save category (POST/PUT)
// saveBtn.addEventListener('click', async () => {
//     const category = {
//         name: document.getElementById('categoryName').value,
//         description: document.getElementById('categoryDescription').value
//     };
//     const id = document.getElementById('categoryId').value;
//     const url = id ? `/api/categories/${id}` : '/api/categories';
//     const method = id ? 'PUT' : 'POST';
//
//     try {
//         const response = await fetch(url, {
//             method,
//             headers: { 'Content-Type': 'application/json' },
//             body: JSON.stringify(category)
//         });
//         if (response.ok) {
//             modal.hide();
//             await loadCategories(); // Refresh the list
//         }
//     } catch (error) {
//         console.error('Error saving category:', error);
//     }
// });
//
// // Edit category (pre-fill modal)
// window.editCategory = (id, name, description) => {
//     document.getElementById('categoryId').value = id;
//     document.getElementById('categoryName').value = name;
//     document.getElementById('categoryDescription').value = description || '';
//     document.getElementById('modalTitle').textContent = 'Edit Category';
//     modal.show();
// };
//
// // Delete category
// window.deleteCategory = async (id) => {
//     if (confirm('Are you sure you want to delete this category?')) {
//         try {
//             const response = await fetch(`/api/categories/${id}`, { method: 'DELETE' });
//             if (response.ok) await loadCategories();
//         } catch (error) {
//             console.error('Error deleting category:', error);
//         }
//     }
// };
//

import { initCategories } from './modules/categories.js';
import { initProducts } from './modules/products.js';

// Initialize modules
initCategories();
initProducts();
