class WordOccurrence{

   int lineNo;
   int position;
   
   public WordOccurrence(){}
   public WordOccurrence(int l, int p ){
      lineNo = l;
      position = p;
      
   }
   
   public String toString(){
      return "("+lineNo+","+position+") ";
   }
   
  

}