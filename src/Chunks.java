import java.util.ArrayList;
import java.util.*;

/**
 * Created by xc9pd on 3/15/2016.
 */
public class Chunks {



    public static void partition(String[][] inf){
        int len=inf.length;
        System.out.println("The input is :");
        for(int i=0;i<len;i++){
            System.out.println(inf[i][0]+" has probability "+ inf[i][1]);
        }
        sort(inf);

        System.out.println("The sorted version of this input is: ");
        for(int i=0;i<len;i++){
            System.out.println(inf[i][0]+" has probability "+ inf[i][1]);
        }

        ArrayList<ArrayList> groups=group(inf);
        System.out.println("The grouped version of this input is: ");
        int count=0;
        for(ArrayList group:groups){
            count++;
            int l=group.size();
            System.out.print("Group ["+ count+"] with probability :("+ group.get(0)+") has members : {");
            for(int j=1;j<l;j++){
                System.out.print(group.get(j)+",");
            }
            System.out.println("}");
        }
        System.out.println("So there are total "+groups.size()+" disks");

        ArrayList<Long> freq=freq(groups);
        System.out.println("The frequency of each disk is: ");
        count=0;
        for(long frequency: freq){
            count++;
            System.out.println("#######: The"+ count +"disk's frequency is: "+frequency);

        }
        ArrayList<Integer> chunk=numOfChunk(freq);
        System.out.println("The number of chunk of each disk is: ");
        count=0;
        for(Integer c: chunk){
            count++;
            System.out.println("********: The "+count+" disk's chunk number is: "+ c);
        }

        ArrayList<Integer> actualchunk=actualChunk(groups,chunk);
        System.out.println("The number of actual chunk of each disk is: ");
        count=0;
        for(Integer c: actualchunk){
            count++;
            System.out.println("********: The "+count+" disk's actual chunk number is: "+ c);
        }

        ArrayList<Integer> bc=schedule(groups,actualchunk);
        System.out.println("The broadcast schedule is: ");
        for(int i:bc){
            System.out.print(i+"  ");
        }

    }

    public static void sort(String[][] inf){
        final Comparator<String[]> arrayComparator = new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                if((Double.parseDouble(o1[1]))>(Double.parseDouble(o2[1])))return -1;
                else if((Double.parseDouble(o1[1]))<(Double.parseDouble(o2[1])))return 1;
                else return 0;
            }

        };
        Arrays.sort(inf, arrayComparator);

    }

    public static ArrayList<ArrayList> group(String[][] inf){
        ArrayList result=new ArrayList();

        double currentP;
        int len=inf.length;
        //System.out.println("length "+len   );
        for(int i=0;i<len;){
            ArrayList group=new ArrayList();
            currentP=Double.parseDouble( inf[i][1]);
            group.add(currentP);
            while(i<len&&Math.abs(Double.parseDouble(inf[i][1])-currentP)<0.001){
                group.add(inf[i][0]);
                i++;
            }
            //System.out.println(i);
            result.add(group);
        }
        return result;
    }

    public static ArrayList<Long> freq(ArrayList<ArrayList> groups){
        ArrayList<Double> probOfEachGroup=new ArrayList<Double>();
        ArrayList<Long> result=new ArrayList<Long>();
        for (ArrayList group:groups){
            probOfEachGroup.add((double)group.get(0)*(group.size()-1));
        }
        double lestProb=probOfEachGroup.get(probOfEachGroup.size()-1);

        for(double d:probOfEachGroup){
            result.add(Math.round(Math.sqrt(d/lestProb)));
        }
        return result;
    }

    public static ArrayList<Integer> numOfChunk(ArrayList<Long> freq){
        Long LCMFreq=nLCM(freq);
        ArrayList<Integer> chunk=new ArrayList<Integer>();
        for(Long frequency:freq){
            chunk.add((int)(LCMFreq/frequency));
        }
        return chunk;
    }

    public static ArrayList<Integer> actualChunk(ArrayList<ArrayList> groups,ArrayList<Integer> chunk){
        int size=chunk.size();
        ArrayList<Integer> actualChunk=new ArrayList<Integer>();
        for(int i=0;i<size;i++){
            actualChunk.add((int)gcd(groups.get(i).size()-1,chunk.get(i)));
        }
        return actualChunk;
    }

    public static ArrayList<ArrayList<ArrayList<Integer>>> Cij(ArrayList<ArrayList> groups,ArrayList<Integer> chunk){
        int numOfdisk=groups.size();
        ArrayList result=new ArrayList();

        for(int i=0;i<numOfdisk;i++){
            int node=(groups.get(i).size()-1)/chunk.get(i);
            ArrayList diskI=new ArrayList();
            for(int j=0;j<chunk.get(i);j++){
                ArrayList<Integer> nodesJ=new ArrayList<Integer>();
                for(int k=0;k<node;k++){
                    nodesJ.add(Integer.parseInt((String)groups.get(i).get(1+j*node+k)));
                }
                diskI.add(nodesJ);
            }
            result.add(diskI);
        }
        return result;
    }

    public static ArrayList<Integer> schedule(ArrayList<ArrayList> groups,ArrayList<Integer> actualChunk) {
        int max=max(actualChunk);
        ArrayList<Integer> result=new ArrayList<Integer>();
        int k=0;
        ArrayList<ArrayList<ArrayList<Integer>>> cij=Cij(groups,actualChunk);
        for(int i=0;i<max;i++){
            for(int j=0;j<actualChunk.size();j++){
                k=i%(actualChunk.get(j));
                if(k<cij.get(j).size()){
                    for(int n:cij.get(j).get(k)){
                        result.add(n);
                    }
                }
            }
        }
        return result;
    }

    public static int max(ArrayList<Integer> actualChunk){
        int max=0;
        for(int i:actualChunk){
            if(max<i)max=i;
        }
        return max;
    }


    public static long nLCM(ArrayList<Long> freq){
        int length=freq.size();
        if(freq.size()==1)return freq.get(0);
        long buf=freq.get(0);
        for(int i=1;i<length;i++){
            buf=LCM(buf,freq.get(i));
        }
        return buf;
    }

    public static long LCM(long n1,long n2){
        return n1*n2/gcd(n1,n2);
    }

    public static long gcd(long a, long b)

    {
        long buf;
        if (a < b)
        {
            buf=a;
            a=b;
            b=buf;
        }
        if (b == 0)

            return a;

        else

            return gcd(b, a%b);

    }



    public static void main(String[] arg){
        String[][] input={{"1","0.1"},
                        {"2","0.4"},
                {"3","0.4"},
                {"4","0.1"},
                         };
        partition(input);
    }
}
