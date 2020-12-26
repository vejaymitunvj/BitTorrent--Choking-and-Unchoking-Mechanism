import java.util.ArrayList;

public class InitializingConfigData
{
	static int preferredNeighborsCount;
	static int unchokingIntervalValue;
	static int optimisticUnchokingIntervalValue;
	static int sizeOfFile;
	static int sizeOfFilePiece;
	String nameOfFile;
	int noOfConfigDataLines;
	ArrayList<String> dataList;
	static int totalNoOfPieces;

	public InitializingConfigData()
	{
		this.noOfConfigDataLines = 6;
		this.nameOfFile = null;
		InitializingConfigData.preferredNeighborsCount = 0;
		InitializingConfigData.unchokingIntervalValue = 0;
		InitializingConfigData.optimisticUnchokingIntervalValue = 0;
		InitializingConfigData.sizeOfFile = 0;
		InitializingConfigData.sizeOfFilePiece = 0;
		dataList = new ArrayList<String>();

	}

	public void assignValues(ArrayList<String> data)
	{

		int dataSize = data.size();

		if (dataSize < noOfConfigDataLines)
		{
			System.out.println("Missing Common Config Data");
			return;
		}

		dataList = data;

		for (int num = 0; num < noOfConfigDataLines; num++)
		{
			updateConfigData(num);
		}

	}

	public void updateConfigData(int number)
	{

		if (number > 6)
		{
			System.out.println("Incorrect Data line");
			return;
		}

		String lineData;
		String[] segmentlineData;
		String dataHeaderStr;
		int dataValue = 1;
		int dataHeader = 0;

		switch (number)
		{

			case 0:

				lineData = dataList.get(0);
				segmentlineData = lineData.split(" ");
				dataHeaderStr = segmentlineData[dataHeader];
				InitializingConfigData.preferredNeighborsCount = Integer.parseInt(segmentlineData[dataValue]);
				break;

			case 1:

				lineData = dataList.get(1);
				segmentlineData = lineData.split(" ");
				dataHeaderStr = segmentlineData[dataHeader];
				InitializingConfigData.unchokingIntervalValue = Integer.parseInt(segmentlineData[dataValue]);
				break;

			case 2:

				lineData = dataList.get(2);
				segmentlineData = lineData.split(" ");
				dataHeaderStr = segmentlineData[dataHeader];
				InitializingConfigData.optimisticUnchokingIntervalValue = Integer.parseInt(segmentlineData[dataValue]);
				break;

			case 3:

				lineData = dataList.get(3);
				segmentlineData = lineData.split(" ");
				dataHeaderStr = segmentlineData[dataHeader];
				this.nameOfFile = segmentlineData[dataValue];
				break;

			case 4:

				lineData = dataList.get(4);
				segmentlineData = lineData.split(" ");
				dataHeaderStr = segmentlineData[dataHeader];
				InitializingConfigData.sizeOfFile = Integer.parseInt(segmentlineData[dataValue]);
				break;

			case 5:

				lineData = dataList.get(5);
				segmentlineData = lineData.split(" ");
				dataHeaderStr = segmentlineData[dataHeader];
				InitializingConfigData.sizeOfFilePiece = Integer.parseInt(segmentlineData[dataValue]);
				break;

		default:

			System.out.println("Incorrect Data Line");

		}

	}

	public static int getPreferredNeighborsCount()
	{
		return preferredNeighborsCount;
	}

	public static int getUnchokingIntervalValue()
	{
		return unchokingIntervalValue;
	}

	public static int getOptimisticUnchokingIntervalValue()
	{
		return optimisticUnchokingIntervalValue;
	}

	public String getNameOfFile() {
		return nameOfFile;
	}

	public int getSizeOfFile()
	{
		return sizeOfFile;
	}

	public int getSizeOfFilePiece()
	{
		return sizeOfFilePiece;
	}

	public static int getTotalNoOfPieces()
	{

		InitializingConfigData.totalNoOfPieces = (int) Math.ceil(InitializingConfigData.sizeOfFile / InitializingConfigData.sizeOfFilePiece);

		return InitializingConfigData.totalNoOfPieces;
	}
	
	public void writeCommonConfigLog()
	{
		peerProcess.writePeerNodeLogsObj.writeCommonInfoConfigLogs(peerProcess.currPeerNodeID, InitializingConfigData.preferredNeighborsCount, 
				InitializingConfigData.unchokingIntervalValue, InitializingConfigData.optimisticUnchokingIntervalValue, this.nameOfFile, 
				InitializingConfigData.sizeOfFile, InitializingConfigData.sizeOfFilePiece);
	}
}
