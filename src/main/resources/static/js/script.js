document.addEventListener('DOMContentLoaded', function () {
    // Function to fetch petitions and populate the view_all.html page
    function fetchPetitions() {
        fetch('/api/petitions') // Adjust the API endpoint based on your backend
            .then(response => response.json())
            .then(data => {
                const petitionsContainer = document.getElementById('petitions-container');
                petitionsContainer.innerHTML = ''; // Clear previous content

                data.forEach(petition => {
                    const petitionDiv = document.createElement('div');
                    petitionDiv.innerHTML = `
                        <h3>${petition.name}</h3>
                        <p>${petition.description}</p>
                        <a href="/view_petition?id=${petition.id}">View Details</a>
                        <hr>
                    `;
                    petitionsContainer.appendChild(petitionDiv);
                });
            })
            .catch(error => console.error('Error fetching petitions:', error));
    }

    // Call the fetchPetitions function when the page loads
    fetchPetitions();
});
