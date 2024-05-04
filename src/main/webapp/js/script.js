var wishlistModal = document.getElementById('wishlistModal');
var wishlistBtns = document.querySelectorAll('.wishlistBtn');
var wishlistClose = document.getElementsByClassName("close")[0];

wishlistBtns.forEach(btn => btn.onclick = function () {
        wishlistModal.style.display = "block";

        // Clear previous options and populate new ones based on button data
        var equipmentIDs = this.getAttribute('data-id').split(',');
        var select = document.getElementById('equipmentID');
        select.innerHTML = '';

        equipmentIDs.forEach(function (id) {
            var option = document.createElement('option');
            option.value = id.trim();
            option.text = id.trim();
            select.appendChild(option);
        });

        // Populate other data fields
        document.getElementById('name').value = this.getAttribute('data-name');
        document.getElementById('available').value = this.getAttribute('data-available');
        document.getElementById('campusName').value = this.getAttribute('data-campus');
        document.getElementById('cartCurrentCampus').value = this.getAttribute('data-currentcampus');
        document.getElementById('condition').value = this.getAttribute('data-condition');
        document.getElementById('exclusiveForStaff').value = this.getAttribute('data-exclusive');
        document.getElementById('description').value = this.getAttribute('data-description');
    });

// Close the wishlist modal with close button or outside click
wishlistClose.onclick = function () {
    wishlistModal.style.display = "none";
};
window.onclick = function (event) {
    if (event.target == wishlistModal) {
        wishlistModal.style.display = "none";
    }
};

var cartModal = document.getElementById('cartModal');
var cartBtns = document.querySelectorAll('.cartBtn');
var cartClose = document.getElementsByClassName("close")[1]; // Assuming second close button is for cart

cartBtns.forEach(btn => btn.onclick = function () {
        cartModal.style.display = "block";

        // Populate fields for the cart modal, similar to wishlist
        var totalAvailable = this.getAttribute('data-total'); // Get the total available from data attribute

        document.getElementById('cartName').value = this.getAttribute('data-name');
        document.getElementById('cartAvailable').value = this.getAttribute('data-available');
        document.getElementById('cartCampus').value = this.getAttribute('data-campus');
        document.getElementById('cartCondition').value = this.getAttribute('data-condition');
        document.getElementById('cartDescription').value = this.getAttribute('data-description');
        document.getElementById('cartTotal').value = totalAvailable;
        document.getElementById('cartExclusiveForStaff').value = this.getAttribute('data-exclusive');

        var quantityInput = document.getElementById('cartQuantity');
        quantityInput.setAttribute('max', totalAvailable); // Set maximum value
        quantityInput.value = 1; // Reset to default minimum value whenever modal is opened

        // Set date fields
        var today = new Date().toISOString().split('T')[0];
        var dateFromInput = document.getElementById('cartReservedFrom');
        var dateToInput = document.getElementById('cartReservedTo');
        dateFromInput.setAttribute('min', today);
        dateToInput.setAttribute('min', today);
    });

cartClose.onclick = function () {
    cartModal.style.display = "none";
};

window.onclick = function (event) {
    if (event.target == cartModal) {
        cartModal.style.display = "none";
    }
};
