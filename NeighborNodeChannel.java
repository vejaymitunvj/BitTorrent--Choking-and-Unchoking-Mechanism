import java.io.DataInputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

public class NeighborNodeChannel extends Thread
{
	private NeighborNodeObject neighborNodeObj;
	
	public NeighborNodeChannel(NeighborNodeObject neighborNodeObj)
	{
		this.neighborNodeObj = neighborNodeObj;
	}
	
	private void checkFilePieceAvailability(int[] rcvdPeerNodeBitField)
	{
		boolean filePieceAvailable = false;
		int[] currPeerNodeBitField 	= peerProcess.currPeerNodeObj.getPeerBitFieldChecker();
		for(int ptr=0; ptr<rcvdPeerNodeBitField.length; ptr++)
		{
			if(rcvdPeerNodeBitField[ptr] == 1 && currPeerNodeBitField[ptr] == 0)
			{
				filePieceAvailable = true;
				break;
			}
		}
		
		if(filePieceAvailable)
		{
			neighborNodeObj.sendPeerNodeInterestedMsg();
		}
		else
		{
			neighborNodeObj.sendPeerNodeNotinterestedMsg();
		}
	}
	
	@Override
	public void run()
	{
		synchronized(this)
		{
			try
			{
				DataInputStream dipsObj = new DataInputStream(neighborNodeObj.getPeerNodeSocket().getInputStream());
				neighborNodeObj.sendPeerNodeBitfieldMsg();
				while(peerProcess.noOfPeersWithFullFile < peerProcess.peerNodeTable.size())
				{
					int filePieceSize = dipsObj.readInt();
					byte[] peerNodeMsg = new byte[filePieceSize];
					double filePieceDownloadStartTime = System.nanoTime();
					dipsObj.readFully(peerNodeMsg);
					double filePieceDownloadEndTime = System.nanoTime();
					double filePieceDownloadTime = filePieceDownloadEndTime - filePieceDownloadStartTime;
					double filePieceDownloadSpeed = (filePieceSize/filePieceDownloadTime) * 1000;
					neighborNodeObj.updateFilePieceDownloadSpeed(filePieceDownloadSpeed);
					char bitTorrentMsgType	= (char)peerNodeMsg[0];
					peerNodeMsg = Arrays.copyOfRange(peerNodeMsg, 1, peerNodeMsg.length);
					
					switch(bitTorrentMsgType)
					{
						case BitTorrentMessageTypes.BTMSGTYPE_CHOKE:
							peerProcess.writePeerNodeLogsObj.writeChokingLogs(peerProcess.currPeerNodeID, neighborNodeObj.getPeerNodeID());
							break;
							
						case BitTorrentMessageTypes.BTMSGTYPE_UNCHOKE:
							int randomFilePieceIdxUnchoke = CommonMethods.pickRandomFilePieceIdx(neighborNodeObj.getPeerNodeID());
							if(randomFilePieceIdxUnchoke != -1)
							{
								neighborNodeObj.sendPeerNodeRequestMsg(randomFilePieceIdxUnchoke);
							}
							peerProcess.writePeerNodeLogsObj.writeUnchokingLogs(peerProcess.currPeerNodeID, neighborNodeObj.getPeerNodeID());
							break;
							
						case BitTorrentMessageTypes.BTMSGTYPE_INTERESTED:
							neighborNodeObj.updatePeerNodeInterestedValue(true);
							peerProcess.writePeerNodeLogsObj.writeInterestedLogs(peerProcess.currPeerNodeID, neighborNodeObj.getPeerNodeID());
							break;
							
						case BitTorrentMessageTypes.BTMSGTYPE_NOTINTERESTED:
							neighborNodeObj.updatePeerNodeInterestedValue(false);
							if(!neighborNodeObj.getPeerNodeChokedValue())
							{
								neighborNodeObj.updatePeerNodeChokedValue(true);
								neighborNodeObj.sendPeerNodeChokeMsg();
							}
							peerProcess.writePeerNodeLogsObj.writeNotInterestedLogs(peerProcess.currPeerNodeID, neighborNodeObj.getPeerNodeID());
							break;
							
						case BitTorrentMessageTypes.BTMSGTYPE_HAVE:
							int haveFilePieceIdx = ByteBuffer.wrap(peerNodeMsg).getInt();
							PeerNodeObject havePeerNodeObj = peerProcess.peerNodeTable.get(neighborNodeObj.getPeerNodeID());
							havePeerNodeObj.updatePeerBitFieldChecker(haveFilePieceIdx);
							boolean havePeerNodeHaveFullFile = havePeerNodeObj.doesPeerNodeHaveFullFile();
							if(havePeerNodeHaveFullFile && !peerProcess.peerNodeIDWithFullFile.contains(neighborNodeObj.getPeerNodeID()))
							{
								havePeerNodeObj.setPeerHasFullFile(1);
								peerProcess.peerNodeIDWithFullFile.add(neighborNodeObj.getPeerNodeID());
								peerProcess.noOfPeersWithFullFile++;
							}
							checkFilePieceAvailability(havePeerNodeObj.getPeerBitFieldChecker());
							peerProcess.writePeerNodeLogsObj.writeHaveLogs(peerProcess.currPeerNodeID, neighborNodeObj.getPeerNodeID(), haveFilePieceIdx);
							break;
							
						case BitTorrentMessageTypes.BTMSGTYPE_BITFIELD:
							IntBuffer intbufObj = ByteBuffer.wrap(peerNodeMsg).asIntBuffer();
							int[] rcvdPeerNodeBitField = new int[intbufObj.remaining()];
							intbufObj.get(rcvdPeerNodeBitField);
							PeerNodeObject bitfieldPeerNodeObj = peerProcess.peerNodeTable.get(neighborNodeObj.getPeerNodeID());
							bitfieldPeerNodeObj.updatePeerBitFieldChecker(rcvdPeerNodeBitField);
							peerProcess.writePeerNodeLogsObj.writeBitfieldReceivedLogs(peerProcess.currPeerNodeID, neighborNodeObj.getPeerNodeID());
							boolean bitfieldPeerNodeHaveFullFile = bitfieldPeerNodeObj.doesPeerNodeHaveFullFile();
							if(bitfieldPeerNodeHaveFullFile && !peerProcess.peerNodeIDWithFullFile.contains(neighborNodeObj.getPeerNodeID()))
							{
								bitfieldPeerNodeObj.setPeerHasFullFile(1);
								peerProcess.peerNodeIDWithFullFile.add(neighborNodeObj.getPeerNodeID());
								peerProcess.noOfPeersWithFullFile++;
							}
							else
							{
								bitfieldPeerNodeObj.setPeerHasFullFile(0);
							}
							checkFilePieceAvailability(rcvdPeerNodeBitField);
							break;
							
						case BitTorrentMessageTypes.BTMSGTYPE_REQUEST:
							neighborNodeObj.sendPeerNodePieceMsg(ByteBuffer.wrap(peerNodeMsg).getInt());
							peerProcess.writePeerNodeLogsObj.writeRequestLogs(peerProcess.currPeerNodeID, neighborNodeObj.getPeerNodeID());
							break;
							
						case BitTorrentMessageTypes.BTMSGTYPE_PIECE:
							int filePieceIdx = ByteBuffer.wrap(Arrays.copyOfRange(peerNodeMsg, 0, 4)).getInt();
							if(peerProcess.currPeerNodeObj.isFilePieceAlreadyDownloaded(filePieceIdx))
							{
								continue;
							}
							byte[] filePieceContent = Arrays.copyOfRange(peerNodeMsg, 4, peerNodeMsg.length);
							CommonMethods.writeFilePiecesToDir(peerProcess.currPeerNodeID, filePieceIdx, filePieceContent);
							peerProcess.currPeerNodeObj.updatePeerBitFieldChecker(filePieceIdx);
							if(peerProcess.currPeerNodeObj.peerHasFullFile() && !peerProcess.peerNodeIDWithFullFile.contains(peerProcess.currPeerNodeID))
							{
								peerProcess.peerNodeIDWithFullFile.add(peerProcess.currPeerNodeID);
								peerProcess.noOfPeersWithFullFile++;
								peerProcess.writePeerNodeLogsObj.writeFileCompletedDownloadLogs(peerProcess.currPeerNodeID);
							}
							else
							{
								int randomFilePieceIdxPiece = CommonMethods.pickRandomFilePieceIdx(neighborNodeObj.getPeerNodeID());
								if(randomFilePieceIdxPiece != -1)
								{
									neighborNodeObj.sendPeerNodeRequestMsg(randomFilePieceIdxPiece);
								}
								peerProcess.writePeerNodeLogsObj.writeCompletedPieceDownloadLogs(peerProcess.currPeerNodeID, neighborNodeObj.getPeerNodeID(), filePieceIdx, peerProcess.currPeerNodeObj.getFilePiecesRcvdCount());
							}
							for(int neighborNodeID : peerProcess.neighborNodeTable.keySet())
							{
								peerProcess.neighborNodeTable.get(neighborNodeID).sendPeerNodeHaveMsg(filePieceIdx);
							}
							break;
					}
				}
				CommonMethods.combileFilePiecesIntoFile();
				peerProcess.terminateProgram = true;
			}
			catch(Exception exp)
			{
			}
		}
	}
}
