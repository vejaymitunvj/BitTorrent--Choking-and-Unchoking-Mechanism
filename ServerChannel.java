import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class ServerChannel implements Runnable
{	
	@Override
	public void run()
	{
		byte[] resultByteArr = new byte[32];
		try
		{
			ServerSocket serverChannelSocket = new ServerSocket(peerProcess.currPeerNodeObj.getPeerPortNumber());
			
			for(int ptr=0; peerProcess.neighborNodeTable.size() < peerProcess.peerNodeTable.size()-1; ptr++)
			{
				Socket establishConnSocket = serverChannelSocket.accept();
				DataInputStream dipsObj = new DataInputStream(establishConnSocket.getInputStream());
				dipsObj.readFully(resultByteArr);
				StringBuilder sendHandshakePkt = new StringBuilder();
				sendHandshakePkt.append(new String(Arrays.copyOfRange(resultByteArr, 0, 28)));
				int peerNodeID = ByteBuffer.wrap(Arrays.copyOfRange(resultByteArr, 28, 32)).getInt();
				sendHandshakePkt.append(peerNodeID); //check
				peerProcess.writePeerNodeLogsObj.writeTcpConnectionLogsServer(peerProcess.currPeerNodeID, peerNodeID);
				DataOutputStream dopsObj = new DataOutputStream(establishConnSocket.getOutputStream());
				dopsObj.flush();
				dopsObj.write(CommonMethods.createHandshakePkt(peerProcess.currPeerNodeID));
				
				peerProcess.neighborNodeTable.put(peerNodeID, new NeighborNodeObject(establishConnSocket, peerNodeID));
			}
		}
		catch(IOException exp)
		{
			exp.printStackTrace();
		}
	}
}
