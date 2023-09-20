package apap.tutorial.bacabaca.repository;

import apap.tutorial.bacabaca.model.Buku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BukuDb extends JpaRepository<Buku, UUID> {

}
