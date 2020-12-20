/*M�sl�m T�rk
 * The program is considered as a general cashier which serves customers.
 * It includes implementation of the interface MyQueue.
 */
import java.util.Iterator;
import java.util.Random;

public class CrazyMarket implements MyQueue<Customer>{
	float currentTime=0;//simulat�r�n s�resi
	int size=0;//kuyruktaki eleman say�s�
	Node head=null,tail=null;//linkedlist'in ba� ve son elemanlar�n� tutar
	
	String tekerleme = "O piti piti karamela sepeti "
			+ "\nTerazi lastik jimnastik "
			+ "\nBiz size geldik bitlendik Hamama gittik temizlendik.";
	
	public class Node {
		Node next;
		Customer data;
		public Node(Customer data) {
			this.data=data;
			this.next=null;
		}
	}
			
	@Override
	public Iterator<Customer> iterator() {
		return new QueueIterator();
	}
	
	private class QueueIterator implements Iterator<Customer> {
		private Node itr=head;
		
		@Override
		public boolean hasNext() {
			return itr.next!=null;
		}

		@Override
		public Customer next() {
			Customer data=itr.data;
			itr=itr.next;
			return data;
		}		
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return head==null;
	}

	@Override
	public boolean enqueue(Customer item) {
		Node node=new Node(item);
		
		if(isEmpty()) {//kuyrukta hi� eleman yoksa
			head=node;
			tail=node;
			tail.next=head;//son eleman ilk eleman� g�stericek
			size++;
			return true;
		}		
		tail.next=node;
		tail=node;
		tail.next=head;
		size++;
		return true;
	}

	@Override
	public Customer dequeuNext() {
		
		if(!isEmpty()) {//kuyruk bo� de�ilse
			Customer temp=head.data;			
			if(head==tail){//kuyrukta bir eleman kald�ysa
				head=null;
				size--;
				return temp;
			}
			head=head.next;	
			tail.next=head;
			size--;
			return temp;
		}
		return null;
	}

	@Override
	public Customer dequeuWithCounting(String tekerleme) {
		if(!isEmpty()) {//kuyruk bo� de�ilse
	
			if(head==tail) {//kuyrukta bir eleman kald�ysa
				Customer temp=head.data;
				head=null;
				size--;
				return temp;
			}
			
			int count=heceSayisi()-2;
			Node itr=head;
			
			while(count>0) {//kuyrukta dola�arak silinecek elemandan �nceki eleman bulunur
				itr=itr.next;
				count--;
			}
			
			Node prev=itr;//silinecek elemadan �nceki eleman
			itr=itr.next;//silinecek eleman	
			Customer temp=itr.data;
			
			if(itr==head) {//ilk eleman silinecekse
				head=head.next;
				tail.next=head;
			}
			else if(itr==tail) {//son eleman silinecekse
				prev.next=head;
				tail=prev;
			}
			else {//ortadan silinecekse
				prev.next=itr.next;
			}
			return temp;		
		}
		return null;
	}
	
	public int heceSayisi() {//tekerlemedeki hece say�s�n� d�nd�r�r
		int heceSayisi=0;
		tekerleme=tekerleme.toLowerCase();
		
		for(int i=0;i<tekerleme.length();i++) {
			if(tekerleme.charAt(i)=='a' || tekerleme.charAt(i)=='e' 
				|| tekerleme.charAt(i)=='�' || tekerleme.charAt(i)=='i' 
				||tekerleme.charAt(i)=='o' || tekerleme.charAt(i)=='�' 
				||tekerleme.charAt(i)=='u' || tekerleme.charAt(i)=='�' ) {
				heceSayisi++;
			}
		}
		return heceSayisi;	
	}
	
	public float hizmeteAl(Customer customer) {//removal time hesaplan�p d�nd�r�l�r
		Random rand = new Random();
		float removalTime = currentTime + 1 + rand.nextFloat()%2;
		customer.setRemovalTime(removalTime);
		return removalTime;
	}
	
	public void kalanlariYazdir() {//kuyrukta kalanlar� yazd�r�r
		CrazyMarket queue=this;	
		tail.next=null;		
		int i=1;
		
		for(Customer c : queue) {
			System.out.println("Kurukta kalan "+ i +". eleman�n bekleme s�resi: " +(currentTime-c.arrivalTime));	
			i++;		
		}
	}

	public CrazyMarket(int numberOfCustomer) {
		Random rand = new Random();
		int counter=0;//hizmet g�ren eleman say�s�
		float arrivalTime=0;
		float removalTime=0;
		boolean isAvaible=true;//kasa bo�ta m�
		
		while(counter<numberOfCustomer) {//numberOfCustomer kadar m��teri hizmet g�rene kadar d�ner
			arrivalTime=currentTime+rand.nextFloat()%2;//m��teri geli� zaman� hesaplan�r
			
			while(true) {
				if(currentTime>=arrivalTime) {//currenTime m��teri geli� zaman�na gelince eleman kuyru�a eklenir
					enqueue(new Customer(arrivalTime));
					break;
				}
				if(head!=null && isAvaible==true) {//kasa bo�sa ve kuyrukta eleman varsa hizmete al�n�r
					removalTime = hizmeteAl(chooseCustomer());
					isAvaible=false; 
				}
				if(isAvaible==false && currentTime>=removalTime) {//kasada eleman varsa ve currentTime ayr�lma zaman�na gelmi�se 
					counter++;										//hizmetten ��kar�l�r
					isAvaible=true;
				}
				currentTime+=0.001;//zaman 1 ms att�r�l�r	
			}									
		}
		kalanlariYazdir();
	}	

	public CrazyMarket(int numberOfCustomer, String tekerleme) {
		this.tekerleme = tekerleme;
		Random rand = new Random();
		int counter=0;//hizmet g�ren eleman say�s�
		float arrivalTime=0;
		float removalTime=0;
		boolean isAvaible=true;//kasa bo�ta m�
		
		while(counter<numberOfCustomer) {//numberOfCustomer kadar m��teri hizmet g�rene kadar d�ner
			arrivalTime=currentTime+rand.nextFloat()%2;//m��teri geli� zaman� hesaplan�r
			
			while(true) {
				if(currentTime>=arrivalTime) {//currenTime m��teri geli� zaman�na gelince eleman kuyru�a eklenir
					enqueue(new Customer(arrivalTime));
					break;
				}
				if(head!=null && isAvaible==true) {//kasa bo�sa ve kuyrukta eleman varsa hizmete al�n�r
					removalTime = hizmeteAl(chooseCustomer());
					isAvaible=false; 
				}
				if(isAvaible==false && currentTime>=removalTime) {//kasada eleman varsa ve currentTime ayr�lma zaman�na gelmi�se 
					counter++;										//hizmetten ��kar�l�r
					isAvaible=true;
				}
				currentTime+=0.001;//zaman 1 ms att�r�l�r	
			}									
		}
		kalanlariYazdir();
	}
	
	public Customer chooseCustomer() {		
		if((currentTime-head.data.arrivalTime)>10) {//s�radaki eleman�n bekleme s�resi 10'dan b�y�kse
			return dequeuNext();		
		}		
		return dequeuWithCounting(tekerleme);	
	}
	
	public static void main(String[] args) {
		new CrazyMarket(1000);
	}
}
