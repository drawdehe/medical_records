import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class PatientFileManager {
	public PatientFile readFile(String file) throws FileNotFoundException{
		Scanner scan = null;
		scan = new Scanner(new File(file+".txt"));

		String[] list = new String[5];
		for (int i = 0; i < 5; i++) {
			list[i] = scan.nextLine();
			System.out.println(list[i]);
		}
		return new PatientFile(list[0], list[1], list[2], list[3], list[4]);
	}

	//hey
	
}
