package me.imamapletree.extensions.adapters;

import java.util.List;
import java.util.Map;

import me.imamapletree.api.instances.Flag;

public interface AdapterInterface<S, V> {
    public Map<Flag, List<String>> deserialize(Object var1);

    public Map<String, List<String>> serialize(Object var1);
}