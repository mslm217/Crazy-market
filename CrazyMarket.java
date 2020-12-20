/*Müslüm Türk
 * The program is considered as a general cashier which serves customers.
 * It includes implementation of the interface MyQueue.
 */
import java.util.Iterator;
import java.util.Random;

public class CrazyMarket implements MyQueue<Customer>{
	float currentTime=0;//simulatörün süresi
	int size=0;//kuyruktaki eleman sayýsý
	Node head=null,tail=null;//linkedlist'in baþ ve son elemanlarýný tutar
	
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
		
		if(isEmpty()) {//kuyrukta hiç eleman yoksa
			head=node;
			tail=node;
			tail.next=head;//son eleman ilk elemaný göstericek
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
		
		if(!isEmpty()) {//kuyruk boþ deðilse
			Customer temp=head.data;			
			if(head==tail){//kuyrukta bir eleman kaldýysa
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
		if(!isEmpty()) {//kuyruk boþ deðilse
	
			if(head==tail) {//kuyrukta bir eleman kaldýysa
				Customer temp=head.data;
				head=null;
				size--;
				return temp;
			}
			
			int count=heceSayisi()-2;
			Node itr=head;
			
			while(count>0) {//kuyrukta dolaþarak silinecek elemandan önceki eleman bulunur
				itr=itr.next;
				count--;
			}
			
			Node prev=itr;//silinecek elemadan önceki eleman
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
	
	public int heceSayisi() {//tekerlemedeki hece sayýsýný döndürür
		int heceSayisi=0;
		tekerleme=tekerleme.toLowerCase();
		
		for(int i=0;i<tekerleme.length();i++) {
			if(tekerleme.charAt(i)=='a' || tekerleme.charAt(i)=='e' 
				|| tekerleme.charAt(i)=='ý' || tekerleme.charAt(i)=='i' 
				||tekerleme.charAt(i)=='o' || tekerleme.charAt(i)=='ö' 
				||tekerleme.charAt(i)=='u' || tekerleme.charAt(i)=='ü' ) {
				heceSayisi++;
			}
		}
		return heceSayisi;	
	}
	
	public float hizmeteAl(Customer customer) {//removal time hesaplanýp döndürülür
		Random rand = new Random();
		float removalTime = currentTime + 1 + rand.nextFloat()%2;
		customer.setRemovalTime(removalTime);
		return removalTime;
	}
	
	public void kalanlariYazdir() {//kuyrukta kalanlarý yazdýrýr
		CrazyMarket queue=this;	
		tail.next=null;		
		int i=1;
		
		for(Customer c : queue) {
			System.out.println("Kurukta kalan "+ i +". elemanýn bekleme süresi: " +(currentTime-c.arrivalTime));	
			i++;		
		}
	}

	public CrazyMarket(int numberOfCustomer) {
		Random rand = new Random();
		int counter=0;//hizmet gören eleman sayýsý
		float arrivalTime=0;
		float removalTime=0;
		boolean isAvaible=true;//kasa boþta mý
		
		while(counter<numberOfCustomer) {//numberOfCustomer kadar müþteri hizmet görene kadar döner
			arrivalTime=currentTime+rand.nextFloat()%2;//müþteri geliþ zamaný hesaplanýr
			
			while(true) {
				if(currentTime>=arrivalTime) {//currenTime müþteri geliþ zamanýna gelince eleman kuyruða eklenir
					enqueue(new Customer(arrivalTime));
					break;
				}
				if(head!=null && isAvaible==true) {//kasa boþsa ve kuyrukta eleman varsa hizmete alýnýr
					removalTime = hizmeteAl(chooseCustomer());
					isAvaible=false; 
				}
				if(isAvaible==false && currentTime>=removalTime) {//kasada eleman varsa ve currentTime ayrýlma zamanýna gelmiþse 
					counter++;										//hizmetten çýkarýlýr
					isAvaible=true;
				}
				currentTime+=0.001;//zaman 1 ms attýrýlýr	
			}									
		}
		kalanlariYazdir();
	}	

	public CrazyMarket(int numberOfCustomer, String tekerleme) {
		this.tekerleme = tekerleme;
		Random rand = new Random();
		int counter=0;//hizmet gören eleman sayýsý
		float arrivalTime=0;
		float removalTime=0;
		boolean isAvaible=true;//kasa boþta mý
		
		while(counter<numberOfCustomer) {//numberOfCustomer kadar müþteri hizmet görene kadar döner
			arrivalTime=currentTime+rand.nextFloat()%2;//müþteri geliþ zamaný hesaplanýr
			
			while(true) {
				if(currentTime>=arrivalTime) {//currenTime müþteri geliþ zamanýna gelince eleman kuyruða eklenir
					enqueue(new Customer(arrivalTime));
					break;
				}
				if(head!=null && isAvaible==true) {//kasa boþsa ve kuyrukta eleman varsa hizmete alýnýr
					removalTime = hizmeteAl(chooseCustomer());
					isAvaible=false; 
				}
				if(isAvaible==false && currentTime>=removalTime) {//kasada eleman varsa ve currentTime ayrýlma zamanýna gelmiþse 
					counter++;										//hizmetten çýkarýlýr
					isAvaible=true;
				}
				currentTime+=0.001;//zaman 1 ms attýrýlýr	
			}									
		}
		kalanlariYazdir();
	}
	
	public Customer chooseCustomer() {		
		if((currentTime-head.data.arrivalTime)>10) {//sýradaki elemanýn bekleme süresi 10'dan büyükse
			return dequeuNext();		
		}		
		return dequeuWithCounting(tekerleme);	
	}
	
	public static void main(String[] args) {
		new CrazyMarket(1000);
	}
}
