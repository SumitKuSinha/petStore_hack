document.addEventListener("DOMContentLoaded", () => {
    const modal = document.getElementById("checkout-modal");

    // Check if the modal exists on this page
    if (!modal) {
        return;
    }

    const modalItemName = document.getElementById("modal-item-name");
    const modalItemPrice = document.getElementById("modal-item-price");
    const closeModal = document.getElementById("close-modal");

    const buyButtons = document.querySelectorAll(".btn-buy");

    buyButtons.forEach(button => {
        button.addEventListener("click", (e) => {
            e.preventDefault();

            const name = button.getAttribute("data-name");
            const price = button.getAttribute("data-price");

            modalItemName.textContent = name;
            modalItemPrice.textContent = "$" + price;

            // Use 'flex' to match our CSS
            modal.style.display = "flex";
        });
    });

    closeModal.addEventListener("click", () => {
        modal.style.display = "none";
    });

    // Close modal if user clicks outside of it
    window.addEventListener("click", (e) => {
        if (e.target == modal) {
             modal.style.display = "none";
        }
    });
});