// abrir menus e dependencias
function toggleMenu() {
    document.getElementById('menu-opcoes-usuario').classList.toggle('active');
}

// menu filtro
function toggleMenuFiltro() {
    document.getElementById('menu-opcoes-filtro').classList.toggle('active');
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

// clicar fora pra fechar unificado (pra evitar repetições ja q tem 3 em uma pagina)
window.onclick = function(event) {
    const menuUsuario=document.getElementById('menu-opcoes-usuario');
    const menuFiltro=document.getElementById('menu-opcoes-filtro');
    const modalAdicionar=document.getElementById('modal-adicionar-background');

    // fecha menu usuario
    if(!event.target.closest('.user-menu') && !event.target.closest('.dropdown')){
        if(menuUsuario) menuUsuario.classList.remove('active');
    }

    // fecha menu filtro
    const btnFiltro=document.querySelector('.btn-filtro');
    if(menuFiltro && btnFiltro && !btnFiltro.contains(event.target)
        && !menuFiltro.contains(event.target)) menuFiltro.classList.remove('active');

    // fecha menu modal esther
    if(event.target===modalAdicionar) fecharModalAdicionar();
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

// pesquisa com filtro
document.addEventListener("DOMContentLoaded", () => {
    const input=document.getElementById("input-busca-materia");
    const listaMaterias=document.querySelectorAll(".linha-materia");
    const checkboxes=document.querySelectorAll(".check-periodo");

    function pesquisar(){
        const termo=input.value.toLowerCase().trim();
        const selecionados=Array.from(checkboxes).filter(c => c.checked).map(c => c.value);

        listaMaterias.forEach(linha=>{
            const texto=linha.innerText.toLowerCase();
            const codigo=linha.querySelector(".col-codigo").textContent;
            const numPeriodo=codigo.match(/\d/)[0];
            // ^ já q a faculdade segue um padrao, é só pegar o primeiro numero do codigo

            // só aparece se bater o texto e/ou o periodo
            linha.style.display=(texto.includes(termo) &&
                (selecionados.length===0 || selecionados.includes(numPeriodo)))?"flex":"none";
        });
    }

    // pesquisas
    input.addEventListener("input", pesquisar);
    checkboxes.forEach(c => c.addEventListener("change", pesquisar));

    document.querySelector(".fa-search")?.addEventListener("click", pesquisar);
    input.addEventListener("keydown", e => e.key==="Enter" && (e.preventDefault(), pesquisar()));

    pesquisar();// inicia aplicando os filtros ja que os checkboxes começam marcados
});