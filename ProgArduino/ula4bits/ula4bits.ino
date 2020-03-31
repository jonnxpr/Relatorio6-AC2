/*
 * Relatório 6
 * Alunos: Jonathan e Stenio
 */
 
int A = 0;   
int B = 0;
int opcode = 0;
int saida = 0;
   
int ledF0 = 10;
int ledF1 = 11;
int ledF2 = 12;
int ledF3 = 13;

const int logico0 = 0000;
const int logico1 = 1111;

String entrada;

void setup() {
  Serial.begin(9600);     
  pinMode(ledF0, OUTPUT);
  pinMode(ledF1, OUTPUT);
  pinMode(ledF2, OUTPUT);
  pinMode(ledF3, OUTPUT);
}

int selecionaOpcode()
{
  unsigned int resposta;

  switch(opcode){
    case '0':
      resposta = logico0;
      break;
    case '1':
      resposta = logico1;
      break;
    case '2':
      resposta = NOT(A);
      break;
    case '3':
      resposta = NOT(B);
      break;
    case '4':
      resposta = OR(A, B);
      break;
    case '5':
      resposta = AND(A, B);
      break;
    case '6':
      resposta = XOR(A, B);
      break;
    case '7':
      resposta = NAND(A, B);
      break;
    case '8':
      resposta = NOR(A, B);
      break;
    case '9':
      resposta = XNOR(A, B);
      break;
    case 'A':
      resposta = OR(NOT(A), B);
      break;
    case 'B':
      resposta = OR(A, NOT(B));
      break;
    case 'C':
      resposta = AND(NOT(A), B);
      break;
    case 'D':
      resposta = AND(A, NOT(B));
      break;
    case 'E':
      resposta = OR(NOT(A), NOT(B));
      break;
    case 'F':
      resposta = AND(NOT(A), NOT(B));
      break;
  }

  return resposta;
}

int hexaParaDec(char valor)
{
  int resposta;
  
  if(isAlpha(valor))
  {
    resposta = (int)valor - 55;
  } else {
    resposta = (int)valor - 48;
  }
  
  return resposta;
}

void mostrarSaida(int valor){
  int temp = valor;
  if(valor < 0){
      temp = valor + 16;
  }
  
  if ((temp >= 8) && ((temp -= 8) >= 0))
  {
    digitalWrite(ledF0, 1);
  } else {
    digitalWrite(ledF0, 0);
  }
  
  if((temp >= 4) && ((temp -= 4) >= 0))
  {
    digitalWrite(ledF1, 1);
  } else {
    digitalWrite(ledF1, 0);
  }
  
  if((temp >= 2) && ((temp -= 2) >= 0))
  {
    digitalWrite(ledF2, 1);
  } else {
    digitalWrite(ledF2, 0);
  }
  
  if((temp >= 1) && ((temp -= 1) >= 0))
  {
    digitalWrite(ledF3, 1);
  } else {
    digitalWrite(ledF3, 0);
  }
}

int XOR(int valor1, int valor2)
{
  return (valor1^valor2);
}

int XNOR(int valor1, int valor2)
{
  return ~(valor1 ^ valor2);
}

int OR(int valor1, int valor2)
{
  return (valor1 | valor2);
}

int NOR(int valor1, int valor2)
{
  return ~(valor1 | valor2);
}

int AND(int valor1, int valor2)
{
  return (valor1 & valor2);
}

int NAND(int valor1, int valor2)
{
  return ~(valor1 & valor2);
}

int NOT(int valor)
{
  return (~valor);
}

void loop() {
  if (Serial.available() > 0) 
  {
    entrada = Serial.readString();

    A = hexaParaDec(entrada.charAt(0));
    B = hexaParaDec(entrada.charAt(2));
    opcode = hexaParaDec(entrada.charAt(4));
    
    if (Serial.read()=='\n') 
    {  
      Serial.print("A= ");
      Serial.print(A);
      Serial.println();
      Serial.print("B= ");
      Serial.print(B);
      Serial.println();
      Serial.print("Opcode= ");
      Serial.print(opcode);
      Serial.println();

      saida = selecionaOpcode();
      mostrarSaida(saida);
    }
  }
}
