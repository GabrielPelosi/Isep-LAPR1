======================== COMO USAR 
========================



1. Coloque os ficheiros que quer analisar na pasta com o .jar.

2. Abra o cmd e use o comando: cd DIRECTORIO , onde DIRECTORIO � o local onde tem o .jar.
Alternativamente se estiver no explorador de ficheiros pode clicar no diret�rio e escrever "cmd" e clique no Enter.

3. Averigue com o comando PATH se tem o diret�rio do java (Normalmente C:\Program Files\Java\jdk1.8.0_144\bin) sen�o fa�a PATH = directorio_do_java.

4. Use o comando:

	java -jar nome_programa.jar -n rs_nomedarede_nos.csv rs_nomedarede_ramos.csv

para come�ar o programa em modo menu, ou

	java -jar nome_programa.jar -t - k XXXX rs_nomedarede_nos.csv rs_nomedarede_ramos.csv

caso queira escrever para um ficheiro de texto a informa��o de um grafo n�o orientado, em que XXXX ser� a potencia da matriz de adjacencias, ou,

	java -jar nome_programa.jar -t - k XXXX -d YYYY rs_nomedarede_nos.csv rs_nomedarede_ramos.csv

caso queira escrever para um ficheiro de texto a informa��o de um grafo n�o orientado, em que XXXX ser� o numero de itera��es desejadas,
e o YYYY o damping factor (este valor tem de ser entre 0 e 1. O pageRank da Google utiliza 0.85)


==================== Feito por: Alt+F4 
====================

