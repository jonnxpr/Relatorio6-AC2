
package relatorio6;

import java.io.IOException;

/**
 *
 * @author Jonathan
 */
public class Relatorio6 {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException{
        String arqEntrada = "testeula.ula";
        String arqSaida = "testeula.hex";
        String progCom = "envia.exe";
        String porta = "com3";
        
        Interpretador i = new Interpretador();
        i.gerarHex(arqEntrada, arqSaida);
        
        ComunicadorArduino com = new ComunicadorArduino(arqSaida, progCom, porta);
        com.executar();
    }
    
}
