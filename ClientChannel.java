import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Map.Entry;

public class ClientChannel implements Runnable
{
	@Override
	public void run()
	{
		try
		{
			byte[] resultByteArr;
			
			for(Entry<Integer, PeerNodeObject> peerObjEnt : peerProcess.peerNodeTable.entrySet())
			{
				int peerID = peerObjEnt.getKey();
				if(peerID == peerProcess.currPeerNodeID)
					break;
				
				PeerNodeObject peerNodeObj 	= peerProcess.peerNodeTable.get(peerID);
				String peerHostName 		= peerNodeObj.getPeerHostName();
				int peerPortNumber 			= peerNodeObj.getPeerPortNumber();
				Socket clientChannelSocket	= new Socket(peerHostName, peerPortNumber);
				DataOutputStream dopsObj	= new DataOutputStream(clientChannelSocket.getOutputStream());
				DataInputStream dipsObj		= new DataInputStream(clientChannelSocket.getInputStream());
				byte[] serverHandshakePkt	= CommonMethods.createHandshakePkt(peerProcess.currPeerNodeID);
				
				dopsObj.flush();
				dopsObj.write(serverHandshakePkt);
				dopsObj.flush();
				
				resultByteArr = new byte[32];
				dipsObj.readFully(resultByteArr);
				byte[] partialResultByteArr = CommonMethods.cloneBytePieceArr(resultByteArr, 28, 32);
				int receivedPeerID = ByteBuffer.wrap(partialResultByteArr) .getInt();
				if(receivedPeerID == peerID)
				{
					peerProcess.writePeerNodeLogsObj.writeTcpConnectionLogsClient(peerProcess.currPeerNodeID, peerID);
					StringBuilder receivedHandshakePkt = new StringBuilder("");
					partialResultByteArr = CommonMethods.cloneBytePieceArr(resultByteArr, 0, 28);
					receivedHandshakePkt.append(new String(partialResultByteArr));
					receivedHandshakePkt.append(receivedPeerID);
					peerProcess.neighborNodeTable.put(peerID, new NeighborNodeObject(clientChannelSocket, peerID));
				}
				else
				{
					clientChannelSocket.close();
				}
			}
		}
		catch(IOException exception)
		{
			exception.printStackTrace();
		}
		finally
		{
		}
	}
}
