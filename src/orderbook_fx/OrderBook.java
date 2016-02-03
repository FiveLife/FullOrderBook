package orderbook_fx;

public class OrderBook implements Comparable<OrderBook>{

    private double orderprice;
    private int volume;

    public OrderBook(double orderprice,int volume) {
            super();
            this.orderprice = orderprice;
            this.volume = volume;

    }

    public double getOrderprice() {
            return orderprice;
    }

    public void setOrderprice(double orderprice) {
            this.orderprice = orderprice;
    }

    public int getVolume() {
            return volume;
    }

    public void setVolume(int volume) {
            this.volume = volume;
    }



    @Override
    public int compareTo(OrderBook o) {

        OrderBook f = (OrderBook)o;

        if (this.orderprice > f.orderprice) {
            return 1;
        }
        else if (this.orderprice <  f.orderprice) {
            return -1;
        }
        else {
            return 0;
        }

    }

    @Override
    public String toString() {
        return volume+"\t"+ orderprice ;
    }
		
}

