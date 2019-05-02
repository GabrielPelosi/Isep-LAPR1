package teste.matriz.advertencias;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TesteMatrizAdvertencias {
    public final static String SEPARADOR_EXTENSAO_FICHEIRO = "\\.";
    public final static String SEPARADOR_NOME_FICHEIRO = "_";
    public final static int N_CAMPOS_NOME_FICHEIRO = 3;
    public final static String SEPARADOR_DADOS_FICHEIRO = ",";
    public final static int N_COLUNAS_RAMOS = 3;
    public final static int N_CAMPOS_INFO_DESCRICAONOS = 4;
    public final static int N_COLUNAS_NOS = 4;
    
    public static void main(String[] args) {
       
    }
    
    //Método de leitura da informacao presente no ficheiro da descricao dos nos
    public static int lerInfoFicheiroDescricaoNos(String nomeFich, String[][] DescricaoNos, int nNos) throws FileNotFoundException {
        String[] infoNomeFich = nomeFich.split(SEPARADOR_EXTENSAO_FICHEIRO);
        String[] temp = infoNomeFich[0].split(SEPARADOR_NOME_FICHEIRO);
        //infoNomeFich = infoNomeFich[0].split(SEPARADOR_NOME_FICHEIRO);
        String redesocial = temp[1].trim();

        Scanner fInput = new Scanner(new File(nomeFich));
        while (fInput.hasNext()) {
            String linha = fInput.nextLine();
            //verifica se a linha está em branco
            if ((linha.trim()).length() > 0) {
                nNos = guardarDescricao(linha, DescricaoNos, nNos, N_CAMPOS_INFO_DESCRICAONOS);

            }
        }
        fInput.close();
        return nNos;

    }

    //Método para guardar os dados da descricao dos nos em memoria interna
    public static int guardarDescricao(String linha, String[][] Descricao, int nNos, int campos) {
        String[] temp = linha.split(SEPARADOR_DADOS_FICHEIRO);
        if (temp.length == campos) {
            for (int i = 0; i < Descricao[nNos].length; i++) {
                Descricao[nNos][i] = temp[i].trim();
            }
            nNos++;
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
        while (fInput.hasNext()) {
            String linha = fInput.nextLine();
            //verifica se a linha está em branco
            if ((linha.trim()).length() > 0) {
                nRamos = guardarDescricao(linha, DescricaoRamos, nRamos, N_COLUNAS_RAMOS);

            }
        }
        fInput.close();

        return nRamos;

    }

    /* Matriz Adjacentes */
    public static void construirMatrizAdj(int[][] matrizAdj, String[][] descricaoNos, String[][] descricaoRamos, int nNos, int nRamos) {

        String nome;
        for (int i = 0; i < nNos; i++) {
            nome = descricaoNos[i][0];
            procurarNome(matrizAdj, descricaoRamos, descricaoNos, nome, i, nNos, nRamos);
        }
        return;
    }
    
    public static void procurarNome(int[][] matrizAdj, String[][] descricaoRamos, String[][] descricaoNos, String nome, int j, int nNos, int nRamos) {

        String no;
        int valor;

        for (int i = 0; i < nRamos; i++) {
            if (nome.equals(descricaoRamos[i][0])) {
                no = descricaoRamos[i][1];
                valor = Integer.parseInt(descricaoRamos[i][2].trim());
                colocarValor(matrizAdj, descricaoNos, no, valor, j, nNos);
            }
        }

        return;
    }
    
    public static void colocarValor(int[][] matrizAdj, String[][] descricaoNos, String no, int valor, int j, int nNos) {

        for (int i = 0; i < nNos; i++) {
            if (no.equals(descricaoNos[i][0])) {
                matrizAdj[j][i] = valor;
                return;
            }
        }

    }

}
