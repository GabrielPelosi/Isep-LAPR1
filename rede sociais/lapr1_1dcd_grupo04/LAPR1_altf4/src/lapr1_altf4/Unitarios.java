package lapr1_altf4;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Formatter;
import java.util.Scanner;
import org.la4j.Matrix;
import org.la4j.matrix.DenseMatrix;
import org.la4j.matrix.dense.Basic2DMatrix;
import org.la4j.decomposition.EigenDecompositor;

public class Unitarios {

    public final static String SEPARADOR_EXTENSAO_FICHEIRO = "\\.";
    public final static String SEPARADOR_NOME_FICHEIRO = "_";
    public final static int N_CAMPOS_NOME_FICHEIRO = 3;
    public final static String SEPARADOR_DADOS_FICHEIRO = ",";
    public final static int N_COLUNAS_RAMOS = 3;
    public final static int N_CAMPOS_INFO_DESCRICAONOS = 5;
    public final static int N_COLUNAS_NOS = 5;
    public final static int N_CAMPOS_INFO_DESCRICAONOS_ORT = 5;
    public final static String SEPARADOR_ORIENTACAO = ":";
    static Scanner input = new Scanner(System.in);

    //Método de leitura da informacao presente no ficheiro da descricao dos nos
    public static int lerInfoFicheiroDescricaoNos(String nomeFich, String[][] DescricaoNos, int nNos) throws FileNotFoundException {
        String[] infoNomeFich = nomeFich.split(SEPARADOR_EXTENSAO_FICHEIRO);
        String[] temp = infoNomeFich[0].split(SEPARADOR_NOME_FICHEIRO);
        //infoNomeFich = infoNomeFich[0].split(SEPARADOR_NOME_FICHEIRO);

        Scanner fInput = new Scanner(new File(nomeFich));
        fInput.nextLine();
        while (fInput.hasNext()) {
            String linha = fInput.nextLine();
            //verifica se a linha está em branco
            if ((linha.trim()).length() > 0) {
                nNos = guardarDescricao(linha, DescricaoNos, nNos, N_CAMPOS_INFO_DESCRICAONOS, nomeFich);

            }
        }
        fInput.close();
        return nNos;

    }

    //Método para guardar os dados da descricao dos nos em memoria interna
    public static int guardarDescricao(String linha, String[][] Descricao, int nNos, int campos, String nomeFich) throws FileNotFoundException {
        String[] temp = linha.split(SEPARADOR_DADOS_FICHEIRO);
        int op = 0;
        if (temp.length == campos) {
            if (campos != 3) {
                op = verExistencia(temp, Descricao, nNos, nomeFich);
            }
            if (op == 0) {
                for (int i = 0; i < campos; i++) {
                    Descricao[nNos][i] = temp[i].trim();
                }
                nNos++;
            }
        } else {
            erroSair(temp, nomeFich, 0);
        }
        return nNos;
    }

    //método de leitura da informacao presente no ficheiro da descricao dos ramos
    public static int lerInfoFicheiroDescricaoRamos(String nomeFich, String[][] DescricaoRamos, int nRamos) throws FileNotFoundException {
        String[] infoNomeFich = nomeFich.split(SEPARADOR_EXTENSAO_FICHEIRO);
        String[] temp = infoNomeFich[0].split(SEPARADOR_NOME_FICHEIRO);
        //infoNomeFich = infoNomeFich[0].split(SEPARADOR_NOME_FICHEIRO);
        String redesocial = temp[1].trim();
        Scanner fInput = new Scanner(new File(nomeFich));
        int or = 0;
        int num = 1;
        while (fInput.hasNext()) {
            String linha = fInput.nextLine();
            //verifica se a linha está em branco
            if ((linha.trim()).length() > 0 && num >= 4) {
                nRamos = guardarDescricao(linha, DescricaoRamos, nRamos, N_COLUNAS_RAMOS, nomeFich);
            }

            if (num == 1) {
                String[] temp2 = linha.split(SEPARADOR_ORIENTACAO);

                if (temp2[1].trim().equals("nonoriented")) {
                    or = 1;
                } else if (temp2[1].trim().equals("oriented")) {
                    or = -1;
                } else {
                    erroSair(temp2, nomeFich, 0);
                }
            }

            num++;
        }
        fInput.close();

        return (nRamos * or);

    }

