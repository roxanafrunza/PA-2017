/**
 * Proiectarea Algoritmilor, 2016
 * Lab 9: Arbori minimi de acoperire
 *
 * @author  adinu
 * @email   mandrei.dinu@gmail.com
 */

package graph;

public class Pair<A, B> {

	private A fst;

	private B snd;

	public Pair(A fst, B snd) {
		this.fst = fst;
		this.snd = snd;
	}

	public A first() {
		return fst;
	}

	public B second() {
		return snd;
	}



	@Override
	public boolean equals(Object obj) {
		Pair<A,B> other = (Pair<A,B>) obj;
		
		if(this.first() == other.first())
			if(this.second() == other.second())
				return true;
		
		return false;
	}


}
