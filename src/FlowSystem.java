
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
		
	}

}
