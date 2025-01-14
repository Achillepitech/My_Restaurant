// URLs de l'API
const API_MANAGERS_URL = 'http://localhost:8080/managers';

// Charger les données au démarrage
document.addEventListener('DOMContentLoaded', () => {
    loadManagers();
    // Par défaut, afficher la section managers
    showSection('managers-section');
});

// ============= GESTION DES MANAGERS =============

// Charger tous les managers
async function loadManagers() {
    try {
        const response = await fetch(API_MANAGERS_URL);
        const managers = await response.json();
        displayManagers(managers);
    } catch (error) {
        console.error('Erreur:', error);
        alert('Erreur lors du chargement des managers');
    }
}

// Afficher les managers
function displayManagers(managers) {
    const managerList = document.getElementById('managerList');
    managerList.innerHTML = '';

    managers.forEach(manager => {
        const managerDiv = document.createElement('div');
        managerDiv.className = 'plat-item';
        managerDiv.innerHTML = `
            <h3>${manager.username}</h3>
            <p><strong>Email:</strong> ${manager.email}</p>
            <div class="plat-actions">
                <button class="btn btn-warning" onclick="showEditManagerForm(${manager.id}, '${manager.username}', '${manager.email}')">
                    Modifier
                </button>
                <button class="btn btn-danger" onclick="deleteManager(${manager.id})">
                    Supprimer
                </button>
                <button class="btn btn-primary" onclick="loadManagerRestaurant(${manager.id})">
                    Voir Restaurant
                </button>
            </div>
        `;
        managerList.appendChild(managerDiv);
    });
}

// Ajouter un manager
document.getElementById('addManagerForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const managerData = {
        username: document.getElementById('username').value.trim(),
        email: document.getElementById('email').value.trim(),
        password: document.getElementById('password').value
    };

    try {
        const response = await fetch(API_MANAGERS_URL, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(managerData)
        });

        if (response.ok) {
            alert('Manager ajouté avec succès!');
            document.getElementById('addManagerForm').reset();
            loadManagers();
        }
    } catch (error) {
        console.error('Erreur:', error);
        alert('Erreur lors de l\'ajout du manager');
    }
});

// Afficher le formulaire de modification
function showEditManagerForm(id, username, email) {
    document.getElementById('editManagerForm').style.display = 'block';
    document.getElementById('editManagerId').value = id;
    document.getElementById('editUsername').value = username;
    document.getElementById('editEmail').value = email;
    document.getElementById('editManagerForm').scrollIntoView({behavior: 'smooth'});
}

// Cacher le formulaire de modification
function hideEditManagerForm() {
    document.getElementById('editManagerForm').style.display = 'none';
}

// Mettre à jour un manager
document.getElementById('updateManagerForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const id = document.getElementById('editManagerId').value;
    const managerData = {
        username: document.getElementById('editUsername').value.trim(),
        email: document.getElementById('editEmail').value.trim()
    };

    try {
        const response = await fetch(`${API_MANAGERS_URL}/${id}`, {
            method: 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(managerData)
        });

        if (response.ok) {
            alert('Manager modifié avec succès!');
            hideEditManagerForm();
            loadManagers();
        }
    } catch (error) {
        console.error('Erreur:', error);
        alert('Erreur lors de la modification du manager');
    }
});

// Supprimer un manager
async function deleteManager(id) {
    if (confirm('Voulez-vous vraiment supprimer ce manager?')) {
        try {
            const response = await fetch(`${API_MANAGERS_URL}/${id}`, {
                method: 'DELETE'
            });

            if (response.ok) {
                alert('Manager supprimé avec succès!');
                loadManagers();
            }
        } catch (error) {
            console.error('Erreur:', error);
            alert('Erreur lors de la suppression du manager');
        }
    }
}

// ============= GESTION DU RESTAURANT =============

// Charger le restaurant d'un manager
async function loadManagerRestaurant(managerId) {
    try {
        const response = await fetch(`${API_MANAGERS_URL}/${managerId}/restaurant`);
        const restaurant = await response.json();
        displayRestaurantInfo(restaurant);
        showSection('restaurant-section');
    } catch (error) {
        console.error('Erreur:', error);
        alert('Erreur lors du chargement du restaurant');
    }
}

// Afficher les informations du restaurant
function displayRestaurantInfo(restaurant) {
    const restaurantInfo = document.getElementById('restaurantInfo');
    if (!restaurant) {
        restaurantInfo.innerHTML = '<p>Aucun restaurant associé</p>';
        return;
    }

    restaurantInfo.innerHTML = `
        <div class="info-item">
            <h3>${restaurant.name}</h3>
            <p><strong>Adresse:</strong> ${restaurant.address}</p>
            <p><strong>Téléphone:</strong> ${restaurant.phoneNumber}</p>
        </div>
    `;
}

// Ajouter/Mettre à jour un restaurant
document.getElementById('addRestaurantForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const managerId = document.getElementById('editManagerId').value; // Vous devrez stocker l'ID du manager actif
    const restaurantData = {
        name: document.getElementById('restaurantName').value.trim(),
        address: document.getElementById('address').value.trim(),
        phoneNumber: document.getElementById('phoneNumber').value.trim()
    };

    try {
        const response = await fetch(`${API_MANAGERS_URL}/${managerId}/restaurant`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(restaurantData)
        });

        if (response.ok) {
            alert('Restaurant mis à jour avec succès!');
            loadManagerRestaurant(managerId);
        }
    } catch (error) {
        console.error('Erreur:', error);
        alert('Erreur lors de la mise à jour du restaurant');
    }
});

