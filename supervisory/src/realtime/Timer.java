package realtime;

import java.util.Calendar;
import java.util.Date;

import conexao.Indice;


public class Timer implements Runnable{
	
	
	public void run () {
		Calendar calendar = Calendar.getInstance();
		while(true){
			calendar.setTime(new Date());
			//int hora = calendar.get(Calendar.HOUR_OF_DAY);
			//int minuto = calendar.get(Calendar.MINUTE);
			Indice.segundos = calendar.get(Calendar.SECOND);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
	}

	
}
