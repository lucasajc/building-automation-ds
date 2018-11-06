package politicas;

import java.util.Calendar;
import java.util.Date;

import DTO.RegraSeguranca;
import DTO.Tabela;
import conexao.Configuracao;
import conexao.Constants;
import conexao.Indice;

public class VerificaGatilho {
	
	public static int check(Tabela tabela) throws ClassNotFoundException{
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int minuto = calendar.get(Calendar.MINUTE);
		int segundos = Indice.segundos;
		
		try{
			if(Configuracao.verificaNulidadeTabela(tabela)){
				for(int i=0;i<tabela.getRegistros().size();i++){
					char tipo = tabela.getRegistros().get(i).getId().charAt(2);
					if(Configuracao.verificaNulidadeRegistro(tabela.getRegistros().get(i))){
						switch(tipo){
							case 'T':{	
									//politica de temperatura e umidade
								
									if(minuto==Constants.T_AMOSTRA_TEMP_UM){
										PoliticaTemperaturaUmidade.gravarBDTemperatura(tabela.getRegistros().get(i));
									}
									break;
							}
							case 'U':{	
									//politica de temperatura e umidade
								
									if(minuto==Constants.T_AMOSTRA_TEMP_UM){
										PoliticaTemperaturaUmidade.gravarBDUmidade(tabela.getRegistros().get(i));
									}
									break;
							}
							case 'P':{
									//politica de controle de iluminacao
									if(tabela.getRegistros().get(i).getEstado()==1){
										PoliticaIluminacao.enviarRequisicaoControleIluminacao(tabela.getRegistros().get(i),1);
									}
									else{
										PoliticaIluminacao.enviarRequisicaoControleIluminacao(tabela.getRegistros().get(i),0);
									}
									if(segundos==Constants.T_AMOSTRA_LUMIN){
										PoliticaIluminacao.gravarBD(tabela.getRegistros().get(i));
									}
									break;
							}
							case 'M':{
									//politica de deteccao de invasao
									
									RegraSeguranca regra = PoliticaInvasao.obterRegra(tabela.getRegistros().get(i).getArea());
									if((tabela.getRegistros().get(i).getEstado()==0)&&(segundos==Constants.T_AMOSTRA_MAG)){
										PoliticaInvasao.gravarBD(tabela.getRegistros().get(i));
									}
									if((PoliticaInvasao.verificaHorario(regra))&&(tabela.getRegistros().get(i).getEstado()==1)){
										PoliticaInvasao.gravarBD(tabela.getRegistros().get(i));
										PoliticaInvasao.broadcastAlarme();
									}
									break;
							}
							case 'F':{
								//politica de deteccao de incêndio - fumaça
								if(tabela.getRegistros().get(i).getEstado()==1){
									PoliticaIncendio.gravarBD(tabela.getRegistros().get(i), tipo);
								}
								break;
							}
							case 'G':{
								//politica de deteccao de incêndio - gás glp
								if(tabela.getRegistros().get(i).getEstado()==1){
									PoliticaIncendio.gravarBD(tabela.getRegistros().get(i), tipo);
									PoliticaIncendio.broadcastAlarme();
								}
								break;
							}
							case 'C':{
								//politica de monitoramento de consumo energético
									if(segundos==Constants.T_AMOSTRA_CONSUMO){
										PoliticaConsumoEnergetico.gravarBD(tabela.getRegistros().get(i));
										PoliticaIncendio.broadcastAlarme();
									}
									
								break;
							}
							default:{
								//politica default
							}
						}
					}
					else{
						return 0;
					}
				}
			}
			else{
				return 0;
			}
			
		}catch (NullPointerException ex) {
			System.err.println(ex);
			System.out.println(tabela.toString());
			return 0;
		}
		
		return 1;
		
	}
}
