import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;

public class NeighborNodeObject
{
	private Socket peerNodeSocket;
	private int peerNodeID;
	private double filePieceDownloadSpeed;
	private HashMap<String, Boolean> peerMsgStatus = new HashMap<String, Boolean>();
	private BitTorrentMessageTypes bitTorrentMsgTypesObj = new BitTorrentMessageTypes();
	private static int pktNoPayloadFixedLen = 1;
	private static int pktByteFixedLen = 4;
	private static int pktPayloadFixedLen = 5;
	
	public NeighborNodeObject(Socket peerNodeChannel, int peerNodeID)
	{
		this.peerNodeSocket = peerNodeChannel;
		this.peerNodeID = peerNodeID;
		this.filePieceDownloadSpeed = 0.0;
		peerMsgStatus.put("isPeerNodeInterested", false);
		peerMsgStatus.put("isPeerNodeChoked", true);
		peerMsgStatus.put("isPeerNodeOptUnchoked", false);
		(new NeighborNodeChannel(this)).start();
	}
	
	public double getFilePieceDownloadSpeed()
	{
		return filePieceDownloadSpeed;
	}
	
	public boolean getPeerNodeInterestedValue()
	{
		return peerMsgStatus.get("isPeerNodeInterested");
	}
	
	public boolean getPeerNodeChokedValue()
	{
		return peerMsgStatus.get("isPeerNodeChoked");
	}
	
	public boolean getPeerNodeOptUnchokedValue()
	{
		return peerMsgStatus.get("isPeerNodeOptUnchoked");
	}
	
	public Socket getPeerNodeSocket()
	{
		return this.peerNodeSocket;
	}
	
	public int getPeerNodeID()
	{
		return this.peerNodeID;
	}
	
	public void updateFilePieceDownloadSpeed(double filePieceDownloadSpeed)
	{
		this.filePieceDownloadSpeed = filePieceDownloadSpeed;
	}
	
	public void updatePeerNodeInterestedValue(boolean isPeerNodeInterested)
	{
		this.peerMsgStatus.put("isPeerNodeInterested", isPeerNodeInterested);
	}
	
	public void updatePeerNodeChokedValue(boolean isPeerNodeChoked)
	{
		this.peerMsgStatus.put("isPeerNodeChoked", isPeerNodeChoked);
	}
	
	public void updatePeerNodeOptUnchokedValue(boolean isPeerNodeOptUnchoked)
	{
		this.peerMsgStatus.put("isPeerNodeOptUnchoked", isPeerNodeOptUnchoked);
	}
	
	private synchronized byte[] createMessagePkt(int pktMsgLength, char pktMsgType, byte[] pktMsgContent) throws IOException
	{
		List<Character> noPayloadMsgTypes 	= bitTorrentMsgTypesObj.getNoPayloadMsgTypes();
		List<Character> payloadMsgTypes 	= bitTorrentMsgTypesObj.getPayloadMsgTypes();
		
		ByteArrayOutputStream byteArrOps = new ByteArrayOutputStream();
		byte[] messagePktLenByte = ByteBuffer.allocate(pktByteFixedLen).putInt(pktMsgLength).array();
		byteArrOps.write(messagePktLenByte);
		byteArrOps.write((byte)pktMsgType);
		
		if(noPayloadMsgTypes.contains(pktMsgType))
		{
			return byteArrOps.toByteArray();
		}
		else if(payloadMsgTypes.contains(pktMsgType))
		{
			byteArrOps.write(pktMsgContent);
			return byteArrOps.toByteArray();
		}
		else
		{
			System.out.println("Wrong Type of Packet");
			throw new IOException("Wrong Packet Type");
		}
	}
	
