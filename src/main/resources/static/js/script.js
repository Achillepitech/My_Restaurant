const MANAGER_ID = '1';
const API_BASE_URL = 'http://localhost:8080/managers/' + MANAGER_ID + '/restaurant';

// Fonction pour récupérer et afficher les informations du restaurant
async function fetchRestaurantInfo() {
    try {
        const response = await fetch(API_BASE_URL);
        const restaurant = await response.json();

        // Mise à jour des informations principales
        updateElement('restaurant-nom', restaurant.nom);
        updateElement('restaurant-description', restaurant.description);
        updateElement('restaurant-adresse', restaurant.adresse);
        updateElement('restaurant-telephone', restaurant.telephone);
        updateElement('restaurant-ville', restaurant.ville);
        updateElement('restaurant-presentation', restaurant.presentationText);

        // Mise à jour des heures d'ouverture
        updateHeuresOuverture(restaurant.heuresOuverture);

    } catch (error) {
        console.error('Erreur lors de la récupération des données :', error);
        displayErrorMessage();
    }
}

// Fonction pour mettre à jour les heures d'ouverture
function updateHeuresOuverture(heures) {
    const heuresListe = document.getElementById('restaurant-heures');
    if (!heuresListe || !heures) return;

    let horaireHTML = '';
    const joursSemaine = ['Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi', 'Dimanche'];

    joursSemaine.forEach(jour => {
        const horaire = heures[jour.toLowerCase()] || 'Fermé';
        horaireHTML += `
            <li>
                <span class="jour">${jour}</span>
                <span class="horaire">${horaire}</span>
            </li>`;
    });

    heuresListe.innerHTML = horaireHTML;
}

// Fonction pour récupérer et afficher les menus
async function getMenus() {
    try {
        // Utilisation du bon endpoint défini dans l'API
        const response = await fetch(`${API_BASE_URL}/menus`);
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
        const menus = await response.json();

        const menuDiv = document.getElementById('menu');
        menuDiv.innerHTML = '';

        // Filtre pour n'afficher que les menus actifs
        const activeMenus = menus.filter(menu => menu.actif);

        if (activeMenus.length === 0) {
            menuDiv.innerHTML = '<p>Aucun menu disponible</p>';
            return;
        }

        activeMenus.forEach(menu => {
            const menuHTML = `
                <div class="plat-card">
                    <h3>${menu.nom || 'Sans nom'}</h3>
                    <p class="description">${menu.description || 'Description non disponible'}</p>
                    <div class="prix">${menu.prix ? menu.prix.toFixed(2) + ' €' : 'Prix non disponible'}</div>
                </div>
            `;
            menuDiv.innerHTML += menuHTML;
        });
    } catch (error) {
        console.error('Erreur lors du chargement des menus:', error);
        const menuDiv = document.getElementById('menu');
        menuDiv.innerHTML = '<p>Erreur lors du chargement des menus</p>';
    }
}

// Fonction pour récupérer et afficher les entrées
async function getEntrees() {
    try {
        const response = await fetch(`${API_BASE_URL}/entrees`);
        const entrees = await response.json();

        const entreesDiv = document.getElementById('entrees');
        entreesDiv.innerHTML = '';

        entrees.forEach(entree => {
            const entreeHTML = `
                <div class="plat-card">
                    <h3>${entree.nom}</h3>
                    <p class="description">${entree.description || ''}</p>
                    <div class="prix">${entree.prix.toFixed(2)} €</div>
                </div>
            `;
            entreesDiv.innerHTML += entreeHTML;
        });
    } catch (error) {
        console.error('Erreur lors du chargement des entrées:', error);
        const entreesDiv = document.getElementById('entrees');
        entreesDiv.innerHTML = '<p>Erreur lors du chargement des entrées</p>';
    }
}

// Fonction pour récupérer et afficher les plats
async function getPlats() {
    try {
        const response = await fetch(`${API_BASE_URL}/plats`);
        const plats = await response.json();

        const platsDiv = document.getElementById('plats');
        platsDiv.innerHTML = '';

        plats.forEach(plat => {
            if (plat.actif) { // N'afficher que les plats actifs
                const platHTML = `
                    <div class="plat-card">
                        <h3>${plat.nom}</h3>
                        <p class="description">${plat.description || 'Description non disponible'}</p>
                        <div class="prix">${plat.prix.toFixed(2)} €</div>
                    </div>
                `;
                platsDiv.innerHTML += platHTML;
            }
        });
    } catch (error) {
        console.error('Erreur lors du chargement des plats:', error);
        const platsDiv = document.getElementById('plats');
        platsDiv.innerHTML = '<p>Erreur lors du chargement des plats</p>';
    }
}

// Fonction pour récupérer et afficher les desserts
async function getDesserts() {
    try {
        const response = await fetch(`${API_BASE_URL}/desserts`);
        const desserts = await response.json();

        const dessertsDiv = document.getElementById('desserts');
        dessertsDiv.innerHTML = '';

        desserts.forEach(dessert => {
            const dessertHTML = `
                <div class="plat-card">
                    <h3>${dessert.nom}</h3>
                    <p class="description">${dessert.description || 'Description non disponible'}</p>
                    <div class="prix">${dessert.prix.toFixed(2)} €</div>
                </div>
            `;
            dessertsDiv.innerHTML += dessertHTML;
        });
    } catch (error) {
        console.error('Erreur lors du chargement des desserts:', error);
        const dessertsDiv = document.getElementById('desserts');
        dessertsDiv.innerHTML = '<p>Erreur lors du chargement des desserts</p>';
    }
}

// Fonction utilitaire pour mettre à jour les éléments HTML
function updateElement(id, content) {
    const element = document.getElementById(id);
    if (element && content) {
        element.textContent = content;
    }
}

// Fonction pour afficher les messages d'erreur
function displayErrorMessage() {
    const container = document.querySelector('.info-section');
    if (container) {
        const errorMessage = document.createElement('div');
        errorMessage.className = 'error-message';
        errorMessage.textContent = 'Une erreur est survenue lors du chargement des informations du restaurant.';
        container.insertBefore(errorMessage, container.firstChild);
    }
}

// Initialisation au chargement de la page
document.addEventListener('DOMContentLoaded', () => {
    fetchRestaurantInfo();
    getEntrees();
    getPlats();
    getDesserts();
    getMenus();
});

