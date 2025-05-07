import { escapeHtml } from './shared.js';

// DOM Elements
const tableBody = document.getElementById('categoryTableBody');
const addBtn = document.getElementById('addCategoryBtn');
const modal = new bootstrap.Modal('#categoryModal');
const saveBtn = document.getElementById('saveCategoryBtn');
const form = document.getElementById('categoryForm');

// Public API
export function initCategories() {
    document.addEventListener('DOMContentLoaded', loadCategories);
}

// Private Functions
async function loadCategories() {
    try {
        const response = await fetch('/api/categories');
        const categories = await response.json();
        renderCategories(categories);
    } catch (error) {
        console.error('Error loading categories:', error);
    }
}

function renderCategories(categories) {
    tableBody.innerHTML = '';

    if (!Array.isArray(categories)) {
        console.error('Invalid categories data:', categories);
        categories = [];
    }

    categories.forEach((category, index) => {
        const safeCategory = {
            id: category.id || '',
            name: category.name || '',
            description: category.description || ''
        };

        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${index + 1}</td>
            <td>${escapeHtml(category.name || '')}</td>
            <td>${escapeHtml(category.description || '')}</td>
            <td class="action-btns">
                <button data-id="${category.id || ''}" 
                        data-name="${escapeHtml(category.name || '')}" 
                        data-description="${escapeHtml(category.description || '')}"
                        class="edit-category-btn btn btn-sm btn-warning me-2">Edit</button>
                <button data-id="${category.id || ''}" 
                        class="delete-category-btn btn btn-sm btn-danger">Delete</button>
            </td>
        `;
        tableBody.appendChild(row);
    });

    tableBody.addEventListener('click', handleCategoryActions);
}

async function handleCategoryActions(e) {
    const editBtn = e.target.closest('.edit-category-btn');
    const deleteBtn = e.target.closest('.delete-category-btn');

    if (editBtn) {
        editCategory(
            editBtn.dataset.id,
            editBtn.dataset.name,
            editBtn.dataset.description
        );
    }
    if (deleteBtn) {
        await deleteCategory(deleteBtn.dataset.id);
    }
}

async function saveCategory() {
    const category = {
        name: document.getElementById('categoryName').value,
        description: document.getElementById('categoryDescription').value
    };
    const id = document.getElementById('categoryId').value;
    const url = id ? `/api/categories/${id}` : '/api/categories';
    const method = id ? 'PUT' : 'POST';

    try {
        const response = await fetch(url, {
            method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(category)
        });
        if (response.ok) {
            modal.hide();
            await loadCategories();
        }
    } catch (error) {
        console.error('Error saving category:', error);
    }
}

function editCategory(id, name, description) {
    const elements = {
        idField: document.getElementById('categoryId'),
        nameField: document.getElementById('categoryName'),
        descField: document.getElementById('categoryDescription'),
        titleElement: document.getElementById('modalTitle')
    };

    if (!Object.values(elements).every(el => el)) {
        console.error('Missing modal elements:', elements);
        return;
    }

    elements.idField.value = id || '';
    elements.nameField.value = name || '';
    elements.descField.value = description || '';
    elements.titleElement.textContent = 'Edit Category';

    if (typeof modal?.show === 'function') {
        modal.show();
    } else {
        console.error('Modal system not initialized');
    }
}

async function deleteCategory(id) {
    if (confirm('Are you sure you want to delete this category?')) {
        try {
            const response = await fetch(`/api/categories/${id}`, { method: 'DELETE' });
            if (response.ok) await loadCategories();
        } catch (error) {
            console.error('Error deleting category:', error);
        }
    }
}

// Event Listeners
addBtn.addEventListener('click', () => {
    form.reset();
    document.getElementById('modalTitle').textContent = 'Add Category';
    modal.show();
});

saveBtn.addEventListener('click', saveCategory);
