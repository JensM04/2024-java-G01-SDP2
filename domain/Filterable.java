package domain;

public interface Filterable<T> {
	boolean isInFilter(T filter);
}
