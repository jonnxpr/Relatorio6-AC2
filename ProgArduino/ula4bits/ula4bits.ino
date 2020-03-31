/*
 * Relatório 6
 * Alunos: Jonathan e Stenio
 */
 
int a0 = 0;   
int b0 = 0;
int opcode = 0;   
int ledA = 13;
int ledB = 12;
int ledSaida = 11;
int ledVaiUm = 10;
int saida;

void setup() {
  Serial.begin(9600);     
  pinMode(ledA,OUTPUT);
  pinMode(ledB,OUTPUT);
  pinMode(ledSaida,OUTPUT);
  pinMode(ledVaiUm,OUTPUT);
}


void loop() {
  if (Serial.available() > 0) {
    a0 = Serial.parseInt();
    b0 = Serial.parseInt();
    opcode = Serial.parseInt();
    
    Serial.print("a= ");
    Serial.print(a0);
    Serial.println();
    Serial.print("b= ");
    Serial.print(b0);
    Serial.println();
    Serial.print(opcode);
    Serial.println();
    
    if (Serial.read()=='\n') {
      if (opcode == 0) {
        saida = portaand(a0, b0);
        Serial.print("and= ");
        Serial.print(saida);
        Serial.println();
        if (a0 == 1){
          digitalWrite(ledA, 1);
        }
        if (a0 == 0){
          digitalWrite(ledA, 0);
        }

        if (b0 == 1){
          digitalWrite(ledB, 1);
        }
        if (b0 == 0){
          digitalWrite(ledB, 0);
        }
        
        if (saida == 1){
          digitalWrite(ledSaida, 1);
        } else {
          digitalWrite(ledSaida, 0);
        }
      } else if (opcode == 1) {
        saida = portaor(a0, b0);
        Serial.print("or= ");
        Serial.print(saida);
        Serial.println();

        if (a0 == 1){
          digitalWrite(ledA, 1);
        }
        if (a0 == 0){
          digitalWrite(ledA, 0);
        }

        if (b0 == 1){
          digitalWrite(ledB, 1);
        }
        if (b0 == 0){
          digitalWrite(ledB, 0);
        }
        
        if (saida == 1){
          digitalWrite(ledSaida, 1);
        } else {
          digitalWrite(ledSaida, 0);
        }
      } else if (opcode == 2) {
        saida = portanot(a0);
        Serial.print("not a0= ");
        Serial.print(saida);
        Serial.println();

        if (a0 == 1){
          digitalWrite(ledA, 1);
        }
        if (a0 == 0){
          digitalWrite(ledA, 0);
        }

        if (b0 == 1){
          digitalWrite(ledB, 1);
        }
        if (b0 == 0){
          digitalWrite(ledB, 0);
        }
        
        if (saida == -2){
          digitalWrite(ledSaida, 0);
        }
        if (saida == -1){
          digitalWrite(ledSaida, 1);
        }
      } else if (opcode == 3) {
        saida = sum(a0, b0);
        Serial.print("saida sum= ");
        Serial.print(saida);
        Serial.println();

        if (a0 == 1){
          digitalWrite(ledA, 1);
        }
        if (a0 == 0){
          digitalWrite(ledA, 0);
        }

        if (b0 == 1){
          digitalWrite(ledB, 1);
        }
        if (b0 == 0){
          digitalWrite(ledB, 0);
        }
        
        if (saida == 0){
          digitalWrite(ledSaida, 0);
          digitalWrite(ledVaiUm, 0);
        } else if (saida == 1){
          digitalWrite(ledSaida, 1);
          digitalWrite(ledVaiUm, 0);
        } else if (saida > 1){
          digitalWrite(ledSaida, 1);
          digitalWrite(ledVaiUm, 1);
        }
      } 
    }  
  }
}

int portaxor(int a, int b)
{
  return(a^b);
}

int portaor(int a, int b)
{
  return(a|b);
}

int portaand(int a, int b)
{
  return(a&b);
}

int portanot(int a)
{
  return(~a);
}

int sum(int a, int b){
  return a+b;
}
