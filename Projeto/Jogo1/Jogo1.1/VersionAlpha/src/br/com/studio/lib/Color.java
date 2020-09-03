package br.com.studio.lib;

public class Color {
	
	// << representa uma movimenta��o de valores ao nivel binario isso para gerar o RGBA mais exato
	public static int get(int a, int b, int c, int d) {
		return (get(d) << 24) + (get(c) << 16) + (get(b) << 8) + (get(a));
	}

	// passando apenas D � possivel calcular o valor do RGBA desejado;
	public static int get(int d) {
		if (d < 0)
			return 255;
		int r = d / 100 % 10;
		int g = d / 10 % 10;
		int b = d % 10;
		return r * 36 + g * 6 + b;
	}
}
