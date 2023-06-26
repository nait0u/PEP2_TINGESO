package MilkStgo.PEP2.pagoFinalService.Controllers;

import MilkStgo.PEP2.pagoFinalService.Entities.PagoFinalEntity;
import MilkStgo.PEP2.pagoFinalService.Services.PagoFinalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/planilla")
public class PagoFinalController {

    @Autowired
    PagoFinalService pagoFinalService;

    @GetMapping("/generar/{codigo}")
    public ResponseEntity<Boolean> generarPlanilla(Model model, @PathVariable("codigo") String codigo)
    {
        PagoFinalEntity planilla = pagoFinalService.generarPlanilla(codigo);
        if (planilla == null)
        {
            System.out.println("Planilla nula");
            ResponseEntity.ok(false);
        }
        return ResponseEntity.ok(true);
    }

    @GetMapping("/obtener")
    public ResponseEntity<PagoFinalEntity> getAll()
    {
        PagoFinalEntity pagoFinal = pagoFinalService.getAll();
        return ResponseEntity.ok(pagoFinal);
    }

    @PostMapping("/eliminar")
    public void eliminarData(@RequestBody ArrayList<PagoFinalEntity> planilla)
    {
        pagoFinalService.eliminarData(planilla);
    }
}
