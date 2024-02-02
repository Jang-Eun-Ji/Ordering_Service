package org.spring.Item.Service;

import org.spring.Item.Dto.ItemReqDto;
import org.spring.Item.Dto.ItemResDto;
import org.spring.Item.Dto.ItemSearchDto;
import org.spring.Item.domain.Item;
import org.spring.Item.repository.ItemRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item create(ItemReqDto itemReqDto) {
        MultipartFile multipartFile = itemReqDto.getItemImage();
        String fileName = multipartFile.getOriginalFilename();
        Item new_item = Item.builder()
                .name(itemReqDto.getName())
                .category(itemReqDto.getCategory())
                .price(itemReqDto.getPrice())
                .stockQuantity(itemReqDto.getStockQuantity())
                .build();
        Item item = itemRepository.save(new_item);
        Path path = Paths.get("/Users/jang-eunji/Desktop/tmp",item.getId() + "_" + fileName);
        item.setImagePath(path.toString());
        try {
            byte[] bytes = multipartFile.getBytes();
            Files.write(path, bytes, StandardOpenOption.CREATE,StandardOpenOption.WRITE);
        }catch (IOException e){
            throw new IllegalArgumentException("image not available");
        }
        return item;
    }

    public Item update(Long id, ItemReqDto itemReqDto) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("not found item"));

        MultipartFile multipartFile = itemReqDto.getItemImage();
        String fileName = multipartFile.getOriginalFilename();
        Path path = Paths.get("/Users/jang-eunji/Desktop/tmp", item.getId()+"_"+fileName);
        item.updateItem(itemReqDto.getName(), itemReqDto.getCategory(), itemReqDto.getPrice(), itemReqDto.getStockQuantity(), path.toString());
        try{
            byte[] bytes = multipartFile.getBytes();
            Files.write(path, bytes, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        } catch(IOException e){
            throw new IllegalArgumentException("image not available");
        }
        return item;
    }
    public Item delete(Long id) {
      Item item = itemRepository.findById(id).orElseThrow(()->new EntityNotFoundException("not found item"));
      item.deleteItem();
        return null;
    }

    public List<ItemResDto> findAll(ItemSearchDto itemSearchDto, Pageable pageable) {
//        검색을 위해 Specification 객체 사용
//        Sepecification객체는 복잡한 쿼리를 명세를 이용한 정의하여 쉽게 생성
        Specification<Item> spec = new Specification<Item>() {
            @Override
//            root: 엔티티의 속성을 접근하기 위한 객체, builder는 쿼리를 생성하기 위한 객체
            public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(itemSearchDto.getName()!=null){
                    predicates.add(criteriaBuilder.like(root.get("name"),"%" + itemSearchDto.getName() + "%"));
                }
                if(itemSearchDto.getCategory()!=null){
                    predicates.add(criteriaBuilder.like(root.get("category"),"%" + itemSearchDto.getCategory() + "%"));
                }
                predicates.add(criteriaBuilder.equal(root.get("delYn"),"N"));
                Predicate[] predicateArr = new Predicate[predicates.size()];
                for (int i =0; i<predicates.size(); i++){
                    predicateArr[i] = predicates.get(i);
                }
                Predicate predicate = criteriaBuilder.and(predicateArr);
                return predicate;
            }
        };

        Page<Item> item = itemRepository.findAll(spec, pageable);
        List<Item> itemList = item.getContent();
        List<ItemResDto> itemResDtos = new ArrayList<>();
        itemResDtos = itemList.stream().map(i -> ItemResDto.builder()
                .id(i.getId())
                .name(i.getName())
                .category(i.getCategory())
                .price(i.getPrice())
                .stockQuantity(i.getStockQuantity())
                .imagePath(i.getImagePath())
                .build()).collect(Collectors.toList());

        return itemResDtos;
    }

    public Resource getImage(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("not fount item"));
        String imagePath = item.getImagePath();
        Path path = Paths.get(imagePath);
        Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
        return resource;
    }
}
