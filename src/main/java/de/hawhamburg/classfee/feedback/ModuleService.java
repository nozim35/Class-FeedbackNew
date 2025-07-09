package de.hawhamburg.classfee.feedback;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ModuleService {

    public List<Module> getAllModules() {
        return List.of(
                new Module(1L, "Mathematik 1"),
                new Module(2L, "Informatik 1"),
                new Module(3L, "Medienrecht"),
                new Module(4L, "Media Game Design 1"),
                new Module(5L, "Programmieren 1"),
                new Module(6L, "Dramaturgie 1")
        );
    }

    public String getModuleNameById(Long id) {
        return getAllModules().stream()
                .filter(m -> m.getId().equals(id))
                .map(Module::getName)
                .findFirst()
                .orElse("Unbekanntes Modul");
    }
}
