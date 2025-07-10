    document.addEventListener("DOMContentLoaded", function () {
    const btn = document.getElementById("btn-batalla-pendiente");
    if (btn) {
    btn.addEventListener("click", function (e) {
    e.preventDefault(); // prevenir navegación por el href="#"

    const salaId = this.dataset.salaid; // ejemplo: "4_5"
    const miId = parseInt(this.dataset.miid); // tu ID

    const [id1, id2] = salaId.split("_").map(Number);
    const idRival = (id1 === miId) ? id2 : id1;

    window.location.href = "/spring/batalla-websocket/" + idRival;
});
}
});
