const API_BASE_URL = 'http://localhost:8080';
let notificationToast;

document.addEventListener('DOMContentLoaded', function() {
    // Initialisation du toast
    notificationToast = new bootstrap.Toast(document.getElementById('notification'));

    // Gestionnaire de soumission du formulaire manager
    document.getElementById('managerForm').addEventListener('submit', handleManagerSubmit);

    // Gestionnaire de soumission du formulaire restaurant
    document.getElementById('restaurantForm').addEventListener('submit', handleRestaurantSubmit);
});

function showNotification(message, isError = false) {
    const toastBody = document.getElementById('notificationMessage');
    toastBody.textContent = message;
    toastBody.className = isError ? 'toast-body text-danger' : 'toast-body text-success';
    notificationToast.show();
}

// Validation des formulaires
function validateForm(form) {
    if (!form.checkValidity()) {
        form.classList.add('was-validated');
        return false;
    }
    return true;
}

async function handleManagerSubmit(event) {
    event.preventDefault();

    const form = event.target;
    if (!validateForm(form)) return;

    try {
        const managerData = {
            username: document.getElementById('username').value,
            email: document.getElementById('email').value,
            password: document.getElementById('password').value
        };

        const response = await fetch(`${API_BASE_URL}/managers`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(managerData)
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Erreur HTTP ${response.status}: ${errorText}`);
        }

        const manager = await response.json();

        // Stocker l'ID du manager et passer à l'étape 2
        document.getElementById('managerId').value = manager.id;
        showStep2();
        showNotification('Compte créé avec succès ! Vous pouvez maintenant créer votre restaurant.');

    } catch (error) {
        console.error('Erreur lors de la création du compte:', error);
        showNotification(`Erreur lors de la création du compte: ${error.message}`, true);
    }
}

async function handleRestaurantSubmit(event) {
    event.preventDefault();

    const form = event.target;
    if (!validateForm(form)) return;

    const managerId = document.getElementById('managerId').value;

    try {
        const restaurantData = {
            nom: document.getElementById('nom').value,
            adresse: document.getElementById('adresse').value,
            ville: document.getElementById('ville').value,
            telephone: document.getElementById('telephone').value,
            description: document.getElementById('description').value,
            manager: {
                id: managerId
            }
        };

        const response = await fetch(`${API_BASE_URL}/managers/${managerId}/restaurant`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(restaurantData)
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Erreur HTTP ${response.status}: ${errorText}`);
        }

        await response.json();
        showNotification('Restaurant créé avec succès !');

        // Redirection vers le dashboard après un court délai
        setTimeout(() => {
            window.location.href = `/dashboard.html?managerId=${managerId}`;
        }, 1500);

    } catch (error) {
        console.error('Erreur lors de la création du restaurant:', error);
        showNotification(`Erreur lors de la création du restaurant: ${error.message}`, true);
    }
}

function showStep2() {
    document.getElementById('step1').classList.add('d-none');
    document.getElementById('step2').classList.remove('d-none');
}

function retourEtape1() {
    document.getElementById('step2').classList.add('d-none');
    document.getElementById('step1').classList.remove('d-none');
}