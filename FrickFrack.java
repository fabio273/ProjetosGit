
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class FrickFrack {
    private JFrame frame;
    private JButton[][] botoes = new JButton[3][3];
    private String jogadorAtual = "X";
    private String[] tabuleiro = new String[9];
    private boolean jogoAtivo = true;
    private JLabel labelMensagem;
    private boolean vsComputador = true;
    private int jogadasX = 0, jogadasO = 0;
    private int arrastosX = 0, arrastosO = 0;
    private boolean modoArrastar = false;
    private Point origemSelecionada = null;

    public FrickFrack() {
        criarMenuInicial();
    }

    private void criarMenuInicial() {
        frame = new JFrame("FrickFrack - Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        JPanel panelMenu = new JPanel(new GridLayout(3, 1));

        JLabel labelTitulo = new JLabel("Selecione o modo de jogo:", SwingConstants.CENTER);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 16));

        JButton HumanoVsComputador = new JButton("Jogador vs Computador");
        JButton HumanoVsHumano = new JButton("Jogador vs Jogador");

        HumanoVsComputador.addActionListener(e -> {
            vsComputador = true;
            iniciarJogo();
        });

        HumanoVsHumano.addActionListener(e -> {
            vsComputador = false;
            iniciarJogo();
        });

        panelMenu.add(labelTitulo);
        panelMenu.add(HumanoVsComputador);
        panelMenu.add(HumanoVsHumano);

        frame.add(panelMenu, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void iniciarJogo() {
        frame.dispose();
        frame = new JFrame("FrickFrack");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 450);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        JPanel panelTabuleiro = new JPanel(new GridLayout(3, 3));

        Arrays.fill(tabuleiro, "");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final int row = i, col = j;
                botoes[i][j] = new JButton();
                botoes[i][j].setFont(new Font("Arial", Font.BOLD, 40));
                botoes[i][j].setFocusPainted(false);
                botoes[i][j].addActionListener(e -> clique(row, col));
                if (!vsComputador) {
                    botoes[i][j].addMouseListener(new MouseAdapter() {
                        public void mousePressed(MouseEvent e) {
                            if (modoArrastar && botoes[row][col].getText().equals(jogadorAtual)) {
                                origemSelecionada = new Point(row, col);
                            }
                        }

                        public void mouseReleased(MouseEvent e) {
                            if (modoArrastar && origemSelecionada != null && (row != origemSelecionada.x || col != origemSelecionada.y)) {
                                realizarArrasto(origemSelecionada.x, origemSelecionada.y, row, col);
                                origemSelecionada = null;
                            }
                        }
                    });
                }
                panelTabuleiro.add(botoes[i][j]);
            }
        }

        JPanel panelInferior = new JPanel(new BorderLayout());

        JButton botaoReset = new JButton("Reiniciar");
        botaoReset.setFont(new Font("Arial", Font.PLAIN, 12));
        botaoReset.addActionListener(e -> resetarJogo());

        labelMensagem = new JLabel("Vez do Jogador X", SwingConstants.CENTER);
        labelMensagem.setFont(new Font("Arial", Font.PLAIN, 16));

        panelInferior.add(botaoReset, BorderLayout.NORTH);
        panelInferior.add(labelMensagem, BorderLayout.SOUTH);

        frame.add(panelTabuleiro, BorderLayout.CENTER);
        frame.add(panelInferior, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void clique(int row, int col) {
        int indice = row * 3 + col;
        if (!tabuleiro[indice].equals("") || !jogoAtivo || modoArrastar) return;

        tabuleiro[indice] = jogadorAtual;
        botoes[row][col].setText(jogadorAtual);
        botoes[row][col].setForeground(jogadorAtual.equals("X") ? Color.BLUE : Color.RED);

        if (jogadorAtual.equals("X")) jogadasX++;
        else jogadasO++;

        if (verificarVitoria()) {
            labelMensagem.setText("Jogador " + jogadorAtual + " Venceu!");
            jogoAtivo = false;
        } else if (isTabuleiroCompleto()) {
            labelMensagem.setText("Empate!");
            jogoAtivo = false;
        } else {
            if (!vsComputador && jogadasX >= 3 && jogadasO >= 3) modoArrastar = true;
            trocarJogador();
            atualizarMensagem();
        }
    }

    private void realizarArrasto(int origemRow, int origemCol, int destinoRow, int destinoCol) {
        int origemIndex = origemRow * 3 + origemCol;
        int destinoIndex = destinoRow * 3 + destinoCol;
        if (!tabuleiro[destinoIndex].equals("")) return;

        tabuleiro[destinoIndex] = jogadorAtual;
        tabuleiro[origemIndex] = "";

        botoes[destinoRow][destinoCol].setText(jogadorAtual);
        botoes[destinoRow][destinoCol].setForeground(jogadorAtual.equals("X") ? Color.BLUE : Color.RED);
        botoes[origemRow][origemCol].setText("");

        if (jogadorAtual.equals("X")) arrastosX++;
        else arrastosO++;

        if (verificarVitoria()) {
            labelMensagem.setText("Jogador " + jogadorAtual + " Venceu!");
            jogoAtivo = false;
        } else if (arrastosX >= 6 && arrastosO >= 6) {
            labelMensagem.setText("Empate por limite de arrastos!");
        
            jogoAtivo = false;
        } else {
            trocarJogador();
            atualizarMensagem();
            
        }
    }

    private void trocarJogador() {
        jogadorAtual = jogadorAtual.equals("X") ? "O" : "X";
    }

    private void atualizarMensagem() {
        labelMensagem.setText("Vez do Jogador " + jogadorAtual);
    }

    private boolean verificarVitoria() {
        for (int i = 0; i < 9; i += 3) {
            if (!tabuleiro[i].equals("") && tabuleiro[i].equals(tabuleiro[i + 1]) && tabuleiro[i].equals(tabuleiro[i + 2])) return true;
        }
        for (int i = 0; i < 3; i++) {
            if (!tabuleiro[i].equals("") && tabuleiro[i].equals(tabuleiro[i + 3]) && tabuleiro[i].equals(tabuleiro[i + 6])) return true;
        }
        if (!tabuleiro[0].equals("") && tabuleiro[0].equals(tabuleiro[4]) && tabuleiro[0].equals(tabuleiro[8])) return true;
        if (!tabuleiro[2].equals("") && tabuleiro[2].equals(tabuleiro[4]) && tabuleiro[2].equals(tabuleiro[6])) return true;
        return false;
    }

    private boolean isTabuleiroCompleto() {
        for (String pos : tabuleiro) {
            if (pos.equals("")) return false;
        }
        return true;
    }

    private void resetarJogo() {
        Arrays.fill(tabuleiro, "");
        jogadorAtual = "X";
        jogoAtivo = true;
        jogadasX = jogadasO = arrastosX = arrastosO = 0;
        modoArrastar = false;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botoes[i][j].setText("");
            }
        }
        labelMensagem.setText("Vez do Jogador X");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrickFrack());
    }
}
