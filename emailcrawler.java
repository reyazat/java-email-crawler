import java.net.*;
import java.io.*;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.AuthenticationFailedException;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.FolderNotFoundException;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.ReadOnlyFolderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.StoreClosedException;
import javax.mail.internet.InternetAddress;
import javax.mail.Address;
import javax.mail.*; 

import java.util.*;
import java.util.regex.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.*;
import java.lang.InterruptedException;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List; 
import java.util.Properties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.FileInputStream;

import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

class JDBCTest {
        private static final String url = "jdbc:mysql://localhost:3306/databasename?useUnicode=true&characterEncoding=UTF-8";

        private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  

        private static final String user = "user";

        private static final String password = "password";

        public static String robotname ="Email_Crawler";


        public void echo(String data) {System.out.println(data);}  


        public static void errorquery(int getid , String errormessage){
        Connection con = null;
        try {
        con = DriverManager.getConnection(url, user, password);
        String sql;
            
        sql ="UPDATE om_importemail SET errorstatus ="+errormessage+"  WHERE  id = "+getid;
        con.createStatement().executeUpdate(sql);

        con.createStatement().close();
        con.close();	
        } catch (Exception e) {
        e.printStackTrace();
        }finally{
        try{
        if(con.createStatement()!=null)
        con.createStatement().close();
        }catch(SQLException se2){
        }
        try{
        if(con!=null)
        con.close();
        }catch(SQLException se){
        se.printStackTrace();
        }
        }
        }

        public static  int idowner;
        public static  int idmaker;
        public static  String leadsource;
        public static  String ownerproduct;
        public static  String industry;
        public static  String email;
        public static  String imap;
        public static  int port;
        public static  int counter;
        public static  int already;
        public static  String alreadyid;

        public static void startquery(int getid){
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, user, password);
           
        String sql;
        sql ="SELECT * FROM  om_importemail  WHERE id ="+getid+" and action='start'";

        ResultSet rsultcheckstart = con.createStatement().executeQuery(sql);

         while (rsultcheckstart.next())
         {  
                 idowner = rsultcheckstart.getInt("idowner");
                 idmaker = rsultcheckstart.getInt("idmaker");
                 leadsource = rsultcheckstart.getString("leadsource");
                 ownerproduct = rsultcheckstart.getString("ownerproduct");
                 industry = rsultcheckstart.getString("industry");
                 email = rsultcheckstart.getString("email").toLowerCase();
                 imap = rsultcheckstart.getString("imap").toLowerCase();
                 port = rsultcheckstart.getInt("port");
                 counter = rsultcheckstart.getInt("counter");
                 already = rsultcheckstart.getInt("already");
                 alreadyid = rsultcheckstart.getString("alreadyid");	
               }
              
        rsultcheckstart.close();	  

