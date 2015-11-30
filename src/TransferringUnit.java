
public class TransferringUnit {
	int odl;
	boolean ovl;
	int odl0;
	boolean ovl0;
	int out0;
	boolean out0v;
	int out1;
	boolean out1v;
	
	inPriority ip;
	outPriority op;
	
	public TransferringUnit() {
		this.ovl0 = false;
		this.ovl0 = false;
		this.out0v = false;
		this.out1v = false;
		this.ip = inPriority.left;
		this.op = outPriority.right;
	}
	
	enum inPriority {
		left, up
	}
	enum outPriority{
		right, down
	}
	
	public TOutSignal ClockCycle(int ud, boolean uv, int ld, boolean lv, boolean rs, boolean ds) {
		int rd = 0;
		boolean rv = false;
		int dd = 0;
		boolean dv = false;
		boolean us = false;
		boolean ls = false;
		//System.out.println(this.out0);
		//System.out.println(this.out1);
		//System.out.println(this.ovl);
		//System.out.println(this.ovl0);
		//System.out.println(this.odl0);
		if (this.op == outPriority.right) {
			if (!rs) {
			if (this.out0v) {
				rd = this.out0;
				rv = this.out0v;
				this.out0v = false;
				if (!ds && this.out1v) {
					dd = this.out1;
					dv = this.out1v;
					this.out1v = false;
				} 
			}
			else {
				rd = this.out1;
				rv = this.out1v;
				this.out1v = false;
			}
			}
			else if (!ds){
				if (this.out0v) {
					//System.out.println("we are here");
					dd = this.out0;
					dv = this.out0v;
					this.out0v = false;
				}
				else {
					dd = this.out1;
					dv = this.out1v;
					this.out1v = false;
				}
			}
		}
		else {
			if (!ds) {
				if (this.out0v) {
					dd = this.out0;
					dv = this.out0v;
					this.out0v = false;
					if (!rs && this.out1v) {
						rd = this.out1;
						rv = this.out1v;
						this.out1v = false;
					} 
				}
				else {
					dd = this.out1;
					dv = this.out1v;
					this.out1v = false;
				}
				}
				else if (!rs){
					if (this.out0v) {
						rd = this.out0;
						rv = this.out0v;
						this.out0v = false;
					}
					else {
						rd = this.out1;
						rv = this.out1v;
						this.out1v = false;
					}
				}
		}
		
		
		
	//	System.out.println(this.ins);
	//	System.out.println(this.ins0);
		if (this.ovl&&this.ovl0) {
			ls = true;
			us = true;
		} else if (this.ovl||this.ovl0) {
			if (this.ip==inPriority.left) us = true;
			else ls = true;
		}
		TOutSignal output = new TOutSignal(rd, rv, dd, dv, us, ls);
		//case 0: no input, do nothing
		
		//case 2: 2 input
		if (uv&&!this.ovl&&lv&&!this.ovl0) {
			
			if (this.ip== inPriority.up) {
				this.odl = ud;
				this.ovl = uv;
				this.odl0 = ld;
				this.ovl0= lv;
			}
			else {
				this.odl = ld;
				//System.out.println(this.odl);
				this.ovl = lv;
				this.odl0 = ud;
				//System.out.println(this.odl0);
				this.ovl0= uv;
			}
		}
		//case 1: 1 input
		//else if ((uv&&!us)^(lv&&ls)) {
		//else if ((!this.ovl^!this.ovl0)&&(uv||lv)){
		else if ((!this.ovl||!this.ovl0)&&(uv||lv)){
			System.out.println("one input");
			//System.out.println("we are here");
					/*	if (this.ovl) {
							
							this.odl = uv?ud:ld;
							this.ovl = true;
						} else {
							this.odl0 = uv?ud:ld;
							this.ovl0 = true;
						}*/
			
				if (!this.ovl0) {
					if (uv^lv) {
						this.odl0 = uv?ud:ld;
						this.ovl0 = true;
					}
					else if (this.ip==inPriority.left) {
						this.odl0 = ld;
						this.ovl0 = true;
					} else {
						this.odl0 = ud;
						this.ovl0 = true;
					}
				} else {
					if (uv^lv) {
						this.odl = uv?ud:ld;
						this.ovl = true;
					}
					else if (this.ip==inPriority.left) {
						this.odl = ld;
						this.ovl = true;
					} else {
						this.odl = ud;
						this.ovl = true;
					}
				}
			} 
		//System.out.println(this.out0);
		//System.out.println(this.out1);
		
		//backpressure
		/*if (rs&this.ovl&ds&this.ovl0) {
			this.ins = true;
			this.ins0 = true;
		} else if (this.ovl0&(rs||ds)) {
			this.ins0 = true;
		}*/
		//System.out.println(this.ins);
		//System.out.println(this.ins0);
		//System.out.printf("input is %d %d %b %b\n", ud, ld, rs, ds);
		
		if (!this.out0v) {
			if (this.ovl) {
			this.out0 = this.odl;
			this.out0v = this.ovl;
			this.ovl = false;
			} else if (this.ovl0){
				this.out1 = this.odl0;
				this.out1v = this.ovl0;
				this.ovl0 = false;
			}
		} 
		if (!this.out1v) {
			if (this.ovl) {
				this.out0 = this.odl;
				this.out0v = this.ovl;
				this.ovl = false;
				} else if (this.ovl0){
					this.out1 = this.odl0;
					this.out1v = this.ovl0;
					this.ovl0 = false;
				}
		}
		return output;
	}
	
	
}
