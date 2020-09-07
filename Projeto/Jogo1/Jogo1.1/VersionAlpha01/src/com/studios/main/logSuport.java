package com.studios.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.swing.JOptionPane;

public class logSuport {
	private String Nome;
	
	public logSuport(int numero, String msg, Object erro, String classe) {
		generateNome();
		File file = new File(System.getProperty("java.io.tmpdir\\studios"));
		if(file.exists()) {
			try {
				FileWriter arq = new FileWriter("java.io.tmpdir\\studios\\"+Nome+".txt");
				PrintWriter gravarArq = new PrintWriter(arq);
				gravarArq.printf("| Erro de execução |\n");
				gravarArq.printf(msg+"\n"+erro+"\nClasse: "+classe+"\nNum: "+numero);
				arq.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,"Nao foi possivel criar o log de erro", "E R R O", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}else {
			file.mkdirs();
			try {
				FileWriter arq = new FileWriter("java.io.tmpdir\\studios\\"+Nome+".txt");
				PrintWriter gravarArq = new PrintWriter(arq);
				gravarArq.printf("| Erro de execução |\n");
				gravarArq.printf(msg+"\n"+erro+"\nClasse: "+classe+"\nNum: "+numero);
				arq.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,"Nao foi possivel criar o log de erro", "E R R O", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}
	public void generateNome() {
		Random rand = new Random();
		
		Nome = String.valueOf(rand.nextInt());
	}
}

