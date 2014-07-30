import java.lang.reflect.Field;

import sun.misc.Unsafe;

public class UnsafeSupport {
    public static Unsafe getUnsafeInstance() throws SecurityException,
    NoSuchFieldException, IllegalArgumentException,
    IllegalAccessException {
   Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
   theUnsafeInstance.setAccessible(true);
   return (Unsafe) theUnsafeInstance.get(Unsafe.class);
  }
}
