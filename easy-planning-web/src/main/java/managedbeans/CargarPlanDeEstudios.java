/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.Asignatura;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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
import org.apache.poi.ss.usermodel.Cell;
import org.primefaces.model.UploadedFile;
import sessionbeans.AsignaturaFacadeLocal;

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
    private String ruta;
    private HSSFWorkbook workbook;
    private String nombrePlan;
    //private Asignatura asignatura; //TODO interfaces para la persistencia
    private String aux;

    public CargarPlanDeEstudios() {
    }

    public String getNombrePlan() {
        return nombrePlan;
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
    
    public void cargarArchivo() throws FileNotFoundException, IOException{
        List sheetData = new ArrayList();
        
        String[] aux2 = ruta.split("=", 5);
        //aux = aux2[2].split(", size")[0];
        aux = nombrePlan;
        
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
            asignatura.setPrerequisitos(new ArrayList());
            asignatura.setPlanEstudio(nombrePlan);
            persist(asignatura);
        }
        aux = "Archivo cargado con éxito";
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
}
