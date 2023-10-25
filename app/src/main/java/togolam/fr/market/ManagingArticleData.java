package togolam.fr.market;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManagingArticleData {

    // fields
    ArrayList<ArrayList<String>> arrayLists ;
    StringBuilder myBuilder;

    // constructors

    public ManagingArticleData( ) {

    }

    public ManagingArticleData(ArrayList<ArrayList<String>> arrayLists) {
        this.arrayLists = arrayLists;
    }

    public ManagingArticleData(StringBuilder myBuilder) {
        this.myBuilder = myBuilder;
    }

    // methods

    public int getArralistsSize(){
        return arrayLists.size()/arrayLists.get(0).size();
    }


    public String  getStringBuilder(ArrayList<ArrayList<String>> _arrayLists){
        /** get String from string arraylists */

        StringBuilder builder = new StringBuilder();
        int size = _arrayLists.size() ;
        for(int i = 0; i < size; i++){

            for(int j = 0; j < _arrayLists.get(0).size(); j++){

                builder.append(_arrayLists.get(i).get(j));

                if(j != _arrayLists.get(i).size() - 1 ) {
                    builder.append("--");
                }
            }

            if(i != size - 1 ) {
                builder.append("#");
            }
        }





        return builder.toString();

    }



    public ArrayList<ArrayList<String>> getArralistFromBuider(String str, ArrayList<ArrayList<String>> listArrayList){
    /** getting arralists from given StringBuilder */


        String[] parts = str.split("#");

        for(int t = 0; t < parts.length; t++){

            String[] myStrings = parts[t].split("--");
            ArrayList<String> stringArray= new ArrayList<>();

            for(int l = 0; l<myStrings.length; l++){
                stringArray.add(myStrings[l]);
            }

            listArrayList.add(stringArray);

        }
        return listArrayList;

    }






}
