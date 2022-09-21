import java.io.*;

public class LaunchTest
{
	//listes des properties à transmettre
	private static String properties[]={"Nom","Prenom","Commune"};
	
	private static String fichiers[]={"alphabet","Random"};
	
	private static String randomString="RandomString.dat";
	private static String randomInt="int.dat";
	private static String alphabet="alphabet.dat";
	private static String emptyFile="empty.dat";
	
	private final static int MAX_USER = 5;
	private final static int NB_LOOPS = 40;
	
	private static String testPlan="nb3.jmx";
	
	public static void main(String argv[])
	{
		int nbUser=5;
		int id=1;
		String executeString="java -jar ApacheJMeter.jar -l log.jtl -JnbLoops="+NB_LOOPS+" ";
		File list=new File("list.dat");
		
		for(;nbUser<=MAX_USER;nbUser=nbUser+5)
		{
			for(int x=0;x< fichiers.length;x++)
			{
				if(list.exists())				
					list.delete();
				
				File courant=new File(fichiers[x]+".dat");
				try
				{
					copyFile(courant,list);
				}
				catch(Exception e )
				{
					System.out.println("ERROR : "+e);	
				}
				
				for(int j=0;j<properties.length;j++)
				{
					//DEBUG 
					j=2;
					String param ="";
					for(int z=0;z<properties.length;z++)
						param+=(z==j)?"-JnbThreads"+(z+1)+"="+nbUser+" ":"-JnbThreads"+(z+1)+"=0 ";
					String exec=executeString+" "+param+" -n -t "+testPlan;
					System.out.println(exec);
					try
					{
						System.out.println("Debut de test");
						Runtime.getRuntime().exec(exec).waitFor();
						System.out.println("Fin de test");
					}
					catch(Exception e)
					{
						System.out.println("ERROR : "+e);	
					}
					
					System.out.println("Debut de gestion de fichier");
					File tmp=new File("log.jtl");
					
					//destruction du fichier precedent s il existe.
					File result=new File(id+"-log-"+nbUser+"-"+fichiers[x]+"-"+properties[j]+".jtl");
					if(result.exists())
						result.delete();
					System.out.println("Debut de rennomage de fichier");					
					tmp.renameTo(result);			
					System.out.println("Fin de rennomage de fichier");
					
					
					/*tmp=new File(outGraph);
					tmp.renameTo(new File("Graph-"+nbUser+"-"+id+".txt"));
					tmp=new File(outResultTable);
					tmp.renameTo(new File("Table-"+nbUser+"-"+id+".txt"));
					tmp=new File(outAssert);
					tmp.renameTo(new File("Assert-"+nbUser+"-"+id+".txt"));*/
					
					id++;
				}
			}
		}
	}

	public static void copyFile(File in, File out) throws Exception 
	{
	    FileInputStream fis  = new FileInputStream(in);
	    FileOutputStream fos = new FileOutputStream(out);
	    byte[] buf = new byte[1024];
	    int i = 0;
	    while((i=fis.read(buf))!=-1) 
	    {
	      fos.write(buf, 0, i);
	    }
	    fis.close();
	    fos.close();
	}
}