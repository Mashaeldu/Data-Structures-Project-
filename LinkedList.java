class LinkedList<T>{
   private Node<T> head;
   private Node<T> current;
   private int size;

   public LinkedList(){
      head=current=null;
      size=0;
   }
   
   public boolean empty(){
      return size==0;}
   public boolean last(){
      return current.next==null;
   
   }
   public boolean full(){
      return false;}
   public void findfirst(){current=head;}
   public void findnext(){current=current.next;}
   public T retrieve(){
      return current.data;}
   public void insert(T val){
   
      Node<T> tmp;
      if(empty())
         current=head=new Node<T>(val);
      else{
         tmp=current.next;
         current.next=new Node<T>(val);
         current=current.next;
         current.next=tmp;
      }
      
      size++;
   
   
   }
   
   public int listSize(){
      return size;}
      
}