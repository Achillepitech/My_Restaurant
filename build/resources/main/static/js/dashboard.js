// URL de l'API avec les autres constantes
const API_INFO_URL = 'http://localhost:8080/api/restaurant/info';

// Dans le DOMContentLoaded existant
document.addEventListener('DOMContentLoaded', () => {
    loadPlats();
    loadEntrees();
    loadDesserts();
    loadMenus();
    loadRestaurantInfo();
});

// ============= GESTION DES INFORMATIONS DU RESTAURANT =============

// Charger les informations du restaurant
async function loadRestaurantInfo() {
    try {
        const response = await fetch(API_INFO_URL);
        const info = await response.json();
        displayRestaurantInfo(info);
        fillInfoForm(info);
    } catch (error) {
        console.error('Erreur:', error);
        alert('Erreur lors du chargement des informations du restaurant');
    }
}

// Afficher les informations du restaurant
function displayRestaurantInfo(info) {
    const infoList = document.getElementById('infoList');

    // Création du HTML pour les horaires
    const horairesList = Object.entries(info.heuresOuverture || {}).map(([jour, horaire]) =>
        `<li><strong>${jour}:</strong> ${horaire}</li>`
    ).join('');

    infoList.innerHTML = `
        <div class="info-item">
            <h3>${info.nom || ''}</h3>
            <p><strong>Description :</strong> ${info.description || ''}</p>
            <p><strong>Adresse :</strong> ${info.adresse || ''}</p>
            <p><strong>Ville :</strong> ${info.ville || ''}</p>
            <p><strong>Téléphone :</strong> ${info.telephone || ''}</p>
            <p><strong>Présentation :</strong> ${info.presentationText || ''}</p>
            <div class="horaires-section">
                <h4>Horaires d'ouverture :</h4>
                <ul class="horaires-liste">
                    ${horairesList}
                </ul>
            </div>
        </div>
    `;
}

// Remplir le formulaire avec les informations actuelles
function fillInfoForm(info) {
    document.getElementById('infoNom').value = info.nom || '';
    document.getElementById('infoDescription').value = info.description || '';
    document.getElementById('infoAdresse').value = info.adresse || '';
    document.getElementById('infoVille').value = info.ville || '';
    document.getElementById('infoTelephone').value = info.telephone || '';
    document.getElementById('infoPresentationText').value = info.presentationText || '';

    // Remplir les horaires
    if (info.heuresOuverture) {
        Object.entries(info.heuresOuverture).forEach(([jour, horaire]) => {
            const input = document.getElementById(`horaires${jour}`);
            if (input) input.value = horaire;
        });
    }
}

// Mettre à jour les informations du restaurant
document.getElementById('updateInfoForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    // Récupérer les horaires
    const heuresOuverture = {
        Lundi: document.getElementById('horairesLundi').value,
        Mardi: document.getElementById('horairesMardi').value,
        Mercredi: document.getElementById('horairesMercredi').value,
        Jeudi: document.getElementById('horairesJeudi').value,
        Vendredi: document.getElementById('horairesVendredi').value,
        Samedi: document.getElementById('horairesSamedi').value,
        Dimanche: document.getElementById('horairesDimanche').value
    };

    const infoData = {
        nom: document.getElementById('infoNom').value.trim(),
        description: document.getElementById('infoDescription').value.trim(),
        adresse: document.getElementById('infoAdresse').value.trim(),
        ville: document.getElementById('infoVille').value.trim(),
        telephone: document.getElementById('infoTelephone').value.trim(),
        presentationText: document.getElementById('infoPresentationText').value.trim(),
        heuresOuverture: heuresOuverture
    };

    try {
        const response = await fetch(API_INFO_URL, {
            method: 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(infoData)
        });

        if (response.ok) {
            alert('Informations du restaurant mises à jour avec succès!');
            loadRestaurantInfo();
        } else {
            throw new Error('Erreur lors de la mise à jour');
        }
    } catch (error) {
        console.error('Erreur:', error);
        alert('Erreur lors de la mise à jour des informations du restaurant');
    }
});


// URL de base de l'API
const API_URL = 'http://localhost:8080/api/plats';

// Charger les plats au démarrage
document.addEventListener('DOMContentLoaded', loadPlats);

// Fonction pour charger tous les plats
async function loadPlats() {
    try {
        const response = await fetch(API_URL);
        const plats = await response.json();
        displayPlats(plats);
    } catch (error) {
        console.error('Erreur:', error);
        alert('Erreur lors du chargement des plats');
    }
}

