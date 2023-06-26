package MilkStgo.PEP2.acopioService.Entities;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table (name= "acopio")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class AcopioEntity {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer IDACOPIO;
    private Integer IDarchivo;
    private String fecha;
    private String turno;
    private String proveedor;
    private Integer kls_leche;

}
