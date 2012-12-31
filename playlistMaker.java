/*import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;*/
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class playlistMaker {
		static List <String> finalList= new ArrayList<String>();
		static List <Integer> finalRept= new ArrayList<Integer>();
		static int Sum =0;
		public static String makePlaylist(Playlist p, String player){
		  String list="(";
		  if(p.gcd == 1) p.gcdReset();
		  for(int i=0; i<p.count.size(); i++){
			  p.rem.set(i, ((int)p.count.get(i) - p.gcd*(int)((int)p.count.get(i) / p.gcd)));
			  p.count.set(i,( (int)p.count.get(i) / p.gcd));
		  }
		  int width = p.toSum();
		  int lookback = 1000;
		  for(int i=0;i<width;i++){
			  Object[] pr = p.count.toArray();
			  Arrays.sort(pr);
			  int val = (Integer) pr[pr.length-1];
			  if(val == 0) break;
			  int index = p.count.indexOf((Integer) val);
			  if(lookback == index){
				  val = (Integer) pr[pr.length-2];
				  if(val > 0){
					  index = p.count.lastIndexOf(((Integer) val));
					  if(list.endsWith(p.ads.get(index)))
						  index = p.count.indexOf(((Integer) val));
				  }
			  }
			  list = list + " "+p.ads.get(index);
			  p.count.set(index,p.count.get(index) - 1);
			  lookback = index;
		  }
		  finalList.add(list.substring(2));
		  finalRept.add(p.gcd);
		  Sum = Sum +p.gcd;
		  list = list +" )*"+p.gcd+" ";
		  return list;
		  
	  }
		
	public static String Make(Playlist ilist){
		String list = " ";
		while(true){
			 list = list + makePlaylist(ilist,"Player1");
			 for(int i=0; i<ilist.count.size(); i++)
				 ilist.count.set(i, ilist.rem.get(i));
			 if( ilist.maxRem() == 0)
					break;
			 ilist.gcdReset();
		 }
		return list;
	}
	public static void Print(){
		for(int i=0; i<Sum; i++){
			 int j = (int)(Math.random())%(finalList.size());
			 if(finalRept.get(j) >0){
				System.out.print(finalList.get(j) +" ");
				finalRept.set(j,finalRept.get(j) - 1);
			 }
		 }
	}
	  public static void main(String args[]){
		 String list;
		 Playlist ilist = new Playlist();
		 ilist.insertAd("Pepsi", 23);
		 ilist.insertAd("Coke", 87);
		 ilist.insertAd("Motorola", 91);
		 list = Make(ilist); 
		 System.out.println("Writing Output to Playlist: Player1");
		 System.out.println(list.substring(1));
		 Print();
	  }
	  
}
class Playlist{
	public List <String> ads = new ArrayList<String>();
	public List <Integer> count = new ArrayList<Integer>();
	public List <Integer> rem = new ArrayList<Integer>();
	public int gcd;
	boolean insertAd(String name, int plays){
		if(ads.size() == 0)
			gcd = plays;
		
		this.ads.add(name);
		this.count.add(plays);
		
		int temp = greatCommDiv(gcd,plays);
		this.rem.add(0);
		gcd = temp;
		return true;
	}
	boolean gcdReset(){
		for(int i=0;i<count.size();i++)
			gcd = this.greatCommDiv(gcd, count.get(i));
		if(gcd == 1) gcd = this.firstNonZero();
		return true;
	}
	int maxRem(){
		Object[] temp = count.toArray();
		Arrays.sort(temp);
		int maxValue = (Integer)temp[temp.length -1];
		return maxValue;
	}
	int firstNonZero(){
		Object[] temp = count.toArray();
		Arrays.sort(temp);
		int i;
		for(i=0;i<temp.length;i++){
			if((Integer)temp[i] >0)
				break;
		}
		if((Integer)temp[i] == 0)
			return 0;
		else
			return (Integer)temp[i];
		
	}
	int greatCommDiv(int g,int b){
		if(b==0)
			return g;
		else 
			g = greatCommDiv(b,g%b);
		return g;
	}
	public int toSum(){
		int sum = 0;
		for(int i=0; i<count.size();i++)
			sum = sum + count.get(i);
		return sum;
	}
	
	public String toString(){
		String list="";
		for(int i=0;i<ads.size();i++){
			list = list +ads.get(i)+": "+count.get(i)+", ";
		}
		list = list +", with gcd "+gcd+", Remainders ";
		for(int i=0;i<ads.size();i++){
			list = list +ads.get(i)+": "+rem.get(i)+", ";
		}
		return list;
	}
}