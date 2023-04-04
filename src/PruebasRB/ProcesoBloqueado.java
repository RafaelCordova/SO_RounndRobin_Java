/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PruebasRB;


import java.util.Vector;

public class ProcesoBloqueado extends Thread {

	private Proceso p;
	private int delay;
	private Vector<Proceso> colaListos;

	public ProcesoBloqueado(Proceso _p, int _delay, Vector<Proceso> _colaListos) {
		p = _p;
		delay = _delay;
		colaListos = _colaListos;
	}

	public void run() {
			long tiempoBloqueado = p.tBloqueadoIO;
			for (int i = (int) tiempoBloqueado; i > 0; i--) {
				try {
					sleep(delay);
					if (p.tBloqueadoReferencia < tiempoBloqueado && p.tBloqueadoIO > 0) {
						p.tBloqueadoReferencia++;
						p.tBloqueadoIO--;
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			p.bloqueado = false;
			if (p.bloqueado == false && p.tBloqueadoIO == 0) {
				colaListos.add(p);
			}
			
			this.interrupt();
	}

	/**
	 * @return the p
	 */
	public Proceso getP() {
		return p;
	}

}