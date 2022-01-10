package me.imamapletree.extensions.adapters;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.MemorySection;

import me.imamapletree.Skyconomy;
import me.imamapletree.api.instances.Flag;

public class FlagSerializer implements AdapterInterface<Map<Flag, Integer>, Map<String, Integer>>
{
	private Skyconomy skyconomy = (Skyconomy) Skyconomy.getInstance();
	private static Skyconomy staticskyconomy = Skyconomy.getInstance();
	 @SuppressWarnings("unused")
    private List<String> n;
    @SuppressWarnings("unused")
    private List<String> n2;
    @SuppressWarnings("unused")
    private static List<String> sn;
    @SuppressWarnings("unused")
	private static List<String> sn2;
    
    @SuppressWarnings("unchecked")
	@Override
    public Map<Flag, List<String>> deserialize(final Object object) {
        final Map<Flag, List<String>> result = new HashMap<Flag, List<String>>();
        if (object == null) {
            return result;
        }
        if (object instanceof MemorySection) {
            final MemorySection section = (MemorySection)object;
            for (final String key : section.getKeys(false)) {
                
                skyconomy.getFlagManager().getFlag(key).ifPresent(flag -> n = result.put(flag, section.getStringList(key)));
            }
        }
        else {
        	for (final Map.Entry<String, List<String>> en : ((Map<String, List<String>>)object).entrySet()) {
                
                skyconomy.getFlagManager().getFlag(en.getKey()).ifPresent(flag -> n2 = result.put(flag, en.getValue()));
            }
        }
        return result;
    }
    
    @Override
    public Map<String, List<String>> serialize(final Object object) {
        final Map<String, List<String>> result = new HashMap<String, List<String>>();
        if (object == null) {
            return result;
        }
        @SuppressWarnings("unchecked")
		final Map<Flag, List<String>> flags = (Map<Flag, List<String>>)object;
        for (final Map.Entry<Flag, List<String>> en : flags.entrySet()) {
            if (en != null && en.getKey() != null) {
                result.put(en.getKey().getName(), en.getValue());
            }
        }
        return result;
    }
    
    @SuppressWarnings("unchecked")
	public static Map<Flag, List<String>> StaticDeserialize(final Object object) {
        final Map<Flag, List<String>> result = new HashMap<Flag, List<String>>();
        if (object == null) {
            return result;
        }
        if (object instanceof MemorySection) {
            final MemorySection section = (MemorySection)object;
            for (final String key : section.getKeys(false)) {
                
                staticskyconomy.getFlagManager().getFlag(key).ifPresent(flag -> sn = result.put(flag, section.getStringList(key)));
            }
        }
        else {
        	for (final Map.Entry<String, List<String>> en : ((Map<String, List<String>>)object).entrySet()) {
                staticskyconomy.getFlagManager().getFlag(en.getKey()).ifPresent(flag -> sn2 = result.put(flag, en.getValue()));
            }
        }
        return result;
    }
    
    public static Map<String, List<String>> StaticSerialize(final Object object) {
        final Map<String, List<String>> result = new HashMap<String, List<String>>();
        if (object == null) {
            return result;
        }
        @SuppressWarnings("unchecked")
		final Map<Flag, List<String>> flags = (Map<Flag, List<String>>)object;
        for (final Map.Entry<Flag, List<String>> en : flags.entrySet()) {
            if (en != null && en.getKey() != null) {
                result.put(en.getKey().getName(), en.getValue());
            }
        }
        return result;
    }
}