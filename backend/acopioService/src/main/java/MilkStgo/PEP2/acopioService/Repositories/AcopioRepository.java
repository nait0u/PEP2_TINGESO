package MilkStgo.PEP2.acopioService.Repositories;

import MilkStgo.PEP2.acopioService.Entities.AcopioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository

public interface AcopioRepository extends JpaRepository<AcopioEntity, String> {
    @Query("select e from AcopioEntity e where e.proveedor = :codigo")
    ArrayList<AcopioEntity> findByCodigo(@Param("codigo") String codigo);

    @Query("select e from AcopioEntity e")
    ArrayList<AcopioEntity> getAll();

    @Query("select e from AcopioEntity e where e.proveedor = :codigo and e.IDarchivo = :id_archivo")
    ArrayList<AcopioEntity> findFiltro(Integer id_archivo, String codigo);

    @Query("select e.fecha from AcopioEntity e where e.IDarchivo = :id_archivo and e.proveedor = :codigo")
    ArrayList<String> fechasFiltro(Integer id_archivo, String codigo);

    @Query("select e.kls_leche from AcopioEntity e where e.proveedor = :codigo and e.IDarchivo = :id_archivo")
    ArrayList<Integer> kgsFiltro(Integer id_archivo, String codigo);

    @Query("select e.turno from AcopioEntity e where e.proveedor = :codigo and e.IDarchivo = :id_archivo")
    ArrayList<String> findTurnos(Integer id_archivo, String codigo);
}
