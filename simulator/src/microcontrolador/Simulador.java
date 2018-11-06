package microcontrolador;

import java.util.Random;

public class Simulador {
	
	public static Tabela popularTabela(Tabela tabela){
			if(ClienteArduino.port==501){
				tabela.getRegistros().add(new Registro("O:L:1:1", "L�mpada", "Lampada LED presente na sala 1", 1,
						1, 0,0,'D', 7));
				tabela.getRegistros().add(new Registro("O:L:1:2", "L�mpada", "Lampada LED presente na sala 1", 1,
							2, 0,0, 'D', 8));
				tabela.getRegistros().add(new Registro("O:L:1:3", "L�mpada", "Lampada LED presente na sala 1", 1,
							3, 0,0, 'D', 9));
				tabela.getRegistros().add(new Registro("I:P:1:1", "Sensor de Presen�a", "Sensor de presen�a da sala 1", 1,
							3, 0,0, 'D', 6));
				tabela.getRegistros().add(new Registro("I:T:1:1", "Sensor TU", "Sensor de TU presente na sala 1", 1,
							1, 0,0, 'A', 5));
				tabela.getRegistros().add(new Registro("I:M:1:1", "Sensor Magn�tico", "Sensor Magn�tico presente na sala 1", 1,
							1, 0,0, 'D', 10));
			}
			else if(ClienteArduino.port==502){
				tabela.getRegistros().add(new Registro("O:L:2:1", "L�mpada", "Lampada LED presente na cozinha", 2,
						1, 0,0,'D', 7));
				tabela.getRegistros().add(new Registro("O:L:2:2", "L�mpada", "Lampada LED presente na cozinha", 2,
							2, 0,0, 'D', 8));
				tabela.getRegistros().add(new Registro("O:L:2:3", "L�mpada", "Lampada LED presente na cozinha", 2,
							3, 0,0, 'D', 9));
				tabela.getRegistros().add(new Registro("I:P:2:1", "Sensor de Presen�a", "Sensor de presen�a da sala 1", 2,
							3, 0,0, 'D', 6));
				tabela.getRegistros().add(new Registro("I:M:2:1", "Sensor Magn�tico", "Sensor Magn�tico presente na cozinha", 2,
							1, 0,0, 'D', 10));
				tabela.getRegistros().add(new Registro("I:C:2:1", "Consumo Energ�tico", "Sensor de Consumo Energ�tico do circuito 1", 2,
							1, 0,0, 'A', 5));
				tabela.getRegistros().add(new Registro("I:C:2:2", "Consumo Energ�tico", "Sensor de Consumo Energ�tico do circuito 2", 2,
							2, 0,0, 'A', 5));
				tabela.getRegistros().add(new Registro("I:F:2:1", "Detector de Fuma�a/Ind�ncio", "Sensor de Fuma�a/Ind�ncio da cozinha", 2,
							1, 0,0, 'A', 5));
			}
			else if(ClienteArduino.port==503){
				tabela.getRegistros().add(new Registro("O:L:3:1", "L�mpada", "Lampada LED presente no quarto", 3,
						1, 0,0,'D', 7));
				tabela.getRegistros().add(new Registro("O:L:3:2", "L�mpada", "Lampada LED presente no quarto", 3,
							2, 0,0, 'D', 8));
				tabela.getRegistros().add(new Registro("O:L:3:3", "L�mpada", "Lampada LED presente no quarto", 3,
							3, 0,0, 'D', 9));
				tabela.getRegistros().add(new Registro("I:P:3:1", "Sensor de Presen�a", "Sensor de presen�a do quarto", 3,
							1, 0,0, 'D', 6));
				tabela.getRegistros().add(new Registro("I:M:3:1", "Sensor Magn�tico", "Sensor Magn�tico presente no quarto", 3,
							1, 0,0, 'D', 10));
				tabela.getRegistros().add(new Registro("O:A:3:1", "Alarme", "Alarme presente no quarto", 3,
							1, 0,0, 'A', 5));
			}
			
			return tabela;
	}
	
	public static Tabela randomizarTabela(Tabela tabela){
		
		Random gerador = new Random();
		
		for(int i=0;i<tabela.getRegistros().size();i++){
			char tipo = tabela.getRegistros().get(i).getId().charAt(2);
			switch(tipo){
				case 'T':{
						int sinal = 1;
						if(tabela.getRegistros().get(i).getValor()==20){
							sinal = 1;
						}
						else if (tabela.getRegistros().get(i).getValor()==30){
							sinal = -1;
						}
						if(tabela.getRegistros().get(i).getValor()>=20){
							tabela.getRegistros().get(i).setValor(gerador.nextDouble()*0.1+sinal*tabela.getRegistros().get(i).getValor());
						}
						else{
							tabela.getRegistros().get(i).setValor(gerador.nextDouble()*10+20);
						}
						break;
				}
				case 'P':{
						if(gerador.nextInt(100)<=20){
							tabela.getRegistros().get(i).setEstado(1);
						}
						else{
							tabela.getRegistros().get(i).setEstado(0);
						}
						
						break;
				}
				case 'M':{
						if(gerador.nextInt(100)<=5){
							tabela.getRegistros().get(i).setEstado(1);
						}
						else{
							tabela.getRegistros().get(i).setEstado(0);
						}
						break;
				}
				case 'F':{
						if(gerador.nextInt(100)<=5){
							tabela.getRegistros().get(i).setEstado(1);
						}
						else{
							tabela.getRegistros().get(i).setEstado(0);
						}
						break;
				}
				case 'C':{
						tabela.getRegistros().get(i).setValor(gerador.nextDouble()*200+100);
						break;
				}
				default:{
					//politica default
				}
			}
		}
		return tabela;
		
	}
		
	
}
