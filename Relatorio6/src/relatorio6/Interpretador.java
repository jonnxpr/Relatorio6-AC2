package relatorio6;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 *
 * @author Jonathan
 */
public class Interpretador {

    private final Queue<String> filaDeComandos;
    private char A;
    private char B;
    
    public Interpretador() {
        filaDeComandos = new LinkedList();
        A = 'X';
        B = 'X';
    }

    private void lerArquivo(String nomeArq) {
        String buffer;
        System.out.println("Lendo dados do arquivo: " + nomeArq);

        try (FileReader fr = new FileReader(nomeArq);
                Scanner in = new Scanner(fr)) {

            buffer = in.nextLine();
            while (true) {
                if (buffer.equals("fim.")) {
                    break;
                }

                if (buffer.equals("inicio:")) {

                } else {
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
                if (!(instrucao == null)){
                    fw.write(instrucao);
                }
            }
        } catch (IOException e) {
            System.out.println("ERRO: Não foi possível abrir o arquivo para escrita desejado ou ocorreu algum erro no seu uso.");
        }

    }

    private String decodificaComando(String comando) {
        String instrucao = null;
        
        if (comando.contains("=")) {
            if (comando.charAt(0) == 'A'){
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
    
    private char recuperaOpcode(String comando){
        char opcode;
        System.out.println("Comando = " + comando);
        switch(comando){
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
