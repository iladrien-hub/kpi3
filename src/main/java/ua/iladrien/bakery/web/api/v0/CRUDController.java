package ua.iladrien.bakery.web.api.v0;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.iladrien.bakery.web.entities.IEntity;

@Controller
public abstract class CRUDController<T extends IEntity, R extends CrudRepository<T, Integer>> {

    // CRUD Repo
    @Autowired
    private R repository;

    // List
    @GetMapping
    public ResponseEntity<Iterable<T>> list() {
        return ResponseEntity.ok(repository.findAll());
    }

    // Create
    @PostMapping
    public ResponseEntity<Object> create(@ModelAttribute T t) {
        repository.save(t);
        return ResponseEntity.ok(t);
    }

    // Retrieve
    @GetMapping("/{id}")
    public ResponseEntity<Object> retrieve(@PathVariable Integer id) {
        return ResponseEntity.ok(repository.findById(id));
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Integer id, @ModelAttribute T t) {
        if (repository.existsById(id)) {
            t.setId(id);
            repository.save(t);
            return ResponseEntity.ok(t);
        }
        return ResponseEntity.ok(HttpStatus.NOT_FOUND);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok(HttpStatus.OK);
        }
        return ResponseEntity.ok(HttpStatus.NOT_FOUND);
    }

}
