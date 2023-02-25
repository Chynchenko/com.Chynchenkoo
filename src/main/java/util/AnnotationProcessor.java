package util;
import annotation.Autowired;
import annotation.Singleton;
import lombok.ToString;
import org.reflections.Reflections;
import repository.Repository;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@ToString
public class AnnotationProcessor {
    public static final Map<Class, Object> CACHE = new HashMap<>();
    public void executeSingleton() {
        Reflections reflections = new Reflections("com.Chynchenkoo.repository");
        Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(Singleton.class);
        for (Class<?> aClass : classSet) {
            createAndCacheObject(aClass);
        }
    }

    public void executeAutowired() {
        Reflections reflections = new Reflections("com.Chynchenkoo");
        Set<Class<?>> typesAnnotatedWithSingleton = reflections.getTypesAnnotatedWith(Singleton.class);
        for (Class<?> aClass : typesAnnotatedWithSingleton) {
            Arrays.stream(aClass.getDeclaredConstructors())
                    .filter(constructor -> constructor.getDeclaredAnnotation(Autowired.class) != null)
                    .forEach(constructor -> {
                        constructor.setAccessible(true);
                        Class<? extends Repository> repository = constructor.getDeclaredAnnotation(Autowired.class).repository();
                        if (CACHE.get(repository) != null) {
                            System.out.println(repository);
                        } else if (CACHE.get(repository) == null) {
                            try {
                                Object o = constructor.newInstance(CACHE.get(repository));
                                System.out.println(o);
                                if (!CACHE.containsKey(aClass)) {
                                    CACHE.put(aClass, o);
                                    System.out.println("Object: " + o + " has been cached");
                                }
                            } catch (InstantiationException | IllegalAccessException |
                                     InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
    }

    private void createAndCacheObject(Class<?> aClass) {
        Method getInstance = null;
        try {
            getInstance = aClass.getDeclaredMethod("getInstance");
            getInstance.setAccessible(true);
            getInstance.invoke(aClass);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        CACHE.put(aClass, getInstance);
    }
}