======================== COMO USAR 
========================



1. Coloque os ficheiros que quer analisar na pasta com o .jar.

2. Abra o cmd e use o comando: cd DIRECTORIO , onde DIRECTORIO é o local onde tem o .jar.
Alternativamente se estiver no explorador de ficheiros pode clicar no diretório e escrever "cmd" e clique no Enter.

3. Averigue com o comando PATH se tem o diretório do java (Normalmente C:\Program Files\Java\jdk1.8.0_144\bin) senão faça PATH = directorio_do_java.

4. Use o comando:

	java -jar nome_programa.jar -n rs_nomedarede_nos.csv rs_nomedarede_ramos.csv

para começar o programa em modo menu, ou

	java -jar nome_programa.jar -t - k XXXX rs_nomedarede_nos.csv rs_nomedarede_ramos.csv

caso queira escrever para um ficheiro de texto a informação de um grafo não orientado, em que XXXX será a potencia da matriz de adjacencias, ou,

	java -jar nome_programa.jar -t - k XXXX -d YYYY rs_nomedarede_nos.csv rs_nomedarede_ramos.csv

caso queira escrever para um ficheiro de texto a informação de um grafo não orientado, em que XXXX será o numero de iterações desejadas,
e o YYYY o damping factor (este valor tem de ser entre 0 e 1. O pageRank da Google utiliza 0.85)


==================== Feito por: Alt+F4 
====================

