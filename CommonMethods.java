import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

public class CommonMethods
{
	public static byte[] cloneBytePieceArr(byte[] bytePieceArr, int startIdx, int endIdx)
	{
		int cloneLength = endIdx - startIdx;
		int lengthDiff 	= bytePieceArr.length - startIdx;
		if(cloneLength < 0)
		{
			throw new IllegalArgumentException("End Index greater than Start Index"); 
		}
		
		byte[] clonedByteArr = new byte[cloneLength];
		System.arraycopy(bytePieceArr, startIdx, clonedByteArr, 0, Math.min(lengthDiff, cloneLength));
		
		return clonedByteArr;
	}
	
	public static byte[] createHandshakePkt(int peerNodeID)
	{
		byte[] handshakePkt 				= new byte[32];
		String handshakePktHeader			= PreFixedValues.getHandshakePktHeader();
		byte[] handshakePktHeaderByteArr	= new String(handshakePktHeader).getBytes();
		String paddingZeroes 				= PreFixedValues.getPaddingZeroes();
		byte[] paddingZeroesByteArr 		= new String(paddingZeroes).getBytes();
		byte[] peerNodeIDByteArr 			= ByteBuffer.allocate(4).putInt(peerNodeID).array();
		
		int ctr = 0;
		for(int ptr=0; ptr<handshakePktHeaderByteArr.length; ptr++)
		{
			handshakePkt[ctr] = handshakePktHeaderByteArr[ptr];
			ctr++;
		}
		for(int ptr=0; ptr<paddingZeroesByteArr.length; ptr++)
		{
			handshakePkt[ctr] = paddingZeroesByteArr[ptr];
			ctr++;
		}
		for(int ptr=0; ptr<peerNodeIDByteArr.length; ptr++)
		{
			handshakePkt[ctr] = peerNodeIDByteArr[ptr];
			ctr++;
		}
		
		return handshakePkt;
	}
	
	public synchronized static int pickRandomFilePieceIdx(int peerNodeID)
	{
		int[] reqPeerNodeBitField 	= peerProcess.peerNodeTable.get(peerNodeID).getPeerBitFieldChecker();
		int[] currPeerNodeBitField 	= peerProcess.currPeerNodeObj.getPeerBitFieldChecker();
		List<Integer> filePieceIdxList = new ArrayList<>();
		int randomFilePieceIdx = -1;
		
		for(int ptr=0; ptr<reqPeerNodeBitField.length; ptr++)
		{
			if(reqPeerNodeBitField[ptr] == 1 && currPeerNodeBitField[ptr] == 0)
			{
				filePieceIdxList.add(ptr);
			}
		}
		if(filePieceIdxList.size() > 0)
		{
			Random randomObj = new Random();
			randomFilePieceIdx = filePieceIdxList.get(randomObj.nextInt(filePieceIdxList.size()));
		}
		
		return randomFilePieceIdx;
	}
	
	public synchronized static List<Integer> getInterestedPeerNodeList()
	{
		List<Integer> neighborPeerNodeList = new ArrayList<>(peerProcess.neighborNodeTable.keySet());
		List<Integer> interestedPeerNodeList = new ArrayList<>();
		for(int ptr=0; ptr<neighborPeerNodeList.size(); ptr++)
		{
			if(peerProcess.neighborNodeTable.get(neighborPeerNodeList.get(ptr)).getPeerNodeInterestedValue())
			{
				interestedPeerNodeList.add(neighborPeerNodeList.get(ptr));
			}
		}
		return interestedPeerNodeList;
	}
	
	public synchronized static TreeMap<Double, Integer> getDownloadRatePeerNodeList(List<Integer> interestedPeerNodeList)
	{
		TreeMap<Double, Integer> downloadSpeedMap = new TreeMap<>();
		for(int ptr=0; ptr<interestedPeerNodeList.size(); ptr++)
		{
			NeighborNodeObject neighborNodeObj = peerProcess.neighborNodeTable.get(interestedPeerNodeList.get(ptr));
			if(neighborNodeObj.getFilePieceDownloadSpeed() > 0)
			{
				downloadSpeedMap.put(neighborNodeObj.getFilePieceDownloadSpeed(), neighborNodeObj.getPeerNodeID());
			}
		}
		return downloadSpeedMap;
	}
	
	public static synchronized byte[] getFilePiecesFromDir(int peerNodeID, int filePieceIdx) throws IOException
	{
		String dirFilePath = PreFixedValues.FILE_NAME + peerNodeID + File.separator + 
				PreFixedValues.DOWNLOAD_FILE_NAME + PreFixedValues.NAME_UNDERSCORE + filePieceIdx;
		File filePiece = new File(dirFilePath);
		FileInputStream fipsObj = new FileInputStream(filePiece);
		byte[] filePieceByteArr = new byte[(int)filePiece.length()];
		fipsObj.read(filePieceByteArr);
		fipsObj.close();
		return filePieceByteArr;
	}
	
	public static synchronized void writeFilePiecesToDir(int peerNodeID, int filePieceIdx, byte[] filePieceContent) throws IOException
	{
		String dirFilePath = PreFixedValues.FILE_NAME + peerNodeID + File.separator + 
				PreFixedValues.DOWNLOAD_FILE_NAME + PreFixedValues.NAME_UNDERSCORE + filePieceIdx;
		
		File filePiece = new File(dirFilePath);
		FileOutputStream fopsObj = new FileOutputStream(filePiece);
		fopsObj.write(filePieceContent);
		fopsObj.close();
	}
	
	public static synchronized void combileFilePiecesIntoFile() throws IOException
	{
		int peerNodeID = peerProcess.currPeerNodeID;
		String fullFilePath = PreFixedValues.FILE_NAME + peerNodeID + File.separator + 
				PreFixedValues.DOWNLOAD_FILE_NAME;
		File fullFileObj = new File(fullFilePath);
		FileOutputStream fopsObj = new FileOutputStream(fullFileObj);
		for(int ptr=0; ptr<=InitializingConfigData.getTotalNoOfPieces(); ptr++)
		{
			String dirFilePath = PreFixedValues.FILE_NAME + peerNodeID + File.separator + 
					PreFixedValues.DOWNLOAD_FILE_NAME + PreFixedValues.NAME_UNDERSCORE + ptr;
			File getFilePiece = new File(dirFilePath);
			FileInputStream fipsObj = new FileInputStream(getFilePiece);
			byte[] filePieceByteArr = new byte[(int)getFilePiece.length()];
			fipsObj.read(filePieceByteArr);
			fipsObj.close();
			fopsObj.flush();
			fopsObj.flush();
			fopsObj.write(filePieceByteArr);
			fopsObj.flush();
			fopsObj.flush();
		}
		fopsObj.close();
	}
}
