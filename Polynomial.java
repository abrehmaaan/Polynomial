import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
public class Polynomial implements Comparable<Polynomial> {
	private TreeMap<Integer, Integer> expression;
	private int degree;
	public Polynomial() {
		expression = new TreeMap<Integer, Integer>(Collections.reverseOrder());
		degree = -1;
	}
	public Polynomial(int power, int coefficient) {
		try {
			if(power<0) {
				throw new IllegalArgumentException("Power of a term can't be negative. The term ignored.");
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		expression = new TreeMap<Integer, Integer>(Collections.reverseOrder());
		expression.put(power, coefficient);
		degree = power;
	}
	public Polynomial(TreeMap<Integer, Integer> polynomials) {
		degree = -1;
		expression = new TreeMap<Integer, Integer>(Collections.reverseOrder());
		for(Map.Entry m:polynomials.entrySet()){
			if((int)m.getKey()>degree) {
				degree = (int)m.getKey();
			}
			try {
				if((int)m.getKey()<0) {
					throw new IllegalArgumentException("Power of a term can't be negative. The term ignored.");
				}
				else {
					expression.put((int)m.getKey(),(int)m.getValue());
				}
			}
			catch(Exception e) {
				System.out.println(e);
			}
		}
	}
	public Polynomial(Polynomial rhs) {
		degree = rhs.degree;
		expression = new TreeMap<Integer, Integer>(Collections.reverseOrder());
		for(Map.Entry m:rhs.expression.entrySet()){
			expression.put((int)m.getKey(),(int)m.getValue());    
		}
	}
	public void add(Polynomial rhs) {
		for(Map.Entry m:rhs.expression.entrySet()){
			if((int)m.getKey()>degree) {
				degree = (int)m.getKey();
			}
			if(expression.containsKey((int)m.getKey())){
				expression.replace((int)m.getKey(), (int)expression.get((int)m.getKey())+(int)m.getValue());
				if(((int)expression.get((int)m.getKey())==0)) {
					expression.remove((int)m.getKey());
				}
			}
			else {
				expression.put((int)m.getKey(),(int)m.getValue());    
			}
		}
	}
	public static Polynomial add(Polynomial lhs, Polynomial rhs) {
		Polynomial result = new Polynomial(lhs);
		result.add(rhs);
		return result;
	}

	public void subtract(Polynomial rhs) {
		for(Map.Entry m:rhs.expression.entrySet()){
			int power = (int)m.getKey();
			int coefficient = (int)m.getValue();
			if(power>degree) {
				degree = power;
			}
			if(expression.containsKey(power)){
				expression.replace(power, ((int)expression.get(power))-coefficient);
				if(((int)expression.get(power))==0) {
					expression.remove(power);
				}
			}
			else {
				expression.put(power,-1*coefficient);
			}
		}
	}
	public static Polynomial subtract(Polynomial lhs, Polynomial rhs) {
		Polynomial result = new Polynomial(lhs);
		result.subtract(rhs);
		return result;
	}
	public Polynomial multiply(Polynomial rhs) {
		Polynomial result = new Polynomial();
		result.degree = -1;
		result.expression = new TreeMap<Integer, Integer>(Collections.reverseOrder());
		for(Map.Entry m:expression.entrySet()){
			for(Map.Entry n:rhs.expression.entrySet()){
			    int d = (int) m.getKey() + (int) n.getKey();
			    int c = (int) m.getValue() * (int) n.getValue();
			    if(d>result.degree) {
			    	result.degree = d;
			    }
			    if(result.expression.containsKey(d)){
					result.expression.replace(d,result.expression.get(d)+c);
				}
				else {
					result.expression.put(d,c);
				}
			}    
		}
		return result;
	}
	public int getDegree() {
		return degree;
	}
	public int coefficient(int power) {
		if(expression.containsKey(power)) {
			return (int)expression.get(power);
		}
		else {
			return 0;
		}
	}
	public void changeCoefficient(int power, int coefficient) {
		if(expression.containsKey(power)){
			expression.replace(power,coefficient);
		}
	}
	public void removeTerm(int power) {
		expression.remove(power);
	}
	public double evaluate(int x) {
		double result = 0;
		for(Map.Entry m:expression.entrySet()){
			result+=Math.pow(x, (int)m.getKey())*(int)m.getValue();
		}
		return result;
	}
	@Override
    public boolean equals(Object o) { 
        if (o == this) {
            return true;
        }
        if (!(o instanceof Polynomial)) {
            return false;
        }
        Polynomial p = new Polynomial((Polynomial) o);
        if(expression.size()==p.expression.size()) {
        	for(Map.Entry m:expression.entrySet()){
    			int power = (int)m.getKey();
    			int coefficient = (int)m.getValue();
    			if(!p.expression.containsKey(power)||((int)p.expression.get(power))!=coefficient) {
    				return false;
    			}
    		}
        }
        else {
        	return false;
        }
        return true;
    }
	@Override
    public String toString() {
		String exp = "";
		for(Map.Entry m:expression.entrySet()){
			int power = (int)m.getKey();
			int coefficient = (int)m.getValue();
			if(coefficient>1) {
				exp+="+ "+coefficient;
			}
			else if(coefficient==-1) {
				exp+="- ";
			}
			else if(coefficient<0) {
				exp+="- "+(-1*coefficient);
			}
			if(power>1) {
				exp+="x"+power+" ";
			}
			else if (power == 1) {
				exp+="x ";
			}
		}
		if(exp.charAt(0)=='+') {
			return exp.substring(2);
		}
        return exp;
    }
	@Override
	public int compareTo(Polynomial p) {
		double result = evaluate(0)-p.evaluate(0);
		if(result>0) {
			return 1;
		}
		else if(result<0) {
			return -1;
		}
		else {
			return 0;
		}
	}
}
