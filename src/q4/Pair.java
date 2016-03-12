package q4;

public class Pair {
	
	private char first, second;
	private boolean empty;

	public Pair(char first, char second) {
		super();
		this.first = first;
		this.second = second;
		empty = false;
	}

	public char getFirst() {
		return first;
	}

	public void setFirst(char first) {
		this.first = first;
	}

	public char getSecond() {
		return second;
	}

	public void setSecond(char second) {
		this.second = second;
	}

	@Override
	public String toString() {
		return "Pair [first=" + first + ", second=" + second + "]";
	}

	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}
}
