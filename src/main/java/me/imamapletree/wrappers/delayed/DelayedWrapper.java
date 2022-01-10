package me.imamapletree.wrappers.delayed;

import me.imamapletree.wrappers.Wrapper;

public interface DelayedWrapper extends Wrapper {
	void execute(Object obj1, Object obj2, Object obj3);
}
