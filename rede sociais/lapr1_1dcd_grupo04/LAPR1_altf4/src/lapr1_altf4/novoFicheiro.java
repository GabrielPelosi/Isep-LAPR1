/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr1_altf4;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Formatter;
import static lapr1_altf4.Unitarios.listarMatrizDouble;
import static lapr1_altf4.Unitarios.listarMatrizGrauEntrada;
import static lapr1_altf4.Unitarios.listarMatrizGrauSaida;
import static lapr1_altf4.Unitarios.matrizIntDouble;
import org.la4j.Matrix;
import org.la4j.matrix.DenseMatrix;
import org.la4j.matrix.dense.Basic2DMatrix;
import org.la4j.decomposition.EigenDecompositor;

/**
 *
 * @author franc, rickropes
 */
public class novoFicheiro {
    private final static String SEPARADOR_EXTENSAO_FICHEIRO = "\\.";
    private final static String SEPARADOR_NOME_FICHEIRO = "_";
    private final static int N_CAMPOS_NOME_FICHEIRO = 3;
    private final static String SEPARADOR_DADOS_FICHEIRO = ",";
    private final static int N_COLUNAS_RAMOS = 3;
    private final static int N_CAMPOS_INFO_DESCRICAONOS = 4;
    private final static int N_COLUNAS_NOS = 4;
    
    public static void produtorio(Formatter out,int[][] a, int nNos, int k) {
        int[][] resultado = new int[nNos][nNos];
        int[][] a2 = new int[nNos][nNos];
        int cont = 1;

        for (int i = 0; i < nNos; i++) {
            for (int j = 0; j < nNos; j++) {
                a2[i][j] = a[i][j];
            }
        }
        out.format ("\r\n");
        out.format ("Potência da Matriz de Adjacências K=" + cont);
        out.format ("\r\n");
         for (int i=0; i<nNos; i++){
            for (int j=0; j<nNos;j++){
            out.format("%5s",a[i][j]);   
            }
            out.format ("\r\n");
        }
         out.format ("\r\n");
        System.out.println("\r\n");
        for (int m = 1; m < k; m++) {
            cont++;
           out.format ("Potência da Matriz de Adjacências k="+cont);
           out.format ("\r\n");

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
            for (int i = 0; i < nNos; i++) {
                for (int j = 0; j < nNos; j++) {
                    out.format("%5s",a[i][j]);
                    
                }
               out.format ("\r\n");
            }
             out.format ("\r\n");
        }

    }
    //Método de cálculo do grau dos nós-------------------------------------------
      public static void grauNo(int[][] matrizAdj, int[] graus, int nNos) {
        for (int i = 0; i < nNos; i++) {
            graus[i] = calculoGrauNo(matrizAdj, i, nNos);
        }
    }

   
    private static int calculoGrauNo(int[][] matrizAdj, int num, int nNos) {
        int grau = 0;
        for (int i = 0; i < nNos; i++) {
            grau += matrizAdj[num][i];

        }
        return grau;
    }

    public static void mostrarGrauNo(Formatter out ,int[] graus, String[][] descricaoNos) {
        for (int i = 0; i < graus.length; i++) {
          out.format (descricaoNos[i][0] + ",tem grau: " + graus[i]);
          out.format ("\r\n");
        }
    }
     //Mostrar informação sobre os nós da rede social carregada em meméria sob a forma de matriz
    
