package action;

import util.AnnotationProcessor;

public class SingletonAndAutowiredAction implements Action{

    @Override
    public void execute() {
        AnnotationProcessor annotationProcessor = new AnnotationProcessor();
        annotationProcessor.executeSingleton();
        System.out.println(AnnotationProcessor.CACHE.values() + " -> Singleton");
        annotationProcessor.executeAutowired();
        System.out.println(AnnotationProcessor.CACHE.values() + " -> Autowired");
    }
}