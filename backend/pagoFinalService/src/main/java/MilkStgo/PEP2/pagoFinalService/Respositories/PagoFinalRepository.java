package MilkStgo.PEP2.pagoFinalService.Respositories;

import MilkStgo.PEP2.pagoFinalService.Entities.PagoFinalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface PagoFinalRepository extends JpaRepository<PagoFinalEntity, String> {

    @Query(value = "select * from planilla e ORDER BY e.ID_PLANILLA DESC LIMIT 1", nativeQuery = true)
    ArrayList<PagoFinalEntity> find();
}