	private synchronized byte[] getFilePieceContent(int filePieceIdx, byte[] filePiece) throws Exception
	{
		byte[] filePieceContent = null;
		try
		{
			ByteArrayOutputStream byteArrOps = new ByteArrayOutputStream();
			byte[] messagePktLenByte = ByteBuffer.allocate(pktByteFixedLen).putInt(filePieceIdx).array();
			byteArrOps.write(messagePktLenByte);
			byteArrOps.write(filePiece);
			filePieceContent = createMessagePkt(pktPayloadFixedLen+filePiece.length, bitTorrentMsgTypesObj.getBitTorrentMsgType("piece"), byteArrOps.toByteArray());
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
		return filePieceContent;
	}
	
	public synchronized void sendPeerNodeChokeMsg()
	{
		try
		{
			DataOutputStream dopsObj = new DataOutputStream(peerNodeSocket.getOutputStream());
			dopsObj.flush();
			byte[] chokeMessagePkt = createMessagePkt(pktNoPayloadFixedLen, bitTorrentMsgTypesObj.getBitTorrentMsgType("choke"), null);
			dopsObj.write(chokeMessagePkt);
			dopsObj.flush();
		}
		catch(Exception exp)
		{
			System.out.println("Choke message packet Failure");
			exp.printStackTrace();
		}
	}
	
	public synchronized void sendPeerNodeUnchokeMsg()
	{
		try
		{
			DataOutputStream dopsObj = new DataOutputStream(peerNodeSocket.getOutputStream());
			dopsObj.flush();
			byte[] unchokeMessagePkt = createMessagePkt(pktNoPayloadFixedLen, bitTorrentMsgTypesObj.getBitTorrentMsgType("unchoke"), null);
			dopsObj.write(unchokeMessagePkt);
			dopsObj.flush();
		}
		catch(Exception exp)
		{
			System.out.println("Unchoke message packet Failure");
			exp.printStackTrace();
		}
	}
	
	public synchronized void sendPeerNodeInterestedMsg()
	{
		try
		{
			DataOutputStream dopsObj = new DataOutputStream(peerNodeSocket.getOutputStream());
			dopsObj.flush();
			byte[] interestedMessagePkt = createMessagePkt(pktNoPayloadFixedLen, bitTorrentMsgTypesObj.getBitTorrentMsgType("interested"), null);
			dopsObj.write(interestedMessagePkt);
			dopsObj.flush();
		}
		catch(Exception exp)
		{
			System.out.println("Interested message packet Failure");
			exp.printStackTrace();
		}
	}
	
	public synchronized void sendPeerNodeNotinterestedMsg()
	{
		try
		{
			DataOutputStream dopsObj = new DataOutputStream(peerNodeSocket.getOutputStream());
			dopsObj.flush();
			byte[] notinterestedMessagePkt = createMessagePkt(pktNoPayloadFixedLen, bitTorrentMsgTypesObj.getBitTorrentMsgType("notinterested"), null);
			dopsObj.write(notinterestedMessagePkt);
			dopsObj.flush();
		}
		catch(Exception exp)
		{
			System.out.println("Notinterested message packet Failure");
			exp.printStackTrace();
		}
	}
	
	public synchronized void sendPeerNodeHaveMsg(int pieceIdxFieldValue)
	{
		try
		{
			DataOutputStream dopsObj = new DataOutputStream(peerNodeSocket.getOutputStream());
			dopsObj.flush();
			byte[] pktMsgContent = ByteBuffer.allocate(pktByteFixedLen).putInt(pieceIdxFieldValue).array();
			byte[] haveMessagePkt = createMessagePkt(pktPayloadFixedLen, bitTorrentMsgTypesObj.getBitTorrentMsgType("have"), pktMsgContent);
			dopsObj.write(haveMessagePkt);
			dopsObj.flush();
		}
		catch(Exception exp)
		{
			System.out.println("Have message packet Failure");
			exp.printStackTrace();
		}
	}
	
	public synchronized void sendPeerNodeBitfieldMsg()
	{
		try
		{
			DataOutputStream dopsObj = new DataOutputStream(peerNodeSocket.getOutputStream());
			dopsObj.flush();
			int[] peerBitFieldChecker = peerProcess.currPeerNodeObj.getPeerBitFieldChecker();
			ByteArrayOutputStream byteArrOps = new ByteArrayOutputStream();
			for(int ptr=0; ptr<peerBitFieldChecker.length; ptr++)
			{
				byte[] pieceBitFieldCheckerByte = ByteBuffer.allocate(pktByteFixedLen).putInt(peerBitFieldChecker[ptr]).array();
				byteArrOps.write(pieceBitFieldCheckerByte);
			}
			
			int pktPayloadLen = (pktByteFixedLen * peerBitFieldChecker.length) + 1;
			byte[] bitfieldMessagePkt = createMessagePkt(pktPayloadLen, bitTorrentMsgTypesObj.getBitTorrentMsgType("bitfield"), byteArrOps.toByteArray());
			dopsObj.write(bitfieldMessagePkt);
			dopsObj.flush();
		}
		catch(Exception exp)
		{
			System.out.println("Bitfield message packet Failure");
			exp.printStackTrace();
		}
	}
	
	public synchronized void sendPeerNodeRequestMsg(int pieceIdxFieldValue)
	{
		try
		{
			DataOutputStream dopsObj = new DataOutputStream(peerNodeSocket.getOutputStream());
			dopsObj.flush();
			byte[] pktMsgContent = ByteBuffer.allocate(pktByteFixedLen).putInt(pieceIdxFieldValue).array();
			byte[] requestMessagePkt = createMessagePkt(pktPayloadFixedLen, bitTorrentMsgTypesObj.getBitTorrentMsgType("request"), pktMsgContent);
			dopsObj.write(requestMessagePkt);
			dopsObj.flush();
		}
		catch(Exception exp)
		{
			System.out.println("Request message packet Failure");
			exp.printStackTrace();
		}
	}
	
	public synchronized void sendPeerNodePieceMsg(int pieceIdxFieldValue)
	{
		try
		{
			DataOutputStream dopsObj = new DataOutputStream(peerNodeSocket.getOutputStream());
			dopsObj.flush();
			dopsObj.write(getFilePieceContent(pieceIdxFieldValue, CommonMethods.getFilePiecesFromDir(peerProcess.currPeerNodeID, pieceIdxFieldValue)));
			dopsObj.flush();
		}
		catch(Exception exp)
		{
			System.out.println("Piece message packet Failure");
			exp.printStackTrace();
		}
	}
	
	public synchronized void writePieceFileToDir(String filePath, byte[] fileContentByte) throws IOException
	{
		BufferedOutputStream bopsObj = new BufferedOutputStream(new FileOutputStream(filePath));
		bopsObj.write(fileContentByte);
		bopsObj.close();
	}
}
