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

    public ArrayList freq(ArrayList<ArrayList> groups){
        ArrayList<Double> probOfEachGroup=new ArrayList<Double>();
        ArrayList result=new ArrayList();
        for (ArrayList group:groups){
            probOfEachGroup.add((double)group.get(0)*(group.size()-1));
        }

        for(double d:probOfEachGroup){

        }


    }



    public static void main(String[] arg){
        String[][] input={{"1","0.2"},
                        {"2","0.3"},
                {"3","0.3"},
                {"4","0.2"},
                         };
        partition(input);
    }
}
