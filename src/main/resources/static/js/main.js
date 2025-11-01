document.addEventListener('DOMContentLoaded', function() {
    // 1. Get the Bootstrap Modal element
    const checkoutModal = new bootstrap.Modal(document.getElementById('checkout-modal'));
    
    // 2. Find all "Buy Now" and "Adopt Now" buttons across the page
    const buyButtons = document.querySelectorAll('.btn-buy');

    // 3. Attach an event listener to each button
    buyButtons.forEach(button => {
        button.addEventListener('click', function(event) {
            // Prevent the default action (like navigating to '#')
            event.preventDefault(); 
            
            // 4. Get data attributes from the clicked button
            const itemName = this.getAttribute('data-name');
            const itemPrice = this.getAttribute('data-price');
            
            // 5. Update the content inside the modal
            document.getElementById('modal-item-name').textContent = itemName;
            document.getElementById('modal-item-price').textContent = '$' + parseFloat(itemPrice).toFixed(2);
            
            // 6. Use the Bootstrap JavaScript method to show the modal
            checkoutModal.show();
        });
    });

    // Note: Bootstrap automatically handles the modal close buttons 
    // (e.g., the 'x' button and clicking outside) because of the 
    // data-bs-dismiss="modal" attribute we added in index.html.
});