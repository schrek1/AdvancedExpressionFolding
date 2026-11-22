package data;

import <fold text='...' expand='false'>java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;</fold>

@SuppressWarnings("ALL")
public class WithLockTestData {
    private final Lock lock = <fold text='' expand='false'>new </fold>ReentrantLock();
    private final Lock otherLock = <fold text='' expand='false'>new </fold>ReentrantLock();
    private final ReadWriteLock stockLock = <fold text='' expand='false'>new </fold>ReentrantReadWriteLock();
    private int balance;

    public void deposit(int amount) <fold text='{...}' expand='true'>{
       <fold text=' withLock(' expand='true'> </fold>lock<fold text='' expand='true'>.lock(</fold>)<fold text='' expand='true'>;</fold><fold text='' expand='true'>
        </fold><fold text='' expand='true'>try</fold> <fold text='{...}' expand='true'>{
            balance += amount;
        }</fold><fold text='' expand='true'> finally <fold text='{...}' expand='true'>{
            lock.unlock();
        }</fold></fold>
    }</fold>

    public int balance() <fold text='{...}' expand='true'>{
       <fold text=' withLock(' expand='true'> </fold>this.lock<fold text='' expand='true'>.lock(</fold>)<fold text='' expand='true'>;</fold><fold text='' expand='true'>
        </fold><fold text='' expand='true'>try</fold> <fold text='{...}' expand='true'>{
            return balance;
        }</fold><fold text='' expand='true'> finally <fold text='{...}' expand='true'>{
            this.lock.unlock();
        }</fold></fold>
    }</fold>

    public int readStock() <fold text='{...}' expand='true'>{
       <fold text=' withLock(' expand='true'> </fold>stockLock.readLock()<fold text='' expand='true'>.lock(</fold>)<fold text='' expand='true'>;</fold><fold text='' expand='true'>
        </fold><fold text='' expand='true'>try</fold> <fold text='{...}' expand='true'>{
            <fold text='val' expand='false'>int</fold> current = balance;
            return current * 2;
        }</fold><fold text='' expand='true'> finally <fold text='{...}' expand='true'>{
            stockLock.readLock().unlock();
        }</fold></fold>
    }</fold>

    public void withCatch() <fold text='{...}' expand='true'>{
        lock.lock();
        try <fold text='{...}' expand='true'>{
            balance++;
        }</fold> catch <fold text='' expand='false'>(</fold>RuntimeException e<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            balance--;
        }</fold> finally <fold text='{...}' expand='true'>{
            lock.unlock();
        }</fold>
    }</fold>

    public void differentLock() <fold text='{...}' expand='true'>{
        lock.lock();
        try <fold text='{...}' expand='true'>{
            balance++;
        }</fold> finally <fold text='{...}' expand='true'>{
            otherLock.unlock();
        }</fold>
    }</fold>

    public void moreInFinally() <fold text='{...}' expand='true'>{
        lock.lock();
        try <fold text='{...}' expand='true'>{
            balance++;
        }</fold> finally <fold text='{...}' expand='true'>{
            lock.unlock();
            balance--;
        }</fold>
    }</fold>

    public void statementBetween() <fold text='{...}' expand='true'>{
        lock.lock();
        balance++;
        try <fold text='{...}' expand='true'>{
            balance++;
        }</fold> finally <fold text='{...}' expand='true'>{
            lock.unlock();
        }</fold>
    }</fold>

    public void tryLock() <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>lock.tryLock()<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            try <fold text='{...}' expand='true'>{
                balance++;
            }</fold> finally <fold text='{...}' expand='true'>{
                lock.unlock();
            }</fold>
        }</fold>
    }</fold>

    public void lockInsideTry() <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold>try <fold text='{...}' expand='true'>{
            lock.lock();
            balance++;
        }</fold> finally <fold text='{...}' expand='true'>{
            lock.unlock();
        }</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    public void freshLockEachTime() <fold text='{...}' expand='true'>{
        newLock().lock();
        try <fold text='{...}' expand='true'>{
            balance++;
        }</fold> finally <fold text='{...}' expand='true'>{
            newLock().unlock();
        }</fold>
    }</fold>

    private Lock newLock()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>new </fold>ReentrantLock()<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>
}
