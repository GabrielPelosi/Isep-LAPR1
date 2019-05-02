package lapr1_altf4;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Formatter;
import static lapr1_altf4.Unitarios.*;

/**
 * AltD - Push AltT - Pull AltK - Commit
 *
 * @author rickropes, franscisco, gabriel, diogo
 */
public class LAPR1_altf4 {

    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) throws FileNotFoundException {

        String descricaoNos[][] = new String[200][52];
        String descricaoRamos[][] = new String[200][3];
        int nNos = 0, nRamos = 0;
        int op;
        String nome = "";
        String nome2 = "";
        boolean orien = false;

        if (args[0].equals("-n")) {
            if (args.length == 3) {

                nome = args[1];
                nome2 = args[2];
                nNos = Unitarios.lerInfoFicheiroDescricaoNos(nome, descricaoNos, nNos);
                nRamos = Unitarios.lerInfoFicheiroDescricaoRamos(nome2, descricaoRamos, nRamos);
                if (nRamos < 0) {
                    orien = true;
                    nRamos *= -1;
                }
                int[] grauEntrada = new int[nNos];
                int[] graus1 = new int[nNos];
                int[][] matrizAdj = new int[nNos][nNos];
                double[] matrizCent = new double[nNos];
                Unitarios.construirMatrizAdj(matrizAdj, descricaoNos, descricaoRamos, nNos, nRamos, orien);
                int[] graus = new int[nNos];
                Unitarios.grauNo(matrizAdj, graus, nNos);
                if (!orien) {
                    do {
                        op = menuPrincipalNonOriented();
                        switch (op) {
                            case 1:
                                System.out.println("Graus dos nós");
                                Unitarios.mostrarGrauNo(graus, descricaoNos);
                                break;
                            case 2:
                                System.out.println("Centralidade da Matriz\n");
                                Unitarios.centralidadeMatriz(matrizAdj, matrizCent, descricaoNos);
                                break;
                            case 3:
                                System.out.println("Valor do grau médio");
                                double graumedio = Unitarios.GrauMedio(matrizAdj, nNos);
                                System.out.printf("%.2f", graumedio);
                                break;
                            case 4:
                                System.out.println("Valor da densidade");
                                double densidade = Unitarios.densidade(matrizAdj, nNos);
                                System.out.printf("%.2f", densidade);
                                break;
                            case 5:
                                System.out.println("Valor das potências da matriz das adjacências\n");
                                System.out.println("Introduza o número da potência a calcular");
                                int k = in.nextInt();
                                Unitarios.produtorio(matrizAdj, nNos, k);
                                break;
                            case 6:
                                Unitarios.listarMatrizIntString(matrizAdj, descricaoNos, nNos);
                                break;
                            case 7:
                                Unitarios.mostrarNos(descricaoNos, nNos, nome);
                                break;
                            case 8:
                                Unitarios.mostrarRamos(descricaoRamos, nRamos, nome2);
                                break;
                            case 0:
                                System.out.println("FIM\nSoftware por Alt+F4\nRicardo Lopes - 1180669\nFrancisco Ferreira - 1180682\nGabriel Pelosi - 1180017\nDiogo Soares - 1180704");
                                break;
                            default:
                                System.out.println("Operação Incorreta.Repita!");
                                break;
                        }
                    } while (op != 0);
                } else {
                    do {
                        op = menuPrincipalOriented();
                        switch (op) {
                            case 1:
                                System.out.println("Graus de entrada dos nós");
                                Unitarios.grauNoEntrada(matrizAdj, grauEntrada, nNos, descricaoNos);
                                break;
                            case 2:
                                System.out.println("Graus de saída dos nós");
                                Unitarios.grauNoSaida(matrizAdj, graus1, nNos, descricaoNos);
                                break;
                            case 3:
                                int iterações = 4;
                                double d = 0.85;
                                do {
                                    System.out.println("Valor do damping factor (entre 0 e 1, Google usa 0,85): ");
                                    d = input.nextDouble();
                                } while (d < 0 || d > 1);

                                char resposta = 'r';
                                do {
                                    System.out.println("Pretende que o Page Rank seja calculado através do método iterativo? (s/n)");
                                    resposta = in.next().charAt(0);
                                } while (resposta != 's' && resposta != 'S' && resposta != 'n' && resposta != 'N');

                                if (resposta == 's' || resposta == 'S') {
                                    do {
                                        System.out.println("Numero de iterações pretendidas: ");
                                        iterações = input.nextInt();
                                    } while (iterações <= 0);
                                    System.out.println("Page Rank Iterativo");
                                    Unitarios.pageRankV(matrizAdj, iterações, d, descricaoNos, true);
                                } else if (resposta == 'n' || resposta == 'N') {
                                    System.out.println("Page Rank Absoluto ");
                                    Unitarios.pageRankV(matrizAdj, iterações, d, descricaoNos, false);
                                }
                                break;
                            case 4:
                                Unitarios.listarMatrizIntString(matrizAdj, descricaoNos, nNos);
                                break;
                            case 5:
                                Unitarios.mostrarNos(descricaoNos, nNos, nome);
                                break;
                            case 6:
                                Unitarios.mostrarRamos(descricaoRamos, nRamos, nome2);
                                break;
                            case 0:
                                System.out.println("FIM\nSoftware por Alt+F4\nRicardo Lopes - 1180669\nFrancisco Ferreira - 1180682\nGabriel Pelosi - 1180017\nDiogo Soares - 1180704");
                                break;
                            default:
                                System.out.println("Opção incorreta.Repita!");

                        }
                    } while (op != 0);

                }
            } else {
                System.out.println("Numero de argumentos incorrectos.");
            }
        } else if (args[0].equals("-t") && args[1].equals("-k")) {

            if (args.length == 5) {
                orien = false;
            } else if (args.length == 7) {
                orien = true;
            } else {
                System.out.println("Numero de argumentos incorrectos.");
            }

            if (!orien) {
                String k1 = args[2];
                nome = args[3];
                nome2 = args[4];
                nNos = Unitarios.lerInfoFicheiroDescricaoNos(nome, descricaoNos, nNos);
                nRamos = Unitarios.lerInfoFicheiroDescricaoRamos(nome2, descricaoRamos, nRamos);
                if (nRamos < 0) {
                    System.out.println("Sem argumento '-d' num grafo orientado.");
                    System.exit(0);
                }

                int[][] matrizAdj = new int[nNos][nNos];
                double[] matrizCent = new double[nNos];
                Unitarios.construirMatrizAdj(matrizAdj, descricaoNos, descricaoRamos, nNos, nRamos, orien);
                int[] graus = new int[nNos];
                Unitarios.grauNo(matrizAdj, graus, nNos);

                String[] infoNomeFich = nome.split(SEPARADOR_EXTENSAO_FICHEIRO);
                String[] temp = infoNomeFich[0].split(SEPARADOR_NOME_FICHEIRO);
                String redesocial = temp[1].trim();
                LocalDate date = LocalDate.now();
                Formatter fOutput = new Formatter(new File("out_" + redesocial + "_" + date + ".txt"));
                fOutput.format("ESTUDO DA REDE SOCIAL");
                fOutput.format("\r\n");
                fOutput.format("Medidas ao Nível dos Nós");
                fOutput.format("\r\n");
                fOutput.format("Grau de todos os nós da rede social----------");
                fOutput.format("\r\n");
                novoFicheiro.mostrarGrauNo(fOutput, graus, descricaoNos);
                fOutput.format("Centralidade de Vetor Próprio----------");
                fOutput.format("\r\n");
                novoFicheiro.centralidadeMatriz(fOutput, matrizAdj, matrizCent);
                fOutput.format("\r\n");
                fOutput.format("Medidas ao Nível da Rede");
                fOutput.format("\r\n");
                fOutput.format("Grau médio----------");
                fOutput.format("\r\n");
                double graumedio = GrauMedio(matrizAdj, nNos);
                fOutput.format("%.2f", graumedio);
                fOutput.format("\r\n");
                fOutput.format("Densidade----------");
                fOutput.format("\r\n");
                double densidade = Unitarios.densidade(matrizAdj, nNos);
                fOutput.format("%.2f", densidade);
                fOutput.format("\r\n");
                fOutput.format("Potências da Matriz de Adjacências----------");
                fOutput.format("\r\n");
                int k2 = Integer.parseInt(k1);
                novoFicheiro.produtorio(fOutput, matrizAdj, nNos, k2);
                fOutput.format("Apresentação dos nós e ramos da rede social " + redesocial);
                fOutput.format("\r\n");
                novoFicheiro.mostrarNos(descricaoNos, nNos, fOutput, nome);
                novoFicheiro.mostrarRamos(descricaoRamos, fOutput, nRamos, nome2);
                fOutput.format("%n%n Software por Alt+F4 %n Ricardo Lopes - 1180669 %n Francisco Ferreira - 1180682 %n Gabriel Pelosi - 1180017 %n Diogo Soares - 1180704%n");
                fOutput.close();
                System.out.println("Ficheiro criado com sucesso");
            } else {
                String k1 = args[2];
                int k2 = Integer.parseInt(k1);
                nome = args[5];
                nome2 = args[6];
                String d1 = args[4].trim();
                d1 = d1.replaceAll(",",".");
                double d = Double.parseDouble(d1);
                if(d < 0 || d > 1){
                    System.out.println("valor de -d incorrectto. Tem de estar entre 0 e 1");
                    System.exit(0);
                }
                nNos = Unitarios.lerInfoFicheiroDescricaoNos(nome, descricaoNos, nNos);
                nRamos = Unitarios.lerInfoFicheiroDescricaoRamos(nome2, descricaoRamos, nRamos);
                if (nRamos < 0) {
                    nRamos *= -1;
                }else{
                    System.out.println("'-d' em grafo não orientado.");
                    System.exit(0);
                }
                int[][] matrizAdj = new int[nNos][nNos];
                double[] matrizCent = new double[nNos];
                int[] grauEntrada = new int[nNos];
                int[] graus1 = new int[nNos];
                Unitarios.construirMatrizAdj(matrizAdj, descricaoNos, descricaoRamos, nNos, nRamos, orien);
                int[] graus = new int[nNos];
                Unitarios.grauNo(matrizAdj, graus, nNos);

                String[] infoNomeFich = nome.split(SEPARADOR_EXTENSAO_FICHEIRO);
                String[] temp = infoNomeFich[0].split(SEPARADOR_NOME_FICHEIRO);
                String redesocial = temp[1].trim();
                LocalDate date = LocalDate.now();
                Formatter fOutput = new Formatter(new File("out_" + redesocial + "_" + date + ".txt"));
                fOutput.format("ESTUDO DA REDE SOCIAL");
                fOutput.format("\r\n");
                fOutput.format("MEDIDAS AO NÍVEL DOS NÓS");
                fOutput.format("\r\n");
                fOutput.format("Graus de entrada dos nós");
                fOutput.format("\r\n");
                novoFicheiro.grauNoEntrada(fOutput, matrizAdj, grauEntrada, nNos);
                fOutput.format("\r\n");
                fOutput.format("Graus de saída dos nós");
                fOutput.format("\r\n");
                novoFicheiro.grauNoSaida(fOutput, matrizAdj, graus1, nNos);
                fOutput.format("\r\n");
                fOutput.format("Page Rank");
                fOutput.format("\r\n");
                novoFicheiro.pageRank(fOutput, matrizAdj, k2, d);
                fOutput.format("\r\n");
                fOutput.format("%n%n Software por Alt+F4 %n Ricardo Lopes - 1180669 %n Francisco Ferreira - 1180682 %n Gabriel Pelosi - 1180017 %n Diogo Soares - 1180704%n");
                fOutput.close();
                System.out.println("Ficheiro criado com sucesso");
            }
        } else {
            System.out.println("Erro de Sintax.");
        }
    }

    private static int menuPrincipalNonOriented() {
        String cabeçalho = "Escolha uma opção"
                + "\nREDES/GRAFOS NÃO ORIENTADAS"
                + "\nMEDIDAS AO NÍVEL DOS NÓS ----------"
                + "\n1-Calcular grau dos nós"
                + "\n2-Calcular centralidade de vetor próprio"
                + "\nMEDIDAS AO NÍVEL DA REDE ----------"
                + "\n3-Calcular grau médio"
                + "\n4-Calcular densidade"
                + "\n5-Calcular potências da matriz de adjacências"
                + "\n6-Mostrar matriz das adjacências"
                + "\nLISTAGENS ----------"
                + "\n7-Apresentar os nós da rede"
                + "\n8-Apresentar os ramos da rede"
                + "\n-TERMINAR ----------"
                + "\n0-Terminar";
        System.out.printf("%n%s%n", cabeçalho);
        int op = in.nextInt();
        return op;
    }

    private static int menuPrincipalOriented() {
        String cabeçalho = "Escolha uma opção"
                + "\nREDES/GRAFOS ORIENTADAS----------"
                + "\nMEDIDAS AO NÍVEL DOS NÓS----------"
                + "\n1-Graus de entrada de um nó"
                + "\n2-Graus de saída de um nó"
                + "\n3-PageRank"
                + "\nLISTAGENS ----------"
                + "\n4-Mostrar Matriz Adjacente"
                + "\n5-Listar Nós"
                + "\n6-Listar Ramos"
                + "\n-TERMINAR ----------"
                + "\n0-Terminar";
        System.out.printf("%n%s%n", cabeçalho);
        int op1 = in.nextInt();
        return op1;
    }
}
