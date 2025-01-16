const MANAGER_ID = '1';
const API_BASE_URL = 'http://localhost:8080/managers/' + MANAGER_ID + '/restaurant';

// Afficher les informations de débogage
document.getElementById('debugManagerId').textContent = MANAGER_ID;
document.getElementById('debugApiUrl').textContent = API_BASE_URL;

function displayResponse(data, isError = false) {
    const responseElement = document.getElementById('apiResponse');
    if (isError) {
        responseElement.style.color = 'red';
    } else {
        responseElement.style.color = 'black';
    }
    responseElement.textContent = typeof data === 'string' ? data : JSON.stringify(data, null, 2);
}

function getRestaurantData() {
    const data = {
        id: document.getElementById('restaurantId').value,
        nom: document.getElementById('nom').value,
        description: document.getElementById('description').value,
        adresse: document.getElementById('adresse').value,
        telephone: document.getElementById('telephone').value,
        ville: document.getElementById('ville').value,
        presentationText: document.getElementById('presentationText').value,
        heuresOuverture: {},
        manager: {
            id: MANAGER_ID,
            username: document.getElementById('managerUsername').value,
            email: document.getElementById('managerEmail').value
        }
    };
    console.log('Données à envoyer:', data);
    return data;
}

async function loadRestaurantInfo() {
    try {
        displayResponse('Chargement des données...');
        const response = await fetch(API_BASE_URL);

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const data = await response.json();
        console.log('Données reçues:', data);

        // Remplir le formulaire avec les données reçues
        document.getElementById('restaurantId').value = data.id || '';
        document.getElementById('nom').value = data.nom || '';
        document.getElementById('adresse').value = data.adresse || '';
        document.getElementById('telephone').value = data.telephone || '';
        document.getElementById('ville').value = data.ville || '';
        document.getElementById('description').value = data.description || '';
        document.getElementById('presentationText').value = data.presentationText || '';

        // Informations manager
        if (data.manager) {
            document.getElementById('managerUsername').value = data.manager.username || '';
            document.getElementById('managerEmail').value = data.manager.email || '';
        }

        displayResponse({
            message: 'Données chargées avec succès',
            data: data
        });
    } catch (error) {
        console.error('Erreur de chargement:', error);
        displayResponse(`Erreur de chargement: ${error.message}`, true);
    }
}

async function updateRestaurant() {
    try {
        displayResponse('Mise à jour en cours...');
        const requestData = getRestaurantData();

        const response = await fetch(API_BASE_URL, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(requestData)
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Erreur HTTP ${response.status}: ${errorText}`);
        }

        const data = await response.json();

        displayResponse({
            message: 'Restaurant mis à jour avec succès',
            data: data
        });

        // Recharger les données après la mise à jour
        await loadRestaurantInfo();
    } catch (error) {
        console.error('Erreur de mise à jour:', error);
        displayResponse(`Erreur de mise à jour: ${error.message}`, true);
    }
}


let entreeModal;

document.addEventListener('DOMContentLoaded', function() {
    loadRestaurantInfo();
    entreeModal = new bootstrap.Modal(document.getElementById('entreeModal'));
    loadEntrees();
});

// Fonctions pour la gestion des entrées
async function loadEntrees() {
    try {
        const response = await fetch(`${API_BASE_URL}/entrees`);
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
        const entrees = await response.json();

        const tbody = document.getElementById('entreesTableBody');
        tbody.innerHTML = '';

        entrees.forEach(entree => {
            const row = tbody.insertRow();
            row.innerHTML = `
                <td>${entree.nom}</td>
                <td>${entree.description}</td>
                <td>${entree.prix.toFixed(2)} €</td>
                <td>
                    <button class="btn btn-sm btn-warning" onclick='showEditForm(${JSON.stringify(entree)})'>Modifier</button>
                    <button class="btn btn-sm btn-danger" onclick="deleteEntree(${entree.id})">Supprimer</button>
                </td>
            `;
        });
    } catch (error) {
        displayResponse(`Erreur de chargement des entrées: ${error.message}`, true);
    }
}

function showAddForm() {
    document.getElementById('entreeId').value = '';
    document.getElementById('entreeForm').reset();
    document.getElementById('modalTitle').textContent = 'Ajouter une entrée';
    entreeModal.show();
}

function showEditForm(entree) {
    document.getElementById('entreeId').value = entree.id;
    document.getElementById('entreeNom').value = entree.nom;
    document.getElementById('entreeDescription').value = entree.description;
    document.getElementById('entreePrix').value = entree.prix;
    document.getElementById('modalTitle').textContent = 'Modifier une entrée';
    entreeModal.show();
}

async function saveEntree() {
    try {
        const entreeData = {
            id: document.getElementById('entreeId').value || null,
            nom: document.getElementById('entreeNom').value,
            description: document.getElementById('entreeDescription').value,
            prix: parseFloat(document.getElementById('entreePrix').value),
            manager: {
                id: MANAGER_ID
            }
        };

        const url = entreeData.id
            ? `${API_BASE_URL}/entrees/${entreeData.id}`
            : `${API_BASE_URL}/entrees`;
        const method = entreeData.id ? 'PUT' : 'POST';

        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(entreeData)
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Erreur HTTP ${response.status}: ${errorText}`);
        }

        await response.json();
        displayResponse(`Entrée ${entreeData.id ? 'modifiée' : 'ajoutée'} avec succès`);
        entreeModal.hide();
        loadEntrees();
    } catch (error) {
        displayResponse(`Erreur: ${error.message}`, true);
    }
}

