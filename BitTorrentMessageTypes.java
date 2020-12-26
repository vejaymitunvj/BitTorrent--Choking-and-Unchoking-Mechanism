import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BitTorrentMessageTypes {
	
 private HashMap<String, Character> msgTypeMap;
 
 public static final char BTMSGTYPE_CHOKE 			= '0';
 public static final char BTMSGTYPE_UNCHOKE 		= '1';
 public static final char BTMSGTYPE_INTERESTED 		= '2';
 public static final char BTMSGTYPE_NOTINTERESTED 	= '3';
 public static final char BTMSGTYPE_HAVE 			= '4';
 public static final char BTMSGTYPE_BITFIELD 		= '5';
 public static final char BTMSGTYPE_REQUEST			= '6';
 public static final char BTMSGTYPE_PIECE 			= '7';
	
	public BitTorrentMessageTypes() {
		msgTypeMap = new HashMap<>();
		msgTypeMap.put("choke" , '0');
		msgTypeMap.put("unchoke" , '1');
		msgTypeMap.put("interested" , '2');
		msgTypeMap.put("notinterested" , '3');
		msgTypeMap.put("have" , '4');
		msgTypeMap.put("bitfield" , '5');
		msgTypeMap.put("request" , '6');
		msgTypeMap.put("piece" , '7');
	}
	
	public char getBitTorrentMsgType(String msg) {
		char valueChar = 0;
		
		switch(msg) {
		  case "choke":
			  valueChar = msgTypeMap.get("choke");
			  	return valueChar;
		
		  case "unchoke":
			  valueChar = msgTypeMap.get("unchoke");
			  return valueChar;
		    
		  case "interested":
			  valueChar = msgTypeMap.get("interested");
			  return valueChar;
			
		  case "notinterested":
			  valueChar = msgTypeMap.get("notinterested");
			  return valueChar;
			  
		  case "have":
			  valueChar = msgTypeMap.get("have");
			  return valueChar;
			
		  case "bitfield":
			  valueChar = msgTypeMap.get("bitfield");
			  return valueChar;
			  
		  case "request":
			  valueChar = msgTypeMap.get("request");
			  return valueChar;
			
		  case "piece":
			  valueChar = msgTypeMap.get("piece");
			  return valueChar;

		  default:
		    System.out.println("Wrong message");
		}
		return valueChar;
			
	}
	
	public List<Character> getNoPayloadMsgTypes()
	{
		List<Character> resultList = new ArrayList<Character>();
		resultList.add('0');
		resultList.add('1');
		resultList.add('2');
		resultList.add('3');
		return resultList;
	}
	
	public List<Character> getPayloadMsgTypes()
	{
		List<Character> resultList = new ArrayList<Character>();
		resultList.add('4');
		resultList.add('5');
		resultList.add('6');
		resultList.add('7');
		return resultList;
	}

}
