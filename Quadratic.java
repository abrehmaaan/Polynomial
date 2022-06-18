import java.util.TreeMap;

public class Quadratic extends Polynomial {
	private double delta, root1, root2;
	public Quadratic(int x2, int x1, int x0) {
		super(new TreeMap<Integer,Integer>() {{ put(0,x0); put(1,x1); put(2,x2); }});
		delta = Math.pow(x1, 2) - (4*x2*x0);
		if(delta>0) {
			root1 = (-x1 + Math.sqrt(delta))/(2*x2);
			root2 = (-x1 - Math.sqrt(delta))/(2*x2);
		}
		else if(delta==0) {
			root1 = -x1 + Math.sqrt(delta)/2*x2;
			root2 = root1;
		}
		else {
			root1=root2=Integer.MAX_VALUE;
		}
	}
	public double getRoot1() {
		return root1;
	}
	public double getRoot2() {
		return root2;
	}
	public boolean roots() {
		if(delta>0) {
			return true;
		}
		else if(delta==0) {
			return true;
		}
		else {
			return false;
		}
	}
}
