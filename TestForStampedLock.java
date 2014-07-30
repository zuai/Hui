
import java.util.concurrent.TimeUnit;

import com.hui.stamped.pac.StampedLock;

public class TestForStampedLock {

    /**
     * @param args
     */
    public static void main(String[] args) throws InterruptedException{
        // TODO Auto-generated method stub
        StampedLock lock = new StampedLock();
        new Thread(new WriteThread(lock)).start();
//        new Thread(new ReadThreadTry(lock)).start();
        Thread.sleep(200);
        new Thread(new ReadThreadTry(lock)).start();
        Thread.sleep(100);
//        new Thread(new ReadThreadTry(lock)).start();
        for( int i = 0; i < 6; ++i)
            new Thread(new ReadThread(lock)).start();
        Thread.sleep(300);
        for( int i = 0; i < 6; ++i)
            new Thread(new WriteThreadLast(lock)).start();
    }
    
    
    
    
    private static class WriteThread implements Runnable{
        private StampedLock lock;
        public WriteThread(StampedLock lock){
            this.lock = lock;
        }
        
        public void run(){
            long writeLong = lock.writeLock();
            System.out.println(Thread.currentThread().getName() + " WRITE");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            lock.unlockWrite(writeLong);
        }
    }
    
    private static class ReadThreadTry implements Runnable{
        private StampedLock lock;
        public ReadThreadTry(StampedLock lock){
            this.lock = lock;
        }
        
        public void run(){
            long readLong = 0;
            try {
                readLong = lock.tryReadLock(600, TimeUnit.MILLISECONDS);
                if(readLong  > 0)
                    System.out.println(Thread.currentThread().getName() + " READ LOCK");
                else
                    System.out.println(Thread.currentThread().getName() + " READ noLOCK");
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally{
                if(readLong>0)
                    lock.unlockRead(readLong);
            }
        }
    }
    
    private static class ReadThread implements Runnable{
        private StampedLock lock;
        public ReadThread(StampedLock lock){
            this.lock = lock;
        }
        
        public void run(){
            long readLong = lock.readLock();
            System.out.println(Thread.currentThread().getName() + " READ");
            lock.unlockRead(readLong);
        }
    }
    
    private static class WriteThreadLast implements Runnable{
        private StampedLock lock;
        public WriteThreadLast(StampedLock lock){
            this.lock = lock;
        }
        
        public void run(){
            long writeLong = lock.writeLock();
            System.out.println(Thread.currentThread().getName() + " WRITE");
            lock.unlockWrite(writeLong);
        }
    }

}
