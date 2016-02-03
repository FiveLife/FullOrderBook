package orderbook_fx;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class main {

    public static void main(String[] args) throws IOException {

        final String TICKER = "SBER";
        final long TIME = 184000000;
        final String DELIMITER = "|";
        
        String fileName="C:/Users/.../Documents/.../file.txt";
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        
        List<Order2> sell_order = new ArrayList<Order2>();
        List<Order2> buy_order = new ArrayList<Order2>();
        List<OrderBook> sell_book = new ArrayList<OrderBook>();
        List<OrderBook> buy_book = new ArrayList<OrderBook>();

        Long t1 = System.currentTimeMillis();
        String date=""; 
        int f1, f2,f3,f4,f5,f6,f7,f8,f9; // delimiter indices in string
        String ticker = "", type="";
        long time = 0, orderid=0L, no=0;
        byte action=0;
        double price=0;
        int volume=0;
        
        br.readLine();
        String data;
        while ((data = br.readLine()) != null) {
            try{
                f1 = data.indexOf(DELIMITER, 0);
                f2 = data.indexOf(DELIMITER, f1+1);
                f3 = data.indexOf(DELIMITER, f2+1);
                f4 = data.indexOf(DELIMITER, f3+1);
                f5 = data.indexOf(DELIMITER, f4+1);
                f6 = data.indexOf(DELIMITER, f5+1);
                f7 = data.indexOf(DELIMITER, f6+1);
                f8 = data.indexOf(DELIMITER, f7+1);
                f9 = data.indexOf(DELIMITER, f8+1);
                
                date = data.substring(0, f1);
                ticker = data.substring(f2+1, f3);
                type=data.substring(f3+1,f4);
                time=Long.parseLong(data.substring(f4+1,f5));
                orderid = Long.parseLong(data.substring(f5+1, f6));
                action = Byte.parseByte(data.substring(f6+1, f7));
                price = Double.parseDouble(data.substring(f7+1,f8));
                volume = Integer.parseInt(data.substring(f8+1,f9));
            }catch(java.lang.StringIndexOutOfBoundsException e){
                e.printStackTrace();
            }

            if(time>=TIME) break; 

            if(ticker.equals(TICKER)){
                
                //Action=1 (order placement)
                if(action ==1){
                    if(type.equals("B")){
                        buy_order.add(new Order2(date, no, ticker, type, time, orderid, action, price,volume));
                    }

                    if(type.equals("S")){
                        sell_order.add(new Order2(date, no, ticker, type, time, orderid, action,price,volume));
                    }
                }
                
                //Action=0 (order cancellation)
                if(action==0){

                    int i=-2;
                    int j=-2;
                    if(type.equals("S")){
                        for(Order2 d : sell_order){

                                if(d.getorderid() != 0 && (d.getorderid()==orderid)){
                                    i=sell_order.indexOf(d);
                                    break;
                                }
                        }
                        if(i<0) System.out.println("# where is the put order?;"+ data);
                        if(i!=-2) sell_order.remove(i);
                    }
                    
                    if(type.equals("B")){
                        for(Order2 d : buy_order){
                            if(d.getorderid() != 0 && (d.getorderid()==orderid)){
                                j=buy_order.indexOf(d);
                                break;
                            }
                        }
                        if(j<0) System.out.println("# where is the put order?;"+ data);
                        if(j!=-2) buy_order.remove(j);
                    }
                }

                //Action=2 (trade transaction) 
                if(action ==2 && type.equals("S")){
                        int i=-1;
                        for(Order2 d : sell_order){
                            if(d.getorderid() != 0 && (d.getorderid()==orderid))
                            i=sell_order.indexOf(d);
                        }

                        if(i!=-1){
                                if(sell_order.get(i).getVolume()<0) System.out.println("# Volume issue"+buy_order.get(i).getorderid());
                                sell_order.get(i).setVolume(sell_order.get(i).getVolume()-volume);
                                if (sell_order.get(i).getVolume()<=0) sell_order.remove(i);
                        }else System.out.println("!!!"+time+" - OrderId: "+ orderid);
                }

                if(action ==2 && type.equals("B")){	
                    int i=-1;
                    for(Order2 c : buy_order){
                        if(c.getorderid() != 0 && (c.getorderid()==orderid)){
                            i=buy_order.indexOf(c);
                        }
                    }

                    if(i!=-1){
                            if(buy_order.get(i).getVolume()<0) System.out.println("# Volume issue"+buy_order.get(i).getorderid());
                            buy_order.get(i).setVolume(buy_order.get(i).getVolume()-volume);
                            if (buy_order.get(i).getVolume()<=0) buy_order.remove(i);
                    }else System.out.println("!!!"+time+" - OrderId: "+ orderid);
                }
            }

            
        }
        
        // Orders aggregated by prices, Orders => OrderBook
        try{
            Collections.sort(sell_order);
            Collections.sort(buy_order);

            int sell_z=0;
            if(sell_order.size()>0){
                int sell_vol=sell_order.get(0).getVolume();
                sell_book.add(new OrderBook(sell_order.get(0).getprice(),sell_order.get(0).getVolume()));
                for(int i=1;i<sell_order.size();i++){
                    if(sell_order.get(i-1).getprice()==sell_order.get(i).getprice()){
                            sell_vol=sell_vol+sell_order.get(i).getVolume();
                            sell_book.set(sell_z, new OrderBook(sell_order.get(i).getprice(),sell_vol));
                    }else{
                            sell_vol=sell_order.get(i).getVolume();
                            sell_z++;
                            sell_book.add(new OrderBook(sell_order.get(i).getprice(),sell_vol));

                    }
                }
            }
            int buy_z=0;
            if(buy_order.size()>0){
                int buy_vol=buy_order.get(0).getVolume();
                buy_book.add(new OrderBook(buy_order.get(0).getprice(),buy_order.get(0).getVolume()));
                for(int i=1;i<buy_order.size();i++){
                    if(buy_order.get(i-1).getprice()==buy_order.get(i).getprice()){
                            buy_vol=buy_vol+buy_order.get(i).getVolume();
                            buy_book.set(buy_z, new OrderBook(buy_order.get(i).getprice(),buy_vol));
                    }else{
                            buy_vol=buy_order.get(i).getVolume();
                            buy_z++;
                            buy_book.add(new OrderBook(buy_order.get(i).getprice(),buy_vol));

                    }
                }
            }
        }catch(java.lang.IndexOutOfBoundsException e){
            e.printStackTrace();
        }

        //print result
        for(OrderBook str: buy_book)
                System.out.println(str);
        System.out.println("------------");
        for(OrderBook str: sell_book)
                System.out.println(str);
    
    br.close();
    System.out.println(System.currentTimeMillis()-t1);

    }

}
