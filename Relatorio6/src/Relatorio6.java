import java.io.IOException;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.io.FileReader;

/**
 *
 * @author Jonathan e Stenio
 */
public class Relatorio6 {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner in = new Scanner(System.in);
        String arqEntrada = "testeula.ula";
        String arqSaida = "testeula.hex";
        String progCom = "envia.exe";
        String porta;
        
        System.out.print("Digite em qual porta o arduino está: ");
        porta = in.nextLine();
       
        Compilador i = new Compilador();
        i.gerarHex(arqEntrada, arqSaida);

        ComunicadorArduino com = new ComunicadorArduino(arqSaida, progCom, porta);
        com.executar();
    }
}

class Compilador {

    private final Queue<String> filaDeComandos;
    private char A;
    private char B;

    public Compilador() {
        filaDeComandos = new LinkedList();
        A = 'X';
        B = 'X';
    }

    private void lerArquivo(String nomeArq) {
        String buffer;
        System.out.println("Lendo comandos do arquivo: " + nomeArq);

        try (FileReader fr = new FileReader(nomeArq);
                Scanner in = new Scanner(fr)) {

            buffer = in.nextLine();
            while (true) {
                if (buffer.equals("fim.")) {
                    break;
                }

                if (!buffer.equals("inicio:")) {
                    filaDeComandos.add(buffer);
                }

                buffer = in.nextLine();
            }
        } catch (IOException e) {
            System.out.println("ERRO: Não foi possível abrir o arquivo para leitura desejado ou ocorreu algum erro no seu uso.");
        }
    }

    public void gerarHex(String nomeArqLeitura, String nomeArqSaida) {
        lerArquivo(nomeArqLeitura);

        try (FileWriter fw = new FileWriter(nomeArqSaida)) {
            while (!filaDeComandos.isEmpty()) {
                String comando = filaDeComandos.remove();
                String instrucao = decodificaComando(comando);
                if (!(instrucao == null)) {
                    fw.write(instrucao);
                }
            }

            System.out.println("Arquivo .hex gerado com sucesso!\n\n");
        } catch (IOException e) {
            System.out.println("ERRO: Não foi possível abrir o arquivo para escrita desejado ou ocorreu algum erro no seu uso.");
        }
    }

    private String decodificaComando(String comando) {
        String instrucao = null;

        if (comando.contains("=")) {
            if (comando.charAt(0) == 'A') {
                A = comando.charAt(2);
            } else {
                B = comando.charAt(2);
            }
        } else {
            StringBuilder s = new StringBuilder();
            s.append(A);
            s.append(B);
            s.append(recuperaOpcode(comando));
            s.append("\n");
            instrucao = s.toString();
        }

        return instrucao;
    }

    private char recuperaOpcode(String comando) {
        char opcode;
        //System.out.println("Comando = " + comando);
        switch (comando) {
            case "zeroL;":
                opcode = '0';
                break;
            case "umL;":
                opcode = '1';
                break;
            case "An;":
                opcode = '2';
                break;
            case "Bn;":
                opcode = '3';
                break;
            case "AouB;":
                opcode = '4';
                break;
            case "AeB;":
                opcode = '5';
                break;
            case "AxorB;":
                opcode = '6';
                break;
            case "AnandB;":
                opcode = '7';
                break;
            case "AnorB;":
                opcode = '8';
                break;
            case "AxnorB;":
                opcode = '9';
                break;
            case "AnouB;":
                opcode = 'A';
                break;
            case "AouBn;":
                opcode = 'B';
                break;
            case "AneB;":
                opcode = 'C';
                break;
            case "AeBn;":
                opcode = 'D';
                break;
            case "AnouBn;":
                opcode = 'E';
                break;
            case "AneBn;":
                opcode = 'F';
                break;
            default:
                opcode = '*';
                break;
        }

        return opcode;
    }
}

class ComunicadorArduino {

    private final String arqExec, progCom, porta;

    public ComunicadorArduino(String arqExec, String progCom, String porta) {
        this.arqExec = arqExec;
        this.progCom = progCom;
        this.porta = porta;
    }

    public void executar() throws IOException, InterruptedException {
        Scanner scn = new Scanner(System.in);
        ProcessBuilder pb;
        Process p;

        try (FileReader fr = new FileReader(arqExec);
                Scanner in = new Scanner(fr)) {
            while (in.hasNext()) {
                String instrucao = in.nextLine();
                System.out.println("Instrução a ser executada = " + instrucao);
                String instrucaoDecodificada = decodificaInstrucao(instrucao);
                System.out.println("Instrução decodificada a ser executada = " + instrucaoDecodificada);
                System.out.println("Pressione uma tecla para executar a instrução.");
                scn.nextLine();
                //pb = new ProcessBuilder(progCom, porta, instrucaoDecodificada);
                //p = pb.start();
                //p.waitFor();
            }
            System.out.println("Fim da execução.");

        } catch (IOException e) {
            System.out.println("ERRO: Não foi possível abrir o arquivo para leitura desejado ou ocorreu algum erro no seu uso.");
        }
    }

    private String decodificaInstrucao(String instrucao) {
        StringBuilder instrucaoDecodificada = new StringBuilder();
        instrucaoDecodificada.append(instrucao.charAt(0)).append(" ");
        instrucaoDecodificada.append(instrucao.charAt(1)).append(" ");
        instrucaoDecodificada.append(instrucao.charAt(2));

        return instrucaoDecodificada.toString();
    }
}
