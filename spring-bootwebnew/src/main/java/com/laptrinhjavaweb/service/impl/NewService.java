package com.laptrinhjavaweb.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.laptrinhjavaweb.converter.NewConverter;
import com.laptrinhjavaweb.dto.NewDTO;
import com.laptrinhjavaweb.entity.CategoryEntity;
import com.laptrinhjavaweb.entity.NewEntity;
import com.laptrinhjavaweb.repository.CategoryRepository;
import com.laptrinhjavaweb.repository.NewRepository;
import com.laptrinhjavaweb.service.INewService;
@Service
public class NewService implements INewService{
	@Autowired
NewRepository newRepository;
	@Autowired
CategoryRepository categoryRepository;
    @Autowired
NewConverter newConverter;
	@Override
	public NewDTO save(NewDTO newDTO) {
		NewEntity newEntity = new NewEntity();
		if(newDTO.getId() !=  null) {
			  NewEntity oldNewEntity = newRepository.findById(newDTO.getId()).orElse(new NewEntity());
			  newEntity = newConverter.toNewEntity(newDTO, oldNewEntity);
		}else {
			  newEntity = newConverter.toNewEntity(newDTO);
		}
		CategoryEntity category = categoryRepository.findOneByCode(newDTO.getCategoryCode());
		newEntity.setCategory(category);
		newEntity = newRepository.save(newEntity);
		return newConverter.toNewDTO(newEntity);
	}
	/*
	 * @Override public NewDTO update(NewDTO newDTO) { NewEntity oldNewEntity =
	 * newRepository.findOne(newDTO.getId()); CategoryEntity category =
	 * categoryRepository.findOneByCode(newDTO.getCategoryCode()); NewEntity
	 * newEntity = newConverter.toNewEntity(newDTO, oldNewEntity);
	 * newEntity.setCategory(category); newEntity = newRepository.save(newEntity);
	 * return newConverter.toNewDTO(newEntity); }
	 */
	@Override
	public void delete(long[] ids) {
		for(long item:ids) {
			newRepository.deleteById(item);
		}
		
	}
	@Override
	public List<NewDTO> findAll(int page, int limit) {
		List<NewDTO> news = new ArrayList<>();
		Page<NewEntity> pageEntity = newRepository.findAll(PageRequest.of(page, limit));
		List<NewEntity> listEntity = pageEntity.getContent();
		for(NewEntity item: listEntity) {
			NewDTO dto = newConverter.toNewDTO(item);
			news.add(dto);
		}
		
		return news;
	}
	@Override
	public int totalItem() {
		
		return (int) newRepository.count();
	}
	@Override
	public List<NewDTO> findAll() {
		
		List<NewDTO> news = new ArrayList<>();
		List<NewEntity> listEntity = newRepository.findAll();
		
		for(NewEntity item: listEntity) {
			NewDTO dto = newConverter.toNewDTO(item);
			news.add(dto);
		}
		
		return news;
	}
}
