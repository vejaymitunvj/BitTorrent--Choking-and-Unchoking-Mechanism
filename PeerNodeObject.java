public class PeerNodeObject
{
	private int peerID;
	private int peerPortNumber;
	private int peerHasFullFile;
	private int filePiecesRcvdCount;
	private int[] peerBitFieldChecker;
	private String peerHostName;
	
	public PeerNodeObject()
	{
		this.filePiecesRcvdCount = 0;
		this.peerHasFullFile = 0;
	}
	
	@Override
	public String toString()
	{
		String retStr = "peerID: " + this.peerID + 
				" peerHostName: " + this.peerHostName +
				" peerPortNumber: " + this.peerPortNumber +
				"peerHasFile : " + this.peerHasFullFile;
		return retStr;
	}
	
	public int initializePeerNodeObject(String peerInfoStr)
	{
		String[] strVal = peerInfoStr.split(" ");
		this.peerID = Integer.parseInt(strVal[0]);
		this.peerHostName = strVal[1];
		this.peerPortNumber = Integer.parseInt(strVal[2]);
		this.peerHasFullFile = Integer.parseInt(strVal[3]);
		this.peerBitFieldChecker = new int[InitializingConfigData.getTotalNoOfPieces()];
		for(int ptr=0; ptr<InitializingConfigData.getTotalNoOfPieces(); ptr++)
		{
			this.peerBitFieldChecker[ptr] = 0;
		}
		return this.peerID;
	}
	
	public boolean peerHasFullFile()
	{
		if(this.peerHasFullFile == 1)
		{
			return true;
		}
		return false;
	}
	
	public boolean isFilePieceAlreadyDownloaded(int filePieceIdx)
	{
		if(this.peerBitFieldChecker[filePieceIdx] == 1)
		{
			return true;
		}
		return false;
	}
	
	public void updatePeerHasFullFile(int peerHasFileVal)
	{
		this.peerHasFullFile = peerHasFileVal;
	}
	
	public boolean doesPeerNodeHaveFullFile()
	{
		int completedFilePiecesCount = 0;
		int filePiecesCount = peerProcess.currPeerNodeObj.getPeerBitFieldChecker().length;
		for(int ptr=0; ptr<peerBitFieldChecker.length; ptr++)
		{
			if(peerBitFieldChecker[ptr] == 1)
			{
				completedFilePiecesCount++;
			}
		}
		return completedFilePiecesCount == filePiecesCount ? true : false;
	}
	
	public void updatePeerBitFieldChecker(int[] peerBitFieldCheckerVal)
	{
		this.peerBitFieldChecker = peerBitFieldCheckerVal;
	}
	
	public void updatePeerBitFieldChecker(int peerBitFieldCheckerIndex)
	{
		if(this.peerBitFieldChecker != null && this.peerBitFieldChecker[peerBitFieldCheckerIndex] != 1)
		{
			this.filePiecesRcvdCount++;
			this.peerBitFieldChecker[peerBitFieldCheckerIndex] = 1;
			if(doesPeerNodeHaveFullFile())
			{
				this.peerHasFullFile = 1;
			}
		}
	}
	
	public int getPeerID()
	{
		return this.peerID;
	}
	
	public int getPeerPortNumber()
	{
		return this.peerPortNumber;
	}
	
	public int getPeerHasFullFile()
	{
		return this.peerHasFullFile;
	}
	
	public int getFilePiecesRcvdCount()
	{
		return this.filePiecesRcvdCount;
	}
	
	public String getPeerHostName()
	{
		return this.peerHostName;
	}
	
	public int getPeerBitFieldCheckerLength()
	{
		return this.peerBitFieldChecker.length;
	}
	
	public int[] getPeerBitFieldChecker()
	{
		return this.peerBitFieldChecker;
	}
	
	public void setPeerHasFullFile(int peerHasFileVal)
	{
		this.peerHasFullFile = peerHasFileVal;
	}
	
	public void setFilePiecesRcvdCount(int peerBitFieldCount)
	{
		this.filePiecesRcvdCount = peerBitFieldCount;
	}
}
