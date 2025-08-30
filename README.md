# 🎴 Controle Cacheta (Android)

App Android para gerenciar partidas de **cacheta**: abrir/encerrar partidas por mesa, cronômetro em tempo real, cobrança automática por hora e **relatórios** diários (estabelecimento) e individuais (jogadores).  
Funciona **offline** com armazenamento local (**Room/SQLite**).

---

## ✨ Principais Funcionalidades

### Mesas
- Criar e **remover mesas** (remoção apenas se **Livre** e **sem histórico**).
- Status em tempo real: **Livre** / **Ocupada**.
- Cronômetro visível quando ocupada e **alerta** a cada nova hora completa.
- Exibição do **valor acumulado** da mesa durante a partida.

### Partidas
- Início: informar os **4 jogadores** (cria jogadores automaticamente se não existirem).
- Encerramento: registra **hora final**, **duração** e **valor**.
- Cobrança: arredondamento **para cima por hora** (mínimo 1h) × **preço/hora** × 4.

### Relatórios
- **Estabelecimento (diário)**
  - Data
  - **Mesas ocupadas** no dia
  - **Total arrecadado** (geral e por mesa)
- **Jogadores (diário)**
  - Lista das partidas do dia (Mesa, intervalo de horas, **duração**)
  - **Total a pagar** (valor por jogador)
  - Exibe **apenas** jogadores que jogaram no dia

### Experiência de Uso
- Navegação simples: Tela inicial → **Mesas** | **Relatórios**
- **Portrait only** (somente vertical)
- Diálogos para iniciar/encerrar partidas e remover mesa

---

## 🧰 Tecnologias
- **Android (Java)**
- **Room/SQLite**
- RecyclerView, Material Components, AlertDialog
- Handler/Looper (cronômetro em tempo real)

---

## ▶️ Como executar
1. Clonar o repositório
   ```bash
   git clone https://github.com/Gabrieldsrod/controle-cacheta.git
   cd controle-cacheta 

2. Abra o projeto no **Android Studio**.
3. Conecte um dispositivo/emulador Android (API 26+).
4. Clique em **Run ▶️**.

**Gerar APK:**  
`Build > Build Bundle(s) / APK(s) > Build APK(s)`  
(Use *Generate Signed Bundle / APK* para versão de produção.)

---

## 📦 Persistência (visão geral)
- **Table**: número da mesa (PK), status, horário de início (quando ocupada).
- **Game**: mesa, início/fim, duração (min), valor total.
- **Player**: id e nome.
- **GamePlayer**: relação N:N entre partida e jogadores.

> Dado histórico é preservado; vínculos de partida e jogadores são mantidos em tabela de junção.

---

## 🔒 Regras Importantes
- Valor/hora **fixo** R$10,00 (pode ser alterado na programação em MatchService).
- Remover mesa **apenas** quando **Livre** e sem partidas registradas.
- Valor por jogador = **valor total / 4** (distribuição igual).
- Relatórios diários consideram a **data atual** do dispositivo.

---

## 🗺️ Próximos passos (sugestões)
- Preferências para **preço/hora** configurável.
- Destaque do **jogador com mais tempo** no dia.
- Migrações do Room e testes instrumentados.

---

## 👤 Autor
**@Gabrieldsrod**

---

## 📜 Licença
Uso pessoal/estudo. Para uso comercial, contate o autor.ncies).
