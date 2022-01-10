package me.imamapletree.tools;

public final class Validate
{
    private Validate() {
    }
    
    public static void notNull(final Object object, final String error) {
        if (object == null) {
            throw new NullPointerException(error);
        }
    }
    
    public static void isTrue(final boolean statement, final String error) {
        if (!statement) {
            throw new IllegalArgumentException(error);
        }
    }
    
    public static void isFalse(final boolean statement, final String error) {
        if (statement) {
            throw new IllegalArgumentException(error);
        }
    }
}