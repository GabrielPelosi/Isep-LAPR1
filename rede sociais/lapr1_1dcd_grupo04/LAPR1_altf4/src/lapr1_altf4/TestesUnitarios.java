package lapr1_altf4;

import java.io.FileNotFoundException;
import static lapr1_altf4.Unitarios.*;
import org.la4j.Matrix;
import org.la4j.decomposition.EigenDecompositor;
import org.la4j.matrix.dense.Basic2DMatrix;

public class TestesUnitarios {

    public static void main(String[] args) {
        //main testeGrauMedio
        String m1 = "2,250";
        System.out.println("\n\n Teste associado ao método GrauMedio() ");
        System.out.println("\nTestar para os valores da seguinte matriz e retorna 2,250");
        System.out.println("\n");
        for (int i = 0; i < nMatrizTeste; i++) {
            for (int j = 0; j < nMatrizTeste; j++) {
                System.out.print(matrizTeste[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("");
        System.out.println(testeGrauMedio(matrizTeste, 4, m1));
        System.out.println("\nTestar para os valores da seguinte matriz e retorna 2,000");
        System.out.println("");
        for (int i = 0; i < nMatrizTeste2; i++) {
            for (int j = 0; j < nMatrizTeste2; j++) {
                System.out.print(matrizTeste2[i][j] + " ");
            }
            System.out.println();
        }
        String m4 = "2,000";
        System.out.println("");
        System.out.println(testeGrauMedio(matrizTeste2, nMatrizTeste2, m4));

//main testeDensidade
        System.out.println("\n\n\n Teste associado ao método Densidade() ");
        System.out.println("\nTestar para os valores da matriz teste e retornar o valor da densidade igual a 0,75");
        System.out.println("");
        double m = (0.75f);
        String resposta2 = testeDensidade(matrizTeste, nMatrizTeste, m);
        System.out.println(resposta2);
        System.out.println("\nTestar para os valores da matriz teste 2 e retornar o valor da densidade igual a 1");
        double m2 = (1.f);
        String resposta3 = testeDensidade(matrizTeste2, nMatrizTeste2, m2);
        System.out.println(resposta3);

//main testeGrau
        System.out.println("\n\n\n Teste associado ao método Calcular Grau() ");
        System.out.print("\nTestar para os valores da matriz teste e retornar a matriz {2,3,2,2}");
        System.out.println("");
        int[] teste1 = {2, 3, 2, 2};
        int[] graus1 = new int[nMatrizTeste];
        grauNo(matrizTeste, graus1, nMatrizTeste);
        String resp1 = testegrauNo(graus1, teste1, nMatrizTeste);
        System.out.println(resp1);
        System.out.println("\nTestar para os valores da matriz teste 2 e retornar a matriz {3,1,2}");
        int[] teste2 = {3, 1, 2};
        int[] graus2 = new int[nMatrizTeste2];
        grauNo(matrizTeste2, graus2, nMatrizTeste2);
        String resp2 = testegrauNo(graus2, teste2, nMatrizTeste2);
        System.out.println(resp2);

//main TesteCalculoMatrizPotencias
        System.out.println("\n\n\n Teste associado ao método Calcular Matriz Potêncais() ");
        System.out.println("\nTestar para os valores da matriz teste, calculando essa matriz ao cubo e retornar a matriz {{1,1,0,2},{2,2,2,1},{1,2,2,0},{1,2,1,1}}");
        int[][] matriznova = {{1, 1, 0, 2}, {2, 2, 2, 1}, {1, 2, 2, 0}, {1, 2, 1, 1}};
        produtorioteste(matrizTeste, nMatrizTeste, 2);
        String resp3 = verificacao(matrizTeste, matriznova, 4);
        System.out.println(resp3);
        System.out.println("\nTestar para os valores da matriz teste 2,calculando essa matriz ao cubo e retornar a matriz {{3,1,2},{1,1,1},{2,1,2}}");
        int[][] matriznova1 = {{3, 1, 2}, {1, 1, 1}, {2, 1, 2}};
        produtorioteste(matrizTeste2, nMatrizTeste2, 2);
        String resp4 = verificacao(matrizTeste2, matriznova1, 3);
        System.out.println(resp4);

        /* Teste da centralidade*/
        System.out.println("\n\n\n Testes para a Centralidade");
        int[][] matrizAdj1 = {{0, 1, 0}, {1, 0, 1}, {0, 1, 0}};
        double[] matrizCent1 = new double[matrizAdj1.length];
        double[] matrizSup1 = {0.500, 0.707, 0.500};
        System.out.println("\nTestar para a matrizteste e retornar a matriz {0.500, 0.707, 0.500}");
        centralidadeMatriz(matrizAdj1, matrizCent1);
        System.out.println(comparaMatriz(matrizCent1, matrizSup1));
        int[][] matrizAdj2 = {{0, 1, 0, 1, 0}, {1, 0, 1, 1, 1}, {0, 1, 0, 1, 0}, {1, 1, 1, 0, 0}, {0, 1, 0, 0, 0}};;
        double[] matrizCent2 = new double[matrizAdj2.length];
        double[] matrizSup2 = {0.412, 0.583, 0.412, 0.524, 0.217};
        System.out.println("\nTestar para a matrizteste 2 e retornar a matriz {0.500, 0.707, 0.500}");
        centralidadeMatriz(matrizAdj2, matrizCent2);
        System.out.println(comparaMatriz(matrizCent2, matrizSup2));
        
        //teste metodo grau de saida do no
        System.out.println("\n\n\n Teste para os graus de saida dos nós \n");
        int[][] matrizOrientada = {{0, 0, 1, 1}, {1, 0, 0, 0}, {1, 0, 0, 1}, {1, 1, 1, 0}};
        int [][] matrizOrientada1 = {{1,1,0,1},{0,0,1,1},{1,0,1,0}, {0,0,1,0}};
        int [] testegrauSaida = {3,1,2,2};
        int [] testegrauSaida1 = {2,1,3,2};
        int nNos = 4;
        int[] graus = new int[nNos];
        String resposta;
        System.out.println("\nTestar para a matrizteste e retornar a matriz {3,1,2,2} ");
        testeGrauNoSaida(matrizOrientada, graus, nNos);
        resposta = compararGrausSaida(testegrauSaida, graus, nNos);
        System.out.println(resposta);
        System.out.println("\nTestar para a matrizteste 2 e retornar a matriz {2,1,3,2} ");
        testeGrauNoSaida(matrizOrientada1, graus, nNos);
        resposta = compararGrausSaida(testegrauSaida1, graus, nNos);
        System.out.println(resposta);
        
        //teste metodo grau de entrada no
        System.out.println("\n\n\n Teste para o grau de entrada do no ");
        int [] testegrauentrada = {2,1,2,3};
        int [] grauentrada=new int[nNos];
        int [] testegrauentrada1 ={3,2,2,1};
        String resposta1;
        System.out.println("\nTestar para a matrizteste e retornar a matriz {2,1,2,3}");
        grauNoEntradaTeste(matrizOrientada,grauentrada,nNos);
        resposta1=compararGrauNoEntrada(testegrauentrada,grauentrada,nNos);
        System.out.println(resposta1);
        System.out.println("\nTestar para a matrizteste 2 e retornar a matriz {3,2,2,1}");
        grauNoEntradaTeste(matrizOrientada1,grauentrada,nNos);
        resposta1=compararGrauNoEntrada(testegrauentrada1,grauentrada,nNos);
        System.out.println(resposta1);
    }

    //Método teste do cálculo do grau médio de uma rede
    public final static int[][] matrizTeste = {{1, 0, 0, 1}, {1, 1, 1, 0}, {0, 1, 1, 0}, {0, 1, 0, 1}};
    public final static int nMatrizTeste = 4;
    public final static int[][] matrizTeste2 = {{1, 1, 1}, {1, 0, 0}, {1, 0, 1}};
    public final static int nMatrizTeste2 = 3;

    public static String testeGrauMedio(int[][] matrizTeste, int nMatriz, String resposta) {
        String res = "FALSE!";
        double grauMedio = Unitarios.GrauMedio(matrizTeste, nMatriz);
        String grauMedio1 = String.format("%.3f", grauMedio);
        if (grauMedio1.equals(resposta)) {
            res = "TRUE!";
        }
        return res;
    }

    //Método teste do cálculo da densidade da rede social
    //Para a matrizTeste o cálculo da densidade terá de ser igual a (9/(4*4)-4) = 0,75; 9 representa o número de interações e 4 representa o número de linahs e colunas da matriz de teste. 
    //Para a matrizTeste 2 o cálculo da densidade terá de ser igual a (6/(3*3)-3) = 1;
    public static String testeDensidade(int[][] matrizTeste, int nMatriz, double resposta) {
        String res = "FALSE!";
        if ((Unitarios.densidade(matrizTeste, nMatriz)) == resposta) {
            res = "TRUE!";
        }
        return res;
    }

    private static String testegrauNo(int[] graus, int[] teste, int num) {
        String resposta = "FALSE!!";
        for (int i = 0; i < num; i++) {
            if (graus[i] != teste[i]) {
                return resposta;
            }
        }
        resposta = "TRUE!!";
        return resposta;
    }

    public static void produtorioteste(int[][] a, int nNos, int k) {
        int[][] resultado = new int[nNos][nNos];
        int[][] a2 = new int[nNos][nNos];
        int cont = 1;

        for (int i = 0; i < nNos; i++) {
            for (int j = 0; j < nNos; j++) {
                a2[i][j] = a[i][j];
            }
        }
        for (int m = 1; m < k; m++) {
            cont++;
            for (int i = 0; i < nNos; i++) {
                for (int j = 0; j < nNos; j++) {
                    for (int l = 0; l < nNos; l++) {
                        resultado[i][j] += a2[i][l] * a[l][j];
                    }
                }
            }
            for (int i = 0; i < nNos; i++) {
                for (int j = 0; j < nNos; j++) {
                    a[i][j] = resultado[i][j];
                }
            }
            for (int i = 0; i < nNos; i++) {
                for (int j = 0; j < nNos; j++) {
                    resultado[i][j] = 0;

                }
            }
        }
    }

    public static String verificacao(int[][] matriz, int[][] matriznova, int a) {
        String resposta = "FALSE!!";
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < a; j++) {
                if (matriz[i][j] == matriznova[i][j]) {
                } else {
                    return resposta;
                }
            }
        }
        resposta = "TRUE!!";
        return resposta;
    }

    /* Centralidade */
    public static void centralidadeMatriz(int[][] matrizAdj, double[] matrizCent) {
        double[][] matrizDouble = new double[matrizAdj.length][matrizAdj.length];

        matrizIntDouble(matrizAdj, matrizDouble);

        Matrix a = new Basic2DMatrix(matrizDouble);

        EigenDecompositor eigenD = new EigenDecompositor(a);

        Matrix[] mattD = eigenD.decompose();

        //Valores Proprios
        double matrizValores[][] = mattD[0].toDenseMatrix().toArray();

        //Vetores Proprios
        double matrizVetores[][] = mattD[1].toDenseMatrix().toArray();

        int nmaior = maiorCentral(matrizVetores);

        colocarMatrizCent(matrizValores, matrizCent, nmaior);
    }

    public static void matrizIntDouble(int[][] m1, double[][] m2) {
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m1.length; j++) {
                m2[i][j] = m1[i][j];
            }
        }
    }

