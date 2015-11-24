
public class POutSignal {
	int pd;
	boolean pv;
	int td;
	boolean tv;
	boolean ins;
	public POutSignal(int pdin, boolean pvin, int tdin, boolean tvin, boolean insin){
		this.pd = pdin;
		this.pv = pvin;
		this.td = tdin;
		this.tv = tvin;
		this.ins = insin;
	}
	public String print(){
		StringBuilder sb = new StringBuilder();
		sb. append(this.pd);
		sb.append(" ");
		sb.append(this.pv);
		sb.append(" ");
		sb.append(this.td);
		sb.append(" ");
		sb.append(this.tv);
		sb.append(" ");
		sb.append(this.ins);
		return sb.toString();
	}
}
