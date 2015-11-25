
public class FlowSystem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ProcessingUnit pu = new ProcessingUnit(10);
		//public POutSignal ClockCycle(int ind, boolean inv, boolean ps, boolean ts)
		System.out.println(pu.ClockCycle(9, true, false, true).print());
		System.out.println(pu.ClockCycle(12, true, false, false).print());
		System.out.println(pu.ClockCycle(15, true, false, true).print());
		System.out.println(pu.ClockCycle(2, true, false, false).print());
		System.out.println(pu.ClockCycle(8, true, false, false).print());
		System.out.println(pu.ClockCycle(20, true, false, false).print());
		System.out.println(pu.ClockCycle(11, true, false, false).print());
		TransferringUnit tu = new TransferringUnit();
		System.out.println(tu.ClockCycle(10, true, 6, true, false, false).print());
		System.out.println(tu.ClockCycle(15, true, 16, true, false, false).print());
		System.out.println(tu.ClockCycle(20, true, 26, true, true, false).print());
		System.out.println(tu.ClockCycle(25, true, 36, true, false, false).print());
		System.out.println(tu.ClockCycle(30, true, 46, true, false, false).print());
		System.out.println(tu.ClockCycle(35, true, 56, true, false, false).print());
		System.out.println(tu.ClockCycle(40, true, 66, true, false, false).print());
		System.out.println(tu.ClockCycle(45, true, 76, true, false, false).print());
		System.out.println(tu.ClockCycle(50, true, 86, true, false, false).print());
		System.out.println(tu.ClockCycle(55, true, 96, true, false, false).print());
		System.out.println(tu.ClockCycle(60, true, 106, true, false, false).print());
		System.out.println(tu.ClockCycle(65, true, 116, true, false, false).print());
		
	}//public TOutSignal ClockCycle(int ud, boolean uv, int ld, boolean lv, boolean rs, boolean ds) {

}
