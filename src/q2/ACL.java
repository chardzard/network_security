package q2;
import java.util.ArrayList;

public class ACL {
	
	private ArrayList<ListEntry> rules;

	public ACL() {
		super();
		this.rules = new ArrayList<ListEntry>();
	}

	public ArrayList<ListEntry> getRules() {
		return rules;
	}

	public void setRules(ArrayList<ListEntry> rules) {
		this.rules = rules;
	}
}
