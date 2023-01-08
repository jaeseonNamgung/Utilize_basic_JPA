package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository repository;

    @Transactional
    public void saveItem(Item item) {
        repository.save(item);
    }

    public Item findOne(Long id){
        return repository.findOne(id);
    }

    public List<Item> findAll(){
        return repository.findAll();
    }

    @Transactional
    public void updateItem(Long id, String name, int price, String author){
        Book book = (Book)repository.findOne(id);
        book.setName(name);
        book.setPrice(price);
        book.setAuthor(author);
    }

}
