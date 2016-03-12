package q2;

public class ListEntry {
	
	private int listNumber;
	private boolean allow;
	private String sourceMask, sourceIP, destMask, destIP;
	private boolean extended;
	
	public ListEntry(int ln, boolean a, String sip, String sm) {
		listNumber = ln;
		allow = a;
		sourceIP = sip;
		sourceMask = sm;
		extended = false;
	}
	
	public ListEntry(int ln, boolean a, String sip, String sm, String dip, String dm) {
		listNumber = ln;
		allow = a;
		sourceIP = sip;
		sourceMask = sm;
		destIP = dip;
		destMask = dm;
		extended = true;
	}

	public int getListNumber() {
		return listNumber;
	}

	public void setListNumber(int listNumber) {
		this.listNumber = listNumber;
	}

	public boolean isAllow() {
		return allow;
	}

	public void setAllow(boolean allow) {
		this.allow = allow;
	}

	public String getSourceMask() {
		return sourceMask;
	}

	public void setSourceMask(String sourceMask) {
		this.sourceMask = sourceMask;
	}

	public String getSourceIP() {
		return sourceIP;
	}

	public void setSourceIP(String sourceIP) {
		this.sourceIP = sourceIP;
	}

	public String getDestMask() {
		return destMask;
	}

	public void setDestMask(String destMask) {
		this.destMask = destMask;
	}

	public String getDestIP() {
		return destIP;
	}

	public void setDestIP(String destIP) {
		this.destIP = destIP;
	}

	public boolean isExtended() {
		return extended;
	}

	public void setExtended(boolean extended) {
		this.extended = extended;
	}

	@Override
	public String toString() {
		return "ListEntry [listNumber=" + listNumber + ", allow=" + allow + ", sourceMask=" + sourceMask + ", sourceIP="
				+ sourceIP + ", destMask=" + destMask + ", destIP=" + destIP + ", extended=" + extended + "]";
	}
}
