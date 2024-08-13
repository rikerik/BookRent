function rentFunction() {
  alert("Book rented!");
}
function alreadyRented() {
  alert("Book is already rented!");
}
function returnFunction() {
  alert("Book returned to the library!");
}
function registerBook() {
  alert(
    "Book added to library! After a few moments, the book will become visible in the library."
  );
}

document.addEventListener("DOMContentLoaded", function () {
  const modalMessage = document.getElementById("noBook").textContent;

  if (modalMessage && modalMessage.trim() !== "") {
    alert(modalMessage);
  }
});
