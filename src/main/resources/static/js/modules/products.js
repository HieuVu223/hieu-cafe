import { escapeHtml } from './shared.js';

// DOM Elements
const productTableBody = document.getElementById('productTableBody');
const addProductBtn = document.getElementById('addProductBtn');
const productModal = new bootstrap.Modal('#productModal');
const saveProductBtn = document.getElementById('saveProductBtn');
const productForm = document.getElementById('productForm');
const categorySelect = document.getElementById('productCategory');

// Public API
export function initProducts() {
    document.addEventListener('DOMContentLoaded', async () => {
        await loadCategoriesForDropdown();
        await loadProducts();
    });
    // Event listener for add button
    addProductBtn.addEventListener('click', () => {
        productForm.reset();
        document.getElementById('productModalTitle').textContent = 'Add Product';
        productModal.show();
    });

    // Inside initProducts() or a dedicated function
    saveProductBtn.addEventListener('click', async (e) => {
        e.preventDefault();
        const formData = new FormData(productForm);
        const productData = {
            name: formData.get('name'),
            description: formData.get('description'),
            price: parseFloat(formData.get('price')),
            categoryId: formData.get('categoryId')
        };

        try {
            const isEditMode = productForm.dataset.editId; // Check if editing
            const url = isEditMode
                ? `/api/products/${productForm.dataset.editId}`
                : '/api/products';
            const method = isEditMode ? 'PUT' : 'POST';

            const response = await fetch(url, {
                method,
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(productData)
            });

            if (response.ok) {
                productModal.hide();
                await loadProducts(); // Refresh the list
            } else {
                alert('Failed to save product');
            }
        } catch (error) {
            console.error('Error saving product:', error);
            alert('An error occurred. Check console for details.');
        }
    });

}

// Private Functions
async function loadProducts() {
    try {
        const response = await fetch('/api/products');
        const products = await response.json();
        renderProducts(products);
    } catch (error) {
        console.error('Error loading products:', error);
        alert('Failed to load products. Check console for details.');
    }
}

async function loadCategoriesForDropdown() {
    try {
        const response = await fetch('/api/categories');
        const categories = await response.json();
        categorySelect.innerHTML = categories.map(category =>
            `<option value="${category.id}">${escapeHtml(category.name)}</option>`
        ).join('');
    } catch (error) {
        console.error('Error loading categories:', error);
    }
}

function renderProducts(products) {
    productTableBody.innerHTML = '';
    products.forEach((product, index) => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${index + 1}</td>
            <td>${escapeHtml(product.name)}</td>
            <td>${escapeHtml(product.description)}</td>
            <td>$${product.price.toFixed(2)}</td>
            <td>${product.category?.name || 'Uncategorized'}</td>
            <td class="action-btns">
                <button data-id="${product.id}"
                        data-name="${escapeHtml(product.name)}"
                        data-description="${escapeHtml(product.description)}"
                        data-price="${product.price}"
                        data-category-id="${product.category?.id || ''}"
                        class="edit-product-btn btn btn-sm btn-warning me-2">Edit</button>
                <button data-id="${product.id}"
                        class="delete-product-btn btn btn-sm btn-danger">Delete</button>
            </td>
        `;
        productTableBody.appendChild(row);
    });

    // Event delegation
    productTableBody.addEventListener('click', handleProductActions);
}

function handleProductActions(e) {
    const editBtn = e.target.closest('.edit-product-btn');
    const deleteBtn = e.target.closest('.delete-product-btn');

    if (editBtn) {
        editProduct(
            editBtn.dataset.id,
            editBtn.dataset.name,
            editBtn.dataset.description,
            editBtn.dataset.price,
            editBtn.dataset.categoryId
        );
    }
    if (deleteBtn) {
        deleteProduct(deleteBtn.dataset.id);
    }
}

// ... (Rest of product functions remain similar but use event delegation)
