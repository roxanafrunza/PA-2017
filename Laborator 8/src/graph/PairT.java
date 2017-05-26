/**
 * Proiectarea Algoritmilor, 2016
 * Lab 8: Drumuri minime
 *
 * @author  adinu
 * @email   mandrei.dinu@gmail.com
 */

package graph;

public class PairT<A, B> {

	private A fst;

	private B snd;

	public PairT(A fst, B snd) {
		this.fst = fst;
		this.snd = snd;
	}

	public A first() {
		return fst;
	}

	public B second() {
		return snd;
	}

}
