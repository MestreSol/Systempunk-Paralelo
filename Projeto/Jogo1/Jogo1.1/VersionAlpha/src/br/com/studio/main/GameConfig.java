package br.com.studio.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import javax.swing.JOptionPane;

public class GameConfig {

	// Define o tamanho da tela inicial, NOTA: pode ser colocado em full screen
	// atraves de outros metodos
	public static final int WIDTH = 240;
	public static final int HEIGHT = 160;

	// Scale ira multiplicar todos os sprites e tiles para que os mesmos nao fiquem
	// muito grandes ou muito minusculos
	public static final int SCALE = 3;

	// Variaveis globlais de validação
	public static boolean saveExists = false;
	public static boolean saveGame = false;
	public static boolean isRunning = false;

	// Define de forma globla o estado atual do jogo, como padrão o jogo inicia como
	// Menu para começar sempre no menu de start;
	public static String GameState = "Menu";

	// Valor atual do nivel, sendo 0 default e os demais podem ser utilizados para
	// mudar os tiles ou inimigos que estao sendo gerados
	public static int CUR_LEVEL = 0;

	// Gerador de valores aleatores de qualquer tipo para ter uma aleatoriedade
	// seletiva dentro do jogo
	public static Random random = new Random();

	// Toda vez que um objeto do GameConfig for criado ira verificar se o save
	// existe
	public GameConfig() {
		isSaved();
	}

	public void isSaved() {
		// Cria um objeto com o nome do savefile que por padrao em gerado em /res
		File file = new File("save.TheStudio");

		if (file.exists()) {

			saveExists = true;

		} else {

			saveExists = false;

		}
	}

	// NOTA: Buffered sao metodos e bibliotecas mais complexas do java que trabalham
	// em nivem de processador

	// passa a String correspondende do que esta sendo salvo, o int "val2" o valor
	// que sera salvo e o code que sera o numero de criptografia, como um char_dump
	public static void saveGame(String[] val1, int[] val2, int code) {
		// Cria um write para escrever no txt

		BufferedWriter write = null;

		// Tenta abrir o txt, caso o mesmo nao estiver acessivel ou acontecer um erro de
		// comunicação do disco o save n será gerado e o jogo sera encerado.
		try {

			write = new BufferedWriter(new FileWriter("save.TheStudio"));

		} catch (IOException e) {

			e.printStackTrace();

			System.out.println("E R R O: 0xFF000002");

			JOptionPane.showInternalMessageDialog(null,
					"Ocorreu um erro ao tentar salvar o jogo, verifique as permiçoes e tente novamente mais tarde",
					"E R R O: 0xFF000002", JOptionPane.ERROR_MESSAGE);

		}

		// Executa um for em x vezes correspondendo o tamanho do val2
		for (int i = 0; i < val1.length; i++) {

			// Recebe o val1 da posição x em uma variavel local para adicionar todos os
			// valores que serão salvos
			String current = val1[i];

			// Adiciona um simbolo ordinario para futuramente ser usado como split de
			// separação
			current += ":";

			// joga em um array todas as chars do int de val2;
			char[] value = Integer.toString(val2[i]).toCharArray();

			// executa outro for para fazer a codificação do save
			for (int in = 0; in < value.length; in++) {

				// Ao somar um int em um char o mesmo pula em x casas até o que foi solicitado
				value[in] += code;

				// adiciona a variavel local o valor codificado
				current += value[in];
			}

			// Tenta escrever o "Nome:codificado" no aquivo de save
			try {

				write.write(current);

				// Faz uma validação para ver se esta escrevendo na mesma linha;
				if (i < val1.length - 1) {

					write.newLine();

				}
			} catch (IOException e) {

				e.printStackTrace();

				System.out.println("E R R O: 0xFF000003");

				JOptionPane.showInternalMessageDialog(null,
						"Ocorreu um erro ao tentar salvar o jogo, verifique as permiçoes e tente novamente mais tarde",
						"E R R O: 0xFF000003", JOptionPane.ERROR_MESSAGE);

			}
		}
		// Tenta fechar o arquivo, este erro pode acontecer se o sistema ou o usuario
		// apaga o arquivo
		try {

			write.flush();
			write.close();
			saveExists = true;

		} catch (IOException e) {

			e.printStackTrace();

			System.out.println("E R R O: 0xFF000004");

			JOptionPane.showInternalMessageDialog(null,
					"Ocorreu um erro ao tentar salvar o jogo, verifique as permiçoes e tente novamente mais tarde",
					"E R R O: 0xFF000004", JOptionPane.ERROR_MESSAGE);
		}
	}

	// O codigo tem que ser o mesmo do que foi utilizado para salvar
	public static String loadGame(int code) {

		String line = "";
		File file = new File("save.TheStudio");

		// Valida se o save exite
		if (saveExists) {

			String singleLine = null;

			// Tenta abrir o aquivo de save
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				try {
					// executa o loop até que nao exista mais linhas "sozinhas"
					while ((singleLine = reader.readLine()) != null) {

						// efetua o split onde 0 é o que define e 1 o valor
						String[] trans = singleLine.split(":");

						char[] val = trans[1].toCharArray();
						trans[1] = "";

						// Decodifica o save
						for (int i = 0; i < val.length; i++) {
							val[i] -= code;
							trans[1] += val[i];

						}

						// adiciona em uma linha unica o OqueE:Val/OqueE1:Val1...
						line += trans[0];
						line += ":";
						line += trans[1];
						line += "/";
					}

				} catch (IOException e) {
					e.printStackTrace();

					System.out.println("E R R O: 0xFF000005");

					JOptionPane.showInternalMessageDialog(null, "Nao foi possivel carregar o save",
							"E R R O: 0xFF000005", JOptionPane.ERROR_MESSAGE);
				}

			} catch (IOException e) {
				e.printStackTrace();

				System.out.println("E R R O: 0xFF000006");

				JOptionPane.showInternalMessageDialog(null, "Nao foi possivel carregar o save", "E R R O: 0xFF000006",
						JOptionPane.ERROR_MESSAGE);
			}

			// se o save nao existir.
		} else {
			return line;
		}
		return line;
	}

	// Metodo para aplicar o save recebe a string grande como line do metodo
	// anterior
	public static void applySave(String str) {

		// faz um split das linhas e salva em um array
		String[] spl = str.split("/");

		// faz um tratamento expecifico para cada um dos valores
		for (int i = 0; i < spl.length; i++) {

			String[] spl2 = spl[i].split(":");
			// spl2[0] = O que o valor significa, spl2[1] = o valor, como ele vem como
			// string devesse converter para o valor original
			switch (spl2[0]) {
			
			//cada case é uma variavel que foi salva
			
			case "level":
				
				CUR_LEVEL = Integer.parseInt(spl2[1]);
				
				System.out.println("Level:" + CUR_LEVEL);
			
			}
		}
	}
}
