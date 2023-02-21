package repository;
import java.util.Optional;
public interface Repository <T> {
    void save(final T car);
    T[] getAll();
    Optional<T> getById(final String id);
    void delete(final String id);
}