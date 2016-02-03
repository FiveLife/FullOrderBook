package orderbook_fx;

import java.math.RoundingMode;
import java.util.Comparator;

public class Order2 implements Comparable<Order2>{

    private String date;
    private long no;
    private String ticker;
    private String type;
    private long time;
    private long orderid;
    private byte action;
    private double price;
    private int volume;
		
		
	 
    public Order2(String date, long no, String ticker, String type, long time, long orderid, byte action,double price,int volume) {

            super();
            this.date = date;
            this.no = no;
            this.ticker = ticker;
            this.type=type;
            this.time = time;
            this.orderid =  orderid;
            this.action = action;
            this.price = price;
            this.volume = volume;
    }
	 
    public byte getAction() {
            return action;
    }

    public void setAction(byte action) {
            this.action = action;
    }

    public double getprice() {
            return price;
    }

    public void setprice(double price) {
            this.price = price;
    }

    public int getVolume() {
            return volume;
    }

    public void setVolume(int volume) {
            this.volume = volume;
    }

    public String getticker() {
            return ticker;
    }
    public void setticker(String ticker) {
            this.ticker = ticker;
    }

    public String getdate() {
            return date;
    }
    public void setdate(String date) {
            this.date = date;
    }
    public long getorderid() {
            return orderid;
    }
    public void setorderid(long orderid) {
            this.orderid = orderid;
    }

				
    @Override
    public int compareTo(Order2 o) {

        Order2 f = (Order2)o;

        if (this.price > f.price) {
            return 1;
        }
        else if (this.price <  f.price) {
            return -1;
        }
        else {
            return 0;
        }

    }

    @Override
    public String toString() {
        return ticker +"," + date +","+orderid+","+action+","+price+","+volume ;
    }
		
}

