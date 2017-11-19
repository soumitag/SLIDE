/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vtbase;

/**
 *
 * @author Soumita
 */
public class SlideException extends Exception {
    
    int error_code;
    
    public SlideException() {
        error_code = 0;
    }
    
    public SlideException (String message, int error_code) {
        super(message);
        this.error_code = error_code;
    }
    
    @Override
    public String getLocalizedMessage() {
        return getMessage();
    }
    
}
