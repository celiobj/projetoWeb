/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ultraBarber.model.dao;

/**
 *
 * @author celio.junior
 */
import java.sql.Connection;

public interface IDatabase {

    public Connection conectar(String cliente);

    public Connection conectar();

}
