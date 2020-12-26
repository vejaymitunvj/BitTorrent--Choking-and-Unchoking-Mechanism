
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.io.*;


public class peerProcess
{

	static CommonFileReader commonFileReaderObj;
	static InitializingConfigData initializingConfigDataObj;
	static BitTorrentMessageTypes bitTorrentMsgTypesObj;
	public static LinkedHashMap<Integer, PeerNodeObject> peerNodeTable;
	public static ConcurrentHashMap<Integer, NeighborNodeObject> neighborNodeTable;
	public static CopyOnWriteArrayList<Integer> peerNodeIDWithFullFile;
	public static WritePeerNodeLogs writePeerNodeLogsObj;
	public static PeerNodeObject currPeerNodeObj;
	public static int currPeerNodeID;
	public static int noOfPeersWithFullFile = 0;
	public static boolean terminateProgram = false;
		
	public peerProcess()
	{
		// TODO Auto-generated constructor stub
	}
	
	public static void createPeerNodeDirectory() throws IOException
	{
		File currPeerNodeDirectory = new File(PreFixedValues.FILE_NAME + currPeerNodeID);
		if(!currPeerNodeDirectory.exists())
		{
			currPeerNodeDirectory.mkdir();
		}
		File peerNodeLogFile = new File(PreFixedValues.LOG_FILE_NAME + currPeerNodeID + PreFixedValues.LOG_FILE_FORMAT);
		peerNodeLogFile.createNewFile();
		writePeerNodeLogsObj = new WritePeerNodeLogs(peerNodeLogFile);
	}
	
	public static void splitFileIntoPieces(int sizeOfFile, int sizeOfPiece, int noOfPieces) throws IOException
	{
		String dataFile = commonFileReaderObj.getFilePath() + PreFixedValues.getDownloadFile();
		
		FileInputStream fipsObj = new FileInputStream(dataFile);
		byte[] dataFileByteArr = new byte[sizeOfFile];
		fipsObj.read(dataFileByteArr);
		
		int copiedFileSize 	= 0;
		int filePieceIdx 	= 0;
		while(copiedFileSize < sizeOfFile)
		{
			int writingFilePtr = 0;
			if(copiedFileSize + sizeOfPiece <= sizeOfFile)
			{
				writingFilePtr = copiedFileSize+sizeOfPiece;
				
			}
			else
			{
				writingFilePtr = sizeOfFile;
			}
			byte[] pieceFileByteArr = Arrays.copyOfRange(dataFileByteArr, copiedFileSize, writingFilePtr);
			String dirFilePath = PreFixedValues.FILE_NAME + currPeerNodeID + File.separator + 
					PreFixedValues.DOWNLOAD_FILE_NAME + PreFixedValues.NAME_UNDERSCORE + filePieceIdx;
			File filePiece = new File(dirFilePath);
			FileOutputStream fopsObj = new FileOutputStream(filePiece);
			fopsObj.write(pieceFileByteArr);
			fopsObj.close();
			filePieceIdx++;
			copiedFileSize+= sizeOfPiece; 
		}
		fipsObj.close();
	}

	public static void setCurrPeerNodeBitField() throws IOException
	{
		int sizeOfFile 		= initializingConfigDataObj.getSizeOfFile();
		int sizeOfPiece 	= initializingConfigDataObj.getSizeOfFilePiece();
		double noOfPieces 	= (sizeOfFile * 1.0) / sizeOfPiece;
		int intValOfPieces	= (int) Math.ceil(noOfPieces);
		
		int[] peerBitFieldChecker = new int[intValOfPieces];
		boolean peerHasFile = currPeerNodeObj.peerHasFullFile();
		if(peerHasFile)
		{
			peerNodeIDWithFullFile.add(currPeerNodeID);
			noOfPeersWithFullFile++;
			for(int ptr=0; ptr<peerBitFieldChecker.length; ptr++)
			{
				peerBitFieldChecker[ptr] = 1;
			}
			currPeerNodeObj.updatePeerBitFieldChecker(peerBitFieldChecker);
			currPeerNodeObj.setFilePiecesRcvdCount(peerBitFieldChecker.length);
			currPeerNodeObj.updatePeerHasFullFile(1);
			splitFileIntoPieces(sizeOfFile, sizeOfPiece, intValOfPieces);
			peerProcess.writePeerNodeLogsObj.writePeerInfoConfigLogs(peerProcess.currPeerNodeID, true);
		}
		else
		{
			for(int ptr=0; ptr<peerBitFieldChecker.length; ptr++)
			{
				peerBitFieldChecker[ptr] = 0;
			}
			currPeerNodeObj.updatePeerBitFieldChecker(peerBitFieldChecker);
			peerProcess.writePeerNodeLogsObj.writePeerInfoConfigLogs(peerProcess.currPeerNodeID, false);
		}
	}

	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		bitTorrentMsgTypesObj 		= new BitTorrentMessageTypes();
		commonFileReaderObj 		= new CommonFileReader();
		initializingConfigDataObj 	= new InitializingConfigData();
		currPeerNodeObj				= new PeerNodeObject();
		
		ArrayList<String> commonCfgFileData = commonFileReaderObj.getFileData("Common.cfg");
		initializingConfigDataObj.assignValues(commonCfgFileData);
		
		peerNodeTable 			= new LinkedHashMap<>();
		neighborNodeTable		= new ConcurrentHashMap<>();
		peerNodeIDWithFullFile 	= new CopyOnWriteArrayList<>();
		
		ArrayList<String> peerInfoCfgFileData = commonFileReaderObj.getFileData("PeerInfo.cfg");
		for(String peerInfoStr : peerInfoCfgFileData)
		{
			PeerNodeObject newPeerNodeObj = new PeerNodeObject();
			int newPeerNodeID = newPeerNodeObj.initializePeerNodeObject(peerInfoStr);
			peerNodeTable.put(newPeerNodeID, newPeerNodeObj);
		}

		currPeerNodeID = Integer.parseInt(args[0]);
		currPeerNodeObj = peerNodeTable.get(currPeerNodeID);

		createPeerNodeDirectory();

		setCurrPeerNodeBitField();
		initializingConfigDataObj.writeCommonConfigLog();
		
		Thread clientChannel 		= new Thread(new ClientChannel());
		Thread serverChannel 		= new Thread(new ServerChannel());
		Thread unchokedChannel 		= new Thread(new UnchokedChannel());
		Thread optUnchokedChannel 	= new Thread(new OptimisticallyUnchokedChannel());
		
		clientChannel.start();
		serverChannel.start();
		unchokedChannel.start();
		optUnchokedChannel.start();
		
		try
		{
			while(!terminateProgram)
			{
				Thread.sleep(10000);
			}
		}
		catch(Exception exp)
		{
			System.out.println("Termination error");
		}
		peerProcess.writePeerNodeLogsObj.writeProcessCompletedTerminationLogs();
		System.out.println("Download Process Completed. Terminating Process");
		System.exit(0);
	}
}