// Afficher les plats dans la liste
function displayPlats(plats) {
    const platList = document.getElementById('platList');
    platList.innerHTML = '';

    plats.forEach(plat => {
        const platDiv = document.createElement('div');
        platDiv.className = 'plat-item';
        platDiv.innerHTML = `
                    <h3>${plat.nom}</h3>
                    <p>${plat.description}</p>
                    <p><strong>${plat.prix} €</strong></p>
                    <div class="plat-actions">
                        <button class="btn btn-warning" onclick="showEditForm(${plat.id}, '${plat.nom}', ${plat.prix}, '${plat.description}')">
                            Modifier
                        </button>
                        <button class="btn btn-danger" onclick="deletePlat(${plat.id})">
                            Supprimer
                        </button>
                    </div>
                `;
        platList.appendChild(platDiv);
    });
}

// Ajouter un plat
document.getElementById('addForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const platData = {
        nom: document.getElementById('nom').value.trim(),
        prix: parseFloat(document.getElementById('prix').value),
        description: document.getElementById('description').value.trim()
    };

    try {
        const response = await fetch(API_URL, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(platData)
        });

        if (response.ok) {
            alert('Plat ajouté avec succès!');
            document.getElementById('addForm').reset();
            loadPlats();
        }
    } catch (error) {
        console.error('Erreur:', error);
        alert('Erreur lors de l\'ajout du plat');
    }
});


// Afficher le formulaire de modification
function showEditForm(id, nom, prix, description) {
    document.getElementById('editForm').style.display = 'block';
    document.getElementById('editId').value = id;
    document.getElementById('editNom').value = nom;
    document.getElementById('editPrix').value = prix;
    document.getElementById('editDescription').value = description;
    document.getElementById('editForm').scrollIntoView({behavior: 'smooth'});
}

// Cacher le formulaire de modification
function hideEditForm() {
    document.getElementById('editForm').style.display = 'none';
}

// Mettre à jour un plat
document.getElementById('updateForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const id = document.getElementById('editId').value;
    const platData = {
        id: id,
        nom: document.getElementById('editNom').value,
        prix: parseFloat(document.getElementById('editPrix').value),
        description: document.getElementById('editDescription').value
    };

    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(platData)
        });

        if (response.ok) {
            alert('Plat modifié avec succès!');
            hideEditForm();
            loadPlats();
        }
    } catch (error) {
        console.error('Erreur:', error);
        alert('Erreur lors de la modification du plat');
    }
});

// Supprimer un plat
async function deletePlat(id) {
    if (confirm('Voulez-vous vraiment supprimer ce plat?')) {
        try {
            const response = await fetch(`${API_URL}/${id}`, {
                method: 'DELETE'
            });

            if (response.ok) {
                alert('Plat supprimé avec succès!');
                loadPlats();
            }
        } catch (error) {
            console.error('Erreur:', error);
            alert('Erreur lors de la suppression du plat');
        }
    }
}


// nos entree


// URL de base de l'API pour les entrées
const API_ENTREES_URL = 'http://localhost:8080/api/entrees';

// Charger les entrées au démarrage
document.addEventListener('DOMContentLoaded', loadEntrees);

