package MilkStgo.PEP2.acopioService.Services;

import MilkStgo.PEP2.acopioService.Entities.AcopioEntity;
import MilkStgo.PEP2.acopioService.Repositories.AcopioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.Generated;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AcopioService {

    @Autowired
    AcopioRepository acopioRepository;

    Integer IDArchivoAcopio =1;

    private final Logger logg = LoggerFactory.getLogger(AcopioService.class);

    @Generated
    public boolean guardar(MultipartFile file){
        String filename = file.getOriginalFilename();
        if(filename != null){
            if(!file.isEmpty()){
                try{
                    byte [] bytes = file.getBytes();
                    Path path  = Paths.get(file.getOriginalFilename());
                    Files.write(path, bytes);
                    logg.info("Archivo guardado");
                }
                catch (IOException e){
                    logg.error("ERROR", e);
                }
            }
            return true;
        }
        else{
            return false;
        }
    }

    @Generated
    public boolean leerCsvAcopio(String direccion){
        String texto = "";
        BufferedReader bf = null;
        //dataAcopioRepository.deleteAll();
        try{
            bf = new BufferedReader(new FileReader(direccion));
            String temp = "";
            String bfRead;
            int count = 1;
            while((bfRead = bf.readLine()) != null){
                if (count == 1){
                    count = 0;
                }
                else{
                    guardarDataDBAcopio(IDArchivoAcopio, bfRead.split(";")[0], bfRead.split(";")[1], bfRead.split(";")[2], Integer.parseInt(bfRead.split(";")[3]));
                    temp = temp + "\n" + bfRead;
                }
            }
            texto = temp;
            System.out.println("Archivo leido exitosamente");
            IDArchivoAcopio++;
            return true;
        }catch(Exception e){
            System.err.println(e);
        }finally{
            if(bf != null){
                try{
                    bf.close();
                }catch(IOException e){
                    logg.error("ERROR", e);
                }
            }
        }
        return false;
    }

    public void guardarDataAcopio(AcopioEntity data){
        acopioRepository.save(data);
    }

    public void guardarDataDBAcopio(Integer ID_archivo, String fecha, String turno, String proveedor, Integer kg_leche){
        AcopioEntity newData = new AcopioEntity();
        newData.setIDACOPIO(ID_archivo);
        newData.setFecha(fecha);
        newData.setTurno(turno);
        newData.setProveedor(proveedor);
        newData.setKls_leche(kg_leche);
        guardarDataAcopio(newData);
    }

    public ArrayList<AcopioEntity> obtenerAcopios(){
        return (ArrayList<AcopioEntity>) acopioRepository.findAll();
    }

    public int klsLeche(String codigo){
        int totalKgs = 0;
        ArrayList<AcopioEntity> acopios = acopioRepository.getAll();

        int id = obtenerCantArchivos(acopios);
        ArrayList<Integer> klsLeche = acopioRepository.kgsFiltro(id, codigo);

        for (Integer kg:klsLeche)
        {
            totalKgs = totalKgs + kg;
        }
        return totalKgs;
    }

    public int obtenerCantArchivos(ArrayList<AcopioEntity> acopios) {
        int cant = 0;
        ArrayList<Integer> aux = new ArrayList<>();
        for (AcopioEntity acopio : acopios) {
            if (!(aux.contains(acopio.getIDarchivo()))) {
                aux.add(acopio.getIDarchivo());
            }
        }
        cant = aux.size();
        return cant;
    }

    public ArrayList<String> obtenerFechas(String codigo){
        ArrayList<AcopioEntity> acopios = acopioRepository.getAll();
        int id = obtenerCantArchivos(acopios);
        ArrayList<String> fechas = acopioRepository.fechasFiltro(id, codigo);
        return fechas;
    }

    public ArrayList<String> obtenerVariacionLeche(String codigo){
        ArrayList<String> salida= new ArrayList<>();
        String variacion = "0";
        String variacionNum= "0";
        ArrayList<AcopioEntity> acopios = acopioRepository.getAll();
        int cantArchivos = obtenerCantArchivos(acopios);
        if (cantArchivos != 1){
            ArrayList<AcopioEntity> acopioActualFiltrado = acopioRepository.findFiltro(cantArchivos, codigo);
            ArrayList<AcopioEntity> acopioAnteriorFiltrado = acopioRepository.findFiltro(cantArchivos-1, codigo);
            if (!(acopioAnteriorFiltrado.isEmpty())){
                int kilosActual = 0;
                int kilosAnterior = 0;

                for(AcopioEntity acopio:acopioActualFiltrado){
                    kilosActual = kilosActual + acopio.getKls_leche();
                }

                for(AcopioEntity acopio:acopioAnteriorFiltrado){
                    kilosAnterior = kilosAnterior + acopio.getKls_leche();
                }
                variacionNum=Integer.toString(kilosActual-kilosAnterior);
                int porcentaje = ((kilosActual*100)/kilosAnterior);
                variacion = Integer.toString(porcentaje);
            }
        }
        salida.add(variacionNum);
        salida.add(variacion);
        return salida;
    }

    public ArrayList<Boolean> obtenerTurnos(String codigo){
        ArrayList<Boolean> salida = new ArrayList<>();
        ArrayList<AcopioEntity> acopios = acopioRepository.getAll();
        int id = obtenerCantArchivos(acopios);
        ArrayList<String> turnos = acopioRepository.findTurnos(id, codigo);
        if(turnos.contains("M")) {
            salida.add(true);
        }
        else{
            salida.add(false);
        }
        if(turnos.contains("T")) {
            salida.add(true);
        }
        else{
            salida.add(false);
        }

        return salida;
    }

    public void eliminarData(ArrayList<AcopioEntity>datas){
        acopioRepository.deleteAll(datas);
    }
}
