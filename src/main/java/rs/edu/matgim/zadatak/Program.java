package rs.edu.matgim.zadatak;

import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        
         DB _db = new DB();
        _db.printUkupnaTezina();
        System.out.println('\n');
        
        DB __db = new DB();
        __db.printFirma();
        
        Scanner s = new Scanner(System.in);
        int IDPut = s.nextInt();
        _db.zadatak(IDPut);
        
    }
}