// Fonction pour charger toutes les entrées
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
                <button class="btn btn-warning edit-entree" 
                    data-id="${entree.id}"
                    data-nom="${entree.nom}"
                    data-prix="${entree.prix}"
                    data-description="${entree.description}">
                    Modifier
                </button>
                <button class="btn btn-danger delete-entree" 
                    data-id="${entree.id}">
                    Supprimer
                </button>
            </div>
        `;
        entreeList.appendChild(entreeDiv);
    });

    // Ajouter les écouteurs d'événements pour les boutons
    document.querySelectorAll('.edit-entree').forEach(button => {
        button.addEventListener('click', function () {
            const {id, nom, prix, description} = this.dataset;
            showEditEntreeForm(id, nom, prix, description);
        });
    });

    document.querySelectorAll('.delete-entree').forEach(button => {
        button.addEventListener('click', function () {
            const id = this.dataset.id;
            deleteEntree(id);
        });
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
    const form = document.getElementById('editEntreeForm');
    form.style.display = 'block';

    document.getElementById('editEntreeId').value = id;
    document.getElementById('editEntreeNom').value = nom;
    document.getElementById('editEntreePrix').value = prix;
    document.getElementById('editEntreeDescription').value = description;

    form.scrollIntoView({behavior: 'smooth'});
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


// Ajouter l'URL de l'API des desserts avec les autres URLs en haut du fichier
const API_DESSERTS_URL = 'http://localhost:8080/api/desserts';

// Ajouter l'appel à loadDesserts() dans le DOMContentLoaded
document.addEventListener('DOMContentLoaded', () => {
    loadPlats();
    loadEntrees();
    loadDesserts();
});

// ============= GESTION DES DESSERTS =============

// Fonction pour charger tous les desserts
async function loadDesserts() {
    try {
        const response = await fetch(API_DESSERTS_URL);
        const desserts = await response.json();
        displayDesserts(desserts);
    } catch (error) {
        console.error('Erreur:', error);
        alert('Erreur lors du chargement des desserts');
    }
}

// Afficher les desserts dans la liste
function displayDesserts(desserts) {
    const dessertList = document.getElementById('dessertList');
    dessertList.innerHTML = '';

    desserts.forEach(dessert => {
        const dessertDiv = document.createElement('div');
        dessertDiv.className = 'plat-item';

        dessertDiv.innerHTML = `
            <h3>${dessert.nom}</h3>
            <p>${dessert.description}</p>
            <p><strong>${dessert.prix} €</strong></p>
            <div class="plat-actions">
                <button class="btn btn-warning edit-dessert" 
                    data-id="${dessert.id}"
                    data-nom="${dessert.nom}"
                    data-prix="${dessert.prix}"
                    data-description="${dessert.description}">
                    Modifier
                </button>
                <button class="btn btn-danger delete-dessert" 
                    data-id="${dessert.id}">
                    Supprimer
                </button>
            </div>
        `;
        dessertList.appendChild(dessertDiv);
    });

    // Ajouter les écouteurs d'événements pour les boutons
    document.querySelectorAll('.edit-dessert').forEach(button => {
        button.addEventListener('click', function () {
            const {id, nom, prix, description} = this.dataset;
            showEditDessertForm(id, nom, prix, description);
        });
    });

    document.querySelectorAll('.delete-dessert').forEach(button => {
        button.addEventListener('click', function () {
            const id = this.dataset.id;
            deleteDessert(id);
        });
    });
}

// Ajouter un dessert
document.getElementById('addDessertForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const dessertData = {
        nom: document.getElementById('nomDessert').value.trim(),
        prix: parseFloat(document.getElementById('prixDessert').value),
        description: document.getElementById('descriptionDessert').value.trim()
    };

    try {
        const response = await fetch(API_DESSERTS_URL, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(dessertData)
        });

        if (response.ok) {
            alert('Dessert ajouté avec succès!');
            document.getElementById('addDessertForm').reset();
            loadDesserts();
        }
    } catch (error) {
        console.error('Erreur:', error);
        alert('Erreur lors de l\'ajout du dessert');
    }
});

// Afficher le formulaire de modification de dessert
function showEditDessertForm(id, nom, prix, description) {
    document.getElementById('editDessertForm').style.display = 'block';
    document.getElementById('editDessertId').value = id;
    document.getElementById('editDessertNom').value = nom;
    document.getElementById('editDessertPrix').value = prix;
    document.getElementById('editDessertDescription').value = description;
    document.getElementById('editDessertForm').scrollIntoView({behavior: 'smooth'});
}

// Cacher le formulaire de modification de dessert
function hideEditDessertForm() {
    document.getElementById('editDessertForm').style.display = 'none';
}

// Mettre à jour un dessert
document.getElementById('updateDessertForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const id = document.getElementById('editDessertId').value;
    const dessertData = {
        id: id,
        nom: document.getElementById('editDessertNom').value.trim(),
        prix: parseFloat(document.getElementById('editDessertPrix').value),
        description: document.getElementById('editDessertDescription').value.trim()
    };

    try {
        const response = await fetch(`${API_DESSERTS_URL}/${id}`, {
            method: 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(dessertData)
        });

        if (response.ok) {
            alert('Dessert modifié avec succès!');
            hideEditDessertForm();
            loadDesserts();
        }
    } catch (error) {
        console.error('Erreur:', error);
        alert('Erreur lors de la modification du dessert');
    }
});

// Supprimer un dessert
async function deleteDessert(id) {
    if (confirm('Voulez-vous vraiment supprimer ce dessert?')) {
        try {
            const response = await fetch(`${API_DESSERTS_URL}/${id}`, {
                method: 'DELETE'
            });

            if (response.ok) {
                alert('Dessert supprimé avec succès!');
                loadDesserts();
            }
        } catch (error) {
            console.error('Erreur:', error);
            alert('Erreur lors de la suppression du dessert');
        }
    }
}


// Ajouter l'URL de l'API des menus avec les autres URLs en haut du fichier
const API_MENU_URL = 'http://localhost:8080/api/menu';

// Ajouter l'appel à loadMenus() dans le DOMContentLoaded
document.addEventListener('DOMContentLoaded', () => {
    loadPlats();
    loadEntrees();
    loadDesserts();
    loadMenus();
});

// ============= GESTION DES MENUS =============

// Fonction pour charger tous les menus
async function loadMenus() {
    try {
        const response = await fetch(API_MENU_URL);
        const menus = await response.json();
        displayMenus(menus);
    } catch (error) {
        console.error('Erreur:', error);
        alert('Erreur lors du chargement des menus');
    }
}

// Afficher les menus dans la liste
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
                <button class="btn btn-warning edit-menu" 
                    data-id="${menu.id}"
                    data-nom="${menu.nom}"
                    data-prix="${menu.prix}"
                    data-description="${menu.description}">
                    Modifier
                </button>
                <button class="btn btn-danger delete-menu" 
                    data-id="${menu.id}">
                    Supprimer
                </button>
            </div>
        `;
        menuList.appendChild(menuDiv);
    });

    // Ajouter les écouteurs d'événements pour les boutons
    document.querySelectorAll('.edit-menu').forEach(button => {
        button.addEventListener('click', function () {
            const {id, nom, prix, description} = this.dataset;
            showEditMenuForm(id, nom, prix, description);
        });
    });

    document.querySelectorAll('.delete-menu').forEach(button => {
        button.addEventListener('click', function () {
            const id = this.dataset.id;
            deleteMenu(id);
        });
    });
}

