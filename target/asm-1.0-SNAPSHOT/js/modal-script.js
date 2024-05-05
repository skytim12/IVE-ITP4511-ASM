// Get the modal elements
var editModal = document.getElementById('editModal');
var deleteModal = document.getElementById('deleteModal');
var addModal = document.getElementById('addModal');

var editCloseButton = document.querySelector('#editModal .close');
var deleteCloseButton = document.querySelector('#deleteModal .close');
var addCloseButton = document.querySelector('#addModal .close');

// Function to open the edit modal and set input values
function showEditModal(data) {
    // Set input values
    setInputValue('editEquipmentID', data.equipmentID);
    setInputValue('editDescription', data.description);
    setInputValue('editCurrentCampus', data.currentCampus);
    setInputValue('editOriginalCampus', data.campusName);
    setInputValue('editCondition', data.condition);

    // Set radio inputs
    setRadioValue('available', data.available);
    setRadioValue('exclusiveForStaff', data.exclusiveForStaff);

    // Display the modal
    editModal.style.display = 'block';
}

// Function to set input values
function setInputValue(id, value) {
    var input = document.getElementById(id);
    if (input) {
        input.value = value;
    }
}

// Function to set the checked state of radio buttons
function setRadioValue(name, value) {
    var radios = document.querySelectorAll(`input[name="${name}"]`);

    radios.forEach(function (radio) {
        radio.checked = radio.value === value;
    });
}

// Function to close the edit modal
function closeEditModal() {
    editModal.style.display = 'none';
}

// Close button event listener for edit modal
editCloseButton.onclick = function () {
    closeEditModal();
};

// Function to show the delete modal
function showDeleteModal(equipmentID) {
    // Get the delete modal element
    var deleteModal = document.getElementById('deleteModal');

    // Set the equipment ID in the hidden input field
    var deleteEquipmentIDInput = document.getElementById('deleteEquipmentID');
    deleteEquipmentIDInput.value = equipmentID;

    // Display the delete modal
    deleteModal.style.display = 'block';
}

// Function to close the delete modal
function closeDeleteModal() {
    // Get the delete modal element
    var deleteModal = document.getElementById('deleteModal');

    // Hide the delete modal
    deleteModal.style.display = 'none';
}

// Function to show the add modal
function showAddModal() {
    addModal.style.display = 'block';
}

// Function to close the add modal
function closeAddModal() {
    addModal.style.display = 'none';
}

// Close modal if outside click
window.onclick = function (event) {
    if (event.target === editModal) {
        closeEditModal();
    } else if (event.target === deleteModal) {
        closeDeleteModal();
    } else if (event.target === addModal) {
        closeAddModal();
    }
};

// Close button event listeners
editCloseButton.onclick = function () {
    closeEditModal();
};

deleteCloseButton.onclick = function () {
    closeDeleteModal();
};

addCloseButton.onclick = function () {
    closeAddModal();
};
