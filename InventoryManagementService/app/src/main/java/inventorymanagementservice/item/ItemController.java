package inventorymanagementservice.item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/items") // Group Routing
public class ItemController {
    private static final Logger log = LoggerFactory.getLogger(ItemController.class);
    private final ItemRepository itemRepository;

    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository; // With Dependency Injection of Spring Boot Framework, it injects it into
                                              // here instead of always creating a new object.
    }

    @GetMapping("/get")
    public ResponseEntity<List<ItemModel>> getAllItems() {
        // Old implementation:
        // Optional<List<ItemModel>> optionalItems = Optional.ofNullable(itemsList.isEmpty() ? null : itemsList);

        List<ItemModel> items = itemRepository.findAll();
        
        return items.isEmpty() 
            ? ResponseEntity.notFound().build() 
            : ResponseEntity.ok(items);
    }

    @GetMapping("/get/{itemName}")
    public ResponseEntity<ItemModel> getItem(@PathVariable("itemName") String itemName) {
        // Old implementation:
        // return itemRepository.retrieveItem(itemName)
        //         .map(ResponseEntity::ok)
        //         .orElseGet(() -> ResponseEntity.notFound().build());

        return itemRepository.findByNameIgnoreCase(itemName)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createItem(@Valid @RequestBody ItemModel item) {
        // Old implementation:
        // if (!itemRepository.addItem(item)) {
        //     throw new ItemNotFoundException();
        // }

        try {
            ItemModel savedItem = itemRepository.save(item);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
        } catch (Exception e) {
            log.error("Error creating item", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/update/{itemName}")
    public ResponseEntity<?> updateItem(
            @PathVariable("itemName") String itemName,
            @Valid @RequestBody ItemModel item) {
        // Old implementation:
        // if (!itemRepository.editItem(itemName, item)) {
        //     throw new ItemNotFoundException();
        // }

        return itemRepository.findByNameIgnoreCase(itemName)
            .map(existingItem -> {
                // Update the existing item's properties
                existingItem.setName(item.getName());
                existingItem.setDescription(item.getDescription());
                existingItem.setPrice(item.getPrice());
                
                // Save the updated item
                ItemModel updatedItem = itemRepository.save(existingItem);
                return ResponseEntity.ok(updatedItem);
            })
            .orElseThrow(() -> new ItemNotFoundException("Item not found with name: " + itemName));
    }

    @DeleteMapping("/delete/{itemName}")
    public ResponseEntity<?> deleteItem(@PathVariable("itemName") String itemName) {
        // Old implementation:
        // if (!itemRepository.removeItem(itemName)) {
        //     throw new ItemNotFoundException();
        // }

        return itemRepository.findByNameIgnoreCase(itemName)
            .map(item -> {
                itemRepository.delete(item);
                return ResponseEntity.noContent().build();
            })
            .orElseThrow(() -> new ItemNotFoundException("Item not found with name: " + itemName));
    }
}
