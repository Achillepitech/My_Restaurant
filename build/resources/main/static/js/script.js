



async function fetchRestaurantInfo() {
    try {
        const response = await fetch('http://localhost:8080/api/restaurant/info');
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

function updateElement(id, content) {
    const element = document.getElementById(id);
    if (element) {
        element.textContent = content;
    } else {
        console.warn(`Élément avec l'ID ${id} non trouvé`);
    }
}

function updateHeuresOuverture(heures) {
    const heuresListe = document.getElementById('restaurant-heures');
    if (!heuresListe) {
        console.warn('Liste des heures non trouvée');
        return;
    }

    const horairesList = Object.entries(heures).map(([jour, horaire]) => {
        return `<li>
            <span class="jour">${jour}</span>
            <span class="horaire">${horaire}</span>
        </li>`;
    });

    heuresListe.innerHTML = horairesList.join('');
}


// Appeler la fonction au chargement de la page
document.addEventListener('DOMContentLoaded', fetchRestaurantInfo);






// Fonction pour afficher un message d'erreur
function displayErrorMessage() {
    const container = document.querySelector('.restaurant-container');
    if (container) {
        const errorMessage = document.createElement('div');
        errorMessage.className = 'error-message';
        errorMessage.textContent = 'Une erreur est survenue lors du chargement des informations du restaurant.';
        container.insertBefore(errorMessage, container.firstChild);
    }
}

document.addEventListener('DOMContentLoaded', fetchRestaurantInfo);

async function getPlats() {
    try {
        const response = await fetch('http://localhost:8080/api/plats');
        const plats = await response.json();
        console.log("Plats reçus:", plats);

        const platsDiv = document.getElementById('plats');
        platsDiv.innerHTML = '';

        plats.forEach(plat => {
            const platHTML = `
                <div class="plat-card">
                    ${plat.image ? `<div class="plat-image">
                        <img src="${plat.image}" alt="${plat.nom}" 
                             onerror="this.style.display='none'">
                    </div>` : ''}
                    <h3>${plat.nom}</h3>
                    <p class="description">${plat.description || 'Description non disponible'}</p>
                    <div class="prix">${plat.prix.toFixed(2)} €</div>
                </div>
            `;

            platsDiv.innerHTML += platHTML;
        });
    } catch (error) {
        console.error('Erreur lors du chargement des plats:', error);
        const platsDiv = document.getElementById('plats');
        platsDiv.innerHTML = `
            <div class="error-message">
                <p>Erreur lors du chargement des plats</p>
            </div>
        `;
    }
}

    async function getEntrees() {
    try {
    const response = await fetch('http://localhost:8080/api/entrees');
    const entrees = await response.json();
    console.log("Entrées reçues:", entrees);

    const entreesDiv = document.getElementById('entrees');
    entreesDiv.innerHTML = '';

    entrees.forEach(entree => {
    const platHTML = `
      <div class="plat-card">
        <h3>${entree.nom}</h3>
        <p class="description">${entree.description || ''}</p>
        <div class="prix">${entree.prix} €</div>
      </div>
    `;

    entreesDiv.innerHTML += platHTML;
});
} catch (error) {
    console.error('Erreur lors du chargement des entrées:', error);
    const entreesDiv = document.getElementById('entrees');
    entreesDiv.innerHTML = '<p>Erreur lors du chargement des entrées</p>';
}
}


async function getDesserts() {
    try {
        const response = await fetch('http://localhost:8080/api/desserts');
        const desserts = await response.json();
        console.log("Desserts reçus:", desserts);

        const dessertsDiv = document.getElementById('desserts');
        dessertsDiv.innerHTML = '';

        desserts.forEach(dessert => {
            const dessertHTML = `
                <div class="plat-card">
                    ${dessert.image ? `<div class="plat-image">
                        <img src="${dessert.image}" alt="${dessert.nom}" 
                             onerror="this.style.display='none'">
                    </div>` : ''}
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
        dessertsDiv.innerHTML = `
            <div class="error-message">
                <p>Erreur lors du chargement des desserts</p>
            </div>
        `;
    }
}

async function getMenus() {
    try {
        const response = await fetch('http://localhost:8080/api/menu');
        const menus = await response.json();
        console.log("Menus reçus:", menus);

        const menuDiv = document.getElementById('menu');
        menuDiv.innerHTML = '';

        menus.forEach(menu => {
            const menuHTML = `
                <div class="menu-card">
                    ${menu.image ? `
                        <div class="menu-header">
                            <img src="${menu.image}" 
                                 alt="${menu.nom}"
                                 onerror="this.src='/images/default-menu.jpg'">
                        </div>
                    ` : ''}
                    <div class="menu-content">
                        <h3 class="menu-title">${menu.nom}</h3>
                        <div class="menu-description">
                            ${menu.description.split('\n').map(line => `<p>${line}</p>`).join('')}
                        </div>
                        <div class="menu-price-container">
                            <span class="menu-price">${menu.prix.toFixed(2)} €</span>
                        </div>
                    </div>
                </div>
            `;

            menuDiv.innerHTML += menuHTML;
        });
    } catch (error) {
        console.error('Erreur lors du chargement des menus:', error);
        const menuDiv = document.getElementById('menu');
        menuDiv.innerHTML = `
            <div class="error-message">
                <p>Erreur lors du chargement des menus</p>
            </div>
        `;
    }
}

// Assurez-vous d'ajouter getMenus() à vos appels au chargement de la page
document.addEventListener('DOMContentLoaded', () => {
    fetchRestaurantInfo();
    getEntrees();
    getPlats();
    getMenus();
    getDesserts();
});


