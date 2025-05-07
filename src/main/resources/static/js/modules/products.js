import { escapeHtml } from './shared.js';

// DOM Elements
const productTableBody = document.getElementById('productTableBody');
const addProductBtn = document.getElementById('addProductBtn');
const productModal = new bootstrap.Modal('#productModal');
const saveProductBtn = document.getElementById('saveProductBtn');
const productForm = document.getElementById('productForm');
const categorySelect = document.getElementById('productCategory');

// Public API
export async function initProducts() {
    // 1. Load categories first (await completion)
    try {
        await loadCategoriesForDropdown();
        console.log('Categories loaded:', categorySelect.options.length);
    } catch (error) {
        console.error('Category load failed:', error);
        return;
    }

    // 2. Load products
    await loadProducts();

    // 3. Verify critical elements exist
    const requiredElements = [
        productTableBody,
        addProductBtn,
        saveProductBtn,
        productForm
    ];

    if (requiredElements.some(el => !el)) {
        console.error('Missing elements:', requiredElements.map((el, i) =>
            !el ? ['TableBody', 'AddBtn', 'SaveBtn', 'Form'][i] : null
        ).filter(Boolean));
        return;
    }

    // 4. Attach event listeners
    productTableBody.addEventListener('click', handleProductActions);
    addProductBtn.addEventListener('click', () => {
        productForm.reset();
        productForm.removeAttribute('data-edit-id');
        document.getElementById('productModalTitle').textContent = 'Add Product';
        productModal.show();
    });
    saveProductBtn.addEventListener('click', handleProductSave);
}

// Private Functions
async function handleProductSave(e) {
    e.preventDefault();

    // 1. Safely get form data with null checks
    const formData = new FormData(productForm);
    const name = formData.get('productName')?.toString().trim() || '';
    const description = formData.get('productDescription')?.toString().trim() || '';
    const categoryId = formData.get('productCategory') || '';

    // 2. Basic validations
    if (!name) {
        document.getElementById('productName').classList.add('is-invalid');
        document.getElementById('productName').focus();
        return;
    }


    // === UPDATED PRICE VALIDATION ===
    const rawPrice = formData.get('productPrice');
    const priceInput = rawPrice !== null ? Number(String(rawPrice).replace(/,/g, '.')) : NaN;

    if (isNaN(priceInput)) {
        showPriceError('Price must be a valid number');
        return;
    }

    if (priceInput <= 0) {
        showPriceError('Price must be greater than zero');
        return;
    }

    document.getElementById('productPrice').classList.remove('is-invalid');
    // === END PRICE VALIDATION ===

    if (!categoryId) {
        document.getElementById('productCategory').classList.add('is-invalid');
        document.getElementById('productCategory').focus();
        return;
    }

    // 3. Prepare payload (price is now properly validated)
    const productData = { name, description, price: priceInput, categoryId };
    console.log('Submitting:', productData);

    // ... rest of your existing save logic remains unchanged ...
    try {
        const isEditMode = productForm.dataset.editId;
        const url = isEditMode ? `/api/products/${productForm.dataset.editId}` : '/api/products';
        const method = isEditMode ? 'PUT' : 'POST';

        const response = await fetch(url, {
            method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(productData)
        });

        if (!response.ok) throw new Error(await response.text());

        productModal.hide();
        productForm.reset();
        await loadProducts();
    } catch (error) {
        console.error('Save failed:', error);
        alert(`Save failed: ${error.message}`);
    }
}

// Modify renderProducts() to handle database format
function renderProducts(products) {
    productTableBody.innerHTML = '';

    if (!Array.isArray(products)) {
        console.error('Expected products array, got:', typeof products);
        return;
    }

    products.forEach((product, index) => {
        // Handle both API formats
        const categoryId = product.category?.id || product.category_id;
        const categoryName = product.category?.name ||
            categorySelect.querySelector(`option[value="${categoryId}"]`)?.text ||
            'Uncategorized';

        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${index + 1}</td>
            <td>${escapeHtml(product.name || '')}</td>
            <td>${escapeHtml(product.description || '')}</td>
            <td>$${(Number(product.price) || 0).toFixed(2)}</td>
            <td>${categoryName}</td>
            <td class="action-btns">
                <button data-id="${product.id}"
                        data-name="${escapeHtml(product.name || '')}"
                        data-description="${escapeHtml(product.description || '')}"
                        data-price="${product.price || ''}"
                        data-category-id="${categoryId}"
                        class="edit-product-btn btn btn-sm btn-warning me-2">Edit</button>
                <button data-id="${product.id}"
                        class="delete-product-btn btn btn-sm btn-danger">Delete</button>
            </td>
        `;
        productTableBody.appendChild(row);
    });
}

// Add modal state reset
function showAddModal() {
    if (!productModal) {
        console.error('Bootstrap modal not initialized');
        return;
    }

    productForm.reset();
    productForm.removeAttribute('data-edit-id');
    document.getElementById('productModalTitle').textContent = 'Add Product';
    productModal.show();
}

// Update editProduct()
function editProduct(id, name, description, price, categoryId) {
    document.getElementById('productName').value = name;
    document.getElementById('productDescription').value = description;
    document.getElementById('productPrice').value = price;
    document.getElementById('productCategory').value = categoryId;

    productForm.dataset.editId = id;
    document.getElementById('productModalTitle').textContent = 'Edit Product';

    if (!productModal) {
        console.error('Modal unavailable during edit');
        return;
    }
    productModal.show();
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

async function deleteProduct(id) {
    if (!confirm('Are you sure you want to delete this product?')) return;

    try {
        const response = await fetch(`/api/products/${id}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            await loadProducts(); // Refresh the list
        } else {
            alert('Failed to delete product');
        }
    } catch (error) {
        console.error('Error deleting product:', error);
        alert('An error occurred. Check console for details.');
    }
}

// Helper functions
function showPriceError(message) {
    console.error(message);
    const priceField = document.getElementById('productPrice');
    priceField.classList.add('is-invalid');
    priceField.focus();
}