// ============= GESTION DES MENUS =============

// Charger les menus d'un restaurant
async function loadMenus(managerId) {
    try {
        const response = await fetch(`${API_MANAGERS_URL}/${managerId}/restaurant/menus`);
        const menus = await response.json();
        displayMenus(menus);
    } catch (error) {
        console.error('Erreur:', error);
        alert('Erreur lors du chargement des menus');
    }
}

// Afficher les menus
function displayMenus(menus) {
    const menuList = document.getElementById('menuList');
    menuList.innerHTML = '';

    menus.forEach(menu => {
        const menuDiv = document.createElement('div');
        menuDiv.className = 'plat-item';
        menuDiv.innerHTML = `
            <h3>${menu.nom}</h3>
            <p>${menu.description}</p>
            <p><strong>${menu.prix} €</strong></p>
            <div class="plat-actions">
                <button class="btn btn-danger" onclick="deleteMenu(${menu.id})">
                    Supprimer
                </button>
            </div>
        `;
        menuList.appendChild(menuDiv);
    });
}

// Au démarrage de l'application
document.addEventListener('DOMContentLoaded', () => {
    loadEntrees();
});

// URL de base pour les entrées (à adapter selon le manager actif)
const managerId = 1; // À remplacer par l'ID du manager actif
const API_ENTREES_URL = `http://localhost:8080/managers/${managerId}/restaurant/entrees`;

// Charger toutes les entrées
async function loadEntrees() {
    try {
        const response = await fetch(API_ENTREES_URL);
        const entrees = await response.json();
        displayEntrees(entrees);
    } catch (error) {
        console.error('Erreur:', error);
        alert('Erreur lors du chargement des entrées');
    }
}

// Afficher les entrées dans la liste
function displayEntrees(entrees) {
    const entreeList = document.getElementById('entreeList');
    entreeList.innerHTML = '';

    entrees.forEach(entree => {
        const entreeDiv = document.createElement('div');
        entreeDiv.className = 'plat-item';
        entreeDiv.innerHTML = `
            <h3>${entree.nom}</h3>
            <p>${entree.description}</p>
            <p><strong>${entree.prix} €</strong></p>
            <div class="plat-actions">
                <button class="btn btn-warning" onclick="showEditEntreeForm(${entree.id}, '${entree.nom}', ${entree.prix}, '${entree.description}')">
                    Modifier
                </button>
                <button class="btn btn-danger" onclick="deleteEntree(${entree.id})">
                    Supprimer
                </button>
            </div>
        `;
        entreeList.appendChild(entreeDiv);
    });
}

// Ajouter une entrée
document.getElementById('addEntreeForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const entreeData = {
        nom: document.getElementById('nomEntree').value.trim(),
        prix: parseFloat(document.getElementById('prixEntree').value),
        description: document.getElementById('descriptionEntree').value.trim()
    };

    try {
        const response = await fetch(API_ENTREES_URL, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(entreeData)
        });

        if (response.ok) {
            alert('Entrée ajoutée avec succès!');
            document.getElementById('addEntreeForm').reset();
            loadEntrees();
        } else {
            throw new Error('Erreur lors de l\'ajout');
        }
    } catch (error) {
        console.error('Erreur:', error);
        alert('Erreur lors de l\'ajout de l\'entrée');
    }
});

// Afficher le formulaire de modification
function showEditEntreeForm(id, nom, prix, description) {
    document.getElementById('editEntreeForm').style.display = 'block';
    document.getElementById('editEntreeId').value = id;
    document.getElementById('editEntreeNom').value = nom;
    document.getElementById('editEntreePrix').value = prix;
    document.getElementById('editEntreeDescription').value = description;
    document.getElementById('editEntreeForm').scrollIntoView({behavior: 'smooth'});
}

// Cacher le formulaire de modification
function hideEditEntreeForm() {
    document.getElementById('editEntreeForm').style.display = 'none';
}

// Mettre à jour une entrée
document.getElementById('updateEntreeForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const id = document.getElementById('editEntreeId').value;
    const entreeData = {
        id: id,
        nom: document.getElementById('editEntreeNom').value.trim(),
        prix: parseFloat(document.getElementById('editEntreePrix').value),
        description: document.getElementById('editEntreeDescription').value.trim()
    };

    try {
        const response = await fetch(`${API_ENTREES_URL}/${id}`, {
            method: 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(entreeData)
        });

        if (response.ok) {
            alert('Entrée modifiée avec succès!');
            hideEditEntreeForm();
            loadEntrees();
        } else {
            throw new Error('Erreur lors de la modification');
        }
    } catch (error) {
        console.error('Erreur:', error);
        alert('Erreur lors de la modification de l\'entrée');
    }
});

// Supprimer une entrée
async function deleteEntree(id) {
    if (confirm('Voulez-vous vraiment supprimer cette entrée?')) {
        try {
            const response = await fetch(`${API_ENTREES_URL}/${id}`, {
                method: 'DELETE'
            });

            if (response.ok) {
                alert('Entrée supprimée avec succès!');
                loadEntrees();
            } else {
                throw new Error('Erreur lors de la suppression');
            }
        } catch (error) {
            console.error('Erreur:', error);
            alert('Erreur lors de la suppression de l\'entrée');
        }
    }
}