
public class ProcessingUnit {
	int pdl;
	boolean invl;
	int indl;
	boolean pvl;
	//boolean psl;
	int tdl;
	boolean tvl;
	//boolean tsl;
	boolean insl;
	int val;
	int counter = 0;
	boolean lasttvl;
	int togo;
	
	public ProcessingUnit(int valin, int intogo) {
		this.pvl = false;
		//this.psl = false;
		this.tvl = false;
		//this.tsl = false;
		this.insl = false;
		this.val = valin;
		this.counter = 0;
		this.lasttvl = false;
		this.togo = intogo;
		this.invl = false;
		this.indl = 0;
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
		if (this.invl)	
			ins = true;
		POutSignal output = new POutSignal(pd, pv, td, tv, ins);
		if (!this.invl && inv) {
			//this.counter = this.counter+1;
			this.indl = ind;
			this.invl = inv;
		}
		
		if (this.invl && this.counter==0 && this.indl>=this.val) {//conditions changeme
			if (!this.pvl&&!this.tvl) {
				//System.out.println("hohofirst");
				this.counter++;
				this.pdl = this.indl;
				this.tdl=this.indl;
				this.pvl = this.invl;
				this.tvl = this.invl;
				this.lasttvl = this.invl;
				this.invl = false;
		}
		} else if (this.invl&& this.counter ==1 && this.lasttvl) {
			if (!this.pvl&&!this.tvl) {
				//System.out.println("hoho");

				this.counter++;
				this.pdl = this.indl;
				this.tdl=this.indl;
				this.pvl = this.invl;
				this.tvl = this.invl;
				this.lasttvl = this.invl;
				this.invl = false;
		} 
		} else if (this.counter == 2&& this.lasttvl) {
			//System.out.println("to go to print yet");
			//System.out.println(this.togo);
			if (!this.tvl) {
				this.counter++;
				//this.pdl = this.indl;
				this.tdl=this.togo;
				//this.pvl = this.invl;
				this.tvl = true;
				this.lasttvl = false;
				this.counter =0;
				this.invl = true;
		} 
		}
		if (this.invl && this.counter==0 && this.indl<this.val) {//conditions changeme
			if (!this.pvl) {
				this.counter++;
				this.pdl = this.indl;
				//this.tdl=this.indl;
				this.pvl = this.invl;
				//this.tvl = this.invl;
				//this.lasttvl = this.invl;
				this.invl = false;
		}
		} else if (this.invl&& this.counter ==1 && !this.lasttvl) {
			if (!this.pvl) {
				this.counter++;
				this.pdl = this.indl;
				//this.tdl=this.indl;
				this.pvl = this.invl;
				//this.tvl = this.invl;
				this.lasttvl = false;
				this.invl = false;
				this.counter = 0;
		} 
		}
		//if (this.val==0) {
			//System.out.println(counter);
			//System.out.println(this.invl);
			//System.out.println(this.lasttvl);
		//}
		return output;
	}
	
	
}
