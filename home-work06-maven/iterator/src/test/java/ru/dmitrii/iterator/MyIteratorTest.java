package ru.dmitrii.iterator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class MyIteratorTest {

    private MyIterator<Integer> myIterator;

    @Before
    public void setMyIterator() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        myIterator = new MyIterator<>(list);
    }

    @Test
    public void hasNext() {
        myIterator.next();
        myIterator.next();
        assertTrue(myIterator.hasNext());
        myIterator.next();
        assertFalse(myIterator.hasNext());
    }

    @Test
    public void next() {
        assertEquals(1, (int) myIterator.next());
        assertEquals(2, (int) myIterator.next());
        assertEquals(3, (int) myIterator.next());
        try {
            myIterator.next();
            Assert.fail("Expected IOException");
        } catch (IndexOutOfBoundsException thrown) {
            Assert.assertEquals("Нет больше элементов", thrown.getMessage());
        }
    }

    @Test
    public void hasPrevious() {
        for (int i = 0; i < 3; i++) {
            myIterator.next();
        }
        assertTrue(myIterator.hasPrevious());
        myIterator.previous();
        myIterator.previous();
        myIterator.previous();
        assertFalse(myIterator.hasPrevious());
    }

    @Test
    public void previous() {
        for (int i = 0; i < 3; i++) {
            myIterator.next();
        }
        assertEquals(3, (int) myIterator.previous());
        assertEquals(2, (int) myIterator.previous());
        assertEquals(1, (int) myIterator.previous());
        try {
            myIterator.previous();
            Assert.fail("Expected Exception");
        } catch (IndexOutOfBoundsException thrown) {
            Assert.assertEquals("Нет предыдущего элемента", thrown.getMessage());
        }
    }

    @Test
    public void nextIndex() {
        assertEquals(1, (int) myIterator.nextIndex());
        myIterator.next();
        assertEquals(2, (int) myIterator.nextIndex());
        myIterator.next();
        assertEquals(3, (int) myIterator.nextIndex());
    }

    @Test
    public void previousIndex() {
        myIterator.next();
        assertEquals(0, (int) myIterator.previousIndex());
        myIterator.next();
        assertEquals(1, (int) myIterator.previousIndex());
        myIterator.next();
        assertEquals(2, (int) myIterator.previousIndex());
    }

    @Test
    public void remove() {
        myIterator.remove();
        assertEquals(2, (int) myIterator.next());
        myIterator.remove();
        assertEquals(2, (int) myIterator.previous());
        myIterator.remove();
        try {
            myIterator.remove();
            Assert.fail("Expected Exception");
        } catch (IndexOutOfBoundsException thrown) {
            Assert.assertEquals("Нет элемента для удаления", thrown.getMessage());
        }
        assertEquals(0, myIterator.list.size());

    }

    @Test
    public void set() {
        myIterator.set(4);
        assertEquals(4, (int) myIterator.next());
        myIterator.set(8);
        assertEquals(8, (int) myIterator.next());
        assertEquals(3, myIterator.list.size());
    }

    @Test
    public void add() {
        myIterator.add(6);
        assertEquals(6, (int) myIterator.next());
        assertEquals(1, (int) myIterator.next());
        myIterator.add(9);
        assertEquals(1, (int) myIterator.previous());
        assertEquals(5, myIterator.list.size());

    }

    @Test
    public void mock() {
        MyIterator iterator = Mockito.spy(myIterator);
        Mockito.when(iterator.next()).thenReturn(0);
        Mockito.when(iterator.nextIndex()).thenReturn(10);
        assertEquals(0, iterator.next());
        assertEquals(10, iterator.nextIndex());
        Mockito.verify(iterator,Mockito.times(2));
    }

}