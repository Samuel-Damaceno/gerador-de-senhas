/* =====================================
   ELEMENTOS
===================================== */

const btnNormal = document.getElementById("btnNormal");
const btnPreferencial = document.getElementById("btnPreferencial");
const btnVip = document.getElementById("btnVip"); // Novo botão VIP

const modal = document.getElementById("modal");
const ticketNumber = document.getElementById("ticketNumber");
const ticketType = document.getElementById("ticketType");
const closeModal = document.getElementById("closeModal");
const finishButton = document.getElementById("finishButton");
const clock = document.getElementById("clock");
const dateElement = document.getElementById("date");

/* =====================================
   ELEMENTOS DAS ABAS
===================================== */

const navTabs = document.querySelectorAll(".nav-tab");
const tabContents = document.querySelectorAll(".tab-content");

/* =====================================
   ELEMENTOS DO HISTÓRICO
===================================== */

const historyList = document.getElementById("historyList");
const historyCount = document.getElementById("historyCount");
const currentTicket = document.getElementById("currentTicket");
const currentTicketType = document.getElementById("currentTicketType");

/* =====================================
   ANIMAÇÃO INICIAL
===================================== */

const introAnimation = gsap.timeline({
    defaults: { ease: "power3.out" }
});

introAnimation
    .from(".header", { opacity: 0, y: -18, duration: 0.65 })
    .from(".eyebrow", { opacity: 0, x: -18, duration: 0.4 }, "-=0.25")
    .from(".welcome h1", { opacity: 0, y: 32, duration: 0.75 }, "-=0.2")
    .from(".service-area", { opacity: 0, x: 35, duration: 0.75 }, "-=0.7")
    .from(".service-card", { opacity: 0, y: 18, stagger: 0.12, duration: 0.45 }, "-=0.4")
    .from(".footer", { opacity: 0, duration: 0.4 }, "-=0.2");

/* =====================================
   HOVER DOS CARDS
===================================== */

document.querySelectorAll(".service-card").forEach(card => {
    card.addEventListener("mouseenter", () => {
        gsap.to(card, { y: -4, duration: 0.25, ease: "power2.out" });
    });

    card.addEventListener("mouseleave", () => {
        gsap.to(card, { y: 0, duration: 0.3, ease: "power2.out" });
    });
});

/* =====================================
   GERAR SENHA
===================================== */

async function generateTicket(type) {
    let tipo;

    if (type === "normal") {
        tipo = "N";
    } else if (type === "preferencial") {
        tipo = "P";
    } else if (type === "vip") {
        tipo = "V"; // Letra enviada ao backend para VIP
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
        } else if (tipo === "P") {
            label = "Atendimento Preferencial";
            ticketType.style.background = "#dcf3ef";
            ticketType.style.color = "#18a89d";
        } else {
            label = "Atendimento VIP";
            ticketType.style.background = "#faebd7"; // Cor personalizada para VIP (exemplo)
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

        if (!response.ok) {
            throw new Error("Erro ao buscar histórico.");
        }

        const ticketHistory = await response.json();
        historyList.innerHTML = "";

        if (ticketHistory.length === 0) {
            historyList.innerHTML = `
                <div class="history-empty">
                    Nenhuma senha foi gerada ainda.
                </div>
            `;
            currentTicket.textContent = "---";
            currentTicketType.textContent = "Nenhuma senha gerada";
            historyCount.textContent = "0 senhas";
            return;
        }

        const latest = ticketHistory[ticketHistory.length - 1];
        currentTicket.textContent = latest.senha;
        
        // Define o texto correto conforme o tipo da última senha
        if (latest.tipo === "N") {
            currentTicketType.textContent = "Atendimento Normal";
        } else if (latest.tipo === "P") {
            currentTicketType.textContent = "Atendimento Preferencial";
        } else {
            currentTicketType.textContent = "Atendimento VIP";
        }

        historyCount.textContent = `${ticketHistory.length} ${
            ticketHistory.length === 1 ? "senha" : "senhas"
        }`;

        const latestTickets = ticketHistory.slice().reverse().slice(0, 5);

        latestTickets.forEach(senha => {
            const item = document.createElement("div");
            item.classList.add("history-item");

            let label;
            if (senha.tipo === "N") {
                label = "Atendimento Normal";
            } else if (senha.tipo === "P") {
                label = "Atendimento Preferencial";
            } else {
                label = "Atendimento VIP";
            }

            item.innerHTML = `
                <span class="history-number">
                    ${senha.senha}
                </span>
                <div class="history-info">
                    <strong>${label}</strong>
                    <span>Senha registrada</span>
                </div>
            `;

            historyList.appendChild(item);
        });

        const firstItem = historyList.querySelector(".history-item:first-child");
        if (firstItem) {
            gsap.from(firstItem, {
                opacity: 0,
                x: 20,
                duration: 0.35,
                ease: "power2.out"
            });
        }

        gsap.fromTo(
            currentTicket,
            { opacity: 0, scale: 0.85 },
            { opacity: 1, scale: 1, duration: 0.4, ease: "back.out(1.5)" }
        );

    } catch (error) {
        console.error(error);
    }
}

