package com.laptrinhjavaweb.service;

import java.util.List;

import com.laptrinhjavaweb.dto.NewDTO;

public interface INewService {
NewDTO save(NewDTO newDTO);
//NewDTO update(NewDTO newDTO);
void delete(long[] ids);
List<NewDTO> findAll(int page, int limit);
List<NewDTO> findAll();
int totalItem();
}
