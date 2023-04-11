class WordInformation{

   String word;
   LinkedList<WordOccurrence> occList;
   int size;
   
   public WordInformation(){      
      occList = new LinkedList<WordOccurrence>();
   }

   public WordInformation(String w, int l, int p){
      occList = new LinkedList<WordOccurrence>();
      word=w;
      add(l,p);
   
   }
   public void add(int l, int p){
      occList.insert(new WordOccurrence(l,p));
      size++;
   
   }
   
   public String getWord(){
      return word;}
      
   public int getSize(){
      return size;}
      
   public LinkedList<WordOccurrence> getOccList(){
      return occList;}
      

}