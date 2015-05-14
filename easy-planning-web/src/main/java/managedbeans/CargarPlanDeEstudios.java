/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import business.AsignaturasLocal;
import business.CarrerasLocal;
import entities.Asignatura;
import entities.Carrera;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.NotOLE2FileException;
import org.apache.poi.ss.usermodel.Cell;
import sessionbeans.AsignaturaFacadeLocal;
import sessionbeans.CarreraFacadeLocal;

/**
 *
 * @author jano
 */
@Named(value = "cargarPlanDeEstudios")
//@Dependent
@RequestScoped
public class CargarPlanDeEstudios implements Serializable {

    @EJB
    private AsignaturaFacadeLocal ejbFacade;
    
    @EJB
    private AsignaturasLocal asignaturas;
    
    @EJB
    private CarreraFacadeLocal carreraFacade;
    
    @EJB
    private CarrerasLocal carreraBusiness;
    
    private int carreraSelected;
    private String ruta;
    private HSSFWorkbook workbook;
    private String nombrePlan;
    //private Asignatura asignatura; //TODO interfaces para la persistencia
    private String aux;
    private boolean cargados = false;
    private List<Asignatura> asignaturasAñadidas = new ArrayList();

    public CargarPlanDeEstudios() {
    }
    
    public String getNombrePlan() {
        return nombrePlan;
    }

    public boolean isCargados() {
        return cargados;
    }

    public int getCarreraSelected() {
        return carreraSelected;
    }

    public void setCarreraSelected(int carreraSelected) {
        this.carreraSelected = carreraSelected;
    }
    
    public CarreraFacadeLocal getCarreraFacade() {
        return carreraFacade;
    }

    public void setCarreraFacade(CarreraFacadeLocal carreraFacade) {
        this.carreraFacade = carreraFacade;
    }
    
    public void setCargados(boolean cargados) {
        this.cargados = cargados;
    }

    public void setNombrePlan(String nombrePlan) {
        this.nombrePlan = nombrePlan;
    }
    
    
    private AsignaturaFacadeLocal getFacade(){
        return ejbFacade;
    }

    public String getAux() {
        return aux;
    }

    public void setAux(String aux) {
        this.aux = aux;
    }

    public String getRuta() {
        return ruta;
    }
    
    public void setRuta(String Ruta) {
        this.ruta = Ruta;
    }

    public CarrerasLocal getCarreraBusiness() {
        return carreraBusiness;
    }

    public void setCarreraBusiness(CarrerasLocal carreraBusiness) {
        this.carreraBusiness = carreraBusiness;
    }
    
    
    
