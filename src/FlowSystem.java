import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FlowSystem {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		/*
		 * ProcessingUnit pu = new ProcessingUnit(10); public POutSignal
		 * ClockCycle(int ind, boolean inv, boolean ps, boolean ts)
		 * System.out.println(pu.ClockCycle(9, true, false, true).print());
		 * System.out.println(pu.ClockCycle(12, true, false, false).print());
		 * System.out.println(pu.ClockCycle(15, true, false, true).print());
		 * System.out.println(pu.ClockCycle(2, true, false, false).print());
		 * System.out.println(pu.ClockCycle(8, true, false, false).print());
		 * System.out.println(pu.ClockCycle(20, true, false, false).print());
		 * System.out.println(pu.ClockCycle(11, true, false, false).print());
		 * TransferringUnit tu = new TransferringUnit();
		 * System.out.println(tu.ClockCycle(10, true, 6, true, false,
		 * false).print()); System.out.println(tu.ClockCycle(15, true, 16, true,
		 * false, false).print()); System.out.println(tu.ClockCycle(20, true,
		 * 26, true, true, false).print()); System.out.println(tu.ClockCycle(25,
		 * true, 36, true, false, false).print());
		 * System.out.println(tu.ClockCycle(30, true, 46, true, false,
		 * false).print()); System.out.println(tu.ClockCycle(35, true, 56, true,
		 * false, false).print()); System.out.println(tu.ClockCycle(40, true,
		 * 66, true, false, false).print());
		 * System.out.println(tu.ClockCycle(45, true, 76, true, false,
		 * false).print()); System.out.println(tu.ClockCycle(50, true, 86, true,
		 * false, false).print()); System.out.println(tu.ClockCycle(55, true,
		 * 96, true, false, false).print());
		 * System.out.println(tu.ClockCycle(60, true, 106, true, false,
		 * false).print()); System.out.println(tu.ClockCycle(65, true, 116,
		 * true, false, false).print());
		 */
		int depth = 10;
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
		int rclk = 0;
		int lastcounter = -1;
		while (clk < 24) {
			clk = clk + 1;
			System.out.printf("Clock Cycle %d\n", clk);
			if (!pout[0].ins) {
				rclk++;
				if (Line != null) {
					String[] tmp = Line.split(" ");
					int in = 0;
					// if (pu[0].counter!=lastcounter) {
					if (rclk == 1) {
						in = Integer.parseInt(tmp[0]);
						nextpout[0] = pu[0].ClockCycle(in, true, pout[1].ins,
								tout[0][0].us);
					} else if (rclk == 2) {
						in = Integer.parseInt(tmp[1]);
						nextpout[0] = pu[0].ClockCycle(in, true, pout[1].ins,
								tout[0][0].us);
					}
					// else {
					// nextpout[0]=pu[0].ClockCycle(0, true,
					// pout[1].ins,tout[0][0].us);
					// }
					// } else {
					// nextpout[0]=pu[0].ClockCycle(0, false,
					// pout[1].ins,tout[0][0].us);
					// }

					// } else {
					// nextpout[0]=pu[0].ClockCycle(0, false,
					// pout[1].ins,tout[0][0].us);
					// }
					// }
				} else {
					nextpout[0] = pu[0].ClockCycle(0, false, pout[1].ins,
							tout[0][0].us);
				}
				if (rclk==2) rclk=0;
			} else {
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
			// System.out.println();
			 for (int i=0; i<depth; i++) {
				 System.err.printf("p=%b %d, ", pout[i].pv, pout[i].pd);
			 }
			 System.err.println();
			 for (int i=0; i<depth; i++) {
			 System.err.printf("t=%b %d, ", pout[i].tv, pout[i].td);
			 }
			 System.err.println();
			 System.out.println("transferring line situation");
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < depth; j++) {
					if (!tout[i][j].rv) System.out.printf("r=%b %d, ", tout[i][j].rv, tout[i][j].rd);
					else System.err.printf("r=%b %d, ", tout[i][j].rv, tout[i][j].rd);
					

				}
				System.out.println();
				for (int j = 0; j < depth; j++) {
					if (!tout[i][j].dv) System.out.printf("d=%b %d, ", tout[i][j].dv, tout[i][j].dd);
					else System.err.printf("d=%b %d, ", tout[i][j].dv, tout[i][j].dd);
				}
				System.out.println();
				if (tout[i][depth - 1].rv) {
					// System.out.println("haha");
					// System.out.printf("%b %d\n", tout[i][depth-1].rv,
					// tout[i][depth-1].rd);
					// System.out.println(tout[i][depth-1].rd);
				}
			}
			
			if (rclk%2==0) {
				Line = br.readLine();
				//System.out.println(Line);
				
			}
			System.out.println();

		}

	}
}