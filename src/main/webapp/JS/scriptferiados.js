function formatDateBR(date) {
    const d = String(date.getDate()).padStart(2, "0");
    const m = String(date.getMonth() + 1).padStart(2, "0");
    const y = date.getFullYear();
    return `${d}/${m}/${y}`;
}

function parseDateBR(str) {
    const [d, m, y] = str.split("/");
    return new Date(y, m - 1, d);
}

const hoje = new Date();
const diaSemana = hoje.getDay(); // 0 = dom, 1 = seg ... 6 = sáb
const domingo = new Date(hoje);
domingo.setDate(hoje.getDate() - diaSemana);

const diasTabela = ["Segunda", "Terça", "Quarta", "Quinta", "Sexta"];
const ths = Array.from(document.querySelectorAll("th")).filter(th => diasTabela.includes(th.innerText));

ths.forEach((th, i) => {
    const data = new Date(domingo);
    data.setDate(domingo.getDate() + 1 + i); // +1 porque segunda = domingo + 1
    th.dataset.date = formatDateBR(data);
});

const contextPath = window.location.pathname.split("/")[1];
// Buscar feriados - criou uma api para ser chamada no js
fetch(`/${contextPath}/api/feriados`)
    .then(res => res.json())
    .then(f => {
        console.log(f);
        const feriados = f.feriados;

        // somente feriados da semana de acordo com as datas da semana
        const mapa = {};
        feriados.forEach(f => {
            const data = parseDateBR(f.data);
            const inicioSemana = parseDateBR(ths[0].dataset.date); // agora 0 = segunda
            const fimSemana = parseDateBR(ths[ths.length - 1].dataset.date); // sexta
            if (data >= inicioSemana && data <= fimSemana) {
                mapa[f.data] = f.nome;
            }
        });

        // aplica o style nos dias da semana (th) da tabela
        ths.forEach(th => {
            const data = th.dataset.date;
            if (data && mapa[data]) {
                // o elemento circular da notificacao
                const notificacao = document.createElement("span");
                notificacao.className = "holiday-notify";
                notificacao.innerText = "!";

                // criando o modal em hover com nome do feriado
                const tooltip = document.createElement("div");
                tooltip.className = "tooltip-text";
                tooltip.appendChild(document.createTextNode("Feriado,\n" + mapa[data]));
                tooltip.style.whiteSpace = "pre-line";

                notificacao.appendChild(tooltip);
                th.appendChild(notificacao);

                // conserta o erro do texto descentralizado
                // erro por causa da notificacao
                const paddingLeft = parseInt(window.getComputedStyle(th).paddingLeft);
                th.style.paddingLeft = (paddingLeft + 4) + "px";
            }
        });

    })
    .catch(err => console.log("Erro:", err));