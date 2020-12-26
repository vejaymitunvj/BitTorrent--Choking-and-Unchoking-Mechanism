import java.util.List;
import java.util.Random;

public class OptimisticallyUnchokedChannel implements Runnable
{
	@Override
	public void run()
	{
		try
		{
			while(peerProcess.noOfPeersWithFullFile < peerProcess.peerNodeTable.size())
			{
				List<Integer> interestedPeerNodeList = CommonMethods.getInterestedPeerNodeList();
				NeighborNodeObject optUnchokedNeighborObj;
				if(interestedPeerNodeList.size() > 0)
				{
					Random randomObj = new Random();
					int randomInterestedPeerNode = interestedPeerNodeList.get(randomObj.nextInt(interestedPeerNodeList.size()));
					optUnchokedNeighborObj = peerProcess.neighborNodeTable.get(randomInterestedPeerNode);
					optUnchokedNeighborObj.sendPeerNodeUnchokeMsg();
					optUnchokedNeighborObj.updatePeerNodeOptUnchokedValue(true);
					peerProcess.writePeerNodeLogsObj.writeOptUnchokedLogs(peerProcess.currPeerNodeID, randomInterestedPeerNode);
					int optUnchokingIntervalVal = InitializingConfigData.getOptimisticUnchokingIntervalValue();
					int optUnchokingIntervalTime = optUnchokingIntervalVal * 1000;
					Thread.sleep(optUnchokingIntervalTime);
					optUnchokedNeighborObj.updatePeerNodeOptUnchokedValue(false);
					if(optUnchokedNeighborObj.getPeerNodeChokedValue())
					{
						optUnchokedNeighborObj.sendPeerNodeChokeMsg();
					}
				}
			}
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
	}
}
