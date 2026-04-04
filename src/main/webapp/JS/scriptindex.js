
// INTERAÇÕES VISUAIS DA TELA


// Toggle do Menu Hambúrguer
function toggleMenu() {
    document.getElementById('menu-opcoes-usuario').classList.toggle('active');
}

// Fechar menu ou modal ao clicar fora
window.onclick = function(event) {
    if (!event.target.closest('.user-menu') && !event.target.closest('.dropdown')) {
        const menu = document.getElementById('menu-opcoes-usuario');
        if (menu) menu.classList.remove('active');
    }
    const modal = document.getElementById('modal-falta-background');
    if (event.target === modal) fecharModalFalta();
}

// Abre/Fecha o card sanfona da disciplina
function alternarDetalhesCard(idDoCard) {
    const card = document.getElementById(idDoCard);
    if(card) card.classList.toggle('expanded');
}

// Abre Modal de Falta
function abrirModalFalta() {
    document.getElementById('modal-falta-background').classList.add('active');
    document.getElementById('menu-opcoes-usuario').classList.remove('active');
}

// Fecha Modal de Falta
function fecharModalFalta() {
    document.getElementById('modal-falta-background').classList.remove('active');
}

// Contador do Modal de Falta (Botões + e -)
let numAulas = 2;
function alterarAulas(valor) {
    numAulas += valor;
    if(numAulas < 1) numAulas = 1;
    if(numAulas > 10) numAulas = 10;

    // Atualiza visualmente pro usuário
    document.getElementById('texto-quantidade-aulas').innerText = numAulas;

    // Atualiza o Input Hidden que vai pro formulário do Java
    document.getElementById('input-hidden-aulas').value = numAulas;
}

// OBS: A função "enviarFalta(event)" com fetch foi removida. 
// O próprio HTML <form action="Servlet"> vai recarregar a página e mandar os dados.