async function deleteEntree(entreeId) {
    if (!confirm('Êtes-vous sûr de vouloir supprimer cette entrée ?')) return;

    try {
        const response = await fetch(`${API_BASE_URL}/entrees/${entreeId}`, {
            method: 'DELETE'
        });

        if (!response.ok) {
            throw new Error(`Erreur HTTP ${response.status}`);
        }

        displayResponse('Entrée supprimée avec succès');
        loadEntrees();
    } catch (error) {
        displayResponse(`Erreur de suppression: ${error.message}`, true);
    }
}



let platModal;

document.addEventListener('DOMContentLoaded', function() {
    loadRestaurantInfo();
    entreeModal = new bootstrap.Modal(document.getElementById('entreeModal'));
    platModal = new bootstrap.Modal(document.getElementById('platModal'));
    loadEntrees();
    loadPlats();
});

// Fonctions pour la gestion des plats
async function loadPlats() {
    try {
        const response = await fetch(`${API_BASE_URL}/plats`);
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
        const plats = await response.json();

        const tbody = document.getElementById('platsTableBody');
        tbody.innerHTML = '';

        plats.forEach(plat => {
            const row = tbody.insertRow();
            row.innerHTML = `
                <td>${plat.nom}</td>
                <td>${plat.description}</td>
                <td>${plat.prix.toFixed(2)} €</td>
                <td>${plat.actif ? 'Actif' : 'Inactif'}</td>
                <td>
                    <button class="btn btn-sm btn-warning" onclick='showEditPlatForm(${JSON.stringify(plat)})'>Modifier</button>
                    <button class="btn btn-sm btn-danger" onclick="deletePlat(${plat.id})">Supprimer</button>
                </td>
            `;
        });
    } catch (error) {
        displayResponse(`Erreur de chargement des plats: ${error.message}`, true);
    }
}

function showAddPlatForm() {
    document.getElementById('platId').value = '';
    document.getElementById('platForm').reset();
    document.getElementById('modalPlatTitle').textContent = 'Ajouter un plat';
    document.getElementById('platActif').checked = true;
    platModal.show();
}

function showEditPlatForm(plat) {
    document.getElementById('platId').value = plat.id;
    document.getElementById('platNom').value = plat.nom;
    document.getElementById('platDescription').value = plat.description;
    document.getElementById('platPrix').value = plat.prix;
    document.getElementById('platActif').checked = plat.actif;
    document.getElementById('modalPlatTitle').textContent = 'Modifier un plat';
    platModal.show();
}

