
public class TOutSignal {
	int rd;
	boolean rv;
	int dd;
	boolean dv;
	boolean us;
	boolean ls;
	
	public TOutSignal(int rdin, boolean rvin, int ddin, boolean dvin, boolean usin, boolean rsin){
		this.rd = rdin;
		this.rv = rvin;
		this.dd = ddin;
		this.dv = dvin;
		this.us = usin;
		this.ls = rsin;
	}
	
	public String print() {
		StringBuilder sb = new StringBuilder();
		sb.append("right: ");
		sb.append(this.rd);
		sb.append(" ");
		sb.append(this.rv);
		sb.append(" ");
		sb.append("down: ");
		sb.append(this.dd);
		sb.append(" ");
		sb.append(this.dv);
		sb.append(" ");
		sb.append("up bp");
		sb.append(this.us);
		sb.append(" ");
		sb.append("left bp");
		sb.append(this.ls);
		return sb.toString();
		
	}
}
