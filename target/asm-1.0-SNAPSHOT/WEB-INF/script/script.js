/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


var modal = document.getElementById('wishlistModal');


var btns = document.querySelectorAll('.wishlistBtn');


var span = document.getElementsByClassName("close")[0];


btns.forEach(btn => btn.onclick = function() {
    modal.style.display = "block";
    document.getElementById('equipmentID').value = this.getAttribute('data-id');
    document.getElementById('available').value = this.getAttribute('data-available');
    document.getElementById('campusName').value = this.getAttribute('data-campus');
    document.getElementById('condition').value = this.getAttribute('data-condition');
    document.getElementById('exclusiveForStaff').value = this.getAttribute('data-exclusive');
    document.getElementById('description').value = this.getAttribute('data-description');
});


span.onclick = function() {
    modal.style.display = "none";
}


window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
