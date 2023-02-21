package action;

import lombok.Getter;

@Getter
public enum Actions {
    CREATE("Create cars", new CreateAction()),
    COMPARE("Compare cars", new CompareAction()),
    SHOW_ALL("Show all cars", new ShowAllAction()),
    SINGLETON_AND_AUTOWIRED_ACTION("Singleton and Autowired example", new SingletonAndAutowiredAction()),
    EXIT("Finish program", new ExitAction());

    private final String name;
    private final Action action;

    Actions(final String name, final Action action) {
        this.name = name;
        this.action = action;
    }

    public void execute()  {
        action.execute();
    }
}