public class Customer {
	//datafield tiplerini degistirebilirsiniz
	float arrivalTime; //musteri gelis zamani-
	//bekleme zamanini hesaplamada kullanabilirsiniz
	float removalTime;
		
	public Customer(float arrivalTime) {
		this.arrivalTime=arrivalTime;
	}
		
	public void setRemovalTime(float removalTime) {
		this.removalTime = removalTime;
	}
	
	public float getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(float arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public float getRemovalTime() {
		return removalTime;
	}

	
	
}