// Menu Hambúrguer
function toggleMenu() {
    document.getElementById('menu-opcoes-usuario').classList.toggle('active');
}

// Fechar menu ou modal ao clicar fora
window.onclick = function(event) {
    if (!event.target.closest('.user-menu') && !event.target.closest('.dropdown')) {
        const menu = document.getElementById('menu-opcoes-usuario');
        if (menu) menu.classList.remove('active');
    }
    const modalAdicionar = document.getElementById('modal-adicionar-background');
    if (event.target === modalAdicionar) fecharModalAdicionar();
}

// Abre Modal de Adicionar
function abrirModalAdicionar(codigo, nome) {
    document.getElementById('modal-codigo-disciplina').value=codigo;
    document.getElementById('modal-nome-disciplina').value=nome;

    document.getElementById('modal-adicionar-background').classList.add('active');
    document.getElementById('menu-opcoes-usuario').classList.remove('active');
}

// Fecha Modal de Adicionar
function fecharModalAdicionar() {
    document.getElementById('modal-adicionar-background').classList.remove('active');
}

// Contador do Modal de Falta (Botões + e -)
let numAulas = 2;
function alterarAulasAdicionar(valor) {
    numAulas += valor;
    if(numAulas < 2) numAulas = 2;
    if(numAulas > 4) numAulas = 4;

    // Atualiza visualmente pro usuário
    document.getElementById('texto-quantidade-adicionar').innerText = numAulas;

    // Atualiza o Input Hidden que vai pro formulário do Java
    document.getElementById('input-hidden-adicionar').value = numAulas;
}