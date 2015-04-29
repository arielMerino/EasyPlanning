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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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

/**
 *
 * @author jano
 */
@Named(value = "cargarPlanDeEstudios")
//@Dependent
@RequestScoped
public class CargarPlanDeEstudios {

    private String ruta;
    private HSSFWorkbook workbook;
    //private Asignatura asignatura; //TODO interfaces para la persistencia
    private String aux;

    public CargarPlanDeEstudios() {
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
        aux = aux2[2].split(", size")[0];
        
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
        Asignatura asignatura = new Asignatura();
        List list = (List) sheetData.get(0);
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
        //aux = cell.getStringCellValue();
        //int num = (int) cell.getNumericCellValue();
        //aux = num+"";
        aux = asignatura.toString();
        
    }
}
