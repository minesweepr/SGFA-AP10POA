
// INTEGRAÇÃO COM O BACKEND JAVA 


// Variável global para armazenar os dados temporariamente
let dadosUsuarioGlobal = null;

// Função que busca os dados do Banco via Java (GET)
async function carregarDadosDoUsuario() {
    try {
        // A requisição real para o seu endpoint no Java
        const resposta = await fetch('/SGFA-AP10POA/api/dados-aluno');
        
        // Verifica se a resposta foi bem sucedida 
        if (!resposta.ok) {
            throw new Error(`Erro na requisição: ${resposta.status}`);
        }

        // Converte a resposta do Java para um objeto JSON que o JavaScript entende
        const dados = await resposta.json(); 

        dadosUsuarioGlobal = dados;

        // Atualiza Nome e Status
        document.getElementById('nome-usuario-logado').innerText = `Olá, ${dados.nome}!`;
        document.getElementById('status-situacao-aluno').innerText = dados.status;

        // Calcula e atualiza Estatísticas
        atualizarEstatisticas(dados.disciplinas);

        // Atualiza o Modal de Faltas
        atualizarSelectModal(dados.disciplinas);

        // Desenha as matérias e a tabela
        renderizarCards(dados.disciplinas);
        renderizarTabelaHorarios(dados.disciplinas);

    } catch (error) {
        console.error("Erro ao buscar dados do Java:", error);
        // Um alerta amigável caso o Tomcat/Servidor esteja desligado
        alert("Não foi possível carregar os dados. Verifique se o servidor Java está rodando.");
    }
}


// FUNÇÕES DE ATUALIZAÇÃO DA TELA


function atualizarEstatisticas(disciplinas) {
    document.getElementById('quantidade-total-disciplinas').innerText = disciplinas.length;
    
    // Calcula quantas matérias estão em atenção (entre 50% e 85% de faltas)
    let atencaoCount = 0;
    disciplinas.forEach(mat => {
        const porc = (mat.faltasAtuais / mat.limiteFaltas) * 100;
        if (porc >= 50 && porc < 85) atencaoCount++; 
    });
    
    document.getElementById('quantidade-disciplinas-atencao').innerText = atencaoCount;
}

function atualizarSelectModal(disciplinas) {
    const select = document.getElementById('select-disciplina-modal');
    // Limpa opções antigas e coloca o placeholder
    select.innerHTML = '<option value="">Selecione...</option>';
    
    // Preenche com as matérias do banco
    disciplinas.forEach(mat => {
        select.innerHTML += `<option value="${mat.id}">${mat.sigla} - ${mat.professor}</option>`;
    });
}

function renderizarCards(disciplinas) {
    const container = document.getElementById('container-lista-disciplinas');
    let htmlGerado = '';

    disciplinas.forEach(materia => {
        const porcentagemCalculada = (materia.faltasAtuais / materia.limiteFaltas) * 100;
        const faltasRestantes = materia.limiteFaltas - materia.faltasAtuais;

        let classeCorBarra = 'bg-green';
        let classeCorCirculo = 'color-green';

        if (porcentagemCalculada >= 85) {
            classeCorBarra = 'bg-red';
            classeCorCirculo = 'color-red';
        } else if (porcentagemCalculada >= 50) {
            classeCorBarra = 'bg-yellow';
            classeCorCirculo = 'color-yellow';
        }

        htmlGerado += `
        <div class="course-card" id="card-disciplina-${materia.id}">
            <div class="course-header" onclick="alternarDetalhesCard('card-disciplina-${materia.id}')">
                <h4 id="disciplina-sigla-${materia.id}">${materia.sigla}</h4>
                <span id="disciplina-professor-${materia.id}">${materia.professor}</span>
            </div>
            
            <div class="course-summary" id="resumo-disciplina-${materia.id}" onclick="alternarDetalhesCard('card-disciplina-${materia.id}')">
                <div class="progress-bar-container">
                    <div class="progress-bar ${classeCorBarra}" style="width: ${porcentagemCalculada}%;"></div>
                </div>
                <i class="fas fa-chevron-down toggle-icon"></i>
            </div>

            <div class="course-details" id="detalhes-disciplina-${materia.id}">
                <div class="detalhes-header">
                    <div class="progress-bar-container-large">
                        <div class="progress-bar ${classeCorBarra}" style="width: ${porcentagemCalculada}%;"></div>
                    </div>
                    <div class="circular-progress ${classeCorCirculo}">
                        <span>${porcentagemCalculada.toFixed(1)}%</span>
                    </div>
                </div>
                
                <div class="detalhes-textos">
                    <p>Faltas registradas: <span>${materia.faltasAtuais}/${materia.limiteFaltas}</span></p>
                    <p class="destaque">Você ainda pode faltar <span>${faltasRestantes}</span> vezes</p>
                </div>
                
                <hr class="divisor-card">
                
                <div class="detalhes-acoes">
                    <button class="btn-acao-card" onclick="alert('Funcionalidade de ajuste em desenvolvimento para: ${materia.sigla}')">
                        <i class="fas fa-edit"></i> Ajustar faltas
                    </button>
                </div>
            </div>
        </div>
        `;
    });

    container.innerHTML = htmlGerado;
}

