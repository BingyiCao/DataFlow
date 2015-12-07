
public class TransferringUnit {
	int left;
	boolean leftv;
	int up;
	boolean upv;
	int down;
	boolean downv;
	int right;
	boolean rightv;
	boolean uptoright;
	boolean lefttoright;
	int cup;
	int cleft;
	
	inPriority ip;
	outPriority op;
	
	public TransferringUnit() {
		this.left = 0;
		this.leftv= false;
		this.up = 0;
		this.upv= false;
		this.right=0;
		this.rightv = false;
		this.down = 0;
		this.downv = false;
		this.cup=0;
		this.cleft=0;
		this.lefttoright = false;
		this.uptoright = false;
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
		//transfer output first
		
		if (!rs&&this.rightv)	{
			rd = this.right;
			rv = this.rightv;
			this.rightv = false;
		}
		if (!ds && this.downv) {
			dd = this.down;
			dv = this.downv;
			this.downv = false;
		}
		//backpressure assert
		if (this.upv) us = true;
		if (this.leftv) ls = true;
		
		TOutSignal output = new TOutSignal(rd, rv, dd, dv, us, ls);
		//input process
		
		if (!this.upv&&uv) {
			this.up = ud;
			this.upv = uv;
			this.cup ++;
			if (this.cup==4)  {
				this.cup =1;
				this.uptoright = false;
			}
		} else {
			if (this.cup==3) {
				this.cup=0;
				this.uptoright = false;
			}
		}
		if (!this.leftv&&lv) {
			this.left= ld;
			this.leftv = lv;
			this.cleft++;
			if (this.cleft==4)  {
				this.cleft=1;
				this.lefttoright = false;
			}
		} else {
			if (this.cleft==3) {
				this.cleft=0;
				this.lefttoright = false;
			}
		}
		if (this.cleft==0) {
			if (this.cup==1) {
				if (this.op==outPriority.right) {
					if (!this.rightv) {
						this.right=this.up;
						this.rightv=this.upv;
						this.upv=false;
						this.uptoright = true;
					}
					else if (!this.downv) {
						this.down=this.up;
						this.downv =this.upv;
						this.upv = false;
					}
				}
				else {
					if (!this.downv) {
						this.down=this.up;
						this.downv =this.upv;
						this.upv = false;
					} else if (!this.rightv) {
						this.right=this.up;
						this.rightv=this.upv;
						this.upv=false;
						this.uptoright = true;
					}
				}
			} else {
				if (this.uptoright) {
					if (!this.rightv) {
						this.right=this.up;
						this.rightv=this.upv;
						this.upv=false;
					}
				}
				else {
					if (!this.downv) {
						this.down=this.up;
						this.downv =this.upv;
						this.upv = false;
					}
				}
			}
		}
		else if (this.cleft==1) {
			if (this.cup==0) {
				if (this.op==outPriority.right) {
					if (!this.rightv) {
						this.right=this.left;
						this.rightv=this.leftv;
						this.leftv=false;
						this.lefttoright = true;
						System.out.println("first");
					}
					else if (!this.downv) {
						this.down=this.left;
						this.downv =this.leftv;
						this.leftv = false;
					}
				}
				else {
					if (!this.downv) {
						this.down=this.left;
						this.downv =this.leftv;
						this.leftv = false;
					} else if (!this.rightv) {
						this.right=this.left;
						this.rightv=this.leftv;
						this.leftv=false;
						this.lefttoright = true;
						System.out.println("second");
					}
				}
			}
			else if (this.cup==1) {
				if (this.ip==inPriority.left) {
					if (this.op==outPriority.right) {
						if (!this.rightv) {
							this.right=this.left;
							this.rightv=this.leftv;
							this.leftv=false;
							this.lefttoright = true;
							System.out.println("third");
							if (!this.downv) {
								this.down=this.up;
								this.downv =this.upv;
								this.upv = false;
							}
						}
						else if (!this.downv) {
							this.down=this.left;
							this.downv =this.leftv;
							this.leftv = false;
						}
					}
					else {
						if (!this.downv) {
							this.down=this.left;
							this.downv =this.leftv;
							this.leftv = false;
							if (!this.rightv) {
								this.right=this.up;
								this.rightv=this.upv;
								this.upv=false;
								this.uptoright = true;
							}
						} else if (!this.rightv) {
							this.right=this.left;
							this.rightv=this.leftv;
							this.leftv=false;
							this.lefttoright = true;
							System.out.println("fourth");
						}
					}
				}
				else {//in priority = up
					if (this.op==outPriority.right) {
						if (!this.upv) {
							this.right=this.up;
							this.rightv=this.upv;
							this.upv=false;
							this.uptoright = true;
							if (!this.downv) {
								this.down=this.left;
								this.downv =this.leftv;
								this.leftv = false;
							}
						}
						else if (!this.downv) {
							this.down=this.up;
							this.downv =this.upv;
							this.upv = false;
						}
					}
					else {
						if (!this.downv) {
							this.down=this.up;
							this.downv =this.upv;
							this.upv = false;
							if (!this.rightv) {
								this.right=this.left;
								this.rightv=this.leftv;
								this.leftv=false;
								this.lefttoright = true;
								System.out.println("fifth");
							}
						} else if (!this.rightv) {
							this.right=this.up;
							this.rightv=this.upv;
							this.upv=false;
							this.uptoright = true;
						}
					}
				}
			}
			else if (this.cup>=2) {
				if (this.uptoright) {
					//System.out.println(this.lefttoright);
					if (!this.rightv) {
						this.right = this.up;
						this.rightv = this.upv;
						this.upv = false;
					}
					if (!this.downv) {
						this.down = this.left;
						this.downv = this.leftv;
						this.leftv = false;
					}
				}
				else {
					if (!this.downv) {
						this.down=this.up;
						this.downv = this.upv;
						this.upv = false;
					}
					if (!this.rightv) {
						this.right = this.left;
						this.rightv = this.leftv;
						this.leftv = false;
						if (this.cleft==1)
						this.lefttoright = true;
						System.out.println("sixth");
					}
				}
			}
		}
		else if (this.cleft>1) {
			System.out.println("we are at the left following stage");
			System.out.println(this.lefttoright);
			System.out.println(this.left);
			System.out.println(this.cup);
			System.out.println(this.cleft);
			if (this.lefttoright) {
				if (!this.rightv) {
					this.right = this.left;
					this.rightv = this.leftv;
					this.leftv = false;
				}
				if (!this.downv) {
					this.down=this.up;
					this.downv = this.upv;
					this.upv = false;
				}
			}
			else {
				
				if (!this.downv) {
					this.down = this.left;
					this.downv = this.leftv;
					this.leftv = true;
				}
				if (!this.rightv) {
					this.right = this.up;
					this.rightv = this.upv;
					this.upv = false;
					if (this.cup==1)
					this.uptoright = true;
				}
			}
		}
		
		//if (this.cup==3) this.cup=0;
		//if (this.cleft==3) this.cleft =0;
		
		
		
		return output;
	}
	
	
}
