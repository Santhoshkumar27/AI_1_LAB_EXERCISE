import java.util.*;

public class Node {
	
	enum NodeType {ANDNODE, ORNODE, LEAF, DEFAULT};
	enum NodeState {SOLVED, UNSOLVED, UNKNOWN};
	
	int					idx;							
	String				str;							
	int					next = 0;						
														
	int					from;							
	ArrayList<Integer>	to = new ArrayList<Integer>();	
	NodeType			type;							
	NodeState			state = NodeState.UNKNOWN;		
	int					depth = 0;						
	
	
	static HashMap<String, String[]> rule = new HashMap<String, String[]>();
	
	public Node(int id){
		idx = id;
	}
	
	public Node(int id, String s){
		idx = id;
		str = s;
		decideNodeType();
	}
	
	public Node(int id, String s, int parent, int dp){
		idx = id;
		str = s;
		decideNodeType();
		from = parent;
		depth = dp;
	}
	
	

	/*
	
	public void setIdx(int index){
		idx = index;
	}
	*/
	
	public int getIdx(){
		return idx;
	}
	
	public void setStr(String input){
		str = input;
	}
	
	public String getStr(){
		return str;
	}

	public void setNext(int id){
		next = id;
	}
	
	public int getNext(){
		return next;
	}
	
	
	public void setFrom(int f){
		from = f;
	}
	
	public int getFrom(){
		return from;
	}
	
	public void addTo(int t){
		to.add(t);
	}
	
	public void removeTo(int nodeId){
		for(int i=0; i<to.size(); i++)
			if(to.get(i) == nodeId)
				to.remove(i);
	}
	
	public ArrayList<Integer> getTo(){
		return to;
	}
	
	/*
	
	public void setType(NodeType nt){
		type = nt;
	}
	*/
	
	public boolean isNodeAND(){
		return type == NodeType.ANDNODE;
	}
	
	public boolean isNodeOR(){
		return type == NodeType.ORNODE;
	}
	
	public boolean isNodeLeaf(){
		return type == NodeType.LEAF;
	}
	
	public void setSolved(){
		state = NodeState.SOLVED;
	}
	
	public void setUnsolved(){
		state = NodeState.UNSOLVED;
	}
	
	public boolean isSolved(){
		return state == NodeState.SOLVED;
	}
	
	public boolean isUnsolved(){
		return state == NodeState.UNSOLVED;
	}
	
	public boolean isUnknown(){
		return state == NodeState.UNKNOWN;
	}
	
	
	public static void setRule(String key, String[] value){
		rule.put(key, value);
	}
	
	public static String[] getRule(String input){
		return rule.get(input);
	}
	
	
	public void printStatus(){
		for(int i=0; i<depth; i++)
			System.out.print("\t");
		
		System.out.println(str + " idx:" + idx + " strlen:" + str.length() + 
				" from:" + from + " to:" + to + " state:" + state);
		/* デバッグ用
		System.out.println(str + " idx:" + idx + " strlen:" + str.length() + 
				" next:" + next + " type:" + type + " from:" + from + " to:" + to + " state:" + state);
		*/
	}
	
	
	public void decideNodeType(){
		if(str.length() > 1){
			type = NodeType.ANDNODE;
		}else if(str.length() == 1){
			char[] ch = str.toCharArray();
			type = ( Character.isUpperCase(ch[0]) ? NodeType.ORNODE :
				   ( Character.isLowerCase(ch[0]) ? NodeType.LEAF : NodeType.DEFAULT ) );
		}
	}
	
	
	public boolean isNodeExpanded(){
		if(isNodeAND()){
			// ANDノードなら文字列の長さとnextとの比較で判定する
			if(next >= str.length())
				return true;
			else
				return false;
		}else if(isNodeOR()){
			// ORノードなら生成規則の数とnextとの比較で判定する
			if(next >= rule.get(str).length)
				return true;
			else
				return false;
		}else{
			// Leafは展開済みとして扱う
			return true;
		}
	}
	
	// 子ノードを一つ展開して返す
	public Node generateNextNode(int id)
	{
		String st=null;
		
		try{
			if(type == NodeType.ANDNODE){
				st = str.substring(next++, next);
				to.add(id);
				return new Node(id, st, idx, depth+1);
			}else if(type == NodeType.ORNODE){
				st = rule.get(str)[next++];
				to.add(id);
				return new Node(id, st, idx, depth+1);
			}else{
				throw new RuntimeException();
			}
		}catch(RuntimeException e){
			throw new RuntimeException();
		}
	}
}
