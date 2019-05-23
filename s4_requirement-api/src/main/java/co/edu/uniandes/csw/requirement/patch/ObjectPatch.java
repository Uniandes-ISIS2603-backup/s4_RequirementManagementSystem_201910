/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.patch;

/**
 *
 * @author jorgeandresesguerraalarcon
 */
public interface ObjectPatch {
 
  <T> T apply(T target) throws ObjectPatchException;

    public static class ObjectPatchException extends Exception 
    {
        public ObjectPatchException() {
            super("Error en el patcheo del objeto, pudo cambiar antes de que esta modificación entrase");
        }
    }
  
}
