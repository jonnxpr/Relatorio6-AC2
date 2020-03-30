package relatorio6;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Jonathan
 */
public class ComunicadorArduino {

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
            while (in.hasNext()){
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
    
    private String decodificaInstrucao(String instrucao){
        StringBuilder instrucaoDecodificada = new StringBuilder();
        instrucaoDecodificada.append(hexaParaDec(instrucao.charAt(0))).append(" ");
        instrucaoDecodificada.append(hexaParaDec(instrucao.charAt(1))).append(" ");
        instrucaoDecodificada.append(hexaParaDec(instrucao.charAt(2)));
        
        return instrucaoDecodificada.toString();
    }
 
    private int hexaParaDec(char hex){
        int valor;

        switch(hex){
            case '0':
                valor = 0;
                break;
            case '1':
                valor = 1;
                break;
            case '2':
                valor = 2;
                break;
            case '3':
                valor = 3;
                break;
            case '4':
                valor = 4;
                break;
            case '5':
                valor = 5;
                break;
            case '6':
                valor = 6;
                break;
            case '7':
                valor = 7;
                break;
            case '8':
                valor = 8;
                break;
            case '9':
                valor = 9;
                break;
            case 'A':
                valor = 10;
                break;
            case 'B':
                valor = 11;
                break;
            case 'C':
                valor = 12;
                break;
            case 'D':
                valor = 13;
                break;
            case 'E':
                valor = 14;
                break;
            case 'F':
                valor = 15;
                break;
            default:
                valor = -1;
                break;
        }
        return valor;
    }
}
