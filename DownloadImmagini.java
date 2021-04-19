/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downloadimmagini;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.StringTokenizer;

/**
 *
 * @author Daviglia
 */
public class DownloadImmagini {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws URISyntaxException, IOException {
        
        String[] squqdre =new String[]{"Atalanta","Bologna","Brescia","Cagliari","Fiorentina","Genoa","Inter","Juventus","Lazio","Lecce","Milan", "Napoli","Parma","Roma"
        ,"Samptoria","Sassuolo","SPAl", "Torino","Udinese", "Verona"};
        //String[] squqdre= new String[]{"Alaves","Athletic-Bilbao","Athletico-Madrid","Barcellona","Celta-Vigo","Eibar","Espanyol","Getafe","Granada","Leganes","Levante",
        //    "Mallorca","Osasuna","Real-Betis","Real-Madrid","Real-Sociedad","Real-Vallodolid","Sevilla","Valencia","Villareal"};
        
        //String [] squqdre= new String[]{"Arsenal","Aston-Villa","Bournemouth","Brighton","Burnley","Chelsea","Crystal-Palace","Everton","Leicester","Liverpool","Manchester-City",
        //"Manchester-United","Newcastle-United","Norwich-City","Sheffield-United","Southampton","Tottenham","Watford","West-Ham-United","Wolverhampton"}; 
        for(int i=0; i<20; i++){
            creaElencoImmagini(squqdre[i]);
            downloadImmagine(squqdre[i]); 
        }

    }

    public static void creaElencoImmagini(String nomeSquadra) throws FileNotFoundException, IOException {
        File file = new File("D:\\NetBeans\\provaFrontend\\DownloadImmagini\\src\\downloadimmagini\\SerieA\\" + "html" + nomeSquadra + ".txt");
        File fileTmp = new File("D:\\NetBeans\\provaFrontend\\DownloadImmagini\\src\\downloadimmagini\\SerieA\\" + nomeSquadra + ".txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fileTmp)));
        String line = " ";
        StringTokenizer st = new StringTokenizer("");
        while ((line = br.readLine()) != null) {
            if (line.trim().contains("https://s.hs-data.com/bilder/spieler/klein/")) {
                if (line.trim().contains("<a href=\"/profilo_giocatori/")) {
                String nome=line.substring(line.indexOf("title=")); 
                nome=nome.substring(7);
                st = new StringTokenizer((line.substring(line.indexOf("https://s.hs-data.com/bilder/spieler/klein/"))) );
                StringTokenizer stNome = new StringTokenizer(nome,"\"");
                
                nome=stNome.nextToken(); 
                
                String tmp = st.nextToken();
                pw.write(tmp.substring(0, (tmp.indexOf("spieler/")+8))+"gross/"+tmp.substring(tmp.indexOf("klein/")+6,tmp.length()-1) +""+" nome: "+nome+"\n");
            }
            }
        }
        br.close();
        pw.close();
    }

    public static void cercaLink(String url) throws URISyntaxException, IOException {

    }

    public static void downloadImmagine(String nomeSquadra) throws FileNotFoundException, IOException {

        String remoteFile = "D:\\NetBeans\\provaFrontend\\DownloadImmagini\\src\\downloadimmagini\\SerieA\\" + nomeSquadra;

        File input = new File(remoteFile+".txt");
        BufferedReader bf = new BufferedReader(new FileReader(input));
        String line = "";
        while ((line = bf.readLine()) != null) {
            try {
                StringTokenizer str= new StringTokenizer(line," "); 
                System.out.println(line);
                URL website = new URL(str.nextToken());
                str.nextToken(); 
                
                String tmp=""; 
                String nome=""; 
                try{
                while((tmp=str.nextToken())!=null){
                nome=nome+" "+tmp;
                }
                }
                catch( Exception e ){}
                System.out.println("nome: "+ nome);
                
                String estensione= line.substring(line.indexOf("gross/"));
                estensione=estensione.substring(line.indexOf("."));
                StringTokenizer stEst= new StringTokenizer(estensione); 
                estensione=stEst.nextToken(); 
                try (
                        ReadableByteChannel rbc = Channels.newChannel(website.openStream());  FileOutputStream fos = new FileOutputStream(new File(remoteFile+"\\"+nome+estensione))) {
                    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            //
        }
    }
    public static void modificaUrl(){
        
    }
}
