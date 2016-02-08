
public class ProcessingUnit {
	boolean invl;
	int indl;
	boolean pvl;
	int pdl;
	int tdl;
	boolean tvl;
	int val;
	int counter = 0;
	boolean lasttvl;//indicates where the following data should go
	int togo;
	
	public ProcessingUnit(int valin, int intogo) {
		this.pvl = false;
		this.tvl = false;
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
			this.indl = ind;
			this.invl = inv;
		}
		//if (this.val==0&&this.invl) {
			//System.out.printf("pu0.invl=%d \n", this.indl);
		//}
		if (this.invl && this.counter==0 && this.indl>=this.val) {//conditions changeme
			//need to go both way, only go when both are open, do nothing if any of them are closed
			if (!this.pvl&&!this.tvl) {
				//if (this.val==0)
					//System.out.println("hohofirst");
				this.counter=1;
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
				this.invl = false;//bingyi cao
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
				//this.counter =0;//Feb 8th
				//this.invl = false;//bingyi cao
		} 
		}
		else if (this.invl && this.counter==0 && this.indl<this.val) {//conditions changeme
			if (!this.pvl) {
				//System.out.println("seond last part");
				this.counter=1;
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
		else if (this.counter==3) {
			this.counter =0;
		}
		
		return output;
	}
	
	
}
