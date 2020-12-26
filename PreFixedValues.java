public class PreFixedValues
{

	public final static String DOWNLOAD_FILE_NAME = "thefile";
	public final static String COMMON_CONFIG_FILE_NAME = "Common.cfg";
	public final static String PEER_CONFIG_FILE_NAME = "PeerInfo.cfg";
	public final static String HANDSHAKE_PACKET_HEADER_NAME = "P2PFILESHARINGPROJ";
	public final static String PADDING_ZEROES = "0000000000";
	public final static String FILE_NAME = "peer_";
	public final static String LOG_FILE_NAME = "log_peer_";
	public final static String LOG_FILE_FORMAT = ".log";
	public final static String NAME_UNDERSCORE = "_";
	public final static String LOG_FILE_MSG_1 = ": Peer [";
	public final static String LOG_FILE_MSG_2 = "].";
	public final static String LOG_FILE_MSG_3 = ".";
	public final static String LOG_FILE_MSG_TCPCONN_CLI = "] makes a connection to Peer [";
	public final static String LOG_FILE_MSG_TCPCONN_SER = "] is connected from Peer [";
	public final static String LOG_FILE_MSG_PREFNEIGHBOR = "] has the preferred neighbors ";
	public final static String LOG_FILE_MSG_OPTUNCHOKE = "] has the optimistically unchoked neighbor [";
	public final static String LOG_FILE_MSG_UNCHOKE = "] is unchoked by [";
	public final static String LOG_FILE_MSG_CHOKE = "] is choked by [";
	public final static String LOG_FILE_MSG_HAVE_1 = "] received the 'have' message from [";
	public final static String LOG_FILE_MSG_HAVE_2 = "] for the piece [";
	public final static String LOG_FILE_MSG_REQUEST = "] received the 'request' message from [";
	public final static String LOG_FILE_MSG_INTERESTED = "] received the 'interested' message from [";
	public final static String LOG_FILE_MSG_NOTINTERESTED = "] received the 'not interested' message from [";
	public final static String LOG_FILE_MSG_BITFIELDRCVD = "] received the 'bitfield' message from [";
	public final static String LOG_FILE_MSG_PIECEDOWNLOAD_1 = "] has downloaded the piece [";
	public final static String LOG_FILE_MSG_PIECEDOWNLOAD_2 = "] from [";
	public final static String LOG_FILE_MSG_PIECEDOWNLOAD_3 = "]. Now the number of pieces it has is [";
	public final static String LOG_FILE_MSG_FILEDOWNLOAD = "] has downloaded the complete file.";
	public final static String LOG_FILE_MSG_CONFIGFILE_1 = "] has the following configurations: NumberOfPreferredNeighbors: ";
	public final static String LOG_FILE_MSG_CONFIGFILE_2 = " UnchokingInterval: ";
	public final static String LOG_FILE_MSG_CONFIGFILE_3 = " OptimisticUnchokingInterval: ";
	public final static String LOG_FILE_MSG_CONFIGFILE_4 = " FileName: ";
	public final static String LOG_FILE_MSG_CONFIGFILE_5 = " FileSize: ";
	public final static String LOG_FILE_MSG_CONFIGFILE_6 = " PieceSize: ";
	public final static String LOG_FILE_MSG_PEERINFOFILE_1 = ": Reading the peer node configurations from PeerInfo.cfg. Peer[";
	public final static String LOG_FILE_MSG_PEERINFOFILE_2 = "] has the file. Splitting the file into chunks and setting all the Bitfield components to 1.";
	public final static String LOG_FILE_MSG_PEERINFOFILE_3 = "] does not have the file. Setting all the Bitfield components to 0.";
	public final static String LOG_FILE_MSG_PROCESSCOMP = ": All the Peer Nodes have downloaded the Complete File. Terminating Process!";
	public static String UNCHOKECONDN = "false";

	public static String getDownloadFile()
	{
		return DOWNLOAD_FILE_NAME;
	}
	
	public static String getCommonconfigfile()
	{
		return COMMON_CONFIG_FILE_NAME;
	}
	
	public static String getPeerConfigFileName()
	{
		return PEER_CONFIG_FILE_NAME;
	}
	
	public static String getHandshakePktHeader()
	{
		return HANDSHAKE_PACKET_HEADER_NAME;
	}
	
	public static String getPaddingZeroes()
	{
		return PADDING_ZEROES;
	}

}
