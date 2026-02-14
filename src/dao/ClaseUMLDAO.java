package dao;

import util.DBConnection;

import java.sql.CallableStatement;
import java.sql.Connection;

public class ClaseUMLDAO {

    public void guardarClase(int idDiagrama, String nombre) throws Exception {

        Connection con = DBConnection.getConnection();
        CallableStatement cs =
                con.prepareCall("{ call SP_GUARDAR_CLASE(?,?) }");

        cs.setInt(1, idDiagrama);
        cs.setString(2, nombre);

        cs.execute();

        cs.close();
        con.close();
    }
}
