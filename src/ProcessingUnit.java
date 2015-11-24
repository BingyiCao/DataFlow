
public class ProcessingUnit {
	int pdl;
	boolean pvl;
	//boolean psl;
	int tdl;
	boolean tvl;
	//boolean tsl;
	boolean insl;
	int val;
	
	public ProcessingUnit(int valin) {
		this.pvl = false;
		//this.psl = false;
		this.tvl = false;
		//this.tsl = false;
		this.insl = false;
		this.val = valin;
	}
	
	public POutSignal ClockCycle(int ind, boolean inv, boolean ps, boolean ts) {
		int pd = 0;
		boolean pv = false;
		int td = 0;
		boolean tv = false;
		boolean ins = false;
		if (!ps) {
			pd = this.pdl;
			pv = this.pvl;
			this.pvl = false;
		}
		if (!ts) {
			td = this.tdl;
			tv = this.tvl;
			this.tvl = false;
		}
		//if ((this.psl&&this.pvl)||(this.tsl&&this.tvl))
		if (this.insl)	
			ins = true;
		POutSignal output = new POutSignal(pd, pv, td, tv, ins);
		if (!ins && inv) {
			this.pdl= ind;
			this.pvl=inv;
			if (ind>this.val){
				this.tdl = ind;
				this.tvl = inv;
			}
		}
		if ((ps && this.pvl)||(ts && this.tvl)) 
			this.insl = true;
		else 
			this.insl = false;
		return output;
	}
	
	
}