function renderizarTabelaHorarios(disciplinas) {
    const corpoTabela = document.getElementById('corpo-tabela-horarios');
    const rotulosTempos = ["7:10-8:00", "8:00-8:50", "8:50-9:40", "9:50-10:40", "10:40-11:30", "11:30-12:20"];
    
    // Cria matriz vazia
    let gradeMatriz = Array(6).fill().map(() => Array(5).fill(null));

    // Preenche com os dados que vieram do Java
    disciplinas.forEach(materia => {
        materia.agenda.forEach(diaDaAgenda => {
            diaDaAgenda.tempos.forEach(tempoIndex => {
                gradeMatriz[tempoIndex][diaDaAgenda.dia] = {
                    sigla: materia.sigla,
                    cor: materia.corTabela
                };
            });
        });
    });

    // Constrói as <tr> e <td>
    let htmlTabela = '';
    gradeMatriz.forEach((linha, rowIndex) => {
        htmlTabela += `<tr>`;
        htmlTabela += `<td class="col-horario">${rotulosTempos[rowIndex]}</td>`; 

        linha.forEach(celula => {
            if (celula) {
                htmlTabela += `<td class="${celula.cor}">${celula.sigla}</td>`;
            } else {
                htmlTabela += `<td></td>`;
            }
        });

        htmlTabela += `</tr>`;
    });

    corpoTabela.innerHTML = htmlTabela;
}


// INICIALIZAÇÃO E EVENTOS DE INTERFACE


// Inicia a requisição ao Java assim que a página carrega!
document.addEventListener('DOMContentLoaded', () => {
    carregarDadosDoUsuario();
});

function toggleMenu() {
    document.getElementById('menu-opcoes-usuario').classList.toggle('active');
}

window.onclick = function(event) {
    if (!event.target.closest('.user-menu') && !event.target.closest('.dropdown')) {
        const menu = document.getElementById('menu-opcoes-usuario');
        if (menu) menu.classList.remove('active');
    }
    const modal = document.getElementById('modal-falta-background');
    if (event.target === modal) fecharModalFalta();
}

function alternarDetalhesCard(idDoCard) {
    const card = document.getElementById(idDoCard);
    if(card) card.classList.toggle('expanded');
}

function abrirModalFalta() {
    document.getElementById('modal-falta-background').classList.add('active');
    document.getElementById('menu-opcoes-usuario').classList.remove('active'); 
}

function fecharModalFalta() {
    document.getElementById('modal-falta-background').classList.remove('active');
}

let numAulas = 2;
function alterarAulas(valor) {
    numAulas += valor;
    if(numAulas < 1) numAulas = 1; 
    if(numAulas > 10) numAulas = 10; 
    document.getElementById('texto-quantidade-aulas').innerText = numAulas;
    document.getElementById('input-hidden-aulas').value = numAulas;
}


// 4. ENVIO DE DADOS (POST PARA O JAVA)


async function enviarFalta(event) {
    event.preventDefault(); 
    
    // Coleta os dados que o usuário digitou no modal
    const disciplinaId = document.getElementById('select-disciplina-modal').value;
    const dataFalta = document.getElementById('input-data-falta').value;
    const quantidadeAulas = document.getElementById('input-hidden-aulas').value;

    // Monta o objeto que o Java vai receber no backend
    const payload = {
        disciplinaId: parseInt(disciplinaId),
        data: dataFalta,
        quantidade: parseInt(quantidadeAulas)
    };

    try {
        // Envia (POST) os dados para a sua rota de salvar falta no Java
        const resposta = await fetch('/SGFA-AP10POA/api/registrar-falta', {
            method: 'POST',
            headers: { 
                'Content-Type': 'application/json' 
            },
            body: JSON.stringify(payload)
        });

        if (resposta.ok) {
            alert(`Falta registrada com sucesso!`);
            fecharModalFalta();
            
            // Recarrega a tela chamando a API do Java de novo!!!!!!
            carregarDadosDoUsuario(); 
        } else {
            alert('Falha ao registrar a falta no banco de dados.');
        }

    } catch (error) {
        console.error("Erro ao enviar falta para o servidor:", error);
        alert('Erro de conexão ao tentar salvar a falta.');
    }
}