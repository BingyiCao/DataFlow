import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
//newest version
public class FlowSystem {

	public static void main(String[] args) throws IOException {
		int depth = 5;
		int width = 4;
		ProcessingUnit[] pu = new ProcessingUnit[depth];
		TransferringUnit[][] tu = new TransferringUnit[width][depth];
		for (int i = 0; i < depth; i++)
			pu[i] = new ProcessingUnit(i, i * 1000);
		for (int i = 0; i < depth; i++) {
			for (int j = 0; j < width; j++) {
				tu[j][i] = new TransferringUnit();
			}
		}
		POutSignal[] pout = new POutSignal[depth + 1];
		POutSignal[] nextpout = new POutSignal[depth + 1];
		for (int i = 0; i < depth + 1; i++) {
			pout[i] = new POutSignal(0, false, 0, false, false);
			nextpout[i] = new POutSignal(0, false, 0, false, false);
		}
		TOutSignal[][] tout = new TOutSignal[width][depth];
		TOutSignal[][] nexttout = new TOutSignal[width][depth];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < depth; j++) {
				tout[i][j] = new TOutSignal(0, false, 0, false, false, false);
				nexttout[i][j] = new TOutSignal(0, false, 0, false, false,
						false);
			}
		}
		BufferedReader br = new BufferedReader(new FileReader("tablea"));
		String Line = null;
		try {
			Line = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int clk = 0;
		int lastcounter = -1;
		while (clk < 24) {
			clk = clk + 1;
			System.out.printf("Clock Cycle %d\n", clk);
			//if (!pout[0].ins) {
			if (!pu[0].invl) {
				if (Line != null) {
					String[] tmp = Line.split(" ");
					int in = 0;
						in = Integer.parseInt(tmp[0]);
						nextpout[0] = pu[0].ClockCycle(in, true, pout[1].ins,
								tout[0][0].us);
						System.err.printf("     in=%d  ", in);
					
				} else {
					nextpout[0] = pu[0].ClockCycle(0, false, pout[1].ins,
							tout[0][0].us);
					System.err.printf("        in=empty  ");
				}
				Line = br.readLine();
			} else {
				//System.out.println("now we are unable to take any input");
				System.err.printf("hd=%s ", Line);
				System.err.printf("inr=%d ", pu[0].indl);
				nextpout[0] = pu[0].ClockCycle(0, false, pout[1].ins,
						tout[0][0].us);
			}
			for (int i = 1; i < depth; i++) {
				nextpout[i] = pu[i].ClockCycle(pout[i - 1].pd, pout[i - 1].pv,
						pout[i + 1].ins, tout[0][i].us);
			}
			nexttout[0][0] = tu[0][0].ClockCycle(pout[0].td, pout[0].tv, 0,
					false, tout[0][1].ls, tout[1][0].us);
			for (int j = 1; j < depth - 1; j++) {
				nexttout[0][j] = tu[0][j].ClockCycle(pout[j].td, pout[j].tv,
						tout[0][j - 1].rd, tout[0][j - 1].rv,
						tout[0][j + 1].ls, tout[1][j].us);
			}
			nexttout[0][depth - 1] = tu[0][depth - 1].ClockCycle(
					pout[depth - 1].td, pout[depth - 1].tv,
					tout[0][depth - 1 - 1].rd, tout[0][depth - 1 - 1].rv,
					false, tout[1][depth - 1].us);
			for (int i = 1; i < width - 1; i++) {
				nexttout[i][0] = tu[i][0].ClockCycle(tout[i - 1][0].dd,
						tout[i - 1][0].dv, 0, false, tout[i][1].ls,
						tout[i + 1][0].us);
			}
			nexttout[width - 1][0] = tu[width - 1][0].ClockCycle(
					tout[width - 1 - 1][0].dd, tout[width - 1 - 1][0].dv, 0,
					false, tout[width - 1][1].ls, true);
			for (int i = 1; i < width - 1; i++) {
				for (int j = 1; j < depth; j++) {
					if (j < depth - 1)
						nexttout[i][j] = tu[i][j].ClockCycle(tout[i - 1][j].dd,
								tout[i - 1][j].dv, tout[i][j - 1].rd,
								tout[i][j - 1].rv, tout[i][j + 1].ls,
								tout[i + 1][j].us);
					else
						nexttout[i][j] = tu[i][j].ClockCycle(tout[i - 1][j].dd,
								tout[i - 1][j].dv, tout[i][j - 1].rd,
								tout[i][j - 1].rv, false, tout[i + 1][j].us);
				}
			}
			tout = nexttout;
			pout = nextpout;
			 //System.out.println();
			 //System.err.printf("in=%s ", Line);
			 for (int i=0; i<depth; i++) {
				 System.err.printf("p=%b %d, ", pout[i].pv, pout[i].pd);
			 }
			 System.err.println();
			 System.err.printf("            ");
			 for (int i=0; i<depth; i++) {
			 System.err.printf("t=%b %d, ", pout[i].tv, pout[i].td);
			 }
			 System.err.println();
			 System.out.println("transferring line situation");
			 
			// System.out.printf("r=%b %d, ", tout[0][0].rv, tout[0][0].rd);
			// System.out.printf("%d ", pu[0].counter);
			// System.out.println();
			// System.out.printf("d=%b %d, ", tout[0][0].dv, tout[0][0].dd);
			 System.out.println();
			
			for (int i = 0; i < width; i++) {
				 System.out.printf("            ");
				for (int j = 0; j < depth; j++) {
					if (!tout[i][j].rv) System.out.printf("r=%b %d, ", tout[i][j].rv, tout[i][j].rd);
					else System.err.printf("r=%b %d, ", tout[i][j].rv, tout[i][j].rd);
					
				}
				System.out.println();
				 System.out.printf("            ");
				for (int j = 0; j < depth; j++) {
					//if (!tout[i][j].dv) 
					System.out.printf("d=%b %d, ", tout[i][j].dv, tout[i][j].dd);
					//else System.err.printf("d=%b %d, ", tout[i][j].dv, tout[i][j].dd);
				}
				System.out.println();
				if (tout[i][depth - 1].rv) {
				}
			}
//			System.out.println(Line);
			
		}

	}
}


