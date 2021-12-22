package ua.iladrien.bakery.web.api.v0;

public interface IService<T> {
    T findById(Integer id);
}