    /* Matriz Adjacentes */
    public static void construirMatrizAdj(int[][] matrizAdj, String[][] descricaoNos, String[][] descricaoRamos, int nNos, int nRamos, boolean orien) {

        String nome;
        for (int i = 0; i < nNos; i++) {
            nome = descricaoNos[i][0];
            procurarNome(matrizAdj, descricaoRamos, descricaoNos, nome, i, nNos, nRamos, orien);
        }
        return;
    }

    public static void procurarNome(int[][] matrizAdj, String[][] descricaoRamos, String[][] descricaoNos, String nome, int j, int nNos, int nRamos, boolean orien) {

        String no;
        int valor;

        for (int i = 0; i < nRamos; i++) {
            if (nome.equals(descricaoRamos[i][0])) {
                no = descricaoRamos[i][1];
                if (!no.equals(nome)) {
                    valor = Integer.parseInt(descricaoRamos[i][2].trim());
                    colocarValor(matrizAdj, descricaoNos, no, valor, j, nNos, orien);
                }
            }
        }

        return;
    }

    public static void colocarValor(int[][] matrizAdj, String[][] descricaoNos, String no, int valor, int j, int nNos, boolean orien) {

        for (int i = 1; i < nNos; i++) {
            if (no.equals(descricaoNos[i][0])) {
                matrizAdj[j][i] = valor;
                if (orien == false) {
                    matrizAdj[i][j] = valor;
                }
                return;
            }
        }
        return;

    }

    /* Grau os nós */
    public static void grauNo(int[][] matrizAdj, int[] graus, int nNos) {
        for (int i = 0; i < nNos; i++) {
            graus[i] = calculoGrauNo(matrizAdj, i, nNos);
        }
    }

    /* Grau nó */
    public static int calculoGrauNo(int[][] matrizAdj, int num, int nNos) {
        int grau = 0;
        for (int i = 0; i < nNos; i++) {
            grau += matrizAdj[num][i];

        }
        return grau;
    }

    public static void mostrarGrauNo(int[] graus, String[][] descricaoNos) {
        for (int i = 0; i < graus.length; i++) {
            System.out.println(descricaoNos[i][0] + ",tem grau: " + graus[i]);
        }

        double[] grauDouble = new double[graus.length];
        matrizSIntDouble(graus, grauDouble);
        getPage(grauDouble, descricaoNos);
    }

    public static double GrauMedio(int[][] matrizAdj, int nNos) {
        double media;
        double conta = 0;
        for (int i = 0; i < nNos; i++) {
            int num = grauMedioNo(matrizAdj, i, nNos);
            conta = conta + num;
        }
        media = conta / nNos;
        return media;
    }

    /* Grau nó para calcular a sua média */
    public static int grauMedioNo(int[][] matrizAdj, int cont, int nNos) {
        int num = 0;
        for (int i = 0; i < nNos; i++) {
            num += matrizAdj[cont][i];
        }
        return num;
    }

    public static double densidade(int[][] matrizAdj, int nNos) {
        double densidade;
        double soma = 0;
        for (int i = 0; i < nNos; i++) {
            for (int j = 0; j < nNos; j++) {
                soma = soma + matrizAdj[i][j];
            }
        }
        densidade = (soma / ((nNos * nNos) - nNos));
        return densidade;
    }

