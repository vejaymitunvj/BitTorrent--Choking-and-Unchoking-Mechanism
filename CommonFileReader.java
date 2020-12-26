import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CommonFileReader
{
	String baserootFolderPath;
	ArrayList<String> data;

	public CommonFileReader()
	{
		baserootFolderPath = System.getProperty("user.dir") + File.separator;
	}

	public ArrayList<String> getFileData(String nameOfFile) throws FileNotFoundException, IOException
	{
		
		data = new ArrayList<String>();

		BufferedReader collectData = new BufferedReader(new FileReader(baserootFolderPath + nameOfFile));
		String inputData;
		
		while((inputData = collectData.readLine()) != null)
		{	
			data.add(inputData);
		}
		
		collectData.close();
		return data;
	}
	
	public String getFilePath()
	{
		return this.baserootFolderPath;
	}
}
