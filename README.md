# TicTacToe
 
Jogo do galo concorrente em RMI.

Algoritmo de funcionamento:
1: peer1 manda invite ao peer2. isto acontece chamando o método na interface remota na máquina do peer2
2:peer2 aceita ou recusa, enviando a resposta ao peer1
3:se aceitou, peer1 tem de escolher o símbolo e o jogo inicia
4:efetua jogada e envia ao peer2 que irá efetuar e responder ao peer1 através da call do método da interface
5:processo repete-se até ao fim
6:no fim, para começar outro jogo deve-se fechar o programa. isto é necessário, pois a única maneira de voltar ao menu inicial em ambas as situações seria com threads e um peer à escuta de uma mensagem do estilo "o jogo acabou"