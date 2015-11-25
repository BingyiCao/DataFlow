
public class TransferringUnit {
	int odl;
	boolean ovl;
	int odl0;
	boolean ovl0;
	boolean ins;
	boolean ins0;
	inPriority ip;
	outPriority op;
	
	public TransferringUnit() {
		this.ovl0 = false;
		this.ovl0 = false;
		this.ins = false;
		this.ins0 = false;
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
		if (this.op == outPriority.right) {
			if (!rs) {
			if (this.ovl) {
				rd = this.odl;
				rv = this.ovl;
				this.ovl = false;
				if (!ds && this.ovl0) {
					dd = this.odl0;
					dv = this.ovl0;
					this.ovl0 = false;
				} 
			}
			else {
				rd = this.odl0;
				rv = this.ovl0;
				this.ovl0 = false;
			}
			}
			else if (!ds){
				if (this.ovl) {
					dd = this.odl;
					dv = this.ovl;
					this.ovl = false;
				}
				else {
					dd = this.odl0;
					dv = this.ovl0;
					this.ovl0 = false;
				}
			}
		}
		else {
			if (!ds) {
				if (this.ovl) {
					dd = this.odl;
					dv = this.ovl;
					this.ovl = false;
					if (!rs && this.ovl0) {
						rd = this.odl0;
						rv = this.ovl0;
						this.ovl0 = false;
					} 
				}
				else {
					dd = this.odl0;
					dv = this.ovl0;
					this.ovl0 = false;
				}
				}
				else if (!rs){
					if (this.ovl) {
						rd = this.odl;
						rv = this.ovl;
						this.ovl = false;
					}
					else {
						rd = this.odl0;
						rv = this.ovl0;
						this.ovl0 = false;
					}
				}
		}
		
		if (this.ins&&this.ins0) {
			ls = true;
			us = true;
		} else if (this.ins||this.ins0) {
			if (this.ip==inPriority.left) us = true;
			else ls = true;
		}
		TOutSignal output = new TOutSignal(rd, rv, dd, dv, us, ls);
		//case 0: no input, do nothing
		
		//case 2: 2 input
		if (uv&&!us&&lv&&!ls) {
			
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
		else if ((uv&&!us)^(lv&&ls)) {
					
						if (this.ovl) {
							this.odl = uv?ud:ld;
							this.ovl = true;
						} else {
							this.odl0 = uv?ud:ld;
							this.ovl0 = true;
						}
			} 
		//backpressure
		if (rs&this.ovl&ds&this.ovl0) {
			this.ins = true;
			this.ins0 = true;
		} else if (this.ovl0&(rs||ds)) {
			this.ins0 = true;
		}
		
		
		return output;
	}
	
	
}