        con.createStatement().close();
        con.close();	
        } catch (Exception e) {
        e.printStackTrace();
        }finally{
        try{
        if(con.createStatement()!=null)
        con.createStatement().close();
        }catch(SQLException se2){
        }
        try{
        if(con!=null)
        con.close();
        }catch(SQLException se){
        se.printStackTrace();
        }
        }

        }	
	

    
	public static void myquery(String inserting_email, int getid){
		 Connection con = null;
		try {
			con = DriverManager.getConnection(url, user, password);
            String sql;
            sql = "SELECT id,idowner,idmaker FROM om_info WHERE idowner = "+idowner+" and email = '" + inserting_email+"'";
            ResultSet rsultexistscheck = con.createStatement().executeQuery(sql);
	
			
			if(!rsultexistscheck.next()){
			
			java.util.Date dt = new java.util.Date();

			java.text.SimpleDateFormat sdf = 
			new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String currentTime = "'" + sdf.format(dt) + "'";
			
		
            sql ="INSERT INTO om_info ( email, status, idowner, idmaker, importer, joindate  ) VALUES ( '" + inserting_email + "', 'Active','" +idowner + "','" +getid + "','importemail', " + currentTime +")";
			con.createStatement().executeUpdate(sql);
            
			sql ="SELECT id,idowner,idmaker FROM om_info WHERE idowner = "+idowner+" and email = '" + inserting_email+"'";
			ResultSet rsultfounding = con.createStatement().executeQuery(sql);
			 while (rsultfounding.next())
              {    int idinfo = rsultfounding.getInt("id");
			 
			sql ="INSERT INTO om_arrayinfo ( idinfo ,  industry, owner_product, lead_source  ) VALUES ( '" + idinfo + "','" + industry + "', '" + ownerproduct + "','" + leadsource + "')";
			con.createStatement().executeUpdate(sql);
                   }
			rsultfounding.close();
			
			sql ="UPDATE om_importemail SET counter = counter + 1 WHERE id = "+getid ;
			con.createStatement().executeUpdate(sql);
			
			}else { 
             int idalreadyinfo = rsultexistscheck.getInt("id");
            
            sql ="SELECT id,already,alreadyid FROM om_importemail WHERE id = "+getid;
			ResultSet rsultcounting = con.createStatement().executeQuery(sql);
			 while (rsultcounting.next())
              {
                int already = rsultcounting.getInt("already");
                already++;
                String idsalready = rsultcounting.getString("alreadyid");
                 
                if(idsalready != null){
                idsalready = idsalready + "," +already;
                }else{
                idsalready = "" +already;
                }
              
				sql ="UPDATE om_importemail SET already = "+ already +" , alreadyid = '"+idsalready+"' WHERE id = " + getid ;
			con.createStatement().executeUpdate(sql);
                   }
			rsultcounting.close();
            }
			
			
	  rsultexistscheck.close();
      con.createStatement().close();
      con.close();	
		} catch (Exception e) {
            e.printStackTrace();
        }finally{
      try{
         if(con.createStatement()!=null)
            con.createStatement().close();
      }catch(SQLException se2){
      }
      try{
         if(con!=null)
            con.close();
      }catch(SQLException se){
         se.printStackTrace();
      }
   }
	}
 
    
    
	public static void updatealready(int getid){
		 Connection con = null;
		try {
			con = DriverManager.getConnection(url, user, password);
            String sql;
				
			sql ="UPDATE om_importemail SET already = 0 , alreadyid  = NULL , errorstatus = NULL WHERE id = "+getid;
			con.createStatement().executeUpdate(sql);
			
	  con.createStatement().close();
      con.close();	
		} catch (Exception e) {
            e.printStackTrace();
        }finally{
      try{
         if(con.createStatement()!=null)
            con.createStatement().close();
      }catch(SQLException se2){
      }
      try{
         if(con!=null)
            con.close();
      }catch(SQLException se){
         se.printStackTrace();
      }
   }
	}
 
    
    public static void endquery(int getid){
		 Connection con = null;
		try {
			con = DriverManager.getConnection(url, user, password);
            String sql;
				
			sql ="UPDATE om_importemail SET action ='end'  WHERE  id = "+getid;
			con.createStatement().executeUpdate(sql);
					
	  con.createStatement().close();
      con.close();	
		} catch (Exception e) {
            e.printStackTrace();
        }finally{
      try{
         if(con.createStatement()!=null)
            con.createStatement().close();
      }catch(SQLException se2){
      }
      try{
         if(con!=null)
            con.close();
      }catch(SQLException se){
         se.printStackTrace();
      }
   }
	}
	
}


