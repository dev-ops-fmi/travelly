package com.sofia.uni.fmi.travelly.controller;

import com.sofia.uni.fmi.travelly.dto.ItemCreateUpdateDto;
import com.sofia.uni.fmi.travelly.dto.ItemDto;
import com.sofia.uni.fmi.travelly.mapper.ItemMapper;
import com.sofia.uni.fmi.travelly.model.Item;
import com.sofia.uni.fmi.travelly.service.ItemService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("items")
public class ItemController {
    private ItemService itemService;
    private ItemMapper itemMapper;

    public ItemController(ItemService itemService, ItemMapper itemMapper) {
        this.itemService = itemService;
        this.itemMapper = itemMapper;
    }

    @PatchMapping("{itemId}")
    public Long updateItem(@PathVariable Long itemId, @RequestBody ItemCreateUpdateDto itemCreateUpdateDto) {
        Item item = itemMapper.toEntity(itemCreateUpdateDto);
        item.setId(itemId);
        Item updatedItem = itemService.updateItem(item);
        ItemDto updatedItemDto = itemMapper.toDto(updatedItem);

        return updatedItemDto.getId();
    }

    @DeleteMapping("{itemId}")
    public void deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
    }
}
