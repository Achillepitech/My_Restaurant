// Attendre que le DOM soit chargé
document.addEventListener('DOMContentLoaded', () => {
    // Récupération des éléments du DOM
    const signupForm = document.getElementById('signupForm');
    const restaurantForm = document.getElementById('restaurantForm');
    const restaurantModal = new bootstrap.Modal(document.getElementById('restaurantModal'));
    let managerId = null; // Variable pour stocker l'ID du manager créé

    // Gestionnaire d'événement pour le formulaire d'inscription du manager
    signupForm.addEventListener('submit', async (e) => {
        e.preventDefault(); // Empêcher le comportement par défaut du formulaire

        // Récupération des valeurs des champs de mot de passe
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirmPassword').value;

        // Vérification de la correspondance des mots de passe
        if (password !== confirmPassword) {
            alert('Les mots de passe ne correspondent pas');
            return;
        }

        // Création de l'objet avec les données du manager
        const managerData = {
            username: document.getElementById('username').value,
            email: document.getElementById('email').value,
            password: password
        };

        try {
            // Envoi de la requête POST pour créer le manager
            const response = await fetch('/managers', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(managerData)
            });

            // Vérification de la réponse
            if (!response.ok) {
                const errorData = await response.text();
                throw new Error(`Erreur lors de la création du compte: ${errorData}`);
            }

            // Récupération et stockage de l'ID du manager créé
            const manager = await response.json();
            managerId = manager.id;

            // Affichage du modal pour la création du restaurant
            restaurantModal.show();

        } catch (error) {
            console.error('Erreur:', error);
            alert(error.message);
        }
    });

    // Gestionnaire d'événement pour le formulaire de création du restaurant
    restaurantForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        // Vérification de l'existence de l'ID du manager
        if (!managerId) {
            alert('Erreur: Aucun manager trouvé');
            return;
        }

        // Création de l'objet avec les données du restaurant
        const restaurantData = {
            nom: document.getElementById('nomRestaurant').value,
            description: document.getElementById('description').value,
            adresse: document.getElementById('adresse').value,
            telephone: document.getElementById('telephone').value,
            ville: document.getElementById('ville').value,
            presentationText: document.getElementById('presentation').value,
            heuresOuverture: {
                "lundi": "12:00-14:30, 19:00-22:30",
                "mardi": "12:00-14:30, 19:00-22:30",
                "mercredi": "12:00-14:30, 19:00-22:30",
                "jeudi": "12:00-14:30, 19:00-22:30",
                "vendredi": "12:00-14:30, 19:00-22:30",
                "samedi": "12:00-14:30, 19:00-23:00",
                "dimanche": "Fermé"
            },
            // Inclusion des informations du manager
            manager: {
                id: managerId,
                username: document.getElementById('username').value,
                email: document.getElementById('email').value,
                password: document.getElementById('password').value
            }
        };

        try {
            // Envoi de la requête POST pour créer le restaurant
            const response = await fetch(`/managers/${managerId}/restaurant`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(restaurantData)
            });

            // Vérification de la réponse
            if (!response.ok) {
                const errorData = await response.text();
                throw new Error(`Erreur lors de la création du restaurant: ${errorData}`);
            }

            // Notification du succès et redirection
            alert('Restaurant créé avec succès');
            window.location.href = '/dashboard';

        } catch (error) {
            console.error('Erreur:', error);
            alert(error.message);
        }
    });

    // Ajout des validations supplémentaires si nécessaire
    const validatePhoneNumber = (phone) => {
        const phoneRegex = /^[0-9]{10}$/;
        return phoneRegex.test(phone);
    };

    document.getElementById('telephone').addEventListener('input', (e) => {
        const phone = e.target.value.replace(/\D/g, ''); // Supprimer tous les caractères non numériques
        if (phone.length > 10) {
            e.target.value = phone.slice(0, 10); // Limiter à 10 chiffres
        } else {
            e.target.value = phone;
        }
    });

    // Validation de l'email
    document.getElementById('email').addEventListener('input', (e) => {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        const isValid = emailRegex.test(e.target.value);
        e.target.classList.toggle('is-invalid', !isValid);
    });
});