public class emailcrawler {


public void echo(String data) {System.out.println(data);}

public Pattern p = Pattern.compile("\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b",Pattern.CASE_INSENSITIVE);

public void processMail(int n) {
   Session session = null;
   Store store = null;
   Folder folder = null;
   MimeMessage message = null;
   Message[] messages = null;
   int emailcount = 0;
   Object messagecontentObject = null;
   String sender = null;
   String body = null;
   String subject = null;
   Multipart multipart = null;
   Part part = null;
   String contentType = null;
        
   JDBCTest.startquery(getid);
   echo("--------------processing mails started-----------------");
   try {
      try{
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imap");
        props.setProperty("mail.mime.base64.ignoreerrors", "true");
        props.setProperty("mail.imap.ssl.enable", "true");
        props.setProperty("mail.imap.partialfetch", "false");
        props.setProperty("mail.imaps.partialfetch", "false");
        props.setProperty("mail.mime.ignoreunknownencoding", "true");
        session = Session.getInstance(props, null);
     
     // session = Session.getDefaultInstance(props, null);

      store = session.getStore("imap");
        } catch (Exception e) {
            echo("11==>"+e);
        }
   try{
      store.connect(JDBCTest.imap,JDBCTest.email,mailpass);
        } catch (Exception e) {
            echo("22==>"+e);
        String errorstr = ""+e;
        errorstr = errorstr.toLowerCase();
        String errorfound = "incorrect username or password";
        int b = errorstr.indexOf(errorfound);
        if(b != -1){JDBCTest.errorquery(getid , "'Inbox Folder Not Found Or Your Inbox Is Empty!!'"); }
        
        errorfound = "imap error";
        int c = errorstr.indexOf(errorfound);
        if(c != -1){ JDBCTest.errorquery(getid , "'Inbox Folder Not Found Or Your Inbox Is Empty!!'");}
        System.exit(0);
        }
 if (store.isConnected()) { 
    try{
      folder = store.getDefaultFolder();
        }
    catch (Exception e) {
        echo("getfolderdef");
     JDBCTest.errorquery(getid , "'Inbox Folder Not Found Or Your Inbox Is Empty!!'");
     System.exit(0);
  }

if (folder == null){ 
echo("folder null");
     JDBCTest.errorquery(getid , "'Inbox Folder Not Found Or Your Inbox Is Empty!!'");
     System.exit(0);
     }
     try{
      folder = folder.getFolder("inbox");
            } catch (Exception e) {
                echo("get inbox folder");
           JDBCTest.errorquery(getid , "'Inbox Folder Not Found Or Your Inbox Is Empty!!'"); 
            System.exit(0);
       }
  if (folder == null){ 
  echo("inbox null");
     JDBCTest.errorquery(getid , "'Inbox Folder Not Found Or Your Inbox Is Empty!!'");
     processMail(0);
     }
     try{
     folder.open(Folder.READ_WRITE);
         } catch (Exception e) {
       echo("open folder");
      processMail(0);
       }
        try{
    messages = folder.getMessages();
        } catch (Exception e) {
      echo("get All message");
      processMail(0);
       }
JDBCTest.updatealready(getid);
      for (int messageNumber = n; messageNumber < messages.length; messageNumber++) {
        emailcount = messageNumber;
        message = (MimeMessage)messages[messageNumber];
     try{
           sender = ((InternetAddress) message.getFrom()[0]).getPersonal();
        } catch (Exception e) {
               }   
               if (sender == null) {
                   try{
               sender = ((InternetAddress) message.getFrom()[0]).getAddress();
                } catch (Exception e) {
                                        }   
                 }
           if (sender != null) { 
             Matcher matcher = p.matcher(sender.toUpperCase());
		        while(matcher.find()) {
                    JDBCTest.myquery(""+matcher.group(0).toLowerCase()+"" , getid);
                        }
           }
            
         try{ body = getText(message) ;
             } catch (IOException e) {
               echo("content not get message");
               continue;  
             }
               catch (Exception e) {
              echo("content not get message");
              continue;
               }   
            
              if((body)!= null){

                Matcher matcher = p.matcher(body.toUpperCase());
		        while(matcher.find()) {
                    JDBCTest.myquery(""+matcher.group(0).toLowerCase()+"" , getid);                     
                        }
                
				} 

      }
 
      try{
      folder.close(true);
      JDBCTest.endquery(getid);
      } catch (Exception e) {}
 }else{echo("connect loose");}
      // Close the message store
      store.close();
  } catch(AuthenticationFailedException e) {
     echo("1");
     emailcount++;
     processMail(emailcount);
     e.printStackTrace();
  } catch(FolderClosedException e) {
     echo("2");
     e.printStackTrace();
  } catch(FolderNotFoundException e) {
     echo("3");
     e.printStackTrace();
  }  catch(NoSuchProviderException e) {
     echo("4");
     e.printStackTrace();
  } catch(ReadOnlyFolderException e) {
     echo("5");
     e.printStackTrace();
  } catch(StoreClosedException e) {
     echo("6");
     emailcount++;
     processMail(emailcount);
     e.printStackTrace();
  } catch (Exception e) {
     echo("7==>"+e);
    emailcount++;
     processMail(emailcount);
     e.printStackTrace();
  }
}

private String getText(Part p) throws  MessagingException, IOException {
        boolean textIsHtml = false;
    String s;
    Multipart mp;
   if (p.isMimeType("text/*")) {
       try{
        s = (String)p.getContent();
       }catch (IOException e) {return null;}
       catch (Exception e) {return null;}
           
        textIsHtml = p.isMimeType("text/html");
        return s;
    }
    if (p.isMimeType("multipart/alternative")) {
        try{
        mp = (Multipart)p.getContent();
        }catch (IOException e) {return null;}
       catch (Exception e) {return null;}
        String text = null;
        for (int i = 0; i < mp.getCount(); i++) {
            Part bp = mp.getBodyPart(i);
            if (bp.isMimeType("text/plain")) {
                if (text == null)
                    text = getText(bp);
                
            } else if (bp.isMimeType("text/html")) {
                 s = getText(bp);
                if (s != null)
                    return s;
            } else {
                return getText(bp);
            }
        }
        return text;
    } else if (p.isMimeType("multipart/*")) {
        try{
        mp = (Multipart)p.getContent();
        }catch (IOException e) {return null;}
       catch (Exception e) {return null;}
        for (int i = 0; i < mp.getCount(); i++) {
            s = getText(mp.getBodyPart(i));
            if (s != null)
                return s;
        }
    }else if (p.isMimeType("message/rfc822")) {
     try{
     getText((Part) p.getContent());
     }catch (IOException e) {return null;}
       catch (Exception e) {return null;}
  } 
    return null;
}

	
public static  int getid ;
public static  String mailpass ;  
//Main  Function for The readEmail Class

public static void main(String args[]) {
    String gettext[] = new String[10];
    for(int i = 0; i < args.length; i++) {
        gettext[i] = args[i];
        }
    getid = Integer.parseInt(gettext[0]);
    mailpass = gettext[1];
   
    emailcrawler readMail = new emailcrawler();
    readMail.processMail(0);
}
 
}