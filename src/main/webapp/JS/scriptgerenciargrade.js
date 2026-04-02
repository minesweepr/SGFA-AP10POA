// ==========================================
// INTEGRAÇÃO COM O BACKEND JAVA (API)
// ==========================================

async function carregarDadosParaEdicao() {
    try {
    
        // Alterar a rota de acordo com a API
        // const resposta = await fetch('/SGFA-AP10POA/api/materias-disponiveis');
        // const dados = await resposta.json();
        
        // Simulação do Java para visualização do HTML
        const dados = {
            nome: "Esther",
            materiasDisponiveis: [
                { id: 101, nome: "4 Programação Orientada a Objetos", horario: "Terça 8:50 - 10:20", codigo: "POA12", carga: "80h" },
                { id: 102, nome: "4 Segurança da Informação", horario: "Segunda 7:10 - 8:50", codigo: "SEG14", carga: "80h" },
                { id: 103, nome: "4 Banco de Dados", horario: "Quinta 10:40 - 12:20", codigo: "UBD10", carga: "80h" },
                { id: 104, nome: "4 Estrutura de Dados", horario: "Quarta 8:50 - 11:30", codigo: "EST05", carga: "120h" }
            ],
            // Matérias que ela já selecionou
            materiasAtuais: [] 
        };

        document.getElementById('nome-usuario-logado').innerText = `Olá, ${dados.nome}!`;
        
        renderizarListaDeMaterias(dados.materiasDisponiveis);
        // Aqui também viria a chamada para `renderizarTabelaHorarios` igual fizemos na index

    } catch (error) {
        console.error("Erro ao buscar dados:", error);
    }
}

// RENDERIZAÇÃO DA TELA


function renderizarListaDeMaterias(lista) {
    const container = document.getElementById('container-materias-disponiveis');
    let htmlGerado = '';

    lista.forEach(materia => {
        htmlGerado += `
        <div class="linha-materia" id="linha-materia-${materia.id}">
            <span class="col-nome">${materia.nome}</span>
            <span class="col-horario">${materia.horario}</span>
            <span class="col-codigo">${materia.codigo}</span>
            <span class="col-carga">${materia.carga}</span>
            <button class="btn-selecionar" id="btn-sel-${materia.id}" onclick="selecionarMateria(${materia.id})">Selecionar</button>
        </div>
        `;
    });

    container.innerHTML = htmlGerado;
}

// Função simulada de clique no botão "Selecionar"
let qtdSelecionadas = 0;
document.getElementById('contador-disciplinas-selecionadas').innerText = qtdSelecionadas;

function selecionarMateria(id) {
    const btn = document.getElementById(`btn-sel-${id}`);
    
    if (btn.innerText === "Selecionar") {
        btn.innerText = "Remover";
        btn.classList.add("selecionado");
        btn.style.backgroundColor = "#c82333"; // Fica vermelho para dar opção de remover
        qtdSelecionadas++;
    } else {
        btn.innerText = "Selecionar";
        btn.classList.remove("selecionado");
        btn.style.backgroundColor = "var(--success-green)"; // Volta pra verde
        qtdSelecionadas--;
    }
    
    document.getElementById('contador-disciplinas-selecionadas').innerText = qtdSelecionadas;
}

function salvarNovaGrade() {
    alert("Dados enviados para o Java! A grade foi salva com sucesso.");
    window.location.href = "index.html"; // Redireciona de volta para a Home
}

// Inicia assim que a página carrega
document.addEventListener('DOMContentLoaded', () => {
    carregarDadosParaEdicao();
});

// Menu Hambúrguer
function toggleMenu() {
    document.getElementById('menu-opcoes-usuario').classList.toggle('active');
}