    public static int maiorCentral(double[][] m) {
        double maior = 0;
        int n = 0;

        for (int i = 0; i < m.length; i++) {
            if (i == 0) {
                maior = m[i][i];
            } else if (m[i][i] > maior) {
                maior = m[i][i];
                n = i;
            }
        }
        return n;
    }

    public static void colocarMatrizCent(double[][] matrizValores, double[] matrizCent, int n) {
        for (int i = 0; i < matrizValores.length; i++) {
            matrizCent[i] = matrizValores[i][n];
        }
    }

    public static String comparaMatriz(double[] m1, double[] m2) {
        for (int i = 0; i < m1.length; i++) {
            String m1s = String.format("%.3f", m1[i]);
            String m2s = String.format("%.3f", m2[i]);
            if (!m1s.equals(m2s)) {
                return "FALSE";
            }
        }
        return "TRUE";
    }
    
    public static String compararGrausSaida(int []testegraus, int[]graus, int nNos){
        for (int i = 0; i < nNos; i++) {
            if(testegraus[i] != graus[i]){
                return "FALSO";
            }
            
        }return "TRUE!";
        
    }
    
    public static String compararGrauNoEntrada (int [] testegraus, int [] grausentrada , int nNos){
     for (int i=0;i<nNos;i++)  {
     if (testegraus[i]!=grausentrada[i]){
         return "FALSE!";
     }    
     }return "TRUE!";
    }
    
    public static void grauNoEntradaTeste(int[][] matrizAdj, int[] grauEntrada, int nNos) {
        for (int i = 0; i < nNos; i++) {
            grauEntrada[i] = calcularGrauNoEntrada(matrizAdj, i, nNos);
        }
    }

    
    
    
    
    
    
    
     public static void testeGrauNoSaida(int[][] matrizAdj, int[] graus, int nNos) {
        for (int i = 0; i < nNos; i++) {
            graus[i] = calculoGrauNoSaida(matrizAdj, i, nNos);
        }
        
    }
}