    public static int pesquisarId(String[][] descricaoRamos, String no) {
        for (int i = 0; i < descricaoRamos.length; i++) {
            if ((descricaoRamos[i][0]).equals(no)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * * LISTAR **
     */
    public static void listarMatriz(String[][] matriz, int num) {
        for (int i = 0; i < num; i++) {
            for (int k = 0; k < matriz[i].length; k++) {
                System.out.printf("%10s", matriz[i][k]);
            }
            System.out.println("\n");
        }
    }

    public static void listarMatrizInt(int[][] matriz, int num) {
        for (int i = 0; i < num; i++) {
            for (int k = 0; k < num; k++) {
                System.out.printf("%3s", matriz[i][k]);
            }
            System.out.println("\n");
        }

    }

    public static void listarMatrizGrauEntrada(int[] matriz, int num) {
        for (int i = 0; i < num; i++) {
            System.out.println("O valor do grau de entrada na posição " + (i + 1) + " é " + matriz[i] + ".");
            System.out.println("");
        }
    }

    public static void listarMatrizGrauSaida(int[] matriz, int num) {
        for (int i = 0; i < num; i++) {
            System.out.println("O valor do grau de saida na posição " + (i + 1) + " é " + matriz[i] + ".");
            System.out.println("");
        }
    }

    public static void listarMatrizDouble(double[][] matriz, int num) {
        for (int i = 0; i < num; i++) {
            for (int k = 0; k < num; k++) {
                System.out.printf("%10.2f", matriz[i][k]);
            }
            System.out.println("\n");
        }
    }

    public static void listarMatrizIntString(int[][] matriz, String[][] nomes, int num) {
        System.out.printf("%8s", "   ");
        for (int i = 0; i < num; i++) {
            System.out.printf("%8s", nomes[i][0]);
        }
        System.out.println("\n");
        for (int i = 0; i < num; i++) {
            System.out.printf("%8s", nomes[i][0]);
            for (int k = 0; k < num; k++) {
                System.out.printf("%8s", matriz[i][k]);
            }
            System.out.println("\n");
        }
    }
    //Mostrar informação sobre os nós da rede social carregada em meméria sob a forma de matriz

    public static void mostrarNos(String[][] descricaoNos, int nNos, String nomeFich) {
        String[] infoNomeFich = nomeFich.split(SEPARADOR_EXTENSAO_FICHEIRO);
        String[] temp = infoNomeFich[0].split(SEPARADOR_NOME_FICHEIRO);
        String redesocial = temp[1].trim();
        System.out.println("Nós da Rede Social " + redesocial + ":");
        System.out.println("ID" + " | " + "MEDIA" + " | " + "MEDIA.TYPE" + " | " + "TYPE.LABEL" + " | " + "webURL");
        for (int i = 0; i < nNos; i++) {
            for (int j = 0; j < N_COLUNAS_NOS; j++) {
                System.out.printf(descricaoNos[i][j] + " | ");
            }
            System.out.println("\n");
        }
    }
    //Mostrar informação sobre os ramos da rede social carregada em meméria sob a forma de matriz

    public static void mostrarRamos(String[][] descricaoRamos, int nRamos, String nomeFich) {
        String[] infoNomeFich = nomeFich.split(SEPARADOR_EXTENSAO_FICHEIRO);
        String[] temp = infoNomeFich[0].split(SEPARADOR_NOME_FICHEIRO);
        String redesocial = temp[1].trim();
        System.out.println("Ramos da Rede Social " + redesocial + ":");
        System.out.println("FROM" + " | " + "TO" + " | " + "WEIGHT");
        for (int i = 0; i < nRamos; i++) {
            for (int j = 0; j < N_COLUNAS_RAMOS; j++) {
                System.out.printf(descricaoRamos[i][j] + " | ");
            }
            System.out.println("\n");
        }
    }

    //Método de cálculo de cálculo das Potências da Matriz de Adjacências
    public static void produtorio(int[][] a, int nNos, int k) {
        int[][] resultado = new int[nNos][nNos];
        int[][] a2 = new int[nNos][nNos];
        int[][] a3 = new int[nNos][nNos];
        int cont = 1;

        for (int i = 0; i < nNos; i++) {
            for (int j = 0; j < nNos; j++) {
                a2[i][j] = a[i][j];
            }
        }

        for (int i = 0; i < nNos; i++) {
            for (int j = 0; j < nNos; j++) {
                a3[i][j] = a[i][j];
            }
        }

        System.out.println("Potência da Matriz de Adjacências (k =" + cont + ")");
        for (int i = 0; i < nNos; i++) {
            for (int j = 0; j < nNos; j++) {
                System.out.printf("%5s", a3[i][j]);

            }
            System.out.println(" ");
        }
        System.out.println("\n");

        for (int m = 1; m < k; m++) {
            System.out.println("Potência da Matriz de Adjacências (k =" + m + ")");
            System.out.println("\n");

            for (int i = 0; i < nNos; i++) {
                for (int j = 0; j < nNos; j++) {
                    for (int l = 0; l < nNos; l++) {
                        resultado[i][j] += a2[i][l] * a3[l][j];
                    }
                }
            }
            for (int i = 0; i < nNos; i++) {
                for (int j = 0; j < nNos; j++) {
                    a3[i][j] = resultado[i][j];
                }
            }
            for (int i = 0; i < nNos; i++) {
                for (int j = 0; j < nNos; j++) {
                    resultado[i][j] = 0;

                }
            }
            for (int i = 0; i < nNos; i++) {
                for (int j = 0; j < nNos; j++) {
                    System.out.printf("%5s", a3[i][j]);

                }
                System.out.println(" ");
            }
            System.out.println("\n");
        }

    }

    /* Centralidade */
    public static void centralidadeMatriz(int[][] matrizAdj, double[] matrizCent, String[][] descricaoNos) {
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

        listarVetorDouble(matrizCent, matrizCent.length);

        getPage(matrizCent, descricaoNos);
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

    public static void listarVetorDouble(double[] matriz, int num) {
        for (int i = 0; i < num; i++) {
            System.out.printf("%17.3f%n", matriz[i]);
        }
        System.out.println();
    }

    /* Erros */
    public static void erroSair(String[] linhas, String nomeFich, int type) throws FileNotFoundException {
        String date = dateTime();
        String linha = "";
        for (int i = 0; i < linhas.length; i++) {
            linha += linhas[i];
            if (i < linhas.length - 1) {
                linha += SEPARADOR_DADOS_FICHEIRO;
            }
        }
        if (type == 0) {
            Formatter outputLogErros = new Formatter(new File(date + "-ERRO.txt"));
            outputLogErros.format("%s%s%n%n%s---%s%n", "Erro no ficheiro: ", nomeFich, linha, "numero de campos irregular.");
            outputLogErros.close();
        } else {
            Formatter outputLogErros = new Formatter(new File(date + "-REPETIDO.txt"));
            outputLogErros.format("%s%s%n%n%s---%s%n", "Erro no ficheiro: ", nomeFich, linha, "id já existente");
            outputLogErros.close();
        }
        System.exit(0);
    }

    public static String dateTime() {
        LocalDateTime dateb = LocalDateTime.now();
        String dates = "";
        dates += dateb;
        String datev = dates.replace("T", "-");
        String dater = datev.replace(":", "-");
        String[] date = dater.split("\\.");
        return date[0];
    }

    public static int verExistencia(String[] linha, String[][] matriz, int n, String nomeFich) throws FileNotFoundException {
        for (int i = 0; i < n; i++) {
            if (matriz[i][0].equals(linha[0])) {
                erroRepetido(linha, nomeFich);
                return -1;
            }

        }
        return 0;
    }

    public static void erroRepetido(String[] linha, String nomeFich) throws FileNotFoundException {
        System.out.println("Já existe uma pessoa com id = " + linha[0]);
        int op = 0;
        do {
            System.out.println("Deseja prosseguir?\n(Este id será passado à frente)\n1-Sim\n2-Não");
            op = input.nextInt();
            switch (op) {
                case 1:
                    return;
                case 2:
                    erroSair(linha, nomeFich, 1);
                    break;
                default:
                    System.out.println("Opção incorrecta.");
                    break;
            }
        } while (op != 1);

    }

    public static int lerInfoDescricaoNosOrientado(String nomeFich, String[][] NosOrientado, int nNosOrt) throws FileNotFoundException {
        String[] infoNomeFich = nomeFich.split(SEPARADOR_EXTENSAO_FICHEIRO);
        String[] temp = infoNomeFich[0].split(SEPARADOR_NOME_FICHEIRO);
        //String redesocial = temp[1].trim();
        Scanner fInput = new Scanner(new File(nomeFich));
        while (fInput.hasNext()) {
            String linha = fInput.nextLine();
            //verifica se a linha está em branco
            if ((linha.trim()).length() > 0) {
                nNosOrt = guardarDescricaoOrt(linha, NosOrientado, nNosOrt, N_CAMPOS_INFO_DESCRICAONOS_ORT);
            }
        }
        fInput.close();
        return nNosOrt;

    }

    public static int guardarDescricaoOrt(String linha, String[][] Descricao, int n, int campos) {
        String[] temp = linha.split(SEPARADOR_DADOS_FICHEIRO);
        if (temp.length == campos) {
            for (int i = 0; i < Descricao[n].length; i++) {
                Descricao[n][i] = temp[i].trim();
            }
            n++;
        }
        return n;
    }

    public static int lerInfoFicheiroDescricaoRamosOrientado(String nomeFich, String[][] RamosOrientado, int nRamosOrt) throws FileNotFoundException {
        String[] infoNomeFich = nomeFich.split(SEPARADOR_EXTENSAO_FICHEIRO);
        String[] temp = infoNomeFich[0].split(SEPARADOR_NOME_FICHEIRO);
        //String redesocial = temp[1].trim();
        Scanner fInput = new Scanner(new File(nomeFich));
        while (fInput.hasNext()) {
            String linha = fInput.nextLine();
            //verifica se a linha está em branco
            if ((linha.trim()).length() > 0) {
                nRamosOrt = guardarDescricaoOrt(linha, RamosOrientado, nRamosOrt, N_COLUNAS_RAMOS);
            }
        }
        fInput.close();

        return nRamosOrt;
    }

    public static void grauNoSaida(int[][] matrizAdj, int[] graus, int nNos, String[][] descricaoNos) {
        for (int i = 0; i < nNos; i++) {
            graus[i] = calculoGrauNoSaida(matrizAdj, i, nNos);
        }
        listarMatrizGrauSaida(graus, nNos);

        double[] grauDouble = new double[graus.length];
        matrizSIntDouble(graus, grauDouble);
        getPage(grauDouble, descricaoNos);
    }

    public static int calculoGrauNoSaida(int[][] matrizAdj, int num, int nNos) {
        int grau = 0;
        for (int i = 0; i < nNos; i++) {
            grau += matrizAdj[i][num];

        }
        return grau;
    }

    public static void grauNoEntrada(int[][] matrizAdj, int[] grauEntrada, int nNos, String[][] descricaoNos) {
        for (int i = 0; i < nNos; i++) {
            grauEntrada[i] = calcularGrauNoEntrada(matrizAdj, i, nNos);
        }
        listarMatrizGrauEntrada(grauEntrada, nNos);

        double[] grauDouble = new double[grauEntrada.length];
        matrizSIntDouble(grauEntrada, grauDouble);
        getPage(grauDouble, descricaoNos);
    }

    public static int calcularGrauNoEntrada(int[][] matrizAdj, int num, int nNos) {
        int grau = 0;
        for (int i = 0; i < nNos; i++) {
            grau = grau + matrizAdj[num][i];

        }
        return grau;
    }
/*
    public static double pageRank(int[][] matrizAdj) {
        double[][] matrizDouble = new double[matrizAdj.length][matrizAdj.length];

        matrizIntDouble(matrizAdj, matrizDouble);

        Matrix a = new Basic2DMatrix(matrizDouble);

        EigenDecompositor eigenD = new EigenDecompositor(a);

        Matrix[] mattD = eigenD.decompose();

        //Valores Proprios
        double matrizValores[][] = mattD[0].toDenseMatrix().toArray();

        listarMatrizDouble(matrizValores, matrizValores.length);
        System.out.println("\n\n");
        //Vetores Proprios
        double matrizVetores[][] = mattD[1].toDenseMatrix().toArray();
        listarMatrizDouble(matrizVetores, matrizValores.length);

        int indiceMValor = maiorValorProprio(matrizValores);
        double vProprio = matrizVetores[indiceMValor][indiceMValor];
        System.out.println(vProprio);
        return vProprio;
    }*/
/*
    public static int maiorValorProprio(double[][] m) {
        double maior = 0;
        int n = 0;
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                if (i == 0) {
                    maior = m[i][j];
                } else if (m[i][j] > maior) {
                    maior = m[i][j];
                    n = i;
                }
            }
        }
        return n;
    }*/

    /* Page Rank */
    public static void matrixM(double[][] matrix, double[][] matrixM, double d) {
        double num = ((1 - d) / (double) matrix.length);

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrixM[i][j] = (matrix[i][j] * d) + num;
            }
        }
    }

    /*public static void pageRank(int[][] matrizAdj, int vezes, String[][] descricaoNos) {
        double[][] matrizDouble = new double[matrizAdj.length][matrizAdj.length];
        matrizIntDouble(matrizAdj, matrizDouble);

        vezes++;
        double[][] matrix = new double[matrizDouble.length][matrizDouble.length];
        int[] conexoes = new int[matrizDouble.length];

        matrizConexoes(conexoes, matrizDouble);

        matrixSet(matrix, conexoes, matrizDouble);
        /*
        listarMatrizDouble(matrix, matrix.length);
        listarMatrizS(conexoes, conexoes.length);
        pageRankMath(matrix, vezes, descricaoNos);

    }*/
    public static void pageRankV(int[][] matrizAdj, int vezes, double d, String[][] descricaoNos, boolean it) {
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
        //System.out.println("Matrix M");
        //listarMatrizDouble(matrixM, matrixM.length);
        if (it) {
            pageRankMath(matrixM, vezes, descricaoNos);
        } else {
            pageRankMathV(matrixM, descricaoNos);
        }
    }

    public static void pageRankMath(double[][] matrix, int vezes, String[][] descricaoNos) {
        double[] x = new double[matrix.length];
        double[] y = new double[x.length];
        for (int i = 0; i < matrix.length; i++) {
            x[i] = (double) 1 / matrix.length;
        }

        for (int i = 0; i < vezes; i++) {
            System.out.println("Iteração " + i);
            listarPageRank(x);
            System.out.println();

            for (int j = 0; j < matrix.length; j++) {
                for (int k = 0; k < matrix.length; k++) {
                    y[j] += matrix[j][k] * x[k];
                }

            }
            colocarDoubles(x, y);
        }
        getPage(x, descricaoNos);

    }

    public static void listarPageRank(double[] x) {
        for (int i = 0; i < x.length; i++) {
            System.out.printf("%10.3f", x[i]);
        }
        System.out.println();
    }

    public static void colocarDoubles(double[] x, double[] y) {
        for (int i = 0; i < x.length; i++) {
            x[i] = y[i];
            y[i] = 0;
        }
    }

    public static void matrixSet(double[][] matrix, int[] conexoes, double[][] matrizDouble) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (conexoes[i] != 0) {
                    if (matrizDouble[i][j] != 0) {
                        matrix[j][i] = 1 / (double) conexoes[i];
                    }
                } else {
                    matrix[j][i] = 1 / (double) matrix.length;
                }
            }
        }
    }

    public static void matrizConexoes(int[] conexoes, double[][] matrizDouble) {
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

    /* Cenas de outras coisas so usadas para listar e centralidade e essas merdas *///////////////// 
    public static void pageRankMathV(double[][] matrizDouble, String[][] descricaoNos) {

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
        listarLinha(matrizValores, nmaior, descricaoNos);

    }

    public static void listarLinha(double[][] matriz, int n, String[][] descricaoNos) {
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
            System.out.printf("%10.3f", matriz[i][n] / num);
        }
        
        getPage(mt, descricaoNos);
    }

    public static void getPage(double[] x, String[][] descricaoNos) {
        int n = 0;
        double maior = 0;

        for (int i = 0; i < x.length; i++) {
            if (i == 0 || x[i] > maior) {
                maior = x[i];
                n = i;
            }
        }

        String URL = descricaoNos[n][4];
        mostrarPaginaWeb(URL);

    }

    public static void mostrarPaginaWeb(String URL) {
        try {
            Desktop desktop = java.awt.Desktop.getDesktop();
            URI oURL = new URI(URL);
            desktop.browse(oURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void matrizSIntDouble(int[] mati, double[] matd) {
        for (int i = 0; i < mati.length; i++) {
            matd[i] = mati[i];
        }
    }

    public static void listarMatrizS(double[] matriz, int num) {
        for (int i = 0; i < num; i++) {
            System.out.println(matriz[i]);
        }
    }

}