async function savePlat() {
    try {
        const platData = {
            id: document.getElementById('platId').value || null,
            nom: document.getElementById('platNom').value,
            description: document.getElementById('platDescription').value,
            prix: parseFloat(document.getElementById('platPrix').value),
            actif: document.getElementById('platActif').checked,
            manager: {
                id: MANAGER_ID
            }
        };

        const url = platData.id
            ? `${API_BASE_URL}/plats/${platData.id}`
            : `${API_BASE_URL}/plats`;
        const method = platData.id ? 'PUT' : 'POST';

        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(platData)
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Erreur HTTP ${response.status}: ${errorText}`);
        }

        await response.json();
        displayResponse(`Plat ${platData.id ? 'modifié' : 'ajouté'} avec succès`);
        platModal.hide();
        loadPlats();
    } catch (error) {
        displayResponse(`Erreur: ${error.message}`, true);
    }
}

async function deletePlat(platId) {
    if (!confirm('Êtes-vous sûr de vouloir supprimer ce plat ?')) return;

    try {
        const response = await fetch(`${API_BASE_URL}/plats/${platId}`, {
            method: 'DELETE'
        });

        if (!response.ok) {
            throw new Error(`Erreur HTTP ${response.status}`);
        }

        displayResponse('Plat supprimé avec succès');
        loadPlats();
    } catch (error) {
        displayResponse(`Erreur de suppression: ${error.message}`, true);
    }
}



let dessertModal;

document.addEventListener('DOMContentLoaded', function() {
    loadRestaurantInfo();
    entreeModal = new bootstrap.Modal(document.getElementById('entreeModal'));
    platModal = new bootstrap.Modal(document.getElementById('platModal'));
    dessertModal = new bootstrap.Modal(document.getElementById('dessertModal'));
    loadEntrees();
    loadPlats();
    loadDesserts();
});

// Fonctions pour la gestion des desserts
async function loadDesserts() {
    try {
        const response = await fetch(`${API_BASE_URL}/desserts`);
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
        const desserts = await response.json();

        const tbody = document.getElementById('dessertsTableBody');
        tbody.innerHTML = '';

        desserts.forEach(dessert => {
            const row = tbody.insertRow();
            row.innerHTML = `
                <td>${dessert.nom}</td>
                <td>${dessert.description}</td>
                <td>${dessert.prix.toFixed(2)} €</td>
                <td>
                    <button class="btn btn-sm btn-warning" onclick='showEditDessertForm(${JSON.stringify(dessert)})'>Modifier</button>
                    <button class="btn btn-sm btn-danger" onclick="deleteDessert(${dessert.id})">Supprimer</button>
                </td>
            `;
        });
    } catch (error) {
        displayResponse(`Erreur de chargement des desserts: ${error.message}`, true);
    }
}

function showAddDessertForm() {
    document.getElementById('dessertId').value = '';
    document.getElementById('dessertForm').reset();
    document.getElementById('modalDessertTitle').textContent = 'Ajouter un dessert';
    dessertModal.show();
}

function showEditDessertForm(dessert) {
    document.getElementById('dessertId').value = dessert.id;
    document.getElementById('dessertNom').value = dessert.nom;
    document.getElementById('dessertDescription').value = dessert.description;
    document.getElementById('dessertPrix').value = dessert.prix;
    document.getElementById('modalDessertTitle').textContent = 'Modifier un dessert';
    dessertModal.show();
}

async function saveDessert() {
    try {
        const dessertData = {
            id: document.getElementById('dessertId').value || null,
            nom: document.getElementById('dessertNom').value,
            description: document.getElementById('dessertDescription').value,
            prix: parseFloat(document.getElementById('dessertPrix').value),
            manager: {
                id: MANAGER_ID
            }
        };

        const url = dessertData.id
            ? `${API_BASE_URL}/desserts/${dessertData.id}`
            : `${API_BASE_URL}/desserts`;
        const method = dessertData.id ? 'PUT' : 'POST';

        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(dessertData)
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Erreur HTTP ${response.status}: ${errorText}`);
        }

        await response.json();
        displayResponse(`Dessert ${dessertData.id ? 'modifié' : 'ajouté'} avec succès`);
        dessertModal.hide();
        loadDesserts();
    } catch (error) {
        displayResponse(`Erreur: ${error.message}`, true);
    }
}

