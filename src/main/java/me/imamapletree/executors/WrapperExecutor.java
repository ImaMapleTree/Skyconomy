package me.imamapletree.executors;

import me.imamapletree.wrappers.Wrapper;

public class WrapperExecutor implements Runnable {
	private Wrapper wrapper;
	private Object obj1;
	private Object obj2;
	private Object obj3;
	
	public WrapperExecutor(Wrapper wrapper, Object obj1, Object obj2, Object obj3) {
		this.wrapper = wrapper;
		this.obj1 = obj1;
		this.obj2 = obj2;
		this.obj3 = obj3;
	}
	
	@Override
	public void run() {
		this.wrapper.execute(obj1, obj2, obj3);
	}
}
