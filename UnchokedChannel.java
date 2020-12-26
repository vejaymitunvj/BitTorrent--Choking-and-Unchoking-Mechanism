import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

public class UnchokedChannel implements Runnable
{	
	private int pickRandomInterestedIdx(List<Integer> interestedPeerNodeList)
	{
		Random randomObj = new Random();
		return randomObj.nextInt(interestedPeerNodeList.size());
	}
	
	private List<Integer> getPeerNodeListAccToDownloadRate(List<Integer> interestedPeerNodeList)
	{
		TreeMap<Double, Integer> downloadSpeedMap = CommonMethods.getDownloadRatePeerNodeList(interestedPeerNodeList);
		List<Integer> logFileNeighborList = new ArrayList<>();
		if(downloadSpeedMap.size() > 0)
		{
			int unchokingPeerCount = 0;
			for(int ptr=0; ptr<downloadSpeedMap.size(); ptr++)
			{
				if(unchokingPeerCount<=InitializingConfigData.getPreferredNeighborsCount())
				{
					int unchokePeerNodeID = downloadSpeedMap.get(downloadSpeedMap.keySet().toArray()[ptr]);
					NeighborNodeObject unchokeNeighborObj = peerProcess.neighborNodeTable.get(unchokePeerNodeID);
					logFileNeighborList.add(interestedPeerNodeList.get(unchokePeerNodeID));
					if(unchokeNeighborObj.getPeerNodeChokedValue())
					{
						unchokeNeighborObj.updatePeerNodeChokedValue(false);
						unchokeNeighborObj.sendPeerNodeUnchokeMsg();
					}
					unchokingPeerCount++;
				}
				else
				{
					int chokePeerNodeID = downloadSpeedMap.get(downloadSpeedMap.keySet().toArray()[ptr]);
					NeighborNodeObject chokeNeighborObj = peerProcess.neighborNodeTable.get(chokePeerNodeID);
					if(!chokeNeighborObj.getPeerNodeChokedValue() && !chokeNeighborObj.getPeerNodeOptUnchokedValue())
					{
						chokeNeighborObj.updatePeerNodeChokedValue(true);
						chokeNeighborObj.sendPeerNodeChokeMsg();
					}
				}
			}
		}
		return logFileNeighborList;
	}
	
	private void chokePeerNodeAfterNewUnchoke(List<Integer> interestedPeerNodeList)
	{
		for(Integer peerNodeID : peerProcess.neighborNodeTable.keySet())
		{
			if(!interestedPeerNodeList.contains(peerNodeID))
			{
				NeighborNodeObject chokeNeighborObj = peerProcess.neighborNodeTable.get(peerNodeID);
				if(!chokeNeighborObj.getPeerNodeChokedValue() && !chokeNeighborObj.getPeerNodeOptUnchokedValue())
				{
					chokeNeighborObj.updatePeerNodeChokedValue(true);
					chokeNeighborObj.sendPeerNodeChokeMsg();
				}
			}
			
		}
	}
	
	@Override
	public void run()
	{
		try
		{
			while(peerProcess.noOfPeersWithFullFile < peerProcess.peerNodeTable.size())
			{
				List<Integer> interestedPeerNodeList = CommonMethods.getInterestedPeerNodeList();
				
				if(interestedPeerNodeList.size() > 0)
				{
					if(peerProcess.currPeerNodeObj.peerHasFullFile())
					{
						if(interestedPeerNodeList.size() <= InitializingConfigData.getPreferredNeighborsCount())
						{
							for(int ptr=0; ptr<interestedPeerNodeList.size(); ptr++)
							{
								NeighborNodeObject unchokeNeighborObj = peerProcess.neighborNodeTable.get(interestedPeerNodeList.get(ptr));
								if(unchokeNeighborObj.getPeerNodeChokedValue())
								{
									unchokeNeighborObj.updatePeerNodeChokedValue(false);
									unchokeNeighborObj.sendPeerNodeUnchokeMsg();
								}
							}
							chokePeerNodeAfterNewUnchoke(interestedPeerNodeList);
							peerProcess.writePeerNodeLogsObj.writePreferredNeighborLogs(peerProcess.currPeerNodeID, interestedPeerNodeList);
						}
						else
						{
							List<Integer> logFileNeighborList = new ArrayList<>();
							for(int ptr=0; ptr<InitializingConfigData.getPreferredNeighborsCount(); ptr++)
							{
								int randomNeighborIdx = pickRandomInterestedIdx(interestedPeerNodeList);
								NeighborNodeObject unchokeNeighborObj = peerProcess.neighborNodeTable.get(interestedPeerNodeList.get(randomNeighborIdx));
								logFileNeighborList.add(interestedPeerNodeList.get(randomNeighborIdx));
								interestedPeerNodeList.remove(randomNeighborIdx);
								if(unchokeNeighborObj.getPeerNodeChokedValue())
								{
									unchokeNeighborObj.updatePeerNodeChokedValue(false);
									unchokeNeighborObj.sendPeerNodeUnchokeMsg();
								}
							}
							chokePeerNodeAfterNewUnchoke(logFileNeighborList);
							peerProcess.writePeerNodeLogsObj.writePreferredNeighborLogs(peerProcess.currPeerNodeID, logFileNeighborList);
						}
					}
					else
					{
						if(interestedPeerNodeList.size() <= InitializingConfigData.getPreferredNeighborsCount())
						{
							for(int ptr=0; ptr<interestedPeerNodeList.size(); ptr++)
							{
								NeighborNodeObject unchokeNeighborObj = peerProcess.neighborNodeTable.get(interestedPeerNodeList.get(ptr));
								if(unchokeNeighborObj.getPeerNodeChokedValue())
								{
									unchokeNeighborObj.updatePeerNodeChokedValue(false);
									unchokeNeighborObj.sendPeerNodeUnchokeMsg();
								}
							}
							chokePeerNodeAfterNewUnchoke(interestedPeerNodeList);
							peerProcess.writePeerNodeLogsObj.writePreferredNeighborLogs(peerProcess.currPeerNodeID, interestedPeerNodeList);
						}
						else
						{
							List<Integer> logFileNeighborList = new ArrayList<>();
							for(int ptr=0; ptr<InitializingConfigData.getPreferredNeighborsCount(); ptr++)
							{
								int randomNeighborIdx = pickRandomInterestedIdx(interestedPeerNodeList);
								NeighborNodeObject unchokeNeighborObj = peerProcess.neighborNodeTable.get(interestedPeerNodeList.get(randomNeighborIdx));
								logFileNeighborList.add(interestedPeerNodeList.get(randomNeighborIdx));
								interestedPeerNodeList.remove(randomNeighborIdx);
								if(unchokeNeighborObj.getPeerNodeChokedValue())
								{
									unchokeNeighborObj.updatePeerNodeChokedValue(false);
									unchokeNeighborObj.sendPeerNodeUnchokeMsg();
								}
							}
							if(PreFixedValues.UNCHOKECONDN == "true")
							{
								getPeerNodeListAccToDownloadRate(interestedPeerNodeList);
							}
							chokePeerNodeAfterNewUnchoke(logFileNeighborList);
							peerProcess.writePeerNodeLogsObj.writePreferredNeighborLogs(peerProcess.currPeerNodeID, logFileNeighborList);
						}
					}
				}
				int unchokingIntervalVal = InitializingConfigData.getUnchokingIntervalValue();
				int unchokingIntervalTime = unchokingIntervalVal * 1000;
				Thread.sleep(unchokingIntervalTime);
			}
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
	}
}