async function deleteDessert(dessertId) {
    if (!confirm('Êtes-vous sûr de vouloir supprimer ce dessert ?')) return;

    try {
        const response = await fetch(`${API_BASE_URL}/desserts/${dessertId}`, {
            method: 'DELETE'
        });

        if (!response.ok) {
            throw new Error(`Erreur HTTP ${response.status}`);
        }

        displayResponse('Dessert supprimé avec succès');
        loadDesserts();
    } catch (error) {
        displayResponse(`Erreur de suppression: ${error.message}`, true);
    }
}

let menuModal;

document.addEventListener('DOMContentLoaded', function() {
    loadRestaurantInfo();
    entreeModal = new bootstrap.Modal(document.getElementById('entreeModal'));
    platModal = new bootstrap.Modal(document.getElementById('platModal'));
    dessertModal = new bootstrap.Modal(document.getElementById('dessertModal'));
    menuModal = new bootstrap.Modal(document.getElementById('menuModal'));
    loadEntrees();
    loadPlats();
    loadDesserts();
    loadMenus();
});

// Fonctions pour la gestion des menus
async function loadMenus() {
    try {
        const response = await fetch(`${API_BASE_URL}/menus`);
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
        const menus = await response.json();

        const tbody = document.getElementById('menusTableBody');
        tbody.innerHTML = '';

        menus.forEach(menu => {
            const row = tbody.insertRow();
            row.innerHTML = `
                <td>${menu.nom || '-'}</td>
                <td>${menu.description || '-'}</td>
                <td>${menu.prix ? menu.prix.toFixed(2) + ' €' : '-'}</td>
                <td>${menu.actif ? 'Oui' : 'Non'}</td>
                <td>
                    <button class="btn btn-sm btn-warning" onclick='showEditMenuForm(${JSON.stringify(menu)})'>
                        Modifier
                    </button>
                    <button class="btn btn-sm btn-danger" onclick="deleteMenu(${menu.id})">
                        Supprimer
                    </button>
                </td>
            `;
        });
    } catch (error) {
        displayResponse(`Erreur de chargement des menus: ${error.message}`, true);
    }
}

function showAddMenuForm() {
    document.getElementById('menuId').value = '';
    document.getElementById('menuForm').reset();
    document.getElementById('menuActif').checked = true;
    document.getElementById('modalMenuTitle').textContent = 'Ajouter un menu';
    menuModal.show();
}

function showEditMenuForm(menu) {
    document.getElementById('menuId').value = menu.id;
    document.getElementById('menuNom').value = menu.nom || '';
    document.getElementById('menuDescription').value = menu.description || '';
    document.getElementById('menuPrix').value = menu.prix || '';
    document.getElementById('menuActif').checked = menu.actif;
    document.getElementById('modalMenuTitle').textContent = 'Modifier un menu';
    menuModal.show();
}

async function saveMenu() {
    try {
        const menuData = {
            id: document.getElementById('menuId').value || null,
            nom: document.getElementById('menuNom').value,
            description: document.getElementById('menuDescription').value,
            prix: parseFloat(document.getElementById('menuPrix').value),
            actif: document.getElementById('menuActif').checked,
            manager: {
                id: MANAGER_ID
            }
        };

        const url = menuData.id
            ? `${API_BASE_URL}/menus/${menuData.id}`
            : `${API_BASE_URL}/menus`;
        const method = menuData.id ? 'PUT' : 'POST';

        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(menuData)
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Erreur HTTP ${response.status}: ${errorText}`);
        }

        await response.json();
        displayResponse(`Menu ${menuData.id ? 'modifié' : 'ajouté'} avec succès`);
        menuModal.hide();
        loadMenus();
    } catch (error) {
        displayResponse(`Erreur: ${error.message}`, true);
    }
}

async function deleteMenu(menuId) {
    if (!confirm('Êtes-vous sûr de vouloir supprimer ce menu ?')) return;

    try {
        const response = await fetch(`${API_BASE_URL}/menus/${menuId}`, {
            method: 'DELETE'
        });

        if (!response.ok) {
            throw new Error(`Erreur HTTP ${response.status}`);
        }

        displayResponse('Menu supprimé avec succès');
        loadMenus();
    } catch (error) {
        displayResponse(`Erreur de suppression: ${error.message}`, true);
    }
}



// Charger les données au démarrage
document.addEventListener('DOMContentLoaded', loadRestaurantInfo);