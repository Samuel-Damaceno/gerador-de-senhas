/* =====================================
   ELEMENTOS
===================================== */

const btnNormal = document.getElementById("btnNormal");
const btnIdoso = document.getElementById("btnIdoso");
const btnVip = document.getElementById("btnVip");

const modal = document.getElementById("modal");
const ticketNumber = document.getElementById("ticketNumber");
const ticketType = document.getElementById("ticketType");
const closeModal = document.getElementById("closeModal");
const finishButton = document.getElementById("finishButton");
const clock = document.getElementById("clock");
const dateElement = document.getElementById("date");

/* =====================================
   ELEMENTOS DAS ABAS E HISTÓRICO
===================================== */

const navTabs = document.querySelectorAll(".nav-tab");
const tabContents = document.querySelectorAll(".tab-content");
const historyList = document.getElementById("historyList");
const historyCount = document.getElementById("historyCount");
const currentTicket = document.getElementById("currentTicket");
const currentTicketType = document.getElementById("currentTicketType");

/* =====================================
   GERAR SENHA
===================================== */

async function generateTicket(type) {
    let tipo;

    if (type === "normal") {
        tipo = "N";
    } else if (type === "idoso") {
        tipo = "I"; // Altere para "P" se o seu backend esperar a letra P
    } else if (type === "vip") {
        tipo = "V";
    }

    try {
        const response = await fetch(`/api/senhas/${tipo}`, {
            method: "POST"
        });

        if (!response.ok) {
            throw new Error("Erro ao gerar senha.");
        }

        const senha = await response.json();
        let label;

        if (tipo === "N") {
            label = "Atendimento Normal";
            ticketType.style.background = "#dceff5";
            ticketType.style.color = "#174f68";
        } else if (tipo === "I" || tipo === "P") {
            label = "Atendimento Idoso";
            ticketType.style.background = "#dcf3ef";
            ticketType.style.color = "#18a89d";
        } else {
            label = "Atendimento VIP";
            ticketType.style.background = "#faebd7";
            ticketType.style.color = "#b8860b";
        }

        ticketNumber.textContent = senha.senha;
        ticketType.textContent = label;

        showModal();
        await updateHistory();

    } catch (error) {
        console.error(error);
        alert("Não foi possível gerar a senha.");
    }
}

/* =====================================
   ATUALIZAR HISTÓRICO
===================================== */

async function updateHistory() {
    try {
        const response = await fetch("/api/senhas/historico");
        if (!response.ok) throw new Error("Erro ao buscar histórico.");

        const ticketHistory = await response.json();
        historyList.innerHTML = "";

        if (ticketHistory.length === 0) {
            historyList.innerHTML = `<div class="history-empty">Nenhuma senha gerada ainda.</div>`;
            currentTicket.textContent = "---";
            currentTicketType.textContent = "Nenhuma senha gerada";
            historyCount.textContent = "0 senhas";
            return;
        }

        const latest = ticketHistory[ticketHistory.length - 1];
        currentTicket.textContent = latest.senha;
        
        if (latest.tipo === "N") {
            currentTicketType.textContent = "Atendimento Normal";
        } else if (latest.tipo === "I" || latest.tipo === "P") {
            currentTicketType.textContent = "Atendimento Idoso";
        } else {
            currentTicketType.textContent = "Atendimento VIP";
        }

        historyCount.textContent = `${ticketHistory.length} ${ticketHistory.length === 1 ? "senha" : "senhas"}`;

        ticketHistory.slice().reverse().slice(0, 5).forEach(senha => {
            const item = document.createElement("div");
            item.classList.add("history-item");

            let label = senha.tipo === "N" ? "Atendimento Normal" : (senha.tipo === "I" || senha.tipo === "P" ? "Atendimento Idoso" : "Atendimento VIP");

            item.innerHTML = `
                <span class="history-number">${senha.senha}</span>
                <div class="history-info">
                    <strong>${label}</strong>
                    <span>Senha registrada</span>
                </div>
            `;
            historyList.appendChild(item);
        });

    } catch (error) {
        console.error(error);
    }
}

/* =====================================
   MODAL (COM FECHAMENTO AUTOMÁTICO)
===================================== */

let autoCloseTimer = null;

function showModal() {
    modal.style.visibility = "visible";
    modal.setAttribute("aria-hidden", "false");

    gsap.to(modal, { opacity: 1, duration: 0.25 });
    gsap.fromTo(".ticket-modal", { opacity: 0, y: 28, scale: 0.96 }, { opacity: 1, y: 0, scale: 1, duration: 0.45, ease: "power3.out" });

    // Fecha a janela sozinho após 4 segundos para não prender a tela
    if (autoCloseTimer) clearTimeout(autoCloseTimer);
    autoCloseTimer = setTimeout(() => {
        hideModal();
    }, 4000);
}

function hideModal() {
    if (autoCloseTimer) clearTimeout(autoCloseTimer);
    gsap.to(".ticket-modal", { opacity: 0, y: 18, scale: 0.97, duration: 0.22 });
    gsap.to(modal, {
        opacity: 0,
        duration: 0.25,
        delay: 0.03,
        onComplete: () => {
            modal.style.visibility = "hidden";
            modal.setAttribute("aria-hidden", "true");
        }
    });
}

/* =====================================
   EVENTOS DOS BOTÕES
===================================== */

btnNormal.addEventListener("click", () => generateTicket("normal"));
btnIdoso.addEventListener("click", () => generateTicket("idoso"));
btnVip.addEventListener("click", () => generateTicket("vip"));

closeModal.addEventListener("click", hideModal);
finishButton.addEventListener("click", hideModal);

modal.addEventListener("click", event => {
    if (event.target === modal) hideModal();
});

document.addEventListener("keydown", event => {
    if (event.key === "Escape" && modal.style.visibility === "visible") hideModal();
});

/* =====================================
   DATA, HORA E INICIALIZAÇÃO
===================================== */

function updateDateTime() {
    const now = new Date();
    clock.textContent = now.toLocaleTimeString("pt-BR", { hour: "2-digit", minute: "2-digit" });
    dateElement.textContent = now.toLocaleDateString("pt-BR", { day: "2-digit", month: "short", year: "numeric" });
}

updateDateTime();
updateHistory();
setInterval(updateDateTime, 1000);