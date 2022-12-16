package fr.devops.game.util;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Wrapper pour une LinkedList avec des références faibles.
 * Impossible de stocker la valeur null.
 */
public class WeakLinkedList<T> implements List<T>, Collection<T>{

	private LinkedList<WeakReference<T>> wrappedList = new LinkedList<>();
	
	private void clearDiscarded() {
		wrappedList.removeIf(e -> e.get() == null);
	}
	
	@Override
	public int size() {
		clearDiscarded();
		return wrappedList.size();
	}

	@Override
	public boolean isEmpty() {
		return wrappedList.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		clearDiscarded();
		return wrappedList.stream().anyMatch(ref -> o.equals(ref.get()));
	}

	@Override
	public Iterator<T> iterator() {
		clearDiscarded();
		return wrappedList.stream().map(i -> i.get()).iterator();
	}

	@Override
	public Object[] toArray() {
		clearDiscarded();
		return wrappedList.stream().map(i -> i.get()).toArray();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] a) {
		clearDiscarded();
		if (a.length>=wrappedList.size()) {
			for (var i = 0; i < wrappedList.size(); i++) {
				a[i] = (T) wrappedList.get(i).get();
			}
			return a;
		}
		return (T[]) wrappedList.stream().map(i -> i.get()).toArray();
	}

	@Override
	public boolean add(T e) {
		if (e == null) {
			return false;
		}
		return wrappedList.add(new WeakReference<T>(e));
	}

	@Override
	public boolean remove(Object o) {
		if (o == null) {
			return false;
		}
		return wrappedList.removeIf(i -> o.equals(i.get()));
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		clearDiscarded();
		return wrappedList.stream().map(i -> i.get()).allMatch(i -> c.contains(i));
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		return wrappedList.addAll(c.stream().map(i -> new WeakReference<T>(i)).toList());
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		return wrappedList.addAll(index, c.stream().map(i -> new WeakReference<T>(i)).toList());
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return wrappedList.removeIf(i -> c.contains(i.get()));
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return wrappedList.removeIf(i -> !c.contains(i.get()));
	}

	@Override
	public void clear() {
		wrappedList.clear();
	}

	@Override
	public T get(int index) {
		clearDiscarded();
		return wrappedList.get(index).get();
	}

	@Override
	public T set(int index, T element) {
		return wrappedList.set(index, new WeakReference<T>(element)).get();
	}

	@Override
	public void add(int index, T element) {
		wrappedList.add(index, new WeakReference<T>(element));
	}

	@Override
	public T remove(int index) {
		return wrappedList.remove(index).get();
	}

	@Override
	public int indexOf(Object o) {
		if (o == null) {
			return -1;
		}
		int i = 0;
		for (var item : wrappedList) {
			if (o.equals(item.get())) {
				return i;
			}
			i++;
		}
		return -1;
	}

	@Override
	public int lastIndexOf(Object o) {
		if (o == null) {
			return -1;
		}
		int i = 0;
		var returned = -1;
		for (var item : wrappedList) {
			if (o.equals(item.get())) {
				returned = i;
			}
			i++;
		}
		return returned;
	}

	@Override
	public ListIterator<T> listIterator() {
		return null;
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		return null;
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		return wrappedList.stream().map(i -> i.get()).toList().subList(fromIndex, toIndex);
	}

	
	
}
