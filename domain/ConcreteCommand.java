package domain;

public abstract class ConcreteCommand<T> implements Command {
	protected final T item;

	public ConcreteCommand(T item) {
		this.item = item;
	}
}
