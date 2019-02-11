/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.dtos;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author estudiante
 */
public class CasoDeUsoDetail extends CasoDeUsoDTO implements Serializable
{
    //private List<CaminoDTO> caminosExcepcion;
    //private List<CaminoDTO> caminosAlternativos;
    private List<CondicionDTO> precondiciones;
    private List<CondicionDTO> postcondiciones;

    public CasoDeUsoDetail()
    {
        
    }
}