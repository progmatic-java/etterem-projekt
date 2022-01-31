package java.hu.progmatic.kozos.etterem.rendeles;


import org.springframework.data.jpa.repository.JpaRepository;

import java.hu.progmatic.kozos.etterem.asztal.Asztal;
import java.util.List;

public interface RendelesRepository extends JpaRepository<Rendeles, Integer>{
    List<Rendeles> findAllByAsztal(Asztal asztal);
}
