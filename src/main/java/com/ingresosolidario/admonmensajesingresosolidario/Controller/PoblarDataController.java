package com.ingresosolidario.admonmensajesingresosolidario.Controller;

import com.ingresosolidario.admonmensajesingresosolidario.models.Cliente;
import com.ingresosolidario.admonmensajesingresosolidario.repository.ClienteRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

@Controller
public class PoblarDataController {

    @Autowired
    private ClienteRepository clienteRep;

    @GetMapping("/")
    public String index() {
        return "insercionDatos";
    }

    @PostMapping("/uploadExcel")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> uploadExcel(@RequestParam("file") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();

        if (file.isEmpty()) {
            response.put("Titulo", "Error");
            response.put("Mensaje", "Por favor, seleccione un archivo Excel para subir.");
            return ResponseEntity.badRequest().body(response);
        }

        try (InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0); // Asumiendo que los datos están en la primera hoja

            Iterator<Row> rows = sheet.iterator();
            boolean headerFound = false;
            int cedulaCol = -1;
            int nombreCol = -1;
            List<Cliente> clientes = new ArrayList<>();

            while (rows.hasNext()) {
                Row currentRow = rows.next();

                if (!headerFound) {
                    // Buscar la fila de encabezados que contiene "CC" y "Nombre"
                    for (Cell cell : currentRow) {
                        if (cell.getCellType() == CellType.STRING) {
                            String cellValue = cell.getStringCellValue().trim();
                            if (cellValue.equalsIgnoreCase("CC")) {
                                cedulaCol = cell.getColumnIndex();
                            }
                            if (cellValue.equalsIgnoreCase("Nombre")) {
                                nombreCol = cell.getColumnIndex();
                            }
                        }
                    }

                    if (cedulaCol != -1 && nombreCol != -1) {
                        headerFound = true;
                    }
                } else {
                    // Procesar las filas de datos
                    Cell cedulaCell = currentRow.getCell(cedulaCol);
                    Cell nombreCell = currentRow.getCell(nombreCol);

                    String cedula = (cedulaCell != null) ? obtenerValorCelda(cedulaCell) : null;
                    String nombre = (nombreCell != null) ? obtenerValorCelda(nombreCell) : null;

                    if (cedula != null && nombre != null && !cedula.isEmpty() && !nombre.isEmpty()) {
                        clientes.add(new Cliente(cedula, nombre));
                    }
                }
            }

            if (!clientes.isEmpty()) {
                this.clienteRep.saveAll(clientes);
                response.put("Titulo", "Éxito");
                response.put("Mensaje", "Se han insertado " + clientes.size() + " registros exitosamente.");
            } else {
                response.put("Titulo", "Advertencia");
                response.put("Mensaje", "No se encontraron registros válidos para insertar.");
            }

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("Titulo", "Error");
            response.put("Mensaje", "Ocurrió un error al procesar el archivo Excel.");
            return ResponseEntity.status(500).body(response);
        }
    }

    private String obtenerValorCelda(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {

                    return String.valueOf((long) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
            case _NONE:
            case ERROR:
            default:
                return "";
        }
    }

}
