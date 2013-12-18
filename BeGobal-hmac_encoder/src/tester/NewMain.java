/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tester;

import hmac_encoder.LWAccess;
/**
 *
 * @author dghevde
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try{
            
            String fr_str = "<rsm><span class='xform_browse_doc_title beglobalYes' lang='fr' id='d8e4'>Ajouter Description de code d'erreur </span><pre class='xform_browse_doc_title beglobalYes' lang='fr' id='d8e15'>Ajouter Description de code d'erreur </pre></rsm>";
            //String strvg = LWAccess.TransEnglish2French("this is just english");
            String strvg = LWAccess.TransFrench2English(fr_str);
            
            System.out.println(strvg);
        }catch(Exception Exp){
            System.out.println(Exp.getMessage());
        }
        
        
    }
}
