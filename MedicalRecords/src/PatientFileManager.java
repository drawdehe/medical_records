import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class PatientFileManager {
	public PatientFile readFile(String file) throws FileNotFoundException{
		Scanner scan = null;
		scan = new Scanner(new File(file+".txt"));

		String[] list = new String[6];
		for (int i = 0; i < 6; i++) {
			list[i] = scan.nextLine();
			System.out.println(list[i]);
		}
		return new PatientFile(list[0], list[1], list[2], list[3], list[4], list[5]);
	}
	
	//////tog bort subject och har enbart patientSSN kvar
	public String deleteFile(String fileName) throws FileNotFoundException, IOException{
		File file = new File(fileName + ".txt");
		if(file.delete()){
			return "File deleted!";
		}else{
			return "File couldn't be deleted!";
		}
	}
	
	public void createFile(String fileName, PatientFile pf) throws FileNotFoundException {
		try {
			FileWriter fw = new FileWriter(new File(fileName + ".txt"));
			
			fw.write(pf.toString());
			fw.close();
		} catch (IOException e) {	
			e.printStackTrace();
		}
	}
	
	public void writeToFile(String fileName, String data) throws FileNotFoundException {
		PatientFile pf = readFile(fileName);
		System.out.println("Before: " + pf.toString());
		pf.appendMedicalText(data);
		System.out.println(pf.getMedicalInfo());
		System.out.println("After: " + pf.toString());
		
		createFile(fileName, pf);
	}
}
