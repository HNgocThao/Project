package elec.lab.service;

import java.util.Collection;

import elec.lab.domain.CartItem;

public interface ShoppingCartService{

	int getCount();

	double getAmount();

	void update(Long id, int quantity);

	void clear();

	Collection<CartItem> getCartItems();

	void remove(Long id);

	void add(CartItem item);



}
