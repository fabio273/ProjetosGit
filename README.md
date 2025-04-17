#  Jogo Frick Frack  


## Autor: Fábio Emilson Tavares Rodrigues  

## Tecnologia: java Swing



**FrickFrack** é um jogo de quebra-cabeça para dois jogadores, chamados "X" e "O", que se revezam marcando os espaços em uma grade 3 × 3. Com objetivo do jogador que conseguir colocar as três respectivas marcas em uma linha horizontal, vertical ou diagonal, ganha o jogo desenvolvida em Java com interface gráfica utilizando Swing. Com o modo de **arrastar peças** disponível no modo de jogo entre dois jogadores humanos, adicionando uma nova camada de estratégia ao jogo tradicional.

##  Modos de Jogo

Ao iniciar o jogo, o usuário pode escolher entre dois modos:

- **Jogador vs Computador**: O jogador humano joga contra um oponente controlado por computador.
- **Jogador vs Jogador**: Dois jogadores humanos se revezam no mesmo dispositivo.

>  Nota: O modo de arrastar peças só está disponível no modo **Jogador vs Jogador**.

---

##  Regras do Jogo

### Fase 1: Colocação de Peças

- Cada jogador (X ou O) faz até 3 jogadas.
- As peças são colocadas em casas vazias do tabuleiro 3x3.
- Após 6 jogadas (3 para cada), entra-se no **modo de arrasto**.

### Fase 2: Arrastar Peças 

- Jogadores podem **arrastar uma de suas próprias peças para uma casa vazia**.
- Cada jogador tem no máximo **6 arrastos disponíveis**.
- O jogo termina quando:
  - Um jogador forma uma linha com 3 peças (horizontal, vertical ou diagonal).
  - Empate apos atingir limites de 12 arrastos (6 para cada lado).
  - Ambos os jogadores esgotam seus 6 arrastos sem vitória (empate por limite de arrastos).

---

##  Interface

- O tabuleiro é composto por 9 botões em uma grade 3x3.
- Cada botão exibe "X" ou "O" conforme a jogada.
- As cores são:
  - **X** em **azul**
  - **O** em **vermelho**
- Uma mensagem na parte inferior indica o turno atual ou o resultado final.

---

##  Funcionalidades

- **Reiniciar Jogo**: Botão para resetar o tabuleiro, jogadas e arrastos.
- **Detecção de vitória**: Checa todas as combinações possíveis.
- **Validação de jogadas e arrastos**: Apenas peças do jogador atual podem ser movidas, e apenas para casas vazias.

---

##  Estrutura do Código

- `FrickFrack`: Classe principal com lógica completa do jogo.
- Componentes Swing usados:
  - `JFrame`, `JButton`, `JLabel`, `JPanel`, `MouseAdapter`, `ActionListener`.
- Organização por métodos:
  - `criarMenuInicial()`: Tela inicial com escolha de modo.
  - `iniciarJogo()`: Monta o tabuleiro e interface de jogo.
  - `clique()`: Trata jogadas normais.
  - `realizarArrasto()`: Move uma peça de um lugar para outro.
  - `verificarVitoria()`: Checa se algum jogador venceu.
  - `resetarJogo()`: Limpa o tabuleiro e reinicia variáveis.

---