    public static void mostrarNos (String[][] descricaoNos, int nNos,Formatter out, String nomeFich) {
        String[] infoNomeFich = nomeFich.split(SEPARADOR_EXTENSAO_FICHEIRO);
        String[] temp = infoNomeFich[0].split(SEPARADOR_NOME_FICHEIRO);
        String redesocial = temp[1].trim();
        out.format ("Nós da Rede Social "+redesocial + "----------");
        out.format ("\r\n");
        out.format ( "ID"+" | " + "MEDIA" + " | " + "MEDIA.TYPE" + " | " + "TYPE.LABEL" + " | " + "webURL");
        out.format ("\r\n");
     for (int i = 0 ; i < nNos; i++){
        for (int j = 0; j < N_COLUNAS_NOS; j++){
        out.format (descricaoNos[i][j]+ " | ");
    }
        out.format ("\r\n");
    }
    }
       //Mostrar informação sobre os ramos da rede social carregada em meméria sob a forma de matriz
    public static void mostrarRamos (String [][] descricaoRamos, Formatter fOutput, int nRamos, String nomeFich){
        String[] infoNomeFich = nomeFich.split(SEPARADOR_EXTENSAO_FICHEIRO);
        String[] temp = infoNomeFich[0].split(SEPARADOR_NOME_FICHEIRO);
        String redesocial = temp[1].trim();
        fOutput.format("Ramos da Rede Social "+redesocial+ "----------");
        fOutput.format ("\r\n");
        fOutput.format("FROM"+" | "+ "TO"+" | "+"WEIGHT");
        fOutput.format ("\r\n");
        for (int i = 0; i <nRamos ; i++){
            for (int j = 0; j < N_COLUNAS_RAMOS; j++) {
                fOutput.format (descricaoRamos[i][j]+ " | ");
            }
            fOutput.format ("\r\n");
            }
    } 
    public static void centralidadeMatriz(Formatter out, int[][] matrizAdj, double[] matrizCent) {
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

        listarVetorDouble(out, matrizCent, matrizCent.length);
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

    
    /* Erros */
    private static void erroSair(int length, String[] linhas) throws FileNotFoundException {
        LocalDateTime dateb = LocalDateTime.now();
        String dates = "";
        dates += dateb;
        String datev = dates.replace("T","-");
        String dater = datev.replace(":","-");
        String[] date = dater.split("\\.");
        Formatter outputLogErros = new Formatter(new File(date[0]+"-ERRO.txt"));
        String linha = "";
        for(int i = 0; i < linhas.length; i++){
            linha += linhas[i];
            if(i < linhas.length-1) linha += SEPARADOR_DADOS_FICHEIRO;
        }
        outputLogErros.format("%s---%s%n", linha, "numero de campos irregular.");
        outputLogErros.close();
        System.exit(0);

    }
       public static void listarVetorDouble(Formatter out, double[] matriz, int num) {
        for (int i = 0; i < num; i++) {
            out.format ("%17.3f%n", matriz[i]);
        }
    }
       public static void grauNoSaida(Formatter out,int[][] matrizAdj, int[] graus, int nNos) {
        for (int i = 0; i < nNos; i++) {
            graus[i] = calculoGrauNoSaida(matrizAdj, i, nNos);
        }
        listarMatrizGrauSaida(out,graus, nNos);
    }

    public static int calculoGrauNoSaida(int[][] matrizAdj, int num, int nNos) {
        int grau = 0;
        for (int i = 0; i < nNos; i++) {
            grau += matrizAdj[i][num];

        }
        return grau;
    }

    public static void grauNoEntrada(Formatter out, int[][] matrizAdj, int[] grauEntrada, int nNos) {
        for (int i = 0; i < nNos; i++) {
            grauEntrada[i] = calcularGrauNoEntrada(matrizAdj, i, nNos);
        }
        listarMatrizGrauEntrada(out,grauEntrada, nNos);
    }

    public static int calcularGrauNoEntrada(int[][] matrizAdj, int num, int nNos) {
        int grau = 0;
        for (int i = 0; i < nNos; i++) {
            grau = grau + matrizAdj[num][i];

        }
        return grau;
    }
       public static void listarMatrizGrauEntrada(Formatter out,int[] matriz, int num) {
        for (int i = 0; i < num; i++) {
            out.format("O valor do grau de entrada na posição " + (i+1)+ " é " + matriz[i]+ ".");
            out.format("\r\n");
        }
    }

    public static void listarMatrizGrauSaida(Formatter out, int[] matriz, int num) {
        for (int i = 0; i < num; i++) {
            out.format("O valor do grau de saida na posição " + (i+1) + " é " + matriz[i] + ".");
            out.format("\r\n");
        }
    }
    public static double pageRank(Formatter out, int[][] matrizAdj) {
        double[][] matrizDouble = new double[matrizAdj.length][matrizAdj.length];

        matrizIntDouble(matrizAdj, matrizDouble);

        Matrix a = new Basic2DMatrix(matrizDouble);

        EigenDecompositor eigenD = new EigenDecompositor(a);

        Matrix[] mattD = eigenD.decompose();

        //Valores Proprios
        double matrizValores[][] = mattD[0].toDenseMatrix().toArray();

        listarMatrizDouble(matrizValores,matrizValores.length);
        out.format("\r\n\r\n");
        //Vetores Proprios
        double matrizVetores[][] = mattD[1].toDenseMatrix().toArray();
        listarMatrizDouble(matrizVetores,matrizValores.length);
        
        int indiceMValor = maiorValorProprio(matrizValores);
        double vProprio = matrizVetores [indiceMValor] [indiceMValor];
        out.format ("%.2f",vProprio);
        return vProprio;
    }
    
    public static int maiorValorProprio(double[][] m) {
        double maior = 0;
        int n = 0; 
    for (int i = 0; i < m.length; i++){
        for (int j = 0; j < m.length; j++){
          if (i == 0) {
                maior = m[i][j];
            } else if (m[i][j] > maior) {
                maior = m[i][j];
                n = i;
            }  
        }
    }    
    return n;    
    }
    public static void pageRank(Formatter out,int[][] matrizAdj, int vezes, double d) {
        double[][] matrizDouble = new double[matrizAdj.length][matrizAdj.length];
        matrizIntDouble(matrizAdj, matrizDouble);

        vezes++;
        double[][] matrix = new double[matrizDouble.length][matrizDouble.length];
        double[][] matrixM = new double[matrizDouble.length][matrizDouble.length];
        int[] conexoes = new int[matrizDouble.length];
        matrizConexoes(conexoes, matrizDouble);
        matrixSet(matrix, conexoes, matrizDouble);

        //System.out.println("Matrix A");
        //listarMatrizDouble(matrix, matrix.length);
        matrixM(matrix, matrixM, d);

        pageRankMath(out,matrixM, vezes);
        out.format("%n Page Rank Absoluto %n");
        pageRankMathV(matrixM, out);
    }
    
    public static void matrixM(double[][] matrix, double[][] matrixM, double d) {
        double num = ((1 - d) / (double) matrix.length);

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrixM[i][j] = (matrix[i][j] * d) + num;
            }
        }
    }

    private static void pageRankMath(Formatter fOutput,double[][] matrix, int vezes) {
        double[] x = new double[matrix.length];
        double[] y = new double[x.length];
        for (int i = 0; i < matrix.length; i++) {
            x[i] = (double) 1 / matrix.length;
        }

        for (int i = 0; i < vezes; i++) {
            fOutput.format("Iteração %d %n", i);
            listarPageRank(fOutput,x);
            fOutput.format("%n");

            for (int j = 0; j < matrix.length; j++) {
                for (int k = 0; k < matrix.length; k++) {
                    y[j] += matrix[j][k] * x[k];
                }

            }
            colocarDoubles(x, y);
        }

    }

    private static void listarPageRank(Formatter out,double[] x) {
        for (int i = 0; i < x.length; i++) {
            out.format("%.3f     ",x[i]);
        }
        out.format("");
    }

    private static void colocarDoubles(double[] x, double[] y) {
        for (int i = 0; i < x.length; i++) {
            x[i] = y[i];
            y[i] = 0;
        }
    }

    private static void matrixSet(double[][] matrix, int[] conexoes, double[][] matrizDouble) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (conexoes[i] != 0) {
                    if (matrizDouble[i][j] != 0) {
                        matrix[j][i] = 1 / (double) conexoes[i];
                    }
                }else matrix[j][i] = 1 / (double) matrix.length;
            }
        }
    }

    private static void matrizConexoes(int[] conexoes, double[][] matrizDouble) {
        int num = 0;
        for (int i = 0; i < conexoes.length; i++) {
            for (int j = 0; j < conexoes.length; j++) {
                if (matrizDouble[i][j] != 0) {
                    num++;
                }
            }
            conexoes[i] = num;
            num = 0;
        }
    }
    
    public static void pageRankMathV(double[][] matrizDouble, Formatter out) {

        Matrix a = new Basic2DMatrix(matrizDouble);

        EigenDecompositor eigenD = new EigenDecompositor(a);

        Matrix[] mattD = eigenD.decompose();

        //Valores Proprios
        double matrizValores[][] = mattD[0].toDenseMatrix().toArray();

        //Vetores Proprios
        double matrizVetores[][] = mattD[1].toDenseMatrix().toArray();

        /*System.out.println("########### Valores Proprios");
        listarMatrizDouble(matrizValores, matrizValores.length);
        System.out.println("########### Vetores Proprios");
        listarMatrizDouble(matrizVetores, matrizVetores.length);*/
        int nmaior = maiorCentral(matrizVetores);
        listarLinha(matrizValores, nmaior, out);

    }
    
    public static void listarLinha(double[][] matriz, int n, Formatter out) {
        double num = 0;
        double[] mt = new double[matriz.length];
        for (int i = 0; i < matriz.length; i++) {
            if (matriz[i][n] < 0) {
                matriz[i][n] *= -1;
            }
            num += matriz[i][n];
            mt[i]=matriz[i][n];
        }

        for (int i = 0; i < matriz.length; i++) {
            out.format("%10.3f", matriz[i][n] / num);
        }
        
    }
      
    
       
       
}