// Ajouter un menu
document.getElementById('addMenuForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const menuData = {
        nom: document.getElementById('nomMenu').value.trim(),
        prix: parseFloat(document.getElementById('prixMenu').value),
        description: document.getElementById('descriptionMenu').value.trim()
    };

    try {
        const response = await fetch(API_MENU_URL, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(menuData)
        });

        if (response.ok) {
            alert('Menu ajouté avec succès!');
            document.getElementById('addMenuForm').reset();
            loadMenus();
        }
    } catch (error) {
        console.error('Erreur:', error);
        alert('Erreur lors de l\'ajout du menu');
    }
});

// Afficher le formulaire de modification de menu
function showEditMenuForm(id, nom, prix, description) {
    document.getElementById('editMenuForm').style.display = 'block';
    document.getElementById('editMenuId').value = id;
    document.getElementById('editMenuNom').value = nom;
    document.getElementById('editMenuPrix').value = prix;
    document.getElementById('editMenuDescription').value = description;
    document.getElementById('editMenuForm').scrollIntoView({behavior: 'smooth'});
}

// Cacher le formulaire de modification de menu
function hideEditMenuForm() {
    document.getElementById('editMenuForm').style.display = 'none';
}

// Mettre à jour un menu
document.getElementById('updateMenuForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const id = document.getElementById('editMenuId').value;
    const menuData = {
        id: id,
        nom: document.getElementById('editMenuNom').value.trim(),
        prix: parseFloat(document.getElementById('editMenuPrix').value),
        description: document.getElementById('editMenuDescription').value.trim()
    };

    try {
        const response = await fetch(`${API_MENU_URL}/${id}`, {
            method: 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(menuData)
        });

        if (response.ok) {
            alert('Menu modifié avec succès!');
            hideEditMenuForm();
            loadMenus();
        }
    } catch (error) {
        console.error('Erreur:', error);
        alert('Erreur lors de la modification du menu');
    }
});

// Supprimer un menu
async function deleteMenu(id) {
    if (confirm('Voulez-vous vraiment supprimer ce menu?')) {
        try {
            const response = await fetch(`${API_MENU_URL}/${id}`, {
                method: 'DELETE'
            });

            if (response.ok) {
                alert('Menu supprimé avec succès!');
                loadMenus();
            }
        } catch (error) {
            console.error('Erreur:', error);
            alert('Erreur lors de la suppression du menu');
        }
    }
}


    function showSection(sectionId) {
    // Cacher toutes les sections
    document.querySelectorAll('.section').forEach(section => {
        section.classList.remove('active');
    });

    // Afficher la section sélectionnée
    document.getElementById(sectionId).classList.add('active');
}

    // Afficher la première section par défaut
    window.onload = function() {
    showSection('info-section');
};
