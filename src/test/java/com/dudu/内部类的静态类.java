package com.dudu;


public class 内部类的静态类 {

	public static void main(String[] args) {
		/*try {
		Outer a=new Outer();
		a.i=new inner();
		a.i.a=100;
		
		Outer b=new Outer();
		b.i=new inner();
		b.i.a=200;
		System.out.println(a.i.a);
		System.out.println(b.i.a);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}*/
		Outer aOuter=new Outer();
		aOuter.a="love";
		aOuter.m=10;
		M(aOuter);
		System.out.println(aOuter.a);
		System.out.println(aOuter.m);
		
		
	}
	
	public static void M(Outer o) {
		o.a="hate";
		o.m=0;
	}
}
