import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WritePeerNodeLogs
{
	File peerNodeLogFile = null;
	BufferedWriter logFileBuffWriter = null;
	
	public WritePeerNodeLogs(File peerNodeLogFile)
	{
		try
		{
			this.peerNodeLogFile = peerNodeLogFile;
			this.logFileBuffWriter = new BufferedWriter(new FileWriter(peerNodeLogFile.getAbsolutePath(),true));
		}
		catch(Exception exp)
		{
			System.out.println("Log Error");
			exp.printStackTrace();
		}
	}
	
	private synchronized String getLogTimestampVal()
	{
		Date logDateVal = new Date();
		DateFormat logDateFormatVal = new SimpleDateFormat("MMM/dd/yyyy HH:mm:ss");
		String logTimestampVal = logDateFormatVal.format(logDateVal);
		return logTimestampVal;
	}
	
	
	public synchronized void writeTcpConnectionLogsClient(int sourcePeerNodeID, int actionedPeerNodeID)
	{
		try
		{
			String toWriteLogContent =  getLogTimestampVal() + PreFixedValues.LOG_FILE_MSG_1 + sourcePeerNodeID +
										PreFixedValues.LOG_FILE_MSG_TCPCONN_CLI + actionedPeerNodeID +
										PreFixedValues.LOG_FILE_MSG_2;
			logFileBuffWriter.flush();
			logFileBuffWriter.write(toWriteLogContent);
			logFileBuffWriter.newLine();
			logFileBuffWriter.flush();
		}
		catch(Exception exp)
		{
			System.out.println("TCP Log Error");
			exp.printStackTrace();
		}
	}
	
	public synchronized void writeTcpConnectionLogsServer(int sourcePeerNodeID, int actionedPeerNodeID)
	{
		try
		{
			String toWriteLogContent =  getLogTimestampVal() + PreFixedValues.LOG_FILE_MSG_1 + sourcePeerNodeID +
										PreFixedValues.LOG_FILE_MSG_TCPCONN_SER + actionedPeerNodeID +
										PreFixedValues.LOG_FILE_MSG_2;
			logFileBuffWriter.flush();
			logFileBuffWriter.write(toWriteLogContent);
			logFileBuffWriter.newLine();
			logFileBuffWriter.flush();
		}
		catch(Exception exp)
		{
			System.out.println("TCP Log Error");
			exp.printStackTrace();
		}
	}
	
	public synchronized void writePreferredNeighborLogs(int sourcePeerNodeID, List<Integer> preferredNeighborsList)
	{
		try
		{
			String toWriteLogContent =  getLogTimestampVal() + PreFixedValues.LOG_FILE_MSG_1 + sourcePeerNodeID +
										PreFixedValues.LOG_FILE_MSG_PREFNEIGHBOR + preferredNeighborsList.toString() +
										PreFixedValues.LOG_FILE_MSG_3;
			logFileBuffWriter.flush();
			logFileBuffWriter.write(toWriteLogContent);
			logFileBuffWriter.newLine();
			logFileBuffWriter.flush();
		}
		catch(Exception exp)
		{
			System.out.println("Preferred Neighbor Log Error");
			exp.printStackTrace();
		}
	}
	
	public synchronized void writeOptUnchokedLogs(int sourcePeerNodeID, int actionedPeerNodeID)
	{
		try
		{
			String toWriteLogContent =  getLogTimestampVal() + PreFixedValues.LOG_FILE_MSG_1 + sourcePeerNodeID +
										PreFixedValues.LOG_FILE_MSG_OPTUNCHOKE + actionedPeerNodeID +
										PreFixedValues.LOG_FILE_MSG_2;
			logFileBuffWriter.flush();
			logFileBuffWriter.write(toWriteLogContent);
			logFileBuffWriter.newLine();
			logFileBuffWriter.flush();
		}
		catch(Exception exp)
		{
			System.out.println("Optimistically Unchoked Log Error");
			exp.printStackTrace();
		}
	}
	
	public synchronized void writeUnchokingLogs(int sourcePeerNodeID, int actionedPeerNodeID)
	{
		try
		{
			String toWriteLogContent =  getLogTimestampVal() + PreFixedValues.LOG_FILE_MSG_1 + sourcePeerNodeID +
										PreFixedValues.LOG_FILE_MSG_UNCHOKE + actionedPeerNodeID +
										PreFixedValues.LOG_FILE_MSG_2;
			logFileBuffWriter.flush();
			logFileBuffWriter.write(toWriteLogContent);
			logFileBuffWriter.newLine();
			logFileBuffWriter.flush();
		}
		catch(Exception exp)
		{
			System.out.println("Unchoke Log Error");
			exp.printStackTrace();
		}
	}
	
	public synchronized void writeChokingLogs(int sourcePeerNodeID, int actionedPeerNodeID)
	{
		try
		{
			String toWriteLogContent =  getLogTimestampVal() + PreFixedValues.LOG_FILE_MSG_1 + sourcePeerNodeID +
										PreFixedValues.LOG_FILE_MSG_CHOKE + actionedPeerNodeID +
										PreFixedValues.LOG_FILE_MSG_2;
			logFileBuffWriter.flush();
			logFileBuffWriter.write(toWriteLogContent);
			logFileBuffWriter.newLine();
			logFileBuffWriter.flush();
		}
		catch(Exception exp)
		{
			System.out.println("Choke Log Error");
			exp.printStackTrace();
		}
	}
	
	public synchronized void writeHaveLogs(int sourcePeerNodeID, int actionedPeerNodeID, int filePieceIdx)
	{
		try
		{
			String toWriteLogContent =  getLogTimestampVal() + PreFixedValues.LOG_FILE_MSG_1 + sourcePeerNodeID +
										PreFixedValues.LOG_FILE_MSG_HAVE_1 + actionedPeerNodeID +
										PreFixedValues.LOG_FILE_MSG_HAVE_2 + filePieceIdx +
										PreFixedValues.LOG_FILE_MSG_2;
			logFileBuffWriter.flush();
			logFileBuffWriter.write(toWriteLogContent);
			logFileBuffWriter.newLine();
			logFileBuffWriter.flush();
		}
		catch(Exception exp)
		{
			System.out.println("Have Log Error");
			exp.printStackTrace();
		}
	}
	
	public synchronized void writeInterestedLogs(int sourcePeerNodeID, int actionedPeerNodeID)
	{
		try
		{
			String toWriteLogContent =  getLogTimestampVal() + PreFixedValues.LOG_FILE_MSG_1 + sourcePeerNodeID +
										PreFixedValues.LOG_FILE_MSG_INTERESTED + actionedPeerNodeID +
										PreFixedValues.LOG_FILE_MSG_2;
			logFileBuffWriter.flush();
			logFileBuffWriter.write(toWriteLogContent);
			logFileBuffWriter.newLine();
			logFileBuffWriter.flush();
		}
		catch(Exception exp)
		{
			System.out.println("Interested Log Error");
			exp.printStackTrace();
		}
	}
	
	public synchronized void writeNotInterestedLogs(int sourcePeerNodeID, int actionedPeerNodeID)
	{
		try
		{
			String toWriteLogContent =  getLogTimestampVal() + PreFixedValues.LOG_FILE_MSG_1 + sourcePeerNodeID +
										PreFixedValues.LOG_FILE_MSG_NOTINTERESTED + actionedPeerNodeID +
										PreFixedValues.LOG_FILE_MSG_2;
			logFileBuffWriter.flush();
			logFileBuffWriter.write(toWriteLogContent);
			logFileBuffWriter.newLine();
			logFileBuffWriter.flush();
		}
		catch(Exception exp)
		{
			System.out.println("NotInterested Log Error");
			exp.printStackTrace();
		}
	}
	
	public synchronized void writeRequestLogs(int sourcePeerNodeID, int actionedPeerNodeID)
	{
		try
		{
			String toWriteLogContent =  getLogTimestampVal() + PreFixedValues.LOG_FILE_MSG_1 + sourcePeerNodeID +
										PreFixedValues.LOG_FILE_MSG_REQUEST + actionedPeerNodeID +
										PreFixedValues.LOG_FILE_MSG_2;
			logFileBuffWriter.flush();
			logFileBuffWriter.write(toWriteLogContent);
			logFileBuffWriter.newLine();
			logFileBuffWriter.flush();
		}
		catch(Exception exp)
		{
			System.out.println("Request Log Error");
			exp.printStackTrace();
		}
	}
	
	public synchronized void writeBitfieldReceivedLogs(int sourcePeerNodeID, int actionedPeerNodeID)
	{
		try
		{
			String toWriteLogContent =  getLogTimestampVal() + PreFixedValues.LOG_FILE_MSG_1 + sourcePeerNodeID +
										PreFixedValues.LOG_FILE_MSG_BITFIELDRCVD + actionedPeerNodeID +
										PreFixedValues.LOG_FILE_MSG_2;
			logFileBuffWriter.flush();
			logFileBuffWriter.write(toWriteLogContent);
			logFileBuffWriter.newLine();
			logFileBuffWriter.flush();
		}
		catch(Exception exp)
		{
			System.out.println("Bitfield Log Error");
			exp.printStackTrace();
		}
	}
	
	public synchronized void writeCompletedPieceDownloadLogs(int sourcePeerNodeID, int actionedPeerNodeID, int filePieceIdx, int filePiecesRcvdCount)
	{
		try
		{
			String toWriteLogContent =  getLogTimestampVal() + PreFixedValues.LOG_FILE_MSG_1 + sourcePeerNodeID +
										PreFixedValues.LOG_FILE_MSG_PIECEDOWNLOAD_1 + filePieceIdx +
										PreFixedValues.LOG_FILE_MSG_PIECEDOWNLOAD_2 + actionedPeerNodeID +
										PreFixedValues.LOG_FILE_MSG_PIECEDOWNLOAD_3 + filePiecesRcvdCount +
										PreFixedValues.LOG_FILE_MSG_2;
			logFileBuffWriter.flush();
			logFileBuffWriter.write(toWriteLogContent);
			logFileBuffWriter.newLine();
			logFileBuffWriter.flush();
		}
		catch(Exception exp)
		{
			System.out.println("Completed File Piece Download Log Error");
			exp.printStackTrace();
		}
	}
	
	public synchronized void writeFileCompletedDownloadLogs(int sourcePeerNodeID)
	{
		try
		{
			String toWriteLogContent =  getLogTimestampVal() + PreFixedValues.LOG_FILE_MSG_1 + sourcePeerNodeID +
										PreFixedValues.LOG_FILE_MSG_FILEDOWNLOAD;
			logFileBuffWriter.flush();
			logFileBuffWriter.write(toWriteLogContent);
			logFileBuffWriter.newLine();
			logFileBuffWriter.flush();
		}
		catch(Exception exp)
		{
			System.out.println("Completed File Download Log Error");
			exp.printStackTrace();
		}
	}
	
	public synchronized void writeCommonInfoConfigLogs(int sourcePeerNodeID, int preferredNeighborsCount, int unchokingIntervalValue, 
			int optimisticUnchokingIntervalValue, String nameOfFile, int sizeOfFile, int sizeOfFilePiece)
	{
		try
		{
			String toWriteLogContent =  getLogTimestampVal() + PreFixedValues.LOG_FILE_MSG_1 + sourcePeerNodeID +
										PreFixedValues.LOG_FILE_MSG_CONFIGFILE_1 + preferredNeighborsCount +
										PreFixedValues.LOG_FILE_MSG_CONFIGFILE_2 + unchokingIntervalValue +
										PreFixedValues.LOG_FILE_MSG_CONFIGFILE_3 + optimisticUnchokingIntervalValue +
										PreFixedValues.LOG_FILE_MSG_CONFIGFILE_4 + nameOfFile +
										PreFixedValues.LOG_FILE_MSG_CONFIGFILE_5 + sizeOfFile +
										PreFixedValues.LOG_FILE_MSG_CONFIGFILE_6 + sizeOfFilePiece;
			logFileBuffWriter.flush();
			logFileBuffWriter.write(toWriteLogContent);
			logFileBuffWriter.newLine();
			logFileBuffWriter.flush();
		}
		catch(Exception exp)
		{
			System.out.println("Common Info Log Error");
			exp.printStackTrace();
		}
	}
	
	public synchronized void writePeerInfoConfigLogs(int sourcePeerNodeID, boolean doesNodeHaveFile)
	{
		try
		{
			String toWriteLogContent =  getLogTimestampVal() + PreFixedValues.LOG_FILE_MSG_PEERINFOFILE_1 + sourcePeerNodeID;
			
			if(doesNodeHaveFile)
			{
				toWriteLogContent = toWriteLogContent + PreFixedValues.LOG_FILE_MSG_PEERINFOFILE_2;
			}
			else
			{
				toWriteLogContent = toWriteLogContent + PreFixedValues.LOG_FILE_MSG_PEERINFOFILE_3;
			}
			logFileBuffWriter.flush();
			logFileBuffWriter.write(toWriteLogContent);
			logFileBuffWriter.newLine();
			logFileBuffWriter.flush();
		}
		catch(Exception exp)
		{
			System.out.println("Peer Info Log Error");
			exp.printStackTrace();
		}
	}
	
	public synchronized void writeProcessCompletedTerminationLogs()
	{
		try
		{
			String toWriteLogContent =  getLogTimestampVal() + PreFixedValues.LOG_FILE_MSG_PROCESSCOMP;
			logFileBuffWriter.flush();
			logFileBuffWriter.write(toWriteLogContent);
			logFileBuffWriter.newLine();
			logFileBuffWriter.flush();
		}
		catch(Exception exp)
		{
			System.out.println("Peer Completed Terminating Log Error");
			exp.printStackTrace();
		}
	}
}
