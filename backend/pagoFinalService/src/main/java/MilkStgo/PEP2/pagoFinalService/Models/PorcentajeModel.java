package MilkStgo.PEP2.pagoFinalService.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PorcentajeModel {

    private Integer ID_archivo;
    private String cod_proveedor;
    private Integer grasa;
    private Integer solido;
}
