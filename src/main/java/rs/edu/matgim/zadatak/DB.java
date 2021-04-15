package rs.edu.matgim.zadatak;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DB {

    String connectionString = "jdbc:sqlite:src\\main\\java\\KompanijaZaPrevoz.db";
    
  public void printFirma() {
        try (Connection conn = DriverManager.getConnection(connectionString); Statement s = conn.createStatement()) {

            ResultSet rs = s.executeQuery("SELECT * FROM Firma");
            while (rs.next()) {
                int IdFil = rs.getInt("IdFir");
                String Naziv = rs.getString("Naziv");
                String Adresa = rs.getString("Adresa");
                String Tel1 = rs.getString("Tel1");
                String Tel2 = rs.getString("Tel2");

                System.out.println(String.format("%d\t%s\t%s\t%s\t%s", IdFil, Naziv, Adresa, Tel1, Tel2));
            }

        } catch (SQLException ex) {
            System.out.println("Greska prilikom povezivanja na bazu");
            System.out.println(ex);
        }
    }
     public void printUkupnaTezina() {
        try (Connection conn = DriverManager.getConnection(connectionString); Statement s = conn.createStatement()) {

            ResultSet rs = s.executeQuery("SELECT P.IDPut, P.MestoOd, P.MestoDo, P.Duzina, sum(Tezina) FROM Putovanje P, Posiljka O, SePrevozi S WHERE S.IDPut=P.IDPut AND S.IDPos==O.IDPos GROUP BY P.IDPut");
            while (rs.next()) {
                int idput=rs.getInt(1);
                String mestood=rs.getString(2);
                String mestodo=rs.getString(3);
                int duzina=rs.getInt(4);
                int tezina=rs.getInt(5);

                System.out.println(String.format("%d\t%s\t%s\t%d\t%d", idput, mestood, mestodo, duzina, tezina));
            }

        } catch (SQLException ex) {
            System.out.println("Greska");
            System.out.println(ex);
        }
    }
    public int zadatak(int IdPut){
         int br=0;
         String s1 = "UPDATE Putovanje SET Status = 'P' WHERE IDPut = "+IdPut;
        String s2 = "SELECT IDZap FROM Mehanicar";
        String s3 = "SELECT IDZap FROM Popravlja";
        String s4 = "SELECT IDKam FROM Putovanje WHERE IDPut = "+IdPut;
        String s5 = "DELETE FROM SePrevozi WHERE IdPut = " + IdPut;
        String s6 = "UPDATE Kamion SET BrPopravljanja = BrPopravljanja + 1 WHERE IDKam = ?";
 
        try (Connection conn = DriverManager.getConnection(connectionString); 
             PreparedStatement ps = conn.prepareStatement(s1);
             PreparedStatement ps1 = conn.prepareStatement(s2);
             PreparedStatement ps2 = conn.prepareStatement(s3);
             PreparedStatement ps3 = conn.prepareStatement(s4);
             PreparedStatement ps4 = conn.prepareStatement(s5);
             PreparedStatement ps5 = conn.prepareStatement(s6)) {
            conn.setAutoCommit(false);
            List<Integer> IDMehanicari = new LinkedList<>();
            List<Integer> IDZauzeti = new LinkedList<>();
            
            ps.executeUpdate();
            
            ResultSet rs1 = ps1.executeQuery();
            while(rs1.next())
            {
                IDMehanicari.add(rs1.getInt(1));
            }
            
            ResultSet rs2 = ps2.executeQuery();
            while(rs2.next())
            {
                IDZauzeti.add(rs2.getInt(1));
            }
            IDMehanicari.removeAll(IDZauzeti); 
            br=IDMehanicari.size(); 
            ResultSet rs3 = ps3.executeQuery();
            //Insert :(
            ps4.execute();
           
            conn.commit();
            System.out.println("Uspesna realizacija");

        } catch (SQLException ex) {
            System.out.println("Dogodila se greska.");
            System.out.println(ex);
        }
        
        return br;
   
 
 
 
     }


}
