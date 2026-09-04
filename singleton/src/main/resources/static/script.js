/* =====================================
   ELEMENTOS
===================================== */
const $ = (id) => document.getElementById(id);
const $$ = (selector) => document.querySelectorAll(selector);

const btnNormal = $("btnNormal"), btnIdoso = $("btnIdoso"), btnVip = $("btnVip");
const modal = $("modal"), ticketNumber = $("ticketNumber"), ticketType = $("ticketType");
const closeModal = $("closeModal"), finishButton = $("finishButton");
const clock = $("clock"), dateElement = $("date");

const navTabs = $$(".nav-tab"), tabContents = $$(".tab-content");
const historyList = $("historyList"), historyCount = $("historyCount");
const currentTicket = $("currentTicket"), currentTicketType = $("currentTicketType");

/* =====================================
   ANIMAÇÃO INICIAL
===================================== */
const introAnimation = gsap.timeline({ defaults: { ease: "power3.out" } });
introAnimation
    .from(".header", { opacity: 0, y: -18, duration: 0.65 })
    .from(".eyebrow", { opacity: 0, x: -18, duration: 0.4 }, "-=0.25")
    .from(".welcome h1", { opacity: 0, y: 32, duration: 0.75 }, "-=0.2")
    .from(".welcome > .welcome-content > p", { opacity: 0, y: 18, duration: 0.55 }, "-=0.4")
    .from(".service-area", { opacity: 0, x: 35, duration: 0.75 }, "-=0.7")
    .from(".service-card", { opacity: 0, y: 18, stagger: 0.12, duration: 0.45 }, "-=0.4")
    .from(".footer", { opacity: 0, duration: 0.4 }, "-=0.2");

/* =====================================
   HOVER DOS CARDS
===================================== */
$$(".service-card").forEach(card => {
    card.addEventListener("mouseenter", () => gsap.to(card, { y: -4, duration: 0.25, ease: "power2.out" }));
    card.addEventListener("mouseleave", () => gsap.to(card, { y: 0, duration: 0.3, ease: "power2.out" }));
});

/* =====================================
   GERAR SENHA
===================================== */
async function generateTicket(type) {
    const types = { normal: "N", idoso: "I", vip: "V" };
    const tipo = types[type];

    try {
        const response = await fetch(`/api/senhas/${tipo}`, {
            method: "POST"
        });

        if (!response.ok) {
            throw new Error("Erro ao gerar senha.");
        }

        const senha = await response.json();

        const labels = {
            N: { text: "Atendimento Normal", bg: "#dceff5", color: "#174f68" },
            I: { text: "Atendimento Idoso", bg: "#dcf3ef", color: "#18a89d" },
            V: { text: "Atendimento VIP", bg: "#dcf3ef", color: "#18a89d" }
        };

        const currentLabel = labels[tipo] || labels.N;
        ticketNumber.textContent = senha.senha;
        ticketType.textContent = currentLabel.text;
        ticketType.style.background = currentLabel.bg;
        ticketType.style.color = currentLabel.color;

        showModal();
        await updateHistory();

    } catch (error) {
        console.error(error);
        alert("Não foi possível gerar a senha. Verifique a rota do servidor.");
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
            historyList.innerHTML = `<div class="history-empty">Nenhuma senha foi gerada ainda.</div>`;
            currentTicket.textContent = "---";
            currentTicketType.textContent = "Nenhuma senha gerada";
            historyCount.textContent = "0 senhas";
            return;
        }

        const latest = ticketHistory[ticketHistory.length - 1];
        currentTicket.textContent = latest.senha;
        const tipoLatest = latest.senha.charAt(0);
        
        const labelMap = { N: "Atendimento Normal", I: "Atendimento Idoso", V: "Atendimento VIP" };
        currentTicketType.textContent = labelMap[tipoLatest] || "";
        historyCount.textContent = `${ticketHistory.length} ${ticketHistory.length === 1 ? "senha" : "senhas"}`;

        ticketHistory.slice().reverse().slice(0, 5).forEach(senha => {
            const item = document.createElement("div");
            item.classList.add("history-item");
            const t = senha.senha.charAt(0);
            item.innerHTML = `
                <span class="history-number">${senha.senha}</span>
                <div class="history-info">
                    <strong>${labelMap[t]}</strong>
                    <span>Senha registrada</span>
                </div>
            `;
            historyList.appendChild(item);
        });

        const firstItem = historyList.querySelector(".history-item:first-child");
        if (firstItem) gsap.from(firstItem, { opacity: 0, x: 20, duration: 0.35, ease: "power2.out" });
        gsap.fromTo(currentTicket, { opacity: 0, scale: 0.85 }, { opacity: 1, scale: 1, duration: 0.4, ease: "back.out(1.5)" });

    } catch (error) {
        console.error(error);
    }
}

/* =====================================
   MODAL
===================================== */
function showModal() {
    modal.style.visibility = "visible";
    modal.setAttribute("aria-hidden", "false");
    gsap.to(modal, { opacity: 1, duration: 0.25 });
    gsap.fromTo(".ticket-modal", { opacity: 0, y: 28, scale: 0.96 }, { opacity: 1, y: 0, scale: 1, duration: 0.45, ease: "power3.out" });
    gsap.fromTo(".modal-icon", { scale: 0.6, opacity: 0 }, { scale: 1, opacity: 1, duration: 0.45, delay: 0.08, ease: "back.out(1.6)" });
    gsap.fromTo(".ticket-number", { opacity: 0, y: 15 }, { opacity: 1, y: 0, duration: 0.4, delay: 0.12 });
}

function hideModal() {
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
   EVENTOS DE FECHAMENTO GARANTIDOS
===================================== */
if (closeModal) closeModal.addEventListener("click", hideModal);
if (finishButton) finishButton.addEventListener("click", hideModal);

if (modal) {
    modal.addEventListener("click", event => {
        if (event.target === modal) hideModal();
    });
}

document.addEventListener("keydown", event => {
    if (event.key === "Escape" && modal && modal.style.visibility === "visible") {
        hideModal();
    }
});

/* =====================================
   TROCA DE ABAS
===================================== */
navTabs.forEach(tab => {
    tab.addEventListener("click", () => {
        hideModal();
        const target = tab.dataset.tab;
        navTabs.forEach(item => item.classList.remove("active"));
        tabContents.forEach(content => content.classList.remove("active"));
        tab.classList.add("active");
        
        const selectedTab = $(target);
        selectedTab.classList.add("active");
        gsap.fromTo(selectedTab, { opacity: 0, y: 12 }, { opacity: 1, y: 0, duration: 0.4, ease: "power2.out" });
    });
});

/* =====================================
   EVENTOS DOS BOTÕES
===================================== */
[["btnNormal", "normal"], ["btnIdoso", "idoso"], ["btnVip", "vip"]].forEach(([id, type]) => {
    $(id).addEventListener("click", () => generateTicket(type));
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