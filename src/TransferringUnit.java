
public class TransferringUnit {
	int rdl;
	boolean rvl;
	int ddl;
	boolean dvl;
	boolean usl;
	boolean lsl;
	inPriority ip;
	outPriority op;
	
	public TransferringUnit() {
		this.rvl = false;
		this.dvl = false;
		this.usl = false;
		this.lsl = false;
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
		if (!rs && this.rvl) {
			rd = this.rdl;
			rv = this.rvl;
			this.rvl = false;
		}
		if (!ds && this.dvl) {
			dd = this.ddl;
			dv = this.dvl;
			this.dvl = false;
		}
		if (this.lsl) ls = true;
		if (this.usl) us = true;
		//case 0: no input, do nothing
		//case 1: 1 input, matter A or B
		if ((uv&&!this.usl)^(lv&&!this.lsl)) {
			if (this.op== outPriority.right) {
				if (!this.rvl) {
					this.rdl = uv?ud:ld;
					this.rvl = true;
				} else {
					this.ddl = uv?ud:ld;
					this.dvl = true;
				}
			} else {
				if (!this.dvl) {
				this.ddl = uv?ud:ld;
				this.dvl = true;
				} else {
					this.rdl = uv?ud:ld;
					this.rvl = true;
				}
			}
		}
		//case 2: 2 input, matter AC, BC, AD, BD
		if ((uv&&!this.usl)&&(lv&&!this.lsl)) {
			//A right, B down, C up, D left
			if ((this.ip== inPriority.up&&this.op==outPriority.right)&&(this.ip == inPriority.left&&this.op==outPriority.down)) {
				//up->right
				this.rdl = ud;
				this.rvl = uv;
				this.ddl = ld;
				this.dvl = lv;
			}
			else {
				this.rdl = ld;
				this.rvl = lv;
				this.ddl = ud;
				this.dvl = uv;
			}
		}
		//backpressure
		if ((rs&this.rvl)&&(ds&&this.dvl) ) {
			this.lsl = true;
			this.lsl = true;
		}
		else if ((rs&this.rvl)||(ds&&this.dvl)) {
			if (this.ip==inPriority.left) {
				this.lsl = true;
			} else {
				this.lsl = true;
			}
		}
		TOutSignal output = new TOutSignal(rd, rv, dd, dv, us, ls);
		return output;
	}
	
	
}
