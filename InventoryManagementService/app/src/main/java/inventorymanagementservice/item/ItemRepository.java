package inventorymanagementservice.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<ItemModel, Long> {
    // Custom query method to find by name (case-insensitive)
    Optional<ItemModel> findByNameIgnoreCase(String name);
}

// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import java.util.Optional;

// import org.springframework.stereotype.Repository;

// import jakarta.annotation.PostConstruct;

// @Repository
// public class ItemRepository {
//     private Map<String, ItemModel> itemsMap = new HashMap<String, ItemModel>();
//     private List<ItemModel> itemsList = new ArrayList<ItemModel>();

//     public Optional<ItemModel> retrieveItem(String itemName) {
//         return Optional.ofNullable(itemsMap.get(itemName.toLowerCase()));
//     }

//     public Optional<List<ItemModel>> retrieveAllItems() {
//         if (itemsMap.isEmpty()) {
//             return Optional.empty();
//         }

//         List<ItemModel> retrievedItems = new ArrayList<>(itemsMap.values());
//         return Optional.of(retrievedItems);
//     }

//     public boolean addItem(ItemModel item) {
//         if (item == null || item.getName() == null || item.getName().isEmpty()) {
//             return false;
//         }
//         itemsMap.put(item.getName().toLowerCase(), item);
//         return true;
//     }

//     public boolean editItem(String itemName, ItemModel item) {
//         if (this.retrieveItem(itemName).isEmpty()) {
//             return false;
//         }
//         itemsMap.put(itemName, item);
//         return true;
//     }

//     public boolean removeItem(String itemName){
//         if (this.retrieveItem(itemName).isEmpty()) {
//             return false;
//         }
//         itemsMap.remove(itemName);
//         return true;
//     }

//     @PostConstruct // Runs After Construction of the class
//     private void init() {
//         itemsMap.put("Milk".toLowerCase(), new ItemModel("Milk", "This is 1 litre of Milk", 10.0));
//         itemsMap.put("Egg".toLowerCase(), new ItemModel("Egg", "This is 12 pieces of Eggs", 12.0));
//     }
// }

