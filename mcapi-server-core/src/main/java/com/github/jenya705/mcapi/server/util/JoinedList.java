package com.github.jenya705.mcapi.server.util;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Immutable list which is joining lists
 *
 * @author Jenya705
 */
public class JoinedList<T> implements List<T> {

    private static class JoinedListIterator<T> implements ListIterator<T> {

        private final JoinedList<T> joinedList;

        private int current;
        private Iterator<T> currentIterator;

        public JoinedListIterator(JoinedList<T> joinedList) {
            this.joinedList = joinedList;
            current = 0;
            currentIterator = joinedList.lists.get(0).iterator();
        }

        @Override
        public boolean hasNext() {
            return (currentIterator != null && currentIterator.hasNext()) || (joinedList.lists.size() - 1 > current && !joinedList.lists.get(current + 1).isEmpty());
        }

        @Override
        public T next() {
            if (currentIterator.hasNext()) {
                return currentIterator.next();
            }
            if (joinedList.lists.size() > current) {
                do {
                    currentIterator = joinedList.lists.get(current).iterator();
                } while (!currentIterator.hasNext());
                return currentIterator.next();
            }
            throw new NoSuchElementException();
        }

        @Override
        public boolean hasPrevious() {
            throw new UnsupportedOperationException();
        }

        @Override
        public T previous() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int nextIndex() {
            return current + 1;
        }

        @Override
        public int previousIndex() {
            return current - 1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(T t) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(T t) {
            throw new UnsupportedOperationException();
        }

    }

    private final List<List<T>> lists;

    public JoinedList(List<List<T>> lists) {
        this.lists = lists;
    }

    @Override
    public T get(int index) {
        int cur = index;
        for (List<T> list : lists) {
            if (list.size() <= cur) {
                cur -= list.size();
            }
            else {
                return list.get(cur);
            }
        }
        throw outOfBounds(index);
    }

    @Override
    public T set(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        for (List<T> list : lists) {
            int index = list.indexOf(o);
            if (index != -1) return index;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = lists.size() - 1; i >= 0; i--) {
            List<T> list = lists.get(i);
            int index = list.lastIndexOf(o);
            if (index != -1) return index;
        }
        return -1;
    }

    @NotNull
    @Override
    public ListIterator<T> listIterator() {
        return new JoinedListIterator<>(this);
    }

    @NotNull
    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return lists.stream().mapToInt(List::size).sum();
    }

    @Override
    public boolean isEmpty() {
        return !lists.stream().anyMatch(List::isEmpty);
    }

    @Override
    public boolean contains(Object o) {
        return lists.stream().anyMatch(list -> list.contains(o));
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return listIterator();
    }

    @NotNull
    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size()];
        int current = 0;
        for (List<T> list: lists) {
            for (T element: list) {
                arr[current++] = element;
            }
        }
        return arr;
    }

    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    public <T1> T1[] toArray(@NotNull T1[] a) {
        T1[] arr = Arrays.copyOf(a, size());
        int current = 0;
        for (List<T> list: lists) {
            for (T element: list) {
                arr[current++] = (T1) element;
            }
        }
        return arr;
    }

    @Override
    public boolean add(T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return ListUtils.joinCopy(lists).containsAll(c);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, @NotNull Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    private RuntimeException outOfBounds(int index) {
        return new IndexOutOfBoundsException("Out of bounds. Index: " + index + " Size: " + size());
    }

}
