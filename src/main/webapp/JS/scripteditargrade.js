let disciplinasSelecionadas = new Set();

function getCard(codigo) {
    return document.getElementById(`card-disciplina-${codigo}`);
}

function toggleSelecao(codigo) {
    const card = getCard(codigo);
    if (!card) return;

    const selecionado = disciplinasSelecionadas.has(codigo);

    if (selecionado) {
        disciplinasSelecionadas.delete(codigo);
        card.classList.remove("selecionado");
    } else {
        disciplinasSelecionadas.add(codigo);
        card.classList.add("selecionado");
    }
}

function descartarAlteracoes() {
    disciplinasSelecionadas.forEach(codigo => {
        const card = getCard(codigo);
        if (card) card.classList.remove("selecionado");
    });

    disciplinasSelecionadas.clear();
    alert("Alterações descartadas!");
}

function salvarAlteracoes() {
    if (disciplinasSelecionadas.size === 0) {
        alert("Nenhuma disciplina selecionada.");
        return;
    }

    abrirModalConfirmacao();
}

function abrirModalConfirmacao() {
    const modal = document.getElementById("modal-remover-background");
    const lista = document.getElementById("modal-remover-lista");

    modal.style.display = "flex";
    lista.innerHTML = "";

    disciplinasSelecionadas.forEach(codigo => {
        const card = getCard(codigo);
        if (!card) return;

        const nome = card.querySelector("span")?.innerText || "";
        const codigoTexto = card.querySelector("h4")?.innerText || "";

        const item = document.createElement("div");
        item.textContent = `• ${codigoTexto} - ${nome}`;

        lista.appendChild(item);
    });
}

function fecharModalRemover() {
    document.getElementById("modal-remover-background").style.display = "none";
}

function confirmarRemocao() {
    const form = document.getElementById("form-remover");

    form.querySelectorAll('input[name="codigos"]').forEach(e => e.remove());

    disciplinasSelecionadas.forEach(codigo => {
        const input = document.createElement("input");
        input.type = "hidden";
        input.name = "codigos";
        input.value = codigo;
        form.appendChild(input);
    });

    form.submit();
}

document.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll(".course-card").forEach(card => {
        const codigo = card.id.replace("card-disciplina-", "");
        card.addEventListener("click", () => toggleSelecao(codigo));
    });
});

// abrir menus hamburguer
function toggleMenu() {
    document.getElementById('menu-opcoes-usuario').classList.toggle('active');
}