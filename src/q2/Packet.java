package q2;
/**
 * @author Richard
 *
 */
public class Packet {
	
	private String sourceIP, destIP;

	public Packet(String sourceIP, String destIP) {
		super();
		this.sourceIP = sourceIP;
		this.destIP = destIP;
	}

	public String getSourceIP() {
		return sourceIP;
	}

	public void setSourceIP(String sourceIP) {
		this.sourceIP = sourceIP;
	}

	public String getDestIP() {
		return destIP;
	}

	public void setDestIP(String destIP) {
		this.destIP = destIP;
	}

	@Override
	public String toString() {
		return "Packet [sourceIP=" + sourceIP + ", destIP=" + destIP + "]";
	}
	
}
