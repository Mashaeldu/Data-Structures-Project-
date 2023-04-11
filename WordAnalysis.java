import java.util.*;
import java.io.*;
class WordAnalysis{

   LinkedList<WordInformation> arrayOfDifferentLengths[];
   WordInformation sortedArray[];
   int n,m;


   /*************************
   *****HELPING METHOD******
   *************************/

   /*this method gets length of the longest word in the file*/
   private int getSizeOfLongestWord(String f)throws Exception{
      String word; 
      int max=0;
      Scanner read = new Scanner(new File(f));
      while(read.hasNext()){
         word=read.next().replaceAll("(?![' -])\\p{Punct}","");
         if(word.length()>max)
            max=word.length();      
      }
      return max;
   }
   
   
   /*************************
   *****HELPING METHOD******
   *************************/

   /*this methos fills and sorts all unique words based on thier occurrences*/
   private void sortArray(){
      int k=0;
      sortedArray = new WordInformation[m];
      //fill sorted array
      for(int i=1; i<arrayOfDifferentLengths.length; i++){
               
         if(arrayOfDifferentLengths[i] != null)
            if(!arrayOfDifferentLengths[i].empty()){
               arrayOfDifferentLengths[i].findfirst();
               while(!arrayOfDifferentLengths[i].last()){
                  sortedArray[k]=arrayOfDifferentLengths[i].retrieve();
                  k++;
                  arrayOfDifferentLengths[i].findnext(); 
               }
               sortedArray[k]=arrayOfDifferentLengths[i].retrieve();
               k++;
            }}
      
      //sort array     
      for (int i=0;i<m-1;i++) {
         for (int j=0; j<m-1-i;j++) { 
            if (sortedArray[j].getSize() < sortedArray[j+1].getSize()) {
               WordInformation tmp = sortedArray[j]; 
               sortedArray[j] = sortedArray[j+1]; 
               sortedArray[j+1] = tmp;
            }
         }
      }
            
   }
   
  
   public void readFileAndAnalyse(String f){
      m=n=0;
      try{
      //find array size and creat objects of array
         int arrSize = getSizeOfLongestWord(f)+1;
         
         arrayOfDifferentLengths = new LinkedList[arrSize];
         
         for(int i=0; i<arrayOfDifferentLengths.length; i++)
            arrayOfDifferentLengths[i] = new LinkedList<WordInformation>();
      
      
      //read file
         Scanner input = new Scanner(new File(f));
         String words[];
         int lineCount=0, pos=0, length;
         boolean exist;
         
         while(input.hasNext()){
            /*assume empty line is a countable line*/
            words = input.nextLine().split("\\s+");
            lineCount++;
            pos=0;
         
         //read line word by word
            for(int i=0; i<words.length; i++){
            
               if(words[i].equals("\\n") ){
                  if(i < words.length-1){ 
                     lineCount++;
                     pos=0;}
                  else
                     pos=0;
               }
               
               else{
                  pos++;
                  exist=false;
                  words[i]=words[i].replaceAll("(?![' -])\\p{Punct}","");
               
                  if(words[i].equalsIgnoreCase("")) /*if word is all puncuation e.g. (''.)  ***JUST IN CASE*** */
                     continue;
               
                  length=words[i].length();
               
               /*check if word is exist*/
                  if(!arrayOfDifferentLengths[length].empty()){
                     arrayOfDifferentLengths[length].findfirst();
                     while(!arrayOfDifferentLengths[length].last()){
                        if(arrayOfDifferentLengths[length].retrieve().getWord().equalsIgnoreCase(words[i])){
                           arrayOfDifferentLengths[length].retrieve().add(lineCount,pos);
                           exist=true;
                        }
                        arrayOfDifferentLengths[length].findnext(); 
                     }
                  
                  /*check last node*/
                     if(arrayOfDifferentLengths[length].retrieve().getWord().equalsIgnoreCase(words[i])){
                        arrayOfDifferentLengths[length].retrieve().add(lineCount,pos);
                        exist=true; 
                     }
                  
                  }//end if not empty
               
                  if(exist){
                     n++;
                     continue;}
               
               /*this is the case if word d.n.e*/
                  arrayOfDifferentLengths[length].insert(new WordInformation(words[i],lineCount,pos));  
                  m++;
                  n++;
               
               }
            }
         }
      
      /*Fill and Sort sorted array*/
         sortArray();
         
      }catch(Exception e){}
   
   }   
   
         
   public int documentLength(){
      return n;
   }
   
   public int uniqueWords(){
      return m;
   }
   
   public int totalWord (String s){
      for (int i=0; i<m; i++)
         if(sortedArray[i]!=null && sortedArray[i].getWord().equalsIgnoreCase(s))
            return sortedArray[i].getSize();
      return 0;
   }
   public int totalWordsForLength(int l){
      if(l>0 && l<arrayOfDifferentLengths.length)
         return arrayOfDifferentLengths[l].listSize();
      else
         return 0;
   }
   
   public void displayUniqueWords(){
      for (int i=0; i<m; i++)
         System.out.println("(" + sortedArray[i].getWord() + "," + sortedArray[i].getOccList().listSize()+ ") ");
   
   }
   
   public LinkedList<WordOccurrence> occurrences (String w){
   
      for (int i=0; i<m; i++)
         if(sortedArray[i].getWord().equalsIgnoreCase(w))
            return sortedArray[i].getOccList();
         
      return null;
   }
   
   public boolean checkAdjacent (String w1, String w2){
      
      /*there is a chance that w1 or w2 or both dne 
      hence array would be null if so catch exception 
      then return false anyway*/
      
      try{
      
         WordOccurrence [] array1 = moveToArray(w1); 
         WordOccurrence [] array2 = moveToArray(w2);
      
         int i=0, j=0;
      
         while(i<array1.length && j<array2.length){
            if(array1[i].lineNo == array2[j].lineNo)
               if(array1[i].position+1 == array2[j].position || array1[i].position == array2[j].position+1)
                  return true;
               else if(array1[i].position > array2[j].position)
                  j++;
               else
                  i++;
            else if( array1[i].lineNo > array2[j].lineNo)
               j++;
            else
               i++;
         }
      }catch(Exception e){}
   
      
      return false; 
   }
   
  /*************************
   *****HELPING METHOD******
   *************************
   this method moves data 
   of WordOccurrence list to an array*/
   private WordOccurrence [] moveToArray(String w){
     
     /*there is a chance that w dne 
     hence list would be null if so 
     catch exception and return null*/
      
      WordOccurrence [] array;
      
      try{
         
         LinkedList<WordOccurrence> list = occurrences(w);
         array = new WordOccurrence[list.listSize()]; 
         
         int i=0;
         if(!list.empty() ){
            list.findfirst();
            while(!list.last() ){
               array[i++]=list.retrieve();
               list.findnext(); }
            array[i]=list.retrieve();
         }
      }
      catch(Exception e){ 
         return null;}
   
      return array;
   
   }
   
  
}