    public void cargarArchivo() throws FileNotFoundException, IOException{
        List sheetData = new ArrayList();
        
        String[] aux2 = ruta.split("=", 5);
        boolean flag = false;
        
        
        try{
            FileInputStream file = new FileInputStream(aux2[2].split(", size")[0]);
            workbook = new HSSFWorkbook(file);
            HSSFSheet sheet = workbook.getSheetAt(0);

            Iterator rows = sheet.rowIterator();
            while (rows.hasNext()) {
                HSSFRow row = (HSSFRow) rows.next();
                Iterator cells = row.cellIterator();
                List data = new ArrayList();
                while (cells.hasNext()) {
                HSSFCell cell = (HSSFCell) cells.next();
                data.add(cell);
                }
                sheetData.add(data);
            }

            for (Object sheetData1 : sheetData) {
                Asignatura asignatura = new Asignatura();
                List list = (List) sheetData1;
                Cell cell = (Cell) list.get(0);
                try{
                    int num = (int) cell.getNumericCellValue();
                    asignatura.setCodigo(num+"");
                    cell = (Cell) list.get(1);
                    asignatura.setNombre(cell.getStringCellValue());
                    cell = (Cell) list.get(2);
                    asignatura.setTeoria((int) cell.getNumericCellValue());
                    cell = (Cell) list.get(3);
                    asignatura.setEjercicios((int) cell.getNumericCellValue());
                    cell = (Cell) list.get(4);
                    asignatura.setLaboratorio((int) cell.getNumericCellValue());
                    cell = (Cell) list.get(5);
                    asignatura.setNivel((int) cell.getNumericCellValue());
                    asignatura.setPlanEstudio(nombrePlan);
                    cell = (Cell) list.get(6);
                    asignatura.setCarrera(carreraBusiness.findByCodigo(carreraSelected));
                }
                catch(IllegalStateException ex5){
                    aux = "El archivo no contiene los campos requeridos o estos no se encuentran en el orden correcto. Los campos requeridos son código asignatura, nombre asignatura, teoría, ejercicio, laboratorio, nivel y requisitos, en ese mismo orden.";
                    flag = true;
                    break;
                }
                
                try{
                    switch(cell.getCellType()){
                        case Cell.CELL_TYPE_STRING:{
                            if(cell.getStringCellValue().equals("Ingreso") || cell.getStringCellValue().equals("")){
                                asignatura.setPrerequisitos(new ArrayList());
                            }
                            else {
                                String[] prerequisitos = cell.getStringCellValue().split(", "); 
                                List lista = new ArrayList();
                                for (int i=0; i<prerequisitos.length; i++){
                                    Asignatura aux = getBusiness().findByCarreraAndCodigoAndPlan(carreraSelected,prerequisitos[i], nombrePlan);
                                    if(aux != null){
                                        lista.add(aux);
                                    }
                                }
                                asignatura.setPrerequisitos(lista);
                            }
                            break;
                        }
                        case Cell.CELL_TYPE_NUMERIC:{
                            List lista = new ArrayList();
                            asignatura.setPrerequisitos(lista);
                            
                            Asignatura aux = getBusiness().findByCarreraAndCodigoAndPlan(carreraSelected,(int) cell.getNumericCellValue() +"", nombrePlan);
                            if(aux != null){
                                lista.add(aux);
                            }
                        }
                    }
                }
                catch(EJBException ex){
                    Throwable cause = ex.getCause();
                    if (cause != null) {
                        aux = cause.getLocalizedMessage();
                    }
                }
                if(!flag){
                    asignaturasAñadidas.add(asignatura);
                    persist(asignatura);
                }
            }
            if(!flag){
                aux = "Archivo cargado con éxito";
                cargados = true;                
            }
        }
        catch(NotOLE2FileException ex){
            String linea;
            BufferedReader buffer = new BufferedReader(new FileReader(aux2[2].split(", size")[0]));
            try{
                while((linea = buffer.readLine()) != null){
                    String[] elementos = linea.split(",");
                    Asignatura asignatura = new Asignatura();
                    asignatura.setCodigo(elementos[0]);                    
                    try{
                        asignatura.setNombre(elementos[1]);
                        asignatura.setTeoria(Integer.parseInt(elementos[2]));
                        asignatura.setEjercicios(Integer.parseInt(elementos[3]));
                        asignatura.setLaboratorio(Integer.parseInt(elementos[4]));
                        asignatura.setNivel(Integer.parseInt(elementos[5]));
                        asignatura.setPlanEstudio(nombrePlan);
                        if(elementos[6].equals("") || elementos[6].equals("Ingreso")){
                            asignatura.setPrerequisitos(new ArrayList());
                        }
                        else{
                            List lista = new ArrayList();
                            if(elementos.length == 8){
                                Asignatura aux = getBusiness().findByCarreraAndCodigoAndPlan(carreraSelected,elementos[6], nombrePlan);
                                if (aux != null)
                                    lista.add(aux);
                            }
                            else if(elementos.length == 9){
                                Asignatura aux = getBusiness().findByCarreraAndCodigoAndPlan(carreraSelected,elementos[6].substring(1), nombrePlan);
                                
                                if(aux != null)
                                    lista.add(aux);
                                Asignatura aux3 = getBusiness().findByCarreraAndCodigoAndPlan(carreraSelected,elementos[7].substring(1, elementos[7].length()-1), nombrePlan);
                                if(aux3 != null)
                                    lista.add(aux3);
                            }
                            else if(elementos.length > 9){
                                Asignatura aux = getBusiness().findByCarreraAndCodigoAndPlan(carreraSelected,elementos[6].substring(1), nombrePlan);
                                if (aux != null)
                                    lista.add(aux);
                                int i = 7;
                                while(i<elementos.length-2){
                                    Asignatura aux3 = getBusiness().findByCarreraAndCodigoAndPlan(carreraSelected,elementos[i].substring(1), nombrePlan);
                                    if (aux3 != null)
                                        lista.add(aux3);
                                    i++;
                                }
                                Asignatura aux3 = getBusiness().findByCarreraAndCodigoAndPlan(carreraSelected,elementos[i].substring(1, elementos[i].length()-1), nombrePlan);
                                if (aux3 != null)
                                    lista.add(aux3);                            
                            }

                            asignatura.setPrerequisitos(lista);
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException ex3){
                        aux = "El archivo tiene problemas internos de codificación o no corresponde con los formatos adecuados (CSV o XLS). Si el formato del archivo es el adecuado, por favor, ábralo con su editor de texto, guárdelo como un archivo csv o xls y vuelva a intentarlo.";
                        flag = true;
                        break;
                    }
                    catch(IllegalStateException | NumberFormatException ex5){
                        aux = "El archivo no contiene los campos requeridos o estos no se encuentran en el orden correcto. Los campos requeridos son código asignatura, nombre asignatura, teoría, ejercicio, laboratorio, nivel y requisitos, en ese mismo orden.";
                        flag = true;
                        break;
                    }
                    if(!flag){
                        asignaturasAñadidas.add(asignatura);
                        persist(asignatura);
                    }
                    
                }
                if(!flag){
                    aux = "Archivo cargado con éxito";
                    cargados = true;
                }
            }            
            catch(EJBException ex2){
                aux = "Ya existe un Plan de Estudios con el nombre '"+nombrePlan+"'";
            }
            
        }        
    }
    
    public void persist(Asignatura asignatura){
        try{
            getFacade().create(asignatura);
        }
        catch(EJBException ex){
            Throwable cause = ex.getCause();
            if (cause != null) {
                aux = cause.getLocalizedMessage();
            }
        }
    }
    
    public List<Asignatura> getAsignaturasAñadidas(){
        return asignaturasAñadidas;
    }
    
    public AsignaturasLocal getBusiness(){
        return asignaturas;
    }
}
