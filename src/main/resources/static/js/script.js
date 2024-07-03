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
  alert("Book added to library!");
}

document.addEventListener("DOMContentLoaded", function () {
  // Ellenőrizze, hogy van-e üzenet az "noBook" id-jú elem tartalmában és nem üres
  var modalMessage = document.getElementById("noBook").textContent;

  // Ellenőrizze, hogy a modalMessage egy érvényes string, és nem üres
  if (modalMessage && modalMessage.trim() !== "") {
    // Ha van üzenet, akkor jelenítse meg az alert-et
    alert(modalMessage);
  }
});