/* =====================================
   ABRIR E FECHAR MODAL
===================================== */

function showModal() {
    modal.style.visibility = "visible";
    modal.setAttribute("aria-hidden", "false");

    gsap.to(modal, { opacity: 1, duration: 0.25 });
    gsap.fromTo(
        ".ticket-modal",
        { opacity: 0, y: 28, scale: 0.96 },
        { opacity: 1, y: 0, scale: 1, duration: 0.45, ease: "power3.out" }
    );
    gsap.fromTo(
        ".modal-icon",
        { scale: 0.6, opacity: 0 },
        { scale: 1, opacity: 1, duration: 0.45, delay: 0.08, ease: "back.out(1.6)" }
    );
    gsap.fromTo(
        ".ticket-number",
        { opacity: 0, y: 15 },
        { opacity: 1, y: 0, duration: 0.4, delay: 0.12 }
    );
}

function hideModal() {
    gsap.to(".ticket-modal", {
        opacity: 0,
        y: 18,
        scale: 0.97,
        duration: 0.22
    });

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
   TROCA DE ABAS
===================================== */

navTabs.forEach(tab => {
    tab.addEventListener("click", () => {
        const target = tab.dataset.tab;

        navTabs.forEach(item => item.classList.remove("active"));
        tabContents.forEach(content => content.classList.remove("active"));

        tab.classList.add("active");
        const selectedTab = document.getElementById(target);
        selectedTab.classList.add("active");

        gsap.fromTo(
            selectedTab,
            { opacity: 0, y: 12 },
            { opacity: 1, y: 0, duration: 0.4, ease: "power2.out" }
        );
    });
});

/* =====================================
   EVENTOS DOS BOTÕES
===================================== */

btnNormal.addEventListener("click", () => {
    generateTicket("normal");
});

btnPreferencial.addEventListener("click", () => {
    generateTicket("preferencial");
});

// Evento do novo botão VIP
btnVip.addEventListener("click", () => {
    generateTicket("vip");
});

closeModal.addEventListener("click", hideModal);
finishButton.addEventListener("click", hideModal);

modal.addEventListener("click", event => {
    if (event.target === modal) {
        hideModal();
    }
});

document.addEventListener("keydown", event => {
    if (event.key === "Escape" && modal.style.visibility === "visible") {
        hideModal();
    }
});

/* =====================================
   DATA E HORÁRIO
===================================== */

function updateDateTime() {
    const now = new Date();

    clock.textContent = now.toLocaleTimeString(
        "pt-BR",
        { hour: "2-digit", minute: "2-digit" }
    );

    dateElement.textContent = now.toLocaleDateString(
        "pt-BR",
        { day: "2-digit", month: "short", year: "numeric" }
    );
}

/* =====================================
   INICIALIZAÇÃO
===================================== */

updateDateTime();
updateHistory();

setInterval(updateDateTime, 1000);