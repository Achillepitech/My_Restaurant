// URL de base de l'API pour les entrées
const MANAGER_ID = '1';


const API_ENTREES_URL = 'http://localhost:8080/managers/'+MANAGER_ID+'/restaurant/entrees';

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
                <button class="btn btn-warning" onclick="showEditForm(${entree.id}, '${entree.nom}', ${entree.prix}, '${entree.description}')">
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
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(entreeData)
        });

        if (response.ok) {
            alert('Entrée ajoutée avec succès!');
            document.getElementById('addEntreeForm').reset();
            loadEntrees();
        }
    } catch (error) {
        console.error('Erreur:', error);
        alert('Erreur lors de l\'ajout de l\'entrée');
    }
});

// Afficher le formulaire de modification
function showEditForm(id, nom, prix, description) {
    const form = document.getElementById('editEntreeForm');
    form.style.display = 'block';
    document.getElementById('editEntreeId').value = id;
    document.getElementById('editEntreeNom').value = nom;
    document.getElementById('editEntreePrix').value = prix;
    document.getElementById('editEntreeDescription').value = description;
    form.scrollIntoView({ behavior: 'smooth' });
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
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(entreeData)
        });

        if (response.ok) {
            alert('Entrée modifiée avec succès!');
            hideEditEntreeForm();
            loadEntrees();
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
            }
        } catch (error) {
            console.error('Erreur:', error);
            alert('Erreur lors de la suppression de l\'entrée');
        }
    }
}

// URL de l'API pour les desserts
 // À remplacer par l'ID du manager connecté
const API_DESSERTS_URL = `http://localhost:8080/managers/${MANAGER_ID}/restaurant/desserts`;

// Charger les desserts au démarrage
document.addEventListener('DOMContentLoaded', loadDesserts);

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
                <button class="btn btn-warning" 
                    onclick="showEditDessertForm(${dessert.id}, '${dessert.nom}', ${dessert.prix}, '${dessert.description}')">
                    Modifier
                </button>
                <button class="btn btn-danger" onclick="deleteDessert(${dessert.id})">
                    Supprimer
                </button>
            </div>
        `;
        dessertList.appendChild(dessertDiv);
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
            headers: { 'Content-Type': 'application/json' },
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

// Afficher le formulaire de modification
function showEditDessertForm(id, nom, prix, description) {
    document.getElementById('editDessertForm').style.display = 'block';
    document.getElementById('editDessertId').value = id;
    document.getElementById('editDessertNom').value = nom;
    document.getElementById('editDessertPrix').value = prix;
    document.getElementById('editDessertDescription').value = description;
    document.getElementById('editDessertForm').scrollIntoView({ behavior: 'smooth' });
}

// Cacher le formulaire de modification
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
            headers: { 'Content-Type': 'application/json' },
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