package org.apache.jmeter.protocol.http.sampler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class TestConcurrent {

    public static void main(String[] args) {
        
        final List<String> concList = Collections.synchronizedList(new ArrayList<String>());
        for (int i = 0; i < 100; i++) {
            concList.add(""+i);
        }
        
        for (int i = 0; i < 2; i++) {
            final int threadId = i;
            Thread t = new Thread(new Runnable() {
                
                public void run() {
                    Iterator<String> it = concList.iterator();
                    while(it.hasNext())
                    {
                        String s = it.next();
                        if(threadId%2==0)
                        {
                            if((Integer.parseInt(s) % 2==0))
                            {
                                it.remove();
                            }
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            // nOOp
                        }
                    }
                }
            });
            t.start();
        }
    }
}
